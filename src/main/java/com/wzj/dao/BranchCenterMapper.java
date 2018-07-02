package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.DTO.DivRegionDTO;
import com.wzj.DTO.DivisionDTO;
import com.wzj.pojo.Division;
import com.wzj.pojo.Region;

public interface BranchCenterMapper {

	List<Division> findAll();

	@Select({"select * from sys_code_region"})
	List<Region> getAllRegion();

	Division getDivisionByCode(String code);

	@Select({"select * from ys_division_region where DIVISION_CODE like #{code,jdbcType=VARCHAR}"})
	List<String> getregionCodeByCode(@Param("code")String code);

	@Select({"select count(*) from ys_division_region where REGION_CODE=#{regionCode,jdbcType=VARCHAR}"})
	int getDivRegionCount(@Param("regionCode")String regionCode);

	@Update({"update ys_division_region","set DIVISION_CODE=#{divisioncode,jdbcType=VARCHAR},REGION_CODE = #{regioncode,jdbcType=VARCHAR}",
	"where ID=#{id,jdbcType=VARCHAR}"})
	void updateDivRegion(DivRegionDTO drDTO1);
}
