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
	
	//��ת������ҵ���µķ�֧��������
	@RequestMapping("toTasks")
	public String toGroupTask(){
		AddLog.addLog(Log.QUERY,"��ѯ���д�����ĸ���ҵ��������Ϣ");
		return "recognition/review/tasks";
	}
	
	//��ת������ҵ���µķ�֧������д�����ҳ��
	@RequestMapping("toReviewForm")
	public String toReviewForm(String taskId,Model model,HttpServletRequest request){
		model.addAttribute("taskId", taskId);
		//����taskId��ȡҵ��id
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm appFormById = applicationFormService.getAppFormById(bid);
		String titleNo=appFormById.getTitleno();
		//����ҵ��id��ȡ�������Ϣ
		ReviewForm rf=reviewFormService.getReviewByBId(bid);
		if(rf==null)
			rf=new ReviewForm();
		rf.setTitleno(titleNo);
		model.addAttribute("item",rf);
		//��ȡ��ǰ��¼�û�����������
		ActiveUser activeUser=(ActiveUser) request.getSession().getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
//		String branchName=activeUser.getBranchName();
		String branchName=appFormById.getBranchName();
		model.addAttribute("branchId",branchId);
		model.addAttribute("branchName",branchName);
		model.addAttribute("companyName",appFormById.getEnterprisename());
		
		ApplicationForm af=applicationFormService.getAppFormById(Integer.valueOf(bid).intValue());
		String Typ="";
		if(af.getFlat())Typ+="��ƽ�潺ӡ";
		if(af.getGravure())Typ+="������ӡˢ";
		if(af.getWebby())Typ+="��˿��ӡˢ";
		if(af.getFlexible())Typ+="�����԰�ӡˢ";
		if(af.getElsetype()!="")Typ+="��"+af.getElsetype();
		Typ=Typ.substring(1, Typ.length());
		String Mat="";
		if(af.getPapery())Mat+="��ֽ��";
		if(af.getPastern())Mat+="�����ɽ�";
		if(af.getFrilling())Mat+="������ֽ";
		if(af.getMetal())Mat+="������";
		if(af.getPlastic())Mat+="������";
		if(af.getElsematerial()!="")Mat+="��"+af.getElsematerial();
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
	
	/*//����������Ϣ
	@RequestMapping("insertReview")
	@Transactional
	@ResponseBody
	public void insertReview(ReviewForm review,String taskId,HttpSession session,HttpServletRequest request) throws Exception{
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af = applicationFormService.getAppFormById(appId);
		String titleNo=af.getTitleno();
		AddLog.addLog(Log.ADD,"����'"+af.getEnterprisename()+"'����������������Ϣ");
		//����taskId��ȡҵ��id
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//��ȡ��˾��Ϣ
		ApplicationForm aform=applicationFormService.getAppFormById(bid);
		//���ݹ�˾��id����ys_syndic
		ReviewForm getReview=reviewFormService.getReviewByBId(bid);
		if(getReview!=null){//�ж��Ƿ����
			int id=getReview.getId();
			review.setId(id);
			review.setEnterpriseName(aform.getEnterprisename());
			review.setChiCenter(aform.getBranchName());
			review.setTitleno(titleNo);
			reviewFormService.updateRev(review);
		}else{
			review.setCreateDate(new Date());//�����������Ϣ
			review.setPid(bid);//���ù�˾��id,��֪�ֶ��Ƿ���ȷ
			review.setChiCenter(aform.getBranchName());
			review.setEnterpriseName(aform.getEnterprisename());
			review.setTitleno(titleNo);
			reviewFormService.insertRev(review);
		}
		//��ɾ��
		commonsController.delFile(titleNo, CommonsController.�����);
		//�ڱ���
		commonController.exportPDFPS(2,titleNo,request);
		//���������Ϣ���浽���ݿ�
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("�����");
		//����·��
		String path = "upload";
		String filePath = path+"/FR/"+titleNo;
		String fileName = titleNo+"����"+".pdf";
		filePath = filePath+"/"+fileName;
		uFile.setUprul(filePath);
		//���ô�С
		File dir=new File(request.getSession().getServletContext().getRealPath("/upload")+"/"+fileName);
		uFile.setUpsize(dir.getTotalSpace()/1024+"kb");
		
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setDescribeId("6");
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
	}*/
	//����������Ϣ
		@RequestMapping("insertReview")
		@Transactional
		@ResponseBody
		public int insertReview(ReviewForm review,String taskId,HttpSession session,HttpServletRequest request) throws Exception{
			int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			ApplicationForm af = applicationFormService.getAppFormById(appId);
			String titleNo=af.getTitleno();
			AddLog.addLog(Log.ADD,"����'"+af.getEnterprisename()+"'����������������Ϣ");
			//����taskId��ȡҵ��id
			int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			//��ȡ��˾��Ϣ
			ApplicationForm aform=applicationFormService.getAppFormById(bid);
			int id=-1;
			//���ݹ�˾��id����ys_syndic
			ReviewForm getReview=reviewFormService.getReviewByBId(bid);
			if(getReview!=null){//�ж��Ƿ����
				id=getReview.getId();
				review.setId(id);
				review.setEnterpriseName(aform.getEnterprisename());
				review.setChiCenter(aform.getBranchName());
				review.setTitleno(titleNo);
				reviewFormService.updateRev(review);
			}else{
				review.setCreateDate(new Date());//�����������Ϣ
				review.setPid(bid);//���ù�˾��id,��֪�ֶ��Ƿ���ȷ
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
			//����taskId��ȡҵ��id
			int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
			//��ȡ��˾��Ϣ
			ApplicationForm af=applicationFormService.getAppFormById(bid);
			String titleNo=af.getTitleno();
			//��ɾ��
			commonsController.delFile(titleNo, CommonsController.�����);
			//�ڱ���
			commonController.exportPDFPS(2,titleNo,request);
			//���������Ϣ���浽���ݿ�
			UploadFile uFile = new UploadFile();
			uFile.setUpdescribe("�����");
			//����·��
			String path = "upload";
			String filePath = path+"/FR/"+titleNo;
			String fileName = titleNo+"����"+".pdf";
			filePath = filePath+"/"+fileName;
			uFile.setUprul(filePath);
			//���ô�С
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
	public Object review(int id,Model model){//���ݹ�˾id��ѯ�������Ϣ
		ReviewForm review=reviewFormService.getReviewById(1);
		model.addAttribute("item", review);
		return "recognition/common/reviewForm";
	}
	
	@RequestMapping("applyReview")
	public String applyReview(String taskId,MultipartFile file,HttpSession session,HttpServletRequest request){
		int id=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		String titleNo=af.getTitleno();
		AddLog.addLog(Log.IMPORT,"�ϴ������������ɶ�'"+af.getEnterprisename()+"'�������������");
		//������ϴ�
		if(!(file.isEmpty())){
			commonsController.delFile(titleNo,CommonsController.�����_����);
			commonsController.uploadFile(file,titleNo,CommonsController.�����_����,request);
		}
		ApplicationForm app=applicationFormService.getLastAppFormBytitleNo(titleNo);
		app.setSyndicResult(reviewFormService.getReviewByTitleNo(titleNo).getSyndic());
		applicationFormService.updateApp(app);
		appCommonsController.completeTask(taskId, "", "true",24);
		return "recognition/review/tasks";
	}
	
	//��ɱ������ĺ�׼������
	@RequestMapping("completeTask")
	public String completeTask(String taskId,boolean result,String comment){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		int status=31;
		String resu="true";
		if(!result){
			status=30;
			resu="false";
			AddLog.addLog(Log.ADD,"�������ĺ�׼'"+af.getEnterprisename()+"'�ĸ������벵��");
		}else{
			AddLog.addLog(Log.ADD,"�������ĺ�׼'"+af.getEnterprisename()+"'�ĸ�������ͨ��");
		}
		appCommonsController.completeTask(taskId, comment, resu, status);
		return "recognition/checkMsg/tasks";
	}
	
	//��ɱ������ĺ�׼������
	@RequestMapping("completeCommonTask")
	public String completeTask(String taskId,boolean result,String comment,String backPage){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		AddLog.addLog(Log.ADD,"����������ɶ�'"+af.getEnterprisename()+"'�ĸ��������׼����");
		int status=0;
		String url="";
		String resu="true";
		if("��׼".equals(backPage)){
			url="recognition/recheck/tasks";
			if(result){
				status=33;
			}else{
				status=32;
				resu="false";
			}
			appCommonsController.completeTask(taskId, comment, resu, status);
		}
		if("��֤".equals(backPage)){
			url="recognition/recheck/issuing";
			status=34;
		}
		return url;
	}
}
