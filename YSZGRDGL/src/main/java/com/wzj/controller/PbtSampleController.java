package com.wzj.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.wzj.controller.application.AppCommonsController;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.Log;
import com.wzj.pojo.PbtSample;
import com.wzj.pojo.UploadFile;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.service.impl.PbtSampleService;
import com.wzj.util.AddLog;
import com.wzj.util.UploadFileUtil;
import com.wzj.util.WorkflowUtil;

@Controller
@RequestMapping("pbtSample")
public class PbtSampleController {
	@Autowired
	private PbtSampleService pbtSampleService;
	@Autowired
	private AppCommonsController appCommonsController;
	@Autowired
	private CommonsController commonsController;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private WorkflowUtil workflowUtil;
	
	@RequestMapping("insert")
	@ResponseBody
	public void insert(PbtSample ps,ModelMap model){
		AddLog.addLog(Log.ADD,"给'"+ps.getUf_0047225()+"'新办业务添加样品'"+ps.getUf_0047228()+"'");
		pbtSampleService.insert(ps);
		//样品编号
//		String code=ps.getUf_sample_code();
//		if(ps.getMfile()!=null){//如果存在文件
//			commonsController.uploadSampleFile(ps.getMfile(), titlenum, "样品附件", code);
//		}
		model.addAttribute("item", ps.getUf_0047225());
	}
	
	@RequestMapping("createSampleId")
	@ResponseBody
	public String createSampleId(String titleNo){
		String maxId=pbtSampleService.getMaxId(titleNo);
		if(StringUtils.isNotBlank(maxId)){
			String num=maxId.substring(maxId.lastIndexOf("-")+1,maxId.length());
			int n=Integer.parseInt(num)+1;
			if(n<10){
				maxId=titleNo+"-0"+n;
			}else{
				maxId=titleNo+"-"+n;
			}
		}else{
			maxId=titleNo+"-01";
		}
		return maxId;
	}
	
	@RequestMapping("insertSample")
	@ResponseBody
	public void insertSample(String titlenum,String code,MultipartFile mfile,HttpServletRequest request,String describe){
		ApplicationForm af=applicationFormService.getLastAppFormBytitleNo(titlenum);
		AddLog.addLog(Log.ADD,"向'"+af.getEnterprisename()+"'样品编号为:'"+code+"'样品上传分中心检测报告");
		List<UploadFile>  uf=commonsController.getSample(titlenum,describe,code);
		if(!mfile.isEmpty()){//如果存在文件
			commonsController.uploadYPSampleFile(mfile, titlenum, describe, code,request,uf);
		}
	}
	@RequestMapping("updateSample")
	@ResponseBody
	public void updateSample(MultipartFile mfile,String titlenum,String code,HttpServletRequest request){
		List<UploadFile>  uf=commonsController.getSample(titlenum,"样品附件",code);
		String filePath="";
		String path="";
		String projectPath = "";
		String p=System.getProperty("user.dir");
		if(mfile.getOriginalFilename().equals("")){
			mfile=null;
			for (UploadFile uploadFile : uf) {
				if(StringUtils.isNotBlank(uploadFile.getUprul())){
					try {
						projectPath = URLDecoder.decode(p, "UTF-8").replace("\\", "/");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
//					filePath=uploadFile.getUprul().substring(0, uploadFile.getUprul().lastIndexOf("/"));
					String tmp = uploadFile.getUprul().substring(0,uploadFile.getUprul().lastIndexOf("/"));
					filePath = tmp.substring(0,tmp.lastIndexOf("/"));
					path=projectPath+"/src/main/webapp/"+filePath;
				}
			}
			File file = new File(path);
			if(file.exists()){//删除文件
				UploadFileUtil.deleteDir(file);
			}
			//删除数据库中的数据
			uploadFileService.deleteByDescribeId(code);
		}else{
			for (UploadFile uploadFile : uf) {
				if(StringUtils.isNotBlank(uploadFile.getUprul())){
					try {
						projectPath = URLDecoder.decode(p, "UTF-8").replace("\\", "/");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
//					filePath=uploadFile.getUprul().substring(0, uploadFile.getUprul().lastIndexOf("/"));
					String tmp = uploadFile.getUprul().substring(0,uploadFile.getUprul().lastIndexOf("/"));
					filePath = tmp.substring(0,tmp.lastIndexOf("/"));
					path=projectPath+"/src/main/webapp/"+filePath;
				}
			}
			File file = new File(path);
			if(file.exists()){//删除文件
				UploadFileUtil.deleteDir(file);
			}
			//删除数据库中的数据
			uploadFileService.deleteByDescribeId(code);
			commonsController.uploadYPSampleFile(mfile, titlenum, "样品附件", code,request,uf);
		}
	}
	@RequestMapping("update")
	@ResponseBody
	public void update(PbtSample ps,ModelMap model){
		AddLog.addLog(Log.MODIFY,"修改'"+ps.getUf_0047225()+"'新办业务样品'"+ps.getUf_0047228()+"'的信息");
//		MultipartFile mfile = ps.getMfile();
//		if(mfile.getOriginalFilename().equals("")){
//			ps.setMfile(null);
//		}
		pbtSampleService.update(ps);
		model.addAttribute("item", ps.getUf_0047225());
	}
	
	@RequestMapping("delete")
	public String delete(String code,String titleNo,String describe,ModelMap model){
		AddLog.addLog(Log.DELETE,"删除样品编号为'"+code+"'的样品信息");
		pbtSampleService.delete(code);
		List<UploadFile>  uf=commonsController.getSample(titleNo,describe,code);
		String filePath="";
		String path="";
		String projectPath = "";
		String p=System.getProperty("user.dir");
		for (UploadFile uploadFile : uf) {
			if(StringUtils.isNotBlank(uploadFile.getUprul())){
				try {
					projectPath = URLDecoder.decode(p, "UTF-8").replace("\\", "/");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String tmp = uploadFile.getUprul().substring(0,uploadFile.getUprul().lastIndexOf("/"));
				filePath = tmp.substring(0,tmp.lastIndexOf("/"));
//				filePath=uploadFile.getUprul().substring(0, uploadFile.getUprul().lastIndexOf("/"));
				path=projectPath+"/src/main/webapp/"+filePath;
			}
		}
		File file = new File(path);
		if(file.exists()){//删除文件
			UploadFileUtil.deleteDir(file);
		}
		//删除数据库中的数据
		uploadFileService.deleteByDescribeId(code);
		//model.addAttribute("item", mis.getCompanyid());
		//model.addAttribute("item2", mis.getBranchid());
		return "common/sampleinfo";
	}
	
	@RequestMapping("toSamples")
	public String toSamples(String comName,String titleNo,String taskId,ModelMap model){
		AddLog.addLog(Log.QUERY,"查看'"+comName+"'的样品信息");
		model.addAttribute("item", comName);
		model.addAttribute("titleNo", titleNo);
		model.addAttribute("taskId", taskId);
		if(titleNo.startsWith("XB")){
			model.addAttribute("resultPath", "application");
		}else{
			model.addAttribute("resultPath", "recognition");
		}
		return "common/sampleinfo";
	}
	
	@RequestMapping("toSampleReports")
	public String toSampleReports(String titleNo,String taskId,String comName,ModelMap model){
		AddLog.addLog(Log.QUERY,"查看'"+comName+"'的样品信息");
		model.addAttribute("titleNo", titleNo);
		model.addAttribute("taskId", taskId);
		model.addAttribute("item",comName);
		return "common/sampleReport";
	}
	
	@RequestMapping("uploadSampleReport")
	@ResponseBody
	public void uploadSampleReport(String titleNo,String sampleId,String describe,MultipartFile file,HttpServletRequest request){
		ApplicationForm af=applicationFormService.getLastAppFormBytitleNo(titleNo);
		AddLog.addLog(Log.ADD,"给'"+af.getEnterprisename()+"'的样品编号为'"+sampleId+"'的样品上传报告");
		//上传检测报告
		if(file!=null){//如果存在文件
			//先删除
			//commonsController.delFile(titleNo, describe,sampleId);
			//上传
			commonsController.uploadSampleFile(file, titleNo, describe, sampleId,request);
		}
	}
	@RequestMapping("uploadSampleForReport")
	public String uploadSampleForReport(String titleNo,String sampleId,String describe,MultipartFile file,ModelMap model,String enterpriseName,HttpServletRequest request){
		AddLog.addLog(Log.ADD,"给'"+enterpriseName+"'的样品编号为'"+sampleId+"'的样品上传报告");
		//上传检测报告
		if(file!=null){//如果存在文件
			//先删除
			//commonsController.delFile(titleNo, describe,sampleId);
			//上传
			commonsController.uploadSampleFile(file, titleNo, describe, sampleId,request);
		}
		model.addAttribute("item", enterpriseName);
		model.addAttribute("titleNo", titleNo);
		return "common/FRsampleHZ";
	}
	
	@RequestMapping("uploadSampleReportNew")
	@ResponseBody
	public void uploadSampleReportNew(String titleNo,String sampleId,String describe,MultipartFile file){
		ApplicationForm af=applicationFormService.getLastAppFormBytitleNo(titleNo);
		AddLog.addLog(Log.ADD,"给'"+af.getEnterprisename()+"'的样品编号为"+sampleId+"的样品批量上传报告");
		String fileName = file.getOriginalFilename();
		String originalFileName = fileName.substring(fileName.lastIndexOf("."),fileName.length());
		String num = pbtSampleService.getNum(sampleId,describe);
		String name=sampleId+"-"+describe+"-"+num+originalFileName;
		//上传检测报告
		if(file!=null){//如果存在文件
			//先删除
			//commonsController.delFile(titleNo, describe,sampleId);
			//上传
			commonsController.uploadSampleFileNew(file, titleNo, describe, sampleId , name);
		}
	}
	
	@RequestMapping("getSampleByCom")
	@ResponseBody
	public Object getSampleByCom(String comName){
		return pbtSampleService.getSampleByCom(comName);
	}
	
	@RequestMapping("getSampleCount")
	@ResponseBody
	public int getSampleCount(String code){
		return pbtSampleService.getSampleCount(code);
	}
	
	@RequestMapping("getSampleByCode")
	@ResponseBody
	public Object getSampleByCode(String code,String comName){
		AddLog.addLog(Log.QUERY,"查询'"+comName+"'的样品编号为'"+code+"'的样品信息");
		return pbtSampleService.getSampleByCode(code,comName);
	}
	
	@RequestMapping("setSampleResult")
	@ResponseBody
	public void setSampleResult(String code,String comName,boolean result){
		pbtSampleService.setSampleResult(code,comName,result);
	}
	
	@RequestMapping("completeTask")
	public String completeTask(String taskId,String comment,String resultPath){
		int appId = Integer.parseInt(workflowUtil.findBussinessIdByTaskId(taskId));
		ApplicationForm af=applicationFormService.getAppFormById(appId);
		AddLog.addLog(Log.ADD,"提交'"+af.getEnterprisename()+"'的样品意见");
		if("application".equals(resultPath)){
			resultPath="application/enroll/personalTask";
			appCommonsController.completeTask(taskId, comment, "true", 9);
		}else{
			resultPath="recognition/enroll/tasks";
			appCommonsController.completeTask(taskId, comment, "true", 26);
		}
		return resultPath;
	}
	//复认样品
	@RequestMapping("toFRsample")
	public String toFRsample(String comName,String titleNo,ModelMap model){
		AddLog.addLog(Log.QUERY,"查看'"+comName+"'的样品信息");
		model.addAttribute("item", comName);
		model.addAttribute("titleNo", titleNo);
		return "common/FRsample";
	}
	
	//核准
	@RequestMapping("toFRsampleHZ")
	public String toFRsampleHZ(String comName,String titleNo,ModelMap model){
		AddLog.addLog(Log.QUERY,"查看'"+comName+"'的样品信息");
		model.addAttribute("item", comName);
		model.addAttribute("titleNo", titleNo);
		return "common/FRsampleHZ";
	}
	
	//批准
	@RequestMapping("toFRsamplePZ")
	public String toFRsamplePZ(String comName,String titleNo,ModelMap model){
		AddLog.addLog(Log.QUERY,"查看'"+comName+"'的样品信息");
		model.addAttribute("item", comName);
		model.addAttribute("titleNo", titleNo);
		return "common/FRsamplePZ";
	}
	
	//检查样品是否全部上传检验报告
	@RequestMapping("checkUploadedReport")
	@ResponseBody
	public Object checkUploadedReport(String titleNo,String UpDescribe,String sampleIds){
		List<UploadFile> files=new ArrayList<>();
		String[] sIds=sampleIds.split(",");
		files=uploadFileService.checkUploadedReport(titleNo,UpDescribe,sampleIds);
		JSONObject jsonObject=new JSONObject();
		for(UploadFile uploadFile:files){
			String describeId=uploadFile.getDescribeId();
			for(int i=0;i<sIds.length;i++){
				if(describeId.equals(sIds[i])){
					sIds[i]="";
				}
			}
		}
		String result="样品编号为";
		for(int i=0;i<sIds.length;i++){
			if(!sIds[i].equals("")){
				result=result+sIds[i]+",";
			}
		}
		if("样品编号为".equals(result)){
			jsonObject.put("result", "true");
			return jsonObject;
		}else{
			result=result.substring(0, result.length()-1)+"的样品未上传检验报告";
			jsonObject.put("result", result);
			return jsonObject;
		}
	}
}
