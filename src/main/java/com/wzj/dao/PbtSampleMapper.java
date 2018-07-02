package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.pojo.PbtSample;

public interface PbtSampleMapper {
	
	public int delete(String uf_sample_code);

	public int insert(PbtSample sample);

	public int update(PbtSample sample);
	
	@Select({"select * from PBT_SAMPLE where F_SAMPLE_KEY = #{f_sample_key,jdbcType=VARCHAR}"})
	PbtSample findBySampleKey(String f_sample_key);
	
	@Select({"select * from PBT_SAMPLE where UF_SAMPLE_CODE = #{code,jdbcType=VARCHAR}"})
	PbtSample getSampleByCode(@Param("code")String code);
	
	@Select({"select * from PBT_SAMPLE where UF_SAMPLE_CODE = #{code,jdbcType=VARCHAR} and UF_0047225 = #{comName,jdbcType=VARCHAR}"})
	PbtSample getSampleByCodeAndName(@Param("code")String code,@Param("comName")String comName);
	
	@Select({"select * from PBT_SAMPLE where f_add_control = #{status,jdbcType=VARCHAR} and UF_0047225 = #{comName,jdbcType=VARCHAR}"})
	PbtSample getSampleByStatus(@Param("status")String status,@Param("comName")String comName);
	
	@Select({"select * from PBT_SAMPLE where UF_0047225 = #{comName,jdbcType=VARCHAR}"})
	List<PbtSample> getSampleByCom(@Param("comName")String comName);
	
	 @Update({"update pbt_sample set UF_ATTACH=#{newpath,jdbcType=VARCHAR} ",
		"where UF_SAMPLE_CODE=#{code,jdbcType=VARCHAR}"})
	 void setUf_attach(@Param("code")String code, @Param("newpath")String newpath);

	 @Update({"update pbt_sample set UF_REPORT = #{newpath,jdbcType=VARCHAR}",
		 "where UF_SAMPLE_CODE=#{code,jdbcType=VARCHAR} and UF_0047225 = #{comName,jdbcType=VARCHAR}"})
	public void uploadSampleReport(@Param("comName")String comName, @Param("code")String code, @Param("newpath")String newpath);

	 @Update({"update pbt_sample set UF_RESULT = #{result,jdbcType=TINYINT}",
	 "where UF_SAMPLE_CODE=#{code,jdbcType=VARCHAR} and UF_0047225 = #{comName,jdbcType=VARCHAR}"})
	 public void setSampleResult( @Param("code")String code, @Param("comName")String comName,@Param("result")boolean result);
	 
	 @Select({"select count(1) from ys_upload where UpDescribe = #{des,jdbcType=VARCHAR} and describeId = #{sampleId,jdbcType=VARCHAR}"})
     public String getNum(@Param("sampleId")String sampleId,@Param("des")String des);
	 
	 @Select({"select count(*) from pbt_sample where UF_SAMPLE_CODE = #{code,jdbcType=VARCHAR}"})
	 public int getSampleCount(@Param("code")String code);

	@Select({"select max(UF_SAMPLE_CODE) from pbt_sample where UF_SAMPLE_CODE like #{titleNo,jdbcType=VARCHAR}"})
	public String getMaxId(String titleNo);

	@Update({"update pbt_sample set f_add_control=#{status,jdbcType=VARCHAR} ",
		"where uf_sample_code=#{uf_sample_code,jdbcType=VARCHAR}"})
	public void setStatus(@Param("uf_sample_code")String uf_sample_code, @Param("status")String status);
}