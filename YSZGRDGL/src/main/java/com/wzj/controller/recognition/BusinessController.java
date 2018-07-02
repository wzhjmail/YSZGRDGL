package com.wzj.controller.recognition;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.Log;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.util.AddLog;

@Controller("recognition/business")
@RequestMapping("recognition/business")
public class BusinessController {
	@Autowired
	private ApplicationFormService applicationFormService;
	@RequestMapping("businessCheck")
	public String businessCheck(){
		AddLog.addLog(Log.QUERY,"查询所有进行中的复认业务申请信息");
		return "recognition/schedule/schedule";
	}
	@RequestMapping("view")
	public String view(Model model,HttpServletRequest request) {
		String id=request.getParameter("id");
		ApplicationForm af=applicationFormService.getAppFormById(Integer.parseInt(id));
		AddLog.addLog(Log.QUERY,"查询'"+af.getEnterprisename()+"'的复认业务申请进度");
		model.addAttribute("id", Integer.parseInt(id));
		return "recognition/schedule/buSchedule";
	}
	
}
