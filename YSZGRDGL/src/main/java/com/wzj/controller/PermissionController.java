package com.wzj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.DTO.SysPermissionDTO;
import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.Log;
import com.wzj.pojo.SysPermission;
import com.wzj.service.impl.PermissionService;
import com.wzj.util.AddLog;

@Controller
@RequestMapping("permission")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("/toPermissionsJSP")
	public String toPermissionsJSP(){
		return "permission";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public Object findAll(){
		AddLog.addLog(Log.QUERY,"��ѯ���е�Ȩ����Ϣ");
		return permissionService.findAll();
	}
	
	@RequestMapping("/findIds")
	@ResponseBody
	public Object findIds(){
		return permissionService.findIds();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public int update(SysPermissionDTO spd){
		AddLog.addLog(Log.MODIFY,"�޸�Ȩ��'"+spd.getName()+"����Ϣ");
		SysPermission pms = new SysPermission();
		pms.setId(spd.getId());
		pms.setName(spd.getName());
		pms.setType(spd.getType());
		pms.setUrl(spd.getUrl());
//		pms.setPercode(spd.getPercode());
		pms.setParentid(spd.getParentid());
//		pms.setParentids(spd.getParentids());
		pms.setSortstring(spd.getSortstring());
		pms.setAvailable(spd.getAvailable());
		return permissionService.update(pms);
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public int add(SysPermissionDTO spd){
		AddLog.addLog(Log.ADD,"���Ȩ��'"+spd.getName()+"'����Ϣ");
		SysPermission pms = new SysPermission();
		pms.setId(spd.getId());
		pms.setName(spd.getName());
		pms.setType(spd.getType());
		pms.setUrl(spd.getUrl());
//		pms.setPercode(spd.getPercode());
		pms.setParentid(spd.getParentid());
//		pms.setParentids(spd.getParentids());
		pms.setSortstring(spd.getSortstring());
		pms.setAvailable(spd.getAvailable());
		return permissionService.add(pms);
	}
	
	@RequestMapping("deleteById")
	@ResponseBody
	@Transactional
	public int deleteById(int id){
		SysPermission pms=permissionService.getById(id);
		AddLog.addLog(Log.DELETE,"ɾ��Ȩ��'"+pms.getName()+"'����Ϣ");
		permissionService.deleteRolePmsById(id);
//		permissionService.updateAvailabie(id);
		return permissionService.deleteById(id);
	}
	
	@RequestMapping("/exportRecord")
	public void exportRecord(HttpServletRequest request, HttpServletResponse response) {
		AddLog.addLog(Log.EXPORT,"�������е�Ȩ����Ϣ");
		//��ȡ�������ļ���
		String path = request.getSession().getServletContext().getRealPath("export/pms");
		String title = "Ȩ����Ϣ";
		String fileName = title+".xlsx";
		String filePath = path+"/"+fileName;
		//��һ������ʱsheet�������Ƽ�����
		boolean result = permissionService.exportRecord(title,filePath);
		if(!result){//�������
			return;
		}
		try{
			//���ݲ�ͬ����������������ļ�����������
			String userAgent = request.getHeader("User-Agent");
			//���IE������IEΪ�ں˵������
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
			while((len=in.read(b))!=-1){//��������������ѭ��д�뵽�����
				out.write(b,0,len);
			}
			//�ر�
			out.close();
			in.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
