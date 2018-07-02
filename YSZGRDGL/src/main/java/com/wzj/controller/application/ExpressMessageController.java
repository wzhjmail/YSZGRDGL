package com.wzj.controller.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.wzj.pojo.*;
import com.wzj.service.application.impl.CompanyInfoService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.service.alternation.impl.FormChangeService;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.ExpressInfoService;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.service.impl.PbtSampleService;
import com.wzj.util.AddLog;
import com.wzj.util.WorkflowUtil;
import com.wzj.controller.application.AppCommonsController;

@Controller("application/expressMessage")
@RequestMapping("application/expressMessage")
public class ExpressMessageController {
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private ExpressInfoService expressInfoService;
	@Autowired
	private FormChangeService formChangeService;
	@Autowired
	private AppCommonsController appCommonsController;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private PbtSampleService pbtSampleService;
	@RequestMapping("toSendMessage")
	public String toSendMessage(){
		AddLog.addLog(Log.QUERY,"查询所有待分中心审批的新申请业务");
		return "application/common/sendMessage";
	}
	//根据节点名称和候选人获取本中心的组任务
	@RequestMapping("getGroupTasks")
	@ResponseBody
	public Object getGrouptTasks(String pointName,int[] status,HttpSession session){
		//获取用户的真实名称
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String userName=activeUser.getRealname();
		String branchId=activeUser.getRamusCenter();
		
		//根据节点的名称获和用户名称取任务
		List<Task> tasks=workflowUtil.findGroupTaskListByName(pointName,userName);
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		//还要根据分支机构查询相关的id；
		
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		List<ApplicationForm> forms= new ArrayList<ApplicationForm>();
		for(int i=0;i<afList.size();i++){
			//根据业务id查找任务id，并将任务id设置到业务id中
			ApplicationForm af = afList.get(i);
			for(int t=0;t<status.length;t++){
				if(status[t]==af.getStatus()){
					String str = af.getId()+"";
					String tid=busAndTaskId.get(str);
					af.setTaskId(tid);//设置taskId
					forms.add(af);
					break;
				}
			}
		}
		return forms;
	}
	//根据节点名称和候选人获取所有中心组任务
	@RequestMapping("getAllTask")
	@ResponseBody
	public Object getAllTask(String pointName,int[] status,HttpSession session){
		//获取用户的真实名称
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String userName=activeUser.getRealname();
		
		//根据节点的名称获和用户名称取任务
		List<Task> tasks=workflowUtil.findGroupTaskListByName(pointName,userName);
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		//还要根据分支机构查询相关的id；
		
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet());
		List<ApplicationForm> forms= new ArrayList<ApplicationForm>();
		for(int i=0;i<afList.size();i++){
			//根据业务id查找任务id，并将任务id设置到业务id中
			ApplicationForm af = afList.get(i);
			for(int t=0;t<status.length;t++){
				if(status[t]==af.getStatus()){
					String str = af.getId()+"";
					String tid=busAndTaskId.get(str);
					af.setTaskId(tid);//设置taskId
					forms.add(af);
					break;
				}
			}
		}
		return forms;
	}
	//根据节点和用户名获取个人任务
	@RequestMapping("getPersonalTasks")
	@ResponseBody
	public Object getPersonalTasks(String pointName,HttpSession session){
		//获取用户的真实名称
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String userName=activeUser.getRealname();
		String branchId=activeUser.getRamusCenter();
		
		//根据名称查询任务
		List<Task> tasks = workflowUtil.findPersonalTaskListByName(pointName,userName);
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= new ArrayList<ApplicationForm>();
		afList = applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		for(int i=0;i<afList.size();i++){
			//根据业务id查找任务id，并将任务id设置到业务id中
			ApplicationForm af = afList.get(i);
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afList;
	}
		
	@RequestMapping("insertExpress")
	public String insertExpress(ExpressInfo express,String taskId){
		String[] taskIds = null;   
		taskIds = taskId.split(","); 
		for(int i=0;i<taskIds.length;i++){
		 int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskIds[i]));
		 ApplicationForm af = applicationFormService.getAppFormById(appId);
		//删除寄送信息
			expressInfoService.delExpress(appId+"");
		 AddLog.addLog(Log.ADD,"'"+express.getContact()+"'向'"+express.getReciveName()+"'寄送'"+af.getEnterprisename()+"'的快递信息");
		//添加寄送时间、企业编号、分中心编号
		express.setSendDate(new Date());
		express.setCompanyId(af.getId()+"");
		express.setBranch(af.getBranchId());
		//信息上传
		expressInfoService.insertExpress(express);
		//完成个人任务
		//applicationController.completePersonlTask(taskIds[i],"无","中心审核人员","bmzx","true",5);
		appCommonsController.completeTask(taskIds[i], "", "0", 12);
	 }
		return "application/common/sendMessage";
	}
	
	//根据公司id获取寄送信息上传的相关信息
	@RequestMapping("getExpress")
	@ResponseBody
	public Object getExpress(String companyId){
		if(companyId.indexOf("_change") != -1){
			String id=companyId.split("_")[0];
			FormChange fc=formChangeService.getChangeFormById(Integer.parseInt(id));
			AddLog.addLog(Log.QUERY,"查看'"+fc.getCompanynameOld()+"'的快递信息");
		}else{
			ApplicationForm app=applicationFormService.getAppFormById(Integer.parseInt(companyId));
//			CompanyInfo com= companyInfoService.getComById(Integer.parseInt(companyId));
			AddLog.addLog(Log.QUERY,"查看'"+app.getEnterprisename()+"'的快递信息");
		}
		ExpressInfo object=expressInfoService.getExpressByCompanyId(companyId);
		return object;
	}
	//根据公司id获取寄送信息上传的相关信息
	@RequestMapping("getEnterpriseExpress")
	@ResponseBody
	public Object getEnterpriseExpress(String companyId){
		if(companyId.indexOf("_change") != -1){
			String id=companyId.split("_")[0];
			FormChange fc=formChangeService.getChangeFormById(Integer.parseInt(id));
			AddLog.addLog(Log.QUERY,"查看'"+fc.getCompanynameOld()+"'的快递信息");
		}else{
//			ApplicationForm app=applicationFormService.getAppFormById(Integer.parseInt(companyId));
				CompanyInfo com= companyInfoService.getComById(Integer.parseInt(companyId));
			AddLog.addLog(Log.QUERY,"查看'"+com.getEnterprisename()+"'的快递信息");
		}
		ExpressInfo object=expressInfoService.getExpressByCompanyId(companyId);
		return object;
	}
	//根据公司id删除寄送信息上传的相关信息
	@RequestMapping("delExpress")
	@ResponseBody
	public void DelExpress(String companyId){
		expressInfoService.delExpress(companyId);
	}
	
	//根据多家公司id删除寄送信息上传的相关信息
	@RequestMapping("delCompanyMes")
	@ResponseBody
	public void delCompanyMes(String taskId){
		String[] taskIds = null;   
		taskIds = taskId.split(",");
		for(int i=0;i<taskIds.length;i++){
		 int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskIds[i]));
		 ApplicationForm af = applicationFormService.getAppFormById(appId);
		 DelExpress(af.getCompanyId()+"");
		}
	}
		
	//以下是“中心核对材料”的Controller
	@RequestMapping("toGroupTask")
	public String toGroupTask(){
		return "application/checkMsg/groupTask";
	}
	
	@RequestMapping("toPersonalTask")
	public String toPersonalTask(){
		AddLog.addLog(Log.QUERY,"查询所有待编码中心核准的新申请业务信息");
		return "application/checkMsg/personalTask";
	}
	
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		AddLog.addLog(Log.QUERY,"查询所有退回的新申请业务信息");
		return "application/checkMsg/returnTask";
	}
	//查看复认业务所有信息
	@RequestMapping("viewAll")
	public String viewAll(String titleNo,int appId,String comName,String taskId,Model model){
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.QUERY,"查询'"+af.getEnterprisename()+"'的新申请业务信息");
		model.addAttribute("titleNo", titleNo);
		model.addAttribute("id", appId);
		model.addAttribute("comName", comName);
		model.addAttribute("taskId", taskId);
		return "application/common/viewAll";
	}
	
	//查看复认业务所有信息
	@RequestMapping("ratify")
	public String ratify(String titleNo,int appId,String comName,String taskId,Model model){
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.QUERY,"查询'"+af.getEnterprisename()+"'的新申请业务信息");
		model.addAttribute("titleNo", titleNo);
		model.addAttribute("id", appId);
		model.addAttribute("comName", comName);
		model.addAttribute("taskId", taskId);
		//根据企业名称查询样品编号
		List<PbtSample> samples=pbtSampleService.getSampleByCom(comName);
		String sampleIds="";
		for(PbtSample sample:samples)
			sampleIds=sampleIds+sample.getUf_sample_code()+",";
		if(!"".equals(sampleIds))
			sampleIds=sampleIds.substring(0, sampleIds.length()-1);
		model.addAttribute("sampleIds", sampleIds);
		return "application/common/ratify";
	}
		
	//分中心审批的退回
	@RequestMapping("goBack")
	public String goBack(String taskId,String result,String comment){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.QUERY,"分中心审批'"+af.getEnterprisename()+"'的新办业务驳回");
		int status=0;
		switch (result){
			case "1":
				status=10;
				break;
			case "2":
				status=8;
				break;
			case "3":
				status=6;
				break;
			case "4":
				status=4;
				break;
		}
		if(status!=0)
			appCommonsController.completeTask(taskId, comment, result, status);
		return "redirect:toSendMessage";
	}
	//加载查看资料
	@RequestMapping("allFiles")
	@ResponseBody
	public List<UploadFile> allFiles(String titleNo,String comName){
		AddLog.addLog(Log.QUERY,"查询流水号为"+titleNo+"的业务所有资料");
		//获取除样品以及检测报告之外的资料
		List<UploadFile> uFile = uploadFileService.getByTitleNo(titleNo);
		List<PbtSample> yp=pbtSampleService.getSampleByCom(comName);
		List<UploadFile> uf1 = null;
		List<UploadFile> uf2 = null;
		Set<UploadFile> set=new HashSet<>();
		String describe1="";
		String describe2="";
		String describe3="";
		if(yp!=null){
			for (PbtSample pbtSample : yp) {
				UploadFile uf5=new UploadFile();
				describe1=pbtSample.getUf_sample_code();
				uf1 =uploadFileService.getBydesAndTitleNo(titleNo,"分中心检测报告",describe1);
				uf2 =uploadFileService.getBydesAndTitleNo(titleNo,"中心检测报告",describe1);
				if(uf1.size()!=0){
					if(uf1.size()==1){
						for (UploadFile uf3 : uf1) {
							UploadFile uf=new UploadFile();
							describe2=uf3.getDescribeId();
							uf.setUpdescribe(describe2+"分中心检测报告");
							uf.setDescribeId(uf3.getUpdescribe());
							set.add(uf);
						}	
					}else{
						UploadFile uf=new UploadFile();
						describe2=uf1.get(0).getDescribeId();
						uf.setUpdescribe(describe2+"分中心检测报告");
						uf.setDescribeId(uf1.get(0).getUpdescribe());
						set.add(uf);
					}
					
				}
				
				if(uf2.size()!=0){
					if(uf2.size()==1){
						for (UploadFile uf4 : uf2) {
							UploadFile uf=new UploadFile();
							describe3=uf4.getDescribeId();
							uf.setUpdescribe(describe3+"中心检测报告");
							uf.setDescribeId(uf4.getUpdescribe());
							set.add(uf);
						}	
					}else{
						UploadFile uf=new UploadFile();
						describe3=uf2.get(0).getDescribeId();
						uf.setUpdescribe(describe3+"中心检测报告");
						uf.setDescribeId(uf2.get(0).getUpdescribe());
						set.add(uf);
					}
					
				}
				uf5.setUpdescribe(describe1+"样品附件");
				uf5.setDescribeId(describe1);
				uFile.add(uf5);
			}	
		}
		uFile.addAll(set);
		return uFile;
	}
}
