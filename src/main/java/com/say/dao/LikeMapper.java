package com.say.dao;

import com.say.bean.Like;
import com.say.bean.LikeExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LikeMapper {
    int countByExample(LikeExample example);

    int deleteByExample(LikeExample example);

    int deleteByPrimaryKey(Integer lid);

    int insert(Like record);

    int insertSelective(Like record);

    List<Like> selectByExample(LikeExample example);

    Like selectByPrimaryKey(Integer lid);

    int updateByExampleSelective(@Param("record") Like record, @Param("example") LikeExample example);

    int updateByExample(@Param("record") Like record, @Param("example") LikeExample example);

    int updateByPrimaryKeySelective(Like record);

    int updateByPrimaryKey(Like record);

    List<Like> isLike(Integer id ,Integer meid);

    int deleteLike(Integer id,Integer meid);
}