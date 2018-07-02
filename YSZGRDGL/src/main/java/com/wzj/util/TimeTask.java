package com.wzj.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wzj.pojo.Number;
import com.wzj.service.impl.NumberService;

@Component("timeTask")
public class TimeTask {
	@Autowired
	NumberService numberService;
	
	public void run(){
		Number num = numberService.getNumber();
		num.setXbNum(1);
		num.setFrNum(1);
		num.setBgNum(1);
		numberService.setNumber(num);
	}
}
