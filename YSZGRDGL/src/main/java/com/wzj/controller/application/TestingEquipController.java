package com.wzj.controller.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.pojo.Log;
import com.wzj.pojo.TestingEquip;
import com.wzj.service.TestingEquipService;
import com.wzj.util.AddLog;

@Controller("application/testingEquip")
@RequestMapping("application/testingEquip")
public class TestingEquipController {

	@Autowired
	private TestingEquipService testingEquipService;
	
	@RequestMapping("insert")
	@ResponseBody
	public int add(TestingEquip record){
		AddLog.addLog(Log.ADD,"向'"+record.getCompanyName()+"'添加条码检测设备'"+record.getName()+"'的信息");
//		testingEquipService.deleteByComName(record.getCompanyName());
		testingEquipService.insert(record);
		return record.getId();
	}
	
	@RequestMapping("getById")
	@ResponseBody
	public TestingEquip getById(int id){
		return testingEquipService.getById(id);
	}
	
	@RequestMapping("updateById")
	@ResponseBody
	public int updateById(TestingEquip record){
		return testingEquipService.updateById(record);
	}
	
	@RequestMapping("deleteById")
	@ResponseBody
	public int deleteById(int id){
		return testingEquipService.deleteById(id);
	}
	
	@RequestMapping("getByCompanyName")
	@ResponseBody
	public Object getByCompanyName(String companyName){
		List<TestingEquip> lists=testingEquipService.getByCompanyName(companyName);
		return lists;
	}
	
	@RequestMapping("getByCompanyId")
	@ResponseBody
	public Object getByCompanyId(int companyId){
		List<TestingEquip> lists=testingEquipService.getByCompanyId(companyId);
		return lists;
	}
}
