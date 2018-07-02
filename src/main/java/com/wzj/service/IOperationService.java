package com.wzj.service;

import com.wzj.pojo.PbtOperation;
import com.wzj.pojo.PbtSample;
import com.wzj.pojo.PbtSampleItem;

public interface IOperationService {
	public int insertOperation(PbtOperation pbt);
	
	public int insertSample(PbtSample pbt);
	//public int deleteSample(PbtSample pbt);
	//public int updateSample(PbtSample pbt);
	
	
	public int insertSampleItem(PbtSampleItem pbt);
	//public int deleteSampleItem(PbtSampleItem pbt);
	//public int updateSampleItem(PbtSampleItem pbt);
}
