package com.wzj.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wzj.pojo.Log;
import com.wzj.service.impl.LogService;

public class DeleteLogAtFixTime implements ServletContextListener{
	@Override
	public void contextDestroyed(ServletContextEvent sce) {}
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//ʹ��WebApplicationContextUtils�����࣬��ȡspring����������
		LogService logService=WebApplicationContextUtils.getWebApplicationContext(
				sce.getServletContext()).getBean(LogService.class);
		
		/*Timer timer = new Timer();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, 1);//������������ÿ�ܵĵ�һ�졣�п��ܲ�ͬ
		calendar.set(Calendar.HOUR_OF_DAY, 3);//���������
		calendar.set(Calendar.MINUTE,00);
		calendar.set(Calendar.SECOND,00);
		Date time = calendar.getTime();//�ó�ִ�������ʱ�䣬ÿ������賿����
		timer.scheduleAtFixedRate(new TimerTask(){
			public void run(){
				Calendar cld = Calendar.getInstance();
				cld.setTime(new Date());
				String str=cld.get(Calendar.YEAR)+"-"+cld.get(Calendar.MONTH)
							+"-"+cld.get(Calendar.DAY_OF_MONTH);
				if(logService==null){
					System.out.println("�ն���");
				}else{//��ǰʱ���һ���¡�
					logService.deleteBefore(str+" "+"00:00");
					System.out.println("ɾ���ɹ�");
				}
			}
		},time,7*24*60*60*1000);//timeʱִ�У�һ��ִ��һ��*/	
		
		Timer timer = new Timer();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);//ÿ�µ�1�ţ��賿һ��
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE,00);
		calendar.set(Calendar.SECOND,00);
		Date time = calendar.getTime();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR,-1);
				Date date=cal.getTime();
				long bef=date.getTime();
				File path=new File("E:\\export");
				if(!path.exists())//���·�������ڣ��򴴽�·��
					path.mkdirs();
				File file=new File("E:\\export\\log.txt");
				try {
					if(!file.exists())//����ļ��������򴴽��ļ�
						file.createNewFile();
					FileWriter fw=new FileWriter(file);
					//��ȡ���е���־��Ϣ
					List<Log> logs=logService.findAll();
					for(Log log:logs){
						long logTime=log.getTime().getTime();
						if(logTime>bef){//������������־��Ϣ����
							fw.write(log.getId() + "\t");
							fw.write(log.getTime() + "\t\t");
							fw.write(log.getPerson()+ "\t");
							fw.write(log.getDepartment()+ "\t\t\t\t");
							fw.write(log.getOperationType()+ "\t\t");
							fw.write(log.getOperation());
		                    fw.write("\r\n");
						}
					}
					fw.flush();
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				logService.deleteBefore(sdf.format(date));
				for(int i=0;i<1;i++)
					System.out.println("======="+i);
			}
		}, time);
	}
}
