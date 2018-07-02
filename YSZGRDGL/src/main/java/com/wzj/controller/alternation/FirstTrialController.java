package com.wzj.controller.alternation;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.FormChange;
import com.wzj.pojo.Log;
import com.wzj.service.alternation.impl.FormChangeService;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.util.AddLog;
import com.wzj.util.WorkflowUtil;

@Controller("alternation/firstTrial")
@RequestMapping("alternation/firstTrial")
public class FirstTrialController {
	@Autowired
	private AlternationController alternationController;
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private FormChangeService formChangeService;
	
	@RequestMapping("toTasks")
	public String toTasks(){
		AddLog.addLog(Log.QUERY,"查询所有待审查的变更业务申请信息");
		return "alternation/firstTrial/tasks";
	}
	
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		AddLog.addLog(Log.QUERY,"查询所有退回的变更业务申请信息");
		return "alternation/firstTrial/returnTask";
	}
	
	//完成任务
	@RequestMapping("trial")
	public String trial(String taskId,String comment,boolean result,HttpSession session){
		int id=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//ApplicationForm app=applicationFormService.getAppFormById(id);
		FormChange fc=formChangeService.getChangeFormById(id);
		if(result){
			alternationController.climeAndcompleteTask(taskId,comment,"分中心复核人员","true",2,session);
			AddLog.addLog(Log.ADD,"分中心审查'"+fc.getCompanynameOld()+"'的变更申请通过");
		}else{
			alternationController.climeAndcompleteTask(taskId,comment,"分中心普通人员","false",3,session);
			AddLog.addLog(Log.ADD,"分中心审查'"+fc.getCompanynameOld()+"'的变更申请驳回");
		}
		
		return "alternation/firstTrial/tasks";
	}
}
