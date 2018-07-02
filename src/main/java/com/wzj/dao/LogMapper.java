package com.wzj.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.criteria.From;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.Log;

public interface LogMapper {
	@Insert({"insert into log (time,person,department,operation,operationType) ",
		"values (#{time,jdbcType=DATE},#{person,jdbcType=VARCHAR},#{department,jdbcType=VARCHAR},",
		"#{operation,jdbcType=VARCHAR},#{operationType,jdbcType=VARCHAR})"})
	int insert(Log log);
	
	@Select({"select * from log order by id desc"})
	List<Log> findAll();

	@Select({"select * from log where time >= ",
		"#{stime,jdbcType=VARCHAR} and time <=#{etime,jdbcType=VARCHAR}"})
	List<Log> findByTime(@Param("stime")String stime,@Param("etime")String etime);

	@Delete({"delete from log where time < #{time,jdbcType=VARCHAR}"})
	void deleteBefore(Timestamp time);

	@Select({"select * from log limit 1"})
	Log getFirstLog();

	@Select({"select * from log where person like #{str,jdbcType=VARCHAR}",
		"or department like #{str,jdbcType=VARCHAR}",
		"or operation like #{str,jdbcType=VARCHAR}",
		"or operationType like #{str,jdbcType=VARCHAR}"})
	List<Log> selectAll(String str);

	@Select({"select * from log where concat_ws(time,person,department,operation,operationType) "
		,"like #{sSearch,jdbcType=VARCHAR}"})
	List<Log> findByStr(String sSearch);

}
