package com.wzj.controller.alternation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzj.pojo.FormChange;
import com.wzj.pojo.Log;
import com.wzj.service.alternation.impl.FormChangeService;
import com.wzj.util.AddLog;

@Controller("alternation/business")
@RequestMapping("alternation/business")
public class BusinessController {
	@Autowired
	private FormChangeService formChangeService;
	
	@RequestMapping("businessCheck")
	public String businessCheck(){
		AddLog.addLog(Log.QUERY,"查询所有进行中的变更业务申请信息");
		return "alternation/schedule/schedule";
	}
	
	@RequestMapping("view")
	public String view(Model model,HttpServletRequest request) {
		String id=request.getParameter("id");
		int changeId=Integer.parseInt(id);
		FormChange fc=formChangeService.getChangeFormById(changeId); 
		AddLog.addLog(Log.QUERY,"查看'"+fc.getCompanynameOld()+"'的变更申请业务进度");
		model.addAttribute("id", Integer.parseInt(id));
		return "alternation/schedule/buSchedule";
	}
}
