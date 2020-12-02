package com.say.controller;

import com.say.bean.Post;
import com.say.config.ResponseWrapper;
import com.say.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "帖子管理")
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @ApiOperation("发送帖子")
    @PostMapping("/addPost")
    public ResponseWrapper addPost(@RequestBody Post post){
        ResponseWrapper wrapper = postService.addPost(post);
        return wrapper;
    }
    @ApiOperation("删除帖子")
    @DeleteMapping("/deletePost")
    public ResponseWrapper deletePost(@RequestParam(name = "id") Integer id){
        ResponseWrapper wrapper = postService.deletePost(id);
        return wrapper;
    }
    @ApiOperation("查找帖子或者查找自己发送的帖子")
    @GetMapping("/findPost")
    public ResponseWrapper findPost(@RequestParam(name = "uid",required = false)Integer uid,
                                    @RequestParam(name = "content",required = false)String content){
        ResponseWrapper wrapper = postService.findPost(uid,content);
        return wrapper;
    }
    @ApiOperation("单个帖子的详情界面")
    @GetMapping("/getPost")
    public ResponseWrapper getPost(@RequestParam(name ="id")Integer id,
                                   @RequestParam(name ="meid")Integer meid){
        ResponseWrapper wrapper = postService.getPost(id,meid);
        return wrapper;
    }

}
