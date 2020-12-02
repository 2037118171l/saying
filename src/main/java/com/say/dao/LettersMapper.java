package com.say.dao;

import com.say.bean.Letters;
import com.say.bean.LettersExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LettersMapper {
    int countByExample(LettersExample example);

    int deleteByExample(LettersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Letters record);

    int insertSelective(Letters record);

    List<Letters> selectByExample(LettersExample example);

    Letters selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Letters record, @Param("example") LettersExample example);

    int updateByExample(@Param("record") Letters record, @Param("example") LettersExample example);

    int updateByPrimaryKeySelective(Letters record);

    int updateByPrimaryKey(Letters record);

    List<Letters> myLetter(Integer meid);

    List<Letters> letterToMe(Integer meid);
}