package com.say.dao;

import com.say.bean.Friends;
import com.say.bean.FriendsExample;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FriendsMapper {
    int countByExample(FriendsExample example);

    int deleteByExample(FriendsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Friends record);

    int insertSelective(Friends record);

    List<Friends> selectByExample(FriendsExample example);

    Friends selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Friends record, @Param("example") FriendsExample example);

    int updateByExample(@Param("record") Friends record, @Param("example") FriendsExample example);

    int updateByPrimaryKeySelective(Friends record);

    int updateByPrimaryKey(Friends record);

    List<Friends> getFriends(Integer fid, Integer meid);

    int deleteFriends(Integer fid, Integer meid);

    int delFriends(Integer fid, Integer meid);

    List<Objects> findFriends(Integer meid);
}