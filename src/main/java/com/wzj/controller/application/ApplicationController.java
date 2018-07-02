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
	
	//获取最新的地址
	Document doc = connect("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/");
	//String url = doc.select("ul.center_list_contlist").select("li").first().select("a").first().attr("href");
	String url;
	//获取全国各个省级信息
	//Document connect = connect(url);
	Document connect;
	Map<String,Object> provinceMap = new HashMap<String,Object>();
	Map<String,Object> cityMap = new HashMap<String,Object>();
	//获取省的信息
	@RequestMapping("/getProvince")
	@ResponseBody
	public Object getProvince(){
		if(doc==null)return null;
		url = doc.select("ul.center_list_contlist").select("li").first().select("a").first().attr("href");
		connect = connect(url);
		try {
			Thread.sleep(500);//睡眠一下，否则可能出现各种错误状态码
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		provinceMap.clear();
		JSONObject json = new JSONObject();
		Elements rowProvince = connect.select("tr.provincetr");
		for(Element provinceElement:rowProvince){//遍历每一行的省城市
			Elements select = provinceElement.select("a");//provinceElement.select("a");
			for(Element province:select){//每个省份
				String pro=province.html();
				String result=pro.substring(0,pro.length()-4);
				provinceMap.put(result,province);
				json.put(result, result);
			}
		}
		return json;
	}
	
	//获取市的信息
	@RequestMapping("/getCity")
	@ResponseBody
	public Object getCity(String province){
		try {
			Thread.sleep(500);//睡眠一下，否则可能出现各种错误状态码
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Element ele = (Element) provinceMap.get(province);
		cityMap.clear();
		JSONObject json = new JSONObject();
		Document doc = connect(ele.attr("abs:href"));
		if(doc!=null){
			Elements citys = doc.select("tr.citytr");
			//获取表格的一行数据
			for(Element element:citys){
				String str = element.select("a").get(1).html();
				cityMap.put(str, element);
				json.put(str,str); 
			}
		}
		return json;
	}
	
	//获取区的信息
	@RequestMapping("/getArea")
	@ResponseBody
	public Object getArea(String city){
		try{
			Thread.sleep(500);//睡眠一下，否则可能出现各种错误状态码
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		Element ele = (Element)cityMap.get(city);
		ele = ele.select("a").first();
		JSONObject json = new JSONObject();
		Document doc = connect(ele.attr("abs:href"));
		if(doc!=null){
			Elements areas = doc.select("tr.countytr");
			//获取表格的一行数据
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
	public String insert(ApplicationForm app,HttpServletRequest request) throws Exception{//申请
		String companyName=app.getEnterprisename();
		AddLog.addLog(Log.ADD,companyName+"进行新申请业务");
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		
		String bussinessNo = app.getBusinessno();
//		ApplicationForm af = applicationFormService.getAppFormByBussinessNo(bussinessNo);
		//获取最后一条数据 
		ApplicationForm af = applicationFormService.getLastAppByBussinessNo(bussinessNo);
		String titleNo=commonController.getNum("XB",branchId);
		//如果提交的有印刷资格证，则先删除文件和数据，后添加
		if(!(app.getCertificate().isEmpty())){
			delFile(app.getCertificateno());
			uploadFile(app.getCertificate(),app.getCertificateno(),titleNo,request);
		}
		//如果提交的有营业执照，则先删除文件和数据，后添加
		if(!(app.getBusiness().isEmpty())){
			delFile(app.getBusinessno());
			uploadFile(app.getBusiness(),app.getBusinessno(),titleNo,request);
		}
		//得到最后一条申请申请数据
//		ApplicationForm appform=applicationFormService.getLastApp();
		int qualityno=0;
		//若是不包含qualityno，则拿到业务中qualityno最大值+1，否则不变
		ApplicationForm appform=applicationFormService.getMaxQualityNo();
		if(app.getQualityno()==0){
			qualityno=appform.getQualityno()+1;
		}else{
			qualityno=app.getQualityno();
		}
		//如果提交了质量手册
		if(!(app.getQuality().isEmpty())){
//			delFile(String.valueOf(app.getQualityno()));
			uploadFile(app.getQuality(), qualityno+"",titleNo,request);
		}
		if(af==null){//保存数据库
			//设置所属分支机构Id
			app.setQualityno(qualityno);
			app.setBranchId(branchId);
			app.setTitleno(titleNo);
			app.setCreatedate(new Date());
			Division divisionByCode = branchCenterService.getDivisionByCode(activeUser.getRamusCenter());
			app.setBranchName(divisionByCode.getDivisionname());
			applicationFormService.insertApp(app);
		}else{//修改数据库
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
		//开启流程实例
		Map<String,Object> activitiMap = new HashMap<>();
		//String companyName=app.getEnterprisename();
		activitiMap.put("company", branchId);
		String objId="Application:"+app.getId();
		runtimeService.startProcessInstanceByKey("sptmzzrd",objId,activitiMap);
		//根据公司名称查询任务Id
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);
		String taskId=tasks.get(0).getId();
		//完成个人任务
		completePersonlTask(taskId,"无","分中心审核人员","fzjg","true",2);
		return "redirect:toNewForms";
	}
	
	@RequestMapping("insertReturn")
	public String insertReturn(ApplicationForm app,HttpServletRequest request){//退回任务的提交。先更新信息，在执行任务
		AddLog.addLog(Log.ADD,app.getEnterprisename()+"进行新申请业务");
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		String titleNo=app.getTitleno();
		//如果提交的有印刷资格证，则先删除文件和数据，后添加
		if(!(app.getCertificate().isEmpty())){
			delFile(app.getCertificateno());
			uploadFile(app.getCertificate(),app.getCertificateno(),titleNo,request);
		}
		//如果提交的有营业执照，则先删除文件和数据，后添加
		if(!(app.getBusiness().isEmpty())){
			delFile(app.getBusinessno());
			uploadFile(app.getBusiness(),app.getBusinessno(),titleNo,request);
		}
		//如果提交的有质量手册
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
		//完成个人任务
		completePersonlTask(taskId,"无","分中心审核人员","fzjg","true",2);
		return "redirect:toReturnTask";
	}
	
	@RequestMapping("apply")
	public String apply(int appId,String titleNo,MultipartFile file,HttpSession session,HttpServletRequest request){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		//文件上传
		if(!(file.isEmpty())){
			delFile("xb"+appId);
			uploadFile(file,"xb"+appId,titleNo,request);
		}
		//开启流程实例
		Map<String,Object> activitiMap = new HashMap<>();
		activitiMap.put("company", branchId);
		String objId="Application:"+appId;
		runtimeService.startProcessInstanceByKey("sptmzzrd",objId,activitiMap);
		//根据公司名称查询任务Id
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);
		String taskId=tasks.get(0).getId();
		//完成个人任务
		completePersonlTask(taskId,"无","分中心审核人员","fzjg","true",2);
		return "application/newForm/newForms";
	}
	@RequestMapping("applyReturn")
	public String applyReturn(String taskId,String titleNo,MultipartFile file,HttpServletRequest request){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//文件上传
		if(!(file.isEmpty())){
			delFile("xb"+appId);
			uploadFile(file,"xb"+appId,titleNo,request);
		}
		//完成个人任务
		completePersonlTask(taskId,"无","分中心审核人员","fzjg","true",2);
		return "application/newForm/returnTask";
	}
	
	@RequestMapping("climeAndcompleteTask")
	@ResponseBody
	public void climeAndcompleteTask(String taskId,String comment,String role,String candidate,String result,int status,HttpSession session){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"审核"+af.getEnterprisename()+"的新申请业务");
		//拾取为个人任务
		String userName=((ActiveUser)session.getAttribute("activeUser")).getRealname();
		taskService.claim(taskId, userName);
		//添加评论
		if(!("无".equals(comment))&&StringUtils.isNotBlank(comment)){//如果不为无的话这添加评论
			workflowUtil.addComment(taskId,comment);
		}
		
		//修改业务状态
		if(status>0){
			applicationFormService.updateStatus(status,appId);
		}
		
		//办理业务
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
		AddLog.addLog(Log.ADD,"审查'"+af.getEnterprisename()+"'的新申请业务");
		//拾取为个人任务
		String userName=((ActiveUser)session.getAttribute("activeUser")).getRealname();
		taskService.claim(taskId, userName);
		//添加评论
		if(!("无".equals(comment))&&StringUtils.isNotBlank(comment)){//如果不为无的话这添加评论
			workflowUtil.addComment(taskId,comment);
		}
		
		//修改业务状态
		if(status>0){
			applicationFormService.updateStatus(status,appId);
		}
		
		//办理业务
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
	//如何获取未提交的业务以及被退回来的业务
	@RequestMapping("getReturnTasks")
	@ResponseBody
	public Object getReturnTasks(HttpSession session){//查看被退回的业务。
		AddLog.addLog(Log.QUERY,"查询所有已退回的新申请信息");
		//获取当前登录的用户所属的分支机构Id
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		
		//String branchName="分支机构";//获取当前用户所属的分支机构。启动流程实例是也要按分支机构启动
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);//分支机构名称
		tasks=workflowUtil.getTaskByPointName("申请表提交");
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
	
	//获取评论信息
	@RequestMapping("getComment")
	@ResponseBody
	public Object getComment(String taskId,String companyName){
		AddLog.addLog(Log.QUERY,"查询对'"+companyName+"'申请业务的意见");
		if(taskId.equals("null")){
			return null;
		}else{
			return workflowUtil.findCommentByTaskId(taskId);
		}
	}
	
	//获取保存的评论信息
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
	public String save(ApplicationForm app,HttpServletRequest request){//保存
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		String companyName=app.getEnterprisename();
		app.setStatus(1);//设置"已保存，未申请"的状态
		String bussinessNo = app.getBusinessno();
		//获取最后一条数据
		ApplicationForm af = applicationFormService.getLastAppByBussinessNo(bussinessNo);
		String titleNo=commonController.getNum("XB",branchId);
		//如果提交的有印刷资格证，则先删除文件和数据，后添加
		if(!(app.getCertificate().isEmpty())){
			delFile(app.getCertificateno());
			uploadFile(app.getCertificate(),app.getCertificateno(),titleNo,request);
		}
		//如果提交的有营业执照，则先删除文件和数据，后添加
		if(!(app.getBusiness().isEmpty())){
			delFile(app.getBusinessno());
			uploadFile(app.getBusiness(),app.getBusinessno(),titleNo,request);
		}
		//得到最后一条申请申请数据
//		ApplicationForm appform=applicationFormService.getLastApp();
		int qualityno=0;
		//若是不包含qualityno，则拿到业务中qualityno最大值+1，否则不变
		ApplicationForm appform=applicationFormService.getMaxQualityNo();
				if(app.getQualityno()==0){
					qualityno=appform.getQualityno()+1;
				}else{
					qualityno=app.getQualityno();
				}
		//如果提交了质量手册
		if(!(app.getQuality().isEmpty())){
			delFile(String.valueOf(app.getQualityno()));
			uploadFile(app.getQuality(), qualityno+"",titleNo,request);
		}
		if(af==null){//保存数据库
			app.setQualityno(qualityno);
			AddLog.addLog(Log.ADD,"保存"+companyName+"的新申请信息");
			//设置所属分支机构Id
			app.setBranchId(branchId);
			app.setCreatedate(new Date());
			app.setTitleno(titleNo);
			Division divisionByCode = branchCenterService.getDivisionByCode(activeUser.getRamusCenter());
			app.setBranchName(divisionByCode.getDivisionname());
			applicationFormService.insertApp(app);//在后台的插入进行过了配置
			int id=app.getId();//返回插入后的id
			commonController.savePdf(1,id,request);//直接保存为pdf
		}else{//修改数据库
			AddLog.addLog(Log.MODIFY,"修改"+companyName+"的新申请信息");
			app.setId(af.getId());
			applicationFormService.updateApp(app);
			commonController.savePdf(1,af.getId(),request);//直接保存为pdf
		}
		return "redirect:toNewForms.action";
	}
	//打印寄送信息
	@RequestMapping("printSendMsg")
	@ResponseBody
	public void printSendMsg(ExpressInfo express,String companyName,HttpServletRequest request,HttpServletResponse response,String sendUnit,String reciveUnit){//保存
		AddLog.addLog(Log.EXPORT,"打印'"+companyName+"'的寄送信息");
		commonController.exportPDFSendmsg(express, companyName,request, response,sendUnit,reciveUnit);//直接保存为pdf
	}
	
	//企业打印
	@RequestMapping("comPrintSendMsg")
	@ResponseBody
	public void comPrintSendMsg(ExpressInfo express,String companyName,HttpServletRequest request,HttpServletResponse response,String comId){//保存
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		String branchNo=companyInfoService.getComById(Integer.parseInt(comId)).getBranchId();
		String sendUnit=divisionService.getDivisionByCode(activeUser.getRamusCenter()).getDivisionname();
		String reciveUnit=divisionService.getDivisionByCode(branchNo).getDivisionname();
		AddLog.addLog(Log.EXPORT,"打印'"+companyName+"'的寄送信息");
		commonController.exportPDFSendmsg(express, companyName,request, response,sendUnit,reciveUnit);//直接保存为pdf
	}
	//保存申请
	@RequestMapping("saveApp")
	@ResponseBody
	public void saveApp(ApplicationForm app,HttpServletRequest request,String oldName,String printId,String testId){//保存
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		String companyName=app.getEnterprisename();
		app.setStatus(1);//设置"已保存，未申请"的状态
		//获取最后一条数据
		CompanyInfo ci=companyInfoService.getComByName(companyName);
		
		String titleNo="";
		int id=0;
		AddLog.addLog(Log.ADD,"保存'"+companyName+"'的新申请信息");
		if(oldName.equals("")){
			if(ci==null){//保存数据库
				titleNo=commonController.getNum("XB",branchId);
				//如果提交的有印刷资格证，则先删除文件和数据，后添加
				if(!(app.getCertificate().isEmpty())){
					commonsController.delFile(titleNo,CommonsController.印刷经营许可证);
					commonsController.uploadFile(app.getCertificate(),titleNo,CommonsController.印刷经营许可证,request);
				}
				//如果提交的有营业执照，则先删除文件和数据，后添加
				if(!(app.getBusiness().isEmpty())){
					commonsController.delFile(titleNo,CommonsController.营业执照);
					commonsController.uploadFile(app.getBusiness(),titleNo,CommonsController.营业执照,request);
				}
				//如果提交了质量手册
				if(!(app.getQuality().isEmpty())){
					commonsController.delFile(titleNo,CommonsController.质量手册);
					commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.质量手册,request);
				}
				//设置所属分支机构Id
				app.setBranchId(branchId);
				app.setCreatedate(new Date());
				app.setTitleno(titleNo);
				Division divisionByCode = branchCenterService.getDivisionByCode(activeUser.getRamusCenter());
				app.setBranchName(divisionByCode.getDivisionname());
				applicationFormService.insertApp(app);//在后台的插入进行过了配置
				id=app.getId();
				}
		}else{
			ApplicationForm appform=applicationFormService.getAppFormByName(oldName);
				titleNo=appform.getTitleno();
				//如果提交的有印刷资格证，则先删除文件和数据，后添加
				if(!(app.getCertificate().isEmpty())){
					commonsController.delFile(titleNo,CommonsController.印刷经营许可证);
					commonsController.uploadFile(app.getCertificate(),titleNo,CommonsController.印刷经营许可证,request);
				}
				//如果提交的有营业执照，则先删除文件和数据，后添加
				if(!(app.getBusiness().isEmpty())){
					commonsController.delFile(titleNo,CommonsController.营业执照);
					commonsController.uploadFile(app.getBusiness(),titleNo,CommonsController.营业执照,request);
				}
				//如果提交了质量手册
				if(!(app.getQuality().isEmpty())){
					commonsController.delFile(titleNo,CommonsController.质量手册);
					commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.质量手册,request);
				}
				//设置所属分支机构Id
				commonsController.delFile(titleNo,CommonsController.申请表);
				app.setBranchId(branchId);
				app.setCreatedate(new Date());
				app.setTitleno(titleNo);
				Division divisionByCode = branchCenterService.getDivisionByCode(activeUser.getRamusCenter());
				app.setBranchName(divisionByCode.getDivisionname());
				id=appform.getId();
				app.setId(id);
				applicationFormService.updateApp(app);//在后台的插入进行过了配置
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
		commonController.savePdf(1,id,request);//直接保存为pdf
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("申请表");
		
		//设置路径
		String path = "upload";
		String filePath = path+"/XB/"+app.getTitleno();
		String fileName = app.getTitleno()+"申请"+".pdf";
		filePath = filePath+"/"+fileName;
		uFile.setUprul(filePath);
		//设置大小
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
	//修改申请
	@RequestMapping("updateApp")
	@ResponseBody
	public void updateApp(ApplicationForm app,HttpServletRequest request,String oldName,String printId,String testId){//保存
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String companyName=app.getEnterprisename();
		testingEquipService.updateCompanyName(oldName, companyName);
		printEquipmentService.updateCompanyName(oldName, companyName);
		String titleNo=app.getTitleno();
		//如果提交的有印刷资格证，则先删除文件和数据，后添加
		if(!(app.getCertificate().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.印刷经营许可证);
			commonsController.uploadFile(app.getCertificate(),titleNo,CommonsController.印刷经营许可证,request);
		}
		//如果提交的有营业执照，则先删除文件和数据，后添加
		if(!(app.getBusiness().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.营业执照);
			commonsController.uploadFile(app.getBusiness(),titleNo,CommonsController.营业执照,request);
		}
		//如果提交了质量手册
		if(!(app.getQuality().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.质量手册);
			commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.质量手册,request);
		}
		AddLog.addLog(Log.MODIFY,"修改'"+companyName+"'的新申请信息");
		app.setCreatedate(new Date());
		applicationFormService.updateApp(app);
		String path2 = uploadFileService.getPath(app.getTitleno(),CommonsController.申请表);
		if(path2!=null){
			commonsController.delFile(app.getTitleno(),CommonsController.申请表);
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
		commonController.savePdf(1,app.getId(),request);//直接保存为pdf
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("申请表");
		//设置路径
		String path = "upload";
		String filePath = path+"/XB/"+app.getTitleno();
		String fileName = app.getTitleno()+"申请"+".pdf";
		filePath = filePath+"/"+fileName;
		uFile.setUprul(filePath);
		//设置大小
		File dir=new File(request.getSession().getServletContext().getRealPath("/upload")+"/"+fileName);
		uFile.setUpsize(dir.getTotalSpace()/1024+"kb");
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setDescribeId("4");
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
			
	}
	//获取新保存申请表的流水号
	@RequestMapping("getParam")
	@ResponseBody
	/*public ApplicationForm getParam(HttpServletRequest request){//保存,Model model
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
	//上传申请
	@RequestMapping("startApply")
	@ResponseBody
	public int startApply(int appId,String titleNo,MultipartFile file,HttpSession session,HttpServletRequest request){
		ApplicationForm af=applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.IMPORT,"上传'"+af.getEnterprisename()+"'的新办业务盖章申请表并开启新办业务流程");
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		int userId=activeUser.getUserid();
		//文件上传
		if(!(file.isEmpty())){
			commonsController.delFile(titleNo,CommonsController.申请表_盖章);
			int upResult=commonsController.uploadFile(file,titleNo,CommonsController.申请表_盖章,request);
			if(upResult==-1)return -1;//上传失败
		}
		//开启流程实例
		Map<String,Object> activitiMap = new HashMap<>();
		activitiMap.put("user", branchId);
		String objId="Application:"+appId;
		runtimeService.startProcessInstanceByKey("application",objId,activitiMap);
		//根据流程定义id和节点id查询任务Id
		List<Task> tasks = workflowUtil.getTaskByIds("application", "xbywsq");
		List<String> bids=workflowUtil.getBussinessIdsByTasks(tasks);
		int i=0;
		for(;i<bids.size();i++){
			int id=Integer.parseInt(bids.get(i));
			if(id==appId)
				break;
		}
		String taskId=tasks.get(i).getId();
		//完成任务
		appCommonsController.completeTask(taskId,"", "true", 3);
		return userId;
	}
	//修改申请
	@RequestMapping("update")
	public String update(ApplicationForm app,HttpServletRequest request){//保存
		String titleNo=app.getTitleno();
		//如果提交的有印刷资格证，则先删除文件和数据，后添加
		if(!(app.getCertificate().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.印刷经营许可证);
			commonsController.uploadFile(app.getCertificate(),titleNo,CommonsController.印刷经营许可证,request);
		}
		//如果提交的有营业执照，则先删除文件和数据，后添加
		if(!(app.getBusiness().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.营业执照);
			commonsController.uploadFile(app.getBusiness(),titleNo,CommonsController.营业执照,request);
		}
		//如果提交了质量手册
		if(!(app.getQuality().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.质量手册);
			commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.质量手册,request);
		}
		commonsController.delFile(titleNo,CommonsController.申请表);
		applicationFormService.updateApp(app);
		commonController.savePdf(1,app.getId(),request);//直接保存为pdf
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("申请表");
		
		//设置路径
		String path = "upload";
		String filePath = path+"/XB/"+app.getTitleno();
		String fileName = app.getTitleno()+"申请"+".pdf";
		filePath = filePath+"/"+fileName;
		uFile.setUprul(filePath);
		//设置大小
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
	public String saveReturn(ApplicationForm app,HttpServletRequest request){//保存
		AddLog.addLog(Log.ADD,"保存"+app.getEnterprisename()+"的新申请信息");
		String titleNo=app.getTitleno();
		//如果提交的有印刷资格证，则先删除文件和数据，后添加
		if(!(app.getCertificate().isEmpty())){
			delFile(app.getCertificateno());
			uploadFile(app.getCertificate(),app.getCertificateno(),titleNo,request);
		}
		//如果提交的有营业执照，则先删除文件和数据，后添加
		if(!(app.getBusiness().isEmpty())){
			delFile(app.getBusinessno());
			uploadFile(app.getBusiness(),app.getBusinessno(),titleNo,request);
		}
		//如果提交的有质量手册
		if(!(app.getQuality().isEmpty())){
			delFile(String.valueOf(app.getQualityno()));
			uploadFile(app.getQuality(), app.getQualityno()+"",titleNo,request);
		}
		applicationFormService.updateApp(app);
		commonController.savePdf(1,app.getId(),request);//直接保存为pdf
		return "redirect:toReturnTask.action";
	}
	@RequestMapping("deleteApp")
	@ResponseBody
	public void deleteApp(int id,String taskId){
		ApplicationForm af=applicationFormService.getAppFormById(id);
		String companyName=af.getEnterprisename();
		AddLog.addLog(Log.DELETE,"删除'"+companyName+"'已保存的新申请信息");
		if(taskId!=null&&!"null".equals(taskId)){
			workflowUtil.deleteProcess(taskId);
		}
		String titleNo=af.getTitleno();
		commonsController.delFiles(titleNo,CommonsController.印刷经营许可证);
		commonsController.delFiles(titleNo,CommonsController.营业执照);
		commonsController.delFiles(titleNo,CommonsController.质量手册);
		commonsController.delFiles(titleNo,CommonsController.申请表);
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
		AddLog.addLog(Log.QUERY,"查询所有已保存、退回的新申请信息");
		ActiveUser user=(ActiveUser)session.getAttribute("activeUser");
		//获取业务申请节点的所有任务
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
			AddLog.addLog(Log.QUERY,"获取"+af.getEnterprisename()+"的新申请信息");
		}else{
			AddLog.addLog(Log.QUERY,"获取"+af.getEnterprisename()+"的已退回的新申请信息");
		}
		String titleno=af.getTitleno();
		String path1=uploadFileService.getPath(titleno, CommonsController.营业执照);
		String path2=uploadFileService.getPath(titleno, CommonsController.印刷经营许可证);
		String path3=uploadFileService.getPath(titleno, CommonsController.质量手册);
		String path4=uploadFileService.getPath(titleno, CommonsController.申请表_盖章);
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
		AddLog.addLog(Log.QUERY,"查询所有待审查的新申请信息");
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
		AddLog.addLog(Log.QUERY,"查询"+af.getEnterprisename()+"的申请信息");
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
		AddLog.addLog(Log.QUERY,"查询'"+af.getEnterprisename()+"'的申请信息");
		model.addAttribute("id", id);
		model.addAttribute("taskId", taskId);
		String titleno=af.getTitleno();
		String path1=uploadFileService.getPath(titleno, CommonsController.营业执照);
		String path2=uploadFileService.getPath(titleno, CommonsController.印刷经营许可证);
		String path3=uploadFileService.getPath(titleno, CommonsController.质量手册);
		String path4=uploadFileService.getPath(titleno, CommonsController.申请表_盖章);
		String path5=uploadFileService.getPath(titleno, CommonsController.申请表);
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
		String title = "质量手册";
		String fileName = title+".doc";
		String filePath = "src/main/webapp/"+uploadFileByCode.getUprul();
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			InputStream in = new FileInputStream(new File(filePath));
			//设置下载的相应头
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//获取response字节流
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
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
		String path=uploadFileService.getPath(titleNo,CommonsController.申请表_盖章);
		if(path!=null){
			String fileName=path.substring(path.lastIndexOf("/")+1);
			app.setAppFileName(fileName);
		}
		return app;
	}
	
	@RequestMapping("getOngoing")
	@ResponseBody
	public Object getOngoing(int begin,int end,String status,HttpSession session) {
		AddLog.addLog(Log.QUERY,"查询所有进行中的新申请业务信息");
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
	//根据节点名称获取任务
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

	//根据处理节点任务的角色查询任务id
	@RequestMapping("getGroupTasks")
	@ResponseBody
	public Object getGroupTasks(int status,HttpSession session){
		//获取用户的真实名称
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String userName=activeUser.getRealname();
		String branchId=activeUser.getRamusCenter();
		
		List<Task> tasks = workflowUtil.findGroupTaskListByName(userName);
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		List<ApplicationForm> afs = new ArrayList<ApplicationForm>();
		for(int i=0;i<afList.size();i++){
			if(afList.get(i).getStatus()==status)
				afs.add(afList.get(i));
		}
		for(int i=0;i<afs.size();i++){
			//根据业务id查找任务id，并将任务id设置到业务id中
			ApplicationForm af = afs.get(i);
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afs;
	}
	
	//拾取组任务
	@RequestMapping("pickupTask")
	@ResponseBody
	public void pickupTask(String taskId,HttpSession session){
		int id=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm app=applicationFormService.getAppFormById(id);
		AddLog.addLog(Log.ADD,"拾取"+app.getEnterprisename()+"的新申请业务");
		ActiveUser user=(ActiveUser)session.getAttribute("activeUser");
		String userName = user.getRealname();
		taskService.claim(taskId, userName);
	}
	
	//查看个人任务
	@RequestMapping("getPersonalTasks")
	@ResponseBody
	public Object getPersonalTasks(int status,HttpSession session){
		//获取用户的真实名称
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String name=activeUser.getRealname();
		String branchId=activeUser.getRamusCenter();
		//根据名称查询任务
		List<Task> tasks = workflowUtil.findTaskListByName(name);
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= new ArrayList<ApplicationForm>();
		afList = applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		List<ApplicationForm> afs = new ArrayList<ApplicationForm>();
		for(int i=0;i<afList.size();i++){//获取status状态下的
			if(afList.get(i).getStatus()==status)
				afs.add(afList.get(i));
		}
		for(int i=0;i<afs.size();i++){
			//根据业务id查找任务id，并将任务id设置到业务id中
			ApplicationForm af = afs.get(i);
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afs;
	}
	
	//个人任务退回为组任务
	@RequestMapping("/returnTask")
	@ResponseBody
	public void returnTask(String taskId){
		taskService.setAssignee(taskId, null);
	}
	
	//跳转到证书初始值的页面
	@RequestMapping("toCertificate")
	public String toCertificate(){
		return "application/common/certificate";
	}
	
	//获取所有的证书编号
	@RequestMapping("getCerts")
	@ResponseBody
	public Object getCerts(int iDisplayStart,int iDisplayLength,String sEcho,String sSearch){
		//页码
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength);
        //获取dictionary
        List<Certificate> dic = new ArrayList<>();
        if(StringUtils.isNotBlank(sSearch)){//查询功能
        	dic=certificateService.getCertsBySearch(sSearch);
        }else{//非查询功能
        	dic=certificateService.getCerts();
        }
        //进行分页配置
        PageInfo<Certificate> pageInfo = new PageInfo<Certificate>(dic);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        //页面上显示的数据
        List<Certificate> pageInfoList=pageInfo.getList();
        if(!StringUtils.isNotBlank(sSearch)){//非查询功能
        	//获取对应的企业名称
            List<Certificate> names=certificateService.getAllName(pageInfoList);
            Map<Integer, String> nameMap=new HashMap<>();
            for(Certificate c:names){//方便企业名称和编号对应
            	nameMap.put(c.getCertno(), c.getComName());
            }
            int num=iDisplayStart+1;
            for(int i=0;i<pageInfoList.size();i++){
            	Certificate info=pageInfoList.get(i);
            	info.setNum(num++);//设置编号
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
	
	//设置最初的编号
	@RequestMapping("setCertStart")
	public String setCertStart(int startNo){
		AddLog.addLog(Log.ADD,"设定证书初始编号为'"+startNo+"'");
		//获取第一条数据的certno
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
		//添加评论
		if(!("无".equals(comment))&&StringUtils.isNotBlank(comment)){//如果不为无的话这添加评论
			workflowUtil.addComment(taskId,comment);
		}
		//修改业务状态
		if(status>0){
			int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			applicationFormService.updateStatus(status,id);
		}
		
		//办理业务
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
	
	//通过提交不执行，只保存taskId和评论
	@RequestMapping("/saveComment")
	public String saveComment(String taskId,String comment){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		AddLog.addLog(Log.ADD,"分中心审批'"+af.getEnterprisename()+"'的申请业务通过");
		//添加评论
		if(!("无".equals(comment))&&StringUtils.isNotBlank(comment)){//如果不为无的话这添加评论
			workflowUtil.addComment(taskId,comment);
		}
		return "application/common/sendMessage";
	}
	//业务流程，完成任务（没有角色）
	@RequestMapping("completeTask")
	@ResponseBody
	public void completeTask(String taskId,String comment,String result,int status){
		//添加评论
		if(StringUtils.isNotBlank(comment)){//如果不为无的话这添加评论
			workflowUtil.addComment(taskId,comment);
		}
		//修改业务状态
		if(status>0){
			int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			applicationFormService.updateStatus(status,id);
		}
		//完成任务
		Map<String,Object> map = new HashMap<>();
		map.put("result", result); 
		WorkflowBean workflowBean = new WorkflowBean();
		workflowBean.setTaskId(taskId);
		workflowUtil.completeTask(workflowBean, map);
	}
	//根据file和code上传文件
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
	//跟code删除文件
	@RequestMapping("delFile")
	@ResponseBody
	public void delFile(String code){
		//查询路径
		String path=uploadFileService.getPathByCode(code);
		if(StringUtils.isNotBlank(path)){
			File file = new File(path);
			if(file.exists()){//删除文件
				file.delete();
			}
			//删除数据库中的数据
			uploadFileService.deleteByCode(code);
		}
	}
	@RequestMapping("/deploy")
	@ResponseBody
	public void deploy() {
		workflowUtil.deployment("activiti/application", "新申请业务流程");
		System.out.println("部署成功");
	}
	
	@RequestMapping("getRamusCenterId")
	@ResponseBody
	public String getRamusCenterId(HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		return branchId;
	}
	//跳转到证书初始值的页面
	@RequestMapping("onlyApply")
	public String onlyApply(){
		return "application/common/application";
	}
	
	@RequestMapping("toView")
	public String toView(String titleNo,String taskId,int id,ModelMap model){
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(bid);
		AddLog.addLog(Log.QUERY,"查看'"+af.getEnterprisename()+"'的申请表信息");
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
		AddLog.addLog(Log.QUERY,"查看'"+af.getEnterprisename()+"'的申请表信息");
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
		//根据业务id获取评审表信息
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ReviewForm rf=reviewFormService.getReviewByBId(bid);
		ApplicationForm af=applicationFormService.getAppFormById(bid);
		AddLog.addLog(Log.QUERY,"查看'"+af.getEnterprisename()+"'的评审表信息");
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
	//新申请判断申请企业是否已经提交申请
	@RequestMapping("ifApply")
	@ResponseBody
	public int ifApply(String enterpriseName){
		AddLog.addLog(Log.QUERY,"查询企业名称为：'"+enterpriseName+"'的业务数量");
		int companyCount=applicationFormService.getCountByComName(enterpriseName);
		int titleCount=applicationFormService.getCountByComName2(enterpriseName);
		return (companyCount+titleCount);
	}
}
