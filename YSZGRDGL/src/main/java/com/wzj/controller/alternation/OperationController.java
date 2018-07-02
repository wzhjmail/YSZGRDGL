package com.wzj.controller.alternation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.pojo.PbtSampleItem;
import com.wzj.pojo.PbtSample;
import com.wzj.pojo.PbtOperation;
import com.wzj.service.application.impl.OperationService;

@Controller
@RequestMapping("operation")
public class OperationController {
	@Autowired
	private OperationService operationService;
	
	@RequestMapping("insertOperation")
	public int insertOperation(PbtOperation pbt){
		return operationService.insertOperation(pbt);
	}
	
	@RequestMapping("insert")
	@ResponseBody
	public int insert(){
		PbtOperation pbt=new PbtOperation();
		pbt.setF_operation_key("123");
		pbt.setF_operation_code("456");
		return operationService.insertOperation(pbt);
	}
	
	@RequestMapping("insertSample")
	@ResponseBody
	public int insertSample(){
		PbtSample pbt=new PbtSample();
		pbt.setF_operation_key("123");
		pbt.setF_operation_code("456");
		return operationService.insertSample(pbt);
	}
	
	@RequestMapping("insertSampleItem")
	@ResponseBody
	public int insertSampleItem(){
		PbtSampleItem pbt=new PbtSampleItem();
		pbt.setF_operation_key("123");
		pbt.setF_operation_code("456");
		return operationService.insertSampleItem(pbt);
	}
}
