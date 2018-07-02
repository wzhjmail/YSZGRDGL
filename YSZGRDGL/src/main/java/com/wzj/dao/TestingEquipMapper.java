package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.pojo.TestingEquip;

public interface TestingEquipMapper {
	int insert(TestingEquip record);
	int updateById(TestingEquip record);
	
	@Select({"select * from ys_testingEquip where id=#{id,jdbcType=INTEGER}"})
	TestingEquip getById(int id);
	
	@Select({"select * from ys_testingEquip where companyId=#{companyId,jdbcType=INTEGER}"})
	List<TestingEquip> getByCompanyId(int companyId);
	
	@Delete({"delete from ys_testingEquip where id=#{id,jdbcType=INTEGER}"})
	int deleteById(int id);
	
	@Select({"select * from ys_testingEquip where companyName=#{companyName,jdbcType=VARCHAR}"})
	List<TestingEquip> getByCompanyName(String companyName);
	
	@Update({"update ys_testingEquip set companyId=#{companyId,jdbcType=INTEGER}",
		"where companyName=#{companyName,jdbcType=VARCHAR}"})
	int updateByCompanyName(@Param("companyName")String companyName, @Param("companyId")int companyId);
	
	@Delete({"delete from ys_testingEquip where companyName=#{companyName,jdbcType=VARCHAR}"})
	void deleteByComName(@Param("companyName")String companyName);
	
	@Update({"update ys_testingEquip set companyName=#{companyName,jdbcType=INTEGER}",
	"where companyName=#{oldName,jdbcType=VARCHAR}"})
	void updateCompanyName(@Param("oldName")String oldName, @Param("companyName")String companyName);
}