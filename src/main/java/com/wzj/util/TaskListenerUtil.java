package com.wzj.util;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.wzj.pojo.ActiveUser;

public class TaskListenerUtil implements TaskListener {
	Subject subject = SecurityUtils.getSubject();
	ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
	Session session = subject.getSession();
	
	@Override
	public void notify(DelegateTask delegateTask) {
		ActiveUser activeUser = (ActiveUser)subject.getSession().getAttribute("activeUser");
		//判断当前用户的类型：如果当前用户为企业用户，则查询对应的分支机构的所有用户，添加到组的候选人
		//if(activeUser belongTo 
		delegateTask.addCandidateUser("分支机构的用户名");
		//判断当前用户的类型：如果当前用户为分支机构用户，则查询对应的编码中心的所有用户，添加到组的候选人
	}

}
