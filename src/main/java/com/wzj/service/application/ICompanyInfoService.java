package com.wzj.service.application;

import java.util.List;

import com.wzj.pojo.CompanyInfo;

public interface ICompanyInfoService {

	//����ys_title�е�id����˾id���Ƶ�ys_company����
	public int insertById(int titleId);
	public int insert(CompanyInfo com);
	//����״̬�ͷ�֧������ѯ
	public List<CompanyInfo> getComByStatus(int status, String branchId);
	
	//���ݵ�ǰʱ���ж�֤���Ƿ���ڣ����޸Ĺ��ڵ�״ֵ̬
	public int certificateExpired();
}
