package com.wzj.service.application.impl;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.ExpressInfoMapper;
import com.wzj.pojo.ExpressInfo;
import com.wzj.service.application.IExpressInfoService;
@Service("expressInfoService")
public class ExpressInfoService implements IExpressInfoService{

	@Autowired
	private ExpressInfoMapper expressInfoMapper;

	@Override
	public int insertExpress(ExpressInfo express) {
		if(express.getName()==null||"".equals(express.getName())){
			express.setName("EMS");
		}
		return expressInfoMapper.insertExpress(express);
	}

	@Override
	public List<ExpressInfo> expressList() {
		return expressInfoMapper.getExpressList();
	}

	public ExpressInfo getExpressByCompanyId(String companyId) {
		return expressInfoMapper.getExpressByCompanyId(companyId);
	}

	public void delExpress(String companyId) {
		expressInfoMapper.delExpress(companyId);
	}
}
