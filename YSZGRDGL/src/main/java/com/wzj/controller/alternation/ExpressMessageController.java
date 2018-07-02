package com.wzj.controller.alternation;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.pojo.ExpressInfo;
import com.wzj.pojo.FormChange;
import com.wzj.pojo.Log;
import com.wzj.service.alternation.impl.FormChangeService;
import com.wzj.service.application.impl.ExpressInfoService;
import com.wzj.util.AddLog;
import com.wzj.util.WorkflowUtil;

@Controller("alternation/expressMessage")
@RequestMapping("alternation/expressMessage")
public class ExpressMessageController {
	@Autowired
	private ExpressInfoService expressInfoService;
	@Autowired
	private AlternationController alternationController;
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private FormChangeService formChangeService;
	@RequestMapping("toSendMessage")
	public String toSendMessage(){
		AddLog.addLog(Log.QUERY,"��ѯ���д����˵ı��ҵ��������Ϣ");
		return "alternation/issueReport/sendMessage";
	}
	
	@RequestMapping("insertExpress")
	public String insertExpress(ExpressInfo express,String taskId,HttpSession session){
		String[] taskIds = null;   
		 taskIds = taskId.split(","); 
		 for(int i=0;i<taskIds.length;i++){
			 int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskIds[i]));
			 //�����־
			FormChange formchange=formChangeService.getChangeFormById(id);
			//ɾ��������Ϣ
			expressInfoService.delExpress(id+"_change");
			AddLog.addLog(Log.ADD,"'"+express.getContact()+"'��'"+express.getReciveName()+"'����'"+formchange.getCompanynameOld()+"'�Ŀ����Ϣ");
			express.setCompanyId(id+"_change");
			express.setBranch(formchange.getOffshootorganiz());
			//��Ӽ���ʱ��
			express.setSendDate(new Date());
			//��Ϣ�ϴ�
			expressInfoService.insertExpress(express);
			//��ɸ�������
			alternationController.climeAndcompleteTask(taskIds[i],"��","���������Ա","true",-1,session);
		 }
		return "alternation/issueReport/sendMessage";
	}
	
	@RequestMapping("delExpressInfo")
	@ResponseBody
	public void delExpressInfo(String taskId){
		String id=workflowUtil.findBussinessIdByTaskId(taskId);
		expressInfoService.delExpress(id+"_change");
	}
	//��ת�������������
	@RequestMapping("toTasks")
	public String toTasks(){
		AddLog.addLog(Log.QUERY,"�鿴���д��������ĺ�׼�ı��ҵ��������Ϣ");
		return "alternation/issueReport/tasks";
	}
	
	//�鿴����������ҵ��
		@RequestMapping("viewAll")
		public String viewAll(String titleNo,int appId,String comName,String taskId,Model model){
			FormChange fc=formChangeService.getChangeFormById(appId);
			AddLog.addLog(Log.QUERY,"��ѯ'"+fc.getCompanynameOld()+"'�ı��ҵ��������Ϣ");
			model.addAttribute("titleNo", titleNo);
			model.addAttribute("id", appId);
			model.addAttribute("comName", comName);
			model.addAttribute("taskId", taskId);
			return "alternation/common/viewAll";
		}

		//�鿴��������ҵ��
			@RequestMapping("viewAllZX")
			public String viewAllZX(String titleNo,int appId,String comName,String taskId,Model model){
				FormChange fc=formChangeService.getChangeFormById(appId);
				AddLog.addLog(Log.QUERY,"��ѯ'"+fc.getCompanynameOld()+"'�ı��ҵ��������Ϣ");
				model.addAttribute("titleNo", titleNo);
				model.addAttribute("id", appId);
				model.addAttribute("comName", comName);
				model.addAttribute("taskId", taskId);
				return "alternation/common/viewAllZX";
			}

		
}
