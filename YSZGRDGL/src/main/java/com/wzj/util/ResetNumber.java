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
@Component//把普通pojo实例化到spring容器中，相当于在文件中配置了<bean id="" class=""/>
public class ResetNumber implements ServletContextListener{
	/*因为在调用listener时，
	还可以直接使用@Autowired，然后在初始化的时候使用下面一句：
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
		calendar.set(Calendar.DAY_OF_YEAR,1);//每年的第一天
		calendar.set(Calendar.HOUR,01);
		calendar.set(Calendar.MINUTE,00);
		calendar.set(Calendar.SECOND,00);
		Date time = calendar.getTime();
		timer.schedule(new TimerTask(){//如果时间过了则直接执行
			public void run(){
				if(num!=1){
					System.out.println("流水号重置！");
					Number num = numberService.getNumber();
					num.setXbNum(1);
					num.setFrNum(1);
					num.setBgNum(1);
					numberService.setNumber(num);
				}else{
					num=2;
				}
			}
		},time);//time时执行，一年执行一次
	}
}
