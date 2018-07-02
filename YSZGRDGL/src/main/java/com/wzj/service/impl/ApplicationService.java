package com.wzj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.ApplicationMapper;
import com.wzj.pojo.Application;
import com.wzj.pojo.ApplicationForm;
import com.wzj.service.IApplicationService;

@Service("applicationService")
public class ApplicationService implements IApplicationService {
	@Autowired
	private ApplicationMapper applicationMapper;
	
	@Override
	public int saveApplication(Application app) {
		return applicationMapper.insert(app);
	}

	@Override
	public Application findApplicationById(String id) {
		return applicationMapper.findById(id);
	}

	@Override
	public int updateState(Application app) {
		return applicationMapper.updateState(app);
	}

}
