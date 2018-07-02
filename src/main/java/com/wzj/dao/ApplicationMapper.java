package com.wzj.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.pojo.Application;
import com.wzj.pojo.ApplicationForm;

public interface ApplicationMapper {
	@Insert({"insert into application (id,name,state) ",
		"values (#{id,jdbcType=VARCHAR},#{name,jdbcType=VARCHAR},",
		"#{state,jdbcType=INTEGER})"})
	int insert(Application app);
	
	@Select({"select * from application where ",
		"id=#{id,jdbcType=VARCHAR}"})
	Application findById(String id);

	@Update({"update application set state=#{state,jdbcType=INTEGER} ",
		"where id=#{id,jdbcType=VARCHAR}"})
	int updateState(Application app);

	int insertApp(ApplicationForm app);
}
