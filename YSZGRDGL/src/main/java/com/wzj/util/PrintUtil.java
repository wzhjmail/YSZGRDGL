package com.wzj.util;

import java.io.File;
import java.io.FileInputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;

import org.springframework.stereotype.Component;
@Component
public class PrintUtil {
	public static void printFile(String filePath){
		File file = new File(filePath); // 获取文件
	    // 构建打印请求属性集
	    HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
	    // 设置打印格式，因为未确定类型，所以选择autosense
	    DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
	    // 定位默认的打印服务
	    PrintService defaultService = PrintServiceLookup
	            .lookupDefaultPrintService();
	    if (defaultService != null) {
	        try {
	            DocPrintJob job = defaultService.createPrintJob(); // 创建打印作业
	            FileInputStream fis = new FileInputStream(file); // 构造待打印的文件流
	            DocAttributeSet das = new HashDocAttributeSet();
	            Doc doc = new SimpleDoc(fis, flavor, das);
	            job.print(doc, pras);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
}
