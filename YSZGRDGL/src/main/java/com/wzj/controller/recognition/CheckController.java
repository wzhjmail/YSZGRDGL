package com.wzj.controller.recognition;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wzj.common.Obj2Obj;
import com.wzj.controller.application.AppCommonsController;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.Certificate;
import com.wzj.pojo.CompanyInfo;
import com.wzj.pojo.ExpressInfo;
import com.wzj.pojo.Log;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.CertificateService;
import com.wzj.service.application.impl.CompanyInfoService;
import com.wzj.service.application.impl.ExpressInfoService;
import com.wzj.util.AddLog;
import com.wzj.util.WorkflowUtil;

@Controller("recognition/check")
@RequestMapping("recognition/check")
public class CheckController {

	@Autowired
	private RecognitionController recognitionController;
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private CertificateService certificateService;
	@Autowired
	private ExpressInfoService expressInfoService;
	@Autowired
	private AppCommonsController appCommonsController;
	
	//复认业务的中心复核页面
	@RequestMapping("toTasks")
	public String toTasks(){
		AddLog.addLog(Log.QUERY,"查询所有待编码中心批准的复认业务申请信息");
		return "recognition/recheck/tasks";
	}
	
	//复认业务的发证页面
	@RequestMapping("toIssuing")
	public String toIssuing(){
		AddLog.addLog(Log.QUERY,"查询所有待复用证书号的复认业务申请信息");
		return "recognition/recheck/issuing";
	}
	
	//获取证书号，判定是否修改
	@RequestMapping("getSerial")
	@ResponseBody
	public int getSerial(String taskId){
		String bid = workflowUtil.findBussinessIdByTaskId(taskId);
		int aid = Integer.parseInt(bid);//获取ys_title中的记录
		ApplicationForm af = applicationFormService.getAppFormById(aid);
		int cid=af.getCompanyId();//获取ys_company中的记录
		CompanyInfo com = companyInfoService.getComById(cid);
		int serial=com.getSerial();
		return serial;
	}
	//复用证书号
	@RequestMapping("issuing")
	@ResponseBody
	public Object issusing(String taskId,String companyName,HttpSession session){
		AddLog.addLog(Log.ADD,"给'"+companyName+"'的复认申请发证");
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//重新设置印刷资格证的有效日期
		ApplicationForm af=applicationFormService.getAppFormById(id);
		
		Calendar calendar=Calendar.getInstance();
		//Date date1=new Date(System.currentTimeMillis());当天的时间，要改为到期日期
		CompanyInfo info=companyInfoService.getComById(af.getCompanyId());
		Date date1=info.getCerttodate();
		if(date1==null)
			date1=new Date(System.currentTimeMillis());//当天的时间
		calendar.setTime(date1);
		calendar.add(Calendar.YEAR,3);//设置三年的有效期
		Date date2=calendar.getTime();
		calendar.setTime(date2);//设置有效期是三年后的前一天
		calendar.add(Calendar.DATE,-1);
		date2=calendar.getTime();
		af.setCertappdate(date1);
		af.setCerttodate(date2);
		//applicationFormService.updateApp(af);
		//更新ys_company表中的信息
		Obj2Obj obj = new Obj2Obj();
		CompanyInfo com=obj.App2Com(af);
		com.setStatus(null);//不能修改ys_company中的status。
		companyInfoService.update(com);
		//返回发证日期和证书号
		Map map=new HashMap<>();
		//com中没有serial这个值，所以再次根据Id查询CompanyInfo
		CompanyInfo ci = companyInfoService.getComById(com.getId());
		af.setCertNo(ci.getSerial());//在ys_title中加入证书编号
		applicationFormService.updateApp(af);
		map.put("serial",ci.getSerial());
		map.put("date", date1);
		return map;
	}
	
	@RequestMapping("allInfo")
	public String allInfo(String titleNo,int appId,String taskId,String comName,Model model,String backPage){
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.QUERY,"查询'"+af.getEnterprisename()+"'的复认业务申请信息");
		model.addAttribute("titleNo", titleNo);
		model.addAttribute("id", appId);
		model.addAttribute("comName", comName);
		model.addAttribute("taskId", taskId);
		model.addAttribute("backPage", backPage);
		return "recognition/common/allInfo";
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
		return "recognition/common/allInfoView";
	}
	
	//判断证书编号是否存在
	@RequestMapping("checkCertNo")
	@ResponseBody
	public boolean checkCertNo(int certNo){
		Certificate cert =certificateService.checkCert(certNo);
		if(cert==null)
			return false;
		return true;
	}
	
	@RequestMapping("apply")
	public String apply(int appId,String titleNo,MultipartFile file,HttpServletRequest request){
		//文件上传
		if(!(file.isEmpty())){
			recognitionController.delFile("fr_ps_"+appId);
			recognitionController.uploadFile(file,"fr_ps_"+appId,titleNo,request);
		}
		return "recognition/recheck/tasks";
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
			appCommonsController.completeTask(taskIds[i], "", "true", 34);
			//修改ys_company表中的状态
			companyInfoService.setStatus(af.getCompanyId(), 34);
		}
		return "recognition/recheck/issuing";
	}
	
	@RequestMapping("completeTask")
	@ResponseBody
	public void completeTask(String taskId){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		//完成任务
		appCommonsController.completeTask(taskId, "", "true", 34);
		//修改ys_company表中的状态
		companyInfoService.setStatus(af.getCompanyId(), 34);
	}
}
