package com.wzj.dao;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.ReviewForm;

public interface ReviewFormMapper {
    int deleteByIdy(Integer id);

    int insert(ReviewForm record);

    ReviewForm selectById(Integer id);

    int update(ReviewForm record);

    @Select({"select * from ys_syndic where pid=#{bId,jdbcType=INTEGER}"})
	ReviewForm getReviewByBId(int bId);
    
    @Select({"select * from ys_syndic where ",
		"TitleNo=#{titleNo,jdbcType=VARCHAR} order by ID desc limit 1"})
    @ResultMap("BaseResultMap")
    ReviewForm getReviewByTitleNo(String titleNo);
}