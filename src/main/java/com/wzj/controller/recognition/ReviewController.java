package com.wzj.controller.recognition;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wzj.controller.CommonsController;
import com.wzj.controller.application.AppCommonsController;
import com.wzj.controller.application.CommonController;
import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.FormList;
import com.wzj.pojo.Log;
import com.wzj.pojo.ReviewForm;
import com.wzj.pojo.ReviewFormPart;
import com.wzj.pojo.UploadFile;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.ReviewFormPartService;
import com.wzj.service.application.impl.ReviewFormService;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.util.AddLog;
import com.wzj.util.WorkflowUtil;

@Controller("recognition/review")
@RequestMapping("recognition/review")
public class ReviewController {
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private ReviewFormService reviewFormService;
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	AppCommonsController appCommonsController;
	@Autowired
	private CommonController commonController;
	@Autowired
	CommonsController commonsController;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private ReviewFormPartService reviewFormPartService;
	
	//跳转到复认业务下的分支机构评审
	@RequestMapping("toTasks")
	public String toGroupTask(){
		AddLog.addLog(Log.QUERY,"查询所有待评审的复认业务申请信息");
		return "recognition/review/tasks";
	}
	
	//跳转到复认业务下的分支机构填写评审表页面
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
		String num="";
		String psjl="";
		String psjg="";
		if(rf.getId()!=null){
			List<ReviewFormPart> reviewFormPart=reviewFormPartService.getByReviewFormId(rf.getId());
			for (ReviewFormPart reviewFormPart2 : reviewFormPart) {
				num+=reviewFormPart2.getNum()+",";
				psjl+=reviewFormPart2.getPsjl()+"*";
				psjg+=reviewFormPart2.getPsjg()+",";
			}
			model.addAttribute("num",num.subSequence(0, num.length()-1));
			model.addAttribute("psjl",psjl.subSequence(0, psjl.length()-1));
			model.addAttribute("psjg",psjg.subSequence(0, psjg.length()-1));
		}
		return "recognition/review/reviewForm";
	}
	
	/*//添加评审表信息
	@RequestMapping("insertReview")
	@Transactional
	@ResponseBody
	public void insertReview(ReviewForm review,String taskId,HttpSession session,HttpServletRequest request) throws Exception{
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		String titleNo=af.getTitleno();
		AddLog.addLog(Log.ADD,"保存'"+af.getEnterprisename()+"'复认申请的评审表信息");
		//根据taskId获取业务id
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//获取公司信息
		ApplicationForm aform=applicationFormService.getAppFormById(bid);
		//根据公司的id查找ys_syndic
		ReviewForm getReview=reviewFormService.getReviewByBId(bid);
		if(getReview!=null){//判断是否存在
			int id=getReview.getId();
			review.setId(id);
			review.setEnterpriseName(aform.getEnterprisename());
			review.setChiCenter(aform.getBranchName());
			review.setTitleno(titleNo);
			reviewFormService.updateRev(review);
		}else{
			review.setCreateDate(new Date());//插入评审表信息
			review.setPid(bid);//设置公司的id,不知字段是否正确
			review.setChiCenter(aform.getBranchName());
			review.setEnterpriseName(aform.getEnterprisename());
			review.setTitleno(titleNo);
			reviewFormService.insertRev(review);
		}
		//先删除
		commonsController.delFile(titleNo, CommonsController.评审表);
		//在保存
		commonController.exportPDFPS(2,titleNo,request);
		//将评审表信息保存到数据库
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("评审表");
		//设置路径
		String path = "upload";
		String filePath = path+"/FR/"+titleNo;
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
	}*/
	//添加评审表信息
		@RequestMapping("insertReview")
		@Transactional
		@ResponseBody
		public int insertReview(ReviewForm review,String taskId,HttpSession session,HttpServletRequest request) throws Exception{
			int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			ApplicationForm af = applicationFormService.getAppFormById(appId);
			String titleNo=af.getTitleno();
			AddLog.addLog(Log.ADD,"保存'"+af.getEnterprisename()+"'复认申请的评审表信息");
			//根据taskId获取业务id
			int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			//获取公司信息
			ApplicationForm aform=applicationFormService.getAppFormById(bid);
			int id=-1;
			//根据公司的id查找ys_syndic
			ReviewForm getReview=reviewFormService.getReviewByBId(bid);
			if(getReview!=null){//判断是否存在
				id=getReview.getId();
				review.setId(id);
				review.setEnterpriseName(aform.getEnterprisename());
				review.setChiCenter(aform.getBranchName());
				review.setTitleno(titleNo);
				reviewFormService.updateRev(review);
			}else{
				review.setCreateDate(new Date());//插入评审表信息
				review.setPid(bid);//设置公司的id,不知字段是否正确
				review.setChiCenter(aform.getBranchName());
				review.setEnterpriseName(aform.getEnterprisename());
				review.setTitleno(titleNo);
				reviewFormService.insertRev(review);
				id=review.getId();
			}
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
			commonController.exportPDFPS(2,titleNo,request);
			//将评审表信息保存到数据库
			UploadFile uFile = new UploadFile();
			uFile.setUpdescribe("评审表");
			//设置路径
			String path = "upload";
			String filePath = path+"/FR/"+titleNo;
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
	@RequestMapping("view")
	public Object review(int id,Model model){//根据公司id查询评审表信息
		ReviewForm review=reviewFormService.getReviewById(1);
		model.addAttribute("item", review);
		return "recognition/common/reviewForm";
	}
	
	@RequestMapping("applyReview")
	public String applyReview(String taskId,MultipartFile file,HttpSession session,HttpServletRequest request){
		int id=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		String titleNo=af.getTitleno();
		AddLog.addLog(Log.IMPORT,"上传盖章评审表，完成对'"+af.getEnterprisename()+"'复认申请的评审");
		//评审表上传
		if(!(file.isEmpty())){
			commonsController.delFile(titleNo,CommonsController.评审表_盖章);
			commonsController.uploadFile(file,titleNo,CommonsController.评审表_盖章,request);
		}
		ApplicationForm app=applicationFormService.getLastAppFormBytitleNo(titleNo);
		app.setSyndicResult(reviewFormService.getReviewByTitleNo(titleNo).getSyndic());
		applicationFormService.updateApp(app);
		appCommonsController.completeTask(taskId, "", "true",24);
		return "recognition/review/tasks";
	}
	
	//完成编码中心核准的任务
	@RequestMapping("completeTask")
	public String completeTask(String taskId,boolean result,String comment){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		int status=31;
		String resu="true";
		if(!result){
			status=30;
			resu="false";
			AddLog.addLog(Log.ADD,"编码中心核准'"+af.getEnterprisename()+"'的复认申请驳回");
		}else{
			AddLog.addLog(Log.ADD,"编码中心核准'"+af.getEnterprisename()+"'的复认申请通过");
		}
		appCommonsController.completeTask(taskId, comment, resu, status);
		return "recognition/checkMsg/tasks";
	}
	
	//完成编码中心核准的任务
	@RequestMapping("completeCommonTask")
	public String completeTask(String taskId,boolean result,String comment,String backPage){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		AddLog.addLog(Log.ADD,"编码中心完成对'"+af.getEnterprisename()+"'的复认申请核准任务");
		int status=0;
		String url="";
		String resu="true";
		if("批准".equals(backPage)){
			url="recognition/recheck/tasks";
			if(result){
				status=33;
			}else{
				status=32;
				resu="false";
			}
			appCommonsController.completeTask(taskId, comment, resu, status);
		}
		if("发证".equals(backPage)){
			url="recognition/recheck/issuing";
			status=34;
		}
		return url;
	}
}
