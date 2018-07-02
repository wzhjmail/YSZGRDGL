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

    //����ʵ��ID
   // private String _processId;

//    public TaskFlowControlService(String processId)
//    {
//        this._processId = processId;
//    }
    public TaskFlowControlService(){
    	
    }

    /**
     *  ��ת��ָ����ڵ�
     * @param targetTaskDefinitionKey
     */
//    public void jump(String targetTaskDefinitionKey){
//        TaskEntity currentTask = (TaskEntity)taskService
//                .createTaskQuery()
//                .processInstanceId(_processId).singleResult();
//        jump(currentTask,targetTaskDefinitionKey);
//    }

    /** ���˵�ĳ���ڵ��ʱ��  ���̱���Ҳ����Ӧ�Ļ�Ӧ��
     * @param currentTaskEntity ��ǰ����ڵ�
     * @param targetTaskDefinitionKey  Ŀ������ڵ㣨��ģ�Ͷ�������Ľڵ�ID��
     */
    public  void jump(final TaskEntity currentTaskEntity, String targetTaskDefinitionKey){
        final ActivityImpl activity = getActivity(repositoryService,
                currentTaskEntity.getProcessDefinitionId(),targetTaskDefinitionKey);
        final ExecutionEntity execution = (ExecutionEntity)runtimeService
                .createExecutionQuery().executionId(currentTaskEntity.getExecutionId()).singleResult();
        ((RuntimeServiceImpl)runtimeService).getCommandExecutor()
                .execute(new Command<java.lang.Void>() {
                    public Void execute(CommandContext commandContext) {
                    	//����������
                        execution.setActivity(activity);
                        execution.executeActivity(activity);
                        //ɾ����ǰ������
                        //����ɾ����ǰ����ִ�е���������Ҫ�����������
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
                    	//����������
                        execution.setActivity(activity);
                        //activity.setProperties(map);
                        //activity.setVariables(map);
                        execution.executeActivity(activity);
                        //ɾ����ǰ������
                        //����ɾ����ǰ����ִ�е���������Ҫ�����������
                        currentTaskEntity.setExecutionId(null);
                        //currentTaskEntity.setExecutionVariables(map);
                        //currentTaskEntity.setAssignee("111");
                        //currentTaskEntity.addCandidateUser("23");����
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