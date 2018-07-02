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
		//����taskId��ȡҵ��id
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//String titleno=applicationFormService.getAppFormById(bid).getTitleno();
		//����ҵ��id��ȡ�������Ϣ
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
		AddLog.addLog(Log.QUERY,"��ѯ���д�����ĵ�������ҵ����Ϣ");
		return "application/review/personalTask";
	}
	
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		AddLog.addLog(Log.QUERY,"��ѯ�����˻ص�������ҵ����Ϣ");
		return "application/firstTrial/returnTask";
	}
	
	/*@RequestMapping("insertReview")
	@Transactional
	@ResponseBody
	public void insertReview(ReviewForm review,String taskId,HttpSession session,HttpServletRequest request) throws Exception{
		//����taskId��ȡҵ��id
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//��ȡ��˾��Ϣ
		ApplicationForm af=applicationFormService.getAppFormById(bid);
		String titleNo=af.getTitleno();
		
		//���ݹ�˾��title_id����ys_syndic
		ReviewForm getReview=reviewFormService.getReviewByBId(bid);
		if(getReview!=null){//�ж��Ƿ����
			AddLog.addLog(Log.MODIFY,"�޸�'"+af.getEnterprisename()+"'��������ҵ��������");
			review.setId(getReview.getId());
			reviewFormService.updateRev(review);
		}else{
			AddLog.addLog(Log.ADD,"����'"+af.getEnterprisename()+"'��������ҵ��������");
			review.setCreateDate(new Date());//�����������Ϣ
			review.setPid(bid);//���ù�˾��id,��֪�ֶ��Ƿ���ȷ
			review.setEnterpriseName(af.getEnterprisename());
			review.setChiCenter(af.getBranchName());
			review.setTitleno(titleNo);
			reviewFormService.insertRev(review);
		}
		//��ɾ��
		commonsController.delFile(titleNo, CommonsController.�����);
		//�ڱ���
		commonController.exportPDFPS(1,titleNo,request);
		//���������Ϣ���浽���ݿ�
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("�����");
		//����·��
		String path = "upload";
		String filePath = path+"/XB/"+titleNo;
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
		//return "redirect:toPersonalTask";
	}*/
	@RequestMapping("insertReview")
	@Transactional
	@ResponseBody
	public int insertReview(ReviewForm review,String taskId,HttpSession session) throws Exception{
		//����taskId��ȡҵ��id
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//��ȡ��˾��Ϣ
		ApplicationForm af=applicationFormService.getAppFormById(bid);
		String titleNo=af.getTitleno();
		//�����id;
		int id=-1;
		//���ݹ�˾��title_id����ys_syndic
		ReviewForm getReview=reviewFormService.getReviewByBId(bid);
		if(getReview!=null){//�ж��Ƿ����
			AddLog.addLog(Log.MODIFY,"�޸�'"+af.getEnterprisename()+"'��������ҵ��������");
			review.setId(getReview.getId());
			reviewFormService.updateRev(review);
			id=getReview.getId();
		}else{
			AddLog.addLog(Log.ADD,"����'"+af.getEnterprisename()+"'��������ҵ��������");
			review.setCreateDate(new Date());//�����������Ϣ
			review.setPid(bid);//���ù�˾��id,��֪�ֶ��Ƿ���ȷ
			review.setEnterpriseName(af.getEnterprisename());
			review.setChiCenter(af.getBranchName());
			review.setTitleno(titleNo);
			reviewFormService.insertRev(review);
			id=review.getId();
		}
		//��ɾ��
//		commonsController.delFile(titleNo, CommonsController.�����);
//		//�ڱ���
//		commonController.exportPDFPS(1,titleNo,request);
//		//���������Ϣ���浽���ݿ�
//		UploadFile uFile = new UploadFile();
//		uFile.setUpdescribe("�����");
//		//����·��
//		String path = "upload";
//		String filePath = path+"/XB/"+titleNo;
//		String fileName = titleNo+"����"+".pdf";
//		filePath = filePath+"/"+fileName;
//		uFile.setUprul(filePath);
//		//���ô�С
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
		//����taskId��ȡҵ��id
		int bid=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//��ȡ��˾��Ϣ
		ApplicationForm af=applicationFormService.getAppFormById(bid);
		String titleNo=af.getTitleno();
		//��ɾ��
		commonsController.delFile(titleNo, CommonsController.�����);
		//�ڱ���
		commonController.exportPDFPS(1,titleNo,request);
		//���������Ϣ���浽���ݿ�
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe("�����");
		//����·��
		String path = "upload";
		String filePath = path+"/XB/"+titleNo;
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
	
	@RequestMapping("applyReview")
	@ResponseBody
	public void applyReview(String taskId,MultipartFile file,HttpSession session,HttpServletRequest request){
		/*//������ϴ�
		if(!(file.isEmpty())){
			applicationController.delFile("xb_ps_"+psid);
			applicationController.uploadFile(file,"xb_ps_"+psid,titleNo);
		}
		//��ɸ�������
		applicationController.climeAndcompleteTask(taskId,"��","������������Ա","fzjg","true",-1,session);
		//return "application/review/personalTask";*/
		int id=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm appFormById = applicationFormService.getAppFormById(id);
		String titleNo=appFormById.getTitleno();
		AddLog.addLog(Log.IMPORT,"�ϴ������������ɶ�'"+appFormById.getEnterprisename()+"'�°�ҵ�����������");
	//������ϴ�
		if(!(file.isEmpty())){
			commonsController.delFile(titleNo,CommonsController.�����_����);
			commonsController.uploadFile(file,titleNo,CommonsController.�����_����,request);
		}
		ApplicationForm app=applicationFormService.getLastAppFormBytitleNo(titleNo);
		app.setSyndicResult(reviewFormService.getReviewByTitleNo(titleNo).getSyndic());
		applicationFormService.updateApp(app);
		appCommonsController.completeTask(taskId, "", "true", 7);
	}
	
	@RequestMapping("/completePersonlTask")
	@ResponseBody
	public void completePersonlTask(String taskId,String comment,String role,String candidate,String result,int status){
		//�������
		if(!("��".equals(comment))&&StringUtils.isNotBlank(comment))
			workflowUtil.addComment(taskId,comment);
		//����taskId��ȡҵ��id
		int id=Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		//�޸�ҵ��״̬
		if(status>0)
			applicationFormService.updateStatus(status,id);
		
		//����ҵ��
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
	
	//��λ�ȡδ�ύ��ҵ���Լ����˻�����ҵ��
	@RequestMapping("getReturnTasks")
	@ResponseBody
	public Object getReturnTasks(String pointName,int[] status,HttpSession session){//�鿴���˻ص�ҵ��
		AddLog.addLog(Log.QUERY,"��ѯ�������δͨ���˻ص���������Ϣ");
		//��ȡ�û�����ʵ����
		ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
		String userName=activeUser.getRealname();
		String branchId=activeUser.getRamusCenter();
		
		//���ݽڵ�����ƻ���û�����ȡ����
		List<Task> tasks = workflowUtil.findGroupTaskListByName(pointName, userName);
		//���������ȡҵ��
		Map<String,String> busAndTaskId = workflowUtil.getTaskAndBussIdByTask(tasks);
		List<ApplicationForm> afList= applicationFormService.findBusinessByTasks(busAndTaskId.keySet(),branchId);
		List<ApplicationForm> forms= new ArrayList<ApplicationForm>();
		for(int i=0;i<afList.size();i++){
			//����ҵ��id��������id����������id���õ�ҵ��id��
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
	
	//�鿴�����
	@RequestMapping("toView")
	public String toView(String titleNo,ModelMap model){
		model.addAttribute("item", titleNo);
		return "common/viewReview";//��û��
	}
	
	//��ɱ������ĺ�׼������
	@RequestMapping("completeTask")
	public String completeTask(String taskId,boolean result,String comment){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		int status=14;
		String resu="true";
		if(!result){
			status=13;
			resu="false";
			AddLog.addLog(Log.ADD,"�������ĺ�׼'"+af.getEnterprisename()+"'���°�ҵ�񲵻�");
		}else{
			AddLog.addLog(Log.ADD,"�������ĺ�׼'"+af.getEnterprisename()+"'���°�ҵ��ͨ��");
		}
		appCommonsController.completeTask(taskId, comment, resu, status);
		return "application/checkMsg/personalTask";
	}
	
	//��ɱ������ĺ�׼������
	@RequestMapping("completeCommonTask")
	public String completeTask(String taskId,boolean result,String comment,String backPage){
		int id = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(id);
		int status=0;
		String url="";
		String resu="true";
		if("��׼".equals(backPage)){
			url="application/recheck/personalTask";
			if(result){
				status=16;
				AddLog.addLog(Log.ADD,"����������׼'"+af.getEnterprisename()+"'���°�ҵ��ͨ��");
			}else{
				status=15;
				resu="false";
				AddLog.addLog(Log.ADD,"����������׼'"+af.getEnterprisename()+"'���°�ҵ�񲵻�");
			}
			appCommonsController.completeTask(taskId, comment, resu, status);
		}
		if("��֤".equals(backPage)){
			url="application/common/issuing";
			status=17;
		}
		return url;
	}
}
