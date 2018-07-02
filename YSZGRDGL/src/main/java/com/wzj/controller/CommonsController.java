package com.wzj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.FormChange;
import com.wzj.pojo.Log;
import com.wzj.pojo.UploadFile;
import com.wzj.service.alternation.impl.FormChangeService;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.util.AddLog;
import com.wzj.util.ExportUtil;
import com.wzj.util.UploadFileUtil;

@Controller
@RequestMapping("commons")
public class CommonsController {
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private ApplicationFormService applicationFormService;
	@Autowired
	private FormChangeService formChangeService;
	
	public final static String 营业执照="1";
	public final static String 印刷经营许可证="2";
	public final static String 质量手册="3";
	public final static String 申请表="4";
	public final static String 申请表_盖章="5";
	public final static String 评审表="6";
	public final static String 评审表_盖章="7";
	public final static String 样品="8";
	public final static String 分中心检测报告="9";
	public final static String 中心检测报告="10";
	public final static String 变更_其他材料="11";
	public final static String 中心审核结果="12";
	
	//基本文件上传
	@RequestMapping("uploadFile")
	@ResponseBody
	public int uploadFile(MultipartFile mfile,String titleNo,String describeId,HttpServletRequest request){
		UploadFileUtil uploadFileUtil = new UploadFileUtil();
		UploadFile uFile = new UploadFile();
		Map<String,String> map=null;
		//如果是上传申请表或者评审表，为了防止名字重复则在上传的文件名后加上"_盖章"
		if(describeId.equals("5")||describeId.equals("7")){
			String fileName=mfile.getOriginalFilename();
			String newName=fileName.substring(0,fileName.lastIndexOf("."))+"_盖章版"
					+fileName.substring(fileName.lastIndexOf("."),fileName.length());;
			map=uploadFileUtil.uploadFile(mfile,titleNo,newName,request);
		}else{
			map=uploadFileUtil.uploadFile(mfile,titleNo,request);
		}
		if(map==null)return -1;//上传失败
		String describe="";
		switch(describeId){
			case "1":describe="营业执照";break;
			case "2":describe="印刷经营许可证";break;
			case "3":describe="质量手册";break;
			case "4":describe="申请表";break;
			case "5":describe="申请表_盖章";break;
			case "6":describe="评审表";break;
			case "7":describe="评审表_盖章";break;
			case "11":describe="变更_其他材料";break;
			default:return 0;
		}
		uFile.setUpdescribe(describe);
		uFile.setUprul(map.get("path"));
		uFile.setUpsize(map.get("size"));
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setDescribeId(describeId);
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
		return 1;
	}
	
	//样品文件、样品检测报告上传
	@RequestMapping("uploadYPSampleFile")
	@ResponseBody
	public void uploadYPSampleFile(MultipartFile mfile,String titleNo,String describe,String sampleId,HttpServletRequest request,List<UploadFile>  uf){
		ApplicationForm app=applicationFormService.getLastAppFormBytitleNo(titleNo);
		AddLog.addLog(Log.IMPORT,"向流'"+app.getEnterprisename()+"'的业务上传文件");
		UploadFileUtil uploadFileUtil = new UploadFileUtil();
		UploadFile uFile = new UploadFile();
		Map<String,String> map=null;
		map=uploadFileUtil.uploadYPFile(mfile,titleNo,request,uf,describe,sampleId);
		uFile.setUpdescribe(describe);
		uFile.setUprul(map.get("path"));
		uFile.setUpsize(map.get("size"));
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setDescribeId(sampleId);
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
	}
	@RequestMapping("uploadSampleFile")
	@ResponseBody
	public void uploadSampleFile(MultipartFile mfile,String titleNo,String describe,String sampleId,HttpServletRequest request){
		AddLog.addLog(Log.IMPORT,"向流水号为："+titleNo+"的业务上传文件");
		UploadFileUtil uploadFileUtil = new UploadFileUtil();
		UploadFile uFile = new UploadFile();
		Map<String,String> map=null;
		map=uploadFileUtil.uploadFile(mfile,titleNo,request);
		uFile.setUpdescribe(describe);
		uFile.setUprul(map.get("path"));
		uFile.setUpsize(map.get("size"));
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setDescribeId(sampleId);
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
	}
	//批量上传
	@RequestMapping("PLuploadSampleFile")
	@ResponseBody
	public void PLuploadSampleFile(MultipartFile mfile,String titleNo,String describe,String sampleId,HttpServletRequest request){
		List<UploadFile>  uf=getSample(titleNo,describe,sampleId);
		ApplicationForm af=applicationFormService.getLastAppFormBytitleNo(titleNo);
		if(af!=null){
			AddLog.addLog(Log.IMPORT,"向'"+af.getEnterprisename()+"'的业务上传多个文件");
		}else{
			FormChange fc=formChangeService.getByTaskId(titleNo);
			if(fc!=null)
				AddLog.addLog(Log.IMPORT,"向'"+fc.getCompanynameOld()+"'的业务上传多个文件");
		}
		UploadFileUtil uploadFileUtil = new UploadFileUtil();
		UploadFile uFile = new UploadFile();
		Map<String,String> map=null;
		if(describe.equals("样品附件")||describe.equals("分中心检测报告")||describe.equals("中心检测报告")){
			map=uploadFileUtil.uploadYPFile(mfile,titleNo,request,uf,describe,sampleId);
		}else{
			map=uploadFileUtil.uploadFile(mfile,titleNo,request);
		}
		uFile.setUpdescribe(describe);
		uFile.setUprul(map.get("path"));
		uFile.setUpsize(map.get("size"));
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setDescribeId(sampleId);
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
	}
	
	//判断文件是否存在
	@RequestMapping("fileExists")
	@ResponseBody
	public Boolean fileExists(String titleNo,String type,HttpServletRequest request,HttpServletResponse response) {
		Boolean flag=false;
		String path = request.getSession().getServletContext().getRealPath("./");
		String filePath = path+"/"+uploadFileService.getPath(titleNo,type);
		File file=new File(filePath);
		if(file.exists()){
			flag=true;
		}
		return flag;
	}
	//基本文件下载
	@RequestMapping("downloadFile")
	public void downloadFile(String titleNo,String type,HttpServletRequest request,HttpServletResponse response) {
		FormChange fc=formChangeService.getByTaskId(titleNo);
		String ywType=titleNo.substring(0,2);
		ApplicationForm af=applicationFormService.getLastAppFormBytitleNo(titleNo);
		if("XB".equals(ywType)||"FR".equals(ywType)){
			if(type.equals("4")){
				AddLog.addLog(Log.EXPORT,"下载'"+af.getEnterprisename()+"'的业务申请表");
			}else if(type.equals("6")){
				AddLog.addLog(Log.EXPORT,"下载'"+af.getEnterprisename()+"'的业务评审表");
			}
		}else{
			if(type.equals("4")){
				AddLog.addLog(Log.EXPORT,"下载'"+fc.getCompanynameOld()+"'的业务申请表");
			}else if(type.equals("6")){
				AddLog.addLog(Log.EXPORT,"下载'"+fc.getCompanynameOld()+"'的业务评审表");
			}
		}
		String path = request.getSession().getServletContext().getRealPath("./");
		String filePath = path+"/"+uploadFileService.getPath(titleNo,type);
		String[] array= filePath.split("/");
		String fileName = filePath.split("/")[array.length-1];
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			File file=new File(filePath);
			if(file.exists()){
				//获取一个流
				InputStream in = new FileInputStream(new File(filePath));
				//设置下载的相应头
				response.setHeader("content-disposition","attachment;fileName="+fileName);
				response.setCharacterEncoding("UTF-8");
				//获取response字节流
				OutputStream out = response.getOutputStream();
				byte[] b = new byte[1024];
				int len = -1;
				while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
					out.write(b,0,len);
				}
				out.close();
				in.close();
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	//基本文件下载
	@RequestMapping("downloadFileByPath")
	public void downloadFileByPath(String paths,HttpServletRequest request,HttpServletResponse response) {
		String path = request.getSession().getServletContext().getRealPath("./");
		String filePath = path+"/"+paths;
		String[] array= filePath.split("/");
		String fileName = filePath.split("/")[array.length-1];
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			InputStream in = new FileInputStream(new File(filePath));
			//设置下载的相应头
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//获取response字节流
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
				out.write(b,0,len);
			}
			out.close();
			in.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//样品文件、样品检测报告下载
	@RequestMapping("downloadSampleFile")
	public void downloadSampleFile(String titleNo,String sampleId,HttpServletRequest request,HttpServletResponse response) {
		String path = request.getSession().getServletContext().getRealPath("./");
		String filePath = path+"/"+uploadFileService.getPath(titleNo,sampleId);
		String[] array= filePath.split("/");
		String fileName = filePath.split("/")[array.length-1];
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			InputStream in = new FileInputStream(new File(filePath));
			//设置下载的相应头
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//获取response字节流
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
				out.write(b,0,len);
			}
			out.close();
			in.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//多文件打包下载
	@RequestMapping("download")
	public void downloadSampleFile(String titleNo,HttpServletRequest request,HttpServletResponse response) {
		ApplicationForm af=applicationFormService.getLastAppFormBytitleNo(titleNo);
		if(af!=null){
			AddLog.addLog(Log.EXPORT,"下载'"+af.getEnterprisename()+"'业务的所有资料");
		}else{
			FormChange fc=formChangeService.getByTaskId(titleNo);
			if(fc!=null)
				AddLog.addLog(Log.EXPORT,"下载'"+fc.getCompanynameOld()+"'业务的所有资料");
		}
		String path = request.getSession().getServletContext().getRealPath("./");
		String type=titleNo.substring(0,2);//获取XB/FR/BG
		String sourcePath=path+"/upload/"+type+"/"+titleNo;
		String fileName=titleNo+".zip";
		ExportUtil util=new ExportUtil();
		util.fileToZip(sourcePath, sourcePath, titleNo);
		String filePath=sourcePath+"/"+fileName;
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			InputStream in = new FileInputStream(new File(filePath));
			//设置下载的相应头
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//获取response字节流
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//将输入流循环写入到输出流】
				out.write(b,0,len);
			}
			out.close();
			in.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//文件的删除，根据titleNO和type删除
	@RequestMapping("delFile")
	@ResponseBody
	public void delFile(String titleNo,String type){
		//查询路径
		String path="";
		String p=System.getProperty("user.dir");
		
		if(type.equals("4")||type.equals("6")){
			path=uploadFileService.getPath(titleNo,type);
		}else{
			try {
				String projectPath=URLDecoder.decode(p, "UTF-8").replace("\\", "/");
				path=projectPath+"/src/main/webapp/"+uploadFileService.getPath(titleNo,type);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(path)){
			File file = new File(path);
			if(file.exists()){//删除文件
				file.delete();
			}
			//删除数据库中的数据
			uploadFileService.delete(titleNo,type);
		}
	}
	//删除整个文件夹
	@SuppressWarnings("null")
	@RequestMapping("delFiles")
	@ResponseBody
	public void delFiles(String titleNo,String type){
		//查询路径
		String path="";
		String files=uploadFileService.getPath(titleNo,type);
		String lastFile;
		String p=System.getProperty("user.dir");
		if(files!=null&&!files.equals("null")){
			try {
				String projectPath=URLDecoder.decode(p, "UTF-8").replace("\\", "/");
				lastFile=files.substring(0, files.lastIndexOf("/"));
				path=projectPath+"/src/main/webapp/"+lastFile;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(path)){
			File file = new File(path);
			if(file.exists()){//删除文件
				UploadFileUtil.deleteDir(file);
			}
			//删除数据库中的数据
			uploadFileService.delete(titleNo,type);
		}
	}
	//基本文件查看
	@RequestMapping("viewFile")
	@ResponseBody
	public Object viewFile(String titleNo,String type,HttpServletRequest request) {
		ApplicationForm lastAppFormBytitleNo = applicationFormService.getLastAppFormBytitleNo(titleNo);
		AddLog.addLog(Log.QUERY,"查询'"+lastAppFormBytitleNo.getEnterprisename()+"'业务的所有附件");
		return uploadFileService.getSampleAttach(titleNo,type);
	}
	
	
	//样品文件、样品检测报告上传。样品名+序号版本
		@RequestMapping("uploadSampleFileNew")
		@ResponseBody
		public void uploadSampleFileNew(MultipartFile mfile,String titleNo,String describe,String sampleId,String name){
			UploadFileUtil uploadFileUtil = new UploadFileUtil();
			UploadFile uFile = new UploadFile();
			Map<String,String> map=null;
			map=uploadFileUtil.uploadFileNew(mfile,titleNo,name);
			uFile.setUpdescribe(describe);
			uFile.setUprul(map.get("path"));
			uFile.setUpsize(map.get("size"));
			uFile.setCode(titleNo);
			uFile.setAvailability(true);
			uFile.setDescribeId(sampleId);
			uFile.setUploadtime(new Date());
			uploadFileService.insert(uFile);
		}
	//样品文件、样品检测报告获取
	@RequestMapping("getSampleAttach")
	@ResponseBody
	public Object getSampleAttach(String titleNo,String sampleId){
		ApplicationForm af=applicationFormService.getLastAppFormBytitleNo(titleNo);
		AddLog.addLog(Log.QUERY,"查询'"+af.getEnterprisename()+"'业务的附件");
		return uploadFileService.getSampleAttach(titleNo,sampleId);
	}
	
	//样品文件获取
	@RequestMapping("getSampleAttach2")
	@ResponseBody
	public Object getSampleAttach2(String titleNo,String sampleId){
		ApplicationForm af=applicationFormService.getLastAppFormBytitleNo(titleNo);
		AddLog.addLog(Log.QUERY,"查询'"+af.getEnterprisename()+"'业务的附件");
		return uploadFileService.getSampleAttach(titleNo,sampleId,"样品附件");
	}

	public void delFile(String titleNo, String describe, String type) {
		//查询路径
		String path=uploadFileService.getPath(titleNo,describe,type);
		if(StringUtils.isNotBlank(path)){
			File file = new File(path);
			if(file.exists()){//删除文件
				file.delete();
			}
			//删除数据库中的数据
			uploadFileService.delete(titleNo,describe,type);
		}
	}
	public List<UploadFile> getSample(String titlenum, String describe,String code) {
		return uploadFileService.getSample(titlenum,describe,code);
	}
	
	@RequestMapping("delFileById")
	@ResponseBody
	public void delFileById(String id,HttpServletRequest request){
		String path = request.getSession().getServletContext().getRealPath("/");
		path=path.replaceAll("/", "\\")+"/";
		UploadFile up=uploadFileService.getById(Integer.parseInt(id));
		path+=up.getUprul();
		File file=new File(path);
		if(file.exists()){
			file.delete();
		}
		uploadFileService.deleteById(id);
	}
}
