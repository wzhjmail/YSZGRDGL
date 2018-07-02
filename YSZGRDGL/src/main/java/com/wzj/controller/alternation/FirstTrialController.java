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
		AddLog.addLog(Log.QUERY,"��ѯ���д����ı��ҵ��������Ϣ");
		return "alternation/firstTrial/tasks";
	}
	
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		AddLog.addLog(Log.QUERY,"��ѯ�����˻صı��ҵ��������Ϣ");
		return "alternation/firstTrial/returnTask";
	}
	
	//�������
	@RequestMapping("trial")
	public String trial(String taskId,String comment,boolean result,HttpSession session){
		int id=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//ApplicationForm app=applicationFormService.getAppFormById(id);
		FormChange fc=formChangeService.getChangeFormById(id);
		if(result){
			alternationController.climeAndcompleteTask(taskId,comment,"�����ĸ�����Ա","true",2,session);
			AddLog.addLog(Log.ADD,"���������'"+fc.getCompanynameOld()+"'�ı������ͨ��");
		}else{
			alternationController.climeAndcompleteTask(taskId,comment,"��������ͨ��Ա","false",3,session);
			AddLog.addLog(Log.ADD,"���������'"+fc.getCompanynameOld()+"'�ı�����벵��");
		}
		
		return "alternation/firstTrial/tasks";
	}
}
