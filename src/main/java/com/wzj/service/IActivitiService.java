package com.wzj.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

public interface IActivitiService {
	//查询部署对象信息，对应表act_re_deployment
	List<Deployment> findDeploymentList();
	//查询流程定义的信息，对应表（act_re_procdef）
	List<ProcessDefinition> findProcessDefinitionList();
	//部署流程实例
	String deployment(String src,String name);//返回部署Id
	//通过Id启动流程实例
	String startProcessById(String processId,Map<String,Object> map);//返回流程实例Id
	//通过名称启动流程实例
	String startProcessByName(String processName,Map<String,Object> map);
	//查询个人任务
	List<Task> findPersonalTask(String assignee);
	//查询组任务
	List<Task> findGroupTask(String candidateUser);
	//完成个人任务
	boolean completeTaskById(String taskId,Map<String,Object> map);
	//获取图片
	//void getImageById(String deploymentId, String resourceName, String src);
	//查看坐标
	Map<String,Object> findCoordinateByTaskid(String taskId);
	//查看未完成的任务
	List<Task> findUncompleteTasks(String name);
	//根据名字查看已完成的任务
	List<HistoricTaskInstance> findCompleteTasks(String name);
	//根据实例ID查询已完成的任务
	List<HistoricActivityInstance> queryHistoricInstanceById(String processInstanceId);
	//添加评论
	boolean addComment(String taskId,String msg);
	//获取评论信息，传递当前的任务ID，获取历史任务ID对应的批注
	List<Comment> findCommentByTaskId(String taskId);
	//设置角色
	void setRole(String GroupName,String roleId);
}
