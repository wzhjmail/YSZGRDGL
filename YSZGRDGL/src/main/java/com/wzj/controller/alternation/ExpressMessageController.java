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
		AddLog.addLog(Log.QUERY,"查询所有待复核的变更业务申请信息");
		return "alternation/issueReport/sendMessage";
	}
	
	@RequestMapping("insertExpress")
	public String insertExpress(ExpressInfo express,String taskId,HttpSession session){
		String[] taskIds = null;   
		 taskIds = taskId.split(","); 
		 for(int i=0;i<taskIds.length;i++){
			 int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskIds[i]));
			 //添加日志
			FormChange formchange=formChangeService.getChangeFormById(id);
			//删除寄送信息
			expressInfoService.delExpress(id+"_change");
			AddLog.addLog(Log.ADD,"'"+express.getContact()+"'向'"+express.getReciveName()+"'寄送'"+formchange.getCompanynameOld()+"'的快递信息");
			express.setCompanyId(id+"_change");
			express.setBranch(formchange.getOffshootorganiz());
			//添加寄送时间
			express.setSendDate(new Date());
			//信息上传
			expressInfoService.insertExpress(express);
			//完成个人任务
			alternationController.climeAndcompleteTask(taskIds[i],"无","中心审核人员","true",-1,session);
		 }
		return "alternation/issueReport/sendMessage";
	}
	
	@RequestMapping("delExpressInfo")
	@ResponseBody
	public void delExpressInfo(String taskId){
		String id=workflowUtil.findBussinessIdByTaskId(taskId);
		expressInfoService.delExpress(id+"_change");
	}
	//跳转到编码中心审核
	@RequestMapping("toTasks")
	public String toTasks(){
		AddLog.addLog(Log.QUERY,"查看所有待编码中心核准的变更业务申请信息");
		return "alternation/issueReport/tasks";
	}
	
	//查看分中心审批业务
		@RequestMapping("viewAll")
		public String viewAll(String titleNo,int appId,String comName,String taskId,Model model){
			FormChange fc=formChangeService.getChangeFormById(appId);
			AddLog.addLog(Log.QUERY,"查询'"+fc.getCompanynameOld()+"'的变更业务申请信息");
			model.addAttribute("titleNo", titleNo);
			model.addAttribute("id", appId);
			model.addAttribute("comName", comName);
			model.addAttribute("taskId", taskId);
			return "alternation/common/viewAll";
		}

		//查看中心审批业务
			@RequestMapping("viewAllZX")
			public String viewAllZX(String titleNo,int appId,String comName,String taskId,Model model){
				FormChange fc=formChangeService.getChangeFormById(appId);
				AddLog.addLog(Log.QUERY,"查询'"+fc.getCompanynameOld()+"'的变更业务申请信息");
				model.addAttribute("titleNo", titleNo);
				model.addAttribute("id", appId);
				model.addAttribute("comName", comName);
				model.addAttribute("taskId", taskId);
				return "alternation/common/viewAllZX";
			}

		
}
