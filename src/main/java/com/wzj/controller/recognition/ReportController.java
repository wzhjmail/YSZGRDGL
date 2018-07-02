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
		AddLog.addLog(Log.QUERY,"��ѯ���д�����ĸ���ҵ��������Ϣ");
		return "recognition/issueReport/branchTask";
	}
	
	//�����ĳ��߼��鱨���ʰȡ���������
	@RequestMapping("issueReport")
	@ResponseBody
	public void issueReport(String taskId,boolean result,HttpSession session){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"�ύ'"+af.getEnterprisename()+"'����Ʒ�ķ����ļ�ⱨ�����");
		//�������
		recognitionController.climeAndcompleteTask(taskId,"��","�����ĸ�����Ա","true",-1,session);
		/*if(result){
			recognitionController.climeAndcompleteTask(taskId,"��","��������ͨ��Ա","true",16,session);
		}else{//��ͨ��
			recognitionController.climeAndcompleteTask(taskId,"��","�����������Ա","false",17,session);
		}*/
	}
	
	//��ת������ҵ������ĳ��߼��鱨��
	@RequestMapping("toCenterTasks")
	public String toCenterTasks(){
		AddLog.addLog(Log.QUERY,"��ѯ���д�����ĸ���ҵ��������Ϣ");
		return "recognition/issueReport/centerTask";
	}
}
