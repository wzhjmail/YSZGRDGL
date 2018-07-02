package com.wzj.controller.recognition;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.Log;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.util.AddLog;
import com.wzj.util.WorkflowUtil;
@Controller("recognition/enroll")
@RequestMapping("recognition/enroll")
public class EnrollController {
	@Autowired
	private RecognitionController recognitionController;
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private ApplicationFormService applicationFormService;
	@RequestMapping("toTasks")
	public String toGroupTask(){
		AddLog.addLog(Log.QUERY,"�鿴���д�¼ȡ��Ʒ�ĸ���ҵ��������Ϣ");
		return "recognition/enroll/tasks";
	}
	
	@RequestMapping("input")
	@ResponseBody
	public void toPersonalTask(String taskId,HttpSession session){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"��"+af.getEnterprisename()+"�ĸ���ҵ�������������");
		recognitionController.climeAndcompleteTask(taskId,"��","������������Ա","true",-1,session);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public void delete(){
		workflowUtil.deleteProcessDefinitionByDeploymentId("5019");
	}
}
