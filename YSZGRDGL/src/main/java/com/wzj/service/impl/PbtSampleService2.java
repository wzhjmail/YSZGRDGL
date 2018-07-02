package com.wzj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.PbtSampleMapper2;
import com.wzj.pojo.PbtSample2;

@Service("pbtSampleService2")
public class PbtSampleService2 {
	@Autowired
	private PbtSampleMapper2 mapper;
	
	public List<PbtSample2> getWait(){
		return mapper.getWait();
	}
	
	public int chageRead(String key){
		return mapper.chageRead(key);
	}
	
	public int insert(PbtSample2 sample){
		return mapper.insert(sample);
	}
}
