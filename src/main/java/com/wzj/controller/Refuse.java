package com.wzj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Refuse {
	@RequestMapping("/refuse")
	public String refuse() throws Exception{
		return "refuse";
	}
}
