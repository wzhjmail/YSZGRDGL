package com.wzj.util;


import java.util.PropertyResourceBundle;
import org.springframework.stereotype.Controller;

@Controller
public class PropertyUtil {

	public static String getT(){
		// properties 配置文件名称
		PropertyResourceBundle res = (PropertyResourceBundle) PropertyResourceBundle.getBundle("suggestion");
		// 获取properties配置文件中的属性值
		String ftpurl = res.getString("suggest2");
		System.out.println(ftpurl);
		return ftpurl;
	}
	public static void main(String[] args) {
		PropertyUtil.getT();
	}
}
