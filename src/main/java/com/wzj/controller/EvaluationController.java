package com.wzj.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.DTO.EvaluationDTO;
import com.wzj.pojo.Evaluation;
import com.wzj.pojo.EvaluationPart;
import com.wzj.pojo.Log;
import com.wzj.service.impl.EvaluationPartService;
import com.wzj.service.impl.EvaluationService;
import com.wzj.util.AddLog;
import com.mysql.fabric.Server;

@Controller
@RequestMapping("evaluation")
public class EvaluationController {
	@Autowired
	private EvaluationService evaluationService;
	@Autowired
	private EvaluationPartService evaluationPartService;
	@RequestMapping("toReviewInfo")
	public String toreviewInfo(){
		AddLog.addLog(Log.QUERY,"查看评审表配置信息");
		return "common/reviewInfo";
	}
	@RequestMapping("getAll")
	@ResponseBody
	public List<EvaluationDTO> getAll(){
		List<EvaluationDTO> eva=evaluationService.getAllEva();
		
		for (EvaluationDTO evaluation : eva) {
			List<EvaluationPart> byEId =evaluationPartService.getByEId(evaluation.getId());
			String results="";
			String resultnote="";
			for (EvaluationPart evaluationPart : byEId) {
				results+=evaluationPart.getResult()+",";
				resultnote+=evaluationPart.getResultnote()+"/";
			}
			evaluation.setResult(results);
			evaluation.setResultnote(resultnote);
		}
		return eva;
	}
	
	@RequestMapping("getAllEva")
	@ResponseBody
	public List<Evaluation> getAllEva(){
		return evaluationService.getAll();
	}
	@RequestMapping("getById")
	@ResponseBody
	public Object getById(int id){
		AddLog.addLog(Log.QUERY,"查询编号为'"+id+"'的评审表配置信息");
		return evaluationService.select(id);
	}
	
	@RequestMapping("update")
	@ResponseBody
	public int update(Evaluation eval){
		AddLog.addLog(Log.MODIFY,"修改编号为'"+eval.getId()+"'的评审表配置信息");
		return evaluationService.update(eval);
	}
	
	@RequestMapping("getUsing")
	@ResponseBody
	public Object getUsing(){
		return evaluationService.getUsing();
	}
	@RequestMapping("countResult")
	@ResponseBody
	public int countResult(int evalId){
		return evaluationService.countResult(evalId);
	}
	@RequestMapping("addEvaluation")
	public String addEvaluation(Evaluation evaluation){
		int num=evaluationService.getLastId();
		evaluation.setNum(num+1);
		AddLog.addLog(Log.MODIFY,"添加要求为：‘"+evaluation.getMethod()+"’的评审表配置信息");
		evaluationService.addEvaluation(evaluation);
		return "common/reviewInfo";
	}
	
	@RequestMapping("deleteById")
	@ResponseBody
	public void deleteById(int id){
		Evaluation evaluation=evaluationService.select(id);
		AddLog.addLog(Log.DELETE,"删除要求为：‘"+evaluation.getMethod()+"’的评审表配置信息");
		evaluationService.delete(id);
	}
	
	@RequestMapping("toSetABC")
	public String toSetABC(int eid,ModelMap map){
		Evaluation evaluation=evaluationService.select(eid);
		map.addAttribute("eid", eid);
		map.addAttribute("describe", evaluation.getRequired());
		return "common/setABC";
	}

	//获取评审结论
	@RequestMapping("getResultNote")
	@ResponseBody
	public Object getResultNote(int evalId,String result){
		Evaluation evaluation=evaluationService.getByNum(evalId);
		EvaluationPart ep=new EvaluationPart(result,evaluation.getId());
		EvaluationPart evaPart=evaluationPartService.getResultNote(ep);
		return evaPart;
	}
}
