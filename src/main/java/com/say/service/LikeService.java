package com.say.service;

import com.say.bean.Like;
import com.say.config.ResponseWrapper;
import com.say.dao.LikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    LikeMapper likeMapper;

    public ResponseWrapper addLike(Like like){
        int i = likeMapper.insertSelective(like);
        if (i == 1){
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }

    }public ResponseWrapper deleteLike(Integer id,Integer meid){
        int i = likeMapper.deleteLike(id,meid);
        if (i == 1){
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }
    }
}
