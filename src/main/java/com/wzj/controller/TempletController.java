package com.wzj.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wzj.pojo.Log;
import com.wzj.pojo.UploadFile;
import com.wzj.service.application.impl.UploadFileService;
import com.wzj.service.impl.TempletService;
import com.wzj.util.AddLog;
import com.wzj.util.UploadFileUtil;
import com.wzj.controller.CommonsController;

@Controller
@RequestMapping("templet")
public class TempletController {
	
	@Autowired
	private CommonsController commonController;
	
	@Autowired
	private TempletService templetService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@RequestMapping("toTemplet")
	public String toTemplet(){
		AddLog.addLog(Log.QUERY,"查询所有模板信息");
		return "common/templet";
	} 
	
	@RequestMapping("getAll")
	@ResponseBody
	public Object getAll(){
		return templetService.getAll();
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public void insert(UploadFile tem,HttpServletRequest request){
		AddLog.addLog(Log.ADD,"添加模板'"+tem.getUpdescribe()+"'");
		String titleNo="MB0000";
		/*Integer extId = templetService.getIdByDesId(tem.getDescribeId());
		System.out.println(extId);
		if(extId!=null)
			templetService.delete(Integer.toString(extId));
		commonController.uploadFile(tem.getMfile(), titleNo, tem.getDescribeId());*/
		MultipartFile file=tem.getMfile();
		UploadFileUtil uf = new UploadFileUtil();
		Map<String,String> map=uf.uploadFile(file,titleNo,request);
		
		UploadFile uFile = new UploadFile();
		uFile.setUpdescribe(tem.getUpdescribe());
		uFile.setUprul(map.get("path"));
		uFile.setUpsize(map.get("size"));
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
		
		//uploadFile(tem.getUpdescribe(),titleNo,tem.getMfile());
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public void delete(String id,HttpServletRequest request){
		templetService.delete(id,request);
	}
	
	@RequestMapping("updateNoFile")
	@ResponseBody
	public void updateNoFile(String temId,String temDes){
	    UploadFile tem = templetService.getDesById(temId);
	    AddLog.addLog(Log.MODIFY,"修改'"+tem.getUpdescribe()+"'的模板信息");
		tem.setUpdescribe(temDes);
		uploadFileService.deleteById(temId);
		uploadFileService.insert(tem);
	}
	
	
	@RequestMapping("update")
	@ResponseBody
	public void update(UploadFile tem,HttpServletRequest request){
		AddLog.addLog(Log.MODIFY,"修改'"+tem.getUpdescribe()+"'的模板信息");
		templetService.delete(Integer.toString(tem.getId()),request);
		
		String titleNo="MB0000";
		MultipartFile file=tem.getMfile();
		UploadFileUtil uf = new UploadFileUtil();
		Map<String,String> map=uf.uploadFile(file,titleNo,request);
		
		UploadFile uFile = new UploadFile();
//		uFile.setId(tem.getId());
		uFile.setUpdescribe(tem.getUpdescribe());
		uFile.setUprul(map.get("path"));
		uFile.setUpsize(map.get("size"));
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
	}
	
	@RequestMapping("getDesById")
	@ResponseBody
	public UploadFile getDesById(String id){
		UploadFile tem = templetService.getDesById(id);
		AddLog.addLog(Log.QUERY,"查询'"+tem.getUpdescribe()+"'的模板信息");
		return templetService.getDesById(id);
	}
	
	@RequestMapping("download")
	@ResponseBody
	public String download(String id){
		UploadFile tem = templetService.getDesById(id);
		AddLog.addLog(Log.EXPORT,"下载'"+tem.getUpdescribe()+"'的模板");
		String path=templetService.download(id);
		try {
			path=URLDecoder.decode(path, "UTF-8").replace("\\", "/");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//响应到前台为utf-8
		try {
			path= URLEncoder.encode(path, "UTF-8").replace("+","%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	private void uploadFile(String desc,String titleNo,MultipartFile mfile,HttpServletRequest request){
		UploadFileUtil uploadFileUtil = new UploadFileUtil();
		UploadFile uFile = new UploadFile();
		if(mfile!=null)System.out.println("有文件");
		if(titleNo!=null)System.out.println("有titleNo");
		Map<String,String> map=uploadFileUtil.uploadFile(mfile,titleNo,request);
		uFile.setUpdescribe(desc);
		uFile.setUprul(map.get("path"));
		uFile.setUpsize(map.get("size"));
		uFile.setCode(titleNo);
		uFile.setAvailability(true);
		uFile.setUploadtime(new Date());
		uploadFileService.insert(uFile);
	}
	//判断模板是否存在
	@RequestMapping("getTemp")
	@ResponseBody
	public Boolean getTemp(String path,HttpServletRequest request){
		Boolean result=false;
		String filePath = request.getSession().getServletContext().getRealPath("./");
		String realpath = filePath+"/"+path;
		File dir=new File(realpath);
		if(dir.exists()){//如果文件不存在则创建文件
			result=true;
		}
		return result;
	}
}
