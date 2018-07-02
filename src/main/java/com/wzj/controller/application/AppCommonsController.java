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
	
	//ҵ�����̣��������û�н�ɫ��
	@RequestMapping("completeTask")
	@ResponseBody
	public void completeTask(String taskId,String comment,String result,int status){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		AddLog.addLog(Log.ADD,"���'"+af.getEnterprisename()+"'�ĸ���ҵ��");
		//�������
		if(StringUtils.isNotBlank(comment)){//�����Ϊ�޵Ļ����������
			workflowUtil.addComment(taskId,comment);
		}
		//�޸�ҵ��״̬
		if(status>0){
			applicationFormService.updateStatus(status,id);
		}
		//�������
		Map<String,Object> map = new HashMap<>();
		map.put("result", result); 
		WorkflowBean workflowBean = new WorkflowBean();
		workflowBean.setTaskId(taskId);
		workflowUtil.completeTask(workflowBean, map);
	}
	
	//ҵ�����̣��������û�н�ɫ��
	@RequestMapping("completeTask2")
	@ResponseBody
	public void completeTask2(String taskId,String comentType,String comment,String result,int status){
		//�������
		if(StringUtils.isNotBlank(comment)){//�����Ϊ�޵Ļ����������
			workflowUtil.addComment(taskId,"back",comment);
		}
		//�޸�ҵ��״̬
		if(status>0){
			int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			applicationFormService.updateStatus(status,id);
		}
		//�������
		Map<String,Object> map = new HashMap<>();
		map.put("result", result); 
		WorkflowBean workflowBean = new WorkflowBean();
		workflowBean.setTaskId(taskId);
		workflowUtil.completeTask(workflowBean, map);
	}
		
	//���ݽڵ����ƻ�ȡ��������������
	@RequestMapping("getPointTask")
	@ResponseBody
	public Object getPointTask(String pointId,HttpSession session){
		//��ȡ�û�����ʵ����
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		
		//���ݽڵ�����ƻ���û�����ȡ����
		List<Task> tasks = workflowUtil.getTaskByIds("application", pointId);
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		for(ApplicationForm af:afList){
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afList;
	}
	
	//���ݽڵ����ƻ�ȡ��������
	@RequestMapping("getAllTask")
	@ResponseBody
	public Object getAllTask(String pointId){
		//���ݽڵ�����ƻ���û�����ȡ����
		List<Task> tasks = workflowUtil.getTaskByIds("application", pointId);
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet());
		for(ApplicationForm af:afList){
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afList;
	}

	//����act_hi_comment�е�type��ѯ�������б��˻صĴ���
	
	public int countBack(String taskId){
		Task task=workflowUtil.getTaskById(taskId);
		String insId=task.getProcessInstanceId();
		return operationService.count(insId);
	}
}
