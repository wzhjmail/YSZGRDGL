package com.wzj.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.SysRole;
import com.wzj.pojo.WorkflowBean;
@Component
public class WorkflowUtil {
	/*ApplicationContext applicationContext= new ClassPathXmlApplicationContext(
			new String[]{"classpath:spring-mybatis.xml","classpath:spring-mvc.xml"});
	ProcessEngine processEngine=(ProcessEngine)applicationContext.getBean("processEngine");
	*/
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	IdentityService identityService;
	@Autowired
	TaskService taskService;
	@Autowired
	FormService formService;
	@Autowired 
	RuntimeService runtimeService;
	@Autowired
	HistoryService historyService;
	
	/**����zip�ļ���������ʵ��*/
	public void saveNewDeploye(File file, String filename,List<SysRole> roles) {
		try {
			//2����File���͵��ļ�ת����ZipInputStream��
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
			repositoryService.createDeployment()//�����������
							.name(filename)//��Ӳ�������
							.addZipInputStream(zipInputStream)//
							.deploy();//��ɲ���
			for(SysRole role:roles){
				identityService.saveGroup(role);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**bpmn�ļ��ĵ�ַ+���ַ�������*/
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
	
	/**�������*/
	public void addComment(String taskId,String msg){
		//��ȡ��ǰ�û�����Ϣ
		Session session = SecurityUtils.getSubject().getSession();
		ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
		//����taskId��ȡ�����������,��������������ȡ������ִ����������
	    //String user = processEngine.getTaskService().getIdentityLinksForTask(taskId).get(0).getUserId();
		/*ע�⣺�����ע��ʱ������Activiti�ײ�ʹ�ã�
		 * String userId = Authentication.getAuthenticatedUserId();
		 * CommentEntity comment = new CommentEntity();
		 * comment.setUserId(userId);
		 * ������Ҫ�ƶ�����İ����ˣ���Ӧact_hi_comment���е�User_ID�ֶΣ����������ˣ�Ĭ��Ϊnull
		 * ����Ҫ��������ã�ִ��ʹ��Authentication.setAuthenticatedUserId();��ӵ�ǰ���������� */
		
		Authentication.setAuthenticatedUserId(activeUser.getRealname());
		String processInstanceId = getTaskById(taskId).getProcessInstanceId();
		taskService.addComment(taskId, processInstanceId, msg);
	}
	
	/**�������*/
	public void addComment(String taskId,String msg,String type){
		//��ȡ��ǰ�û�����Ϣ
		Session session = SecurityUtils.getSubject().getSession();
		ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
		Authentication.setAuthenticatedUserId(activeUser.getRealname());
		String processInstanceId = getTaskById(taskId).getProcessInstanceId();
		taskService.addComment(taskId, processInstanceId,type,msg);
	}
	
	/**��ѯ���������Ϣ����Ӧ��act_re_deployment��*/
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService
				.createDeploymentQuery()//������������ѯ
				.orderByDeploymenTime().asc()//
				.list();
		return list;
	}
	
	/**��ѯ���̶������Ϣ����Ӧ��act_re_procdef��*/
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService
				.createProcessDefinitionQuery()//�������̶����ѯ
				.orderByProcessDefinitionVersion().asc()//
				.list();
		return list;
	}
	
	/**ʹ�ò������ID��ɾ�����̶���*/
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
		//����ɾ����ɾ����ǰ������ص�������Ϣ����������ִ�е���Ϣ����ʷ��Ϣ��
		repositoryService.deleteDeployment(deploymentId, true);
	}

	/**ʹ�õ�ǰ�û�����ѯ����ִ�е��������ȡ��ǰ����ļ���List<Task>*/
	public List<Task> findTaskListByName(String name) {
		List<Task> list = taskService
				.createTaskQuery()//
				.taskAssignee(name)//ָ�����������ѯ
				.orderByTaskCreateTime().desc()//����ķ�������
				.list();
		return list;
	}
	
	/**ʹ�õ�ǰ�û�����ѯ�����񣬻�ȡ��ǰ����ļ���List<Task>*/
	public List<Task> findGroupTaskListByName(String name) {
		List<Task> list = taskService
				.createTaskQuery()//
				.taskCandidateUser(name)//ָ�����������ѯ
				.orderByTaskCreateTime().asc()//
				.list();
		return list;
	}
	
	/**ʹ�ýڵ����͵�ǰ�û�����ѯ�������񣬻�ȡ��ǰ������ļ���List<Task>*/
	public List<Task> findGroupTaskListByName(String pointName,String userName) {
		List<Task> list = taskService
				.createTaskQuery()//
				.taskName(pointName)
				.taskCandidateUser(userName)//��ѡ�˲�ѯ
				.orderByTaskCreateTime().asc()//
				.list();
		return list;
	}
	
	/**ʹ�ýڵ����͵�ǰ�û�����ѯ�������񣬻�ȡ��ǰ��������ļ���List<Task>*/
	public List<Task> findPersonalTaskListByName(String pointName,String userName) {
		List<Task> list = taskService
				.createTaskQuery()//
				.taskName(pointName)
				.taskAssignee(userName)//ָ�����������ѯ
				.orderByTaskCreateTime().asc()//
				.list();
		return list;
	}
	
	/**ʹ������ID����ȡ��ǰ����ڵ��ж�Ӧ��Form key�е����ӵ�ֵ��*/
	public String findTaskFormKeyByTaskId(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);
		//��ȡForm key��ֵ
		String url = formData.getFormKey();
		return url;
	}
	
	/**һ��ʹ������ID������ҵ�����ID*/
	public String findBussinessIdByTaskId(String taskId) {
		//1��ʹ������ID����ѯ�������Task
		Task task = getTaskById(taskId);
		//2��ʹ���������Task��ȡ����ʵ��ID
		String processInstanceId = task.getProcessInstanceId();
		//3��ʹ������ʵ��ID����ѯ����ִ�е�ִ�ж������������ʵ������
		ProcessInstance pi = runtimeService
				.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)//ʹ������ʵ��ID��ѯ
				.singleResult();
		//4��ʹ������ʵ�������ȡBUSINESS_KEY
		String buniness_key = pi.getBusinessKey();
		//5����ȡBUSINESS_KEY��Ӧ������ID��ʹ������ID����ѯ��ٵ�����LeaveBill.1��
		String id = "";
		if(StringUtils.isNotBlank(buniness_key)){
			//��ȡ�ַ�����ȡbuniness_keyС����ĵ�2��ֵ
			id = buniness_key.split("\\:")[1];
		}
		//����ҵ�����ID
		return id;
	}
	
	/**������֪����ID����ѯProcessDefinitionEntiy���󣬴Ӷ���ȡ��ǰ�������֮����������ƣ������õ�List<String>������*/
	public List<String> findOutComeListByTaskId(String taskId) {
		//���ش�����ߵ����Ƽ���
		List<String> list = new ArrayList<String>();
		//1:ʹ������ID����ѯ�������
		Task task = getTaskById(taskId);
		//2����ȡ���̶���ID
		String processDefinitionId = task.getProcessDefinitionId();
		//3����ѯProcessDefinitionEntiy����
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService
				.getProcessDefinition(processDefinitionId);
		//ʹ���������Task��ȡ����ʵ��ID
		String processInstanceId = task.getProcessInstanceId();
		//ʹ������ʵ��ID����ѯ����ִ�е�ִ�ж������������ʵ������
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//ʹ������ʵ��ID��ѯ
					.singleResult();
		//��ȡ��ǰ���id
		String activityId = pi.getActivityId();
		//4����ȡ��ǰ�Ļ
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		//5����ȡ��ǰ����֮�����ߵ�����
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if(pvmList!=null && pvmList.size()>0){
			for(PvmTransition pvm:pvmList){
				String name = (String) pvm.getProperty("name");
				if(StringUtils.isNotBlank(name)){
					list.add(name);
				}
				else{
					list.add("ֱ�Ӱ���");
				}
			}
		}
		return list;
	}
	
	/**��ȡ��ע��Ϣ�����ݵ��ǵ�ǰ����ID����ȡ��ʷ����ID��Ӧ����ע*/
	public List<Comment> findCommentByTaskId(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		//ʹ�õ�ǰ������ID����ѯ��ǰ���̶�Ӧ����ʷ����ID
		//ʹ�õ�ǰ����ID����ȡ��ǰ�������
		Task task = getTaskById(taskId);
		//��ȡ����ʵ��ID
		String processInstanceId = task.getProcessInstanceId();
		//1.��ȡ��ǰ������
		//List<Comment> currentComment=taskService.getTaskComments(taskId);
		//List<Comment> currentComment=taskService.getProcessInstanceComments(processInstanceId);
		//list.addAll(currentComment);
		//2.��ȡ��ʷ���
		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()//��ʷ������ѯ
				.processInstanceId(processInstanceId).orderByHistoricTaskInstanceEndTime().desc().list();//ʹ������ʵ��ID��ѯ
		//�������ϣ���ȡÿ������ID
		if(htiList!=null && htiList.size()>0){
			for(HistoricTaskInstance hti:htiList){
				String htaskId = hti.getId();//����ID
				//��ȡ��ע��Ϣ
				List<Comment> taskList = taskService.getTaskComments(htaskId);//������ʷ��ɺ������ID
				list.addAll(taskList);
			}
		}
		/*List<Comment> result = new ArrayList<Comment>();
		for(Comment comment:list){
			boolean bool=true;
			for(int i=0;i<result.size();i++){
				if(result.get(i).getId().equals(comment.getId())){
					bool=false;
					break;
				}
			}
			if(bool)
				result.add(comment);
		}*/
		return list;
	}
	
	//����taskId��ȡ��ǰ�����Ƿ��Ѿ�д�������
	public Comment findLastCommentByTaskId(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		list=taskService.getTaskComments(taskId);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**ָ�����ߵ����ƣ��������*/
	public void completeTask(WorkflowBean workflowBean,Map<String,Object> map){
		//��ȡ����ID
		String taskId = workflowBean.getTaskId();
		//��ȡ���ߵ�����
		String outcome = workflowBean.getOutcome();
		//��ע��Ϣ
		String message = workflowBean.getComment();
		//1.�����֮ǰ�����һ����ע��Ϣ����act_hi_comment����������ݣ����ڼ�¼�Ե�ǰ�����˵�һЩ�����Ϣ
		if(StringUtils.isNotBlank(message))
			addComment(taskId,message);
		//2.����ǡ�Ĭ���ύ���Ͳ���Ҫ��һ����������Ҫ�������̱���
			//�������֮ǰ���������̱������������ߵ�����ȥ�������
			//���̱��������ƣ�outcome
		if(outcome!=null&&!outcome.equals("ֱ�Ӱ���"))
			map.put("outcome", outcome);
		taskService.complete(taskId,map);
	}
	
	/**��������ID����ȡ�������ʹ����������ȡ���̶���ID����ѯ���̶������*/
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		//ʹ������ID����ѯ�������
		Task task = getTaskById(taskId);
		//��ȡ���̶���ID
		String processDefinitionId = task.getProcessDefinitionId();
		//��ѯ���̶���Ķ���
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//�������̶����ѯ���󣬶�Ӧ��act_re_procdef 
					.processDefinitionId(processDefinitionId)//ʹ�����̶���ID��ѯ
					.singleResult();
		return pd;
	}
	
	/**����taskID��ȡtask*/
	public Task getTaskById(String taskId){
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//ʹ������ID��ѯ
				.singleResult();
		return task;
	}
	
	//�����������ƻ�ȡ�����б�
	public List<Task> getTaskByPointName(String name){
		List<Task> tasks = taskService.createTaskQuery()
				.taskName(name)
				.orderByTaskCreateTime().asc().list();
		return tasks;
	}
	
	//��������Id�ͽڵ�Id�ƻ�ȡ�����б�
	public List<Task> getTaskByIds(String processId,String pointId){
		List<Task> tasks = taskService.createTaskQuery()
				.processDefinitionKey(processId)
				.taskDefinitionKey(pointId)
				.orderByTaskCreateTime().asc().list();
		return tasks;
	}
	
	//���������б��ȡҵ��ID�ַ���
	public List<String> getBussinessIdsByTasks(List<Task> tasks){
		List businessIds = new ArrayList<String>();
		for(int i=0;i<tasks.size();i++){
			String bussinessId = findBussinessIdByTaskId(tasks.get(i).getId());
			businessIds.add(bussinessId);
		}
		return businessIds;
	}
	
	//���������б��ȡҵ��ID�ַ���,����taskId��businessId
	public Map<String,String> getTaskAndBussIdByTask(List<Task> tasks){
		Map<String, String> map = new HashMap<String,String>();
		for(int i=0;i<tasks.size();i++){
			String taskId=tasks.get(i).getId();
			String bussinessId = findBussinessIdByTaskId(taskId);
			map.put(bussinessId,taskId);
		}
		return map;
	}
	
	//�����������˻ص�������
	public void setAssigneeTask(String taskId){
		taskService.setAssignee(taskId, null);
	}
	
	//��ѯһ�����̾����˶��ٸ��ڵ㣨�����������㱻�˻صĴ�����
	/*public int getCount(String taskId){
		Task task = getTaskById(taskId);
		//2����ȡ���̶���ID
		String processDefinitionId = task.getProcessDefinitionId();
		task.getExecutionId();
		return 0;
	}*/
	
	//��������Idɾ������ʵ��
	public void deleteProcess(String taskId){
		Task task=getTaskById(taskId);
		String pid=task.getProcessInstanceId();
		runtimeService.deleteProcessInstance(pid,"û������");
	}
}
