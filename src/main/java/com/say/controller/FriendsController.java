package com.say.controller;

import com.say.bean.Friends;
import com.say.config.ResponseWrapper;
import com.say.service.FriendsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "好友管理")
@RestController
@RequestMapping("/Friends")
public class FriendsController {

    @Autowired
    FriendsService friendsService;

    @ApiOperation("添加好友")
    @PostMapping("/addFriends")
    public ResponseWrapper addFriends(@RequestBody Friends friends){
        ResponseWrapper wrapper = friendsService.addFriends(friends);
        return wrapper;
    }
    @ApiOperation("查询是否为好友")
    @GetMapping("/getFriends")
    public ResponseWrapper getFriends(@RequestParam(name = "number")String number,
                                      @RequestParam(name = "meid")Integer meid){
        ResponseWrapper wrapper = friendsService.getFriends(number,meid);
        return wrapper;

    }
    @ApiOperation("删除好友")
    @DeleteMapping("/deleteFriends")
    public ResponseWrapper deleteFriends(@RequestParam(name = "fid") Integer fid,
                                         @RequestParam(name = "meid")Integer meid){
        ResponseWrapper wrapper = friendsService.deleteFriends(fid,meid);
        return wrapper;
    }

    @ApiOperation("查询所有好友")
    @GetMapping("/findFriends")
    public ResponseWrapper findFriends(@RequestParam(name = "meid")Integer meid){
        ResponseWrapper wrapper = friendsService.findFriends(meid);
        return wrapper;
    }
}
