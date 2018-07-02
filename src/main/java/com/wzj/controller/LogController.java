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
		AddLog.addLog(Log.QUERY,"��ѯ������־��Ϣ");
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
		//ҳ��
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //����ҳ���ÿҳ���Ƚ��н�ȡ
        PageHelper.startPage(page_num, iDisplayLength,sort);
        //��ȡdictionary
        List<Log> dic = new ArrayList<>();
        if(StringUtils.isNotBlank(sSearch))
        	dic=logService.findByStr(sSearch);
        else
        	dic=logService.findAll();
        //���з�ҳ����
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
		AddLog.addLog(Log.QUERY,"��ѯ'"+sTime+"'��'"+eTime+"'ʱ��ε�������־��Ϣ");
		//ҳ��
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //����ҳ���ÿҳ���Ƚ��н�ȡ
        PageHelper.startPage(page_num, iDisplayLength);
        //��ȡdictionary
        List<Log> dic = logService.findByTime(sTime,eTime);
        //���з�ҳ����
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
		AddLog.addLog(Log.DELETE,"ɾ��"+timePoint+"֮ǰ��������־��Ϣ");
		logService.deleteBefore(timePoint);
		return logService.findAll();
	}
	@RequestMapping("select")
	@ResponseBody
	public Object Select(String str,int iDisplayStart,int iDisplayLength,String sEcho){
		//ҳ��
        int page_num = (iDisplayStart / iDisplayLength) + 1;
        //����ҳ���ÿҳ���Ƚ��н�ȡ
        PageHelper.startPage(page_num, iDisplayLength);
        //��ȡdictionary
        List<Log> dic = logService.selectAll(str);
        //���з�ҳ����
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
		//��ȡ����һ������
		Log log=logService.getFirstLog();
		//��һ�����ݵ�ʱ�䣨�£�
		int day = log.getTime().getMonth()+1;
	    //Ҫ�������ݵĵ�һ����ʼʱ��
		Timestamp start = log.getTime();
	    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
	    Calendar c = Calendar.getInstance();
	    String sTime=sdf.format(start);
		//Ҫ�������ݵĵ�һ������ʱ��
		c.setTime(new Date());
	    c.add(Calendar.MONTH, -1);
	    Date m = c.getTime();
	    String eTime = sdf.format(m);
		int now=Integer.parseInt(time);
		 PrintWriter pw = null;
	     FileWriter fw = null;
	     //��ӡ����
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
	            System.out.println("�������ݿ�ʧ��");
	            System.exit(1);
	        }finally {
	            if (pw != null) {
	                // �ر�IO��
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
//			ִ�е������������ɾ��һ����ǰ������
			 logService.deleteBefore(eTime);
		}
		return logService.findAll();
	}
	
	@RequestMapping("/exportRecord")
	public void exportRecord(HttpServletRequest request,HttpServletResponse response){
		AddLog.addLog(Log.EXPORT,"����������־��Ϣ");
		//��ȡ�������ļ���
		String path = request.getSession().getServletContext().getRealPath("export/log");
		String title = "��־��Ϣ";
		String fileName = title+".xlsx";
		String filePath = path+"/"+fileName;
		//������Ƽ�����
		boolean result = logService.exportRecord(title,filePath);
		if(!result){//����
			return;
		}
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
	
}
