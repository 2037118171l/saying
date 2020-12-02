package com.say.controller;

import com.say.bean.News;
import com.say.config.ResponseWrapper;
import com.say.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "信息管理")
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    NewsService newsService;

    @ApiOperation("发送消息")
    @PostMapping("/addNews")
    public ResponseWrapper addNews(@RequestBody News news){
        ResponseWrapper wrapper = newsService.addNews(news);
        return wrapper;
    }
    @ApiOperation("查询所有消息")
    @GetMapping("/findNews")
    public ResponseWrapper findNews(@RequestParam("meid")Integer meid,
                                    @RequestParam("fid")Integer fid){
        ResponseWrapper wrapper = newsService.findNews(meid,fid);
        return wrapper;
    }
    @ApiOperation("消息列表")
    @GetMapping("/newLists")
    public ResponseWrapper newLists(@RequestParam("meid")Integer meid){
        ResponseWrapper wrapper = newsService.newLists(meid);
        return wrapper;
    }
}
