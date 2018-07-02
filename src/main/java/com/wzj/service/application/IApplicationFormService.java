package com.wzj.service.application;

import java.util.Date;
import java.util.List;

import com.wzj.pojo.ApplicationForm;

public interface IApplicationFormService {
	public int insertApp(ApplicationForm app);
	public ApplicationForm getAppFormByBussinessNo(String bussinessNo);
	public List<ApplicationForm> getAll();
	public List<ApplicationForm> getByBranchId(String branchId);
	public int updateApp(ApplicationForm app);
	//����businessIds������ѯ��¼
	public List<ApplicationForm> findBusinessByTasks(List<String> businessIds,String branchId);
	//����״̬��ȡҵ����Ϣ
	public List<ApplicationForm> getAppByStatus(int status,String branchId);
	//����ҵ��״̬
	public int updateStatus(int status, int id);
	//�޸�ӡˢ�ʸ�֤��Ŀ�ʼ���ں͵�������
	public int updateDate(int id,Date date1,Date date2);
	//����idע��
	public int cancel(int id);
	//����id����
	public int activate(int id);
}
