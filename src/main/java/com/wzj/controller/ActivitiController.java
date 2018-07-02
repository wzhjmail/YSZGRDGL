package com.wzj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.pojo.Application;
import com.wzj.pojo.SysUser;
import com.wzj.pojo.WorkflowBean;
import com.wzj.service.impl.ActivitiService;
import com.wzj.service.impl.ApplicationService;
import com.wzj.service.impl.SysRoleService;
import com.wzj.util.ProcessCoreServiceImpl;
import com.wzj.util.TaskFlowControlService;
import com.wzj.util.WorkflowUtil;

import net.sf.json.JSONObject;

@Controller
public class ActivitiController {
	@Autowired
	private WorkflowUtil workflowUtil;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskFlowControlService ts;
	
	//部署流程实例
	@RequestMapping(value="/deployment",produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String deployment(){
		workflowUtil.deployment("activiti/application", "部署新办流程");
		workflowUtil.deployment("activiti/recognition", "部署复认流程");
		workflowUtil.deployment("activiti/changeBusiness", "部署变更流程");
		return "部署成功";
	}

	@RequestMapping("/toImg")
	public String toImg(){
		return "image";
	}
	@RequestMapping("/getDeps")
	@ResponseBody
	public Object getDeps(){
		List<Deployment> deps = workflowUtil.findDeploymentList();
		JSONObject json=new JSONObject();
		for(Deployment dep:deps){
			json.put(dep.getId(), dep.getName());
			System.out.println("time=="+dep.getDeploymentTime());
		}
		return json;
	}
	@RequestMapping("/delDep")
	public String delDep(String id){
		workflowUtil.deleteProcessDefinitionByDeploymentId(id);
		return "image";
	}
	
	//拾取任务，将组任务变为个人任务
	@RequestMapping("climTask")
	@ResponseBody
	public String climTask(String taskId,String userId){
		taskService.claim(taskId, userId);;
		return "yes";
	}
	//???重新创建表部署，查看业务信息
	//启动流程实例
	@RequestMapping("/start")
	@ResponseBody
	public void  startProcessEngine(String name){
		Map<String,Object> map = new HashMap<>();
		map.put("user", name);
		String objId = "Application:2017081102";
		runtimeService.startProcessInstanceByKey("zzrd2",objId,map);
	}
	
	@RequestMapping("/findPersonalTaskId")
	@ResponseBody
	public Object findPersonalTaskId(String name){
		List<Task> tasks = workflowUtil.findTaskListByName(name);
		JSONObject jsonObj = new JSONObject();
		if(tasks !=null && tasks.size()>0){
			System.out.println("task id = "+tasks.get(0).getId());
			jsonObj.put("result", tasks.get(0).getId());
			//查看业务信息
			String appId = workflowUtil.findBussinessIdByTaskId(tasks.get(0).getId());
		}else{
			System.out.println("没有相关信息");
			jsonObj.put("result", "无");
		}
		String sm="";
		List<Comment> comments = workflowUtil.findCommentByTaskId(tasks.get(0).getId());
		if(comments.size()>0)
			sm=comments.get(0).getFullMessage();
		System.out.println("评审意见："+sm);
		return jsonObj;
	}
	
	@RequestMapping("findGroupTaskId")
	@ResponseBody
	public Object findGroupTaskId(String name){
		List<Task> tasks = workflowUtil.findGroupTaskListByName(name);
		JSONObject jsonObj = new JSONObject();
		if(tasks !=null && tasks.size()>0){
			System.out.println("task id = "+tasks.get(0).getId());
			jsonObj.put("result", tasks.get(0).getId());
			List<Comment> comms =  workflowUtil.findCommentByTaskId(tasks.get(0).getId());
			String comment = "";
			if(comms.size()>0&&comms!=null){
				comment=comms.get(0).getFullMessage();
			}else{
				comment="空";
			}
			jsonObj.put("comment", comment);
			//查看业务信息
			String appId = workflowUtil.findBussinessIdByTaskId(tasks.get(0).getId());
		}else{
			System.out.println("没有相关信息");
			jsonObj.put("result", "无");
		}
		return jsonObj;
	}
	
	@RequestMapping("/completePersonlTask")
	@ResponseBody
	public void completePersonlTask(String taskId,String role,String money,String comment){
		//添加评论
		workflowUtil.addComment(taskId,comment);
		//查看业务信息
		String appId = workflowUtil.findBussinessIdByTaskId(taskId);
		//获取业务信息
		Application app = applicationService.findApplicationById(appId);
		//办理业务
		Map<String,Object> map = new HashMap<>();
		if(StringUtils.isNotBlank(role)){
			List<SysUser> users = sysRoleService.findUsersByRoleName(role);
			String names="";
			for(SysUser user:users){
				names+=user.getUsername()+",";
			}
			names=names.substring(0,names.length()-1);
			map.put("admin",names);
			map.put("manager",names);
		}
		map.put("money", money);
		WorkflowBean workflowBean = new WorkflowBean();
		workflowBean.setTaskId(taskId);
		workflowUtil.completeTask(workflowBean, map);
		app.setState(app.getState()+1);;//改变业务状态
		applicationService.updateState(app);
	}
	
	@RequestMapping("/showProcess")
	@ResponseBody
	public Object showProcess(String taskId){
		Map<String,Object> map = activitiService.findCoordinateByTaskid(taskId);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("x",map.get("x"));
		jsonObj.put("y",map.get("y"));
		Task task = workflowUtil.getTaskById(taskId);
		System.out.println(">>>>>>>>"+task.getName());
		return map;
	}
	//根据节点名称获取task
	@RequestMapping("/showTasks")
	@ResponseBody
	public Object showTasks(String pointName){
		List<Task> tasks=workflowUtil.getTaskByPointName(pointName);
		List<Task> tasks2 = taskService.createTaskQuery()
				.processDefinitionKey("zzrd2")
				.taskDefinitionKey("usertask2")
				.orderByTaskCreateTime().asc().list();
		for(Task task:tasks){
			System.out.println(task.getId());
		}
		for(Task task:tasks2){
			System.out.println("-------------"+task.getId());
		}
		return tasks;
	}
	//完成任务
	@RequestMapping("comp")
	@ResponseBody
	public void comp(String taskId,String com){
		//添加评论
		workflowUtil.addComment(taskId,com);
		taskService.complete(taskId);
	}
	
	@RequestMapping("back")
	@ResponseBody
	public String back(String taskId){
		TaskEntity taskEntity=(TaskEntity)taskService
				.createTaskQuery().taskId(taskId).singleResult();
		taskEntity.getProcessInstanceId();
		Map<String, Object> map=new HashMap<>();
		map.put("manager", "中,心,人,员");
		ts.jump(taskEntity, "usertask1",map);
		List<Task> tasks=taskService.createTaskQuery().taskName("待确认")
				.orderByTaskCreateTime().asc().list();
		for(Task task:tasks){
			//task.setTenantId("a,b,c");
			//task.setAssignee("中心");
			//taskService.saveTask(task);
		}
		//save
		return "ok!";
	}
	@Autowired
	private ProcessCoreServiceImpl pcsi;
	
	@RequestMapping("beforeStep")
	@ResponseBody
	public String beforeStep(String taskId){
		pcsi.backProcess(taskId);
		return "111";
	}
}
