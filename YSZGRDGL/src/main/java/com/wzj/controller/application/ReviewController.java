package com.wzj.controller.application;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.activiti.engine.task.Task;
import org.apache.batik.ext.awt.image.codec.util.FileCacheSeekableStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wzj.controller.CommonsController;
import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.FormList;
import com.wzj.pojo.Log;
import com.wzj.pojo.ReviewForm;
import com.wzj.pojo.ReviewFormPart;
import com.wzj.pojo.SysUser;
import com.wzj.pojo.UploadFile;
import com.wzj.pojo.WorkflowBean;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.ReviewFormPartService;
import com.wzj.service.impl.SysRoleService;
import com.wzj.service.application.impl.ReviewFormService;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.util.AddLog;
import com.wzj.util.WorkflowUtil;

@Controller("application/review")
@RequestMapping("application/review")
public class ReviewController {
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private ReviewFormService reviewFormService;
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private CommonController commonController;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	CommonsController commonsController;
	@Autowired
	AppCommonsController appCommonsController;
	@Autowired
	private ReviewFormPartService reviewFormPartService;
	
	@RequestMapping("toReviewForm")
	public String toReviewForm(String taskId,Model model,HttpServletRequest request){
		model.addAttribute("taskId", taskId);
		//根据taskId获取业务id
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm appFormById = applicationFormService.getAppFormById(bid);
		String titleNo=appFormById.getTitleno();
		//根据业务id获取评审表信息
		ReviewForm rf=reviewFormService.getReviewByBId(bid);
		if(rf==null)
			rf=new ReviewForm();
		rf.setTitleno(titleNo);
		model.addAttribute("item",rf);
		//获取当前登录用户所属分中心
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
//		String branchName=activeUser.getBranchName();
		String branchName=appFormById.getBranchName();
		model.addAttribute("branchId",branchId);
		model.addAttribute("branchName",branchName);
		model.addAttribute("companyName",appFormById.getEnterprisename());
		ApplicationForm af=applicationFormService.getAppFormById(Integer.valueOf(bid).intValue());
		String Typ="";
		if(af.getFlat())Typ+="、平版胶印";
		if(af.getGravure())Typ+="、凹版印刷";
		if(af.getWebby())Typ+="、丝网印刷";
		if(af.getFlexible())Typ+="、柔性版印刷";
		if(af.getElsetype()!="")Typ+="、"+af.getElsetype();
		Typ=Typ.substring(1, Typ.length());
		String Mat="";
		if(af.getPapery())Mat+="、纸质";
		if(af.getPastern())Mat+="、不干胶";
		if(af.getFrilling())Mat+="、瓦楞纸";
		if(af.getMetal())Mat+="、金属";
		if(af.getPlastic())Mat+="、塑料";
		if(af.getElsematerial()!="")Mat+="、"+af.getElsematerial();
		Mat=Mat.substring(1, Mat.length());
		model.addAttribute("printTyp",Typ);
		model.addAttribute("printMat",Mat);
		if(rf.getId()!=null){
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
		return "application/review/reviewForm";
		
	}
	@RequestMapping("getResult")
	@ResponseBody
	public Object getResult(String taskId){
		//根据taskId获取业务id
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//String titleno=applicationFormService.getAppFormById(bid).getTitleno();
		//根据业务id获取评审表信息
		ReviewForm rf=reviewFormService.getReviewByBId(bid);
		//ReviewForm rf=reviewFormService.getReviewByTitleNo(titleno);
		if(rf==null)
			rf=new ReviewForm();
		Map<String,Object> map=new HashMap<>();
		Map<String,Object> psjl=new HashMap<>();
		Map<String,Object> psjg=new HashMap<>();
		
		/*List<ReviewFormPart> reviewFormPart=reviewFormPartService.getByReviewFormId(rf.getId());
		for (ReviewFormPart reviewFormPart2 : reviewFormPart) {
			num+=reviewFormPart2.getNum()+",";
			psjl+=reviewFormPart2.getPsjl()+"*";
			psjg+=reviewFormPart2.getPsjg()+",";
		}
		model.addAttribute("psjl",psjl.subSequence(0, psjl.length()-1));
		model.addAttribute("psjg",psjg.subSequence(0, psjg.length()-1));*/
		
		map.put("psjl",psjl);
		map.put("psjg",psjg);
		return map;
	}           
	@RequestMapping("toGroupTask")
	public String toGroupTask(){
		return "application/review/groupTask";
	}
	
	@RequestMapping("toPersonalTask")
	public String toPersonalTask(){
		AddLog.addLog(Log.QUERY,"查询所有待评审的的新申请业务信息");
		return "application/review/personalTask";
	}
	
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		AddLog.addLog(Log.QUERY,"查询所有退回的新申请业务信息");
		return "application/firstTrial/returnTask";
	}
	
	/*@RequestMapping("insertReview")
	@Transactional
	@ResponseBody
	public void insertReview(ReviewForm review,String taskId,HttpSession session,HttpServletRequest request) throws Exception{
		//根据taskId获取业务id
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//获取公司信息
		ApplicationForm af=applicationFormService.getAppFormById(bid);
		String titleNo=af.getTitleno();
		
		//根据公司的title_id查找ys_syndic
		ReviewForm getReview=reviewFormService.getReviewByBId(bid);
		if(getReview!=null){//判断是否存在
			AddLog.addLog(Log.MODIFY,"修改'"+af.getEnterprisename()+"'的新申请业务的评审表");
			review.setId(getReview.getId());
			reviewFormService.updateRev(review);
		}else{
			AddLog.addLog(Log.ADD,"保存'"+af.getEnterprisename()+"'的新申请业务的评审表");
			review.setCreateDate(new Date());//插入评审表信息
			review.setPid(bid);//设置公司的id,不知字段是否正确
			review.setEnterpriseName(af.getEnterprisename());
			review.setChiCenter(af.getBranchName());
			review.setTitleno(titleNo);
			reviewFormService.insertRev(review);
		}
		//先删除
		commonsController.delFile(titleNo, CommonsController.评审表);
		//在保存
		commonController.exportPDFPS(1,titleNo,request);
		//将评审表信息保存到数据库
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("评审表");
		//设置路径
		String path = "upload";
		String filePath = path+"/XB/"+titleNo;
		String fileName = titleNo+"评审"+".pdf";
		filePath = filePath+"/"+fileName;
		uFile.setUprul(filePath);
		//设置大小
		File dir=new File(request.getSession().getServletContext().getRealPath("/upload")+"/"+fileName);
		uFile.setUpsize(dir.getTotalSpace()/1024+"kb");
		
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setDescribeId("6");
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
		//return "redirect:toPersonalTask";
	}*/
	@RequestMapping("insertReview")
	@Transactional
	@ResponseBody
	public int insertReview(ReviewForm review,String taskId,HttpSession session) throws Exception{
		//根据taskId获取业务id
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//获取公司信息
		ApplicationForm af=applicationFormService.getAppFormById(bid);
		String titleNo=af.getTitleno();
		//评审表id;
		int id=-1;
		//根据公司的title_id查找ys_syndic
		ReviewForm getReview=reviewFormService.getReviewByBId(bid);
		if(getReview!=null){//判断是否存在
			AddLog.addLog(Log.MODIFY,"修改'"+af.getEnterprisename()+"'的新申请业务的评审表");
			review.setId(getReview.getId());
			reviewFormService.updateRev(review);
			id=getReview.getId();
		}else{
			AddLog.addLog(Log.ADD,"保存'"+af.getEnterprisename()+"'的新申请业务的评审表");
			review.setCreateDate(new Date());//插入评审表信息
			review.setPid(bid);//设置公司的id,不知字段是否正确
			review.setEnterpriseName(af.getEnterprisename());
			review.setChiCenter(af.getBranchName());
			review.setTitleno(titleNo);
			reviewFormService.insertRev(review);
			id=review.getId();
		}
		//先删除
//		commonsController.delFile(titleNo, CommonsController.评审表);
//		//在保存
//		commonController.exportPDFPS(1,titleNo,request);
//		//将评审表信息保存到数据库
//		UploadFile uFile = new UploadFile();
//		uFile.setUpdescribe("评审表");
//		//设置路径
//		String path = "upload";
//		String filePath = path+"/XB/"+titleNo;
//		String fileName = titleNo+"评审"+".pdf";
//		filePath = filePath+"/"+fileName;
//		uFile.setUprul(filePath);
//		//设置大小
//		File dir=new File(request.getSession().getServletContext().getRealPath("/upload")+"/"+fileName);
//		uFile.setUpsize(dir.getTotalSpace()/1024+"kb");
//		
//		uFile.setCode(titleNo);
//		uFile.setAvailability(true);
//		uFile.setDescribeId("6");
//		uFile.setUploadtime(new Date());
//		uploadFileService.insert(uFile);
		//return "redirect:toPersonalTask";
		return id;
	}
	
	@RequestMapping("savePDF")
	@ResponseBody
	public void savePDF(String taskId,HttpServletRequest request){
		//根据taskId获取业务id
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//获取公司信息
		ApplicationForm af=applicationFormService.getAppFormById(bid);
		String titleNo=af.getTitleno();
		//先删除
		commonsController.delFile(titleNo, CommonsController.评审表);
		//在保存
		commonController.exportPDFPS(1,titleNo,request);
		//将评审表信息保存到数据库
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("评审表");
		//设置路径
		String path = "upload";
		String filePath = path+"/XB/"+titleNo;
		String fileName = titleNo+"评审"+".pdf";
		filePath = filePath+"/"+fileName;
		uFile.setUprul(filePath);
		//设置大小
		File dir=new File(request.getSession().getServletContext().getRealPath("/upload")+"/"+fileName);
		uFile.setUpsize(dir.getTotalSpace()/1024+"kb");
		
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setDescribeId("6");
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
		
	}
	
	@RequestMapping("insertReviewPart")
	@ResponseBody
	public int insertReviewPart(FormList lists){
		if(lists.getReviewFormParts().size()>0){
			int id=lists.getReviewFormParts().get(0).getSyndicid();
			reviewFormPartService.deleteByCId(id);
		}
		return reviewFormPartService.insertList(lists.getReviewFormParts());
	}
	
	@RequestMapping("getPartByRId")
	@ResponseBody
	public List<ReviewFormPart> getPartByRId(int reviewFormId){
		return reviewFormPartService.getByReviewFormId(reviewFormId);
	}
	
	@RequestMapping("applyReview")
	@ResponseBody
	public void applyReview(String taskId,MultipartFile file,HttpSession session,HttpServletRequest request){
		/*//评审表上传
		if(!(file.isEmpty())){
			applicationController.delFile("xb_ps_"+psid);
			applicationController.uploadFile(file,"xb_ps_"+psid,titleNo);
		}
		//完成个人任务
		applicationController.climeAndcompleteTask(taskId,"无","分中心评审人员","fzjg","true",-1,session);
		//return "application/review/personalTask";*/
		int id=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm appFormById = applicationFormService.getAppFormById(id);
		String titleNo=appFormById.getTitleno();
		AddLog.addLog(Log.IMPORT,"上传盖章评审表，完成对'"+appFormById.getEnterprisename()+"'新办业务申请的评审");
	//评审表上传
		if(!(file.isEmpty())){
			commonsController.delFile(titleNo,CommonsController.评审表_盖章);
			commonsController.uploadFile(file,titleNo,CommonsController.评审表_盖章,request);
		}
		ApplicationForm app=applicationFormService.getLastAppFormBytitleNo(titleNo);
		app.setSyndicResult(reviewFormService.getReviewByTitleNo(titleNo).getSyndic());
		applicationFormService.updateApp(app);
		appCommonsController.completeTask(taskId, "", "true", 7);
	}
	
	@RequestMapping("/completePersonlTask")
	@ResponseBody
	public void completePersonlTask(String taskId,String comment,String role,String candidate,String result,int status){
		//添加评论
		if(!("无".equals(comment))&&StringUtils.isNotBlank(comment))
			workflowUtil.addComment(taskId,comment);
		//根据taskId获取业务id
		int id=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//修改业务状态
		if(status>0)
			applicationFormService.updateStatus(status,id);
		
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
	@RequestMapping("insert")
	@ResponseBody
	public void insert(ReviewForm review) throws ParseException{
		Integer id=review.getId();
		ReviewForm rf=reviewFormService.getReviewById(id);
		Date createDate=new Date();
		review.setCreateDate(createDate);
		if(rf==null){
			reviewFormService.insertRev(review);
		}else{
			review.setId(rf.getId());
			reviewFormService.updateRev(review);
		}
		review=reviewFormService.getReviewById(id);
		//return "review/reviewForm";
	}
	
	//如何获取未提交的业务以及被退回来的业务
	@RequestMapping("getReturnTasks")
	@ResponseBody
	public Object getReturnTasks(String pointName,int[] status,HttpSession session){//查看被退回的业务。
		AddLog.addLog(Log.QUERY,"查询所有审核未通过退回的新申请信息");
		//获取用户的真实名称
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String userName=activeUser.getRealname();
		String branchId=activeUser.getRamusCenter();
		
		//根据节点的名称获和用户名称取任务
		List<Task> tasks = workflowUtil.findGroupTaskListByName(pointName, userName);
		//根据任务获取业务
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		List<ApplicationForm> forms= new ArrayList<ApplicationForm>();
		for(int i=0;i<afList.size();i++){
			//根据业务id查找任务id，并将任务id设置到业务id中
			ApplicationForm af = afList.get(i);
			for(int t=0;t<status.length;t++){
				if(status[t]==af.getStatus()){
					String str = af.getId()+"";
					String tid=busAndTaskId.get(str);
					af.setTaskId(tid);
					forms.add(af);
					break;
				}
			}
		}
		return forms;
	}
	
	//查看评审表
	@RequestMapping("toView")
	public String toView(String titleNo,ModelMap model){
		model.addAttribute("item", titleNo);
		return "common/viewReview";//还没建
	}
	
	//完成编码中心核准的任务
	@RequestMapping("completeTask")
	public String completeTask(String taskId,boolean result,String comment){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		int status=14;
		String resu="true";
		if(!result){
			status=13;
			resu="false";
			AddLog.addLog(Log.ADD,"编码中心核准'"+af.getEnterprisename()+"'的新办业务驳回");
		}else{
			AddLog.addLog(Log.ADD,"编码中心核准'"+af.getEnterprisename()+"'的新办业务通过");
		}
		appCommonsController.completeTask(taskId, comment, resu, status);
		return "application/checkMsg/personalTask";
	}
	
	//完成编码中心核准的任务
	@RequestMapping("completeCommonTask")
	public String completeTask(String taskId,boolean result,String comment,String backPage){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		int status=0;
		String url="";
		String resu="true";
		if("批准".equals(backPage)){
			url="application/recheck/personalTask";
			if(result){
				status=16;
				AddLog.addLog(Log.ADD,"编码中心批准'"+af.getEnterprisename()+"'的新办业务通过");
			}else{
				status=15;
				resu="false";
				AddLog.addLog(Log.ADD,"编码中心批准'"+af.getEnterprisename()+"'的新办业务驳回");
			}
			appCommonsController.completeTask(taskId, comment, resu, status);
		}
		if("发证".equals(backPage)){
			url="application/common/issuing";
			status=17;
		}
		return url;
	}
}
