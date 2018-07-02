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
			System.out.println("<<<<<<<<<<<spring初始化完成>>>>>>>>>>>>");
	}
}
//该函数在开启服务器，初始化时执行一次。
@Component("deploymentActivitiBean")
public class DeploymentActiviti implements InitializingBean{
	@Autowired
	private WorkflowUtil workflowUtil;
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("<<<<<<<<<<<部署流程实例>>>>>>>>>>>>");
		workflowUtil.deployment("activiti/zzrd", "新申请业务办理流程");
	}
	
}*/
