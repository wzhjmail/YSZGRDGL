package com.wzj.common;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.util.WorkflowUtil;

/*@Component("deploymentActivitiBean")
public class DeploymentActiviti implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Boolean first = event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext");
		if(first)
			System.out.println("<<<<<<<<<<<spring��ʼ�����>>>>>>>>>>>>");
	}
}
//�ú����ڿ�������������ʼ��ʱִ��һ�Ρ�
@Component("deploymentActivitiBean")
public class DeploymentActiviti implements InitializingBean{
	@Autowired
	private WorkflowUtil workflowUtil;
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("<<<<<<<<<<<��������ʵ��>>>>>>>>>>>>");
		workflowUtil.deployment("activiti/zzrd", "������ҵ���������");
	}
	
}*/
