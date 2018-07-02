package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wzj.DTO.DivRegionDTO;

public interface DivisionRegionMapper {

	DivRegionDTO getDivRegion(String regionCode);

	List<DivRegionDTO> findAll();

	DivRegionDTO getDivRegionById(int id);

	void updateDivRegById(DivRegionDTO divregDTO);

	List<DivRegionDTO> findByDivcode(String code);

	@Delete({"delete from ys_division_region ",
	"where DIVISION_CODE = #{branchcode,jdbcType=VARCHAR}"})
	void deletePre(@Param("branchcode")String branchcode);

	@Insert({"insert into ys_division_region (REGION_CODE,DIVISION_CODE)",
  		"values (#{regioncode,jdbcType=VARCHAR},#{code,jdbcType=VARCHAR})"})
	void insertDivReg(@Param("regioncode")String regioncode, @Param("code")String code);

	List<DivRegionDTO> getDivRegByCode(String code);

}
