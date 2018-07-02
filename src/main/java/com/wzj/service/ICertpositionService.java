package com.wzj.service;

import java.util.List;

import com.wzj.pojo.Certposition;

public interface ICertpositionService {
	List<Certposition> getAll();
	Certposition getById(int id);
	int update(Certposition cp);
	int setUsed(int id);//设置正在使用的
	int insert(Certposition cert);
	int delete(int id);
}
