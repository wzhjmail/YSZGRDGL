package com.wzj.dao;

import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.PbtOperation;

public interface OperationMapper {

	public int insertOperation(PbtOperation pbt);
	
	public int updateOperation(PbtOperation pbt);
	
	@Select({"select * from pbt_operation where ",
		"f_operation_code=#{code,jdbcType=VARCHAR}"})
	public PbtOperation getOperation(String code);
	
	@Select({"select count(*) from act_hi_comment where ",
		"PROC_INST_ID_=#{insId,jdbcType=VARCHAR} and TYPE_='back'"})
	public int count(String insId);
	
}
