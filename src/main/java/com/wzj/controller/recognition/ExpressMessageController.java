package com.wzj.controller.recognition;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.controller.application.AppCommonsController;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.ExpressInfo;
import com.wzj.pojo.Log;
import com.wzj.pojo.PbtSample;
import com.wzj.pojo.UploadFile;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.ExpressInfoService;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.service.impl.PbtSampleService;
import com.wzj.util.AddLog;
import com.wzj.util.WorkflowUtil;

@Controller("recognition/expressMessage")
@RequestMapping("recognition/expressMessage")
public class ExpressMessageController {

	@Autowired
	private ExpressInfoService expressInfoService;
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private AppCommonsController appCommonsController;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private PbtSampleService pbtSampleService;
	@RequestMapping("toSendMessage")
	public String toSendMessage(){
		AddLog.addLog(Log.QUERY,"查询所有待分中心审批的复认业务申请信息");
		return "recognition/issueReport/sendMessage";
	}
	
	@RequestMapping("insertExpress")
	public String insertExpress(ExpressInfo express,String taskId){
		String[] taskIds = null;   
		 taskIds = taskId.split(","); 
		 for(int i=0;i<taskIds.length;i++){
			 int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskIds[i]));
			ApplicationForm af = applicationFormService.getAppFormById(appId);
			//删除寄送信息
			expressInfoService.delExpress(appId+"");
			 AddLog.addLog(Log.ADD,"'"+express.getContact()+"'向'"+express.getReciveName()+"'寄送'"+af.getEnterprisename()+"'的快递信息");
			//添加寄送时间、企业编号、分中心编号
			express.setSendDate(new Date());
			express.setCompanyId(af.getId()+"");
			express.setBranch(af.getBranchId());
			//信息上传
			expressInfoService.insertExpress(express);
			//完成个人任务
			appCommonsController.completeTask(taskIds[i], "", "0", 29);
		 }
		return "recognition/issueReport/sendMessage";
	}
	
	//跳转到编码中心审核的待审核业务
	@RequestMapping("toTasks")
	public String toTasks(){
		AddLog.addLog(Log.QUERY,"查看所有待编码中心核准的复认业务申请信息");
		return "recognition/checkMsg/tasks";
	}
	
	//跳转到编码中心审核的已退回任务
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		AddLog.addLog(Log.QUERY,"查看所有退回的复认业务申请信息");
		return "recognition/checkMsg/returnTask";
	}
	
	//分中心审批查看所有信息
	@RequestMapping("viewAll")
	public String viewAll(String titleNo,int appId,String comName,String taskId,Model model){
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.QUERY,"查询'"+af.getEnterprisename()+"'的复认业务信息");
		model.addAttribute("titleNo", titleNo);
		model.addAttribute("id", appId);
		model.addAttribute("comName", comName);
		model.addAttribute("taskId", taskId);
		return "recognition/common/viewAll";
	}
	
	//通过提交不执行，只保存taskId和评论
	@RequestMapping("/saveComment")
	@ResponseBody
	public void saveComment(String taskId,String comment){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"分中心审批'"+af.getEnterprisename()+"'的复认申请通过");
		//添加评论
		if(StringUtils.isNotBlank(comment)){//如果不为无的话这添加评论
			workflowUtil.addComment(taskId,comment);
		}
	}
	
	//分中心审批的退回
		@RequestMapping("goBack")
		public String goBack(String taskId,String result,String comment){
			int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			ApplicationForm af=applicationFormService.getAppFormById(appId);
			AddLog.addLog(Log.ADD,"分中心审批'"+af.getEnterprisename()+"'的复认申请驳回");
			int status=0;
			switch (result){
				case "1":
					status=27;
					break;
				case "2":
					status=25;
					break;
				case "3":
					status=23;
					break;
				case "4":
					status=21;
					break;
			}
			if(status!=0)
				appCommonsController.completeTask(taskId, comment, result, status);
			return "redirect:toSendMessage";
		}
		
		//查看复认业务所有信息
		@RequestMapping("ratify")
		public String ratify(String titleNo,int appId,String comName,String taskId,Model model){
			ApplicationForm af = applicationFormService.getAppFormById(appId);
			AddLog.addLog(Log.QUERY,"查询'"+af.getEnterprisename()+"'的复认业务申请信息");
			model.addAttribute("titleNo", titleNo);
			model.addAttribute("id", appId);
			model.addAttribute("comName", comName);
			model.addAttribute("taskId", taskId);
			//根据企业名称查询样品编号
			List<PbtSample> samples=pbtSampleService.getSampleByCom(comName);
			String sampleIds="";
			for(PbtSample sample:samples)
				sampleIds=sampleIds+sample.getUf_sample_code()+",";
			if(!"".equals(sampleIds))
				sampleIds=sampleIds.substring(0, sampleIds.length()-1);
			model.addAttribute("sampleIds", sampleIds);
			return "recognition/common/ratify";
		}
	//加载查看资料
	@RequestMapping("allFiles")
	@ResponseBody
	public List<UploadFile> allFiles(String titleNo,String comName){
		AddLog.addLog(Log.QUERY,"查询流水号为"+titleNo+"的业务所有资料");
		//获取除样品以及检测报告之外的资料
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
				uf1 =uploadFileService.getBydesAndTitleNo(titleNo,"分中心检测报告",describe1);
				uf2 =uploadFileService.getBydesAndTitleNo(titleNo,"中心检测报告",describe1);
				if(uf1.size()!=0){
					if(uf1.size()==1){
						for (UploadFile uf3 : uf1) {
							UploadFile uf=new UploadFile();
							describe2=uf3.getDescribeId();
							uf.setUpdescribe(describe2+"分中心检测报告");
							uf.setDescribeId(uf3.getUpdescribe());
							set.add(uf);
						}	
					}else{
						UploadFile uf=new UploadFile();
						describe2=uf1.get(0).getDescribeId();
						uf.setUpdescribe(describe2+"分中心检测报告");
						uf.setDescribeId(uf1.get(0).getUpdescribe());
						set.add(uf);
					}
				}
				
				if(uf2.size()!=0){
					if(uf2.size()==1){
						for (UploadFile uf4 : uf2) {
							UploadFile uf=new UploadFile();
							describe3=uf4.getDescribeId();
							uf.setUpdescribe(describe3+"中心检测报告");
							uf.setDescribeId(uf4.getUpdescribe());
							set.add(uf);
						}	
					}else{
						UploadFile uf=new UploadFile();
						describe3=uf2.get(0).getDescribeId();
						uf.setUpdescribe(describe3+"中心检测报告");
						uf.setDescribeId(uf2.get(0).getUpdescribe());
						set.add(uf);
					}
				}
				uf5.setUpdescribe(describe1+"样品附件");
				uf5.setDescribeId(describe1);
				uFile.add(uf5);
			}	
		}
		uFile.addAll(set);
		return uFile;
	}
}
