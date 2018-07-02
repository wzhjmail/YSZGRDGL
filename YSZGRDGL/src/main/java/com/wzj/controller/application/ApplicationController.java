package com.wzj.controller.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzj.DTO.AppDTO;
import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.Certificate;
import com.wzj.pojo.CompanyInfo;
import com.wzj.pojo.Division;
import com.wzj.pojo.ExpressInfo;
import com.wzj.pojo.Log;
import com.wzj.pojo.PrintEquipment;
import com.wzj.pojo.ReviewForm;
import com.wzj.pojo.ReviewFormPart;
import com.wzj.pojo.SysUser;
import com.wzj.pojo.TestingEquip;
import com.wzj.pojo.UploadFile;
import com.wzj.pojo.WorkflowBean;
import com.wzj.service.TestingEquipService;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.CertificateService;
import com.wzj.service.application.impl.CompanyInfoService;
import com.wzj.service.application.impl.ReviewFormPartService;
import com.wzj.service.application.impl.ReviewFormService;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.service.impl.BranchCenterService;
import com.wzj.service.impl.DivisionService;
import com.wzj.service.impl.PrintEquipmentService;
import com.wzj.service.impl.SysRoleService;
import com.wzj.util.AddLog;
import com.wzj.util.UploadFileUtil;
import com.wzj.util.WorkflowUtil;
import com.wzj.controller.CommonsController;
import com.wzj.controller.application.CommonController;

@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
@Controller
@RequestMapping("application")
public class ApplicationController {
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private CertificateService certificateService;
	@Autowired
	private BranchCenterService branchCenterService;
	@Autowired
	private CommonController commonController;
	@Autowired
	private AppCommonsController appCommonsController;
	@Autowired
	private CommonsController commonsController;
	@Autowired
	private ReviewFormService reviewFormService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private ReviewFormPartService reviewFormPartService;
	@Autowired
	private TestingEquipService testingEquipService;
	@Autowired
    private PrintEquipmentService printEquipmentService;
	@Autowired
	private DivisionService divisionService;
	
	//��ȡ���µĵ�ַ
	Document doc = connect("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/");
	//String url = doc.select("ul.center_list_contlist").select("li").first().select("a").first().attr("href");
	String url;
	//��ȡȫ������ʡ����Ϣ
	//Document connect = connect(url);
	Document connect;
	Map<String,Object> provinceMap = new HashMap<String,Object>();
	Map<String,Object> cityMap = new HashMap<String,Object>();
	//��ȡʡ����Ϣ
	@RequestMapping("/getProvince")
	@ResponseBody
	public Object getProvince(){
		if(doc==null)return null;
		url = doc.select("ul.center_list_contlist").select("li").first().select("a").first().attr("href");
		connect = connect(url);
		try {
			Thread.sleep(500);//˯��һ�£�������ܳ��ָ��ִ���״̬��
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		provinceMap.clear();
		JSONObject json = new JSONObject();
		Elements rowProvince = connect.select("tr.provincetr");
		for(Element provinceElement:rowProvince){//����ÿһ�е�ʡ����
			Elements select = provinceElement.select("a");//provinceElement.select("a");
			for(Element province:select){//ÿ��ʡ��
				String pro=province.html();
				String result=pro.substring(0,pro.length()-4);
				provinceMap.put(result,province);
				json.put(result, result);
			}
		}
		return json;
	}
	
	//��ȡ�е���Ϣ
	@RequestMapping("/getCity")
	@ResponseBody
	public Object getCity(String province){
		try {
			Thread.sleep(500);//˯��һ�£�������ܳ��ָ��ִ���״̬��
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Element ele = (Element) provinceMap.get(province);
		cityMap.clear();
		JSONObject json = new JSONObject();
		Document doc = connect(ele.attr("abs:href"));
		if(doc!=null){
			Elements citys = doc.select("tr.citytr");
			//��ȡ����һ������
			for(Element element:citys){
				String str = element.select("a").get(1).html();
				cityMap.put(str, element);
				json.put(str,str); 
			}
		}
		return json;
	}
	
	//��ȡ������Ϣ
	@RequestMapping("/getArea")
	@ResponseBody
	public Object getArea(String city){
		try{
			Thread.sleep(500);//˯��һ�£�������ܳ��ָ��ִ���״̬��
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		Element ele = (Element)cityMap.get(city);
		ele = ele.select("a").first();
		JSONObject json = new JSONObject();
		Document doc = connect(ele.attr("abs:href"));
		if(doc!=null){
			Elements areas = doc.select("tr.countytr");
			//��ȡ����һ������
			for(Element element:areas){
				if(element.select("a").size()>1){
					String str = element.select("a").get(1).html();
					json.put(str,str);
				}
			}
		}
		return json;
	}
	
	private Document connect(String url){
		if(url==null||url.isEmpty()){
			throw new IllegalArgumentException("The input url('"+url+"') is invalid!");
		}
		try{
			//Document doc = Jsoup.connect(url).timeout(100*1000).get();
			return Jsoup.parse(new URL(url).openStream(),"gb2312",url);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping("insert")
	@Transactional
	public String insert(ApplicationForm app,HttpServletRequest request) throws Exception{//����
		String companyName=app.getEnterprisename();
		AddLog.addLog(Log.ADD,companyName+"����������ҵ��");
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		
		String bussinessNo = app.getBusinessno();
//		ApplicationForm af = applicationFormService.getAppFormByBussinessNo(bussinessNo);
		//��ȡ���һ������ 
		ApplicationForm af = applicationFormService.getLastAppByBussinessNo(bussinessNo);
		String titleNo=commonController.getNum("XB",branchId);
		//����ύ����ӡˢ�ʸ�֤������ɾ���ļ������ݣ������
		if(!(app.getCertificate().isEmpty())){
			delFile(app.getCertificateno());
			uploadFile(app.getCertificate(),app.getCertificateno(),titleNo,request);
		}
		//����ύ����Ӫҵִ�գ�����ɾ���ļ������ݣ������
		if(!(app.getBusiness().isEmpty())){
			delFile(app.getBusinessno());
			uploadFile(app.getBusiness(),app.getBusinessno(),titleNo,request);
		}
		//�õ����һ��������������
//		ApplicationForm appform=applicationFormService.getLastApp();
		int qualityno=0;
		//���ǲ�����qualityno�����õ�ҵ����qualityno���ֵ+1�����򲻱�
		ApplicationForm appform=applicationFormService.getMaxQualityNo();
		if(app.getQualityno()==0){
			qualityno=appform.getQualityno()+1;
		}else{
			qualityno=app.getQualityno();
		}
		//����ύ�������ֲ�
		if(!(app.getQuality().isEmpty())){
//			delFile(String.valueOf(app.getQualityno()));
			uploadFile(app.getQuality(), qualityno+"",titleNo,request);
		}
		if(af==null){//�������ݿ�
			//����������֧����Id
			app.setQualityno(qualityno);
			app.setBranchId(branchId);
			app.setTitleno(titleNo);
			app.setCreatedate(new Date());
			Division divisionByCode = branchCenterService.getDivisionByCode(activeUser.getRamusCenter());
			app.setBranchName(divisionByCode.getDivisionname());
			applicationFormService.insertApp(app);
		}else{//�޸����ݿ�
			app.setId(af.getId());
			app.setBranchId(branchId);
			app.setTitleno(commonController.getNum("XB",branchId));
			app.setCreatedate(new Date());
			Division divisionByCode = branchCenterService.getDivisionByCode(activeUser.getRamusCenter());
			app.setBranchName(divisionByCode.getDivisionname());
			applicationFormService.updateApp(app);
		}
		app = applicationFormService.getLastAppByBussinessNo(bussinessNo);
		commonController.savePdf(1, app.getId(), request);
		//��������ʵ��
		Map<String,Object> activitiMap = new HashMap<>();
		//String companyName=app.getEnterprisename();
		activitiMap.put("company", branchId);
		String objId="Application:"+app.getId();
		runtimeService.startProcessInstanceByKey("sptmzzrd",objId,activitiMap);
		//���ݹ�˾���Ʋ�ѯ����Id
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);
		String taskId=tasks.get(0).getId();
		//��ɸ�������
		completePersonlTask(taskId,"��","�����������Ա","fzjg","true",2);
		return "redirect:toNewForms";
	}
	
	@RequestMapping("insertReturn")
	public String insertReturn(ApplicationForm app,HttpServletRequest request){//�˻�������ύ���ȸ�����Ϣ����ִ������
		AddLog.addLog(Log.ADD,app.getEnterprisename()+"����������ҵ��");
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		String titleNo=app.getTitleno();
		//����ύ����ӡˢ�ʸ�֤������ɾ���ļ������ݣ������
		if(!(app.getCertificate().isEmpty())){
			delFile(app.getCertificateno());
			uploadFile(app.getCertificate(),app.getCertificateno(),titleNo,request);
		}
		//����ύ����Ӫҵִ�գ�����ɾ���ļ������ݣ������
		if(!(app.getBusiness().isEmpty())){
			delFile(app.getBusinessno());
			uploadFile(app.getBusiness(),app.getBusinessno(),titleNo,request);
		}
		//����ύ���������ֲ�
		if(!(app.getQuality().isEmpty())){
			delFile(String.valueOf(app.getQualityno()));
			uploadFile(app.getQuality(),app.getQualityno()+"",titleNo,request);
		}
		app.setBranchId(branchId);
		app.setTitleno(titleNo);
		app.setCreatedate(new Date());
		Division divisionByCode = branchCenterService.getDivisionByCode(activeUser.getRamusCenter());
		app.setBranchName(divisionByCode.getDivisionname());
		applicationFormService.updateApp(app);
		commonController.savePdf(1, app.getId(), request);
		String taskId=app.getTaskId();
		//��ɸ�������
		completePersonlTask(taskId,"��","�����������Ա","fzjg","true",2);
		return "redirect:toReturnTask";
	}
	
	@RequestMapping("apply")
	public String apply(int appId,String titleNo,MultipartFile file,HttpSession session,HttpServletRequest request){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		//�ļ��ϴ�
		if(!(file.isEmpty())){
			delFile("xb"+appId);
			uploadFile(file,"xb"+appId,titleNo,request);
		}
		//��������ʵ��
		Map<String,Object> activitiMap = new HashMap<>();
		activitiMap.put("company", branchId);
		String objId="Application:"+appId;
		runtimeService.startProcessInstanceByKey("sptmzzrd",objId,activitiMap);
		//���ݹ�˾���Ʋ�ѯ����Id
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);
		String taskId=tasks.get(0).getId();
		//��ɸ�������
		completePersonlTask(taskId,"��","�����������Ա","fzjg","true",2);
		return "application/newForm/newForms";
	}
	@RequestMapping("applyReturn")
	public String applyReturn(String taskId,String titleNo,MultipartFile file,HttpServletRequest request){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//�ļ��ϴ�
		if(!(file.isEmpty())){
			delFile("xb"+appId);
			uploadFile(file,"xb"+appId,titleNo,request);
		}
		//��ɸ�������
		completePersonlTask(taskId,"��","�����������Ա","fzjg","true",2);
		return "application/newForm/returnTask";
	}
	
	@RequestMapping("climeAndcompleteTask")
	@ResponseBody
	public void climeAndcompleteTask(String taskId,String comment,String role,String candidate,String result,int status,HttpSession session){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"���"+af.getEnterprisename()+"��������ҵ��");
		//ʰȡΪ��������
		String userName=((ActiveUser)session.getAttribute("activeUser")).getRealname();
		taskService.claim(taskId, userName);
		//�������
		if(!("��".equals(comment))&&StringUtils.isNotBlank(comment)){//�����Ϊ�޵Ļ����������
			workflowUtil.addComment(taskId,comment);
		}
		
		//�޸�ҵ��״̬
		if(status>0){
			applicationFormService.updateStatus(status,appId);
		}
		
		//����ҵ��
		Map<String,Object> map = new HashMap<>();
		if(StringUtils.isNotBlank(role)){
			List<SysUser> users = sysRoleService.findUsersByRoleName(role);
			String names="";
			for(SysUser user:users){
				names+=user.getUsername()+",";
			}
			names=names.substring(0,names.length()-1);
			map.put(candidate,names);
		}
		map.put("result", result); 
		WorkflowBean workflowBean = new WorkflowBean();
		workflowBean.setTaskId(taskId);
		workflowUtil.completeTask(workflowBean, map);
	}
	@RequestMapping("climeTask")
	@ResponseBody
	public void climeTask(String taskId,String comment,String role,String candidate,String result,int status,HttpSession session){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"���'"+af.getEnterprisename()+"'��������ҵ��");
		//ʰȡΪ��������
		String userName=((ActiveUser)session.getAttribute("activeUser")).getRealname();
		taskService.claim(taskId, userName);
		//�������
		if(!("��".equals(comment))&&StringUtils.isNotBlank(comment)){//�����Ϊ�޵Ļ����������
			workflowUtil.addComment(taskId,comment);
		}
		
		//�޸�ҵ��״̬
		if(status>0){
			applicationFormService.updateStatus(status,appId);
		}
		
		//����ҵ��
		Map<String,Object> map = new HashMap<>();
		if(StringUtils.isNotBlank(role)){
			List<SysUser> users = sysRoleService.findUsersByRoleName(role);
			String names="";
			for(SysUser user:users){
				names+=user.getUsername()+",";
			}
			names=names.substring(0,names.length()-1);
			map.put(candidate,names);
		}
		map.put("result", result); 
		WorkflowBean workflowBean = new WorkflowBean();
		workflowBean.setTaskId(taskId);
		workflowUtil.completeTask(workflowBean, map);
//		return "redirect:edit.action";
	}
	//��λ�ȡδ�ύ��ҵ���Լ����˻�����ҵ��
	@RequestMapping("getReturnTasks")
	@ResponseBody
	public Object getReturnTasks(HttpSession session){//�鿴���˻ص�ҵ��
		AddLog.addLog(Log.QUERY,"��ѯ�������˻ص���������Ϣ");
		//��ȡ��ǰ��¼���û������ķ�֧����Id
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		
		//String branchName="��֧����";//��ȡ��ǰ�û������ķ�֧��������������ʵ����ҲҪ����֧��������
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);//��֧��������
		tasks=workflowUtil.getTaskByPointName("������ύ");
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= new ArrayList<ApplicationForm>();
		afList = applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		for(int i=0;i<afList.size();i++){
			//����ҵ��id��������id����������id���õ�ҵ��id��
			ApplicationForm af = afList.get(i);
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afList;
	}
	
	//��ȡ������Ϣ
	@RequestMapping("getComment")
	@ResponseBody
	public Object getComment(String taskId,String companyName){
		AddLog.addLog(Log.QUERY,"��ѯ��'"+companyName+"'����ҵ������");
		if(taskId.equals("null")){
			return null;
		}else{
			return workflowUtil.findCommentByTaskId(taskId);
		}
	}
	
	//��ȡ�����������Ϣ
	@RequestMapping("getSavedComment")
	@ResponseBody
	public Object getSavedComment(String taskId){
		if(taskId.equals("null")){
			return null;
		}else{
			return workflowUtil.findLastCommentByTaskId(taskId);
		}
	}
	
	@RequestMapping("save")
	public String save(ApplicationForm app,HttpServletRequest request){//����
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		String companyName=app.getEnterprisename();
		app.setStatus(1);//����"�ѱ��棬δ����"��״̬
		String bussinessNo = app.getBusinessno();
		//��ȡ���һ������
		ApplicationForm af = applicationFormService.getLastAppByBussinessNo(bussinessNo);
		String titleNo=commonController.getNum("XB",branchId);
		//����ύ����ӡˢ�ʸ�֤������ɾ���ļ������ݣ������
		if(!(app.getCertificate().isEmpty())){
			delFile(app.getCertificateno());
			uploadFile(app.getCertificate(),app.getCertificateno(),titleNo,request);
		}
		//����ύ����Ӫҵִ�գ�����ɾ���ļ������ݣ������
		if(!(app.getBusiness().isEmpty())){
			delFile(app.getBusinessno());
			uploadFile(app.getBusiness(),app.getBusinessno(),titleNo,request);
		}
		//�õ����һ��������������
//		ApplicationForm appform=applicationFormService.getLastApp();
		int qualityno=0;
		//���ǲ�����qualityno�����õ�ҵ����qualityno���ֵ+1�����򲻱�
		ApplicationForm appform=applicationFormService.getMaxQualityNo();
				if(app.getQualityno()==0){
					qualityno=appform.getQualityno()+1;
				}else{
					qualityno=app.getQualityno();
				}
		//����ύ�������ֲ�
		if(!(app.getQuality().isEmpty())){
			delFile(String.valueOf(app.getQualityno()));
			uploadFile(app.getQuality(), qualityno+"",titleNo,request);
		}
		if(af==null){//�������ݿ�
			app.setQualityno(qualityno);
			AddLog.addLog(Log.ADD,"����"+companyName+"����������Ϣ");
			//����������֧����Id
			app.setBranchId(branchId);
			app.setCreatedate(new Date());
			app.setTitleno(titleNo);
			Division divisionByCode = branchCenterService.getDivisionByCode(activeUser.getRamusCenter());
			app.setBranchName(divisionByCode.getDivisionname());
			applicationFormService.insertApp(app);//�ں�̨�Ĳ�����й�������
			int id=app.getId();//���ز�����id
			commonController.savePdf(1,id,request);//ֱ�ӱ���Ϊpdf
		}else{//�޸����ݿ�
			AddLog.addLog(Log.MODIFY,"�޸�"+companyName+"����������Ϣ");
			app.setId(af.getId());
			applicationFormService.updateApp(app);
			commonController.savePdf(1,af.getId(),request);//ֱ�ӱ���Ϊpdf
		}
		return "redirect:toNewForms.action";
	}
	//��ӡ������Ϣ
	@RequestMapping("printSendMsg")
	@ResponseBody
	public void printSendMsg(ExpressInfo express,String companyName,HttpServletRequest request,HttpServletResponse response,String sendUnit,String reciveUnit){//����
		AddLog.addLog(Log.EXPORT,"��ӡ'"+companyName+"'�ļ�����Ϣ");
		commonController.exportPDFSendmsg(express, companyName,request, response,sendUnit,reciveUnit);//ֱ�ӱ���Ϊpdf
	}
	
	//��ҵ��ӡ
	@RequestMapping("comPrintSendMsg")
	@ResponseBody
	public void comPrintSendMsg(ExpressInfo express,String companyName,HttpServletRequest request,HttpServletResponse response,String comId){//����
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		String branchNo=companyInfoService.getComById(Integer.parseInt(comId)).getBranchId();
		String sendUnit=divisionService.getDivisionByCode(activeUser.getRamusCenter()).getDivisionname();
		String reciveUnit=divisionService.getDivisionByCode(branchNo).getDivisionname();
		AddLog.addLog(Log.EXPORT,"��ӡ'"+companyName+"'�ļ�����Ϣ");
		commonController.exportPDFSendmsg(express, companyName,request, response,sendUnit,reciveUnit);//ֱ�ӱ���Ϊpdf
	}
	//��������
	@RequestMapping("saveApp")
	@ResponseBody
	public void saveApp(ApplicationForm app,HttpServletRequest request,String oldName,String printId,String testId){//����
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		String companyName=app.getEnterprisename();
		app.setStatus(1);//����"�ѱ��棬δ����"��״̬
		//��ȡ���һ������
		CompanyInfo ci=companyInfoService.getComByName(companyName);
		
		String titleNo="";
		int id=0;
		AddLog.addLog(Log.ADD,"����'"+companyName+"'����������Ϣ");
		if(oldName.equals("")){
			if(ci==null){//�������ݿ�
				titleNo=commonController.getNum("XB",branchId);
				//����ύ����ӡˢ�ʸ�֤������ɾ���ļ������ݣ������
				if(!(app.getCertificate().isEmpty())){
					commonsController.delFile(titleNo,CommonsController.ӡˢ��Ӫ���֤);
					commonsController.uploadFile(app.getCertificate(),titleNo,CommonsController.ӡˢ��Ӫ���֤,request);
				}
				//����ύ����Ӫҵִ�գ�����ɾ���ļ������ݣ������
				if(!(app.getBusiness().isEmpty())){
					commonsController.delFile(titleNo,CommonsController.Ӫҵִ��);
					commonsController.uploadFile(app.getBusiness(),titleNo,CommonsController.Ӫҵִ��,request);
				}
				//����ύ�������ֲ�
				if(!(app.getQuality().isEmpty())){
					commonsController.delFile(titleNo,CommonsController.�����ֲ�);
					commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.�����ֲ�,request);
				}
				//����������֧����Id
				app.setBranchId(branchId);
				app.setCreatedate(new Date());
				app.setTitleno(titleNo);
				Division divisionByCode = branchCenterService.getDivisionByCode(activeUser.getRamusCenter());
				app.setBranchName(divisionByCode.getDivisionname());
				applicationFormService.insertApp(app);//�ں�̨�Ĳ�����й�������
				id=app.getId();
				}
		}else{
			ApplicationForm appform=applicationFormService.getAppFormByName(oldName);
				titleNo=appform.getTitleno();
				//����ύ����ӡˢ�ʸ�֤������ɾ���ļ������ݣ������
				if(!(app.getCertificate().isEmpty())){
					commonsController.delFile(titleNo,CommonsController.ӡˢ��Ӫ���֤);
					commonsController.uploadFile(app.getCertificate(),titleNo,CommonsController.ӡˢ��Ӫ���֤,request);
				}
				//����ύ����Ӫҵִ�գ�����ɾ���ļ������ݣ������
				if(!(app.getBusiness().isEmpty())){
					commonsController.delFile(titleNo,CommonsController.Ӫҵִ��);
					commonsController.uploadFile(app.getBusiness(),titleNo,CommonsController.Ӫҵִ��,request);
				}
				//����ύ�������ֲ�
				if(!(app.getQuality().isEmpty())){
					commonsController.delFile(titleNo,CommonsController.�����ֲ�);
					commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.�����ֲ�,request);
				}
				//����������֧����Id
				commonsController.delFile(titleNo,CommonsController.�����);
				app.setBranchId(branchId);
				app.setCreatedate(new Date());
				app.setTitleno(titleNo);
				Division divisionByCode = branchCenterService.getDivisionByCode(activeUser.getRamusCenter());
				app.setBranchName(divisionByCode.getDivisionname());
				id=appform.getId();
				app.setId(id);
				applicationFormService.updateApp(app);//�ں�̨�Ĳ�����й�������
		}
		PrintEquipment pe=null;
		
		if(!"".equals(printId)){
			String[] printid=printId.split(",");
			for(int i=0;i<printid.length;i++){
				pe=printEquipmentService.getById(Integer.parseInt(printid[i]));
				pe.setCompanyName(companyName);
				pe.setId(Integer.parseInt(printid[i]));
				printEquipmentService.updateById(pe);
			}
		}
		TestingEquip te=null;
		
		if(!"".equals(testId)){
			String[] testid=testId.split(",");
			for(int j=0;j<testid.length;j++){
				te=testingEquipService.getById(Integer.parseInt(testid[j]));
				te.setCompanyName(companyName);
				te.setId(Integer.parseInt(testid[j]));
				testingEquipService.updateById(te);
			}
		}
		commonController.savePdf(1,id,request);//ֱ�ӱ���Ϊpdf
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("�����");
		
		//����·��
		String path = "upload";
		String filePath = path+"/XB/"+app.getTitleno();
		String fileName = app.getTitleno()+"����"+".pdf";
		filePath = filePath+"/"+fileName;
		uFile.setUprul(filePath);
		//���ô�С
		System.out.println(request.getSession().getServletContext().getRealPath("/upload")+"/"+fileName);
		File dir=new File(request.getSession().getServletContext().getRealPath("/upload")+"/"+fileName);
		System.out.println(dir.getTotalSpace()/1024+"kb");
		uFile.setUpsize(dir.getTotalSpace()/1024+"kb");
		
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setDescribeId("4");
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
	}
	//�޸�����
	@RequestMapping("updateApp")
	@ResponseBody
	public void updateApp(ApplicationForm app,HttpServletRequest request,String oldName,String printId,String testId){//����
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String companyName=app.getEnterprisename();
		testingEquipService.updateCompanyName(oldName, companyName);
		printEquipmentService.updateCompanyName(oldName, companyName);
		String titleNo=app.getTitleno();
		//����ύ����ӡˢ�ʸ�֤������ɾ���ļ������ݣ������
		if(!(app.getCertificate().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.ӡˢ��Ӫ���֤);
			commonsController.uploadFile(app.getCertificate(),titleNo,CommonsController.ӡˢ��Ӫ���֤,request);
		}
		//����ύ����Ӫҵִ�գ�����ɾ���ļ������ݣ������
		if(!(app.getBusiness().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.Ӫҵִ��);
			commonsController.uploadFile(app.getBusiness(),titleNo,CommonsController.Ӫҵִ��,request);
		}
		//����ύ�������ֲ�
		if(!(app.getQuality().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.�����ֲ�);
			commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.�����ֲ�,request);
		}
		AddLog.addLog(Log.MODIFY,"�޸�'"+companyName+"'����������Ϣ");
		app.setCreatedate(new Date());
		applicationFormService.updateApp(app);
		String path2 = uploadFileService.getPath(app.getTitleno(),CommonsController.�����);
		if(path2!=null){
			commonsController.delFile(app.getTitleno(),CommonsController.�����);
		}
		PrintEquipment pe=new PrintEquipment();
		pe.setCompanyName(companyName);
		if(!"".equals(printId)){
			String[] printid=printId.split(",");
			for(int i=0;i<printid.length;i++){
				pe.setId(Integer.parseInt(printid[i]));
				printEquipmentService.updateById(pe);
			}
		}
		TestingEquip te=new TestingEquip();
		te.setCompanyName(companyName);
		if(!"".equals(testId)){
			String[] testid=testId.split(",");
			for(int j=0;j<testid.length;j++){
				te.setId(Integer.parseInt(testid[j]));
				testingEquipService.updateById(te);
			}
		}
		commonController.savePdf(1,app.getId(),request);//ֱ�ӱ���Ϊpdf
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("�����");
		//����·��
		String path = "upload";
		String filePath = path+"/XB/"+app.getTitleno();
		String fileName = app.getTitleno()+"����"+".pdf";
		filePath = filePath+"/"+fileName;
		uFile.setUprul(filePath);
		//���ô�С
		File dir=new File(request.getSession().getServletContext().getRealPath("/upload")+"/"+fileName);
		uFile.setUpsize(dir.getTotalSpace()/1024+"kb");
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setDescribeId("4");
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
			
	}
	//��ȡ�±�����������ˮ��
	@RequestMapping("getParam")
	@ResponseBody
	/*public ApplicationForm getParam(HttpServletRequest request){//����,Model model
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		String titleNo1=commonController.getNum("XB",branchId);
		String one=titleNo1.substring(titleNo1.length()-3, titleNo1.length());
		int two=Integer.parseInt(one)-1;
		String titleNo=titleNo1.substring(0, titleNo1.length()-3)+String.format("%03d", two);
		ApplicationForm applicationForm=applicationFormService.getLastAppFormBytitleNo(titleNo);
		applicationForm.setTitleno(titleNo);
		return applicationForm;
	}*/
	public Object getParam(String name,ModelMap map){
		ApplicationForm aForm=applicationFormService.getAppFormByName(name,"%XB%");
		return aForm;
	}
	//�ϴ�����
	@RequestMapping("startApply")
	@ResponseBody
	public int startApply(int appId,String titleNo,MultipartFile file,HttpSession session,HttpServletRequest request){
		ApplicationForm af=applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.IMPORT,"�ϴ�'"+af.getEnterprisename()+"'���°�ҵ���������������°�ҵ������");
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		int userId=activeUser.getUserid();
		//�ļ��ϴ�
		if(!(file.isEmpty())){
			commonsController.delFile(titleNo,CommonsController.�����_����);
			int upResult=commonsController.uploadFile(file,titleNo,CommonsController.�����_����,request);
			if(upResult==-1)return -1;//�ϴ�ʧ��
		}
		//��������ʵ��
		Map<String,Object> activitiMap = new HashMap<>();
		activitiMap.put("user", branchId);
		String objId="Application:"+appId;
		runtimeService.startProcessInstanceByKey("application",objId,activitiMap);
		//�������̶���id�ͽڵ�id��ѯ����Id
		List<Task> tasks = workflowUtil.getTaskByIds("application", "xbywsq");
		List<String> bids=workflowUtil.getBussinessIdsByTasks(tasks);
		int i=0;
		for(;i<bids.size();i++){
			int id=Integer.parseInt(bids.get(i));
			if(id==appId)
				break;
		}
		String taskId=tasks.get(i).getId();
		//�������
		appCommonsController.completeTask(taskId,"", "true", 3);
		return userId;
	}
	//�޸�����
	@RequestMapping("update")
	public String update(ApplicationForm app,HttpServletRequest request){//����
		String titleNo=app.getTitleno();
		//����ύ����ӡˢ�ʸ�֤������ɾ���ļ������ݣ������
		if(!(app.getCertificate().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.ӡˢ��Ӫ���֤);
			commonsController.uploadFile(app.getCertificate(),titleNo,CommonsController.ӡˢ��Ӫ���֤,request);
		}
		//����ύ����Ӫҵִ�գ�����ɾ���ļ������ݣ������
		if(!(app.getBusiness().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.Ӫҵִ��);
			commonsController.uploadFile(app.getBusiness(),titleNo,CommonsController.Ӫҵִ��,request);
		}
		//����ύ�������ֲ�
		if(!(app.getQuality().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.�����ֲ�);
			commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.�����ֲ�,request);
		}
		commonsController.delFile(titleNo,CommonsController.�����);
		applicationFormService.updateApp(app);
		commonController.savePdf(1,app.getId(),request);//ֱ�ӱ���Ϊpdf
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("�����");
		
		//����·��
		String path = "upload";
		String filePath = path+"/XB/"+app.getTitleno();
		String fileName = app.getTitleno()+"����"+".pdf";
		filePath = filePath+"/"+fileName;
		uFile.setUprul(filePath);
		//���ô�С
		File dir=new File(request.getSession().getServletContext().getRealPath("/upload")+"/"+fileName);
		uFile.setUpsize(dir.getTotalSpace()/1024+"kb");
		
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setDescribeId("4");
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
		return "redirect:toPersonalTask.action";
	}
	@RequestMapping("saveReturn")
	public String saveReturn(ApplicationForm app,HttpServletRequest request){//����
		AddLog.addLog(Log.ADD,"����"+app.getEnterprisename()+"����������Ϣ");
		String titleNo=app.getTitleno();
		//����ύ����ӡˢ�ʸ�֤������ɾ���ļ������ݣ������
		if(!(app.getCertificate().isEmpty())){
			delFile(app.getCertificateno());
			uploadFile(app.getCertificate(),app.getCertificateno(),titleNo,request);
		}
		//����ύ����Ӫҵִ�գ�����ɾ���ļ������ݣ������
		if(!(app.getBusiness().isEmpty())){
			delFile(app.getBusinessno());
			uploadFile(app.getBusiness(),app.getBusinessno(),titleNo,request);
		}
		//����ύ���������ֲ�
		if(!(app.getQuality().isEmpty())){
			delFile(String.valueOf(app.getQualityno()));
			uploadFile(app.getQuality(), app.getQualityno()+"",titleNo,request);
		}
		applicationFormService.updateApp(app);
		commonController.savePdf(1,app.getId(),request);//ֱ�ӱ���Ϊpdf
		return "redirect:toReturnTask.action";
	}
	@RequestMapping("deleteApp")
	@ResponseBody
	public void deleteApp(int id,String taskId){
		ApplicationForm af=applicationFormService.getAppFormById(id);
		String companyName=af.getEnterprisename();
		AddLog.addLog(Log.DELETE,"ɾ��'"+companyName+"'�ѱ������������Ϣ");
		if(taskId!=null&&!"null".equals(taskId)){
			workflowUtil.deleteProcess(taskId);
		}
		String titleNo=af.getTitleno();
		commonsController.delFiles(titleNo,CommonsController.ӡˢ��Ӫ���֤);
		commonsController.delFiles(titleNo,CommonsController.Ӫҵִ��);
		commonsController.delFiles(titleNo,CommonsController.�����ֲ�);
		commonsController.delFiles(titleNo,CommonsController.�����);
		printEquipmentService.deleteByCompanyName(companyName);
		testingEquipService.deleteByComName(companyName);
		applicationFormService.deleteById(id);
	}
	@RequestMapping("toNewForms")
	public String toNewForms(){
		return "application/newForm/newForms";
	}
	
	@RequestMapping("getNewForms")
	@ResponseBody
	public Object getNewForms(HttpSession session){
		AddLog.addLog(Log.QUERY,"��ѯ�����ѱ��桢�˻ص���������Ϣ");
		ActiveUser user=(ActiveUser)session.getAttribute("activeUser");
		//��ȡҵ������ڵ����������
		List<ApplicationForm> resultList=new ArrayList<>();
		List<ApplicationForm> apps=(List<ApplicationForm>)appCommonsController.getPointTask("xbywsq", session);
		List<ApplicationForm> forms=applicationFormService.getAppByStatus(1, user.getRamusCenter());
		forms.addAll(apps);
		for (ApplicationForm applicationForm : forms) {
			if(applicationForm.getStatus()==2){
				resultList.add(applicationForm);
			}else if(applicationForm.getStatus()==1&&applicationForm.getTaskId()==null){
				resultList.add(applicationForm);
			}
		}
		return resultList;
	}
	
	@RequestMapping("toApp")
	public String getApp(Model model){
		return "application/newForm/application_form";
	}
	
	@RequestMapping("toReturnForm")
	public String toReturnForm(String taskId,String id,Model model){
		ApplicationForm af=applicationFormService.getAppFormById(Integer.parseInt(id));
		if(taskId==null||taskId.equals("null")){
			AddLog.addLog(Log.QUERY,"��ȡ"+af.getEnterprisename()+"����������Ϣ");
		}else{
			AddLog.addLog(Log.QUERY,"��ȡ"+af.getEnterprisename()+"�����˻ص���������Ϣ");
		}
		String titleno=af.getTitleno();
		String path1=uploadFileService.getPath(titleno, CommonsController.Ӫҵִ��);
		String path2=uploadFileService.getPath(titleno, CommonsController.ӡˢ��Ӫ���֤);
		String path3=uploadFileService.getPath(titleno, CommonsController.�����ֲ�);
		String path4=uploadFileService.getPath(titleno, CommonsController.�����_����);
		model.addAttribute("taskId",taskId);
		model.addAttribute("id",id);
		model.addAttribute("item",af);
		if(path1!=null){
			model.addAttribute("path1",path1.split("/")[path1.split("/").length-1]);
		}
		if(path2!=null){
			model.addAttribute("path2",path2.split("/")[path2.split("/").length-1]);
		}
		if(path3!=null){
			model.addAttribute("path3",path3.split("/")[path3.split("/").length-1]);
		}
		if(path4!=null){
			model.addAttribute("path4",path4.split("/")[path4.split("/").length-1]);
		}
		return "application/common/returnForm";
	}
	
	@RequestMapping("toGroupTask")
	public String toGroupTask(){
		return "application/firstTrial/groupTask";
	}
	
	@RequestMapping("toPersonalTask")
	public String toPersonalTask(Model mode){
		AddLog.addLog(Log.QUERY,"��ѯ���д�������������Ϣ");
		return "application/firstTrial/personalTask";
	}
	
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		return "application/newForm/returnTask";
	}
	@RequestMapping("view")
	public String view(HttpServletRequest request) {
		String id1=request.getParameter("id");
		int id=Integer.parseInt(id1);
		ApplicationForm af=applicationFormService.getAppFormById(id); 
		AddLog.addLog(Log.QUERY,"��ѯ"+af.getEnterprisename()+"��������Ϣ");
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute("id",id);
		return "application/common/application_view";
	}
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model) {
		String id1=request.getParameter("id");
		String taskId=request.getParameter("taskId");
		int id=Integer.parseInt(id1);
		ApplicationForm af=applicationFormService.getAppFormById(id); 
		AddLog.addLog(Log.QUERY,"��ѯ'"+af.getEnterprisename()+"'��������Ϣ");
		model.addAttribute("id", id);
		model.addAttribute("taskId", taskId);
		String titleno=af.getTitleno();
		String path1=uploadFileService.getPath(titleno, CommonsController.Ӫҵִ��);
		String path2=uploadFileService.getPath(titleno, CommonsController.ӡˢ��Ӫ���֤);
		String path3=uploadFileService.getPath(titleno, CommonsController.�����ֲ�);
		String path4=uploadFileService.getPath(titleno, CommonsController.�����_����);
		String path5=uploadFileService.getPath(titleno, CommonsController.�����);
		model.addAttribute("taskId",taskId);
		model.addAttribute("id",id);
		model.addAttribute("item",af);
		if(path1!=null){
			model.addAttribute("path1",path1.split("/")[path1.split("/").length-1]);
		}
		if(path2!=null){
			model.addAttribute("path2",path2.split("/")[path2.split("/").length-1]);
		}
		if(path3!=null){
			model.addAttribute("path3",path3.split("/")[path3.split("/").length-1]);
		}
		if(path4!=null){
			model.addAttribute("path4",path4.split("/")[path4.split("/").length-1]);
		}
		if(path5!=null){
			model.addAttribute("path5",path5.split("/")[path5.split("/").length-1]);
		}
		return "application/common/application_edit";
	}
	@RequestMapping("viewMaterial")
	public String viewMaterial(HttpServletRequest request) {
		String id1=request.getParameter("id");
		int id=Integer.parseInt(id1);
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute("id",id);
		return "common/material";
	}
	@RequestMapping("uploadDisplay")
	public void downloadQuality(HttpServletRequest request,HttpServletResponse response) {
		String code=request.getParameter("code");
		UploadFile uploadFileByCode = applicationFormService.getUploadFileByCode(code);
		String title = "�����ֲ�";
		String fileName = title+".doc";
		String filePath = "src/main/webapp/"+uploadFileByCode.getUprul();
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//��ȡһ����
			InputStream in = new FileInputStream(new File(filePath));
			//�������ص���Ӧͷ
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//��ȡresponse�ֽ���
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
				out.write(b,0,len);
			}
			out.close();
			in.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	@RequestMapping("getApp")
	@ResponseBody
	public Object getApp(int id) {
		ApplicationForm app=applicationFormService.getAppFormById(id);
		String titleNo=app.getTitleno();
		String path=uploadFileService.getPath(titleNo,CommonsController.�����_����);
		if(path!=null){
			String fileName=path.substring(path.lastIndexOf("/")+1);
			app.setAppFileName(fileName);
		}
		return app;
	}
	
	@RequestMapping("getOngoing")
	@ResponseBody
	public Object getOngoing(int begin,int end,String status,HttpSession session) {
		AddLog.addLog(Log.QUERY,"��ѯ���н����е�������ҵ����Ϣ");
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		
		AppDTO appdto=new AppDTO(begin, end);
		appdto.setBranchId(branchId);
		if(branchId.equals("0000")){
			List<ApplicationForm> onGoingList;
			if(status.equals("all")||status.equals("null")){
				onGoingList=applicationFormService.getOngoingAll(appdto);
			}else{
				appdto.setStatus(Integer.parseInt(status));
				onGoingList=applicationFormService.getOngoingByStatus(appdto);
			}
			return onGoingList;
		}
		else{
			List<ApplicationForm> onGoingList;
			if(status.equals("all")||status.equals("null")){
				onGoingList=applicationFormService.getOngoing(appdto);
			}else{
				appdto.setStatus(Integer.parseInt(status));
				onGoingList=applicationFormService.getOngoingByBranch(appdto);
			}
			return onGoingList;
		}
	}
	//���ݽڵ����ƻ�ȡ����
	@RequestMapping("getAppsByPointName")
	@ResponseBody
	public Object getAppsByPointName(String name,HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		
		List<Task> tasks=workflowUtil.getTaskByPointName(name);
		List<String> businessIds = workflowUtil.getBussinessIdsByTasks(tasks);
		List<ApplicationForm> afList= new ArrayList<ApplicationForm>();
		afList = applicationFormService.findBusinessByTasks(businessIds,branchId);
		return afList;
	}

	//���ݴ���ڵ�����Ľ�ɫ��ѯ����id
	@RequestMapping("getGroupTasks")
	@ResponseBody
	public Object getGroupTasks(int status,HttpSession session){
		//��ȡ�û�����ʵ����
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String userName=activeUser.getRealname();
		String branchId=activeUser.getRamusCenter();
		
		List<Task> tasks = workflowUtil.findGroupTaskListByName(userName);
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		List<ApplicationForm> afs = new ArrayList<ApplicationForm>();
		for(int i=0;i<afList.size();i++){
			if(afList.get(i).getStatus()==status)
				afs.add(afList.get(i));
		}
		for(int i=0;i<afs.size();i++){
			//����ҵ��id��������id����������id���õ�ҵ��id��
			ApplicationForm af = afs.get(i);
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afs;
	}
	
	//ʰȡ������
	@RequestMapping("pickupTask")
	@ResponseBody
	public void pickupTask(String taskId,HttpSession session){
		int id=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm app=applicationFormService.getAppFormById(id);
		AddLog.addLog(Log.ADD,"ʰȡ"+app.getEnterprisename()+"��������ҵ��");
		ActiveUser user=(ActiveUser)session.getAttribute("activeUser");
		String userName = user.getRealname();
		taskService.claim(taskId, userName);
	}
	
	//�鿴��������
	@RequestMapping("getPersonalTasks")
	@ResponseBody
	public Object getPersonalTasks(int status,HttpSession session){
		//��ȡ�û�����ʵ����
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String name=activeUser.getRealname();
		String branchId=activeUser.getRamusCenter();
		//�������Ʋ�ѯ����
		List<Task> tasks = workflowUtil.findTaskListByName(name);
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= new ArrayList<ApplicationForm>();
		afList = applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		List<ApplicationForm> afs = new ArrayList<ApplicationForm>();
		for(int i=0;i<afList.size();i++){//��ȡstatus״̬�µ�
			if(afList.get(i).getStatus()==status)
				afs.add(afList.get(i));
		}
		for(int i=0;i<afs.size();i++){
			//����ҵ��id��������id����������id���õ�ҵ��id��
			ApplicationForm af = afs.get(i);
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afs;
	}
	
	//���������˻�Ϊ������
	@RequestMapping("/returnTask")
	@ResponseBody
	public void returnTask(String taskId){
		taskService.setAssignee(taskId, null);
	}
	
	//��ת��֤���ʼֵ��ҳ��
	@RequestMapping("toCertificate")
	public String toCertificate(){
		return "application/common/certificate";
	}
	
	//��ȡ���е�֤����
	@RequestMapping("getCerts")
	@ResponseBody
	public Object getCerts(int iDisplayStart,int iDisplayLength,String sEcho,String sSearch){
		//ҳ��
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //����ҳ���ÿҳ���Ƚ��н�ȡ
        PageHelper.startPage(page_num, iDisplayLength);
        //��ȡdictionary
        List<Certificate> dic = new ArrayList<>();
        if(StringUtils.isNotBlank(sSearch)){//��ѯ����
        	dic=certificateService.getCertsBySearch(sSearch);
        }else{//�ǲ�ѯ����
        	dic=certificateService.getCerts();
        }
        //���з�ҳ����
        PageInfo<Certificate> pageInfo = new PageInfo<Certificate>(dic);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        //ҳ������ʾ������
        List<Certificate> pageInfoList=pageInfo.getList();
        if(!StringUtils.isNotBlank(sSearch)){//�ǲ�ѯ����
        	//��ȡ��Ӧ����ҵ����
            List<Certificate> names=certificateService.getAllName(pageInfoList);
            Map<Integer, String> nameMap=new HashMap<>();
            for(Certificate c:names){//������ҵ���ƺͱ�Ŷ�Ӧ
            	nameMap.put(c.getCertno(), c.getComName());
            }
            int num=iDisplayStart+1;
            for(int i=0;i<pageInfoList.size();i++){
            	Certificate info=pageInfoList.get(i);
            	info.setNum(num++);//���ñ��
            	String name=nameMap.get(info.getCertno());
            	if(StringUtils.isBlank(name))
            		name="";
    			info.setComName(name);
    		}
        }
        
        map.put("aaData", pageInfoList);
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	
	@RequestMapping("getMaxCertNo")
	@ResponseBody
	public int getMaxCertNo(){
		return certificateService.getMaxCertNo();
	}
	
	//��������ı��
	@RequestMapping("setCertStart")
	public String setCertStart(int startNo){
		AddLog.addLog(Log.ADD,"�趨֤���ʼ���Ϊ'"+startNo+"'");
		//��ȡ��һ�����ݵ�certno
		int certNo=certificateService.getFirstCertNo();
		Certificate cf=new Certificate();
		cf.setCertno(certNo);
		cf.setStartno(startNo);
		certificateService.setCertStart(cf);
		return "application/common/certificate";
	}
	
	@RequestMapping("/completePersonlTask")
	@ResponseBody
	public void completePersonlTask(String taskId,String comment,String role,String candidate,String result,int status){
		//�������
		if(!("��".equals(comment))&&StringUtils.isNotBlank(comment)){//�����Ϊ�޵Ļ����������
			workflowUtil.addComment(taskId,comment);
		}
		//�޸�ҵ��״̬
		if(status>0){
			int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			applicationFormService.updateStatus(status,id);
		}
		
		//����ҵ��
		Map<String,Object> map = new HashMap<>();
		if(StringUtils.isNotBlank(role)){
			List<SysUser> users = sysRoleService.findUsersByRoleName(role);
			String names="";
			for(SysUser user:users){
				names+=user.getUsername()+",";
			}
			names=names.substring(0,names.length()-1);
			map.put(candidate,names);
		}
		map.put("result", result); 
		WorkflowBean workflowBean = new WorkflowBean();
		workflowBean.setTaskId(taskId);
		workflowUtil.completeTask(workflowBean, map);
	}
	
	//ͨ���ύ��ִ�У�ֻ����taskId������
	@RequestMapping("/saveComment")
	public String saveComment(String taskId,String comment){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		AddLog.addLog(Log.ADD,"����������'"+af.getEnterprisename()+"'������ҵ��ͨ��");
		//�������
		if(!("��".equals(comment))&&StringUtils.isNotBlank(comment)){//�����Ϊ�޵Ļ����������
			workflowUtil.addComment(taskId,comment);
		}
		return "application/common/sendMessage";
	}
	//ҵ�����̣��������û�н�ɫ��
	@RequestMapping("completeTask")
	@ResponseBody
	public void completeTask(String taskId,String comment,String result,int status){
		//�������
		if(StringUtils.isNotBlank(comment)){//�����Ϊ�޵Ļ����������
			workflowUtil.addComment(taskId,comment);
		}
		//�޸�ҵ��״̬
		if(status>0){
			int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			applicationFormService.updateStatus(status,id);
		}
		//�������
		Map<String,Object> map = new HashMap<>();
		map.put("result", result); 
		WorkflowBean workflowBean = new WorkflowBean();
		workflowBean.setTaskId(taskId);
		workflowUtil.completeTask(workflowBean, map);
	}
	//����file��code�ϴ��ļ�
	@RequestMapping("uploadFile")
	@ResponseBody
	public void uploadFile(MultipartFile mfile,String code,String titleNo,HttpServletRequest request){
		UploadFileUtil uploadFileUtil = new UploadFileUtil();
		UploadFile uFile = new UploadFile();
		Map<String,String> map=null;
		map=uploadFileUtil.uploadFile(mfile,titleNo,request);
		uFile.setUprul(map.get("path"));
		uFile.setCode(code);
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
	}
	//��codeɾ���ļ�
	@RequestMapping("delFile")
	@ResponseBody
	public void delFile(String code){
		//��ѯ·��
		String path=uploadFileService.getPathByCode(code);
		if(StringUtils.isNotBlank(path)){
			File file = new File(path);
			if(file.exists()){//ɾ���ļ�
				file.delete();
			}
			//ɾ�����ݿ��е�����
			uploadFileService.deleteByCode(code);
		}
	}
	@RequestMapping("/deploy")
	@ResponseBody
	public void deploy() {
		workflowUtil.deployment("activiti/application", "������ҵ������");
		System.out.println("����ɹ�");
	}
	
	@RequestMapping("getRamusCenterId")
	@ResponseBody
	public String getRamusCenterId(HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		return branchId;
	}
	//��ת��֤���ʼֵ��ҳ��
	@RequestMapping("onlyApply")
	public String onlyApply(){
		return "application/common/application";
	}
	
	@RequestMapping("toView")
	public String toView(String titleNo,String taskId,int id,ModelMap model){
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(bid);
		AddLog.addLog(Log.QUERY,"�鿴'"+af.getEnterprisename()+"'���������Ϣ");
		model.addAttribute("taskId", taskId);
		model.addAttribute("id", id);
		model.addAttribute("item", titleNo);
		int cid=af.getCompanyId();
		if(cid!=0)
			model.addAttribute("companyId",cid);
		return "common/view";
	}
	
	@RequestMapping("toViewReApply")
	public String toViewReApply(String titleNo,String taskId,int id,ModelMap model){
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(bid);
		AddLog.addLog(Log.QUERY,"�鿴'"+af.getEnterprisename()+"'���������Ϣ");
		model.addAttribute("taskId", taskId);
		model.addAttribute("id", id);
		model.addAttribute("item", titleNo);
		model.addAttribute("companyId", af.getCompanyId());
		return "common/view";
	}
	
	@RequestMapping("toViewPS")
	public String toViewPS(String titleNo,String taskId,ModelMap model){
		model.addAttribute("item", titleNo);
		model.addAttribute("taskId", taskId);
		//����ҵ��id��ȡ�������Ϣ
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ReviewForm rf=reviewFormService.getReviewByBId(bid);
		ApplicationForm af=applicationFormService.getAppFormById(bid);
		AddLog.addLog(Log.QUERY,"�鿴'"+af.getEnterprisename()+"'���������Ϣ");
		if(rf!=null){
			model.addAttribute("rf", rf);
			List<ReviewFormPart> reviewFormPart=reviewFormPartService.getByReviewFormId(rf.getId());
			String num="";
			String psjl="";
			String psjg="";
			for (ReviewFormPart reviewFormPart2 : reviewFormPart) {
				num+=reviewFormPart2.getNum()+",";
				psjl+=reviewFormPart2.getPsjl()+"*";
				psjg+=reviewFormPart2.getPsjg()+",";
			}
			model.addAttribute("num",num.subSequence(0, num.length()-1));
			model.addAttribute("psjl",psjl.subSequence(0, psjl.length()-1));
			model.addAttribute("psjg",psjg.subSequence(0, psjg.length()-1));
		}
		
		return "common/viewPS";
	}
	//�������ж�������ҵ�Ƿ��Ѿ��ύ����
	@RequestMapping("ifApply")
	@ResponseBody
	public int ifApply(String enterpriseName){
		AddLog.addLog(Log.QUERY,"��ѯ��ҵ����Ϊ��'"+enterpriseName+"'��ҵ������");
		int companyCount=applicationFormService.getCountByComName(enterpriseName);
		int titleCount=applicationFormService.getCountByComName2(enterpriseName);
		return (companyCount+titleCount);
	}
}
