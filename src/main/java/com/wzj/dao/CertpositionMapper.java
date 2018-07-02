package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.Certposition;

public interface CertpositionMapper {
    int delete(Integer id);

    int insert(Certposition record);

    Certposition select(Integer id);

    int update(Certposition record);
    
    @Select({"select * from sys_certPosition"})
    List<Certposition> getAll();
    
    int changeUsed(int id);
    
    @Select({"select * from sys_certPosition where used=true"})
    @ResultMap("BaseResultMap")
	Certposition getUsing();

    @Select({"select count(*) from sys_certPosition where name=#{name,jdbcType=VARCHAR}"})
	int getCount(@Param("name")String name);
}