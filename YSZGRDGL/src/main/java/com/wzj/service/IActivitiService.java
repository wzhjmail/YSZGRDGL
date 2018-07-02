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
	//��ѯ���������Ϣ����Ӧ��act_re_deployment
	List<Deployment> findDeploymentList();
	//��ѯ���̶������Ϣ����Ӧ��act_re_procdef��
	List<ProcessDefinition> findProcessDefinitionList();
	//��������ʵ��
	String deployment(String src,String name);//���ز���Id
	//ͨ��Id��������ʵ��
	String startProcessById(String processId,Map<String,Object> map);//��������ʵ��Id
	//ͨ��������������ʵ��
	String startProcessByName(String processName,Map<String,Object> map);
	//��ѯ��������
	List<Task> findPersonalTask(String assignee);
	//��ѯ������
	List<Task> findGroupTask(String candidateUser);
	//��ɸ�������
	boolean completeTaskById(String taskId,Map<String,Object> map);
	//��ȡͼƬ
	//void getImageById(String deploymentId, String resourceName, String src);
	//�鿴����
	Map<String,Object> findCoordinateByTaskid(String taskId);
	//�鿴δ��ɵ�����
	List<Task> findUncompleteTasks(String name);
	//�������ֲ鿴����ɵ�����
	List<HistoricTaskInstance> findCompleteTasks(String name);
	//����ʵ��ID��ѯ����ɵ�����
	List<HistoricActivityInstance> queryHistoricInstanceById(String processInstanceId);
	//�������
	boolean addComment(String taskId,String msg);
	//��ȡ������Ϣ�����ݵ�ǰ������ID����ȡ��ʷ����ID��Ӧ����ע
	List<Comment> findCommentByTaskId(String taskId);
	//���ý�ɫ
	void setRole(String GroupName,String roleId);
}
