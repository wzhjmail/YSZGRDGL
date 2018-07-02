package com.wzj.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.ReviewFormPart;

public interface ReviewFormPartMapper {
    int deleteById(Integer id);

    int insert(ReviewFormPart record);

    ReviewFormPart selectById(Integer id);

    int update(ReviewFormPart record);

	int insertList(List<ReviewFormPart> lists);

	@Select({"select * from ys_syndic_part where syndicId=",
		"#{reviewFormId,jdbcType=INTEGER}"})
	List<ReviewFormPart> getByReviewFormId(int reviewFormId);

	@Select({"select * from ys_syndic_part where " +
            "syndicId=#{reviewFormId,jdbcType=INTEGER} and " +
            "num=#{reviewFormNum,jdbcType=INTEGER}"})
    ReviewFormPart getByReviewFormNumId(@Param("reviewFormId")Integer reviewFormId, @Param("reviewFormNum")Integer reviewFormNum);

	int deleteByCId(int id);
}