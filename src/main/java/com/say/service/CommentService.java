package com.say.service;

import com.say.bean.Comment;
import com.say.config.ResponseWrapper;
import com.say.dao.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
    Date articletime= df.parse(date);//将string类型转换为Data

    public CommentService() throws ParseException {
    }

    public ResponseWrapper addComment(Comment comment){
        comment.setSendingtime(articletime);
        int i = commentMapper.insertSelective(comment);
        if (i == 1){
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }
    }
    public ResponseWrapper deleteComment(Integer id,Integer meid){
        int i = commentMapper.deleteComment(id,meid);
        if (i == 1){
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }
    }
}
