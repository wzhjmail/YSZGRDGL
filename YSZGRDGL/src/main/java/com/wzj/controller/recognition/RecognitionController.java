package com.wzj.controller.recognition;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzj.common.Obj2Obj;
import com.wzj.controller.CommonsController;
import com.wzj.controller.application.AppCommonsController;
import com.wzj.controller.application.CommonController;
import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.CompanyInfo;
import com.wzj.pojo.Log;
import com.wzj.pojo.PrintEquipment;
import com.wzj.pojo.SysUser;
import com.wzj.pojo.TestingEquip;
import com.wzj.pojo.UploadFile;
import com.wzj.pojo.WorkflowBean;
import com.wzj.service.TestingEquipService;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.CompanyInfoService;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.service.impl.PrintEquipmentService;
import com.wzj.service.impl.SysRoleService;
import com.wzj.util.AddLog;
import com.wzj.util.UploadFileUtil;
import com.wzj.util.WorkflowUtil;

@Controller
@RequestMapping("recognition")
public class RecognitionController {
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private CommonController commonController;
	@Autowired
	private CommonsController commonsController;
	@Autowired
	private AppCommonsController appCommonsController;
	@Autowired
	private TestingEquipService testingEquipService;
	@Autowired
    private PrintEquipmentService printEquipmentService;
	
	//根据公司id，查询复认的总次数，及信息
	@RequestMapping("getCount")
	@ResponseBody
	public Object getCount(String companyId,int iDisplayStart,int iDisplayLength,String sEcho){
		//页码
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength);
		List<ApplicationForm> list=applicationFormService.getFRCount(companyId);
		for(int i=0;i<list.size();i++){
			list.get(i).setId(i+1);
		}
		//进行分页配置
        PageInfo<ApplicationForm> pageInfo = new PageInfo<ApplicationForm>(list);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	//根据分支机构id查询所有的企业信息
	@RequestMapping("getComsByBranchId")
	@ResponseBody
	public Object getComsByBranchId(String branchId){
		return companyInfoService.getAll(branchId);
	}
	
	//根据公司id将企业信息提前复认
	
	//跳转复认业务申请下的已保存申请
	@RequestMapping("toNewForms")
	public String toNewForms(){
		AddLog.addLog(Log.QUERY,"查询所有已保存、退回的复认业务申请信息");
		return "recognition/newForm/newForms";
	}
	
	//跳转复认业务申请页面
	@RequestMapping("toReAactivate")
	public String toReAactivate(int cid,Model model){
		ApplicationForm af=applicationFormService.getLastAppBycid(cid);
//		model.addAttribute("taskId",taskId);
		model.addAttribute("item",af);
		model.addAttribute("id",af.getId());
		model.addAttribute("titleNo", af.getTitleno());
		return "recognition/newForm/reApply";
	}
	//获取待复认的业务
	@RequestMapping("getNewForms")
	@ResponseBody
	public Object getNewForms(HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		String titleno="";
		//从ys_company中拿出能复认的表
		List<CompanyInfo> coms=companyInfoService.getComByStatus(18,branchId);
		
		Obj2Obj obj=new Obj2Obj();
		//List<ApplicationForm> lists=new ArrayList<ApplicationForm>();
		for(int i=0;i<coms.size();i++){
			CompanyInfo com=coms.get(i);
			ApplicationForm app=obj.Com2App(com);
			titleno=com.getTitleno().replace("XB","FR");
			//lists.add(app);
			//将查询出来的信息保存到ys_title中
			app.setCreatedate(new Date());
			app.setCompanyId(com.getId());
			app.setTitleno(titleno);//清空titleNo
			applicationFormService.insertApp(app);
			//修改ys_company表中数据的状态
			companyInfoService.setStatus(com.getId(),37);//业务审核中
		}
		List<ApplicationForm> apps=applicationFormService.getAppByStatus(18,branchId);
		List<ApplicationForm> returns=(List<ApplicationForm>)getPointTask("xbywsq",session);
		apps.addAll(returns);
		return  apps;
	}
	
	//复认业务的修改
	@RequestMapping("toReturnForm")
	public String toReturnForm(String taskId,String id,Model model){
		ApplicationForm af=applicationFormService.getAppFormById(Integer.parseInt(id));
		AddLog.addLog(Log.QUERY,"查看'"+af.getEnterprisename()+"'的复认业务申请信息");
		model.addAttribute("taskId",taskId);
		model.addAttribute("id",id);
		model.addAttribute("titleNo",af.getTitleno());
		model.addAttribute("companyId",af.getCompanyId());
		String yyzz=uploadFileService.getPath(af.getTitleno(),CommonsController.营业执照);
		String yszg=uploadFileService.getPath(af.getTitleno(),CommonsController.印刷经营许可证);
		String zlsc=uploadFileService.getPath(af.getTitleno(),CommonsController.质量手册);
		if(yyzz!=null){
			String[] split1 = yyzz.split("/");
			model.addAttribute("yyzz",split1[split1.length-1]);
		}
		if(yszg!=null){
			String[] split2 = yszg.split("/");
			model.addAttribute("yszg",split2[split2.length-1]);
		}
		if(zlsc!=null){
			String[] split3 = zlsc.split("/");
			model.addAttribute("zlsc",split3[split3.length-1]);
		}
		return "recognition/newForm/returnForm";
	}
	
	//复认业务的保存
	@RequestMapping("save")
	@ResponseBody
	public void save(ApplicationForm app,HttpServletRequest request,String oldName,String cId){//更新数据
		AddLog.addLog(Log.ADD,"保存'"+app.getEnterprisename()+"'的复认申请信息");
		String titleNo=app.getTitleno();
		String companyName=app.getEnterprisename();
		testingEquipService.updateCompanyName(oldName, app.getEnterprisename());
		printEquipmentService.updateCompanyName(oldName, app.getEnterprisename());
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
		//如果提交的有质量手册，则先删除文件和数据，后添加
		if(!(app.getQuality().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.质量手册);
			commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.质量手册,request);
		}
		PrintEquipment pe=new PrintEquipment();
		pe.setCompanyName(companyName);
		pe.setCompanyId(Integer.parseInt(cId));
		printEquipmentService.updateById(pe);
		TestingEquip te=new TestingEquip();
		te.setCompanyName(companyName);
		te.setCompanyId(Integer.parseInt(cId));
		testingEquipService.updateById(te);
		commonsController.delFile(titleNo,CommonsController.申请表);
		app.setCreatedate(new Date());
		app.setTitleno(titleNo);
		applicationFormService.updateApp(app);
		commonController.savePdf(2, app.getId(), request);//生成pdf
		//return "recognition/newForm/newForms";
		
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("申请表");
		
		//设置路径
		String path = "upload";
		String filePath = path+"/FR/"+app.getTitleno();
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
	//复认业务的保存
	@RequestMapping("saveReApp")
	@ResponseBody
	public void saveReApp(ApplicationForm app,HttpServletRequest request){//更新数据
		AddLog.addLog(Log.ADD,"保存"+app.getEnterprisename()+"的申请信息");
		String titleNo=commonController.getNum("FR",app.getBranchId());
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
		//如果提交的有质量手册，则先删除文件和数据，后添加
		if(!(app.getQuality().isEmpty())){
			delFile(app.getQualityno()+"");
			uploadFile(app.getQuality(),app.getQualityno()+"",titleNo,request);
		}
		app.setCreatedate(new Date());
		app.setTitleno(titleNo);
		applicationFormService.updateApp(app);
		commonController.savebgApp(1, app.getId(), request);//生成pdf
	}
	//获取新保存申请表的流水号
	@RequestMapping("getParam")
	@ResponseBody
	public Object getParam(String name,ModelMap map){
		ApplicationForm aForm=applicationFormService.getAppFormByName(name,"%FR%");
		String titleNo=aForm.getTitleno();
		String path=uploadFileService.getPath(titleNo,CommonsController.申请表_盖章);
		if(path!=null&&path!=""){
			String fileName=path.substring(path.lastIndexOf("/")+1);
			aForm.setAppFileName(fileName);
		}
		return aForm;
	}
	//复认业务的退回页面
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		AddLog.addLog(Log.QUERY,"查询所有退回的复认业务申请信息");
		return "recognition/newForm/returnTask";
	}
	//复认业务退回的保存
	@RequestMapping("saveReturn")
	@ResponseBody
	public void saveReturn(ApplicationForm app,HttpServletRequest request,String oldName,String cId){//保存
		AddLog.addLog(Log.ADD,"保存'"+app.getEnterprisename()+"'的复认申请信息");
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String titleNo=app.getTitleno();
		String branchId=activeUser.getRamusCenter();
		String companyName=app.getEnterprisename();
		testingEquipService.updateCompanyName(oldName, app.getEnterprisename());
		printEquipmentService.updateCompanyName(oldName, app.getEnterprisename());
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
		//如果提交的有质量手册，则先删除文件和数据，后添加
		if(!(app.getQuality().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.质量手册);
			commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.质量手册,request);
		}
		PrintEquipment pe=new PrintEquipment();
		pe.setCompanyName(companyName);
		pe.setCompanyId(Integer.parseInt(cId));
		printEquipmentService.updateById(pe);
		TestingEquip te=new TestingEquip();
		te.setCompanyName(companyName);
		te.setCompanyId(Integer.parseInt(cId));
		testingEquipService.updateById(te);
		commonsController.delFile(titleNo,CommonsController.申请表);
		app.setCreatedate(new Date());
		applicationFormService.updateApp(app);
		commonController.savePdf(2, app.getId(), request);//生成pdf

		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("申请表");
		//设置路径
		String path = "upload";
		String filePath = path+"/FR/"+app.getTitleno();
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
	
	//复认业务的保存
	@RequestMapping("insert")
	@Transactional
	public String insert(ApplicationForm app,HttpServletRequest request) throws Exception{//申请
		AddLog.addLog(Log.ADD,app.getEnterprisename()+"进行复认申请业务");
		String titleNo=commonController.getNum("FR",app.getBranchId());
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
		//如果提交的有质量手册，则先删除文件和数据，后添加
		if(!(app.getQuality().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.质量手册);
			commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.质量手册,request);
		}
		//更新数据
		app.setTitleno(titleNo);
		app.setCreatedate(new Date());
		applicationFormService.updateApp(app);
		commonController.savePdf(2, app.getId(), request);//生成pdf
		//开启流程实例
		Map<String,Object> activitiMap = new HashMap<>();
		String branchName="分支机构";
		activitiMap.put("company", branchName);
		String objId="Application:"+app.getId();
		runtimeService.startProcessInstanceByKey("rerecognition",objId,activitiMap);
		//根据公司名称查询任务Id
		List<Task> tasks = workflowUtil.findTaskListByName(branchName);
		String taskId=tasks.get(0).getId();
		//完成个人任务
		completePersonlTask(taskId,"无","分中心审核人员","true",13);
		//在查询的时候是查的ys_company表中的数据，因此，在开启流程后，将ys_company表中的status修改
		companyInfoService.setStatus(app.getId(),27); 
		return "recognition/newForm/newForms";
	}
	//复认业务的申请
	@RequestMapping("apply")
	public String apply(int appId,String titleNo,MultipartFile file,HttpSession session,HttpServletRequest request){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		//文件上传
		if(!(file.isEmpty())){
			delFile("fr"+appId);
			uploadFile(file,"fr"+appId,titleNo,request);
		}
		//开启流程实例
		Map<String,Object> activitiMap = new HashMap<>();
		activitiMap.put("company", branchId);
		String objId="Application:"+appId;
		runtimeService.startProcessInstanceByKey("rerecognition",objId,activitiMap);
		//根据公司名称查询任务Id
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);
		String taskId=tasks.get(0).getId();
		//完成个人任务
		completePersonlTask(taskId,"无","分中心审核人员","true",13);
		return "recognition/newForm/newForms";
	}
	//上传复认申请
	@RequestMapping("startApply")
	@ResponseBody
	public int startApply(String taskId,int appId,String titleNo,MultipartFile file,HttpSession session,HttpServletRequest request){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		int userId=activeUser.getUserid();
		ApplicationForm af=applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.IMPORT,"上传'"+af.getEnterprisename()+"'的盖章复认申请表并开启复认业务流程");
		//文件上传
		if(file!=null&&!(file.isEmpty())){
			commonsController.delFile(titleNo,CommonsController.申请表_盖章);
			commonsController.uploadFile(file,titleNo,CommonsController.申请表_盖章,request);
		}
		if("null".equals(taskId) || StringUtils.isBlank(taskId)){//如果taskId不存在，则是新申请的
			//开启流程实例
			Map<String,Object> activitiMap = new HashMap<>();
			activitiMap.put("user", branchId);
			String objId="Recognition:"+appId;
			runtimeService.startProcessInstanceByKey("recognition",objId,activitiMap);
			//根据流程定义id和节点id查询任务Id
			List<Task> tasks = workflowUtil.getTaskByIds("recognition", "xbywsq");
			List<String> bids=workflowUtil.getBussinessIdsByTasks(tasks);
			int i=0;
			for(;i<bids.size();i++){
				int id=Integer.parseInt(bids.get(i));
				if(id==appId)
					break;
			}
			taskId=tasks.get(i).getId();
		}
		//完成任务
		appCommonsController.completeTask(taskId,"", "true", 20);
		return userId;
	}
	//已退回申请的申请
	@RequestMapping("applyReturn")
	public String applyReturn(String taskId,String titleNo,MultipartFile file,HttpServletRequest request){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//文件上传
		if(!(file.isEmpty())){
			delFile("fr"+appId);
			uploadFile(file,"fr"+appId,titleNo,request);
		}
		//完成个人任务
		completePersonlTask(taskId,"无","分中心审核人员","true",13);
		return "recognition/newForm/returnTask";
	}
	@RequestMapping("insertReturn")
	public String insertReturn(ApplicationForm app,HttpServletRequest request){//退回任务的提交。先更新信息，在执行任务
		AddLog.addLog(Log.ADD,app.getEnterprisename()+"进行复认申请业务");
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
		//如果提交的有质量手册，则先删除文件和数据，后添加
		if(!(app.getQuality().isEmpty())){
			delFile(app.getQualityno()+"");
			uploadFile(app.getQuality(),app.getQualityno()+"",titleNo,request);
		}
		app.setCreatedate(new Date());
		applicationFormService.updateApp(app);
		commonController.savePdf(2, app.getId(), request);//生成pdf
		String taskId=app.getTaskId();
		//完成个人任务
		completePersonlTask(taskId,"无","分中心审核人员","true",13);
		return "recognition/newForm/returnTask";
	}
	
	//获取初审退回任务
	@RequestMapping("getReturnTasks")
	@ResponseBody
	public Object getReturnTasks(HttpSession session){//查看被退回的业务。
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		
		//String branchName="分支机构";//获取当前用户所属的分支机构。启动流程实例是也要按分支机构启动
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);//分支机构名称
		tasks=workflowUtil.getTaskByPointName("复认表提交");
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
	
	@RequestMapping("/completePersonlTask")
	@ResponseBody
	public void completePersonlTask(String taskId,String comment,String role,String result,int status){
		//添加评论
		if(!("无".equals(comment))||StringUtils.isNotBlank(comment)){//如果不为无的话这添加评论
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
			map.put("fzjg",names);
		}
		map.put("result", result); 
		WorkflowBean workflowBean = new WorkflowBean();
		workflowBean.setTaskId(taskId);
		workflowUtil.completeTask(workflowBean, map);
	}
@RequestMapping("getter")
@ResponseBody
public Object getter(){
	List<Task> tasks=taskService.createTaskQuery().taskAssignee("中心审核人员").list();
	List<Task> task2=taskService.createTaskQuery().taskCandidateUser("中心审核人员").list();
	return tasks;
}
	@RequestMapping("/climeAndcompleteTask")
	@ResponseBody
	public void climeAndcompleteTask(String taskId,String comment,String role,String result,int status,HttpSession session){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"审核"+af.getEnterprisename()+"的复认业务申请");
		//拾取为个人任务
		String userName=((ActiveUser)session.getAttribute("activeUser")).getRealname();
		taskService.claim(taskId, userName);
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
			map.put("fzjg",names);
		}
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
		if(path!=null){
			File file = new File(path);
			if(file.exists()){//删除文件
				file.delete();
			}
		}
		//删除数据库中的数据
		uploadFileService.deleteByCode(code);
	}
	
	//根据节点名称获取所属分中心任务
	@RequestMapping("getPointTask")
	@ResponseBody
	public Object getPointTask(String pointId,HttpSession session){
		//获取用户的真实名称
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		
		//根据节点的名称获和用户名称取任务
		List<Task> tasks = workflowUtil.getTaskByIds("recognition", pointId);
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		for(ApplicationForm af:afList){
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afList;
	}
	
	//根据节点名称获取所有任务
	@RequestMapping("getAllTask")
	@ResponseBody
	public Object getAllTask(String pointId){
		//根据节点的名称获和用户名称取任务
		List<Task> tasks = workflowUtil.getTaskByIds("recognition", pointId);
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet());
		for(ApplicationForm af:afList){
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afList;
	}
		
	//部署复认流程图
	@RequestMapping("/deploy")
	@ResponseBody
	public void deploy() {
		workflowUtil.deployment("activiti/recognition", "复认业务流程");
		System.out.println("部署成功");
	}
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model) {
		String id1=request.getParameter("id");
		String taskId=request.getParameter("taskId");
		int id=Integer.parseInt(id1);
		ApplicationForm af=applicationFormService.getAppFormById(id); 
		AddLog.addLog(Log.QUERY,"查询'"+af.getEnterprisename()+"'的复认申请信息");
		String titleno=af.getTitleno();
		String path1=uploadFileService.getPath(titleno, CommonsController.营业执照);
		String path2=uploadFileService.getPath(titleno, CommonsController.印刷经营许可证);
		String path3=uploadFileService.getPath(titleno, CommonsController.质量手册);
		String path4=uploadFileService.getPath(titleno, CommonsController.申请表);
		model.addAttribute("id", id);
		model.addAttribute("taskId", taskId);
		model.addAttribute("titleNo",af.getTitleno());
		model.addAttribute("comId",af.getCompanyId());
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
		return "recognition/firstTrial/application_edit";
	}
	//办理复认业务申请
	@RequestMapping("reApply")
	public String reApply(HttpServletRequest request,Model model) {
		return "recognition/newForm/reApply";
	}	
	//删除复认业务
	@RequestMapping("deleteReApp")
	@ResponseBody
	public void deleteReApp(int id,String taskId){
		if(taskId!=null&&!"null".equals(taskId)){
			workflowUtil.deleteProcess(taskId);
//			id=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		}
		ApplicationForm af=applicationFormService.getAppFormById(id);
		String companyName=af.getEnterprisename();
		AddLog.addLog(Log.DELETE,"删除'"+companyName+"'的复认申请信息");
		String titleNo=af.getTitleno();
		commonsController.delFiles(titleNo,CommonsController.印刷经营许可证);
		commonsController.delFiles(titleNo,CommonsController.营业执照);
		commonsController.delFiles(titleNo,CommonsController.质量手册);
		commonsController.delFiles(titleNo,CommonsController.申请表);
		CompanyInfo com=companyInfoService.getComById(af.getCompanyId());
		com.setStatus(17);
		String substring = titleNo.substring(0,2);
		com.setTitleno(titleNo.replace(substring, "XB"));
		companyInfoService.update(com);
		printEquipmentService.deleteByCompanyName(companyName);
		testingEquipService.deleteByComName(companyName);
		applicationFormService.deleteById(id);
	}
}
