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
		AddLog.addLog(Log.QUERY,"查询所有待审查的复认业务申请信息");
		return "recognition/firstTrial/tasks";
	}
	
	@RequestMapping("toReturnTask")
	public String toReturnTask(){
		AddLog.addLog(Log.QUERY,"查询所有退回的复认业务申请信息");
		return "recognition/firstTrial/returnTask";
	}
	//修改申请
		@RequestMapping("update")
		public String update(ApplicationForm app,HttpServletRequest request){//保存
			String titleNo=app.getTitleno();
			//如果提交的有印刷资格证，则先删除文件和数据，后添加
			if(!(app.getCertificate().isEmpty())){
				commonsController.delFile(titleNo,CommonsController.印刷经营许可证);
				commonsController.uploadFile(app.getCertificate(),titleNo,CommonsController.印刷经营许可证,request);
			}
			//如果提交的有营业执照，则先删除文件和数据，后添加
			if(!(app.getBusiness().isEmpty())){
				commonsController.delFile(titleNo,CommonsController.营业执照);
				commonsController.uploadFile(app.getBusiness(),titleNo,CommonsController.营业执照,request);
			}
			//如果提交了质量手册
			if(!(app.getQuality().isEmpty())){
				commonsController.delFile(titleNo,CommonsController.质量手册);
				commonsController.uploadFile(app.getQuality(),titleNo,CommonsController.质量手册,request);
			}
			commonsController.delFile(titleNo, CommonsController.申请表);
			UploadFile uFile = new UploadFile();
			uFile.setUpdescribe("申请表");
			
			//设置路径
			String path = "upload";
			String filePath = path+"/FR/"+app.getTitleno();
			String fileName = app.getTitleno()+"申请"+".pdf";
			filePath = filePath+"/"+fileName;
			uFile.setUprul(filePath);
			//设置大小
			File dir=new File(request.getSession().getServletContext().getRealPath("/upload")+"/"+fileName);
			System.out.println(dir.getTotalSpace()/1024+"kb");
			uFile.setUpsize(dir.getTotalSpace()/1024+"kb");
			
			uFile.setCode(titleNo);
			uFile.setAvailability(true);
			uFile.setDescribeId("4");
			uFile.setUploadtime(new Date());
			uploadFileService.insert(uFile);
			applicationFormService.updateApp(app);
			commonController.savePdf(2,app.getId(),request);//直接保存为pdf
			return "redirect:toTasks.action";
		}
}
