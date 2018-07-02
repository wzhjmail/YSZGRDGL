package com.wzj.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wzj.service.impl.NumberService;
import com.wzj.pojo.Number;
@Component//����ͨpojoʵ������spring�����У��൱�����ļ���������<bean id="" class=""/>
public class ResetNumber implements ServletContextListener{
	/*��Ϊ�ڵ���listenerʱ��
	������ֱ��ʹ��@Autowired��Ȼ���ڳ�ʼ����ʱ��ʹ������һ�䣺
	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);*/
	private static NumberService numberService;
	static int num=1;
	@Autowired
	public void ResetNumber(NumberService numberService){
		this.numberService=numberService;	
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {}
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Timer timer = new Timer();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR,1);//ÿ��ĵ�һ��
		calendar.set(Calendar.HOUR,01);
		calendar.set(Calendar.MINUTE,00);
		calendar.set(Calendar.SECOND,00);
		Date time = calendar.getTime();
		timer.schedule(new TimerTask(){//���ʱ�������ֱ��ִ��
			public void run(){
				if(num!=1){
					System.out.println("��ˮ�����ã�");
					Number num = numberService.getNumber();
					num.setXbNum(1);
					num.setFrNum(1);
					num.setBgNum(1);
					numberService.setNumber(num);
				}else{
					num=2;
				}
			}
		},time);//timeʱִ�У�һ��ִ��һ��
	}
}
