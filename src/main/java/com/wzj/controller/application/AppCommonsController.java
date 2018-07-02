package com.wzj.controller.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.Log;
import com.wzj.pojo.WorkflowBean;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.OperationService;
import com.wzj.util.AddLog;
import com.wzj.util.WorkflowUtil;

@Controller("application/common")
@RequestMapping("application/common")
public class AppCommonsController {
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private OperationService operationService;
	
	//业务流程，完成任务（没有角色）
	@RequestMapping("completeTask")
	@ResponseBody
	public void completeTask(String taskId,String comment,String result,int status){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		AddLog.addLog(Log.ADD,"审查'"+af.getEnterprisename()+"'的复认业务");
		//添加评论
		if(StringUtils.isNotBlank(comment)){//如果不为无的话这添加评论
			workflowUtil.addComment(taskId,comment);
		}
		//修改业务状态
		if(status>0){
			applicationFormService.updateStatus(status,id);
		}
		//完成任务
		Map<String,Object> map = new HashMap<>();
		map.put("result", result); 
		WorkflowBean workflowBean = new WorkflowBean();
		workflowBean.setTaskId(taskId);
		workflowUtil.completeTask(workflowBean, map);
	}
	
	//业务流程，完成任务（没有角色）
	@RequestMapping("completeTask2")
	@ResponseBody
	public void completeTask2(String taskId,String comentType,String comment,String result,int status){
		//添加评论
		if(StringUtils.isNotBlank(comment)){//如果不为无的话这添加评论
			workflowUtil.addComment(taskId,"back",comment);
		}
		//修改业务状态
		if(status>0){
			int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			applicationFormService.updateStatus(status,id);
		}
		//完成任务
		Map<String,Object> map = new HashMap<>();
		map.put("result", result); 
		WorkflowBean workflowBean = new WorkflowBean();
		workflowBean.setTaskId(taskId);
		workflowUtil.completeTask(workflowBean, map);
	}
		
	//根据节点名称获取所属分中心任务
	@RequestMapping("getPointTask")
	@ResponseBody
	public Object getPointTask(String pointId,HttpSession session){
		//获取用户的真实名称
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		
		//根据节点的名称获和用户名称取任务
		List<Task> tasks = workflowUtil.getTaskByIds("application", pointId);
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		for(ApplicationForm af:afList){
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afList;
	}
	
	//根据节点名称获取所有任务
	@RequestMapping("getAllTask")
	@ResponseBody
	public Object getAllTask(String pointId){
		//根据节点的名称获和用户名称取任务
		List<Task> tasks = workflowUtil.getTaskByIds("application", pointId);
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet());
		for(ApplicationForm af:afList){
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afList;
	}

	//根据act_hi_comment中的type查询改流程中被退回的次数
	
	public int countBack(String taskId){
		Task task=workflowUtil.getTaskById(taskId);
		String insId=task.getProcessInstanceId();
		return operationService.count(insId);
	}
}
