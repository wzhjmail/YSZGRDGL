package com.wzj.service.application;

import java.util.List;

import com.wzj.pojo.Certificate;

public interface ICertificateService {
	//���ô洢���̡���ys_cert�в���һ�����ݣ������ظ����ݵ�CertNo
	public int getCertNo();
	//����֤����CertNo�ĳ�ʼֵ
	public void setCertStart(Certificate cf);
	//��ѯ���е�CertNo
	public List<Certificate> getCerts();
}
