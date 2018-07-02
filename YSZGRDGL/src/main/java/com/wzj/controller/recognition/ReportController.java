package com.wzj.controller.recognition;

import javax.servlet.http.HttpSession;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.Log;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.util.AddLog;
import com.wzj.util.WorkflowUtil;

@Controller("recognition/report")
@RequestMapping("recognition/report")
public class ReportController {

	@Autowired
	private RecognitionController recognitionController;
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private ApplicationFormService applicationFormService;
	@RequestMapping("toBranchTask")
	public String toBranchTask(){
		AddLog.addLog(Log.QUERY,"查询所有待检验的复认业务申请信息");
		return "recognition/issueReport/branchTask";
	}
	
	//分中心出具检验报告的拾取及完成任务
	@RequestMapping("issueReport")
	@ResponseBody
	public void issueReport(String taskId,boolean result,HttpSession session){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"提交'"+af.getEnterprisename()+"'的样品的分中心检测报告意见");
		//完成任务
		recognitionController.climeAndcompleteTask(taskId,"无","分中心复核人员","true",-1,session);
		/*if(result){
			recognitionController.climeAndcompleteTask(taskId,"无","分中心普通人员","true",16,session);
		}else{//不通过
			recognitionController.climeAndcompleteTask(taskId,"无","分中心审核人员","false",17,session);
		}*/
	}
	
	//跳转到复认业务的中心出具检验报告
	@RequestMapping("toCenterTasks")
	public String toCenterTasks(){
		AddLog.addLog(Log.QUERY,"查询所有待检验的复认业务申请信息");
		return "recognition/issueReport/centerTask";
	}
}
