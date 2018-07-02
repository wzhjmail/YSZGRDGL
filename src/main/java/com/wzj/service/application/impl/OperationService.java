package com.wzj.service.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.service.IOperationService;

import com.wzj.dao.OperationMapper;
import com.wzj.pojo.PbtOperation;

import com.wzj.dao.PbtSampleMapper;
import com.wzj.pojo.PbtSample;

import com.wzj.dao.PbtSampleItemMapper;
import com.wzj.pojo.PbtSampleItem;

@Service("operationService")
public class OperationService implements IOperationService{
	@Autowired
	private OperationMapper operationMapper;
	@Autowired
	private PbtSampleMapper sampleMapper;
	@Autowired
	private PbtSampleItemMapper sampleItemMapper;
	
	public int insertOperation(PbtOperation pbt){
		return operationMapper.insertOperation(pbt);
	}
	
	@Override
	public int insertSample(PbtSample pbt){
		return sampleMapper.insert(pbt);
	}
	
	public int insertSampleItem(PbtSampleItem pbt){
		System.out.println(pbt.getF_operation_key());
		return sampleItemMapper.insert(pbt);
	}
	
	//获取返回评论中的总数
	public int count(String insId){
		return operationMapper.count(insId);
	}
}
