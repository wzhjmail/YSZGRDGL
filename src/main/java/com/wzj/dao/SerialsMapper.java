package com.wzj.dao;

import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.Serials;

public interface SerialsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Serials record);

    Serials selectByPrimaryKey(Integer id);

    int update(Serials record);

    @Select({"select * from serials where serial=#{serial,jdbcType=INTEGER}"})
	Serials getBySerial(int serial);
    
}