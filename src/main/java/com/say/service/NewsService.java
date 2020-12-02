package com.say.service;

import com.say.bean.News;
import com.say.config.ResponseWrapper;
import com.say.dao.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NewsService {
    @Autowired
    NewsMapper newsMapper;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
    Date articletime= df.parse(date);//将string类型转换为Data

    public NewsService() throws ParseException {
    }

    public ResponseWrapper addNews(News news){
        news.setSendingtime(articletime);
        int i = newsMapper.insertSelective(news);
        if (i == 1){
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }
    }
    public ResponseWrapper findNews(Integer meid,Integer fid){
        List<News> news = newsMapper.findNews(meid,fid);
        List<News> news1 = newsMapper.findNew(meid,fid);
        if (news.size() > 0){
            ResponseWrapper wrapper = ResponseWrapper.markSuccess(news);
            wrapper.getData().put("send",news);
            wrapper.getData().put("receive",news1);
            return wrapper;
        }else{
            return ResponseWrapper.markSuccess(news);
        }
    }
    public ResponseWrapper newLists(Integer meid){
        List<News> a = newsMapper.findFrindNews(meid);
        if (a.size() > 0) {
            int i ;

            ArrayList<Object> news1 = new ArrayList<>();
            for (i = 0 ;i<a.size();i++){
                int fid = a.get(i).getFid();
                List<Object> news = newsMapper.findLastNews(meid,fid);
                news1.add(news);
            }
            ResponseWrapper wrapper = ResponseWrapper.markSuccess(news1);
            wrapper.getData().put("list",news1);
            return wrapper;
        }
        System.out.println(a);
        return ResponseWrapper.markSuccess(a);
    }
}
