package com.wzj.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.pojo.SysUser;
import com.wzj.pojo.WorkflowBean;
import com.wzj.service.IActivitiService;
@Service("activitiService")
public class ActivitiService implements IActivitiService{
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private SysUserService userService;
	@Autowired
	private ProcessEngine processEngine;
	
	@Override
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery()//������������ѯ
				.orderByDeploymenTime().asc()//
				.list();
		return list;
	}
	
	@Override
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//�������̶����ѯ
							.orderByProcessDefinitionVersion().asc()//
							.list();
		return list;
	}
	
	@Override
	public String deployment(String src, String name) {
		Deployment deployment = repositoryService.createDeployment()
				.name(name)
				.addClasspathResource(src+".bpmn")
				.addClasspathResource(src+".png")
				.deploy();
		System.out.println("����Id:"+deployment.getId());
		System.out.println("��������"+deployment.getName());
		return deployment.getId();
	}
	/**�������״̬����������ʵ����������������ʵ������ҵ��*/
	public void startProcess(WorkflowBean workflowBean,String name) {
		//1����ȡ��ٵ�ID��ʹ����ٵ�ID����ѯ��ٵ��Ķ���LeaveBill
		/*Long id = workflowBean.getId();
		LeaveBill leaveBill = leaveBillDao.findLeaveBillById(id);
		//2��������ٵ������״̬��0���1����ʼ¼��-->����У�
		leaveBill.setState(1);
		//3��ʹ�õ�ǰ�����ȡ�����̶����key����������ƾ������̶����key��
		String key = leaveBill.getClass().getSimpleName();
		//4����Session�л�ȡ��ǰ����İ����ˣ�ʹ�����̱���������һ������İ�����
		    // inputUser�����̱��������ƣ�
		    // ��ȡ�İ����������̱�����ֵ
		 
		Map<String, Object> variables = new HashMap<String,Object>();
		variables.put("inputUser",name);//��ʾΩһ�û�
		//5��	(1)ʹ�����̱��������ַ�������ʽ��LeaveBill.id����ʽ����ͨ�����ã������������̣�����ʵ��������ҵ��
   		//		(2)ʹ������ִ�ж�����е�һ���ֶ�BUSINESS_KEY��Activiti�ṩ��һ���ֶΣ��������������̣�����ʵ��������ҵ��
		 
		//��ʽ��LeaveBill.id����ʽ��ʹ�����̱�����
		String objId = key+"."+id;
		variables.put("objId", objId);
		//6��ʹ�����̶����key����������ʵ����ͬʱ�������̱�����ͬʱ������ִ�е�ִ�ж�����е��ֶ�BUSINESS_KEY���ҵ�����ݣ�ͬʱ�����̹���ҵ��
		runtimeService.startProcessInstanceByKey(key,objId,variables);	*/
	}

	@Override
	public String startProcessById(String processId,Map<String,Object> map) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processId,map);
		System.out.println("����ʵ��ID"+processInstance.getId());
		System.out.println("���̶���ID"+processInstance.getProcessDefinitionId());
		return processInstance.getId();
	}

	@Override
	public String startProcessByName(String processName,Map<String,Object> map) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processName,map);
		System.out.println("����ʵ��ID"+processInstance.getId());
		System.out.println("���̶���ID"+processInstance.getProcessDefinitionId());
		return processInstance.getId();
	}

	@Override
	public List<Task> findPersonalTask(String assignee) {
		List<Task> lists = processEngine.getTaskService().createTaskQuery()
				.taskAssignee(assignee).list();
		if(lists !=null && lists.size()>0){
			for(Task task : lists){
				System.out.println("����Id��"+task.getId());
				System.out.println("��������"+task.getName());
				System.out.println("����ʱ�䣺"+task.getCreateTime());
			}
		}else{
			System.out.println("û�������Ϣ��");
		}
		return lists;
	}
	
	@Override
	public List<Task> findGroupTask(String candidateUser) {
		List<Task> lists = taskService.createTaskQuery()
				.taskCandidateUser(candidateUser).list();
		if(lists !=null && lists.size()>0){
			for(Task task : lists){
				System.out.println("����Id��"+task.getId());
				System.out.println("����İ����ˣ�"+task.getAssignee());  
	            System.out.println("�������ƣ�"+task.getName());  
				System.out.println("��������"+task.getName());
				System.out.println("����ʱ�䣺"+task.getCreateTime());
				System.out.println("����ʵ��ID��"+task.getProcessInstanceId());  
			}
		}else{
			System.out.println("û�������Ϣ��");
		}
		return lists;
	}

	@Override
	public boolean completeTaskById(String taskId, Map<String, Object> map) {
		try{
			taskService.complete(taskId,map);
		}catch(org.activiti.engine.ActivitiObjectNotFoundException e){
			return false;
		}
		return true;
	}

	//@Override
	public void getImageById(String deploymentId, String resourceName) throws Exception{
		InputStream inputStream = repositoryService
				.getResourceAsStream("7501", "helloWorld/HelloWorld.png");
		FileUtils.copyInputStreamToFile(inputStream, new File("E:\\hello.png"));
	}
	 
	@Override
	public Map<String, Object> findCoordinateByTaskid(String taskId) {
		Map<String,Object> map = new HashMap<String,Object>();
		Task task = taskService.createTaskQuery()
				.taskId(taskId).singleResult();
		if(task!=null){
			//��ȡ���̶����Id
			String processDefinitionId = task.getProcessDefinitionId();
			//��ȡ���̶����ʵ����󣨶�Ӧ.bpmn�ļ��е����ݣ�
			ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService
					.getProcessDefinition(processDefinitionId);
			//����ʵ��Id
			String processInstanceId = task.getProcessInstanceId();
			//ʹ������ʵ��Id����ѯ����ִ�е�ִ�ж������ȡ��ǰ���Ӧ������ʵ������
			ProcessInstance pi = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId)//ʹ������ʵ��Id��ѯ
					.singleResult();
			//��ȡ��ǰ���Id
			String activityId = pi.getActivityId();
			//��ȡ��ǰ�����
			ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
			//��ȡ����
			map.put("x",activityImpl.getX());
			map.put("y",activityImpl.getY());
			map.put("width",activityImpl.getWidth());
			map.put("height",activityImpl.getHeight());
		}else{//���task������
			map.put("x",0);
			map.put("y",0);
			map.put("width",0);
			map.put("height",0);
		}
		return map;
	}

	@Override
	public List<HistoricActivityInstance> queryHistoricInstanceById(String processInstanceId) {
		List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricActivityInstanceEndTime()
				.asc().list();
		return list;
	}

	@Override
	public List<Task> findUncompleteTasks(String name) {
		 List<Task> list = taskService.createTaskQuery().
				taskAssignee(name).
				orderByTaskCreateTime().
				desc().list();
		return list;
	}

	@Override
	public List<HistoricTaskInstance> findCompleteTasks(String name) {
		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().
				taskAssignee(name).
				orderByTaskCreateTime().
				desc().list();
		return list;
	}

	@Override
	public boolean addComment(String taskId, String msg) {
		taskService.addComment(taskId,getProcessInstanceId(taskId),msg);
		return true;
	}

	@Override
	public List<Comment> findCommentByTaskId(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		String processInstanceId = getProcessInstanceId(taskId);
		list = taskService.getProcessInstanceComments(processInstanceId);
		return list;
	}

	public String getProcessInstanceId(String taskId){
		Task task = taskService.createTaskQuery()
				.taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		return processInstanceId;
	}

	@Override
	public void setRole(String GroupName, String roleName) {
		//��ӽ�ɫ
		identityService.saveGroup(new GroupEntity(GroupName));
		//��ȡ�ý�ɫ��Ӧ���û�
		List<SysUser> users = userService.getUsersByRoleName(roleName);
		for(int i=0;i<users.size();i++){
			String userName = users.get(i).getUsername();
			//����û�
			identityService.saveUser(new UserEntity(userName));
			//�����û���ɫ��ϵ
			identityService.createMembership(userName, GroupName);
		}
	}

}
