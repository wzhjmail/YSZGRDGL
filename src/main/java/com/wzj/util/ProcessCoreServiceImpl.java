package com.wzj.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessCoreServiceImpl {
	 @Autowired
	    private RuntimeService runtimeService;
	    
	    @Autowired
	    private TaskService taskService;
	    
	    @Autowired
	    private RepositoryService repositoryService;
	    
	    @Autowired
	    private HistoryService historyService;
	    
	 
	 
	    public void setRuntimeService(RuntimeService runtimeService) {
	        this.runtimeService = runtimeService;
	    }
	 
	    public void setTaskService(TaskService taskService) {
	        this.taskService = taskService;
	    }
	 
	    public void setRepositoryService(RepositoryService repositoryService) {
	        this.repositoryService = repositoryService;
	    }
	 
	    public void setHistoryService(HistoryService historyService) {
	        this.historyService = historyService;
	    }
	 
	    /**
	     * �˻ز���
	     * @param taskId
	     * @return
	     */
	    public String backProcess(String taskId) {
	        try {
	            Map<String, Object> variables;
	            // ȡ�õ�ǰ����
	            HistoricTaskInstance currTask = historyService
	                    .createHistoricTaskInstanceQuery().taskId(taskId)
	                    .singleResult();
	            // ȡ������ʵ��
	            ProcessInstance instance = runtimeService
	                    .createProcessInstanceQuery()
	                    .processInstanceId(currTask.getProcessInstanceId())
	                    .singleResult();
	            if (instance == null) {
	                return "error";
	            }
	            variables = instance.getProcessVariables();
	            // ȡ�����̶���
	            ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
	                    .getDeployedProcessDefinition(currTask
	                            .getProcessDefinitionId());
	            if (definition == null) {
	                return "error";
	            }
	            // ȡ����һ���
	            ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
	                    .findActivity(currTask.getTaskDefinitionKey());
	            List<PvmTransition> nextTransitionList = currActivity
	                    .getIncomingTransitions();
	            // �����ǰ��ĳ���
	            List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
	            List<PvmTransition> pvmTransitionList = currActivity
	                    .getOutgoingTransitions();
	            for (PvmTransition pvmTransition : pvmTransitionList) {
	                oriPvmTransitionList.add(pvmTransition);
	            }
	            pvmTransitionList.clear();
	 
	            // �����³���
	            List<TransitionImpl> newTransitions = new ArrayList<TransitionImpl>();
	            for (PvmTransition nextTransition : nextTransitionList) {
	                PvmActivity nextActivity = nextTransition.getSource();
	                ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition)
	                        .findActivity(nextActivity.getId());
	                TransitionImpl newTransition = currActivity
	                        .createOutgoingTransition();
	                newTransition.setDestination(nextActivityImpl);
	                newTransitions.add(newTransition);
	            }
	            // �������
	            List<Task> tasks = taskService.createTaskQuery()
	                    .processInstanceId(instance.getId())
	                    .taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
	            for (Task task : tasks) {
	                taskService.complete(task.getId(), variables);
	                historyService.deleteHistoricTaskInstance(task.getId());
	            }
	            // �ָ�����
	            for (TransitionImpl transitionImpl : newTransitions) {
	                currActivity.getOutgoingTransitions().remove(transitionImpl);
	            }
	            for (PvmTransition pvmTransition : oriPvmTransitionList) {
	                pvmTransitionList.add(pvmTransition);
	            }
	            return "success";
	        } catch (Exception e) {
	            return "error";
	        }
	    }
	    
	}
