package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.ExpressInfo;

public interface ExpressInfoMapper {

	@Insert({"insert into expressinfo (branch,companyId,name,number,contact,phoneNumber,sendDate,conAddress,reciveName,recivePhoneNum,reciveAddress) ",
		"values (#{branch,jdbcType=VARCHAR},#{companyId,jdbcType=VARCHAR},",
		"#{name,jdbcType=VARCHAR},#{number,jdbcType=VARCHAR},",
		"#{contact,jdbcType=VARCHAR},#{phoneNumber,jdbcType=VARCHAR},#{sendDate,jdbcType=DATE},#{conAddress,jdbcType=VARCHAR},#{reciveName,jdbcType=VARCHAR},#{recivePhoneNum,jdbcType=VARCHAR},#{reciveAddress,jdbcType=VARCHAR})"})
	int insertExpress(ExpressInfo express);

	@Select({"select * from expressinfo"})
	List<ExpressInfo> getExpressList();

	@Select({"select * from expressinfo where ",
		"companyId=#{companyId,jdbcType=VARCHAR} order by id desc limit 1"})
	ExpressInfo getExpressByCompanyId(String companyId);

	@Delete({"delete from expressinfo where ",
		"companyId=#{companyId,jdbcType=VARCHAR}"})
	void delExpress(String companyId);

}
