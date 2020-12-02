package com.say.service;

import com.say.bean.Friends;
import com.say.bean.User;
import com.say.bean.UserExample;
import com.say.config.ResponseWrapper;
import com.say.dao.FriendsMapper;
import com.say.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FriendsService {
    @Autowired
    FriendsMapper friendsMapper;

    @Autowired
    UserMapper userMapper;

    public User findUserByNumber(String number){
        UserExample example = new UserExample();
        UserExample.Criteria criteria =example.createCriteria();
        criteria.andNumberEqualTo(number);
        User user =  userMapper.selectByExample(example);
        return user;
    }

    public ResponseWrapper getFriends(String number,Integer meid){
        User user = findUserByNumber(number);
        if (user == null){
            return ResponseWrapper.markCustom(false,403,"没有该用户",null);
        }else {
            int fid = user.getUid();
            List<Friends> objects = friendsMapper.getFriends(fid, meid);
            if (objects.size()>0){
                ResponseWrapper responseWrapper = ResponseWrapper.markSuccess(user);
                responseWrapper.getData().put("list",objects);
                responseWrapper.getData().put("weather",true);
                return responseWrapper;
            }else {
                ResponseWrapper responseWrapper = ResponseWrapper.markSuccess(user);
                responseWrapper.getData().put("list",user);
                responseWrapper.getData().put("weather",false);
                return responseWrapper;
            }
        }
    }

    public ResponseWrapper addFriends(Friends friends){
        int i = friendsMapper.insertSelective(friends);
        Friends friends1 = new Friends();
        friends1.setFid(friends.getMeid());
        friends1.setMeid(friends.getFid());
        int j = friendsMapper.insertSelective(friends1);
        if (i == 1 && j == 1){
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }
    }
    public ResponseWrapper deleteFriends(Integer fid,Integer meid){
        int i = friendsMapper.deleteFriends(fid,meid);
        int j = friendsMapper.delFriends(fid,meid);
        if (i == 1 && j ==1){
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }
    }
    public ResponseWrapper findFriends(Integer meid){
        List<Objects> objects = friendsMapper.findFriends(meid);
        if (objects.size()>0){
            ResponseWrapper responseWrapper = ResponseWrapper.markSuccess(objects);
            responseWrapper.getData().put("data",objects);
            return responseWrapper;
        }else {
            return ResponseWrapper.markError();
        }
    }
}
