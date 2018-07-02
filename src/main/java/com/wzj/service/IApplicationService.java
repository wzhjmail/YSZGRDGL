package com.wzj.service;

import com.wzj.pojo.Application;
import com.wzj.pojo.ApplicationForm;

public interface IApplicationService {
	int saveApplication(Application app);
	Application findApplicationById(String id);
	public int updateState(Application app);
}
