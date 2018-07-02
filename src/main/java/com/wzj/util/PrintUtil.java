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
		File file = new File(filePath); // ��ȡ�ļ�
	    // ������ӡ�������Լ�
	    HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
	    // ���ô�ӡ��ʽ����Ϊδȷ�����ͣ�����ѡ��autosense
	    DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
	    // ��λĬ�ϵĴ�ӡ����
	    PrintService defaultService = PrintServiceLookup
	            .lookupDefaultPrintService();
	    if (defaultService != null) {
	        try {
	            DocPrintJob job = defaultService.createPrintJob(); // ������ӡ��ҵ
	            FileInputStream fis = new FileInputStream(file); // �������ӡ���ļ���
	            DocAttributeSet das = new HashDocAttributeSet();
	            Doc doc = new SimpleDoc(fis, flavor, das);
	            job.print(doc, pras);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
}
