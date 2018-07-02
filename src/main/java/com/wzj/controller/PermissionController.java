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
		AddLog.addLog(Log.QUERY,"查询所有的权限信息");
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
		AddLog.addLog(Log.MODIFY,"修改权限'"+spd.getName()+"的信息");
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
		AddLog.addLog(Log.ADD,"添加权限'"+spd.getName()+"'的信息");
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
		AddLog.addLog(Log.DELETE,"删除权限'"+pms.getName()+"'的信息");
		permissionService.deleteRolePmsById(id);
//		permissionService.updateAvailabie(id);
		return permissionService.deleteById(id);
	}
	
	@RequestMapping("/exportRecord")
	public void exportRecord(HttpServletRequest request, HttpServletResponse response) {
		AddLog.addLog(Log.EXPORT,"导出所有的权限信息");
		//获取导出的文件夹
		String path = request.getSession().getServletContext().getRealPath("export/pms");
		String title = "权限信息";
		String fileName = title+".xlsx";
		String filePath = path+"/"+fileName;
		//第一个参数时sheet表格的名称及标题
		boolean result = permissionService.exportRecord(title,filePath);
		if(!result){//如果出错
			return;
		}
		try{
			//根据不同的浏览器处理下载文件名乱码问题
			String userAgent = request.getHeader("User-Agent");
			//针对IE或者以IE为内核的浏览器
			if(userAgent.contains("MSIE")||userAgent.contains("Trident")){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{//非IE浏览器的处理
				fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//获取一个流
			InputStream in = new FileInputStream(new File(filePath));
			//设置下载的响应头
			response.setHeader("content-disposition","attachment;fileName="+fileName);
			response.setCharacterEncoding("UTF-8");
			//获取response字节流
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len=in.read(b))!=-1){//将输入流的内容循环写入到输出流
				out.write(b,0,len);
			}
			//关闭
			out.close();
			in.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
