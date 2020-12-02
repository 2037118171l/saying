package com.say.controller;

import com.say.bean.Follow;
import com.say.config.ResponseWrapper;
import com.say.service.FollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "关注和被关注")
@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    FollowService followService;

    @ApiOperation("关注")
    @PostMapping("/addFollow")
    public ResponseWrapper addFollow(@RequestBody Follow follow){
        ResponseWrapper wrapper = followService.addFollow(follow);
        return wrapper;
    }
    @ApiOperation("取消关注")
    @DeleteMapping("/deleteFollow")
    public ResponseWrapper deleteFollow(@RequestParam(name = "meid")Integer meid,
                                        @RequestParam(name = "cid")Integer cid){
        ResponseWrapper wrapper = followService.deleteFollow(meid,cid);
        return wrapper;
    }
    @ApiOperation("关注的人")
    @GetMapping("/myFollow")
    public ResponseWrapper myFollow(@RequestParam(name = "meid")Integer meid){
        ResponseWrapper wrapper = followService.myFollow(meid);
        return wrapper;
    }
    @ApiOperation("粉丝")
    @GetMapping("/followMe")
    public ResponseWrapper followMe(@RequestParam(name = "meid")Integer meid){
        ResponseWrapper wrapper = followService.followMe(meid);
        return wrapper;
    }
}
