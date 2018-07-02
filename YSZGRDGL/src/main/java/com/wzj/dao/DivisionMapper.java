package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.DTO.DivisionDTO;
import com.wzj.pojo.Division;

public interface DivisionMapper{
	
	@Select({"select * from ys_division"})
	@ResultMap("BaseResultMap")
	List<Division> getAllDivision();
	
	//按码查找
	@Select({"select * from ys_division ",
		"where DIVISION_CODE=#{code,jdbcType=VARCHAR}"})
	@ResultMap("BaseResultMap")
	Division getDivisionByCode(@Param("code")String code);
	
	//按名查找
	@Select({"select * from ys_division ",
	"where DIVISION_NAME=#{name,jdbcType=VARCHAR}"})
	@ResultMap("BaseResultMap")
	Division getDivisionByName(@Param("name")String name);
	
	//地区对应分支机构
	@Select({"select * from ys_division ",
	"where (left(DIVISION_NAME,2)=left(#{provincename,jdbcType=VARCHAR},2)) ",
	"OR (left(DIVISION_NAME,2)=left(#{cityname,jdbcType=VARCHAR},2))"})
	@ResultMap("BaseResultMap")
	List<Division> getDivisionByRegion(@Param("provincename")String provincename,@Param("cityname")String cityname);

	@Select({"select count(*) from ys_division where DIVISION_CODE=#{code,jdbcType=VARCHAR}"})
	int getDivCountByCode(@Param("code")String code);

	@Insert({"insert into ys_division (DIVISION_CODE,DIVISION_NAME,DIVISION_ADDRESS,DIVISION_POSTCODE,DIVISION_LINKMAN,DIVISION_PHONE1,DIVISION_PHONE2,DIVISION_FAX)",
  		"values (#{divisioncode,jdbcType=VARCHAR},#{divisionname,jdbcType=VARCHAR},"
  		+ "#{divisionaddress,jdbcType=VARCHAR},#{divisionpostcode,jdbcType=VARCHAR},#{divisionlinkman,jdbcType=VARCHAR},#{divisionphone1,jdbcType=VARCHAR},#{divisionphone2,jdbcType=VARCHAR},#{divisionfax,jdbcType=VARCHAR})"})
	void insert(Division div);

	@Select({"select * from sys_code_region ",
	"where REGION_CODE=#{code,jdbcType=VARCHAR}"})
	Division getByCode(@Param("code")String code);

	@Insert({"insert into ys_division_region (DIVISION_CODE,REGION_CODE)",
		"values(#{code,jdbcType=VARCHAR},#{regionCode,jdbcType=VARCHAR})"})
	void setRegionId(@Param("code")String code, @Param("regionCode")String regionCode);

	@Delete({"delete from ys_division_region ","where DIVISION_CODE=#{code,jdbcType=INTEGER}"})
	void deleteBranch(@Param("code")String code);

	@Delete({"delete from ys_division ","where DIVISION_CODE=#{code,jdbcType=INTEGER}"})
	int deleteBranchByCode(@Param("code")String code);

	@Update({"update ys_division","set DIVISION_CODE=#{code,jdbcType=VARCHAR},DIVISION_NAME = #{name,jdbcType=VARCHAR},",
		"DIVISION_ADDRESS=#{address,jdbcType=VARCHAR},DIVISION_POSTCODE=#{postcode,jdbcType=VARCHAR},DIVISION_LINKMAN=#{linkman,jdbcType=VARCHAR},DIVISION_PHONE1=#{phone1,jdbcType=VARCHAR},",
		"DIVISION_PHONE2=#{phone2,jdbcType=VARCHAR},DIVISION_FAX=#{fax,jdbcType=VARCHAR}",
	"where DIVISION_CODE=#{branchcode,jdbcType=VARCHAR}"})
	int updateBranchByCode(DivisionDTO divdto);

	@Update({"update ys_division_region","set DIVISION_CODE=#{code,jdbcType=VARCHAR},REGION_CODE = #{regionCode,jdbcType=VARCHAR}",
		"where DIVISION_CODE=#{branchcode,jdbcType=VARCHAR}"})
	void updateBranch(String branchcode, String regionCode, String code);

	@Update({"update ys_company_import set branchId=#{id,jdbcType=VARCHAR}",
		"where OffshootOrganiz=#{name,jdbcType=VARCHAR}"})
	void setBname(@Param("id")String id, @Param("name")String name);

	@Select({"select * from ys_division ",
	"where DIVISION_NAME like #{name,jdbcType=VARCHAR}  ORDER BY division_code desc limit 1"})
	@ResultMap("BaseResultMap")
	Division getDivisionLikeName(String name);

	@Select({"select count(*) from ys_division ",
	"where DIVISION_NAME = #{name,jdbcType=VARCHAR}"})
	int countBranch(@Param("name")String name);

	@Select({"select * from ys_division ",
	"where DIVISION_CODE= #{code,jdbcType=VARCHAR}"})
	@ResultMap("BaseResultMap")
	Division getByDivCode(@Param("code")String code);
	
}
