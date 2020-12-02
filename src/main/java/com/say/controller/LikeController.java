package com.say.controller;

import com.say.bean.Like;
import com.say.config.ResponseWrapper;
import com.say.dao.LikeMapper;
import com.say.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "点赞管理")
@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    LikeService likeService;

    @ApiOperation("点赞")
    @PostMapping("/addLike")
    public ResponseWrapper addLike(@RequestBody Like like){
        ResponseWrapper wrapper = likeService.addLike(like);
        return wrapper;
    }

    @ApiOperation("取消点赞")
    @DeleteMapping("/deleteLike")
    public ResponseWrapper delete(@RequestParam(name = "id")Integer id,
                                  @RequestParam(name = "meid")Integer meid){
        ResponseWrapper wrapper = likeService.deleteLike(id,meid);
        return wrapper;
    }

}
