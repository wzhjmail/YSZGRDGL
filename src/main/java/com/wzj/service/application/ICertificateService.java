package com.wzj.service.application;

import java.util.List;

import com.wzj.pojo.Certificate;

public interface ICertificateService {
	//调用存储过程。在ys_cert中插入一条数据，并返回该数据的CertNo
	public int getCertNo();
	//设置证书编号CertNo的初始值
	public void setCertStart(Certificate cf);
	//查询所有的CertNo
	public List<Certificate> getCerts();
}
