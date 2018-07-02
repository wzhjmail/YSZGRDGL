package com.wzj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.Division;
import com.wzj.pojo.Log;
import com.wzj.service.impl.CountService;
import com.wzj.service.impl.DivisionService;
import com.wzj.util.AddLog;
import com.wzj.util.PrintUtil;

import junit.framework.Test;

@Controller
@RequestMapping("count")
public class CountController {
	@Autowired
	private DivisionService divisionService;
	@Autowired
	private CountService countservice;
	
	@RequestMapping("toCount")
	public String toCount(HttpSession session,Model model){
		AddLog.addLog(Log.QUERY,"��ѯ����ҵ������");
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId",branchId);
		return "common/count";
	}
	@RequestMapping("toReturnRate")
	public String toReturnRate(HttpSession session,Model model){
		AddLog.addLog(Log.QUERY,"��ѯ����ҵ���˻ر���");
		ActiveUser activeUser=(ActiveUser) session.getAttribute("activeUser");
		String branchId=activeUser.getRamusCenter();
		model.addAttribute("branchId",branchId);
		return "common/returnRate";
	}
	
	//��ȡ������
	@RequestMapping("getFZX")
	@ResponseBody
	public Object getFZX(){
		AddLog.addLog(Log.QUERY,"��ѯ���з�������Ϣ");
		List<Division> division=divisionService.getAllDivision();
		return division;
	}
	@RequestMapping("getCount")
	@ResponseBody
	public Object getCount(){
		AddLog.addLog(Log.QUERY,"��ѯ����ҵ��ͳ����Ϣ");
		List<Division> branchs=divisionService.getAllDivision();
		List<String> names=new ArrayList<>();
		List<Division> obj=new ArrayList<>();
		List<Integer> xb=new ArrayList<>();
		List<Integer> fr=new ArrayList<>();
		List<Integer> bg=new ArrayList<>();
		//��ȡ��ʼʱ��
        Timestamp s = getStime(null);
        //��ȡ����ʱ��
        Timestamp e = getEtime(null);
		for(Division div:branchs){
			String branchId=div.getDivisioncode();
			if(!"0000".equals(branchId)){
				obj.add(div);
				names.add(div.getDivisionname());
				xb.add(countservice.getCount(branchId,"XB",s,e));
				fr.add(countservice.getCount(branchId,"FR",s,e));
				bg.add(countservice.getCount(branchId,"BG",s,e));
			}
		}
		Map<String,Object> map=new HashMap<>();
		map.put("names", names);
		map.put("obj", obj);
		map.put("xb",xb);
		map.put("fr",fr);
		map.put("bg",bg);
		return map;
	}
	//����ʱ���ѯ���з���������
	@RequestMapping("getSearch")
	@ResponseBody
	public Object getSearch(String d1,String d2) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Division> branchs=divisionService.getAllDivision();
		List<String> names=new ArrayList<>();
		List<Integer> xb=new ArrayList<>();
		List<Integer> fr=new ArrayList<>();
		List<Integer> bg=new ArrayList<>();
		//��ȡ��ʼʱ��
		Date sd=sdf.parse(d1);
        Timestamp s = getStime(sd);
		//��ȡ����ʱ��
        Date ed=sdf.parse(d2);
		Timestamp e = getEtime(ed);
		AddLog.addLog(Log.QUERY,"��ѯ'"+s+"'��'"+e+"'ʱ��ε�ҵ��ͳ����Ϣ");
		for(Division div:branchs){
			String branchId=div.getDivisioncode();
			if(!"0000".equals(branchId)){
				names.add(div.getDivisionname());
				xb.add(countservice.getCount(branchId,"XB",s,e));
				fr.add(countservice.getCount(branchId,"FR",s,e));
				bg.add(countservice.getCount(branchId,"BG",s,e));
			}
		}
		Map<String,Object> map=new HashMap<>();
		map.put("names", names);
		map.put("xb",xb);
		map.put("fr",fr);
		map.put("bg",bg);
		return map;
	}
	
	//��ȡ��ʼʱ��
	private Timestamp getStime(Date start){
		Date stime = null;
		if(start==null){//�����ʼʱ��Ϊnull,��ʹ�ñ���ȵĵ�һ����Ϊ��ʼʱ��
			Calendar calendar = Calendar.getInstance();  
	        calendar.clear();  
	        calendar.set(Calendar.YEAR,new Date().getYear());
	        stime = calendar.getTime();
		}else{
			stime=start;
		}
        Timestamp s = new Timestamp(stime.getTime());
        return s;
	}
	//��ȡ����ʱ��
	private  Timestamp getEtime(Date edate){
		Date etime=null;
		if(edate==null){//���û�н���ʱ�䣬��ȡ���ڵ�ʱ����Ϊ����ʱ��
			etime=new Date();
		}else{
			Calendar gregorian = new GregorianCalendar();
			gregorian.setTime(edate);
//			gregorian.add(Calendar.DATE,1);
			etime=gregorian.getTime();
		}
		Timestamp end = new Timestamp(etime.getTime());
		return end;
	}
	//�������»�õ�������
	private int getDaysofMonth(int year,int m){
		if(m==1 ||m== 3 ||m== 5 ||m== 7 ||m== 8 ||m==10 ||m==12)
		return 31;
		else if(m == 4||m==6 ||m== 9 ||m== 11)
		return 30;
		else if(m==2){
		if(year %4 ==0 && year %100 !=0){
		return 29;
		}else if(year%400==0){
		return 29;
		}else{
		return 28;
		}
		}
		return 0;
		}
	//���ݷ����Ĳ��ұ�������
	@RequestMapping("search")
	@ResponseBody
	public Object search(String year,String branchId) throws ParseException{
		Map<String,Object> shu=new HashMap<>();
		String log="";
		String[] branch=branchId.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		�������һ��
		int endDay=0;
//		������ʼʱ��
		String month1="";
//		������ֹʱ��
		String month2="";
		Date sd;
        Timestamp s = null;//��ȡ��ʼʱ��
        Date ed;
		Timestamp e = null;//��ȡ��ֹʱ��
	for(int j=0;j<branch.length;j++){
		Map<String,Object> returnData=new HashMap<>();
		List<String> names=new ArrayList<>();
		List<Integer> xb=new ArrayList<>();
		List<Integer> fr=new ArrayList<>();
		List<Integer> bg=new ArrayList<>();
		for(int i=1;i<=12;i++){
			names.add(i+"��");
			endDay=getDaysofMonth(Integer.parseInt(year),i);
			month1=year+"-"+String.valueOf(i)+"-"+"01";
			month2=year+"-"+String.valueOf(i)+"-"+String.valueOf(endDay);
			sd=sdf.parse(month1);
	        s = getStime(sd);//��ȡ��ʼʱ��
	        ed=sdf.parse(month2);
			e = getEtime(ed);//��ȡ��ֹʱ��
			xb.add(countservice.getCount(branch[j],"XB",s,e));
			fr.add(countservice.getCount(branch[j],"FR",s,e));
			bg.add(countservice.getCount(branch[j],"BG",s,e));
		}
//		int xb1=0;
//		int fr1=0;
//		int bg1=0;
//		for(int k=0;k<xb.size();k++){
//			xb1+=xb.get(k);
//			fr1+=fr.get(k);
//			bg1+=bg.get(k);
//		}
		returnData.put("xb",xb);
		returnData.put("fr",fr);
		returnData.put("bg",bg);
		returnData.put("names",names);
		
		Division div=divisionService.getDivisionByCode(branch[j]);
		log+=div.getDivisionname()+",";
		String branchName=div.getDivisionname();
		returnData.put("branchName",branchName);
		shu.put(j+"", returnData);
		}
		AddLog.addLog(Log.QUERY,"��ѯ'"+log.substring(0, log.lastIndexOf(","))+"'�ȷ�֧������'"+year+"'���ҵ��ͳ����Ϣ");
		Map<String,Object> map=new HashMap<>();
		map.put("returnData", shu);
		return map;
	}
	//����ʱ����Լ������Ĳ�������
	@RequestMapping("searchByTwo")
	@ResponseBody
	public Object searchByTwo(String d1,String d2,String branchId) throws ParseException{
//		Map<String,Object> shu1=new HashMap<>();
//		Map<String,Object> shu2=new HashMap<>();
		String log="";
		String[] branch=branchId.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		if(d1.substring(0, 4).equals(d2.substring(0, 4))){
//			Map<String,Object> map1=new HashMap<>();
//			int searchBeginMon=Integer.parseInt(d1.substring(5, 7));
//			int searchEndMon=Integer.parseInt(d2.substring(5, 7));
//			String year=d1.substring(0, 4);
//			String searchBeginDay=d1.substring(8, 10);
//			String searchEndDay=d2.substring(8, 10);
//			String month1="";
//			String month2="";
//			for(int j=0;j<branch.length;j++){
//				Map<String,Object> returnData1=new HashMap<>();
//				List<String> names=new ArrayList<>();
//				List<Integer> xb=new ArrayList<>();
//				List<Integer> fr=new ArrayList<>();
//				List<Integer> bg=new ArrayList<>();
//				for(int i=searchBeginMon;i<=searchEndMon;i++){
//					names.add(i+"��");
//					month1=year+"-"+String.valueOf(i)+"-"+searchBeginDay;
//					month2=year+"-"+String.valueOf(i)+"-"+searchEndDay;
//					Date sd=sdf.parse(month1);
//			        Timestamp s = getStime(sd);//��ȡ��ʼʱ��
//			        Date ed=sdf.parse(month2);
//					Timestamp e = getEtime(ed);//��ȡ��ֹʱ��
//				xb.add(countservice.getCount(branch[j],"XB",s,e));
//				fr.add(countservice.getCount(branch[j],"FR",s,e));
//				bg.add(countservice.getCount(branch[j],"BG",s,e));
//				}
//				returnData1.put("names",names);
//				returnData1.put("xb",xb);
//				returnData1.put("fr",fr);
//				returnData1.put("bg",bg);
//				Division div=divisionService.getDivisionByCode(branch[j]);
//				log+=div.getDivisionname()+",";
//				String branchName=div.getDivisionname();
//				returnData1.put("branchName",branchName);
//				shu1.put(j+"", returnData1);
//			}
//			map1.put("returnData", shu1);
//			return map1;
//		}else{
			Map<String,Object> map=new HashMap<>();
			//��ȡ��ʼʱ��
			Date sd=sdf.parse(d1);
	        Timestamp s = getStime(sd);
			//��ȡ����ʱ��
	        Date ed=sdf.parse(d2);
			Timestamp e = getEtime(ed);
			List<String> names=new ArrayList<>();
			List<Integer> xb=new ArrayList<>();
			List<Integer> fr=new ArrayList<>();
			List<Integer> bg=new ArrayList<>();
			for(int i=0;i<branch.length;i++){
				Division div=divisionService.getDivisionByCode(branch[i]);
				names.add(div.getDivisionname());
				log+=div.getDivisionname()+",";
				xb.add(countservice.getCount(branch[i],"XB",s,e));
				fr.add(countservice.getCount(branch[i],"FR",s,e));
				bg.add(countservice.getCount(branch[i],"BG",s,e));
			}
			
			map.put("names",names);
			map.put("xb",xb);
			map.put("fr",fr);
			map.put("bg",bg);
			AddLog.addLog(Log.QUERY,"��ѯ'"+log.substring(0, log.lastIndexOf(","))+"'�ȷ�֧������'"+d1+"'��'"+d2+"'ʱ��ε�ҵ��ͳ����Ϣ");
			return map;
		
		}
//ûѡ������ûѡʱ��ε���Excel
	@RequestMapping("/exportAll")
	public void exportAll(HttpServletRequest request,HttpServletResponse response){
		AddLog.addLog(Log.EXPORT,"�������з����ĵ�ҵ���������");
		//��ȡ�������ļ���
		String path = request.getSession().getServletContext().getRealPath("./");
		String title = "ҵ��ͳ����Ϣ";
		String fileName = title+".xlsx";
		String filePath = path+"/export/count/"+fileName;
		//������Ƽ�����
		boolean result = countservice.exportAll(title,filePath);
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
//ûѡ������ѡ��ʱ��ε���Excel
	@RequestMapping("/exportByTime")
	public void exportByTime(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		String d1=request.getParameter("d1");
		String d2=request.getParameter("d2");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//��ȡ��ʼʱ��
		Date sd=sdf.parse(d1);
        Timestamp start = getStime(sd);
		//��ȡ����ʱ��
        Date ed=sdf.parse(d2);
		Timestamp end = getEtime(ed);
		AddLog.addLog(Log.EXPORT,"����'"+d1+"'��'"+d2+"'ʱ������з����ĵ�ҵ���������");
		//��ȡ�������ļ���
		String path = request.getSession().getServletContext().getRealPath("./");
		String title = "ҵ��ͳ����Ϣ";
		String fileName = title+".xlsx";
		String filePath = path+"/export/count/"+fileName;
		//������Ƽ�����
		boolean result = countservice.exportByTime(title,filePath,start,end);
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
//ѡ���˷�����ûѡʱ��ε���Excel
	@RequestMapping("/exportByBranch")
	public void exportByBranch(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		String branchId=request.getParameter("branchId");
		String[] branch=branchId.split(",");
		String log="";
		for (int i=0;i<branch.length;i++) {
			Division divisionByCode = divisionService.getDivisionByCode(branch[i]);
			log+=divisionByCode.getDivisionname()+",";
		}
		AddLog.addLog(Log.EXPORT,"����'"+log.substring(0, log.lastIndexOf(","))+"'�ȷ�֧���������ҵ���������");
		String time=request.getParameter("year");
		
		//��ȡ�������ļ���
		String path = request.getSession().getServletContext().getRealPath("./");
		String title = "�����ĵ�ҵ��ͳ����Ϣ";
		String fileName = title+".xlsx";
		String filePath = path+"/export/count/"+fileName;
		//������Ƽ�����
		boolean result = countservice.exportByBranch(filePath,time,branchId);
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
//ѡ���˷�����Ҳѡ��ʱ��ε���Excel
	@RequestMapping("/exportByTwo")
	public void exportByTwo(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		String branchId=request.getParameter("branchId");
		String[] branch=branchId.split(",");
		String log="";
		for (int i=0;i<branch.length;i++) {
			Division divisionByCode = divisionService.getDivisionByCode(branch[i]);
			log+=divisionByCode.getDivisionname()+",";
		}
		String d1=request.getParameter("d1");
		String d2=request.getParameter("d2");
		AddLog.addLog(Log.EXPORT,"����'"+log.substring(0, log.lastIndexOf(","))+"'�ȷ�������'"+d1+"'��'"+d2+"'ʱ��ε�ҵ���������");
		//��ȡ�������ļ���
		String path = request.getSession().getServletContext().getRealPath("./");
		String title = "��������"+d1+"��"+d2+"ʱ��ε�ҵ��ͳ����Ϣ";
		String fileName = title+".xlsx";
		String filePath = path+"/export/count/"+fileName;
		//������Ƽ�����
		boolean result = countservice.exportByTwo(filePath,d1,d2,branchId);
//		boolean result = countservice.exportByTwo(title,filePath,d1,d2,branchId);
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
	//���з�����ҵ�������Լ��˻�����
	@RequestMapping("getReturnRate")
	@ResponseBody
	public Object getReturnRate() throws ParseException{
		AddLog.addLog(Log.QUERY,"��ѯ���з������˻�ҵ�������Ϣ");
		List<Division> branchs=divisionService.getAllDivision();
		List<String> names=new ArrayList<>();
		List<Integer> xb=new ArrayList<>();
		List<Integer> fr=new ArrayList<>();
		List<Integer> bg=new ArrayList<>();
		List<Integer> xbReturn=new ArrayList<>();
		List<Integer> frReturn=new ArrayList<>();
		List<Integer> bgReturn=new ArrayList<>();
		String returnXBStatus="2,4,6,8,10,13,15";
		String returnFRStatus="19,21,23,25,27,30,32";
		String returnBGStatus="3,4";
		String result="";
		String FRresult="";
		String BGresult="";
		int XBCount=0;
		int FRCount=0;
		int BGCount=0;
		for(Division div:branchs){
			String branchId=div.getDivisioncode();
			if(!"0000".equals(branchId)){
				names.add(div.getDivisionname());
				xb.add(countservice.getYWCountByType(branchId,"XB"));
				fr.add(countservice.getYWCountByType(branchId,"FR"));
				bg.add(countservice.getYWCountByType(branchId,"BG"));
				result="select count(*) from ys_title where branchId="+branchId+" and TitleNo like'%XB%' and status in("+returnXBStatus+")";
				XBCount=countservice.getXBCountByTime(result);
				FRresult="select count(*) from ys_title where branchId="+branchId+" and TitleNo like'%FR%' and status in("+returnFRStatus+")";
				FRCount=countservice.getFRCountByTime(FRresult);
				BGresult="select count(*) from ys_title where branchId="+branchId+" and TitleNo like'%BG%' and status in("+returnBGStatus+")";
				BGCount=countservice.getBGCountByTime(BGresult);
				xbReturn.add(XBCount);
				frReturn.add(FRCount);
				bgReturn.add(BGCount);
			}
			
		}
		Map<String,Object> map=new HashMap<>();
		map.put("names", names);
		map.put("xb",xb);
		map.put("fr",fr);
		map.put("bg",bg);
		map.put("xbReturn",xbReturn);
		map.put("frReturn", frReturn);
		map.put("bgReturn", bgReturn);
		return map;
	}
	//����ʱ���ѯ���з�����ҵ�������Լ��˻�����
	@RequestMapping("getReturnRateByTime")
	@ResponseBody
	public Object getReturnRateByTime(String d1,String d2) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Division> branchs=divisionService.getAllDivision();
		List<String> names=new ArrayList<>();
		List<Integer> xb=new ArrayList<>();
		List<Integer> fr=new ArrayList<>();
		List<Integer> bg=new ArrayList<>();
		List<Integer> xbReturn=new ArrayList<>();
		List<Integer> frReturn=new ArrayList<>();
		List<Integer> bgReturn=new ArrayList<>();
		//��ȡ��ʼʱ��
		Date sd=sdf.parse(d1);
        Timestamp s = getStime(sd);
		//��ȡ����ʱ��
        Date ed=sdf.parse(d2);
		Timestamp e = getEtime(ed);
		AddLog.addLog(Log.QUERY,"��ѯ'"+s+"'��'"+e+"'ʱ��ε�ҵ���˻ر�����Ϣ");
		String returnXBStatus="2,4,6,8,10,13,15";
		String returnFRStatus="19,21,23,25,27,30,32";
		String returnBGStatus="3,4";
		String result="";
		String FRresult="";
		String BGresult="";
		int XBCount=0;
		int FRCount=0;
		int BGCount=0;
		for(Division div:branchs){
			String branchId=div.getDivisioncode();
			if(!"0000".equals(branchId)){
			if(!"".equals(branchId)){
				names.add(div.getDivisionname());
				xb.add(countservice.getCount(branchId,"XB",s,e));
				fr.add(countservice.getCount(branchId,"FR",s,e));
				bg.add(countservice.getCount(branchId,"BG",s,e));
				result="select count(*) from ys_title where branchId="+branchId+" and TitleNo like'%XB%' and status in("+returnXBStatus+") and cdate between '"+s+"' and '"+e+"'";
				XBCount=countservice.getXBCountByTime(result);
				FRresult="select count(*) from ys_title where branchId="+branchId+" and TitleNo like'%FR%' and status in("+returnFRStatus+") and cdate between '"+s+"' and '"+e+"'";
				FRCount=countservice.getFRCountByTime(FRresult);
				BGresult="select count(*) from ys_title where branchId="+branchId+" and TitleNo like'%BG%' and status in("+returnBGStatus+") and cdate between '"+s+"' and '"+e+"'";
				BGCount=countservice.getBGCountByTime(BGresult);
				xbReturn.add(XBCount);
				frReturn.add(FRCount);
				bgReturn.add(BGCount);
			}
			}
		}
		Map<String,Object> map=new HashMap<>();
		map.put("names", names);
		map.put("xb",xb);
		map.put("fr",fr);
		map.put("bg",bg);
		map.put("xbReturn",xbReturn);
		map.put("frReturn", frReturn);
		map.put("bgReturn", bgReturn);
		return map;
	}
	//���ݷ������˻�����
	@RequestMapping("getReturnRateByBranch")
	@ResponseBody
	public Object getReturnRateByBranch(String branchIds) throws ParseException{
		List<String> names=new ArrayList<>();
		List<Integer> xb=new ArrayList<>();
		List<Integer> fr=new ArrayList<>();
		List<Integer> bg=new ArrayList<>();
		List<Integer> xbReturn=new ArrayList<>();
		List<Integer> frReturn=new ArrayList<>();
		List<Integer> bgReturn=new ArrayList<>();
		List<Division> branchs=new ArrayList<>();
		Division div=null;
		String log="";
		String[] branchId=branchIds.split(",");
		for(int i=0;i<branchId.length;i++){
			div=divisionService.getDivisionByCode(branchId[i]);
			log+=div.getDivisionname()+",";
			branchs.add(div);
		}	
		AddLog.addLog(Log.QUERY,"��ѯ'"+log.substring(0, log.lastIndexOf(","))+"'�ȷ�֧������ҵ���˻ر�����Ϣ");
		String returnXBStatus="2,4,6,8,10,13,15";
		String returnFRStatus="19,21,23,25,27,30,32";
		String returnBGStatus="3,4";
		String result="";
		String FRresult="";
		String BGresult="";
		String xbResult="";
		String frResult="";
		String bgResult="";
		int XBCount=0;
		int FRCount=0;
		int BGCount=0;
		for(Division division:branchs){
			String branchid=division.getDivisioncode();
			names.add(division.getDivisionname());
			xbResult="select count(*) from ys_title where branchId ='"+branchid+"' and TitleNo like'%XB%'";
			frResult="select count(*) from ys_title where branchId ='"+branchid+"' and TitleNo like'%FR%'";
			bgResult="select count(*) from ys_title where branchId ='"+branchid+"' and TitleNo like'%BG%'";
			xb.add(countservice.getxbCountByBranchs(xbResult));
			fr.add(countservice.getfrCountByBranchs(frResult));
			bg.add(countservice.getbgCountByBranchs(bgResult));
			result="select count(*) from ys_title where branchId ='"+branchid+"' and TitleNo like'%XB%' and status in("+returnXBStatus+")";
			XBCount=countservice.getXBCountByBranchs(result);
			FRresult="select count(*) from ys_title where branchId ='"+branchid+"' and TitleNo like'%FR%' and status in("+returnFRStatus+")";
			FRCount=countservice.getFRCountByBranchs(FRresult);
			BGresult="select count(*) from ys_title where branchId ='"+branchid+"' and TitleNo like'%BG%' and status in("+returnBGStatus+")";
			BGCount=countservice.getBGCountByBranchs(BGresult);
			xbReturn.add(XBCount);
			frReturn.add(FRCount);
			bgReturn.add(BGCount);
		}
			
		Map<String,Object> map=new HashMap<>();
		map.put("names", names);
		map.put("xb",xb);
		map.put("fr",fr);
		map.put("bg",bg);
		map.put("xbReturn",xbReturn);
		map.put("frReturn", frReturn);
		map.put("bgReturn", bgReturn);
		return map;
	}
	//����ҵ�����Ͳ�ѯ���з�����ҵ�������Լ��˻�����
	@RequestMapping("getReturnRateByType")
	@ResponseBody
	public Object getReturnRateByType(String type) throws ParseException{
		AddLog.addLog(Log.QUERY,"��ѯ����'"+type+"'ҵ���˻ر�����Ϣ");
		List<Division> branchs=divisionService.getAllDivision();
		List<String> names=new ArrayList<>();
		List<Integer> xb=new ArrayList<>();
		List<Integer> fr=new ArrayList<>();
		List<Integer> bg=new ArrayList<>();
		List<Integer> xbReturn=new ArrayList<>();
		List<Integer> frReturn=new ArrayList<>();
		List<Integer> bgReturn=new ArrayList<>();
		String returnXBStatus="2,4,6,8,10,13,15";
		String returnFRStatus="19,21,23,25,27,30,32";
		String returnBGStatus="3,4";
		String result="";
		String FRresult="";
		String BGresult="";
		int XBCount=0;
		int FRCount=0;
		int BGCount=0;
		for(Division div:branchs){
			String branchId=div.getDivisioncode();
			if(!"0000".equals(branchId)){
				names.add(div.getDivisionname());
				if(type.equals("XB")){
					xb.add(countservice.getYWCountByType(branchId,"XB"));
					result="select count(*) from ys_title where branchId="+branchId+" and TitleNo like'%XB%' and status in("+returnXBStatus+")";
					XBCount=countservice.getXBCountByType(result);
					xbReturn.add(XBCount);
					fr.add(1);
					frReturn.add(0);
					bg.add(1);
					bgReturn.add(0);
				}else if(type.equals("FR")){
					xb.add(1);
					xbReturn.add(0);
					fr.add(countservice.getYWCountByType(branchId,"FR"));
					FRresult="select count(*) from ys_title where branchId="+branchId+" and TitleNo like'%FR%' and status in("+returnFRStatus+")";
					FRCount=countservice.getFRCountByType(FRresult);
					frReturn.add(FRCount);
					bg.add(1);
					bgReturn.add(0);
				}else if(type.equals("BG")){
					bg.add(countservice.getYWCountByType(branchId,"BG"));
					BGresult="select count(*) from ys_title where branchId="+branchId+" and TitleNo like'%BG%' and status in("+returnBGStatus+")";
					BGCount=countservice.getBGCountByType(BGresult);
					bgReturn.add(BGCount);
					xb.add(1);
					xbReturn.add(0);
					fr.add(1);
					frReturn.add(0);
				}
			}
		}
		Map<String,Object> map=new HashMap<>();
		map.put("names", names);
		map.put("xb",xb);
		map.put("fr",fr);
		map.put("bg",bg);
		map.put("xbReturn",xbReturn);
		map.put("frReturn", frReturn);
		map.put("bgReturn", bgReturn);
		return map;
	}
	//����ʱ��κͷ����Ĳ����˻�����
	@RequestMapping("getByTimeAndBranch")
	@ResponseBody
	public Object getByTimeAndBranch(String d1,String d2,String branchIds) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Division> branchs=new ArrayList<>();
		Division div=null;
		List<String> names=new ArrayList<>();
		String log="";
		String[] branchId=branchIds.split(",");
		for(int i=0;i<branchId.length;i++){
			div=divisionService.getDivisionByCode(branchId[i]);
			log+=div.getDivisionname()+",";
			branchs.add(div);
		}
		List<Integer> xb=new ArrayList<>();
		List<Integer> fr=new ArrayList<>();
		List<Integer> bg=new ArrayList<>();
		List<Integer> xbReturn=new ArrayList<>();
		List<Integer> frReturn=new ArrayList<>();
		List<Integer> bgReturn=new ArrayList<>();
				
		//��ȡ��ʼʱ��
		Date sd=sdf.parse(d1);
        Timestamp s = getStime(sd);
		//��ȡ����ʱ��
        Date ed=sdf.parse(d2);
		Timestamp e = getEtime(ed);
		AddLog.addLog(Log.QUERY,"��ѯ'"+log.substring(0, log.lastIndexOf(","))+"'�ȷ�֧������'"+s+"'��'"+e+"'ʱ��ε�ҵ���˻ر�����Ϣ");
		String returnXBStatus="2,4,6,8,10,13,15";
		String returnFRStatus="19,21,23,25,27,30,32";
		String returnBGStatus="3,4";
		String result="";
		String FRresult="";
		String BGresult="";
		int XBCount=0;
		int FRCount=0;
		int BGCount=0;
		for(Division division:branchs){
			String branchid=division.getDivisioncode();
			if(!"0000".equals(branchId)){
			names.add(division.getDivisionname());
			xb.add(countservice.getCount(branchid,"XB",s,e));
			fr.add(countservice.getCount(branchid,"FR",s,e));
			bg.add(countservice.getCount(branchid,"BG",s,e));
			result="select count(*) from ys_title where branchId="+branchid+" and TitleNo like'%XB%' and status in("+returnXBStatus+") and cdate between '"+s+"' and '"+e+"'";
			XBCount=countservice.getXBCountByTime(result);
			FRresult="select count(*) from ys_title where branchId="+branchid+" and TitleNo like'%FR%' and status in("+returnFRStatus+") and cdate between '"+s+"' and '"+e+"'";
			FRCount=countservice.getFRCountByTime(FRresult);
			BGresult="select count(*) from ys_title where branchId="+branchid+" and TitleNo like'%BG%' and status in("+returnBGStatus+") and cdate between '"+s+"' and '"+e+"'";
			BGCount=countservice.getBGCountByTime(BGresult);
			xbReturn.add(XBCount);
			frReturn.add(FRCount);
			bgReturn.add(BGCount);
			}
		}
			
		Map<String,Object> map=new HashMap<>();
		map.put("names", names);
		map.put("xb",xb);
		map.put("fr",fr);
		map.put("bg",bg);
		map.put("xbReturn",xbReturn);
		map.put("frReturn", frReturn);
		map.put("bgReturn", bgReturn);
		return map;
	}

	//����ʱ��κ�ҵ�����Ͳ����˻�����
	@RequestMapping("getByTimeAndtype")
	@ResponseBody
	public Object getByTimeAndtype(String d1,String d2,String type) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> names=new ArrayList<>();
		List<Division> branchs=divisionService.getAllDivision();
		List<Integer> xb=new ArrayList<>();
		List<Integer> fr=new ArrayList<>();
		List<Integer> bg=new ArrayList<>();
		List<Integer> xbReturn=new ArrayList<>();
		List<Integer> frReturn=new ArrayList<>();
		List<Integer> bgReturn=new ArrayList<>();
				
		//��ȡ��ʼʱ��
		Date sd=sdf.parse(d1);
        Timestamp s = getStime(sd);
		//��ȡ����ʱ��
        Date ed=sdf.parse(d2);
		Timestamp e = getEtime(ed);
		AddLog.addLog(Log.QUERY,"��ѯ����'"+s+"'��'"+e+"'ʱ��ε�'"+type+"'ҵ���˻ر�����Ϣ");
		String returnXBStatus="2,4,6,8,10,13,15";
		String returnFRStatus="19,21,23,25,27,30,32";
		String returnBGStatus="3,4";
		String result="";
		String FRresult="";
		String BGresult="";
		int XBCount=0;
		int FRCount=0;
		int BGCount=0;
		for(Division division:branchs){
			String branchid=division.getDivisioncode();
			if(!"0000".equals(branchid)){
				names.add(division.getDivisionname());
				if(type.equals("XB")){
					xb.add(countservice.getCount(branchid,"XB",s,e));
					result="select count(*) from ys_title where branchId="+branchid+" and TitleNo like'%XB%' and status in("+returnXBStatus+") and cdate between '"+s+"' and '"+e+"'";
					XBCount=countservice.getXBCountByTime(result);
					xbReturn.add(XBCount);
					fr.add(1);
					frReturn.add(0);
					bg.add(1);
					bgReturn.add(0);
				}else if(type.equals("FR")){
					xb.add(1);
					xbReturn.add(0);
					fr.add(countservice.getCount(branchid,"FR",s,e));
					FRresult="select count(*) from ys_title where branchId="+branchid+" and TitleNo like'%FR%' and status in("+returnFRStatus+") and cdate between '"+s+"' and '"+e+"'";
					FRCount=countservice.getFRCountByTime(FRresult);
					frReturn.add(FRCount);
					bg.add(1);
					bgReturn.add(0);
				}else if(type.equals("BG")){
					xb.add(1);
					xbReturn.add(0);
					fr.add(1);
					frReturn.add(0);
					bg.add(countservice.getCount(branchid,"BG",s,e));
					BGresult="select count(*) from ys_title where branchId="+branchid+" and TitleNo like'%BG%' and status in("+returnBGStatus+") and cdate between '"+s+"' and '"+e+"'";
					BGCount=countservice.getBGCountByTime(BGresult);
					bgReturn.add(BGCount);
				}
			}
		}
			
		Map<String,Object> map=new HashMap<>();
		map.put("names", names);
		map.put("xb",xb);
		map.put("fr",fr);
		map.put("bg",bg);
		map.put("xbReturn",xbReturn);
		map.put("frReturn", frReturn);
		map.put("bgReturn", bgReturn);
		return map;
	}
	//����ҵ�����ͺͷ����Ĳ����˻�����
	@RequestMapping("getByTypeAndBranch")
	@ResponseBody
	public Object getByTypeAndBranch(String type,String branchIds) throws ParseException{
		List<Division> branchs=new ArrayList<>();
		Division div=null;
		List<String> names=new ArrayList<>();
		String[] branchId=branchIds.split(",");
		String log="";
		for(int i=0;i<branchId.length;i++){
			div=divisionService.getDivisionByCode(branchId[i]);
			log+=div.getDivisionname()+",";
			branchs.add(div);
		}
		AddLog.addLog(Log.QUERY,"��ѯ'"+log.substring(0, log.lastIndexOf(","))+"'�ȷ�֧������'"+type+"'ҵ���˻ر�����Ϣ");
		List<Integer> xb=new ArrayList<>();
		List<Integer> fr=new ArrayList<>();
		List<Integer> bg=new ArrayList<>();
		List<Integer> xbReturn=new ArrayList<>();
		List<Integer> frReturn=new ArrayList<>();
		List<Integer> bgReturn=new ArrayList<>();
				
		String returnXBStatus="2,4,6,8,10,13,15";
		String returnFRStatus="19,21,23,25,27,30,32";
		String returnBGStatus="3,4";
		String result="";
		String FRresult="";
		String BGresult="";
		int XBCount=0;
		int FRCount=0;
		int BGCount=0;
		for(Division division:branchs){
			String branchid=division.getDivisioncode();
			names.add(division.getDivisionname());
			if(type.equals("XB")){
				xb.add(countservice.getYWCountByType(branchid,"XB"));
				result="select count(*) from ys_title where branchId="+branchid+" and TitleNo like'%XB%' and status in("+returnXBStatus+")";
				XBCount=countservice.getXBCountByTime(result);
				xbReturn.add(XBCount);
				fr.add(1);
				frReturn.add(0);
				bg.add(1);
				bgReturn.add(0);
			}else if(type.equals("FR")){
				xb.add(1);
				xbReturn.add(0);
				fr.add(countservice.getYWCountByType(branchid,"FR"));
				FRresult="select count(*) from ys_title where branchId="+branchid+" and TitleNo like'%FR%' and status in("+returnFRStatus+")";
				FRCount=countservice.getFRCountByTime(FRresult);
				frReturn.add(FRCount);
				bg.add(1);
				bgReturn.add(0);
			}else if(type.equals("BG")){
				xb.add(1);
				xbReturn.add(0);
				fr.add(1);
				frReturn.add(0);
				bg.add(countservice.getYWCountByType(branchid,"BG"));
				BGresult="select count(*) from ys_title where branchId="+branchid+" and TitleNo like'%BG%' and status in("+returnBGStatus+")";
				BGCount=countservice.getBGCountByTime(BGresult);
				bgReturn.add(BGCount);
			}
		}
			
		Map<String,Object> map=new HashMap<>();
		map.put("names", names);
		map.put("xb",xb);
		map.put("fr",fr);
		map.put("bg",bg);
		map.put("xbReturn",xbReturn);
		map.put("frReturn", frReturn);
		map.put("bgReturn", bgReturn);
		return map;
	}

	//����ʱ��Ρ������ĺ�ҵ��ʽ�����˻�����
	@RequestMapping("getByTBT")
	@ResponseBody
	public Object getByTBT(String d1,String d2,String branchIds,String type) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Division> branchs=new ArrayList<>();
		Division div=null;
		List<String> names=new ArrayList<>();
		String[] branchId=branchIds.split(",");
		String log="";
		for(int i=0;i<branchId.length;i++){
			div=divisionService.getDivisionByCode(branchId[i]);
			log+=div.getDivisionname()+",";
			branchs.add(div);
		}
		List<Integer> xb=new ArrayList<>();
		List<Integer> fr=new ArrayList<>();
		List<Integer> bg=new ArrayList<>();
		List<Integer> xbReturn=new ArrayList<>();
		List<Integer> frReturn=new ArrayList<>();
		List<Integer> bgReturn=new ArrayList<>();
				
		//��ȡ��ʼʱ��
		Date sd=sdf.parse(d1);
        Timestamp s = getStime(sd);
		//��ȡ����ʱ��
        Date ed=sdf.parse(d2);
		Timestamp e = getEtime(ed);
		AddLog.addLog(Log.QUERY,"��ѯ'"+log.substring(0, log.lastIndexOf(","))+"'�ȷ�֧������'"+s+"'��'"+e+"'ʱ��ε�'"+type+"'ҵ���˻���Ϣ");
		String returnXBStatus="2,4,6,8,10,13,15";
		String returnFRStatus="19,21,23,25,27,30,32";
		String returnBGStatus="3,4";
		String result="";
		String FRresult="";
		String BGresult="";
		int XBCount=0;
		int FRCount=0;
		int BGCount=0;
		for(Division division:branchs){
			String branchid=division.getDivisioncode();
			names.add(division.getDivisionname());
			if(type.equals("XB")){
				xb.add(countservice.getCount(branchid,"XB",s,e));
				result="select count(*) from ys_title where branchId="+branchid+" and TitleNo like'%XB%' and status in("+returnXBStatus+") and cdate between '"+s+"' and '"+e+"'";
				XBCount=countservice.getXBCountByTime(result);
				xbReturn.add(XBCount);
				fr.add(1);
				frReturn.add(0);
				bg.add(1);
				bgReturn.add(0);
			}else if(type.equals("FR")){
				xb.add(1);
				xbReturn.add(0);
				fr.add(countservice.getCount(branchid,"FR",s,e));
				FRresult="select count(*) from ys_title where branchId="+branchid+" and TitleNo like'%FR%' and status in("+returnFRStatus+") and cdate between '"+s+"' and '"+e+"'";
				FRCount=countservice.getFRCountByTime(FRresult);
				frReturn.add(FRCount);
				bg.add(1);
				bgReturn.add(0);
			}else if(type.equals("BG")){
				xb.add(1);
				xbReturn.add(0);
				fr.add(1);
				frReturn.add(0);
				bg.add(countservice.getCount(branchid,"BG",s,e));
				BGresult="select count(*) from ys_title where branchId="+branchid+" and TitleNo like'%BG%' and status in("+returnBGStatus+") and cdate between '"+s+"' and '"+e+"'";
				BGCount=countservice.getBGCountByTime(BGresult);
				bgReturn.add(BGCount);
			}
		}
			
		Map<String,Object> map=new HashMap<>();
		map.put("names", names);
		map.put("xb",xb);
		map.put("fr",fr);
		map.put("bg",bg);
		map.put("xbReturn",xbReturn);
		map.put("frReturn", frReturn);
		map.put("bgReturn", bgReturn);
		return map;
	}
}
