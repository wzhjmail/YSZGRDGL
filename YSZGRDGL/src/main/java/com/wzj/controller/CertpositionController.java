package com.wzj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.pojo.Certposition;
import com.wzj.pojo.Log;
import com.wzj.service.impl.CertpositionService;
import com.wzj.util.AddLog;

@Controller
@RequestMapping("certposition")
public class CertpositionController {
	@Autowired
	private CertpositionService certService;
	
	@RequestMapping("getAll")
	@ResponseBody
	public Object getAll(){
		return certService.getAll();
	}
	
	@RequestMapping("select")
	@ResponseBody
	public Object select(int id){
		return certService.getById(id);
	}
	
	@RequestMapping("update")
	public String update(Certposition cert){
		AddLog.addLog(Log.MODIFY,"修改'"+cert.getName()+"'的打印设置信息");
		certService.update(cert);
		return "common/printSetting";
	}
	
	@RequestMapping("insert")
	public String insert(Certposition cert){
		AddLog.addLog(Log.ADD,"添加'"+cert.getName()+"'打印设置信息");
		certService.insert(cert);
		return "common/printSetting";
	}
	
	@RequestMapping("delete")
	public String delete(int id){
		Certposition cert=certService.getById(id);
		AddLog.addLog(Log.DELETE,"删除'"+cert.getName()+"'的打印设置信息");
		certService.delete(id);
		return "common/printSetting";
	}
	
	@RequestMapping("changeUsed")
	@ResponseBody
	public int chagneUsed(int id){
		Certposition cert=certService.getById(id);
		AddLog.addLog(Log.MODIFY,"切换'"+cert.getName()+"'的打印设置信息");
		return certService.setUsed(id);
	}
	
	@RequestMapping("getUsing")
	@ResponseBody
	public Certposition getUsing(){
		return certService.getUsing();
	}
	
	@RequestMapping("toSetting")
	public String toSetting(){
		AddLog.addLog(Log.QUERY,"查询所有打印设置信息");
		return "common/printSetting";
	} 
	//获取某个打印设置的个数
	@RequestMapping("getCount")
	@ResponseBody
	public int getCount(String name){
		return certService.getCount(name);
	}
}
