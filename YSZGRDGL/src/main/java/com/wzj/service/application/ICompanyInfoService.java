package com.wzj.service.application;

import java.util.List;

import com.wzj.pojo.CompanyInfo;

public interface ICompanyInfoService {

	//根据ys_title中的id将公司id复制到ys_company表中
	public int insertById(int titleId);
	public int insert(CompanyInfo com);
	//根据状态和分支机构查询
	public List<CompanyInfo> getComByStatus(int status, String branchId);
	
	//根据当前时间判断证书是否过期，并修改过期的状态值
	public int certificateExpired();
}
