package com.wzj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzj.pojo.Log;
import com.wzj.service.impl.LogService;
import com.wzj.util.AddLog;

@Controller
@RequestMapping("log")
public class LogController {
	@Autowired
	private LogService logService;
	
	@RequestMapping("/find")
	public String find(){
		AddLog.addLog(Log.QUERY,"查询所有日志信息");
		return "log";
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public Object findAll(Integer isSortCol_0,String sSortDir_0,int iDisplayStart,int iDisplayLength,String sEcho,String sSearch){
		String sort="";
		if(isSortCol_0!=null){
			switch(isSortCol_0){
				case 5:sort+="operation";break;
				case 4:sort+="operationType";break;
				case 3:sort+="department";break;
				case 2:sort+="person";break;
				case 1:sort+="time";break;
			}
		}else{
			sort+="id";
		}
		if("asc".equals(sSortDir_0)){
			sort+=" desc";
		}else{
			sort+=" asc";
		}
		//页码
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength,sort);
        //获取dictionary
        List<Log> dic = new ArrayList<>();
        if(StringUtils.isNotBlank(sSearch))
        	dic=logService.findByStr(sSearch);
        else
        	dic=logService.findAll();
        //进行分页配置
        PageInfo<Log> pageInfo = new PageInfo<Log>(dic);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	
	@RequestMapping("/findByTime")
	@ResponseBody
	public Object findByTime(String sTime,String eTime,int iDisplayStart,int iDisplayLength,String sEcho){
		AddLog.addLog(Log.QUERY,"查询'"+sTime+"'到'"+eTime+"'时间段的所有日志信息");
		//页码
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength);
        //获取dictionary
        List<Log> dic = logService.findByTime(sTime,eTime);
        //进行分页配置
        PageInfo<Log> pageInfo = new PageInfo<Log>(dic);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	
	@RequestMapping("deleteBefore")
	@ResponseBody
	public List<Log> deleteBefore(String timePoint){
		AddLog.addLog(Log.DELETE,"删除"+timePoint+"之前的所有日志信息");
		logService.deleteBefore(timePoint);
		return logService.findAll();
	}
	@RequestMapping("select")
	@ResponseBody
	public Object Select(String str,int iDisplayStart,int iDisplayLength,String sEcho){
		//页码
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //根据页码和每页长度进行截取
        PageHelper.startPage(page_num, iDisplayLength);
        //获取dictionary
        List<Log> dic = logService.selectAll(str);
        //进行分页配置
        PageInfo<Log> pageInfo = new PageInfo<Log>(dic);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("sEcho", sEcho);
        map.put("aaData", pageInfo.getList());
        map.put("iTotalRecords", (int)pageInfo.getTotal());
        map.put("iTotalDisplayRecords", (int)pageInfo.getTotal());
        return map;
	}
	@RequestMapping("exportLog")
	@ResponseBody
	public List<Log> exportLog(String time,HttpServletRequest request){
		//获取表格第一条数据
		Log log=logService.getFirstLog();
		//第一条数据的时间（月）
		int day = log.getTime().getMonth()+1;
	    //要导出数据的第一条开始时间
		Timestamp start = log.getTime();
	    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
	    Calendar c = Calendar.getInstance();
	    String sTime=sdf.format(start);
		//要导出数据的第一条结束时间
		c.setTime(new Date());
	    c.add(Calendar.MONTH, -1);
	    Date m = c.getTime();
	    String eTime = sdf.format(m);
		int now=Integer.parseInt(time);
		 PrintWriter pw = null;
	     FileWriter fw = null;
	     //打印内容
	     int id = 0;
	     Timestamp time1 = null;
	     String person = null;
	     String department = null;
	     String operation = null;
	     String operationType = null;
	     String filename =".txt";
		if(now-day>1){
			 try {
			List<Log> list=logService.findByTime(sTime, eTime);
			String path=request.getSession().getServletContext().getRealPath("export/log");
			  File file = new File(path+"/log"+filename);
				 if(!file.exists()){
	                 try {
	                     file.createNewFile();
	                     pw = new PrintWriter(file);
	                     for (Log log2 : list) {
	        				 id = log2.getId();
	        				 time1 = log2.getTime();
	        				 person = log2.getPerson();
	        				 department=log2.getDepartment();
	        				 operation=log2.getOperation();
	        				 operationType=log2.getOperationType();
	        				 pw.print(id + "\t");
		                     pw.print(time1 + "\t\t");
		                     pw.print(person+ "\t");
		                     pw.print(department+ "\t\t\t\t");
		                     pw.print(operation+ "\t\t");
		                     pw.print(operationType);
		                     pw.println();
		                     pw.flush();
	        			}
	                 } catch (IOException e) {
	                     e.printStackTrace();
	                 }
	             }else{
	                 try {
	                     fw = new FileWriter(path+"/log"+filename,true);
	                     for (Log log2 : list) {
	        				 id = log2.getId();
	        				 time1 = log2.getTime();
	        				 person = log2.getPerson();
	        				 pw.print(id + "\t");
		                     pw.print(time1 + "\t\t");
		                     pw.print(person+ "\t");
		                     pw.print(department+ "\t\t\t\t");
		                     pw.print(operation+ "\t\t");
		                     pw.print(operationType);
		                     pw.println();
		                     pw.flush();
	        			}
	                 } catch (IOException e) {
	                     e.printStackTrace();
	                 }
	             }
			}catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("加载数据库失败");
	            System.exit(1);
	        }finally {
	            if (pw != null) {
	                // 关闭IO流
	                pw.close();
	            }
	            if(fw != null){
	                try {
	                    fw.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
//			执行导入操作，并且删除一个月前的数据
			 logService.deleteBefore(eTime);
		}
		return logService.findAll();
	}
	
	@RequestMapping("/exportRecord")
	public void exportRecord(HttpServletRequest request,HttpServletResponse response){
		AddLog.addLog(Log.EXPORT,"导出所有日志信息");
		//获取导出的文件夹
		String path = request.getSession().getServletContext().getRealPath("export/log");
		String title = "日志信息";
		String fileName = title+".xlsx";
		String filePath = path+"/"+fileName;
		//表格名称及标题
		boolean result = logService.exportRecord(title,filePath);
		if(!result){//出错
			return;
		}
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
	
}
