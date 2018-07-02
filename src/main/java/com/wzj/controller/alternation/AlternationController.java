package com.wzj.controller.alternation;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wzj.DTO.AppDTO;
import com.wzj.common.Obj2Obj;
import com.wzj.common.PdfUtil;
import com.wzj.controller.CommonsController;
import com.wzj.controller.DivisionRegionController;
import com.wzj.controller.application.CommonController;
import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.CompanyInfo;
import com.wzj.pojo.Division;
import com.wzj.pojo.FormChange;
import com.wzj.pojo.Log;
import com.wzj.pojo.SysUser;
import com.wzj.pojo.UploadFile;
import com.wzj.pojo.WorkflowBean;
import com.wzj.service.alternation.impl.FormChangeService;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.CompanyInfoService;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.service.impl.BranchCenterService;
import com.wzj.service.impl.SysRoleService;
import com.wzj.util.AddLog;
import com.wzj.util.UploadFileUtil;
import com.wzj.util.WorkflowUtil;

@Controller
@RequestMapping("alternation")
public class AlternationController {
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private FormChangeService formChangeService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private BranchCenterService branchCenterService;
	@Autowired
	private CommonController commonController;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private DivisionRegionController divisionRegionController;
	@Autowired
	private PdfUtil pdfUtil;
	@Autowired
	private CommonsController commonsController;
	
	//跳转变更业务申请下的已保存申请
	@RequestMapping("toNewForms")
	public String toNewForms(){
		AddLog.addLog(Log.QUERY,"查询所有待变更业务申请信息");
		return "alternation/newForm/newForms";
	}
	@RequestMapping("back")
	@ResponseBody
	public void back(String taskId){
		workflowUtil.setAssigneeTask(taskId);
	}
	//获取所有符合变更业务的信息
	@RequestMapping("getNewForms")
	@ResponseBody
	public Object getNewForms(HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		AppDTO appdto=new AppDTO();
		appdto.setStatus(0);
		//List<FormChange> news=formChangeService.getOngoingByStatus(appdto);
		List<FormChange> news=formChangeService.getOngoingByStatus(appdto,activeUser.getRamusCenter());
		for(FormChange fc:news){
			fc.setTaskId("0");
		}
		
		List<FormChange> back=getReturnTasks(session);
		
		if(activeUser.getRamusCenter().equals("0000")){
			news.addAll(back);
		}else{
			List<FormChange> branch=new LinkedList<>();
			for(FormChange fc:back){
				if(fc.getOffshootorganiz().equals(activeUser.getRamusCenter()))
					branch.add(fc);
			}
			news.addAll(branch);
		}
		for(int i=0;i<news.size();i++){
			String branchName=((Division)divisionRegionController.getDivisionByCode(
					news.get(i).getOffshootorganiz())).getDivisionname();
			news.get(i).setBranchName(branchName);
		}
		return news;
	}
	/*public Object getNewForms(HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		//新申请业务完成
		List<CompanyInfo> com1=companyInfoService.getComByStatus(11,branchId);
		//复认业务完成
		List<CompanyInfo> com2=companyInfoService.getComByStatus(22,branchId);
		//变更业务完成
		List<CompanyInfo> com3=companyInfoService.getComByStatus(5,branchId);
		com1.addAll(com2);
		com1.addAll(com3);
		//获得需要变更的业务信息
		List<CompanyInfo> com1=companyInfoService.getComByStatus(24,branchId);
		Obj2Obj obj=new Obj2Obj();
		for(int i=0;i<com1.size();i++){
			CompanyInfo com=com1.get(i);
			ApplicationForm af= obj.Com2App(com);
			//将查询出来的信息保存到ys_title中
			af.setCreatedate(new Date());
			af.setStatus(23);//修改状态
			af.setCompanyId(com.getId());
			applicationFormService.insertApp(af);
			//修改ys_company表中数据的状态
			companyInfoService.setStatus(com.getId(),23);//业务审核中
		}
		List<ApplicationForm> app1=applicationFormService.getAppByStatus(23,branchId);
		//return app1;
		//以下为添加代码，添加退回的任务
		List<ApplicationForm> app2=new ArrayList<ApplicationForm>();
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);//分支机构名称
		tasks=workflowUtil.getTaskByPointName("变更申请表");
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<FormChange> afList= new ArrayList<FormChange>();
		afList = formChangeService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		for(int i=0;i<afList.size();i++){
			//根据业务id查找任务id，并将任务id设置到业务id中
			FormChange af = afList.get(i);
			String str = af.getId()+"";
			String titleno=applicationFormService.getAppFormById(af.getPid()).getTitleno();
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
			af.setTitleno(titleno);
			ApplicationForm app=obj.Change2App(af);
			app.setTaskId(tid);
			app.setTitleno(titleno);
			app2.add(app);
		}
		app1.addAll(app2);
		return app1;
	}*/
	
	//变更业务的修改
	@RequestMapping("toReturnForm")
	public String toReturnForm(String taskId,String id,Model model){
		int appId=Integer.parseInt(id);
		ApplicationForm af=applicationFormService.getAppFormById(appId); 
		AddLog.addLog(Log.MODIFY,"修改'"+af.getEnterprisename()+"'的变更申请信息");
		model.addAttribute("taskId",taskId);
		model.addAttribute("id",id);
		return "alternation/newForm/returnForm";
	}
	
	//调到修改页面
	@RequestMapping("toFormUpdate")
	public String toFormUpdate(String taskId,String id,Model model){
		int changeId=Integer.parseInt(id);
		FormChange fc=formChangeService.getChangeFormById(changeId); 
		AddLog.addLog(Log.QUERY,"查看'"+fc.getCompanynameOld()+"'的变更申请信息");
		model.addAttribute("taskId",taskId);
		model.addAttribute("id",id);
		return "alternation/newForm/formUpdate";
	}
	
	//变更业务的退回页面
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		AddLog.addLog(Log.QUERY,"查询所有退回的变更业务申请信息");
		return "alternation/newForm/returnTask";
	}
	
	@RequestMapping("saveFormChange")
	@ResponseBody
	public void saveFormChange(FormChange form,HttpSession session){
		formChangeService.update(form);
		FormChange f2=formChangeService.getChangeFormById(form.getId());
		AddLog.addLog(Log.MODIFY,"保存'"+f2.getCompanynameOld()+"'的变更申请信息");
		//return "alternation/newForm/returnTask";
		int cid=f2.getPid();
		CompanyInfo cInfo=companyInfoService.getComById(cid);
		int certNo=cInfo.getSerial();//证书编号
		//先删除
		commonsController.delFile(f2.getTitleno(),CommonsController.申请表);
		//设置路径
		String path1=session.getServletContext().getRealPath("");
		String path2="upload/BG/"+form.getTitleno();
		String filePath = path1+"/"+path2;
		String fileName = form.getTitleno()+"申请"+".pdf";
		File f=new File(filePath);
		if(!f.exists())f.mkdirs();
		filePath = filePath+"/"+fileName;
		//生成pdf
		pdfUtil.createPDFAlteration(f2,filePath,certNo);
		//将pdf信息保存导数据库
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("申请表");
		uFile.setUprul(path2+"/"+fileName);
		File file=new File(filePath);
		uFile.setUpsize(file.length()/1024+"kb");
		uFile.setCode(form.getTitleno());
		uFile.setAvailability(true);
		uFile.setDescribeId("4");
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
		//return "alternation/newForm/newForms";
	}
	
	@RequestMapping("updateFormChange")
	public String updateFormChange(FormChange form,HttpSession session){
		/*formChangeService.update(form);
		completePersonlTask(form.getTaskId(),"无","分中心审核人员","true",1);
		return "alternation/newForm/returnTask";*/
		String taskId=form.getTaskId();
		if("0".equals(taskId)){//开启流程实例，办理任务
			Map<String,Object> activitiMap = new HashMap<>();
			ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
			String branchId=activeUser.getRamusCenter();
			int altId=form.getId();
			activitiMap.put("company",branchId);
			String objId="Alternation:"+altId;
			runtimeService.startProcessInstanceByKey("changeBusiness",objId,activitiMap);
			//根据流程定义id和节点id查询任务Id
			List<Task> tasks = workflowUtil.getTaskByIds("changeBusiness", "usertask1");
			List<String> bids=workflowUtil.getBussinessIdsByTasks(tasks);
			int i=0;
			for(;i<bids.size();i++){
				int id=Integer.parseInt(bids.get(i));
				if(id==altId)
					break;
			}
			taskId=tasks.get(i).getId();
		}
		//拾取并完成任务
		completePersonlTask(taskId,"","分中心审核人员","true",1);
		return "alternation/newForm/newForms";
	}
	@RequestMapping("startApply")
	@ResponseBody
	public String startApply(MultipartFile file,String taskId,int altId,String titleNo,HttpSession session,HttpServletRequest request){
		if("0".equals(taskId)){//开启流程实例，办理任务
			Map<String,Object> activitiMap = new HashMap<>();
			ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
			String branchId=activeUser.getRamusCenter();
			activitiMap.put("company",branchId);
			String objId="Alternation:"+altId;
			runtimeService.startProcessInstanceByKey("changeBusiness",objId,activitiMap);
			//根据流程定义id和节点id查询任务Id
			List<Task> tasks = workflowUtil.getTaskByIds("changeBusiness", "usertask1");
			List<String> bids=workflowUtil.getBussinessIdsByTasks(tasks);
			int i=0;
			for(;i<bids.size();i++){
				int id=Integer.parseInt(bids.get(i));
				if(id==altId)
					break;
			}
			taskId=tasks.get(i).getId();
			int appId=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			FormChange fc=formChangeService.getChangeFormById(appId);
			AddLog.addLog(Log.IMPORT,"上传'"+fc.getCompanynameOld()+"'的变更业务盖章申请表并开启变更业务流程");
		}
		//文件上传
		if(!(file.isEmpty())){
			commonsController.delFile(titleNo,CommonsController.申请表_盖章);
			commonsController.uploadFile(file,titleNo,CommonsController.申请表_盖章,request);
		}
		//拾取并完成任务
		completePersonlTask(taskId,"","分中心审核人员","true",1);
//		return "alternation/newForm/newForms";
		return "1";
	}
	//插入
	@RequestMapping("insert")
	public String Insert(FormChange form,HttpSession session){
		AddLog.addLog(Log.ADD,"'"+form.getCompanynameNew()+"'进行变更业务申请");
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		form.setCreatedate(new Date());
		form.setOffshootorganiz(branchId);
		formChangeService.insertFormChange(form);
		//在插入语句中配置useGeneratedKeys="true" keyProperty="id"才能获取插入的id
		int id=form.getId();
		//开启流程实例
		Map<String,Object> activitiMap = new HashMap<>();
		//String companyName=form.getCompanynameOld();
		activitiMap.put("company",branchId);
		String objId="Application:"+id;
		runtimeService.startProcessInstanceByKey("changeBusiness",objId,activitiMap);
		//根据公司名称查询任务Id
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);
		String taskId=tasks.get(0).getId();
		//完成个人任务
		completePersonlTask(taskId,"无","分中心审核人员","true",1);
		//修改ys_title中对应的状态及titleId
		ApplicationForm app=new ApplicationForm();

		app.setId(form.getPid());
		app.setStatus(24);
		app.setTitleno(commonController.getNum("BG", branchId));
		applicationFormService.updateApp(app);
		return "alternation/newForm/newForms";
	}
	//保存变更申请
	@RequestMapping("saveAlterApp")
	@ResponseBody
	public void insertAlterApp(FormChange form,HttpSession session,HttpServletRequest request){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		String titleNo=commonController.getNum("BG",branchId);
		form.setCreatedate(new Date());
		form.setTitleno(titleNo);
		formChangeService.update(form);
		FormChange fc=formChangeService.getChangeFormById(form.getId());
		//修改ys_title中对应的状态及titleId
		ApplicationForm app=new ApplicationForm();
		app.setId(fc.getPid());
		app.setStatus(24);
		app.setTitleno(titleNo);
		applicationFormService.updateApp(app);
		
		commonController.savebgApp(1, form.getId(), request);//生成pdf
	}
	//修改
	@RequestMapping("update")
	public String update(FormChange form,HttpSession session){
		AddLog.addLog(Log.ADD,"'"+form.getCompanynameNew()+"'进行变更业务修改");
		FormChange fc=formChangeService.getChangeFormByPId(form.getPid());
		form.setId(fc.getId());
		formChangeService.update(form);
		return "alternation/firstTrial/tasks";
	}
	//完成个人任务
	@RequestMapping("/completePersonlTask")
	@ResponseBody
	public void completePersonlTask(String taskId,String comment,String role,String result,int status){
		//添加评论
		if(StringUtils.isNotBlank(comment)){//如果不为无的话这添加评论
			workflowUtil.addComment(taskId,comment);
		}
		
		//修改业务状态
		if(status>0){
			int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			formChangeService.updateStatus(id,status);
		}
		
		//办理业务
		Map<String,Object> map = new HashMap<>();
		String names="";
		List<SysUser> admins = sysRoleService.findUsersByRoleName("中心管理人员");
		for(SysUser user:admins)
			names+=user.getUsername()+",";
		List<SysUser> branchs = sysRoleService.findUsersByRoleName("分支机构管理人员");
		for(SysUser user:branchs)
			names+=user.getUsername()+",";
		if(StringUtils.isNotBlank(role)){
			List<SysUser> users = sysRoleService.findUsersByRoleName(role);
			for(SysUser user:users){
				names+=user.getUsername()+",";
			}
			names=names.substring(0,names.length()-1);
		}
		map.put("fzjg",names);
		map.put("result", result); 
		WorkflowBean workflowBean = new WorkflowBean();
		workflowBean.setTaskId(taskId);
		workflowUtil.completeTask(workflowBean, map);
	}
		
	//查看内容
	@RequestMapping("getChange")
	@ResponseBody
	public Object getChange(int id){
		FormChange change=formChangeService.getChangeFormById(id);
			return change;
	}
	
	//根据节点名称和候选人获取本中心的组任务
	@RequestMapping("getTasks")
	@ResponseBody
	public Object getTasks(String pointName,int[] status,HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		//获取用户的真实名称
		String userName=activeUser.getRealname();
		
		//根据节点的名称获和用户名称取任务
		List<Task> tasks=workflowUtil.findGroupTaskListByName(pointName,userName);
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		//还要根据分支机构查询相关的id；
		
		List<FormChange> afList= formChangeService.findBusinessByTasks(busAndTaskId.keySet());
		List<FormChange> forms= new ArrayList<FormChange>();
		if(activeUser.getRamusCenter().equals("0000")){
			for(int i=0;i<afList.size();i++){
				//根据业务id查找任务id，并将任务id设置到业务id中
				FormChange af = afList.get(i);
				//根据分支机构id获得分支机构名称
				Division divisionByCode = branchCenterService.getDivisionByCode(af.getOffshootorganiz());
				String branchName=divisionByCode.getDivisionname();
				for(int t=0;t<status.length;t++){
					if(status[t]==af.getStatus()){
						String str = af.getId()+"";
						String tid=busAndTaskId.get(str);
						//String titleno=applicationFormService.getAppFormById(af.getPid()).getTitleno();
						af.setTaskId(tid);//设置taskId
						af.setBranchName(branchName);
						//af.setTitleno(titleno);
						forms.add(af);
						break;
					}
				}
			}
		}else{
			for(int i=0;i<afList.size();i++){
				//根据业务id查找任务id，并将任务id设置到业务id中
				FormChange af = afList.get(i);
				if(activeUser.getRamusCenter().equals(af.getOffshootorganiz())){
					//根据分支机构id获得分支机构名称
					Division divisionByCode = branchCenterService.getDivisionByCode(af.getOffshootorganiz());
					String branchName=divisionByCode.getDivisionname();
					for(int t=0;t<status.length;t++){
						if(status[t]==af.getStatus()){
							String str = af.getId()+"";
							String tid=busAndTaskId.get(str);
							af.setTaskId(tid);//设置taskId
							af.setBranchName(branchName);
							forms.add(af);
							break;
						}
					}
				}
			}
		}
		return forms;
	}
	
	//根据节点名称和候选人获取本中心的组任务
	@RequestMapping("getAllTask")
	@ResponseBody
	public Object getAllTask(String pointName,int[] status,HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		//获取用户的真实名称
		String userName=activeUser.getRealname();
		//根据节点的名称获和用户名称取任务
		List<Task> tasks=workflowUtil.findGroupTaskListByName(pointName,userName);
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		//还要根据分支机构查询相关的id；
		
		List<FormChange> afList= formChangeService.findBusinessByTasks(busAndTaskId.keySet());
		List<FormChange> forms= new ArrayList<FormChange>();
		for(int i=0;i<afList.size();i++){
			//根据业务id查找任务id，并将任务id设置到业务id中
			FormChange af = afList.get(i);
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
	
	@RequestMapping("view")
	public String view(int id,Model model) {
		FormChange fc=formChangeService.getChangeFormById(id); 
		AddLog.addLog(Log.QUERY,"查看'"+fc.getCompanynameOld()+"'的变更业务申请信息");
		model.addAttribute("id",id);
		return "alternation/newForm/form_view";
	}
	
	/*@RequestMapping("viewInfo")
	public String viewInfo(int id,Model model){
		FormChange fc=formChangeService.getChangeFormById(id);
		CompanyInfo ci=companyInfoService.getComById(fc.getPid()); 
		AddLog.addLog(Log.QUERY,"查询"+ci.getEnterprisename()+"的申请信息");
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute("id",id);
		return "application/common/application_view";
	}*/
	
	@RequestMapping("trial")
	public String trial(int id,String taskId,Model model) {
		FormChange fc=formChangeService.getChangeFormById(id); 
		AddLog.addLog(Log.QUERY,"查看'"+fc.getCompanynameOld()+"'的变更业务申请信息");
		model.addAttribute("id",id);
		model.addAttribute("taskId",taskId);
		return "alternation/firstTrial/trial";
	}
	
	//获取初审退回任务
	@RequestMapping("getReturnTasks")
	@ResponseBody
	public List<FormChange> getReturnTasks(HttpSession session){//查看被退回的业务。
		String branchId=((ActiveUser)session.getAttribute("activeUser")).getRamusCenter();
		//String branchName="分支机构";//获取当前用户所属的分支机构。启动流程实例是也要按分支机构启动
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);//分支机构名称
		tasks=workflowUtil.getTaskByPointName("变更申请表");
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<FormChange> afList= new ArrayList<FormChange>();
		afList = formChangeService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		for(int i=0;i<afList.size();i++){
			//根据业务id查找任务id，并将任务id设置到业务id中
			FormChange af = afList.get(i);
			String str = af.getId()+"";
			//String titleno=applicationFormService.getAppFormById(af.getPid()).getTitleno();
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
			//af.setTitleno(titleno);
		}
		return afList;
	}
	
	//根据id获取修改表内容
	@RequestMapping("getForm")
	@ResponseBody
	public Object getForm(int id){
		return formChangeService.getForm(id);
	}
	
	//拾取组任务并办理跟人任务
	@RequestMapping("/climeAndcompleteTask")
	@ResponseBody
	public void climeAndcompleteTask(String taskId,String comment,String role,String result,int status,HttpSession session){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//添加日志
		FormChange formchange=formChangeService.getChangeFormById(id);
		AddLog.addLog(Log.ADD,"编码中心核准对'"+formchange.getCompanynameOld()+"'变更业务申请");
		//拾取为个人任务
		String userName=((ActiveUser)session.getAttribute("activeUser")).getRealname();
		taskService.claim(taskId, userName);
		//添加评论
		if(!("无".equals(comment))&&StringUtils.isNotBlank(comment)){//如果不为无的话这添加评论
			workflowUtil.addComment(taskId,comment);
		}
		
		//修改业务状态
		if(status>0){
			formChangeService.updateStatus(id,status);
		}
		
		//办理业务
		Map<String,Object> map = new HashMap<>();
		String names="";
		List<SysUser> admins = sysRoleService.findUsersByRoleName("中心管理人员");
		for(SysUser user:admins)
			names+=user.getUsername()+",";
		List<SysUser> branchs = sysRoleService.findUsersByRoleName("分支机构管理人员");
		for(SysUser user:branchs)
			names+=user.getUsername()+",";
		if(StringUtils.isNotBlank(role)){
			List<SysUser> users = sysRoleService.findUsersByRoleName(role);
			for(SysUser user:users){
				names+=user.getUsername()+",";
			}
			names=names.substring(0,names.length()-1);
		}
		map.put("fzjg",names);
		map.put("result", result); 
		WorkflowBean workflowBean = new WorkflowBean();
		workflowBean.setTaskId(taskId);
		workflowUtil.completeTask(workflowBean, map);
		
		if(status==5){//如果变更业务完成，则修改ys_title表中的记录
			FormChange fc=formChangeService.getChangeFormById(id);
			//获取titleId
			Obj2Obj obj=new Obj2Obj();
			ApplicationForm af=obj.Change2App(fc);
			//applicationFormService.updateApp(af);//修改ys_title
			//修改ys_company中的基本信息
			CompanyInfo info=obj.App2Com(af);
			info.setId(af.getId());
			//获取titleId
			//String titleId=applicationFormService.getAppFormById(af.getId()).getTitleno();
			//info.setTitleno(titleId);
			info.setStatus(35);
			companyInfoService.update(info);
		}
	}
	
	//部署变更业务流程图
	@RequestMapping("deploy")
	@ResponseBody
	public void deploy(){
		workflowUtil.deployment("activiti/changeBusiness", "变更业务流程");
		System.out.println("部署成功");
	}
	
	@RequestMapping("getOngoing")
	@ResponseBody
	public Object getOngoing(int begin,int end,String status,HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String bId=activeUser.getRamusCenter();
		AppDTO appdto=new AppDTO(begin, end);
		appdto.setBranchId(bId);
		List<FormChange> formchange;
		if(bId.equals("0000")){
			if(status.equals("all")||status.equals("null")){
				formchange=formChangeService.getOngoingAll(appdto);
			}else{
				appdto.setStatus(Integer.parseInt(status));
				formchange=formChangeService.getOngoingByStatus(appdto);
			}
		}else{
			if(status.equals("all")||status.equals("null")){
				formchange=formChangeService.getOngoing(appdto);
			}else{
				appdto.setStatus(Integer.parseInt(status));
				formchange=formChangeService.getOngoingByBranch(appdto);
			}
		}
//		 fc=new FormChange();
		String branchId="";
		String branchName="";
		int pid=0;
		Division divisionByCode = null;
		for(int i=0;i<formchange.size();i++){
			FormChange fc=formchange.get(i);
			pid=fc.getPid();
			branchId=fc.getOffshootorganiz();
			divisionByCode = branchCenterService.getDivisionByCode(branchId);
			branchName=divisionByCode.getDivisionname();
			fc.setBranchName(branchName);
		}
		return formchange;
	}
	//变更业务的初审
	@RequestMapping("edit")
	public String edit(String taskId,String id,Model model){
		int appId=Integer.parseInt(id);
		ApplicationForm af=applicationFormService.getAppFormById(appId); 
		AddLog.addLog(Log.MODIFY,"修改'"+af.getEnterprisename()+"'的变更申请信息");
		model.addAttribute("taskId",taskId);
		model.addAttribute("id",id);
		return "alternation/firstTrial/alterForm_edit";
	}
	//从企业信息到办理变更业务申请
	@RequestMapping("alterApp")
	public String alterApply(String taskId,String id,Model model){
		//id是企业编号
		int cid=Integer.parseInt(id);
		ApplicationForm af=applicationFormService.getLastAppBycid(cid); 
		AddLog.addLog(Log.MODIFY,"获得'"+af.getEnterprisename()+"'的变更申请信息");
		FormChange fc=formChangeService.getChangeFormByPId(af.getId());
		model.addAttribute("taskId",taskId);
		model.addAttribute("id",fc.getId());
		return "alternation/newForm/alterForm";
	}
	//办理变更业务申请
	@RequestMapping("toAlterApp")
	public String toAalterApply(){
		return "alternation/newForm/alterForm";
	}
	//获取新保存申请表的流水号
	@RequestMapping("getParam")
	@ResponseBody
	public ApplicationForm getParam(HttpServletRequest request){//保存,Model model
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		String titleNo1=commonController.getNum("BG",branchId);
		String one=titleNo1.substring(titleNo1.length()-2, titleNo1.length());
		int two=0;
		String titleNo="";
		if(one.substring(0,one.length()-1).equals("0")){
			two=Integer.parseInt(one.substring(1))-1;
			titleNo=titleNo1.substring(0, titleNo1.length()-2)+"0"+String.valueOf(two);
		}else{
			two=Integer.parseInt(one)-1;
			titleNo=titleNo1.substring(0, titleNo1.length()-2)+String.valueOf(two);
		}
		ApplicationForm applicationForm=applicationFormService.getLastAppFormBytitleNo(titleNo);
		applicationForm.setTitleno(titleNo);
		return applicationForm;
	}
	//上传变更申请
	/*@RequestMapping("startApply")
	@ResponseBody
	public void startApply(int changeId,String titleNo,MultipartFile file,HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		FormChange fc=formChangeService.getChangeFormByPId(changeId);
		//文件上传
		if(!(file.isEmpty())){
			delFile("bg"+fc.getId());
			uploadFile(file,"bg"+fc.getId(),titleNo);
		}
	//开启流程实例
	Map<String,Object> activitiMap = new HashMap<>();
	//String companyName=form.getCompanynameOld();
	activitiMap.put("company",branchId);
	String objId="Application:"+fc.getId();
	runtimeService.startProcessInstanceByKey("changeBusiness",objId,activitiMap);
	//根据公司名称查询任务Id
	List<Task> tasks = workflowUtil.findTaskListByName(branchId);
	String taskId=tasks.get(0).getId();
	//完成个人任务
	completePersonlTask(taskId,"无","分中心审核人员","true",1);
	
	}*/
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
	
	@RequestMapping("toView")
	public String toView(String titleNo,String taskId,int id,ModelMap model){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		FormChange fc = formChangeService.getChangeFormById(appId);
		AddLog.addLog(Log.QUERY,"查看'"+fc.getCompanynameOld()+"'的变更业务申请信息");
		model.addAttribute("taskId", taskId);
		model.addAttribute("id", id);
		model.addAttribute("item", titleNo);
		return "common/viewBG";
	}
	
	//通过提交不执行，只保存taskId和评论
		@RequestMapping("/saveComment")
		public String saveComment(String taskId,String comment){
			int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			FormChange fc = formChangeService.getChangeFormById(appId);
			AddLog.addLog(Log.ADD,"分中心审批'"+fc.getCompanynameOld()+"'的变更业务申请通过");
			//添加评论
			if(!("无".equals(comment))&&StringUtils.isNotBlank(comment)){//如果不为无的话这添加评论
				workflowUtil.addComment(taskId,comment);
			}
			return "alternation/issueReport/sendMessage";
		}
		
		//通过提交不执行，只保存taskId和评论
		@RequestMapping("/saveCommentZX")
		public String saveCommentZX(String taskId,String comment){
			int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			FormChange fc = formChangeService.getChangeFormById(appId);
			AddLog.addLog(Log.ADD,"编码中心核准'"+fc.getCompanynameOld()+"'的变更业务申请通过");
			//添加评论
			if(!("无".equals(comment))&&StringUtils.isNotBlank(comment)){//如果不为无的话这添加评论
				workflowUtil.addComment(taskId,comment);
			}
			return "alternation/issueReport/tasks";
		}
		//删除变更业务
		@RequestMapping("deleteAlterApp")
		@ResponseBody
		public void deleteAlterApp(int id,String taskId){
			if(!"0".equals(taskId)){
				workflowUtil.deleteProcess(taskId);
			}
			FormChange changeFormById = formChangeService.getChangeFormById(id);
			String companyName=changeFormById.getCompanynameOld();
			AddLog.addLog(Log.DELETE,"删除'"+companyName+"'的变更申请信息");
			String titleNo=changeFormById.getTitleno();
			commonsController.delFiles(titleNo,CommonsController.申请表);
			CompanyInfo com=companyInfoService.getComById(changeFormById.getPid());
			com.setStatus(17);
			String substring = titleNo.substring(0,2);
			com.setTitleno(titleNo.replace(substring, "XB"));
			companyInfoService.update(com);
			formChangeService.deleteById(id);
		}
}
