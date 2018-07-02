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
		List<Deployment> list = repositoryService.createDeploymentQuery()//创建部署对象查询
				.orderByDeploymenTime().asc()//
				.list();
		return list;
	}
	
	@Override
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//创建流程定义查询
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
		System.out.println("部署Id:"+deployment.getId());
		System.out.println("部署名："+deployment.getName());
		return deployment.getId();
	}
	/**更新请假状态，启动流程实例，让启动的流程实例关联业务*/
	public void startProcess(WorkflowBean workflowBean,String name) {
		//1：获取请假单ID，使用请假单ID，查询请假单的对象LeaveBill
		/*Long id = workflowBean.getId();
		LeaveBill leaveBill = leaveBillDao.findLeaveBillById(id);
		//2：更新请假单的请假状态从0变成1（初始录入-->审核中）
		leaveBill.setState(1);
		//3：使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
		String key = leaveBill.getClass().getSimpleName();
		//4：从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人
		    // inputUser是流程变量的名称，
		    // 获取的办理人是流程变量的值
		 
		Map<String, Object> variables = new HashMap<String,Object>();
		variables.put("inputUser",name);//表示惟一用户
		//5：	(1)使用流程变量设置字符串（格式：LeaveBill.id的形式），通过设置，让启动的流程（流程实例）关联业务
   		//		(2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
		 
		//格式：LeaveBill.id的形式（使用流程变量）
		String objId = key+"."+id;
		variables.put("objId", objId);
		//6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		runtimeService.startProcessInstanceByKey(key,objId,variables);	*/
	}

	@Override
	public String startProcessById(String processId,Map<String,Object> map) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processId,map);
		System.out.println("流程实例ID"+processInstance.getId());
		System.out.println("流程定义ID"+processInstance.getProcessDefinitionId());
		return processInstance.getId();
	}

	@Override
	public String startProcessByName(String processName,Map<String,Object> map) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processName,map);
		System.out.println("流程实例ID"+processInstance.getId());
		System.out.println("流程定义ID"+processInstance.getProcessDefinitionId());
		return processInstance.getId();
	}

	@Override
	public List<Task> findPersonalTask(String assignee) {
		List<Task> lists = processEngine.getTaskService().createTaskQuery()
				.taskAssignee(assignee).list();
		if(lists !=null && lists.size()>0){
			for(Task task : lists){
				System.out.println("任务Id："+task.getId());
				System.out.println("任务名："+task.getName());
				System.out.println("创建时间："+task.getCreateTime());
			}
		}else{
			System.out.println("没有相关信息！");
		}
		return lists;
	}
	
	@Override
	public List<Task> findGroupTask(String candidateUser) {
		List<Task> lists = taskService.createTaskQuery()
				.taskCandidateUser(candidateUser).list();
		if(lists !=null && lists.size()>0){
			for(Task task : lists){
				System.out.println("任务Id："+task.getId());
				System.out.println("任务的办理人："+task.getAssignee());  
	            System.out.println("任务名称："+task.getName());  
				System.out.println("任务名："+task.getName());
				System.out.println("创建时间："+task.getCreateTime());
				System.out.println("流程实例ID："+task.getProcessInstanceId());  
			}
		}else{
			System.out.println("没有相关信息！");
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
			//获取流程定义的Id
			String processDefinitionId = task.getProcessDefinitionId();
			//获取流程定义的实体对象（对应.bpmn文件中的数据）
			ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService
					.getProcessDefinition(processDefinitionId);
			//流程实例Id
			String processInstanceId = task.getProcessInstanceId();
			//使用流程实例Id，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
			ProcessInstance pi = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId)//使用流程实例Id查询
					.singleResult();
			//获取当前活动的Id
			String activityId = pi.getActivityId();
			//获取当前活动对象
			ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
			//获取坐标
			map.put("x",activityImpl.getX());
			map.put("y",activityImpl.getY());
			map.put("width",activityImpl.getWidth());
			map.put("height",activityImpl.getHeight());
		}else{//如果task不存在
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
		//添加角色
		identityService.saveGroup(new GroupEntity(GroupName));
		//获取该角色对应的用户
		List<SysUser> users = userService.getUsersByRoleName(roleName);
		for(int i=0;i<users.size();i++){
			String userName = users.get(i).getUsername();
			//添加用户
			identityService.saveUser(new UserEntity(userName));
			//建立用户角色关系
			identityService.createMembership(userName, GroupName);
		}
	}

}
