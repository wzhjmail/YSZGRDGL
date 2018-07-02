package com.wzj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.NumberMapper;
import com.wzj.pojo.Number;
import com.wzj.service.INumberService;

import scala.annotation.meta.getter;

@Service("numberService")
public class NumberService implements INumberService {
	
	@Autowired
	private NumberMapper numberMapper;
	
	@Override
	public Number getNumber(){
		return numberMapper.getNumber();
	}
	
	@Override
	public void setNumber(Number num){
		numberMapper.update(num);
	}
}
	