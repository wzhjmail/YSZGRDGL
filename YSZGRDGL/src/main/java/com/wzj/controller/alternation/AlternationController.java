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
	
	//��ת���ҵ�������µ��ѱ�������
	@RequestMapping("toNewForms")
	public String toNewForms(){
		AddLog.addLog(Log.QUERY,"��ѯ���д����ҵ��������Ϣ");
		return "alternation/newForm/newForms";
	}
	@RequestMapping("back")
	@ResponseBody
	public void back(String taskId){
		workflowUtil.setAssigneeTask(taskId);
	}
	//��ȡ���з��ϱ��ҵ�����Ϣ
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
		//������ҵ�����
		List<CompanyInfo> com1=companyInfoService.getComByStatus(11,branchId);
		//����ҵ�����
		List<CompanyInfo> com2=companyInfoService.getComByStatus(22,branchId);
		//���ҵ�����
		List<CompanyInfo> com3=companyInfoService.getComByStatus(5,branchId);
		com1.addAll(com2);
		com1.addAll(com3);
		//�����Ҫ�����ҵ����Ϣ
		List<CompanyInfo> com1=companyInfoService.getComByStatus(24,branchId);
		Obj2Obj obj=new Obj2Obj();
		for(int i=0;i<com1.size();i++){
			CompanyInfo com=com1.get(i);
			ApplicationForm af= obj.Com2App(com);
			//����ѯ��������Ϣ���浽ys_title��
			af.setCreatedate(new Date());
			af.setStatus(23);//�޸�״̬
			af.setCompanyId(com.getId());
			applicationFormService.insertApp(af);
			//�޸�ys_company�������ݵ�״̬
			companyInfoService.setStatus(com.getId(),23);//ҵ�������
		}
		List<ApplicationForm> app1=applicationFormService.getAppByStatus(23,branchId);
		//return app1;
		//����Ϊ��Ӵ��룬����˻ص�����
		List<ApplicationForm> app2=new ArrayList<ApplicationForm>();
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);//��֧��������
		tasks=workflowUtil.getTaskByPointName("��������");
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<FormChange> afList= new ArrayList<FormChange>();
		afList = formChangeService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		for(int i=0;i<afList.size();i++){
			//����ҵ��id��������id����������id���õ�ҵ��id��
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
	
	//���ҵ����޸�
	@RequestMapping("toReturnForm")
	public String toReturnForm(String taskId,String id,Model model){
		int appId=Integer.parseInt(id);
		ApplicationForm af=applicationFormService.getAppFormById(appId); 
		AddLog.addLog(Log.MODIFY,"�޸�'"+af.getEnterprisename()+"'�ı��������Ϣ");
		model.addAttribute("taskId",taskId);
		model.addAttribute("id",id);
		return "alternation/newForm/returnForm";
	}
	
	//�����޸�ҳ��
	@RequestMapping("toFormUpdate")
	public String toFormUpdate(String taskId,String id,Model model){
		int changeId=Integer.parseInt(id);
		FormChange fc=formChangeService.getChangeFormById(changeId); 
		AddLog.addLog(Log.QUERY,"�鿴'"+fc.getCompanynameOld()+"'�ı��������Ϣ");
		model.addAttribute("taskId",taskId);
		model.addAttribute("id",id);
		return "alternation/newForm/formUpdate";
	}
	
	//���ҵ����˻�ҳ��
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		AddLog.addLog(Log.QUERY,"��ѯ�����˻صı��ҵ��������Ϣ");
		return "alternation/newForm/returnTask";
	}
	
	@RequestMapping("saveFormChange")
	@ResponseBody
	public void saveFormChange(FormChange form,HttpSession session){
		formChangeService.update(form);
		FormChange f2=formChangeService.getChangeFormById(form.getId());
		AddLog.addLog(Log.MODIFY,"����'"+f2.getCompanynameOld()+"'�ı��������Ϣ");
		//return "alternation/newForm/returnTask";
		int cid=f2.getPid();
		CompanyInfo cInfo=companyInfoService.getComById(cid);
		int certNo=cInfo.getSerial();//֤����
		//��ɾ��
		commonsController.delFile(f2.getTitleno(),CommonsController.�����);
		//����·��
		String path1=session.getServletContext().getRealPath("");
		String path2="upload/BG/"+form.getTitleno();
		String filePath = path1+"/"+path2;
		String fileName = form.getTitleno()+"����"+".pdf";
		File f=new File(filePath);
		if(!f.exists())f.mkdirs();
		filePath = filePath+"/"+fileName;
		//����pdf
		pdfUtil.createPDFAlteration(f2,filePath,certNo);
		//��pdf��Ϣ���浼���ݿ�
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("�����");
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
		completePersonlTask(form.getTaskId(),"��","�����������Ա","true",1);
		return "alternation/newForm/returnTask";*/
		String taskId=form.getTaskId();
		if("0".equals(taskId)){//��������ʵ������������
			Map<String,Object> activitiMap = new HashMap<>();
			ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
			String branchId=activeUser.getRamusCenter();
			int altId=form.getId();
			activitiMap.put("company",branchId);
			String objId="Alternation:"+altId;
			runtimeService.startProcessInstanceByKey("changeBusiness",objId,activitiMap);
			//�������̶���id�ͽڵ�id��ѯ����Id
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
		//ʰȡ���������
		completePersonlTask(taskId,"","�����������Ա","true",1);
		return "alternation/newForm/newForms";
	}
	@RequestMapping("startApply")
	@ResponseBody
	public String startApply(MultipartFile file,String taskId,int altId,String titleNo,HttpSession session,HttpServletRequest request){
		if("0".equals(taskId)){//��������ʵ������������
			Map<String,Object> activitiMap = new HashMap<>();
			ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
			String branchId=activeUser.getRamusCenter();
			activitiMap.put("company",branchId);
			String objId="Alternation:"+altId;
			runtimeService.startProcessInstanceByKey("changeBusiness",objId,activitiMap);
			//�������̶���id�ͽڵ�id��ѯ����Id
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
			AddLog.addLog(Log.IMPORT,"�ϴ�'"+fc.getCompanynameOld()+"'�ı��ҵ�����������������ҵ������");
		}
		//�ļ��ϴ�
		if(!(file.isEmpty())){
			commonsController.delFile(titleNo,CommonsController.�����_����);
			commonsController.uploadFile(file,titleNo,CommonsController.�����_����,request);
		}
		//ʰȡ���������
		completePersonlTask(taskId,"","�����������Ա","true",1);
//		return "alternation/newForm/newForms";
		return "1";
	}
	//����
	@RequestMapping("insert")
	public String Insert(FormChange form,HttpSession session){
		AddLog.addLog(Log.ADD,"'"+form.getCompanynameNew()+"'���б��ҵ������");
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		form.setCreatedate(new Date());
		form.setOffshootorganiz(branchId);
		formChangeService.insertFormChange(form);
		//�ڲ������������useGeneratedKeys="true" keyProperty="id"���ܻ�ȡ�����id
		int id=form.getId();
		//��������ʵ��
		Map<String,Object> activitiMap = new HashMap<>();
		//String companyName=form.getCompanynameOld();
		activitiMap.put("company",branchId);
		String objId="Application:"+id;
		runtimeService.startProcessInstanceByKey("changeBusiness",objId,activitiMap);
		//���ݹ�˾���Ʋ�ѯ����Id
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);
		String taskId=tasks.get(0).getId();
		//��ɸ�������
		completePersonlTask(taskId,"��","�����������Ա","true",1);
		//�޸�ys_title�ж�Ӧ��״̬��titleId
		ApplicationForm app=new ApplicationForm();

		app.setId(form.getPid());
		app.setStatus(24);
		app.setTitleno(commonController.getNum("BG", branchId));
		applicationFormService.updateApp(app);
		return "alternation/newForm/newForms";
	}
	//����������
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
		//�޸�ys_title�ж�Ӧ��״̬��titleId
		ApplicationForm app=new ApplicationForm();
		app.setId(fc.getPid());
		app.setStatus(24);
		app.setTitleno(titleNo);
		applicationFormService.updateApp(app);
		
		commonController.savebgApp(1, form.getId(), request);//����pdf
	}
	//�޸�
	@RequestMapping("update")
	public String update(FormChange form,HttpSession session){
		AddLog.addLog(Log.ADD,"'"+form.getCompanynameNew()+"'���б��ҵ���޸�");
		FormChange fc=formChangeService.getChangeFormByPId(form.getPid());
		form.setId(fc.getId());
		formChangeService.update(form);
		return "alternation/firstTrial/tasks";
	}
	//��ɸ�������
	@RequestMapping("/completePersonlTask")
	@ResponseBody
	public void completePersonlTask(String taskId,String comment,String role,String result,int status){
		//�������
		if(StringUtils.isNotBlank(comment)){//�����Ϊ�޵Ļ����������
			workflowUtil.addComment(taskId,comment);
		}
		
		//�޸�ҵ��״̬
		if(status>0){
			int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			formChangeService.updateStatus(id,status);
		}
		
		//����ҵ��
		Map<String,Object> map = new HashMap<>();
		String names="";
		List<SysUser> admins = sysRoleService.findUsersByRoleName("���Ĺ�����Ա");
		for(SysUser user:admins)
			names+=user.getUsername()+",";
		List<SysUser> branchs = sysRoleService.findUsersByRoleName("��֧����������Ա");
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
		
	//�鿴����
	@RequestMapping("getChange")
	@ResponseBody
	public Object getChange(int id){
		FormChange change=formChangeService.getChangeFormById(id);
			return change;
	}
	
	//���ݽڵ����ƺͺ�ѡ�˻�ȡ�����ĵ�������
	@RequestMapping("getTasks")
	@ResponseBody
	public Object getTasks(String pointName,int[] status,HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		//��ȡ�û�����ʵ����
		String userName=activeUser.getRealname();
		
		//���ݽڵ�����ƻ���û�����ȡ����
		List<Task> tasks=workflowUtil.findGroupTaskListByName(pointName,userName);
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		//��Ҫ���ݷ�֧������ѯ��ص�id��
		
		List<FormChange> afList= formChangeService.findBusinessByTasks(busAndTaskId.keySet());
		List<FormChange> forms= new ArrayList<FormChange>();
		if(activeUser.getRamusCenter().equals("0000")){
			for(int i=0;i<afList.size();i++){
				//����ҵ��id��������id����������id���õ�ҵ��id��
				FormChange af = afList.get(i);
				//���ݷ�֧����id��÷�֧��������
				Division divisionByCode = branchCenterService.getDivisionByCode(af.getOffshootorganiz());
				String branchName=divisionByCode.getDivisionname();
				for(int t=0;t<status.length;t++){
					if(status[t]==af.getStatus()){
						String str = af.getId()+"";
						String tid=busAndTaskId.get(str);
						//String titleno=applicationFormService.getAppFormById(af.getPid()).getTitleno();
						af.setTaskId(tid);//����taskId
						af.setBranchName(branchName);
						//af.setTitleno(titleno);
						forms.add(af);
						break;
					}
				}
			}
		}else{
			for(int i=0;i<afList.size();i++){
				//����ҵ��id��������id����������id���õ�ҵ��id��
				FormChange af = afList.get(i);
				if(activeUser.getRamusCenter().equals(af.getOffshootorganiz())){
					//���ݷ�֧����id��÷�֧��������
					Division divisionByCode = branchCenterService.getDivisionByCode(af.getOffshootorganiz());
					String branchName=divisionByCode.getDivisionname();
					for(int t=0;t<status.length;t++){
						if(status[t]==af.getStatus()){
							String str = af.getId()+"";
							String tid=busAndTaskId.get(str);
							af.setTaskId(tid);//����taskId
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
	
	//���ݽڵ����ƺͺ�ѡ�˻�ȡ�����ĵ�������
	@RequestMapping("getAllTask")
	@ResponseBody
	public Object getAllTask(String pointName,int[] status,HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		//��ȡ�û�����ʵ����
		String userName=activeUser.getRealname();
		//���ݽڵ�����ƻ���û�����ȡ����
		List<Task> tasks=workflowUtil.findGroupTaskListByName(pointName,userName);
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		//��Ҫ���ݷ�֧������ѯ��ص�id��
		
		List<FormChange> afList= formChangeService.findBusinessByTasks(busAndTaskId.keySet());
		List<FormChange> forms= new ArrayList<FormChange>();
		for(int i=0;i<afList.size();i++){
			//����ҵ��id��������id����������id���õ�ҵ��id��
			FormChange af = afList.get(i);
			for(int t=0;t<status.length;t++){
				if(status[t]==af.getStatus()){
					String str = af.getId()+"";
					String tid=busAndTaskId.get(str);
					af.setTaskId(tid);//����taskId
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
		AddLog.addLog(Log.QUERY,"�鿴'"+fc.getCompanynameOld()+"'�ı��ҵ��������Ϣ");
		model.addAttribute("id",id);
		return "alternation/newForm/form_view";
	}
	
	/*@RequestMapping("viewInfo")
	public String viewInfo(int id,Model model){
		FormChange fc=formChangeService.getChangeFormById(id);
		CompanyInfo ci=companyInfoService.getComById(fc.getPid()); 
		AddLog.addLog(Log.QUERY,"��ѯ"+ci.getEnterprisename()+"��������Ϣ");
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute("id",id);
		return "application/common/application_view";
	}*/
	
	@RequestMapping("trial")
	public String trial(int id,String taskId,Model model) {
		FormChange fc=formChangeService.getChangeFormById(id); 
		AddLog.addLog(Log.QUERY,"�鿴'"+fc.getCompanynameOld()+"'�ı��ҵ��������Ϣ");
		model.addAttribute("id",id);
		model.addAttribute("taskId",taskId);
		return "alternation/firstTrial/trial";
	}
	
	//��ȡ�����˻�����
	@RequestMapping("getReturnTasks")
	@ResponseBody
	public List<FormChange> getReturnTasks(HttpSession session){//�鿴���˻ص�ҵ��
		String branchId=((ActiveUser)session.getAttribute("activeUser")).getRamusCenter();
		//String branchName="��֧����";//��ȡ��ǰ�û������ķ�֧��������������ʵ����ҲҪ����֧��������
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);//��֧��������
		tasks=workflowUtil.getTaskByPointName("��������");
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<FormChange> afList= new ArrayList<FormChange>();
		afList = formChangeService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		for(int i=0;i<afList.size();i++){
			//����ҵ��id��������id����������id���õ�ҵ��id��
			FormChange af = afList.get(i);
			String str = af.getId()+"";
			//String titleno=applicationFormService.getAppFormById(af.getPid()).getTitleno();
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
			//af.setTitleno(titleno);
		}
		return afList;
	}
	
	//����id��ȡ�޸ı�����
	@RequestMapping("getForm")
	@ResponseBody
	public Object getForm(int id){
		return formChangeService.getForm(id);
	}
	
	//ʰȡ�����񲢰����������
	@RequestMapping("/climeAndcompleteTask")
	@ResponseBody
	public void climeAndcompleteTask(String taskId,String comment,String role,String result,int status,HttpSession session){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//�����־
		FormChange formchange=formChangeService.getChangeFormById(id);
		AddLog.addLog(Log.ADD,"�������ĺ�׼��'"+formchange.getCompanynameOld()+"'���ҵ������");
		//ʰȡΪ��������
		String userName=((ActiveUser)session.getAttribute("activeUser")).getRealname();
		taskService.claim(taskId, userName);
		//�������
		if(!("��".equals(comment))&&StringUtils.isNotBlank(comment)){//�����Ϊ�޵Ļ����������
			workflowUtil.addComment(taskId,comment);
		}
		
		//�޸�ҵ��״̬
		if(status>0){
			formChangeService.updateStatus(id,status);
		}
		
		//����ҵ��
		Map<String,Object> map = new HashMap<>();
		String names="";
		List<SysUser> admins = sysRoleService.findUsersByRoleName("���Ĺ�����Ա");
		for(SysUser user:admins)
			names+=user.getUsername()+",";
		List<SysUser> branchs = sysRoleService.findUsersByRoleName("��֧����������Ա");
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
		
		if(status==5){//������ҵ����ɣ����޸�ys_title���еļ�¼
			FormChange fc=formChangeService.getChangeFormById(id);
			//��ȡtitleId
			Obj2Obj obj=new Obj2Obj();
			ApplicationForm af=obj.Change2App(fc);
			//applicationFormService.updateApp(af);//�޸�ys_title
			//�޸�ys_company�еĻ�����Ϣ
			CompanyInfo info=obj.App2Com(af);
			info.setId(af.getId());
			//��ȡtitleId
			//String titleId=applicationFormService.getAppFormById(af.getId()).getTitleno();
			//info.setTitleno(titleId);
			info.setStatus(35);
			companyInfoService.update(info);
		}
	}
	
	//������ҵ������ͼ
	@RequestMapping("deploy")
	@ResponseBody
	public void deploy(){
		workflowUtil.deployment("activiti/changeBusiness", "���ҵ������");
		System.out.println("����ɹ�");
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
	//���ҵ��ĳ���
	@RequestMapping("edit")
	public String edit(String taskId,String id,Model model){
		int appId=Integer.parseInt(id);
		ApplicationForm af=applicationFormService.getAppFormById(appId); 
		AddLog.addLog(Log.MODIFY,"�޸�'"+af.getEnterprisename()+"'�ı��������Ϣ");
		model.addAttribute("taskId",taskId);
		model.addAttribute("id",id);
		return "alternation/firstTrial/alterForm_edit";
	}
	//����ҵ��Ϣ��������ҵ������
	@RequestMapping("alterApp")
	public String alterApply(String taskId,String id,Model model){
		//id����ҵ���
		int cid=Integer.parseInt(id);
		ApplicationForm af=applicationFormService.getLastAppBycid(cid); 
		AddLog.addLog(Log.MODIFY,"���'"+af.getEnterprisename()+"'�ı��������Ϣ");
		FormChange fc=formChangeService.getChangeFormByPId(af.getId());
		model.addAttribute("taskId",taskId);
		model.addAttribute("id",fc.getId());
		return "alternation/newForm/alterForm";
	}
	//������ҵ������
	@RequestMapping("toAlterApp")
	public String toAalterApply(){
		return "alternation/newForm/alterForm";
	}
	//��ȡ�±�����������ˮ��
	@RequestMapping("getParam")
	@ResponseBody
	public ApplicationForm getParam(HttpServletRequest request){//����,Model model
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
	//�ϴ��������
	/*@RequestMapping("startApply")
	@ResponseBody
	public void startApply(int changeId,String titleNo,MultipartFile file,HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		FormChange fc=formChangeService.getChangeFormByPId(changeId);
		//�ļ��ϴ�
		if(!(file.isEmpty())){
			delFile("bg"+fc.getId());
			uploadFile(file,"bg"+fc.getId(),titleNo);
		}
	//��������ʵ��
	Map<String,Object> activitiMap = new HashMap<>();
	//String companyName=form.getCompanynameOld();
	activitiMap.put("company",branchId);
	String objId="Application:"+fc.getId();
	runtimeService.startProcessInstanceByKey("changeBusiness",objId,activitiMap);
	//���ݹ�˾���Ʋ�ѯ����Id
	List<Task> tasks = workflowUtil.findTaskListByName(branchId);
	String taskId=tasks.get(0).getId();
	//��ɸ�������
	completePersonlTask(taskId,"��","�����������Ա","true",1);
	
	}*/
	//��codeɾ���ļ�
	@RequestMapping("delFile")
	@ResponseBody
	public void delFile(String code){
		//��ѯ·��
		String path=uploadFileService.getPathByCode(code);
		if(path!=null){
			File file = new File(path);
			if(file.exists()){//ɾ���ļ�
				file.delete();
			}
		}
		//ɾ�����ݿ��е�����
		uploadFileService.deleteByCode(code);
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
	
	@RequestMapping("toView")
	public String toView(String titleNo,String taskId,int id,ModelMap model){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		FormChange fc = formChangeService.getChangeFormById(appId);
		AddLog.addLog(Log.QUERY,"�鿴'"+fc.getCompanynameOld()+"'�ı��ҵ��������Ϣ");
		model.addAttribute("taskId", taskId);
		model.addAttribute("id", id);
		model.addAttribute("item", titleNo);
		return "common/viewBG";
	}
	
	//ͨ���ύ��ִ�У�ֻ����taskId������
		@RequestMapping("/saveComment")
		public String saveComment(String taskId,String comment){
			int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			FormChange fc = formChangeService.getChangeFormById(appId);
			AddLog.addLog(Log.ADD,"����������'"+fc.getCompanynameOld()+"'�ı��ҵ������ͨ��");
			//�������
			if(!("��".equals(comment))&&StringUtils.isNotBlank(comment)){//�����Ϊ�޵Ļ����������
				workflowUtil.addComment(taskId,comment);
			}
			return "alternation/issueReport/sendMessage";
		}
		
		//ͨ���ύ��ִ�У�ֻ����taskId������
		@RequestMapping("/saveCommentZX")
		public String saveCommentZX(String taskId,String comment){
			int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			FormChange fc = formChangeService.getChangeFormById(appId);
			AddLog.addLog(Log.ADD,"�������ĺ�׼'"+fc.getCompanynameOld()+"'�ı��ҵ������ͨ��");
			//�������
			if(!("��".equals(comment))&&StringUtils.isNotBlank(comment)){//�����Ϊ�޵Ļ����������
				workflowUtil.addComment(taskId,comment);
			}
			return "alternation/issueReport/tasks";
		}
		//ɾ�����ҵ��
		@RequestMapping("deleteAlterApp")
		@ResponseBody
		public void deleteAlterApp(int id,String taskId){
			if(!"0".equals(taskId)){
				workflowUtil.deleteProcess(taskId);
			}
			FormChange changeFormById = formChangeService.getChangeFormById(id);
			String companyName=changeFormById.getCompanynameOld();
			AddLog.addLog(Log.DELETE,"ɾ��'"+companyName+"'�ı��������Ϣ");
			String titleNo=changeFormById.getTitleno();
			commonsController.delFiles(titleNo,CommonsController.�����);
			CompanyInfo com=companyInfoService.getComById(changeFormById.getPid());
			com.setStatus(17);
			String substring = titleNo.substring(0,2);
			com.setTitleno(titleNo.replace(substring, "XB"));
			companyInfoService.update(com);
			formChangeService.deleteById(id);
		}
}
