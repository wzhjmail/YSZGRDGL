package com.wzj.util;


import java.util.PropertyResourceBundle;
import org.springframework.stereotype.Controller;

@Controller
public class PropertyUtil {

	public static String getT(){
		// properties �����ļ�����
		PropertyResourceBundle res = (PropertyResourceBundle) PropertyResourceBundle.getBundle("suggestion");
		// ��ȡproperties�����ļ��е�����ֵ
		String ftpurl = res.getString("suggest2");
		System.out.println(ftpurl);
		return ftpurl;
	}
	public static void main(String[] args) {
		PropertyUtil.getT();
	}
}
