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
		AddLog.addLog(Log.MODIFY,"�޸�'"+cert.getName()+"'�Ĵ�ӡ������Ϣ");
		certService.update(cert);
		return "common/printSetting";
	}
	
	@RequestMapping("insert")
	public String insert(Certposition cert){
		AddLog.addLog(Log.ADD,"���'"+cert.getName()+"'��ӡ������Ϣ");
		certService.insert(cert);
		return "common/printSetting";
	}
	
	@RequestMapping("delete")
	public String delete(int id){
		Certposition cert=certService.getById(id);
		AddLog.addLog(Log.DELETE,"ɾ��'"+cert.getName()+"'�Ĵ�ӡ������Ϣ");
		certService.delete(id);
		return "common/printSetting";
	}
	
	@RequestMapping("changeUsed")
	@ResponseBody
	public int chagneUsed(int id){
		Certposition cert=certService.getById(id);
		AddLog.addLog(Log.MODIFY,"�л�'"+cert.getName()+"'�Ĵ�ӡ������Ϣ");
		return certService.setUsed(id);
	}
	
	@RequestMapping("getUsing")
	@ResponseBody
	public Certposition getUsing(){
		return certService.getUsing();
	}
	
	@RequestMapping("toSetting")
	public String toSetting(){
		AddLog.addLog(Log.QUERY,"��ѯ���д�ӡ������Ϣ");
		return "common/printSetting";
	} 
	//��ȡĳ����ӡ���õĸ���
	@RequestMapping("getCount")
	@ResponseBody
	public int getCount(String name){
		return certService.getCount(name);
	}
}
