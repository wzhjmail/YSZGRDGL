package com.wzj.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.DTO.AppDTO;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.UploadFile;

public interface ApplicationFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplicationForm record);

    int updateByPrimaryKey(ApplicationForm record);

    @Select({"select * from ys_title where ID=#{id,jdbcType=INTEGER}"})
	ApplicationForm getAppFormById(int id);

    @Select({"select * from ys_title where BusinessNo=#{bussinessNo,jdbcType=VARCHAR}"})
	ApplicationForm getAppFormByBussinessNo(String bussinessNo);

	List<ApplicationForm> findByIds(@Param("list")List<String> businessIds,@Param("branchId")String branchId);
	
	List<ApplicationForm> findByIds2(@Param("list")List<String> businessIds);

	@Select({"select * from ys_title where status=#{status,jdbcType=INTEGER}",
		"and branchId=#{branchId,jdbcType=VARCHAR} and (zhuxiao!='已注销' or zhuxiao is null)"})
	List<ApplicationForm> getAppByStatus(@Param("status")int status,@Param("branchId")String branchId);

	@Update({"update ys_title set status = #{status,jdbcType=INTEGER}",
			" where id = #{id,jdbcType=INTEGER}"})
	int updateStatus(@Param("status")int status,@Param("id")int id);

	@Select({"select * from ys_upload where Code=#{code,jdbcType=VARCHAR}"})
	UploadFile getUploadFileByCode(String code);

	@Update({"update ys_title set certappdate=#{date1,jdbcType=DATE},",
	 		  " certtodate=#{date2,jdbcType=DATE}",
	 		  " where id=#{id,jdbcType=INTEGER}"})
	int updateDate(@Param("id")int id,@Param("date1")Date date1,@Param("date2")Date date2);

	List<ApplicationForm> getOngoing(AppDTO appdto);
	
	List<ApplicationForm> getOngoingAll(AppDTO appdto);
	
	@Select({"select * from ys_title"})
	List<ApplicationForm> getAll();
	 
	@Select({"select * from ys_title where branchId=#{branchId,jdbcType=INTEGER}"})
	List<ApplicationForm> getByBranchId(String branchId);
	
	@Update({"update ys_title set zhuxiao='已注销',zhuxiaodate=now()",
		"where ID=#{id,jdbcType=INTEGER}"})
	int cancel(int id);
	
	@Update({"update ys_title set zhuxiao='',status='1' ",
	"where ID=#{id,jdbcType=INTEGER}"})
	int activate(int id);

	@Select({"select * from ys_title order by ID desc LIMIT 1"})
	ApplicationForm getLastApp();
	
	@Select({"select count(TitleNo) from ys_title where branchId=#{branchId,jdbcType=VARCHAR}",
			"and TitleNo like #{type,jdbcType=VARCHAR}",
			"and CreateDate >= #{stime,jdbcType=TIMESTAMP} and CreateDate<#{etime,jdbcType=TIMESTAMP}"})
	int getCount(@Param("branchId")String branchId,@Param("type")String type,@Param("stime")Timestamp stime,@Param("etime")Timestamp etime);

	 @Select({"select * from ys_title where BusinessNo=#{businessno,jdbcType=VARCHAR} ORDER BY ID DESC LIMIT 1"})
	ApplicationForm getLastAppByBussinessNo(String businessno);

	 @Select({"select * from ys_title order by QualityNo desc LIMIT 1"})
	ApplicationForm getMaxQualityNo();

	 @Select({"select * from ys_title where companyId=",
		 "#{id,jdbcType=INTEGER} order by id desc limit 1"})
	ApplicationForm getLastAppBycid(int id);

	 @Select({"select * from ys_title where companyId=",
	 "#{id,jdbcType=INTEGER}"})
	List<ApplicationForm> getAppBycid(int id);

	 @Select({"select count(*) from ys_title where branchId=#{divisioncode,jdbcType=VARCHAR}",
			"and TitleNo like #{string,jdbcType=VARCHAR}"})
	String getCountByType(@Param("divisioncode")String divisioncode, @Param("string")String string);

	 @Select({"select * from ys_title where TitleNo=#{titleNo,jdbcType=VARCHAR} ORDER BY ID DESC LIMIT 1"})
	ApplicationForm getLastAppFormBytitleNo(@Param("titleNo")String titleNo);
	
	@Select({"select count(*) from ys_company where EnterpriseName=#{enterpriseName,jdbcType=VARCHAR} and status>=17"})
	int getCountByComName(@Param("enterpriseName")String enterpriseName);
	 
	@Select({"select count(*) from ys_title where TitleNo like '%XB%' and status<17 and ",
		"EnterpriseName=#{enterpriseName,jdbcType=VARCHAR}"})
	int getCountByComName2(@Param("enterpriseName")String enterpriseName);
	 
	 @Select({"select * from ys_upload where Code=#{titleNo,jdbcType=VARCHAR} and describeId=#{describe,jdbcType=VARCHAR}"})
	UploadFile getUploadFile(@Param("titleNo")String titleNo, @Param("describe")String describe);
	 
	 @Select({"select * from ys_title where EnterpriseName=#{enterpriseName,jdbcType=VARCHAR}"
		 ," and titleNo like #{type,jdbcType=VARCHAR} order by id desc limit 1"})
	ApplicationForm getAppFormByName(@Param("enterpriseName")String name,@Param("type")String type);
	 
	 @Select({"select * from ys_title where EnterpriseName=#{name,jdbcType=VARCHAR} order by id desc limit 1"})
	 @ResultMap("BaseResultMap")
	 ApplicationForm getByName(@Param("name")String name);

	List<ApplicationForm> getOngoingByStatus(AppDTO appdto);

	List<ApplicationForm> getOngoingByBranch(AppDTO appdto);

	int getXBCountByTime(@Param("result")String result);

	int getFRCountByTime(@Param("fRresult")String fRresult);

	int getBGCountByTime(@Param("bGresult")String bGresult);

	Integer getxbCountByBranchs(@Param("xbResult")String xbResult);

	Integer getfrCountByBranchs(@Param("frResult")String frResult);

	Integer getbgCountByBranchs(@Param("bgResult")String bgResult);

	int getXBCountByBranchs(@Param("result")String result);

	int getFRCountByBranchs(@Param("fRresult")String fRresult);

	int getBGCountByBranchs(@Param("bGresult")String bGresult);

	@Select({"select count(*) from ys_title where branchId=#{branchId,jdbcType=VARCHAR}",
	"and TitleNo like #{type,jdbcType=VARCHAR}"})
	Integer getYWCountByType(@Param("branchId")String branchId, @Param("type")String type);

	int getXBCountByType(@Param("result")String result);

	int getFRCountByType(@Param("fRresult")String fRresult);

	int getBGCountByType(@Param("bGresult")String bGresult);

	//根据公司Id查询复认记录
	@Select({"select * from ys_title where companyId=#{companyId,jdbcType=VARCHAR} and",
		" titleNo like 'FR%' and CertToDate is not null order by CertToDate desc"})
	List<ApplicationForm> getFRByCId(String companyId);

}