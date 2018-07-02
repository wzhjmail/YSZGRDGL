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
	
	//���ݹ�˾id����ѯ���ϵ��ܴ���������Ϣ
	@RequestMapping("getCount")
	@ResponseBody
	public Object getCount(String companyId,int iDisplayStart,int iDisplayLength,String sEcho){
		//ҳ��
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //����ҳ���ÿҳ���Ƚ��н�ȡ
        PageHelper.startPage(page_num, iDisplayLength);
		List<ApplicationForm> list=applicationFormService.getFRCount(companyId);
		for(int i=0;i<list.size();i++){
			list.get(i).setId(i+1);
		}
		//���з�ҳ����
        PageInfo<ApplicationForm> pageInfo = new PageInfo<ApplicationForm>(list);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	//���ݷ�֧����id��ѯ���е���ҵ��Ϣ
	@RequestMapping("getComsByBranchId")
	@ResponseBody
	public Object getComsByBranchId(String branchId){
		return companyInfoService.getAll(branchId);
	}
	
	//���ݹ�˾id����ҵ��Ϣ��ǰ����
	
	//��ת����ҵ�������µ��ѱ�������
	@RequestMapping("toNewForms")
	public String toNewForms(){
		AddLog.addLog(Log.QUERY,"��ѯ�����ѱ��桢�˻صĸ���ҵ��������Ϣ");
		return "recognition/newForm/newForms";
	}
	
	//��ת����ҵ������ҳ��
	@RequestMapping("toReAactivate")
	public String toReAactivate(int cid,Model model){
		ApplicationForm af=applicationFormService.getLastAppBycid(cid);
//		model.addAttribute("taskId",taskId);
		model.addAttribute("item",af);
		model.addAttribute("id",af.getId());
		model.addAttribute("titleNo", af.getTitleno());
		return "recognition/newForm/reApply";
	}
	//��ȡ�����ϵ�ҵ��
	@RequestMapping("getNewForms")
	@ResponseBody
	public Object getNewForms(HttpSession session){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		String titleno="";
		//��ys_company���ó��ܸ��ϵı�
		List<CompanyInfo> coms=companyInfoService.getComByStatus(18,branchId);
		
		Obj2Obj obj=new Obj2Obj();
		//List<ApplicationForm> lists=new ArrayList<ApplicationForm>();
		for(int i=0;i<coms.size();i++){
			CompanyInfo com=coms.get(i);
			ApplicationForm app=obj.Com2App(com);
			titleno=com.getTitleno().replace("XB","FR");
			//lists.add(app);
			//����ѯ��������Ϣ���浽ys_title��
			app.setCreatedate(new Date());
			app.setCompanyId(com.getId());
			app.setTitleno(titleno);//���titleNo
			applicationFormService.insertApp(app);
			//�޸�ys_company�������ݵ�״̬
			companyInfoService.setStatus(com.getId(),37);//ҵ�������
		}
		List<ApplicationForm> apps=applicationFormService.getAppByStatus(18,branchId);
		List<ApplicationForm> returns=(List<ApplicationForm>)getPointTask("xbywsq",session);
		apps.addAll(returns);
		return  apps;
	}
	
	//����ҵ����޸�
	@RequestMapping("toReturnForm")
	public String toReturnForm(String taskId,String id,Model model){
		ApplicationForm af=applicationFormService.getAppFormById(Integer.parseInt(id));
		AddLog.addLog(Log.QUERY,"�鿴'"+af.getEnterprisename()+"'�ĸ���ҵ��������Ϣ");
		model.addAttribute("taskId",taskId);
		model.addAttribute("id",id);
		model.addAttribute("titleNo",af.getTitleno());
		model.addAttribute("companyId",af.getCompanyId());
		String yyzz=uploadFileService.getPath(af.getTitleno(),CommonsController.Ӫҵִ��);
		String yszg=uploadFileService.getPath(af.getTitleno(),CommonsController.ӡˢ��Ӫ���֤);
		String zlsc=uploadFileService.getPath(af.getTitleno(),CommonsController.�����ֲ�);
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
	
	//����ҵ��ı���
	@RequestMapping("save")
	@ResponseBody
	public void save(ApplicationForm app,HttpServletRequest request,String oldName,String cId){//��������
		AddLog.addLog(Log.ADD,"����'"+app.getEnterprisename()+"'�ĸ���������Ϣ");
		String titleNo=app.getTitleno();
		String companyName=app.getEnterprisename();
		testingEquipService.updateCompanyName(oldName, app.getEnterprisename());
		printEquipmentService.updateCompanyName(oldName, app.getEnterprisename());
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
		//����ύ���������ֲᣬ����ɾ���ļ������ݣ������
		if(!(app.getQuality().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.�����ֲ�);
			commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.�����ֲ�,request);
		}
		PrintEquipment pe=new PrintEquipment();
		pe.setCompanyName(companyName);
		pe.setCompanyId(Integer.parseInt(cId));
		printEquipmentService.updateById(pe);
		TestingEquip te=new TestingEquip();
		te.setCompanyName(companyName);
		te.setCompanyId(Integer.parseInt(cId));
		testingEquipService.updateById(te);
		commonsController.delFile(titleNo,CommonsController.�����);
		app.setCreatedate(new Date());
		app.setTitleno(titleNo);
		applicationFormService.updateApp(app);
		commonController.savePdf(2, app.getId(), request);//����pdf
		//return "recognition/newForm/newForms";
		
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("�����");
		
		//����·��
		String path = "upload";
		String filePath = path+"/FR/"+app.getTitleno();
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
	//����ҵ��ı���
	@RequestMapping("saveReApp")
	@ResponseBody
	public void saveReApp(ApplicationForm app,HttpServletRequest request){//��������
		AddLog.addLog(Log.ADD,"����"+app.getEnterprisename()+"��������Ϣ");
		String titleNo=commonController.getNum("FR",app.getBranchId());
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
		//����ύ���������ֲᣬ����ɾ���ļ������ݣ������
		if(!(app.getQuality().isEmpty())){
			delFile(app.getQualityno()+"");
			uploadFile(app.getQuality(),app.getQualityno()+"",titleNo,request);
		}
		app.setCreatedate(new Date());
		app.setTitleno(titleNo);
		applicationFormService.updateApp(app);
		commonController.savebgApp(1, app.getId(), request);//����pdf
	}
	//��ȡ�±�����������ˮ��
	@RequestMapping("getParam")
	@ResponseBody
	public Object getParam(String name,ModelMap map){
		ApplicationForm aForm=applicationFormService.getAppFormByName(name,"%FR%");
		String titleNo=aForm.getTitleno();
		String path=uploadFileService.getPath(titleNo,CommonsController.�����_����);
		if(path!=null&&path!=""){
			String fileName=path.substring(path.lastIndexOf("/")+1);
			aForm.setAppFileName(fileName);
		}
		return aForm;
	}
	//����ҵ����˻�ҳ��
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		AddLog.addLog(Log.QUERY,"��ѯ�����˻صĸ���ҵ��������Ϣ");
		return "recognition/newForm/returnTask";
	}
	//����ҵ���˻صı���
	@RequestMapping("saveReturn")
	@ResponseBody
	public void saveReturn(ApplicationForm app,HttpServletRequest request,String oldName,String cId){//����
		AddLog.addLog(Log.ADD,"����'"+app.getEnterprisename()+"'�ĸ���������Ϣ");
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String titleNo=app.getTitleno();
		String branchId=activeUser.getRamusCenter();
		String companyName=app.getEnterprisename();
		testingEquipService.updateCompanyName(oldName, app.getEnterprisename());
		printEquipmentService.updateCompanyName(oldName, app.getEnterprisename());
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
		//����ύ���������ֲᣬ����ɾ���ļ������ݣ������
		if(!(app.getQuality().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.�����ֲ�);
			commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.�����ֲ�,request);
		}
		PrintEquipment pe=new PrintEquipment();
		pe.setCompanyName(companyName);
		pe.setCompanyId(Integer.parseInt(cId));
		printEquipmentService.updateById(pe);
		TestingEquip te=new TestingEquip();
		te.setCompanyName(companyName);
		te.setCompanyId(Integer.parseInt(cId));
		testingEquipService.updateById(te);
		commonsController.delFile(titleNo,CommonsController.�����);
		app.setCreatedate(new Date());
		applicationFormService.updateApp(app);
		commonController.savePdf(2, app.getId(), request);//����pdf

		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("�����");
		//����·��
		String path = "upload";
		String filePath = path+"/FR/"+app.getTitleno();
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
	
	//����ҵ��ı���
	@RequestMapping("insert")
	@Transactional
	public String insert(ApplicationForm app,HttpServletRequest request) throws Exception{//����
		AddLog.addLog(Log.ADD,app.getEnterprisename()+"���и�������ҵ��");
		String titleNo=commonController.getNum("FR",app.getBranchId());
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
		//����ύ���������ֲᣬ����ɾ���ļ������ݣ������
		if(!(app.getQuality().isEmpty())){
			commonsController.delFile(titleNo,CommonsController.�����ֲ�);
			commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.�����ֲ�,request);
		}
		//��������
		app.setTitleno(titleNo);
		app.setCreatedate(new Date());
		applicationFormService.updateApp(app);
		commonController.savePdf(2, app.getId(), request);//����pdf
		//��������ʵ��
		Map<String,Object> activitiMap = new HashMap<>();
		String branchName="��֧����";
		activitiMap.put("company", branchName);
		String objId="Application:"+app.getId();
		runtimeService.startProcessInstanceByKey("rerecognition",objId,activitiMap);
		//���ݹ�˾���Ʋ�ѯ����Id
		List<Task> tasks = workflowUtil.findTaskListByName(branchName);
		String taskId=tasks.get(0).getId();
		//��ɸ�������
		completePersonlTask(taskId,"��","�����������Ա","true",13);
		//�ڲ�ѯ��ʱ���ǲ��ys_company���е����ݣ���ˣ��ڿ������̺󣬽�ys_company���е�status�޸�
		companyInfoService.setStatus(app.getId(),27); 
		return "recognition/newForm/newForms";
	}
	//����ҵ�������
	@RequestMapping("apply")
	public String apply(int appId,String titleNo,MultipartFile file,HttpSession session,HttpServletRequest request){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		//�ļ��ϴ�
		if(!(file.isEmpty())){
			delFile("fr"+appId);
			uploadFile(file,"fr"+appId,titleNo,request);
		}
		//��������ʵ��
		Map<String,Object> activitiMap = new HashMap<>();
		activitiMap.put("company", branchId);
		String objId="Application:"+appId;
		runtimeService.startProcessInstanceByKey("rerecognition",objId,activitiMap);
		//���ݹ�˾���Ʋ�ѯ����Id
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);
		String taskId=tasks.get(0).getId();
		//��ɸ�������
		completePersonlTask(taskId,"��","�����������Ա","true",13);
		return "recognition/newForm/newForms";
	}
	//�ϴ���������
	@RequestMapping("startApply")
	@ResponseBody
	public int startApply(String taskId,int appId,String titleNo,MultipartFile file,HttpSession session,HttpServletRequest request){
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		int userId=activeUser.getUserid();
		ApplicationForm af=applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.IMPORT,"�ϴ�'"+af.getEnterprisename()+"'�ĸ��¸����������������ҵ������");
		//�ļ��ϴ�
		if(file!=null&&!(file.isEmpty())){
			commonsController.delFile(titleNo,CommonsController.�����_����);
			commonsController.uploadFile(file,titleNo,CommonsController.�����_����,request);
		}
		if("null".equals(taskId) || StringUtils.isBlank(taskId)){//���taskId�����ڣ������������
			//��������ʵ��
			Map<String,Object> activitiMap = new HashMap<>();
			activitiMap.put("user", branchId);
			String objId="Recognition:"+appId;
			runtimeService.startProcessInstanceByKey("recognition",objId,activitiMap);
			//�������̶���id�ͽڵ�id��ѯ����Id
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
		//�������
		appCommonsController.completeTask(taskId,"", "true", 20);
		return userId;
	}
	//���˻����������
	@RequestMapping("applyReturn")
	public String applyReturn(String taskId,String titleNo,MultipartFile file,HttpServletRequest request){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//�ļ��ϴ�
		if(!(file.isEmpty())){
			delFile("fr"+appId);
			uploadFile(file,"fr"+appId,titleNo,request);
		}
		//��ɸ�������
		completePersonlTask(taskId,"��","�����������Ա","true",13);
		return "recognition/newForm/returnTask";
	}
	@RequestMapping("insertReturn")
	public String insertReturn(ApplicationForm app,HttpServletRequest request){//�˻�������ύ���ȸ�����Ϣ����ִ������
		AddLog.addLog(Log.ADD,app.getEnterprisename()+"���и�������ҵ��");
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
		//����ύ���������ֲᣬ����ɾ���ļ������ݣ������
		if(!(app.getQuality().isEmpty())){
			delFile(app.getQualityno()+"");
			uploadFile(app.getQuality(),app.getQualityno()+"",titleNo,request);
		}
		app.setCreatedate(new Date());
		applicationFormService.updateApp(app);
		commonController.savePdf(2, app.getId(), request);//����pdf
		String taskId=app.getTaskId();
		//��ɸ�������
		completePersonlTask(taskId,"��","�����������Ա","true",13);
		return "recognition/newForm/returnTask";
	}
	
	//��ȡ�����˻�����
	@RequestMapping("getReturnTasks")
	@ResponseBody
	public Object getReturnTasks(HttpSession session){//�鿴���˻ص�ҵ��
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		
		//String branchName="��֧����";//��ȡ��ǰ�û������ķ�֧��������������ʵ����ҲҪ����֧��������
		List<Task> tasks = workflowUtil.findTaskListByName(branchId);//��֧��������
		tasks=workflowUtil.getTaskByPointName("���ϱ��ύ");
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
	
	@RequestMapping("/completePersonlTask")
	@ResponseBody
	public void completePersonlTask(String taskId,String comment,String role,String result,int status){
		//�������
		if(!("��".equals(comment))||StringUtils.isNotBlank(comment)){//�����Ϊ�޵Ļ����������
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
	List<Task> tasks=taskService.createTaskQuery().taskAssignee("���������Ա").list();
	List<Task> task2=taskService.createTaskQuery().taskCandidateUser("���������Ա").list();
	return tasks;
}
	@RequestMapping("/climeAndcompleteTask")
	@ResponseBody
	public void climeAndcompleteTask(String taskId,String comment,String role,String result,int status,HttpSession session){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"���"+af.getEnterprisename()+"�ĸ���ҵ������");
		//ʰȡΪ��������
		String userName=((ActiveUser)session.getAttribute("activeUser")).getRealname();
		taskService.claim(taskId, userName);
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
			map.put("fzjg",names);
		}
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
		if(path!=null){
			File file = new File(path);
			if(file.exists()){//ɾ���ļ�
				file.delete();
			}
		}
		//ɾ�����ݿ��е�����
		uploadFileService.deleteByCode(code);
	}
	
	//���ݽڵ����ƻ�ȡ��������������
	@RequestMapping("getPointTask")
	@ResponseBody
	public Object getPointTask(String pointId,HttpSession session){
		//��ȡ�û�����ʵ����
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		
		//���ݽڵ�����ƻ���û�����ȡ����
		List<Task> tasks = workflowUtil.getTaskByIds("recognition", pointId);
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		for(ApplicationForm af:afList){
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afList;
	}
	
	//���ݽڵ����ƻ�ȡ��������
	@RequestMapping("getAllTask")
	@ResponseBody
	public Object getAllTask(String pointId){
		//���ݽڵ�����ƻ���û�����ȡ����
		List<Task> tasks = workflowUtil.getTaskByIds("recognition", pointId);
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet());
		for(ApplicationForm af:afList){
			String str = af.getId()+"";
			String tid=busAndTaskId.get(str);
			af.setTaskId(tid);
		}
		return afList;
	}
		
	//����������ͼ
	@RequestMapping("/deploy")
	@ResponseBody
	public void deploy() {
		workflowUtil.deployment("activiti/recognition", "����ҵ������");
		System.out.println("����ɹ�");
	}
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,Model model) {
		String id1=request.getParameter("id");
		String taskId=request.getParameter("taskId");
		int id=Integer.parseInt(id1);
		ApplicationForm af=applicationFormService.getAppFormById(id); 
		AddLog.addLog(Log.QUERY,"��ѯ'"+af.getEnterprisename()+"'�ĸ���������Ϣ");
		String titleno=af.getTitleno();
		String path1=uploadFileService.getPath(titleno, CommonsController.Ӫҵִ��);
		String path2=uploadFileService.getPath(titleno, CommonsController.ӡˢ��Ӫ���֤);
		String path3=uploadFileService.getPath(titleno, CommonsController.�����ֲ�);
		String path4=uploadFileService.getPath(titleno, CommonsController.�����);
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
	//������ҵ������
	@RequestMapping("reApply")
	public String reApply(HttpServletRequest request,Model model) {
		return "recognition/newForm/reApply";
	}	
	//ɾ������ҵ��
	@RequestMapping("deleteReApp")
	@ResponseBody
	public void deleteReApp(int id,String taskId){
		if(taskId!=null&&!"null".equals(taskId)){
			workflowUtil.deleteProcess(taskId);
//			id=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		}
		ApplicationForm af=applicationFormService.getAppFormById(id);
		String companyName=af.getEnterprisename();
		AddLog.addLog(Log.DELETE,"ɾ��'"+companyName+"'�ĸ���������Ϣ");
		String titleNo=af.getTitleno();
		commonsController.delFiles(titleNo,CommonsController.ӡˢ��Ӫ���֤);
		commonsController.delFiles(titleNo,CommonsController.Ӫҵִ��);
		commonsController.delFiles(titleNo,CommonsController.�����ֲ�);
		commonsController.delFiles(titleNo,CommonsController.�����);
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
