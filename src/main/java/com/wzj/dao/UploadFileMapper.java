package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.UploadFile;

public interface UploadFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UploadFile record);

    UploadFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UploadFile record);

    int updateByPrimaryKeyWithBLOBs(UploadFile record);

    int updateByPrimaryKey(UploadFile record);

    @Select("select UpRUL from ys_upload where Code=#{cNo,jdbcType=VARCHAR}")
	String getPathByCode(String cNo);
    
    @Delete("delete from ys_upload where Code=#{code,jdbcType=VARCHAR}")
	int deleteByCode(String code);
    
    @Select({"select UpRUL from ys_upload where Code=#{titleNo,jdbcType=VARCHAR}",
    	"and describeId=#{type,jdbcType=VARCHAR}"})
	String getPath(@Param("titleNo")String titleNo, @Param("type")String type);

    @Select("select * from ys_upload where Code=#{code,jdbcType=VARCHAR}")
	List<UploadFile> getAllByCode(String code);
    
    @Select("select ID from ys_upload where Code = 'MB0000' and describeId=#{desId,jdbcType=VARCHAR}")
    Integer getIdByDesId(String desId);

    @Delete({"delete from ys_upload where Code=#{titleNo,jdbcType=VARCHAR}",
    	"and describeId=#{type,jdbcType=VARCHAR}"})
	void delete(@Param("titleNo")String titleNo, @Param("type")String type);
    
    @Select("select * from ys_upload where Code=#{titleNo,jdbcType=VARCHAR} and describeId=#{sampleId,jdbcType=VARCHAR}")
    List<UploadFile> getSampleAttach(@Param("titleNo")String titleNo, @Param("sampleId")String sampleId);
    
    @Select({"select * from ys_upload where Code=#{titleNo,jdbcType=VARCHAR} and describeId=#{sampleId,jdbcType=VARCHAR}",
    		" and UpDescribe=#{type,jdbcType=VARCHAR}"})
    List<UploadFile> getSampleAttach2(@Param("titleNo")String titleNo, @Param("sampleId")String sampleId, @Param("type")String type);

    @Select({"select UpRUL from ys_upload where Code=#{titleNo,jdbcType=VARCHAR}",
	"and UpDescribe=#{describe,jdbcType=VARCHAR} and describeId=#{type,jdbcType=VARCHAR}"})
	String getPath2(@Param("titleNo")String titleNo, @Param("describe")String describe, @Param("type")String type);

    @Delete({"delete from ys_upload where Code=#{titleNo,jdbcType=VARCHAR}",
		"and UpDescribe=#{describe,jdbcType=VARCHAR} and describeId=#{type,jdbcType=VARCHAR}"})
	void delete2(@Param("titleNo")String titleNo, @Param("describe")String describe, @Param("type")String type);

    @Select({"select * from ys_upload where Code=#{titlenum,jdbcType=VARCHAR}",
   	"and UpDescribe=#{describe,jdbcType=VARCHAR} and describeId=#{code,jdbcType=VARCHAR}"})
	List<UploadFile> getSample(@Param("titlenum")String titlenum,@Param("describe") String describe,@Param("code")String code);

    @Delete({"delete from ys_upload where describeId=#{code,jdbcType=VARCHAR}"})
	void deleteByDescribeId(@Param("code")String code);

    @Select({"select * from ys_upload where Code=#{titleNo,jdbcType=VARCHAR}",
   	"and describeId in(1,2,3,4,5,6,7)"})
	List<UploadFile> getByTitleNo(@Param("titleNo")String titleNo);

    @Select({"select * from ys_upload where Code=#{titleNo,jdbcType=VARCHAR}",
   	"and UpDescribe=#{desribe,jdbcType=VARCHAR} and describeId=#{describe1,jdbcType=VARCHAR}"})
	List<UploadFile> getBydesAndTitleNo(@Param("titleNo")String titleNo, @Param("desribe")String desribe, @Param("describe1")String describe1);

    @Select({"select * from ys_upload where Code=#{titleNo,jdbcType=VARCHAR}"})
	List<UploadFile> getByCode(@Param("titleNo")String titleNO);

    @Select({"select * from ys_upload where ID=#{id,jdbcType=INTEGER}"})
	UploadFile getById(int id);

    @Select({"select * from ys_upload where Code=#{titleNo,jdbcType=VARCHAR} and UpDescribe=#{UpDescribe,jdbcType=VARCHAR} ",
    	"and instr(#{simpleIds,jdbcType=VARCHAR},describeId)"})
	List<UploadFile> checkUploadedReport(@Param("titleNo")String titleNo,@Param("UpDescribe")String UpDescribe,@Param("simpleIds")String simpleIds);

    @Select({"select * from ys_upload where Code=#{titleNo,jdbcType=VARCHAR} and instr(#{type,jdbcType=VARCHAR},describeId)"})
	List<UploadFile> getByType(@Param("titleNo")String titleNO, @Param("type")String type);

}