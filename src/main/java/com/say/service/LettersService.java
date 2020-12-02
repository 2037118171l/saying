package com.say.service;

import com.say.bean.Letters;
import com.say.bean.User;
import com.say.bean.UserExample;
import com.say.config.ResponseWrapper;
import com.say.dao.LettersMapper;
import com.say.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class LettersService {
    @Autowired
    LettersMapper lettersMapper;
    @Autowired
    UserMapper userMapper;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
    Date articletime= df.parse(date);//将string类型转换为Data

    public LettersService() throws ParseException {
    }

    public ResponseWrapper addLetter(Integer meid,String content,String number){
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNumberEqualTo(number);
        User user = userMapper.selectByExample(example);
        if (user == null){
            return ResponseWrapper.markCustom(false,403,"未找到该用户",null);
        }
        Letters letters = new Letters();
        letters.setMeid(meid);
        letters.setFid(user.getUid());
        letters.setContent(content);
        letters.setTime(articletime);
        System.out.println(letters.getTime());
        System.out.println(letters.getFid());
        int i = lettersMapper.insertSelective(letters);
        if (i == 1){
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }
    }
    public ResponseWrapper deleteLetter(Integer id){
        int i = lettersMapper.deleteByPrimaryKey(id);
        if (i == 1){
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }
    }
    public ResponseWrapper myLetter(Integer meid){
        List<Letters> letters = lettersMapper.myLetter(meid);
        if (letters.size() > 0){
            ResponseWrapper wrapper = ResponseWrapper.markSuccess(letters);
            wrapper.getData().put("list",letters);
            return wrapper;
        }else {
            return ResponseWrapper.markError();
        }
    }
    public ResponseWrapper letterToMe(Integer meid){
        List<Letters> letters = lettersMapper.letterToMe(meid);
        if (letters.size() > 0){
            ResponseWrapper wrapper = ResponseWrapper.markSuccess(letters);
            wrapper.getData().put("list",letters);
            return wrapper;
        }else {
            return ResponseWrapper.markError();
        }
    }
}
