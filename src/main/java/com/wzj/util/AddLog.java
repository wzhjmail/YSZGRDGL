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
@Component//����ͨpojoʵ������spring�����У��൱�������ļ��е�<bean id="" class=""/>
public class AddLog {
	@Autowired
	private LogService logServices;
	
	private static LogService logService;
	@PostConstruct  //@PostConstruct��@PreDestroy����ʵ�ֳ�ʼ��������bean֮ǰ���в���
    public void init() {  
		logService=logServices;
    } 
	
	public static int addLog(String branchName,String name,String operationType, String operation){
		//��Date����ת��Ϊ��ʱ��������Timestamp������
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
