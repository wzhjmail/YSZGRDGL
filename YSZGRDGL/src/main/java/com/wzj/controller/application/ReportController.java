package com.wzj.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.CompanyInfo;
import com.wzj.pojo.Log;
import com.wzj.pojo.PbtSample;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.CompanyInfoService;
import com.wzj.util.AddLog;
import com.wzj.util.ExportUtil;
import com.wzj.util.WorkflowUtil;
//���߼��鱨��
@Controller("application/report")
@RequestMapping("application/report")
public class ReportController {
	@Autowired
	private AppCommonsController appCommonsController;
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private ApplicationFormService applicationFormService;
	//�����ĳ��߼��鱨��
	@RequestMapping("toBranchTask")
	public String toBranchTask(){
		AddLog.addLog(Log.QUERY,"��ѯ���д������������ҵ����Ϣ");
		return "application/issueReport/branchTask";
	}
	
	//�����ĳ��߼��鱨���ʰȡ���������
	@RequestMapping("issueReport")
	public String issueReport(String taskId,String comment,String titleno,HttpSession session){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(id);
		String result="application/issueReport/branchTask";
		if(titleno.startsWith("XB")){
			AddLog.addLog(Log.ADD,"��"+af.getEnterprisename()+"��������ҵ����߼��鱨��");
			appCommonsController.completeTask(taskId, comment, "true", 11);
		}else{
			AddLog.addLog(Log.ADD,"��"+af.getEnterprisename()+"�ĸ���ҵ����߼��鱨��");
			appCommonsController.completeTask(taskId, comment, "true", 28);
			result="recognition/issueReport/branchTask";
		}
		return result;
	}
	
	@RequestMapping("toCenterGroupTask")
	public String toCenterGroupTask(){
		return "application/issueReport/centerGroupTask";
	}
	@RequestMapping("toCenterPersonalTask")
	public String toCenterPersonalTask(){
		AddLog.addLog(Log.QUERY,"��ѯ���д������������ҵ����Ϣ");
		return "application/issueReport/centerPersonalTask";
	}
	
	@RequestMapping("toShowSample")
	public String toShowSample(String comName,String titleNo,ModelMap map){
		map.addAttribute("item",comName);
		map.addAttribute("item1",titleNo);
		return "common/sampleInfo2";
	}
	
	@RequestMapping("exportSimpleReport")
	public void exportSimpleReport(String simpleCode,HttpServletRequest request,HttpServletResponse response) throws Exception{
		PbtSample pbtsam=applicationFormService.getSamByCode(simpleCode);
		String path=pbtsam.getUf_report();
		String filePath=request.getServletContext().getRealPath("/")+"/"+path;
		int start=path.lastIndexOf("/")+4;
		String name=path.substring(start, path.length());
		ExportUtil exportUtil=new ExportUtil();
		exportUtil.exportFile(request, response, name, filePath);
	}
}
