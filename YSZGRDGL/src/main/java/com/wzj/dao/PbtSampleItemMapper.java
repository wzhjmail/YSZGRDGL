package com.wzj.dao;

import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.PbtSampleItem;

public interface PbtSampleItemMapper {
	
	public int delete(String f_sample_key);

	public int insert(PbtSampleItem pbt);

	public int update(PbtSampleItem pbt);
	
	@Select({"select * from PBT_SAMPLE_ITEM where F_SAMPLE_KEY = #{f_sample_key,jdbcType=VARCHAR}"})
	public PbtSampleItem findBySampleKey(String f_sample_key);
	
}