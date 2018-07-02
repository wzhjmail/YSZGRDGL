package com.wzj.service.application;

import java.util.List;

import com.wzj.pojo.ExpressInfo;

public interface IExpressInfoService {

	public int insertExpress(ExpressInfo express);
	public List<ExpressInfo> expressList();
	//根据公司id获取快递信息
	public ExpressInfo getExpressByCompanyId(String string);
	//根据公司id删除快递信息
	public void delExpress(String companyId);
}
