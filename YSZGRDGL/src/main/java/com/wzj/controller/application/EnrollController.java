package com.wzj.controller.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzj.pojo.Log;
import com.wzj.util.AddLog;

@Controller("application/enroll")
@RequestMapping("application/enroll")
public class EnrollController {

	@RequestMapping("toGroupTask")
	public String toGroupTask(){
		return "application/enroll/groupTask";
	}
	
	@RequestMapping("toPersonalTask")
	public String toPersonalTask(){
		AddLog.addLog(Log.QUERY,"查询所有等待录取样品的新申请信息");
		return "application/enroll/personalTask";
	}
}
