package com.say.service;

import com.say.bean.Friends;
import com.say.bean.User;
import com.say.bean.UserExample;
import com.say.config.ResponseWrapper;
import com.say.dao.FriendsMapper;
import com.say.dao.UserMapper;
import com.say.jwt.JWTUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    FriendsMapper friendsMapper;

    public ResponseWrapper login(User user){
        if (user == null){
            return ResponseWrapper.markError();
        }else {

            User list =  getUserByNumber(user.getNumber());
            if (list == null) {
                return ResponseWrapper.markCustom(false, 400, "用户不存在", null);
            } else {
                String realPassword = list.getPassword();
                if (!realPassword.equals(user.getPassword())) {
                    return ResponseWrapper.markCustom(false, 400, "密码错误", null);
                }
            }
            ResponseWrapper wrapper =ResponseWrapper.markSuccess(list);
            wrapper.getData().put("user",list);
            wrapper.getData().put("token",JWTUtil.createToken(user.getNumber()));
            return wrapper;
        }
    }

    public User getUserByNumber(String number){
        User user = userMapper.selectByNumber(number);
        return user;
    }

    public ResponseWrapper addUser(User user)  {
        User user1 = getUserByNumber(user.getNumber());
        if (user1 == null){
            int i = userMapper.insertSelective(user);
            if (i == 1) {
                return ResponseWrapper.markSuccess(i);
            }else {
                return ResponseWrapper.markError();
            }
        }else {
            return ResponseWrapper.markCustom(false,500,"账号不能重复",null);
        }

    }

    public ResponseWrapper updateUser(User user){
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i == 1) {
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }
    }
    public ResponseWrapper getPsw(String number,String phone){
        User user = userMapper.getPsw(number,phone);
        if (user == null){
            return ResponseWrapper.markError();
        }else {
            ResponseWrapper wrapper = ResponseWrapper.markSuccess(user);
            wrapper.getData().put("data",user);
            return wrapper;
        }
    }
    public ResponseWrapper updatePsw(Integer uid,String oldpassword,String newpassword){
        User user = userMapper.selectByPrimaryKey(uid);
        if (user.getPassword().equals(oldpassword)){
            User user1 = new User();
            user1.setUid(uid);
            user1.setPassword(newpassword);
            int i = userMapper.updateByPrimaryKeySelective(user1);
            if (i == 1){
                return ResponseWrapper.markSuccess(i);
            }else {
                return ResponseWrapper.markError();
            }
        }else {
            return ResponseWrapper.markCustom(false,500,"密码错误",null);
        }
    }
    public ResponseWrapper findUserBySearch(String username,Integer meid){
        List<User> users = userMapper.findUserBySearch(username,meid);
        if (users.size()>0){
            ResponseWrapper responseWrapper = ResponseWrapper.markSuccess(users);
            responseWrapper.getData().put("List",users);
            return responseWrapper;
        }else {
            return ResponseWrapper.markCustom(false,403,"没有该好友",null);
        }
    }
    public ResponseWrapper findMine(String number){
       User user =  getUserByNumber(number);
       if (user == null){
           return ResponseWrapper.markError();
       }else {
           ResponseWrapper wrapper = ResponseWrapper.markSuccess(user);
           wrapper.getData().put("date",user);
           return wrapper;
       }
    }
    @Value("${prop.upload-folder}")
    private String setPath;
    /**
     * 上传路径
     */
    private String uploadPath;
    /**
     * 返回路径
     */
    private String resPath;
    /**
     * @param path 需要遍历的路径
     * @return 路径下文件的名称集合
     */
    private static ArrayList<String> getFile(String path) {
        // 获得指定文件对象
        File file = new File(path);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        ArrayList<String> list = new ArrayList<>();
        if (array != null) {
            for (File value : array) {
                list.add(value.getName());
            }
        }
        return list;
    }
    /**
     * @param names 文件下文件名的集合
     * @param name  存入的文件名
     * @param index 索引的开始位置
     * @return 符合要求的文件名
     */
    private static String checkFileName(ArrayList<String> names, String name, int index) {
        String indexName;
        if (index == 0 ) {
            indexName = name;
        } else {
            indexName = name.substring(0, name.indexOf(".")) + "(" + index + ")" +name.substring(name.indexOf("."));
        }
        if (names.contains(indexName)) {
            index +=1;
            name = checkFileName(names, name, index);
        } else {
            return indexName;
        }
        return name;
    }
    public ResponseWrapper picUpload(MultipartFile file,Integer uid) {
        /*文件存放位置*/
        String picPath = "/picture/";
        uploadPath = setPath + picPath;
        /*返回文件路径*/
        resPath = picPath;
        /*获取文件数组*/
        ArrayList<String> list = getFile(uploadPath);
        if (file.isEmpty()) {
            return ResponseWrapper.markCustom(false,400,"上传失败",null);
        }
        /*获取文件名*/
        String getFileName = file.getOriginalFilename();
        String fileName;
        File saveFile;
        if (list.size() == 0) {
            /*创建文件*/
            saveFile = new File(uploadPath, getFileName);
        } else {
            /*遍历所有文件夹，判断是否重名*/
            fileName = checkFileName(list, getFileName,0);
            /*创建文件*/
            saveFile = new File(uploadPath, fileName);
        }
        //判断文件父目录是否存在
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        try {
            BufferedImage read = ImageIO.read(file.getInputStream());
            //压缩图片后保存
            Thumbnails.of(file.getInputStream())
                    .size(read.getWidth(), read.getHeight())
                    .toFile(saveFile.getPath());
            User user = new User();
            String url = "http://123.56.84.99:8085/picture/"+getFileName;
            user.setUid(uid);
            user.setHeadportrait("http://123.56.84.99:8085/picture/"+getFileName);
            userMapper.updateByPrimaryKeySelective(user);
            ResponseWrapper wrapper = ResponseWrapper.markCustom(true,200,"上传成功",url);
            wrapper.getData().put("url",url);
            return wrapper;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseWrapper.markCustom(false,400,"上传失败",null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseWrapper.markCustom(false,400,"上传失败",null);
        }
    }

}
