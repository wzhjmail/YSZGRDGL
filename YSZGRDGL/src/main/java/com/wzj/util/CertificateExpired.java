package com.wzj.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wzj.service.application.impl.CompanyInfoService;

//�ж�֤���Ƿ���ϸ��ϵ�����
public class CertificateExpired implements ServletContextListener{
	static int num=1;
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Timer timer = new Timer();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,1);
		calendar.set(Calendar.MINUTE,00);
		calendar.set(Calendar.SECOND,00);
		Date time=calendar.getTime();//ÿ����賿
		timer.schedule(new TimerTask(){
			public void run(){
				if(num!=1){//��֪��Ϊʲô����Ŀ������ʱ�򣬵�һ�μ��ػ�������
					//ʹ��WebApplicationContextUtils�����࣬��ȡspring����������
					CompanyInfoService companyInfoService=WebApplicationContextUtils.getWebApplicationContext(
							sce.getServletContext()).getBean(CompanyInfoService.class);
					//ѭ��ys_company,���֤��������޸���״̬
					companyInfoService.certificateExpired();
				}else{
					num=2;
				}
			}
		}, time);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {}

}
