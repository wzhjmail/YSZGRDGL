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
		AddLog.addLog(Log.QUERY,"��ѯ���д�����������׼��������ҵ����Ϣ");
		return "application/recheck/personalTask";
	}
	
	@RequestMapping("toIssuing")
	public String toIssuing(){
		AddLog.addLog(Log.QUERY,"��ѯ���д���֤��������ҵ����Ϣ");
		return "application/common/issuing";
	}
	
	//��֤,������ҵ��
	@RequestMapping("issuing")
	@ResponseBody
	public Object issuing(int appId){
		CompanyInfo com =null;
		Date date1=null;
		ApplicationForm aform = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"��'"+aform.getEnterprisename()+"'��������ҵ��֤");
			
		//�趨��֤���ں͵�������
		Calendar calendar=Calendar.getInstance();
		date1=new Date(System.currentTimeMillis());
		calendar.setTime(date1);
		calendar.add(Calendar.YEAR,3);//�����������Ч��
		Date date2=calendar.getTime();
		calendar.setTime(date2);//������ʱ������Ϊ������ǰһ��
		calendar.add(Calendar.DATE,-1);
		date2=calendar.getTime();
		aform.setCertappdate(date1);
		aform.setCerttodate(date2);
		applicationFormService.updateApp(aform);
		
		//��֤��ys_title�е����ݸ��Ƶ�ys_company����
		Obj2Obj obj=new Obj2Obj();
		com = obj.App2Com(aform);
		com.setZhuxiao("��֤��δ��������");
		companyInfoService.insert(com);
		int cid=com.getId();
		//����֤�����к�
		int serial=addSerials(cid+"", Serials.NEWAPP);
		//��֤��Ÿ��µ�ys_company��
		com.setSerial(serial);
		companyInfoService.update(com);
		aform.setCompanyId(cid);
		aform.setCertNo(serial);
		applicationFormService.updateApp(aform);
		//�����������豸��
		testingEquipService.updateByCompanyName(com.getEnterprisename(), cid);
		//����ӡˢ�豸��
		printEquipmentService.updateByCompanyName(com.getEnterprisename(), cid);
		//����֤����Ϣ 
		Map<String,Object> map=new HashMap<>();
		map.put("serial", serial);
		map.put("date", new Date());
		return map;
	}
	//��ȡ֤����Ϣ
	@RequestMapping("getCertInfo")
	@ResponseBody
	public Object getCertInfo(int appId){
		ApplicationForm aform = applicationFormService.getAppFormById(appId);
		//����֤����Ϣ 
		Map<String,Object> map=new HashMap<>();
		map.put("serial", aform.getCertNo());
		map.put("date", aform.getCertappdate());
		return map;
	}
	//�޸ķ�֤����
	@RequestMapping("updateDate")
	@ResponseBody
	public void updateDate(int appId,Date time){
		ApplicationForm aform = applicationFormService.getAppFormById(appId);
		//�趨��֤���ں͵�������
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.YEAR,3);//�����������Ч��
		Date time2=calendar.getTime();
		calendar.setTime(time2);//������ʱ������Ϊ������ǰһ��
		calendar.add(Calendar.DATE,-1);
		time2=calendar.getTime();
		aform.setCertappdate(time);
		aform.setCerttodate(time2);
		aform.setCreatedate(new Date());//�������ڸ�Ϊ�˸�������
		applicationFormService.updateApp(aform);//����ys_title
		//����ys_company
		CompanyInfo companyInfo=companyInfoService.getComById(aform.getCompanyId());
		companyInfo.setCertappdate(time);
		companyInfo.setCerttodate(time2);
		companyInfo.setCreatedate(new Date());//�������ڸ�Ϊ�˸�������
		companyInfoService.update(companyInfo);
	}
	//������Ϣ������ҵ��
	@RequestMapping("centralSendMsg")
	public String centralSendMsg(ExpressInfo express,String taskId,HttpSession session){
		String[] taskIds = taskId.split(","); 
		 for(int i=0;i<taskIds.length;i++){
			 int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskIds[i]));
			 ApplicationForm af = applicationFormService.getAppFormById(appId);
			 //ɾ��������Ϣ
			 expressInfoService.delExpress(appId+"");
			 AddLog.addLog(Log.ADD,"����"+af.getEnterprisename()+"�ĸ���ҵ��������Ϣ");
			 //��Ӽ���ʱ�䡢��ҵ��š������ı��
			 express.setSendDate(new Date());
			 express.setCompanyId(af.getId()+"");
			 express.setBranch(af.getBranchId());
			 //��Ϣ�ϴ�
			 expressInfoService.insertExpress(express);
			//�������
			appCommonsController.completeTask(taskId, "", "true", 17);
			//�޸�ys_company���е�״̬
			companyInfoService.setStatus(af.getCompanyId(), 17);
		 }
		return "application/common/issuing";
	}
	//��֤��������Ϣ
	@RequestMapping("issuing2")
	public String issusing2(ExpressInfo express,String taskId,HttpSession session){
		 String[] taskIds = null;   
		 taskIds = taskId.split(","); 
		 int taskLength=taskIds.length;
		 CompanyInfo com =null;
		 Date date1=null;
		//��ȡ�û�����ʵ����
			String userName=((ActiveUser)session.getAttribute("activeUser")).getRealname();
		for(int i=0;i<taskLength;i++){
			int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskIds[i]));
			ApplicationForm af = applicationFormService.getAppFormById(appId);
			AddLog.addLog(Log.ADD,"��"+af.getEnterprisename()+"��������ҵ��֤");
			AddLog.addLog(Log.ADD,"����"+af.getEnterprisename()+"�Ŀ����Ϣ");
//			//ɾ����Ϣ���е��ظ���������Ϣ
//			expressInfoService.delExpress(af.getCompanyId()+"");
			//ʰȡ��֤����
			taskService.claim(taskIds[i], userName);
			String bid=workflowUtil.findBussinessIdByTaskId(taskIds[i]);
			//��ɷ�֤�鵵����
			applicationController.completePersonlTask(taskIds[i],"��","���ĸ�����Ա","bmzx","true",11);
			//�趨��֤���ں͵�������
			Calendar calendar=Calendar.getInstance();
			date1=new Date(System.currentTimeMillis());
			calendar.setTime(date1);
			calendar.add(Calendar.YEAR,3);//�����������Ч��
			Date date2=calendar.getTime();
			calendar.setTime(date2);//������ʱ������Ϊ������ǰһ��
			calendar.add(Calendar.DATE,-1);
			date2=calendar.getTime();
			int id = Integer.parseInt(bid);
			ApplicationForm aform = applicationFormService.getAppFormById(id);
			aform.setCertappdate(date1);
			aform.setCerttodate(date2);
//			����titleNo;af.setTitleno("");
			applicationFormService.updateApp(aform);
			
			//��֤��ys_title�е����ݸ��Ƶ�ys_company����
			Obj2Obj obj=new Obj2Obj();
			com = obj.App2Com(aform);
			companyInfoService.insert(com);
			int cid=com.getId();
			//����֤�����к�
			int serial=addSerials(cid+"", Serials.NEWAPP);
			//��֤��Ÿ��µ�ys_company��
			com.setSerial(serial);
			
			//��Ӽ���ʱ�䡢��ҵ��š�����������
			express.setSendDate(new Date());
			express.setCompanyId(cid+"");
			express.setBranch(af.getBranchId());
			//��Ϣ�ϴ�
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
		//��ȡ�û�����ʵ����
		String bid=workflowUtil.findBussinessIdByTaskId(taskId);
		int appId = Integer.parseInt(bid);
		ApplicationForm aform = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"��"+aform.getEnterprisename()+"��������ҵ��֤");
			
		//�趨��֤���ں͵�������
		Calendar calendar=Calendar.getInstance();
		date1=new Date(System.currentTimeMillis());
		calendar.setTime(date1);
		calendar.add(Calendar.YEAR,3);//�����������Ч��
		Date date2=calendar.getTime();
		calendar.setTime(date2);//������ʱ������Ϊ������ǰһ��
		calendar.add(Calendar.DATE,-1);
		date2=calendar.getTime();
		aform.setCertappdate(date1);
		aform.setCerttodate(date2);
		applicationFormService.updateApp(aform);
		
		//��֤��ys_title�е����ݸ��Ƶ�ys_company����
		Obj2Obj obj=new Obj2Obj();
		com = obj.App2Com(aform);
		companyInfoService.insert(com);
		int cid=com.getId();
		//����֤�����к�
		int serial=addSerials(cid+"", Serials.NEWAPP);
		//��֤��Ÿ��µ�ys_company��
		com.setSerial(serial);
		companyInfoService.update(com);
		aform.setCompanyId(cid);
		aform.setCertNo(serial);
		applicationFormService.updateApp(aform);
		//�������
		appCommonsController.completeTask(taskId, "", "true", 17);
		//�޸�ys_company���е�״̬
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
		//��ȡ�û�����ʵ����
		String bid=workflowUtil.findBussinessIdByTaskId(taskId);
		int appId = Integer.parseInt(bid);
		ApplicationForm aform = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"�޸�"+aform.getEnterprisename()+"��������ҵ��֤");
		SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");	
		//��֤��ys_title�е����ݸ��Ƶ�ys_company����
		int cid=aform.getCompanyId();
		com=companyInfoService.getComById(cid);
		//����֤�����к�
		int serial=Integer.parseInt(certNo);
		String serial1="";
		List<ApplicationForm> all = applicationFormService.getAll();
		for (ApplicationForm applicationForm : all) {
			serial1+=applicationForm.getCertNo()+",";
		}
		if(serial1.indexOf(serial)>=0){
			prompt="��֤�����Ѿ����ڣ�";
		}else{
			//�趨��֤���ں͵�������
			Calendar calendar=Calendar.getInstance();
			date1=formatter.parse(certTime);
			calendar.setTime(date1);
			calendar.add(Calendar.YEAR,3);//�����������Ч��
			Date date2=calendar.getTime();
			calendar.setTime(date2);//������ʱ������Ϊ������ǰһ��
			calendar.add(Calendar.DATE,-1);
			date2=calendar.getTime();
			aform.setCertappdate(date1);
			aform.setCerttodate(date2);
			//��֤��Ÿ��µ�ys_company��
			com.setCertappdate(date1);
			com.setCerttodate(date2);
			com.setSerial(serial);
			companyInfoService.update(com);
			aform.setCertNo(serial);
			applicationFormService.updateApp(aform);
			prompt="�޸����";
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
			appCommonsController.completeTask(taskIds[i], "", "true", 17);
			//�޸�ys_company���е�״̬
			companyInfoService.setStatus(af.getCompanyId(), 17);
		}
		return "application/common/issuing";
	}
	
	//�����޸ļ�����Ϣ
	@RequestMapping("updateMessage")
	public String updateMessage(ExpressInfo express,String comId){
			ApplicationForm af = applicationFormService.getLastAppBycid(Integer.parseInt(comId));
			AddLog.addLog(Log.ADD,"'"+express.getContact()+"'��'"+express.getReciveName()+"'����'"+af.getEnterprisename()+"'�Ŀ����Ϣ");
			//ɾ��������Ϣ
			expressInfoService.delExpress(comId);
			//��Ӽ���ʱ�䡢��ҵ��š�����������
			express.setSendDate(new Date());
			express.setCompanyId(af.getCompanyId()+"");
			express.setBranch(af.getBranchId());
				//��Ϣ�ϴ�
				expressInfoService.insertExpress(express);
				//�������
//				appCommonsController.completeTask(taskIds[i], "", "true", 17);
				//�޸�ys_company���е�״̬
//				companyInfoService.setStatus(af.getCompanyId(), 17);
//				af.setStatus(17);
//				applicationFormService.updateApp(af);
		return "common/enterpriseInfo";
	}
	
	@RequestMapping("sendMessage")
	public String sendMessage(ExpressInfo express,HttpSession session,int[] ids){
		String branchId=((ActiveUser)session.getAttribute("activeUser")).getRamusCenter();
		//��ȡ�û�����ʵ����
		for(int i=0;i<ids.length;i++){
			CompanyInfo com=companyInfoService.getComById(ids[i]);
			AddLog.addLog(Log.ADD,"����"+com.getEnterprisename()+"�Ŀ����Ϣ");
			//ɾ��������Ϣ
			expressInfoService.delExpress(ids[i]+"");
			//��Ӽ���ʱ�䡢��ҵ��š�����������
			express.setSendDate(new Date());
			express.setCompanyId(ids[i]+"");
			express.setBranch(branchId);
			//��Ϣ�ϴ�
			expressInfoService.insertExpress(express);
		}
		return "common/enterpriseInfo";
	}
	@RequestMapping("addSerials")
	@ResponseBody
	public int addSerials(String code,int type){
		//������ys_cert�в���һ�����ݲ���ȡ�������ݵ�certNo
		int serial=certificateService.getCertNo();
		Serials serials = new Serials();
		serials.setCode(code);
		Calendar calendar=Calendar.getInstance();
		String year=calendar.get(Calendar.YEAR)+"";
		serials.setSerial(serial);
		serials.setYears(year);
		serials.setCreatdate(calendar.getTime());//����ʱ��
		serials.setType(type);//����
		//���뵽���ݿ�
		serialsService.insert(serials);
		return serial;//����֤����
	}
	
	@RequestMapping("apply")
	public String apply(int appId,String titleNo,MultipartFile file,HttpServletRequest request){
		//�ļ��ϴ�
		if(!(file.isEmpty())){
			applicationController.delFile("xb_ps_"+appId);
			applicationController.uploadFile(file,"xb_ps_"+appId,titleNo,request);
		}
		return "application/recheck/personalTask";
	}
	
	@RequestMapping("allInfo")
	public String allInfo(String titleNo,int appId,String taskId,String comName,Model model,String backPage){
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.QUERY,"��ѯ'"+af.getEnterprisename()+"'��������ҵ����Ϣ");
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
		AddLog.addLog(Log.QUERY,"��ѯ"+af.getEnterprisename()+"��ҵ����Ϣ");
		model.addAttribute("titleNo", titleNo);
		model.addAttribute("id", appId);
		model.addAttribute("comName", comName);
		model.addAttribute("taskId", taskId);
		model.addAttribute("backPage", backPage);
		return "application/common/allInfo";
	}
	//�ж��Ƿ�ȫ����֤
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
		//�������
		appCommonsController.completeTask(taskId, "", "true", 17);
		//�޸�ys_company���е�״̬
		companyInfoService.setStatus(af.getCompanyId(), 17);
		//��֤��û�н������̵�����£�����com.setZhuxiao("��֤��δ��������");������ֹ��ҵ��Ϣ�鵽
		//��������̽�����ʱ�򣬽�zhuxiao�ֶ�����Ϊnull
		companyInfoService.changeZhuxiao(af.getCompanyId(),null);
	}
}
