package com.wzj.controller.application;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.Log;
import com.wzj.service.application.impl.ApplicationFormService;
import com.wzj.util.AddLog;


@Controller("application/business")
@RequestMapping("application/business")
public class BusinessController {
	
	@Autowired
	private ApplicationFormService applicationFormService;
	@RequestMapping("businessCheck")
	public String toIssuing(){
		AddLog.addLog(Log.QUERY,"��ѯ���н����е�������ҵ�����");
		return "application/schedule/schedule";
	}
	@RequestMapping("view")
	public String view(Model model,HttpServletRequest request) {
		String id=request.getParameter("id");
		ApplicationForm af=applicationFormService.getAppFormById(Integer.parseInt(id));
		AddLog.addLog(Log.QUERY,"��ѯ'"+af.getEnterprisename()+"'��������ҵ�����");
		model.addAttribute("id", Integer.parseInt(id));
		return "application/schedule/buSchedule";
	}
	
}
