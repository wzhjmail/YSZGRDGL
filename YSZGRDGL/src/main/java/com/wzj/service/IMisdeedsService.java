package com.wzj.service;

import java.util.List;

import com.wzj.pojo.Misdeeds;

public interface IMisdeedsService {
	List<Misdeeds> getMisByCompanyId(int companyId);
}
