package com.say.service;

import com.say.bean.Follow;
import com.say.config.ResponseWrapper;
import com.say.dao.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    @Autowired
    FollowMapper followMapper;

    public ResponseWrapper addFollow(Follow follow){
        int i = followMapper.insertSelective(follow);
        if (i == 1 ){
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }
    }
    public ResponseWrapper deleteFollow(Integer meid,Integer cid){
        int i = followMapper.deleteFollow(meid,cid);
        if (i == 1 ){
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }
    }
    public ResponseWrapper myFollow(Integer meid){
        List<Follow> follows = followMapper.myFollow(meid);
        if (follows.size() > 0){
            ResponseWrapper wrapper = ResponseWrapper.markSuccess(follows);
            wrapper.getData().put("list",follows);
            return wrapper;
        }else {
            return ResponseWrapper.markSuccess(follows);
        }
    }
    public ResponseWrapper followMe(Integer meid){
        List<Follow> follows = followMapper.followMe(meid);
        if (follows.size() > 0){
            ResponseWrapper wrapper = ResponseWrapper.markSuccess(follows);
            wrapper.getData().put("list",follows);
            return wrapper;
        }else {
            return ResponseWrapper.markSuccess(follows);
        }
    }
}
