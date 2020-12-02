package com.say.controller;

import com.say.bean.Comment;
import com.say.config.ResponseWrapper;
import com.say.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "评论管理")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @ApiOperation("增加评论")
    @PostMapping("/addComment")
    public ResponseWrapper addComment(@RequestBody Comment comment){
        ResponseWrapper wrapper = commentService.addComment(comment);
        return wrapper;
    }
    @ApiOperation("删除评论")
    @DeleteMapping("/deleteComment")
    public ResponseWrapper deleteComment(@RequestParam(name = "id")Integer id,
                                         @RequestParam(name = "meid")Integer meid){
        ResponseWrapper wrapper = commentService.deleteComment(id,meid);
        return wrapper;
    }
}
