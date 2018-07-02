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
		//�жϵ�ǰ�û������ͣ������ǰ�û�Ϊ��ҵ�û������ѯ��Ӧ�ķ�֧�����������û�����ӵ���ĺ�ѡ��
		//if(activeUser belongTo 
		delegateTask.addCandidateUser("��֧�������û���");
		//�жϵ�ǰ�û������ͣ������ǰ�û�Ϊ��֧�����û������ѯ��Ӧ�ı������ĵ������û�����ӵ���ĺ�ѡ��
	}

}
