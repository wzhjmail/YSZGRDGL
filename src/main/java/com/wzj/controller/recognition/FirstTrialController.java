package com.wzj.controller.recognition;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzj.controller.CommonsController;
import com.wzj.controller.application.CommonController;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.Log;
import com.wzj.pojo.UploadFile;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.util.AddLog;

@Controller
@RequestMapping("recognition/firstTrial")
public class FirstTrialController {

	@Autowired
	private CommonsController commonsController;
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private CommonController commonController;
	@Autowired
	private UploadFileService uploadFileService;
	@RequestMapping("toTasks")
	public String toTasks(){
		AddLog.addLog(Log.QUERY,"��ѯ���д����ĸ���ҵ��������Ϣ");
		return "recognition/firstTrial/tasks";
	}
	
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		AddLog.addLog(Log.QUERY,"��ѯ�����˻صĸ���ҵ��������Ϣ");
		return "recognition/firstTrial/returnTask";
	}
	//�޸�����
		@RequestMapping("update")
		public String update(ApplicationForm app,HttpServletRequest request){//����
			String titleNo=app.getTitleno();
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
			//����ύ�������ֲ�
			if(!(app.getQuality().isEmpty())){
				commonsController.delFile(titleNo,CommonsController.�����ֲ�);
				commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.�����ֲ�,request);
			}
			commonsController.delFile(titleNo, CommonsController.�����);
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
			System.out.println(dir.getTotalSpace()/1024+"kb");
			uFile.setUpsize(dir.getTotalSpace()/1024+"kb");
			
			uFile.setCode(titleNo);
			uFile.setAvailability(true);
			uFile.setDescribeId("4");
			uFile.setUploadtime(new Date());
			uploadFileService.insert(uFile);
			applicationFormService.updateApp(app);
			commonController.savePdf(2,app.getId(),request);//ֱ�ӱ���Ϊpdf
			return "redirect:toTasks.action";
		}
}
