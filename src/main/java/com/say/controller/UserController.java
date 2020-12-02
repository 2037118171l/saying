package com.say.controller;

import com.say.bean.User;
import com.say.config.ResponseWrapper;
import com.say.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public ResponseWrapper login(@RequestBody User user){
        ResponseWrapper wrapper = userService.login(user);
        return wrapper;
    }

    @ApiOperation("增加用户")
    @PostMapping("/addUser")
    public ResponseWrapper addUser(@RequestBody User user) throws ParseException {
        ResponseWrapper wrapper = userService.addUser(user);
        return wrapper;
    }

    @ApiOperation("修改用户")
    @PutMapping("/updateUser")
    public ResponseWrapper update(@RequestBody User user) throws ParseException {
        ResponseWrapper wrapper = userService.updateUser(user);
        return wrapper;
    }

    @ApiOperation("忘记密码")
    @GetMapping("/getPsw")
    public ResponseWrapper getPsw(@RequestParam(name = "number")String number,
                                  @RequestParam(name = "phone")String phone){
        ResponseWrapper wrapper = userService.getPsw(number,phone);
        return wrapper;
    }
    @ApiOperation("按照旧密码修改密码")
    @PutMapping("/updatePsw")
    public ResponseWrapper updatePsw(@RequestParam("uid")Integer uid,
                                     @RequestParam("oldpassword")String oldpassword,
                                     @RequestParam("newpassword")String newpassword){
        ResponseWrapper wrapper = userService.updatePsw(uid,oldpassword,newpassword);
        return wrapper;
    }
    @ApiOperation("图片上传")
    @PostMapping("/picUpload")
    public ResponseWrapper picUpload(@RequestParam("file") MultipartFile file,
                                     @RequestParam("uid")Integer uid) {
        ResponseWrapper wrapper =userService.picUpload(file,uid);
        return  wrapper;
    }
    @ApiOperation("好友列表查询好友")
    @GetMapping("/findUserBySearch")
    public ResponseWrapper findUserBySearch(@RequestParam("username")String username,
                                            @RequestParam("meid")Integer meid){
        ResponseWrapper wrapper = userService.findUserBySearch(username,meid);
        return wrapper;
    }
    @ApiOperation("查训自己账号")
    @GetMapping("/findMine")
    public ResponseWrapper findMine(@RequestParam("number")String number){
        ResponseWrapper wrapper = userService.findMine(number);
        return wrapper;
    }
}
