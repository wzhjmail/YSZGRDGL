package com.wzj.service.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.CertificateMapper;
import com.wzj.pojo.Certificate;
import com.wzj.service.application.ICertificateService;

@Service("certificateService")
public class CertificateService implements ICertificateService {
	@Autowired
	CertificateMapper certificateMapper;
	
	@Override
	public int getCertNo(){
		return certificateMapper.getCertNo();
	}

	@Override
	public List<Certificate> getCerts() {
		return certificateMapper.findAll();
	}
	
	public List<Certificate> getCertsBySearch(String search) {
		search="%"+search+"%";
		return certificateMapper.getCertsBySearch(search);
	}
	
	public Certificate checkCert(int serial) {
		return certificateMapper.checkCert(serial);
	}
	public int getFirstCertNo() {
		return certificateMapper.getFirstCertNo();
	}
	@Override
	public void setCertStart(Certificate cf) {
		certificateMapper.setCertStart(cf);
	}

	public String getNameByCertNo(int num) {
		return certificateMapper.getNameByCertNo(num);
	}

	public int getMaxCertNo() {
		return certificateMapper.getMaxCertNo();
	}

	public List<Certificate> getAllName(List<Certificate> pageInfoList) {
		return certificateMapper.getAllName(pageInfoList);
	}
}
