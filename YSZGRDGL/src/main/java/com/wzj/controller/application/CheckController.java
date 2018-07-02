package com.wzj.controller.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.wzj.service.impl.PrintEquipmentService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wzj.common.Obj2Obj;
import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.CompanyInfo;
import com.wzj.pojo.ExpressInfo;
import com.wzj.pojo.Log;
import com.wzj.pojo.Serials;
import com.wzj.service.TestingEquipService;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.CertificateService;
import com.wzj.service.application.impl.CompanyInfoService;
import com.wzj.service.application.impl.ExpressInfoService;
import com.wzj.service.application.impl.SerialsService;
import com.wzj.util.AddLog;
import com.wzj.util.WorkflowUtil;

@Controller("application/check")
@RequestMapping("application/check")
public class CheckController {
	@Autowired
	private ApplicationController applicationController;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private SerialsService serialsService;
	@Autowired
	private CertificateService certificateService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private ExpressInfoService expressInfoService;
	@Autowired
	private AppCommonsController appCommonsController;
	@Autowired
	private TestingEquipService testingEquipService;
	@Autowired
	private PrintEquipmentService printEquipmentService;
	
	@RequestMapping("toGroupTask")
	public String toGroupTask(){
		return "application/recheck/groupTask";
	}
	@RequestMapping("toPersonalTask")
	public String toPersonalTask(){
		AddLog.addLog(Log.QUERY,"查询所有待编码中心批准的新申请业务信息");
		return "application/recheck/personalTask";
	}
	
	@RequestMapping("toIssuing")
	public String toIssuing(){
		AddLog.addLog(Log.QUERY,"查询所有待发证的新申请业务信息");
		return "application/common/issuing";
	}
	
	//发证,不办理业务
	@RequestMapping("issuing")
	@ResponseBody
	public Object issuing(int appId){
		CompanyInfo com =null;
		Date date1=null;
		ApplicationForm aform = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"给'"+aform.getEnterprisename()+"'的新申请业务发证");
			
		//设定发证日期和到期日期
		Calendar calendar=Calendar.getInstance();
		date1=new Date(System.currentTimeMillis());
		calendar.setTime(date1);
		calendar.add(Calendar.YEAR,3);//设置三年的有效期
		Date date2=calendar.getTime();
		calendar.setTime(date2);//将到期时间设置为三年后的前一天
		calendar.add(Calendar.DATE,-1);
		date2=calendar.getTime();
		aform.setCertappdate(date1);
		aform.setCerttodate(date2);
		applicationFormService.updateApp(aform);
		
		//发证后将ys_title中的数据复制到ys_company表中
		Obj2Obj obj=new Obj2Obj();
		com = obj.App2Com(aform);
		com.setZhuxiao("发证后未结束流程");
		companyInfoService.insert(com);
		int cid=com.getId();
		//生成证书序列号
		int serial=addSerials(cid+"", Serials.NEWAPP);
		//将证书号更新到ys_company中
		com.setSerial(serial);
		companyInfoService.update(com);
		aform.setCompanyId(cid);
		aform.setCertNo(serial);
		applicationFormService.updateApp(aform);
		//更新条码检测设备表
		testingEquipService.updateByCompanyName(com.getEnterprisename(), cid);
		//更新印刷设备表
		printEquipmentService.updateByCompanyName(com.getEnterprisename(), cid);
		//返回证书信息 
		Map<String,Object> map=new HashMap<>();
		map.put("serial", serial);
		map.put("date", new Date());
		return map;
	}
	//获取证书信息
	@RequestMapping("getCertInfo")
	@ResponseBody
	public Object getCertInfo(int appId){
		ApplicationForm aform = applicationFormService.getAppFormById(appId);
		//返回证书信息 
		Map<String,Object> map=new HashMap<>();
		map.put("serial", aform.getCertNo());
		map.put("date", aform.getCertappdate());
		return map;
	}
	//修改发证日期
	@RequestMapping("updateDate")
	@ResponseBody
	public void updateDate(int appId,Date time){
		ApplicationForm aform = applicationFormService.getAppFormById(appId);
		//设定发证日期和到期日期
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.YEAR,3);//设置三年的有效期
		Date time2=calendar.getTime();
		calendar.setTime(time2);//将到期时间设置为三年后的前一天
		calendar.add(Calendar.DATE,-1);
		time2=calendar.getTime();
		aform.setCertappdate(time);
		aform.setCerttodate(time2);
		aform.setCreatedate(new Date());//创建日期改为了更新日期
		applicationFormService.updateApp(aform);//更新ys_title
		//更新ys_company
		CompanyInfo companyInfo=companyInfoService.getComById(aform.getCompanyId());
		companyInfo.setCertappdate(time);
		companyInfo.setCerttodate(time2);
		companyInfo.setCreatedate(new Date());//创建日期改为了更新日期
		companyInfoService.update(companyInfo);
	}
	//寄送信息，办理业务
	@RequestMapping("centralSendMsg")
	public String centralSendMsg(ExpressInfo express,String taskId,HttpSession session){
		String[] taskIds = taskId.split(","); 
		 for(int i=0;i<taskIds.length;i++){
			 int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskIds[i]));
			 ApplicationForm af = applicationFormService.getAppFormById(appId);
			 //删除寄送信息
			 expressInfoService.delExpress(appId+"");
			 AddLog.addLog(Log.ADD,"寄送"+af.getEnterprisename()+"的复认业务申请信息");
			 //添加寄送时间、企业编号、分中心编号
			 express.setSendDate(new Date());
			 express.setCompanyId(af.getId()+"");
			 express.setBranch(af.getBranchId());
			 //信息上传
			 expressInfoService.insertExpress(express);
			//完成任务
			appCommonsController.completeTask(taskId, "", "true", 17);
			//修改ys_company表中的状态
			companyInfoService.setStatus(af.getCompanyId(), 17);
		 }
		return "application/common/issuing";
	}
	//发证、寄送信息
	@RequestMapping("issuing2")
	public String issusing2(ExpressInfo express,String taskId,HttpSession session){
		 String[] taskIds = null;   
		 taskIds = taskId.split(","); 
		 int taskLength=taskIds.length;
		 CompanyInfo com =null;
		 Date date1=null;
		//获取用户的真实名称
			String userName=((ActiveUser)session.getAttribute("activeUser")).getRealname();
		for(int i=0;i<taskLength;i++){
			int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskIds[i]));
			ApplicationForm af = applicationFormService.getAppFormById(appId);
			AddLog.addLog(Log.ADD,"给"+af.getEnterprisename()+"的新申请业务发证");
			AddLog.addLog(Log.ADD,"寄送"+af.getEnterprisename()+"的快递信息");
//			//删除信息表中的重复寄送送信息
//			expressInfoService.delExpress(af.getCompanyId()+"");
			//拾取发证任务
			taskService.claim(taskIds[i], userName);
			String bid=workflowUtil.findBussinessIdByTaskId(taskIds[i]);
			//完成发证归档任务
			applicationController.completePersonlTask(taskIds[i],"无","中心复核人员","bmzx","true",11);
			//设定发证日期和到期日期
			Calendar calendar=Calendar.getInstance();
			date1=new Date(System.currentTimeMillis());
			calendar.setTime(date1);
			calendar.add(Calendar.YEAR,3);//设置三年的有效期
			Date date2=calendar.getTime();
			calendar.setTime(date2);//将到期时间设置为三年后的前一天
			calendar.add(Calendar.DATE,-1);
			date2=calendar.getTime();
			int id = Integer.parseInt(bid);
			ApplicationForm aform = applicationFormService.getAppFormById(id);
			aform.setCertappdate(date1);
			aform.setCerttodate(date2);
//			设置titleNo;af.setTitleno("");
			applicationFormService.updateApp(aform);
			
			//发证后将ys_title中的数据复制到ys_company表中
			Obj2Obj obj=new Obj2Obj();
			com = obj.App2Com(aform);
			companyInfoService.insert(com);
			int cid=com.getId();
			//生成证书序列号
			int serial=addSerials(cid+"", Serials.NEWAPP);
			//将证书号更新到ys_company中
			com.setSerial(serial);
			
			//添加寄送时间、企业编号、所属分中心
			express.setSendDate(new Date());
			express.setCompanyId(cid+"");
			express.setBranch(af.getBranchId());
			//信息上传
			expressInfoService.insertExpress(express);
			companyInfoService.update(com);
		}
		return "application/common/issuing";
	}
	@RequestMapping("issuingOne")
	@ResponseBody
	public void issuingOne(String taskId){
		CompanyInfo com =null;
		Date date1=null;
		//获取用户的真实名称
		String bid=workflowUtil.findBussinessIdByTaskId(taskId);
		int appId = Integer.parseInt(bid);
		ApplicationForm aform = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"给"+aform.getEnterprisename()+"的新申请业务发证");
			
		//设定发证日期和到期日期
		Calendar calendar=Calendar.getInstance();
		date1=new Date(System.currentTimeMillis());
		calendar.setTime(date1);
		calendar.add(Calendar.YEAR,3);//设置三年的有效期
		Date date2=calendar.getTime();
		calendar.setTime(date2);//将到期时间设置为三年后的前一天
		calendar.add(Calendar.DATE,-1);
		date2=calendar.getTime();
		aform.setCertappdate(date1);
		aform.setCerttodate(date2);
		applicationFormService.updateApp(aform);
		
		//发证后将ys_title中的数据复制到ys_company表中
		Obj2Obj obj=new Obj2Obj();
		com = obj.App2Com(aform);
		companyInfoService.insert(com);
		int cid=com.getId();
		//生成证书序列号
		int serial=addSerials(cid+"", Serials.NEWAPP);
		//将证书号更新到ys_company中
		com.setSerial(serial);
		companyInfoService.update(com);
		aform.setCompanyId(cid);
		aform.setCertNo(serial);
		applicationFormService.updateApp(aform);
		//完成任务
		appCommonsController.completeTask(taskId, "", "true", 17);
		//修改ys_company表中的状态
		companyInfoService.setStatus(aform.getCompanyId(), 17);
//		Map<String,Object> map=new HashMap<>();
//		map.put("serial", serial);
//		map.put("date", new Date());
//		return map;
	}
	
	@RequestMapping("updateIssuCode")
	@ResponseBody
	public String updateIssuCode(String taskId,String certTime,String certNo) throws ParseException{
		CompanyInfo com =null;
		Date date1=null;
		String prompt="";
		//获取用户的真实名称
		String bid=workflowUtil.findBussinessIdByTaskId(taskId);
		int appId = Integer.parseInt(bid);
		ApplicationForm aform = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"修改"+aform.getEnterprisename()+"的新申请业务发证");
		SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");	
		//发证后将ys_title中的数据复制到ys_company表中
		int cid=aform.getCompanyId();
		com=companyInfoService.getComById(cid);
		//生成证书序列号
		int serial=Integer.parseInt(certNo);
		String serial1="";
		List<ApplicationForm> all = applicationFormService.getAll();
		for (ApplicationForm applicationForm : all) {
			serial1+=applicationForm.getCertNo()+",";
		}
		if(serial1.indexOf(serial)>=0){
			prompt="该证书编号已经存在！";
		}else{
			//设定发证日期和到期日期
			Calendar calendar=Calendar.getInstance();
			date1=formatter.parse(certTime);
			calendar.setTime(date1);
			calendar.add(Calendar.YEAR,3);//设置三年的有效期
			Date date2=calendar.getTime();
			calendar.setTime(date2);//将到期时间设置为三年后的前一天
			calendar.add(Calendar.DATE,-1);
			date2=calendar.getTime();
			aform.setCertappdate(date1);
			aform.setCerttodate(date2);
			//将证书号更新到ys_company中
			com.setCertappdate(date1);
			com.setCerttodate(date2);
			com.setSerial(serial);
			companyInfoService.update(com);
			aform.setCertNo(serial);
			applicationFormService.updateApp(aform);
			prompt="修改完成";
		}
		return prompt;
	}
	@RequestMapping("getCertNo")
	@ResponseBody
	public List<ApplicationForm> getCertNo(){
		List<ApplicationForm> all = applicationFormService.getAll();
		return all;
	}
	@RequestMapping("msgAdd")
	public String msgAdd(ExpressInfo express,String taskId){
		String[] taskIds = null;   
		taskIds = taskId.split(",");  
		int taskLength=taskIds.length;
		//获取用户的真实名称
		for(int i=0;i<taskLength;i++){
			int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskIds[i]));
			ApplicationForm af = applicationFormService.getAppFormById(appId);
			AddLog.addLog(Log.ADD,"'"+express.getContact()+"'向'"+express.getReciveName()+"'寄送'"+af.getEnterprisename()+"'的快递信息");
			//删除寄送信息
			expressInfoService.delExpress(appId+"");
			//添加寄送时间、企业编号、所属分中心
			express.setSendDate(new Date());
			express.setCompanyId(af.getCompanyId()+"");
			express.setBranch(af.getBranchId());
			//信息上传
			expressInfoService.insertExpress(express);
			//完成任务
			appCommonsController.completeTask(taskIds[i], "", "true", 17);
			//修改ys_company表中的状态
			companyInfoService.setStatus(af.getCompanyId(), 17);
		}
		return "application/common/issuing";
	}
	
	//中心修改寄送信息
	@RequestMapping("updateMessage")
	public String updateMessage(ExpressInfo express,String comId){
			ApplicationForm af = applicationFormService.getLastAppBycid(Integer.parseInt(comId));
			AddLog.addLog(Log.ADD,"'"+express.getContact()+"'向'"+express.getReciveName()+"'寄送'"+af.getEnterprisename()+"'的快递信息");
			//删除寄送信息
			expressInfoService.delExpress(comId);
			//添加寄送时间、企业编号、所属分中心
			express.setSendDate(new Date());
			express.setCompanyId(af.getCompanyId()+"");
			express.setBranch(af.getBranchId());
				//信息上传
				expressInfoService.insertExpress(express);
				//完成任务
//				appCommonsController.completeTask(taskIds[i], "", "true", 17);
				//修改ys_company表中的状态
//				companyInfoService.setStatus(af.getCompanyId(), 17);
//				af.setStatus(17);
//				applicationFormService.updateApp(af);
		return "common/enterpriseInfo";
	}
	
	@RequestMapping("sendMessage")
	public String sendMessage(ExpressInfo express,HttpSession session,int[] ids){
		String branchId=((ActiveUser)session.getAttribute("activeUser")).getRamusCenter();
		//获取用户的真实名称
		for(int i=0;i<ids.length;i++){
			CompanyInfo com=companyInfoService.getComById(ids[i]);
			AddLog.addLog(Log.ADD,"寄送"+com.getEnterprisename()+"的快递信息");
			//删除寄送信息
			expressInfoService.delExpress(ids[i]+"");
			//添加寄送时间、企业编号、所属分中心
			express.setSendDate(new Date());
			express.setCompanyId(ids[i]+"");
			express.setBranch(branchId);
			//信息上传
			expressInfoService.insertExpress(express);
		}
		return "common/enterpriseInfo";
	}
	@RequestMapping("addSerials")
	@ResponseBody
	public int addSerials(String code,int type){
		//首先在ys_cert中插入一条数据并获取本条数据的certNo
		int serial=certificateService.getCertNo();
		Serials serials = new Serials();
		serials.setCode(code);
		Calendar calendar=Calendar.getInstance();
		String year=calendar.get(Calendar.YEAR)+"";
		serials.setSerial(serial);
		serials.setYears(year);
		serials.setCreatdate(calendar.getTime());//创建时间
		serials.setType(type);//类型
		//插入到数据库
		serialsService.insert(serials);
		return serial;//返回证书编号
	}
	
	@RequestMapping("apply")
	public String apply(int appId,String titleNo,MultipartFile file,HttpServletRequest request){
		//文件上传
		if(!(file.isEmpty())){
			applicationController.delFile("xb_ps_"+appId);
			applicationController.uploadFile(file,"xb_ps_"+appId,titleNo,request);
		}
		return "application/recheck/personalTask";
	}
	
	@RequestMapping("allInfo")
	public String allInfo(String titleNo,int appId,String taskId,String comName,Model model,String backPage){
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.QUERY,"查询'"+af.getEnterprisename()+"'的新申请业务信息");
		model.addAttribute("titleNo", titleNo);
		model.addAttribute("id", appId);
		model.addAttribute("comName", comName);
		model.addAttribute("taskId", taskId);
		model.addAttribute("backPage", backPage);
		return "application/common/allInfo";
	}
	@RequestMapping("allInfoView")
	public String allInfoView(String titleNo,int appId,String taskId,String comName,Model model,String backPage){
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.QUERY,"查询"+af.getEnterprisename()+"的业务信息");
		model.addAttribute("titleNo", titleNo);
		model.addAttribute("id", appId);
		model.addAttribute("comName", comName);
		model.addAttribute("taskId", taskId);
		model.addAttribute("backPage", backPage);
		return "application/common/allInfo";
	}
	//判断是否全部发证
	@RequestMapping("checkIssue")
	@ResponseBody
	public Boolean checkIssue(int ids[]){
		Boolean flag=true;
		for(int i=0;i<ids.length;i++){
			String bid=workflowUtil.findBussinessIdByTaskId(ids[i]+"");
			int appId = Integer.parseInt(bid);
			ApplicationForm appFormById = applicationFormService.getAppFormById(appId);
		if(appFormById.getCertNo()==0){
			flag=false;
			break;
		}
		}
		return flag;
	}
	
	@RequestMapping("completeTask")
	@ResponseBody
	public void completeTask(String taskId){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		//完成任务
		appCommonsController.completeTask(taskId, "", "true", 17);
		//修改ys_company表中的状态
		companyInfoService.setStatus(af.getCompanyId(), 17);
		//发证后没有结束流程的情况下，做了com.setZhuxiao("发证后未结束流程");操作防止企业信息查到
		//因此在流程结束的时候，将zhuxiao字段设置为null
		companyInfoService.changeZhuxiao(af.getCompanyId(),null);
	}
}
