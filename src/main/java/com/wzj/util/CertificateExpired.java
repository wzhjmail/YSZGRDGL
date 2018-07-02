package com.wzj.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wzj.service.application.impl.CompanyInfoService;

//判断证书是否符合复认的条件
public class CertificateExpired implements ServletContextListener{
	static int num=1;
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Timer timer = new Timer();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,1);
		calendar.set(Calendar.MINUTE,00);
		calendar.set(Calendar.SECOND,00);
		Date time=calendar.getTime();//每天的凌晨
		timer.schedule(new TimerTask(){
			public void run(){
				if(num!=1){//不知道为什么，项目启动的时候，第一次加载会有问题
					//使用WebApplicationContextUtils工具类，获取spring容器的引用
					CompanyInfoService companyInfoService=WebApplicationContextUtils.getWebApplicationContext(
							sce.getServletContext()).getBean(CompanyInfoService.class);
					//循环ys_company,如果证书过期则修改其状态
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
