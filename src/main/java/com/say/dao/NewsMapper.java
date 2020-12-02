package com.say.dao;

import com.say.bean.News;
import com.say.bean.NewsExample;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NewsMapper {
    int countByExample(NewsExample example);

    int deleteByExample(NewsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    int insertSelective(News record);

    List<News> selectByExample(NewsExample example);

    News selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") News record, @Param("example") NewsExample example);

    int updateByExample(@Param("record") News record, @Param("example") NewsExample example);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);

    List<News> findNews(Integer meid,Integer fid);

    List<News> findNew(Integer meid,Integer fid);

    List<News> findFrindNews(Integer meid);

    List<Object> findLastNews(Integer meid, Integer fid);
}