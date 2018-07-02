package com.wzj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.PbtReportMapper;
import com.wzj.pojo.PbtReport;

@Service("pbtReportService")
public class PbtReportService {
	@Autowired
	PbtReportMapper mapper;
	
	public List<PbtReport> getReports(){
		return mapper.getReports();
	}

	public int chageStatus(String f_report_key) {
		return mapper.chageStatus(f_report_key);
	}
	
	
}
