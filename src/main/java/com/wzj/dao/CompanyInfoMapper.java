package com.wzj.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.CompanyInfo;

public interface CompanyInfoMapper {
	
	@Insert({"insert into ys_company select * from ys_title ",
		"where id=#{titleId,jdbcType=INTEGER}"})
	public int insertById(int titleId);
	
	public int insert(CompanyInfo com);
	
	@Delete({"delete from ys_company where id=#{id,jdbcType=INTEGER}"})
	public int delete(int id);

	@Update({"update ys_company set status=#{status,jdbcType=INTEGER} ",
		"where id=#{id,jdbcType=INTEGER}"})
	public int setStatus(@Param("id")int id,@Param("status")int status);

	@Select({"select * from ys_company where status=#{status,jdbcType=INTEGER}",
		"and branchId=#{branchId,jdbcType=VARCHAR} and (zhuxiao!='已注销' or zhuxiao is null)"})
	public List<CompanyInfo> getComByStatus(@Param("status")int status, @Param("branchId")String branchId);

	public int update(CompanyInfo com);
	
	@Select({"select ID,branchId,TitleNo,branchName,EnterpriseName,CertAppDate,CertToDate,CreateDate,serial,status from ys_company where zhuxiao!='发证后未结束流程' and zhuxiao!='已注销' or zhuxiao is null"})
	public List<CompanyInfo> getAll();
	
	@Update({"update ys_company set zhuxiao='已注销',zhuxiaodate=NOW()",
		" where id=#{id,jdbcType=INTEGER}"})
	public void setZhuxiao(int id);
	
	@Update({"update ys_company set zhuxiao=#{status,jdbcType=VARCHAR},",
		"zhuxiaodate=NOW() where id=#{id,jdbcType=INTEGER}"})
	public void changeZhuxiao(@Param("id")int id,@Param("status")String status);

	@Select({"select ID,branchId,TitleNo,branchName,EnterpriseName,CertAppDate,CertToDate,CreateDate,serial,status from ys_company where branchId=#{branchId,jdbcType=VARCHAR}",
		"and (zhuxiao!='发证后未结束流程' and zhuxiao!='已注销' or zhuxiao is null)"})
	public List<CompanyInfo> getByBranchId(String branchId);

	@Update({"update ys_company set zhuxiao='已注销',zhuxiaodate=now()",
		"where ID=#{id,jdbcType=INTEGER}"})
	public int cancel(int id);

	@Update({"update ys_company set zhuxiao='',status='1' ",
		"where ID=#{id,jdbcType=INTEGER}"})
	int activate(int id);
	
	@Select({"select * from ys_company where ID=#{id,jdbcType=INTEGER}"})
	public CompanyInfo getComById(int id);

	@Select({"select * from ys_company where certtodate between ",
			"#{0,jdbcType=TIMESTAMP} and #{1,jdbcType=TIMESTAMP}"})
	public List<CompanyInfo> getInTime(Timestamp sTamp, Timestamp eTamp);

	@Select({"select * from ys_company where BusinessNo=#{xbId,jdbcType=VARCHAR}"})
	public CompanyInfo getComByBussino(@Param("xbId")String xbId);

	@Select({"select * from ys_company where zhuxiao='已注销'"})
	public List<CompanyInfo> getAllCancel();
	
	@Select({"select * from ys_company where zhuxiao='已注销' "
		,"and branchId=#{branchId,jdbcType=VARCHAR}"})
	public List<CompanyInfo> getCancelByBranchId(String branchId);

	@Update({"update ys_company set zhuxiao='',status='37' ",
	"where ID=#{id,jdbcType=INTEGER}"})
	public int reActivate(int id);

	public List<CompanyInfo> getAllBySql(@Param(value="result")String result);

	@Update({"update ys_company set status='36' ",
		"where ID=#{id,jdbcType=INTEGER}"})
	public int reChange(int id);

	@Select({"select * from ys_company where EnterpriseName=#{companyName,jdbcType=VARCHAR}"})
	public CompanyInfo getComByName(String companyName);

	@Select({"select * from ys_company where CertToDate between ",
	"#{defaultStartDate,jdbcType=VARCHAR} and #{defaultEndDate,jdbcType=VARCHAR} and (zhuxiao!='已注销' or zhuxiao is null) and concat_ws(EnterpriseName,englishname,Address,EnterpriseKind,Linkman,MainBusiness,branchName) like #{sSearch,jdbcType=VARCHAR} order by createDate desc"})
	public List<CompanyInfo> getRecentCompany(@Param(value="defaultStartDate")String defaultStartDate, @Param(value="defaultEndDate")String defaultEndDate, @Param(value="sSearch")String sSearch);

	@Select({"select * from ys_company where Address like #{address,jdbcType=VARCHAR} and (zhuxiao!='已注销' or zhuxiao is null)"})
	public List<CompanyInfo> getByAddress(@Param("address")String address);

	@Select({"select * from ys_company where Address like #{address,jdbcType=VARCHAR} and branchId=#{branchId,jdbcType=VARCHAR} and (zhuxiao!='已注销' or zhuxiao is null)"})
	public List<CompanyInfo> getByaddress2(@Param("address")String address, @Param("branchId")String branchId);

	@Select({"select * from ys_company where CertToDate between ",
	"#{defaultStartDate,jdbcType=VARCHAR} and #{defaultEndDate,jdbcType=VARCHAR} and branchId=#{branchId,jdbcType=VARCHAR} and (zhuxiao!='已注销' or zhuxiao is null) and concat_ws(EnterpriseName,englishname,Address,EnterpriseKind,Linkman,MainBusiness,branchName) like #{sSearch,jdbcType=VARCHAR} order by createDate desc"})
	public List<CompanyInfo> getRecentCompany2(@Param(value="defaultStartDate")String defaultStartDate, @Param(value="defaultEndDate")String defaultEndDate, @Param("branchId")String branchId, @Param("sSearch")String sSearch);

	@Select({"select * from ys_company where Address like #{address,jdbcType=VARCHAR} and zhuxiao='已注销'"})
	public List<CompanyInfo> getByAddress3(@Param("address")String address);

	@Select({"select * from ys_company where Address like #{address,jdbcType=VARCHAR} and branchId=#{branchId,jdbcType=VARCHAR} and zhuxiao='已注销'"})
	public List<CompanyInfo> getByaddress4(@Param("address")String address, @Param("branchId")String branchId);

	@Select({"select ID,branchId,TitleNo,branchName,EnterpriseName,CertAppDate,CertToDate,CreateDate,serial,status from ys_company where concat_ws(EnterpriseName,englishname,Address,EnterpriseKind,Linkman,MainBusiness,branchName,serial)",
		" like #{sSearch,jdbcType=VARCHAR} and (zhuxiao!='发证后未结束流程' and zhuxiao!='已注销' or zhuxiao is null)"})
	public List<CompanyInfo> getAll2(@Param("sSearch")String sSearch);

	@Select({"select ID,branchId,TitleNo,branchName,EnterpriseName,CertAppDate,CertToDate,CreateDate,serial,status from ys_company where branchId=#{branchId,jdbcType=VARCHAR}",
	"and (zhuxiao!='发证后未结束流程' and zhuxiao!='已注销' or zhuxiao is null) and concat_ws(EnterpriseName,englishname,Address,EnterpriseKind,Linkman,MainBusiness,branchName,serial)",
	" like #{sSearch,jdbcType=VARCHAR}"})
	public List<CompanyInfo> getAllByLike(@Param("branchId")String branchId, @Param("sSearch")String sSearch);

	@Select({"select * from ys_company where zhuxiao='已注销' and concat_ws(EnterpriseName,englishname,Address,EnterpriseKind,Linkman,MainBusiness,branchName,serial) like #{sSearch,jdbcType=VARCHAR}"})
	public List<CompanyInfo> getAllCancel1(@Param("sSearch")String sSearch);

	@Select({"select * from ys_company where zhuxiao='已注销' "
		,"and branchId=#{branchId,jdbcType=VARCHAR} and concat_ws(EnterpriseName,englishname,Address,EnterpriseKind,Linkman,MainBusiness,branchName,serial) like #{sSearch,jdbcType=VARCHAR}"})
	public List<CompanyInfo> getCancelByBranchId1(@Param("branchId")String branchId,@Param("sSearch") String sSearch);

	@Select({"select * from ys_company where CertToDate between ",
	"#{defaultStartDate,jdbcType=VARCHAR} and #{defaultEndDate,jdbcType=VARCHAR} and (zhuxiao!='已注销' or zhuxiao is null) order by createDate desc"})
	public List<CompanyInfo> getRecentCompany3(@Param(value="defaultStartDate")String defaultStartDate, @Param(value="defaultEndDate")String defaultEndDate);

	@Select({"select * from ys_company where CertToDate between ",
	"#{defaultStartDate,jdbcType=VARCHAR} and #{defaultEndDate,jdbcType=VARCHAR} and branchId=#{branchId,jdbcType=VARCHAR} and (zhuxiao!='已注销' or zhuxiao is null) order by createDate desc"})
	public List<CompanyInfo> getRecentCompany4(@Param(value="defaultStartDate")String defaultStartDate, @Param(value="defaultEndDate")String defaultEndDate,@Param("branchId")String branchId);

	@Select({"select * from ys_company where zhuxiao!='已注销' "
		,"and branchId=#{branchId,jdbcType=VARCHAR} and TitleNo like #{search,jdbcType=VARCHAR}"})
	public List<CompanyInfo> getByLike(@Param("branchId")String branchId, @Param("search")String search);

	@Select({"select * from ys_company where branchId=#{branchId,jdbcType=VARCHAR}",
		"and TitleNo like #{type,jdbcType=VARCHAR}",
		"and CreateDate >= #{s,jdbcType=TIMESTAMP} and CreateDate<#{e,jdbcType=TIMESTAMP}"})
	public List<CompanyInfo> getByTwo(@Param("branchId")String branchId, @Param("type")String type, @Param("s")Timestamp s, @Param("e")Timestamp e);

}
