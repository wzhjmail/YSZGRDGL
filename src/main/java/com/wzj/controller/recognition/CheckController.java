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
	
	//����ҵ������ĸ���ҳ��
	@RequestMapping("toTasks")
	public String toTasks(){
		AddLog.addLog(Log.QUERY,"��ѯ���д�����������׼�ĸ���ҵ��������Ϣ");
		return "recognition/recheck/tasks";
	}
	
	//����ҵ��ķ�֤ҳ��
	@RequestMapping("toIssuing")
	public String toIssuing(){
		AddLog.addLog(Log.QUERY,"��ѯ���д�����֤��ŵĸ���ҵ��������Ϣ");
		return "recognition/recheck/issuing";
	}
	
	//��ȡ֤��ţ��ж��Ƿ��޸�
	@RequestMapping("getSerial")
	@ResponseBody
	public int getSerial(String taskId){
		String bid = workflowUtil.findBussinessIdByTaskId(taskId);
		int aid = Integer.parseInt(bid);//��ȡys_title�еļ�¼
		ApplicationForm af = applicationFormService.getAppFormById(aid);
		int cid=af.getCompanyId();//��ȡys_company�еļ�¼
		CompanyInfo com = companyInfoService.getComById(cid);
		int serial=com.getSerial();
		return serial;
	}
	//����֤���
	@RequestMapping("issuing")
	@ResponseBody
	public Object issusing(String taskId,String companyName,HttpSession session){
		AddLog.addLog(Log.ADD,"��'"+companyName+"'�ĸ������뷢֤");
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//��������ӡˢ�ʸ�֤����Ч����
		ApplicationForm af=applicationFormService.getAppFormById(id);
		
		Calendar calendar=Calendar.getInstance();
		//Date date1=new Date(System.currentTimeMillis());�����ʱ�䣬Ҫ��Ϊ��������
		CompanyInfo info=companyInfoService.getComById(af.getCompanyId());
		Date date1=info.getCerttodate();
		if(date1==null)
			date1=new Date(System.currentTimeMillis());//�����ʱ��
		calendar.setTime(date1);
		calendar.add(Calendar.YEAR,3);//�����������Ч��
		Date date2=calendar.getTime();
		calendar.setTime(date2);//������Ч����������ǰһ��
		calendar.add(Calendar.DATE,-1);
		date2=calendar.getTime();
		af.setCertappdate(date1);
		af.setCerttodate(date2);
		//applicationFormService.updateApp(af);
		//����ys_company���е���Ϣ
		Obj2Obj obj = new Obj2Obj();
		CompanyInfo com=obj.App2Com(af);
		com.setStatus(null);//�����޸�ys_company�е�status��
		companyInfoService.update(com);
		//���ط�֤���ں�֤���
		Map map=new HashMap<>();
		//com��û��serial���ֵ�������ٴθ���Id��ѯCompanyInfo
		CompanyInfo ci = companyInfoService.getComById(com.getId());
		af.setCertNo(ci.getSerial());//��ys_title�м���֤����
		applicationFormService.updateApp(af);
		map.put("serial",ci.getSerial());
		map.put("date", date1);
		return map;
	}
	
	@RequestMapping("allInfo")
	public String allInfo(String titleNo,int appId,String taskId,String comName,Model model,String backPage){
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.QUERY,"��ѯ'"+af.getEnterprisename()+"'�ĸ���ҵ��������Ϣ");
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
		AddLog.addLog(Log.QUERY,"��ѯ"+af.getEnterprisename()+"��ҵ����Ϣ");
		model.addAttribute("titleNo", titleNo);
		model.addAttribute("id", appId);
		model.addAttribute("comName", comName);
		model.addAttribute("taskId", taskId);
		model.addAttribute("backPage", backPage);
		return "recognition/common/allInfoView";
	}
	
	//�ж�֤�����Ƿ����
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
		//�ļ��ϴ�
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
		//��ȡ�û�����ʵ����
		for(int i=0;i<taskLength;i++){
			int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskIds[i]));
			ApplicationForm af = applicationFormService.getAppFormById(appId);
			AddLog.addLog(Log.ADD,"'"+express.getContact()+"'��'"+express.getReciveName()+"'����'"+af.getEnterprisename()+"'�Ŀ����Ϣ");
			//ɾ��������Ϣ
			expressInfoService.delExpress(appId+"");
			//��Ӽ���ʱ�䡢��ҵ��š�����������
			express.setSendDate(new Date());
			express.setCompanyId(af.getCompanyId()+"");
			express.setBranch(af.getBranchId());
			//��Ϣ�ϴ�
			expressInfoService.insertExpress(express);
			//�������
			appCommonsController.completeTask(taskIds[i], "", "true", 34);
			//�޸�ys_company���е�״̬
			companyInfoService.setStatus(af.getCompanyId(), 34);
		}
		return "recognition/recheck/issuing";
	}
	
	@RequestMapping("completeTask")
	@ResponseBody
	public void completeTask(String taskId){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		//�������
		appCommonsController.completeTask(taskId, "", "true", 34);
		//�޸�ys_company���е�״̬
		companyInfoService.setStatus(af.getCompanyId(), 34);
	}
}
