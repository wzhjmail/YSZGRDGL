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
	
	public final static String Ӫҵִ��="1";
	public final static String ӡˢ��Ӫ���֤="2";
	public final static String �����ֲ�="3";
	public final static String �����="4";
	public final static String �����_����="5";
	public final static String �����="6";
	public final static String �����_����="7";
	public final static String ��Ʒ="8";
	public final static String �����ļ�ⱨ��="9";
	public final static String ���ļ�ⱨ��="10";
	public final static String ���_��������="11";
	public final static String ������˽��="12";
	
	//�����ļ��ϴ�
	@RequestMapping("uploadFile")
	@ResponseBody
	public int uploadFile(MultipartFile mfile,String titleNo,String describeId,HttpServletRequest request){
		UploadFileUtil uploadFileUtil = new UploadFileUtil();
		UploadFile uFile = new UploadFile();
		Map<String,String> map=null;
		//������ϴ��������������Ϊ�˷�ֹ�����ظ������ϴ����ļ��������"_����"
		if(describeId.equals("5")||describeId.equals("7")){
			String fileName=mfile.getOriginalFilename();
			String newName=fileName.substring(0,fileName.lastIndexOf("."))+"_���°�"
					+fileName.substring(fileName.lastIndexOf("."),fileName.length());;
			map=uploadFileUtil.uploadFile(mfile,titleNo,newName,request);
		}else{
			map=uploadFileUtil.uploadFile(mfile,titleNo,request);
		}
		if(map==null)return -1;//�ϴ�ʧ��
		String describe="";
		switch(describeId){
			case "1":describe="Ӫҵִ��";break;
			case "2":describe="ӡˢ��Ӫ���֤";break;
			case "3":describe="�����ֲ�";break;
			case "4":describe="�����";break;
			case "5":describe="�����_����";break;
			case "6":describe="�����";break;
			case "7":describe="�����_����";break;
			case "11":describe="���_��������";break;
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
	
	//��Ʒ�ļ�����Ʒ��ⱨ���ϴ�
	@RequestMapping("uploadYPSampleFile")
	@ResponseBody
	public void uploadYPSampleFile(MultipartFile mfile,String titleNo,String describe,String sampleId,HttpServletRequest request,List<UploadFile>  uf){
		ApplicationForm app=applicationFormService.getLastAppFormBytitleNo(titleNo);
		AddLog.addLog(Log.IMPORT,"����'"+app.getEnterprisename()+"'��ҵ���ϴ��ļ�");
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
		AddLog.addLog(Log.IMPORT,"����ˮ��Ϊ��"+titleNo+"��ҵ���ϴ��ļ�");
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
	//�����ϴ�
	@RequestMapping("PLuploadSampleFile")
	@ResponseBody
	public void PLuploadSampleFile(MultipartFile mfile,String titleNo,String describe,String sampleId,HttpServletRequest request){
		List<UploadFile>  uf=getSample(titleNo,describe,sampleId);
		ApplicationForm af=applicationFormService.getLastAppFormBytitleNo(titleNo);
		if(af!=null){
			AddLog.addLog(Log.IMPORT,"��'"+af.getEnterprisename()+"'��ҵ���ϴ�����ļ�");
		}else{
			FormChange fc=formChangeService.getByTaskId(titleNo);
			if(fc!=null)
				AddLog.addLog(Log.IMPORT,"��'"+fc.getCompanynameOld()+"'��ҵ���ϴ�����ļ�");
		}
		UploadFileUtil uploadFileUtil = new UploadFileUtil();
		UploadFile uFile = new UploadFile();
		Map<String,String> map=null;
		if(describe.equals("��Ʒ����")||describe.equals("�����ļ�ⱨ��")||describe.equals("���ļ�ⱨ��")){
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
	
	//�ж��ļ��Ƿ����
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
	//�����ļ�����
	@RequestMapping("downloadFile")
	public void downloadFile(String titleNo,String type,HttpServletRequest request,HttpServletResponse response) {
		FormChange fc=formChangeService.getByTaskId(titleNo);
		String ywType=titleNo.substring(0,2);
		ApplicationForm af=applicationFormService.getLastAppFormBytitleNo(titleNo);
		if("XB".equals(ywType)||"FR".equals(ywType)){
			if(type.equals("4")){
				AddLog.addLog(Log.EXPORT,"����'"+af.getEnterprisename()+"'��ҵ�������");
			}else if(type.equals("6")){
				AddLog.addLog(Log.EXPORT,"����'"+af.getEnterprisename()+"'��ҵ�������");
			}
		}else{
			if(type.equals("4")){
				AddLog.addLog(Log.EXPORT,"����'"+fc.getCompanynameOld()+"'��ҵ�������");
			}else if(type.equals("6")){
				AddLog.addLog(Log.EXPORT,"����'"+fc.getCompanynameOld()+"'��ҵ�������");
			}
		}
		String path = request.getSession().getServletContext().getRealPath("./");
		String filePath = path+"/"+uploadFileService.getPath(titleNo,type);
		String[] array= filePath.split("/");
		String fileName = filePath.split("/")[array.length-1];
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			File file=new File(filePath);
			if(file.exists()){
				//��ȡһ����
				InputStream in = new FileInputStream(new File(filePath));
				//�������ص���Ӧͷ
				response.setHeader("content-disposition","attachment;fileName="+fileName);
				response.setCharacterEncoding("UTF-8");
				//��ȡresponse�ֽ���
				OutputStream out = response.getOutputStream();
				byte[] b = new byte[1024];
				int len = -1;
				while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
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

	//�����ļ�����
	@RequestMapping("downloadFileByPath")
	public void downloadFileByPath(String paths,HttpServletRequest request,HttpServletResponse response) {
		String path = request.getSession().getServletContext().getRealPath("./");
		String filePath = path+"/"+paths;
		String[] array= filePath.split("/");
		String fileName = filePath.split("/")[array.length-1];
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//��ȡһ����
			InputStream in = new FileInputStream(new File(filePath));
			//�������ص���Ӧͷ
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//��ȡresponse�ֽ���
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
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
	//��Ʒ�ļ�����Ʒ��ⱨ������
	@RequestMapping("downloadSampleFile")
	public void downloadSampleFile(String titleNo,String sampleId,HttpServletRequest request,HttpServletResponse response) {
		String path = request.getSession().getServletContext().getRealPath("./");
		String filePath = path+"/"+uploadFileService.getPath(titleNo,sampleId);
		String[] array= filePath.split("/");
		String fileName = filePath.split("/")[array.length-1];
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//��ȡһ����
			InputStream in = new FileInputStream(new File(filePath));
			//�������ص���Ӧͷ
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//��ȡresponse�ֽ���
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
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
	
	//���ļ��������
	@RequestMapping("download")
	public void downloadSampleFile(String titleNo,HttpServletRequest request,HttpServletResponse response) {
		ApplicationForm af=applicationFormService.getLastAppFormBytitleNo(titleNo);
		if(af!=null){
			AddLog.addLog(Log.EXPORT,"����'"+af.getEnterprisename()+"'ҵ�����������");
		}else{
			FormChange fc=formChangeService.getByTaskId(titleNo);
			if(fc!=null)
				AddLog.addLog(Log.EXPORT,"����'"+fc.getCompanynameOld()+"'ҵ�����������");
		}
		String path = request.getSession().getServletContext().getRealPath("./");
		String type=titleNo.substring(0,2);//��ȡXB/FR/BG
		String sourcePath=path+"/upload/"+type+"/"+titleNo;
		String fileName=titleNo+".zip";
		ExportUtil util=new ExportUtil();
		util.fileToZip(sourcePath, sourcePath, titleNo);
		String filePath=sourcePath+"/"+fileName;
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE����IEΪ�ں˵������
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//��IE������Ĵ���
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//��ȡһ����
			InputStream in = new FileInputStream(new File(filePath));
			//�������ص���Ӧͷ
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//��ȡresponse�ֽ���
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//��������ѭ��д�뵽�������
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
	
	//�ļ���ɾ��������titleNO��typeɾ��
	@RequestMapping("delFile")
	@ResponseBody
	public void delFile(String titleNo,String type){
		//��ѯ·��
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
			if(file.exists()){//ɾ���ļ�
				file.delete();
			}
			//ɾ�����ݿ��е�����
			uploadFileService.delete(titleNo,type);
		}
	}
	//ɾ�������ļ���
	@SuppressWarnings("null")
	@RequestMapping("delFiles")
	@ResponseBody
	public void delFiles(String titleNo,String type){
		//��ѯ·��
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
			if(file.exists()){//ɾ���ļ�
				UploadFileUtil.deleteDir(file);
			}
			//ɾ�����ݿ��е�����
			uploadFileService.delete(titleNo,type);
		}
	}
	//�����ļ��鿴
	@RequestMapping("viewFile")
	@ResponseBody
	public Object viewFile(String titleNo,String type,HttpServletRequest request) {
		ApplicationForm lastAppFormBytitleNo = applicationFormService.getLastAppFormBytitleNo(titleNo);
		AddLog.addLog(Log.QUERY,"��ѯ'"+lastAppFormBytitleNo.getEnterprisename()+"'ҵ������и���");
		return uploadFileService.getSampleAttach(titleNo,type);
	}
	
	
	//��Ʒ�ļ�����Ʒ��ⱨ���ϴ�����Ʒ��+��Ű汾
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
	//��Ʒ�ļ�����Ʒ��ⱨ���ȡ
	@RequestMapping("getSampleAttach")
	@ResponseBody
	public Object getSampleAttach(String titleNo,String sampleId){
		ApplicationForm af=applicationFormService.getLastAppFormBytitleNo(titleNo);
		AddLog.addLog(Log.QUERY,"��ѯ'"+af.getEnterprisename()+"'ҵ��ĸ���");
		return uploadFileService.getSampleAttach(titleNo,sampleId);
	}
	
	//��Ʒ�ļ���ȡ
	@RequestMapping("getSampleAttach2")
	@ResponseBody
	public Object getSampleAttach2(String titleNo,String sampleId){
		ApplicationForm af=applicationFormService.getLastAppFormBytitleNo(titleNo);
		AddLog.addLog(Log.QUERY,"��ѯ'"+af.getEnterprisename()+"'ҵ��ĸ���");
		return uploadFileService.getSampleAttach(titleNo,sampleId,"��Ʒ����");
	}

	public void delFile(String titleNo, String describe, String type) {
		//��ѯ·��
		String path=uploadFileService.getPath(titleNo,describe,type);
		if(StringUtils.isNotBlank(path)){
			File file = new File(path);
			if(file.exists()){//ɾ���ļ�
				file.delete();
			}
			//ɾ�����ݿ��е�����
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
