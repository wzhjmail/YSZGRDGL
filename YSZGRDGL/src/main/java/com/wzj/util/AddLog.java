package com.wzj.util;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.Division;
import com.wzj.pojo.Log;
import com.wzj.service.impl.LogService;
@Component//把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
public class AddLog {
	@Autowired
	private LogService logServices;
	
	private static LogService logService;
	@PostConstruct  //@PostConstruct和@PreDestroy方法实现初始化和销毁bean之前进行操作
    public void init() {  
		logService=logServices;
    } 
	
	public static int addLog(String branchName,String name,String operationType, String operation){
		//将Date类型转换为带时分秒毫秒的Timestamp的类型
		Log log = new Log(new Timestamp(new Date().getTime()),name,branchName,operationType,operation);
		return logService.insert(log);
	}
	
	public static int addLog(String operationType, String operation){
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		Log log = new Log(new Timestamp(new Date().getTime()),activeUser.getRealname(),
				activeUser.getBranchName(),operationType,operation);
        return logService.insert(log);
	}
}
