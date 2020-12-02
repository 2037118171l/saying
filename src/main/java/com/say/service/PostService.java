package com.say.service;

import com.say.bean.*;
import com.say.config.ResponseWrapper;
import com.say.dao.CommentMapper;
import com.say.dao.FollowMapper;
import com.say.dao.LikeMapper;
import com.say.dao.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PostService {
    @Autowired
    PostMapper postMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    LikeMapper likeMapper;
    @Autowired
    FollowMapper followMapper;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
    Date articletime= df.parse(date);//将string类型转换为Data

    public PostService() throws ParseException {
    }


    public ResponseWrapper addPost(Post post){
        post.setSendingtime(articletime);
        int i = postMapper.insertSelective(post);
        if (i == 1){
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }
    }
    public ResponseWrapper deletePost(Integer id){
        int i = postMapper.deleteByPrimaryKey(id);
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(id);
        LikeExample example1 = new LikeExample();
        LikeExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andPidEqualTo(id);
        int j = commentMapper.deleteByExample(example);
        int k = likeMapper.deleteByExample(example1);
        if (i == 1 && j >0 && k >0){
            return ResponseWrapper.markSuccess(i);
        }else {
            return ResponseWrapper.markError();
        }
    }
    public ResponseWrapper findPost(Integer uid,String content){
        List<Post> objects = postMapper.findPost(uid,content);
        if (objects.size()>0){
            ResponseWrapper wrapper = ResponseWrapper.markSuccess(objects);
            wrapper.getData().put("list",objects);
            return wrapper;
        }else {
            return ResponseWrapper.markSuccess(objects);
        }
    }
    public ResponseWrapper getPost(Integer id,Integer meid){
        List<Post> post = postMapper.getPost(id);
        System.out.println(post);
        if (post != null){
            LikeExample example = new LikeExample();
            LikeExample.Criteria criteria = example.createCriteria();
            criteria.andPidEqualTo(id);
            List<Like> likes = likeMapper.selectByExample(example);
            Integer listCounts = likes.size();
            List<Like> likeList = likeMapper.isLike(id,meid);
            List<Comment> comments = commentMapper.isComment(id);
            Integer commentCounts = comments.size();
            FollowExample example1 = new FollowExample();
            FollowExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andMeidEqualTo(meid);
            List<Follow> follow = followMapper.selectByExample(example1);
            ResponseWrapper wrapper = ResponseWrapper.markSuccess(post);
            wrapper.getData().put("date",post);
            wrapper.getData().put("likeCount",listCounts);
            wrapper.getData().put("commentCounts",commentCounts);
            wrapper.getData().put("comments",comments);
            wrapper.getData().put("isfollow", follow.size() > 0);
            wrapper.getData().put("isLike", likeList.size() == 1);
            return wrapper;
        }else {
            return ResponseWrapper.markError();
        }

    }

}
