package com.wzj.dao;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.Number;

public interface NumberMapper{
	
	@Select({"select * from sys_num where ID = 1"})
	@ResultMap("BaseResultMap")
	Number getNumber();
	
	int update(Number num);
}