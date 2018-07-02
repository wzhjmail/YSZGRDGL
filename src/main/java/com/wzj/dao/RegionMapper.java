package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.Region;


public interface RegionMapper{

	//按码查找
	@Select({"select * from sys_code_region ",
		"where REGION_CODE=#{code,jdbcType=VARCHAR}"})
	@ResultMap("BaseResultMap")
	Region getRegionByCode(String code);
	
	//按名查找
	@Select({"select * from sys_code_region ",
		"where REGION_NAME=#{name,jdbcType=VARCHAR}"})
	@ResultMap("BaseResultMap")
	Region getRegionByName(String name);
	
	//列出下属
	@Select({"select * from sys_code_region ",
		"where PARENT_CODE=#{parent,jdbcType=VARCHAR}"})
	@ResultMap("BaseResultMap")
	List<Region> getChildren(String parent);	
	
	//分支机构对应地区
	@Select({"select * from sys_code_region ",
		"where PROVINCE_CODE=#{citycode,jdbcType=VARCHAR} OR CITY_CODE=#{citycode,jdbcType=VARCHAR}"})
	@ResultMap("BaseResultMap")
	List<Region> getRegionByDivision(String citycode);
	
	//获取城市码
	@Select({"select REGION_CODE from sys_code_region ",
		"where left(REGION_NAME,2)=left(#{divname,jdbcType=VARCHAR},2)"})
	String getCityCodeByDivisionName(String divname);

	List<Region> getAllRegion();

	List<Region> getAllRegionByList(List<String> list);
}