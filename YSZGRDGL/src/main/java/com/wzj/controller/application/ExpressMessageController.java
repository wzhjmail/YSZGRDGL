package com.wzj.controller.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.wzj.pojo.*;
import com.wzj.service.application.impl.CompanyInfoService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.service.alternation.impl.FormChangeService;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.ExpressInfoService;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.service.impl.PbtSampleService;
import com.wzj.util.AddLog;
import com.wzj.util.WorkflowUtil;
import com.wzj.controller.application.AppCommonsController;

@Controller("application/expressMessage")
@RequestMapping("application/expressMessage")
public class ExpressMessageController {
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private ExpressInfoService expressInfoService;
	@Autowired
	private FormChangeService formChangeService;
	@Autowired
	private AppCommonsController appCommonsController;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private PbtSampleService pbtSampleService;
	@RequestMapping("toSendMessage")
	public String toSendMessage(){
		AddLog.addLog(Log.QUERY,"��ѯ���д�������������������ҵ��");
		return "application/common/sendMessage";
	}
	//���ݽڵ����ƺͺ�ѡ�˻�ȡ�����ĵ�������
	@RequestMapping("getGroupTasks")
	@ResponseBody
	public Object getGrouptTasks(String pointName,int[] status,HttpSession session){
		//��ȡ�û�����ʵ����
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String userName=activeUser.getRealname();
		String branchId=activeUser.getRamusCenter();
		
		//���ݽڵ�����ƻ���û�����ȡ����
		List<Task> tasks=workflowUtil.findGroupTaskListByName(pointName,userName);
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		//��Ҫ���ݷ�֧������ѯ��ص�id��
		
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		List<ApplicationForm> forms= new ArrayList<ApplicationForm>();
		for(int i=0;i<afList.size();i++){
			//����ҵ��id��������id����������id���õ�ҵ��id��
			ApplicationForm af = afList.get(i);
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
	//���ݽڵ����ƺͺ�ѡ�˻�ȡ��������������
	@RequestMapping("getAllTask")
	@ResponseBody
	public Object getAllTask(String pointName,int[] status,HttpSession session){
		//��ȡ�û�����ʵ����
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String userName=activeUser.getRealname();
		
		//���ݽڵ�����ƻ���û�����ȡ����
		List<Task> tasks=workflowUtil.findGroupTaskListByName(pointName,userName);
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		//��Ҫ���ݷ�֧������ѯ��ص�id��
		
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet());
		List<ApplicationForm> forms= new ArrayList<ApplicationForm>();
		for(int i=0;i<afList.size();i++){
			//����ҵ��id��������id����������id���õ�ҵ��id��
			ApplicationForm af = afList.get(i);
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
	//���ݽڵ���û�����ȡ��������
	@RequestMapping("getPersonalTasks")
	@ResponseBody
	public Object getPersonalTasks(String pointName,HttpSession session){
		//��ȡ�û�����ʵ����
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String userName=activeUser.getRealname();
		String branchId=activeUser.getRamusCenter();
		
		//�������Ʋ�ѯ����
		List<Task> tasks = workflowUtil.findPersonalTaskListByName(pointName,userName);
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
		
	@RequestMapping("insertExpress")
	public String insertExpress(ExpressInfo express,String taskId){
		String[] taskIds = null;   
		taskIds = taskId.split(","); 
		for(int i=0;i<taskIds.length;i++){
		 int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskIds[i]));
		 ApplicationForm af = applicationFormService.getAppFormById(appId);
		//ɾ��������Ϣ
			expressInfoService.delExpress(appId+"");
		 AddLog.addLog(Log.ADD,"'"+express.getContact()+"'��'"+express.getReciveName()+"'����'"+af.getEnterprisename()+"'�Ŀ����Ϣ");
		//��Ӽ���ʱ�䡢��ҵ��š������ı��
		express.setSendDate(new Date());
		express.setCompanyId(af.getId()+"");
		express.setBranch(af.getBranchId());
		//��Ϣ�ϴ�
		expressInfoService.insertExpress(express);
		//��ɸ�������
		//applicationController.completePersonlTask(taskIds[i],"��","���������Ա","bmzx","true",5);
		appCommonsController.completeTask(taskIds[i], "", "0", 12);
	 }
		return "application/common/sendMessage";
	}
	
	//���ݹ�˾id��ȡ������Ϣ�ϴ��������Ϣ
	@RequestMapping("getExpress")
	@ResponseBody
	public Object getExpress(String companyId){
		if(companyId.indexOf("_change") != -1){
			String id=companyId.split("_")[0];
			FormChange fc=formChangeService.getChangeFormById(Integer.parseInt(id));
			AddLog.addLog(Log.QUERY,"�鿴'"+fc.getCompanynameOld()+"'�Ŀ����Ϣ");
		}else{
			ApplicationForm app=applicationFormService.getAppFormById(Integer.parseInt(companyId));
//			CompanyInfo com= companyInfoService.getComById(Integer.parseInt(companyId));
			AddLog.addLog(Log.QUERY,"�鿴'"+app.getEnterprisename()+"'�Ŀ����Ϣ");
		}
		ExpressInfo object=expressInfoService.getExpressByCompanyId(companyId);
		return object;
	}
	//���ݹ�˾id��ȡ������Ϣ�ϴ��������Ϣ
	@RequestMapping("getEnterpriseExpress")
	@ResponseBody
	public Object getEnterpriseExpress(String companyId){
		if(companyId.indexOf("_change") != -1){
			String id=companyId.split("_")[0];
			FormChange fc=formChangeService.getChangeFormById(Integer.parseInt(id));
			AddLog.addLog(Log.QUERY,"�鿴'"+fc.getCompanynameOld()+"'�Ŀ����Ϣ");
		}else{
//			ApplicationForm app=applicationFormService.getAppFormById(Integer.parseInt(companyId));
				CompanyInfo com= companyInfoService.getComById(Integer.parseInt(companyId));
			AddLog.addLog(Log.QUERY,"�鿴'"+com.getEnterprisename()+"'�Ŀ����Ϣ");
		}
		ExpressInfo object=expressInfoService.getExpressByCompanyId(companyId);
		return object;
	}
	//���ݹ�˾idɾ��������Ϣ�ϴ��������Ϣ
	@RequestMapping("delExpress")
	@ResponseBody
	public void DelExpress(String companyId){
		expressInfoService.delExpress(companyId);
	}
	
	//���ݶ�ҹ�˾idɾ��������Ϣ�ϴ��������Ϣ
	@RequestMapping("delCompanyMes")
	@ResponseBody
	public void delCompanyMes(String taskId){
		String[] taskIds = null;   
		taskIds = taskId.split(",");
		for(int i=0;i<taskIds.length;i++){
		 int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskIds[i]));
		 ApplicationForm af = applicationFormService.getAppFormById(appId);
		 DelExpress(af.getCompanyId()+"");
		}
	}
		
	//�����ǡ����ĺ˶Բ��ϡ���Controller
	@RequestMapping("toGroupTask")
	public String toGroupTask(){
		return "application/checkMsg/groupTask";
	}
	
	@RequestMapping("toPersonalTask")
	public String toPersonalTask(){
		AddLog.addLog(Log.QUERY,"��ѯ���д��������ĺ�׼��������ҵ����Ϣ");
		return "application/checkMsg/personalTask";
	}
	
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		AddLog.addLog(Log.QUERY,"��ѯ�����˻ص�������ҵ����Ϣ");
		return "application/checkMsg/returnTask";
	}
	//�鿴����ҵ��������Ϣ
	@RequestMapping("viewAll")
	public String viewAll(String titleNo,int appId,String comName,String taskId,Model model){
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.QUERY,"��ѯ'"+af.getEnterprisename()+"'��������ҵ����Ϣ");
		model.addAttribute("titleNo", titleNo);
		model.addAttribute("id", appId);
		model.addAttribute("comName", comName);
		model.addAttribute("taskId", taskId);
		return "application/common/viewAll";
	}
	
	//�鿴����ҵ��������Ϣ
	@RequestMapping("ratify")
	public String ratify(String titleNo,int appId,String comName,String taskId,Model model){
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.QUERY,"��ѯ'"+af.getEnterprisename()+"'��������ҵ����Ϣ");
		model.addAttribute("titleNo", titleNo);
		model.addAttribute("id", appId);
		model.addAttribute("comName", comName);
		model.addAttribute("taskId", taskId);
		//������ҵ���Ʋ�ѯ��Ʒ���
		List<PbtSample> samples=pbtSampleService.getSampleByCom(comName);
		String sampleIds="";
		for(PbtSample sample:samples)
			sampleIds=sampleIds+sample.getUf_sample_code()+",";
		if(!"".equals(sampleIds))
			sampleIds=sampleIds.substring(0, sampleIds.length()-1);
		model.addAttribute("sampleIds", sampleIds);
		return "application/common/ratify";
	}
		
	//�������������˻�
	@RequestMapping("goBack")
	public String goBack(String taskId,String result,String comment){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.QUERY,"����������'"+af.getEnterprisename()+"'���°�ҵ�񲵻�");
		int status=0;
		switch (result){
			case "1":
				status=10;
				break;
			case "2":
				status=8;
				break;
			case "3":
				status=6;
				break;
			case "4":
				status=4;
				break;
		}
		if(status!=0)
			appCommonsController.completeTask(taskId, comment, result, status);
		return "redirect:toSendMessage";
	}
	//���ز鿴����
	@RequestMapping("allFiles")
	@ResponseBody
	public List<UploadFile> allFiles(String titleNo,String comName){
		AddLog.addLog(Log.QUERY,"��ѯ��ˮ��Ϊ"+titleNo+"��ҵ����������");
		//��ȡ����Ʒ�Լ���ⱨ��֮�������
		List<UploadFile> uFile = uploadFileService.getByTitleNo(titleNo);
		List<PbtSample> yp=pbtSampleService.getSampleByCom(comName);
		List<UploadFile> uf1 = null;
		List<UploadFile> uf2 = null;
		Set<UploadFile> set=new HashSet<>();
		String describe1="";
		String describe2="";
		String describe3="";
		if(yp!=null){
			for (PbtSample pbtSample : yp) {
				UploadFile uf5=new UploadFile();
				describe1=pbtSample.getUf_sample_code();
				uf1 =uploadFileService.getBydesAndTitleNo(titleNo,"�����ļ�ⱨ��",describe1);
				uf2 =uploadFileService.getBydesAndTitleNo(titleNo,"���ļ�ⱨ��",describe1);
				if(uf1.size()!=0){
					if(uf1.size()==1){
						for (UploadFile uf3 : uf1) {
							UploadFile uf=new UploadFile();
							describe2=uf3.getDescribeId();
							uf.setUpdescribe(describe2+"�����ļ�ⱨ��");
							uf.setDescribeId(uf3.getUpdescribe());
							set.add(uf);
						}	
					}else{
						UploadFile uf=new UploadFile();
						describe2=uf1.get(0).getDescribeId();
						uf.setUpdescribe(describe2+"�����ļ�ⱨ��");
						uf.setDescribeId(uf1.get(0).getUpdescribe());
						set.add(uf);
					}
					
				}
				
				if(uf2.size()!=0){
					if(uf2.size()==1){
						for (UploadFile uf4 : uf2) {
							UploadFile uf=new UploadFile();
							describe3=uf4.getDescribeId();
							uf.setUpdescribe(describe3+"���ļ�ⱨ��");
							uf.setDescribeId(uf4.getUpdescribe());
							set.add(uf);
						}	
					}else{
						UploadFile uf=new UploadFile();
						describe3=uf2.get(0).getDescribeId();
						uf.setUpdescribe(describe3+"���ļ�ⱨ��");
						uf.setDescribeId(uf2.get(0).getUpdescribe());
						set.add(uf);
					}
					
				}
				uf5.setUpdescribe(describe1+"��Ʒ����");
				uf5.setDescribeId(describe1);
				uFile.add(uf5);
			}	
		}
		uFile.addAll(set);
		return uFile;
	}
}
