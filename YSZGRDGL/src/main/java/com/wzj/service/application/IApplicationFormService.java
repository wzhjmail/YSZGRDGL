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
	//根据businessIds批量查询记录
	public List<ApplicationForm> findBusinessByTasks(List<String> businessIds,String branchId);
	//根据状态获取业务信息
	public List<ApplicationForm> getAppByStatus(int status,String branchId);
	//更新业务状态
	public int updateStatus(int status, int id);
	//修改印刷资格证书的开始日期和到期日期
	public int updateDate(int id,Date date1,Date date2);
	//根据id注销
	public int cancel(int id);
	//根据id激活
	public int activate(int id);
}
