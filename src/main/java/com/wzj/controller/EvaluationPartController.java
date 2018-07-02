package com.wzj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.pojo.EvaluationPart;
import com.wzj.pojo.Log;
import com.wzj.service.impl.EvaluationPartService;
import com.wzj.util.AddLog;

@Controller
@RequestMapping("evaluationPart")
public class EvaluationPartController {
	@Autowired
	private EvaluationPartService evaluationPartService;
	
	@RequestMapping("getByEId")
	@ResponseBody
	public List<EvaluationPart> getByEId(int eid){
		return evaluationPartService.getByEId(eid);
	}
	
	@RequestMapping("getById")
	@ResponseBody
	public EvaluationPart getById(int id){
		return evaluationPartService.getById(id);
	}
	
	@RequestMapping("update")
	@ResponseBody
	public int update(EvaluationPart record){
		AddLog.addLog(Log.QUERY,"修改评审表配置的选项");
		return evaluationPartService.update(record);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public int delete(int id){
		AddLog.addLog(Log.QUERY,"删除评审表配置信息");
		return evaluationPartService.delete(id);
	}
	
	@RequestMapping("add")
	@ResponseBody
	public void add(EvaluationPart part){
		evaluationPartService.insert(part);
	}
	
}
