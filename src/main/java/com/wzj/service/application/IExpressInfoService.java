package com.wzj.service.application;

import java.util.List;

import com.wzj.pojo.ExpressInfo;

public interface IExpressInfoService {

	public int insertExpress(ExpressInfo express);
	public List<ExpressInfo> expressList();
	//���ݹ�˾id��ȡ�����Ϣ
	public ExpressInfo getExpressByCompanyId(String string);
	//���ݹ�˾idɾ�������Ϣ
	public void delExpress(String companyId);
}
