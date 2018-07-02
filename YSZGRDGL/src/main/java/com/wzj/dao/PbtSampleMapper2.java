package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.pojo.PbtSample2;

public interface PbtSampleMapper2 {
	
	@Select({"select * from lims_sample where HASREAD=0"})
	List<PbtSample2> getWait();
	
	@Update({"update lims_sample set hasread=1 where",
		" f_sample_key=#{key,jdbcType=VARCHAR}"})
	int chageRead(String key);
	
	int insert(PbtSample2 sample);
	
}
