package com.wzj.util;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hfga
 */
@Component
public class TaskFlowControlService {
    
	@Autowired
    private RuntimeService runtimeService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private RepositoryService repositoryService;

    //流程实力ID
   // private String _processId;

//    public TaskFlowControlService(String processId)
//    {
//        this._processId = processId;
//    }
    public TaskFlowControlService(){
    	
    }

    /**
     *  跳转至指定活动节点
     * @param targetTaskDefinitionKey
     */
//    public void jump(String targetTaskDefinitionKey){
//        TaskEntity currentTask = (TaskEntity)taskService
//                .createTaskQuery()
//                .processInstanceId(_processId).singleResult();
//        jump(currentTask,targetTaskDefinitionKey);
//    }

    /** 回退到某个节点的时候  流程变量也就相应的回应了
     * @param currentTaskEntity 当前任务节点
     * @param targetTaskDefinitionKey  目标任务节点（在模型定义里面的节点ID）
     */
    public  void jump(final TaskEntity currentTaskEntity, String targetTaskDefinitionKey){
        final ActivityImpl activity = getActivity(repositoryService,
                currentTaskEntity.getProcessDefinitionId(),targetTaskDefinitionKey);
        final ExecutionEntity execution = (ExecutionEntity)runtimeService
                .createExecutionQuery().executionId(currentTaskEntity.getExecutionId()).singleResult();
        ((RuntimeServiceImpl)runtimeService).getCommandExecutor()
                .execute(new Command<java.lang.Void>() {
                    public Void execute(CommandContext commandContext) {
                    	//创建新任务
                        execution.setActivity(activity);
                        execution.executeActivity(activity);
                        //删除当前的任务
                        //不能删除当前正在执行的任务，所以要先清除掉关联
                        currentTaskEntity.setExecutionId(null);
                        taskService.saveTask(currentTaskEntity);
                        taskService.deleteTask(currentTaskEntity.getId(),true);
                        return null;
                    }
                });
    }
    public  void jump(final TaskEntity currentTaskEntity, String targetTaskDefinitionKey,Map<String, Object> map){
        final ActivityImpl activity = getActivity(repositoryService,
                currentTaskEntity.getProcessDefinitionId(),targetTaskDefinitionKey,map);
        final ExecutionEntity execution = (ExecutionEntity)runtimeService
                .createExecutionQuery().executionId(currentTaskEntity.getExecutionId()).singleResult();
        ((RuntimeServiceImpl)runtimeService).getCommandExecutor()
                .execute(new Command<java.lang.Void>() {
                    public Void execute(CommandContext commandContext) {
                    	//创建新任务
                        execution.setActivity(activity);
                        //activity.setProperties(map);
                        //activity.setVariables(map);
                        execution.executeActivity(activity);
                        //删除当前的任务
                        //不能删除当前正在执行的任务，所以要先清除掉关联
                        currentTaskEntity.setExecutionId(null);
                        //currentTaskEntity.setExecutionVariables(map);
                        //currentTaskEntity.setAssignee("111");
                        //currentTaskEntity.addCandidateUser("23");报错
                        //currentTaskEntity.createVariablesLocal(map);
                        //currentTaskEntity.insert(execution);
                        taskService.saveTask(currentTaskEntity);
                        taskService.deleteTask(currentTaskEntity.getId(),true);
                        return null;
                    }
                });
    }
    private ActivityImpl getActivity(RepositoryService repositoryService, String processDefId, String activityId,Map<String, Object> map)
    {
        ProcessDefinitionEntity pde = getProcessDefinition(repositoryService, processDefId);
        ActivityImpl asl= pde.findActivity(activityId);
        asl.setVariables(map);
        return asl;
    }

    private ActivityImpl getActivity(RepositoryService repositoryService, String processDefId, String activityId)
    {
        ProcessDefinitionEntity pde = getProcessDefinition(repositoryService, processDefId);
        ActivityImpl asl= pde.findActivity(activityId);
        return asl;
    }
    private ProcessDefinitionEntity getProcessDefinition(RepositoryService repositoryService, String processDefId)
    {
        ProcessDefinitionEntity pDefinitionEntity= (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService)
                .getDeployedProcessDefinition(processDefId);
        return pDefinitionEntity;
    }
}