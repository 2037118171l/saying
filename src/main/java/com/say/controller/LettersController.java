package com.say.controller;

import com.say.config.ResponseWrapper;
import com.say.service.LettersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "寄信管理")
@RestController
@RequestMapping("/letter")
public class LettersController {
    @Autowired
    LettersService lettersService;

    @ApiOperation("寄信")
    @PostMapping("/addLetter")
    public ResponseWrapper addLetter(@RequestParam("meid")Integer meid,
                                     @RequestParam("content")String content,
                                     @RequestParam("number")String number){
        ResponseWrapper wrapper = lettersService.addLetter(meid,content,number);
        return wrapper;
    }
    @ApiOperation("删除信件")
    @DeleteMapping("/deleteLetter")
    public ResponseWrapper deleteLetter(@RequestParam(name = "id")Integer id){
        ResponseWrapper wrapper = lettersService.deleteLetter(id);
        return wrapper;
    }
    @ApiOperation("我寄的信")
    @GetMapping("/myLetter")
    public ResponseWrapper myLetter(@RequestParam(name = "meid")Integer meid){
        ResponseWrapper wrapper = lettersService.myLetter(meid);
        return wrapper;
    }
    @ApiOperation("寄给我的信")
    @GetMapping("/letterToMe")
    public ResponseWrapper letterToMe(@RequestParam(name = "meid")Integer meid){
        ResponseWrapper wrapper = lettersService.letterToMe(meid);
        return wrapper;
    }
}
