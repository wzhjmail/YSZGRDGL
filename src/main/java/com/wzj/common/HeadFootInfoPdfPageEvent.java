package com.wzj.common;

import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeadFootInfoPdfPageEvent extends PdfPageEventHelper{
	private String titleno;
	
	public String getTitleno() {
		return titleno;
	}
	public void setTitleno(String titleno) {
		this.titleno = titleno;
	}
	public HeadFootInfoPdfPageEvent(){  
    } 
	
    public void onEndPage(PdfWriter writer, Document document) { 
        try{  
            PdfContentByte headAndFootPdfContent = writer.getDirectContent();  
            headAndFootPdfContent.saveState();  
            headAndFootPdfContent.beginText();  
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
            headAndFootPdfContent.setFontAndSize(bfChinese, 10);  
            //文档页头信息设置  
            float x = document.top(-20);  
            //页头信息左面  
//            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT,"数据管理",document.left(), x, 0);  
            //页头信息中间  
//            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER, "会员导入报表",(document.right() + document.left())/2,x, 0);  
            //页头信息右面  
            //除第一页有流水号
            if(writer.getPageNumber()!=1){
            	headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_RIGHT,  
                		"编号"+this.getTitleno(),document.right(), x, 0);  
            }
            //文档页脚信息设置  
            float y = document.bottom(-20);  
            //页脚信息左面  
//            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT,  
//                               "--",  
//                               document.left(), y, 0);  
            //页脚信息中间  
//            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER,  
//                                "-",  
//                               (document.right() + document.left())/2,  
//                               y, 0);  
            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER,  
                    "第"+writer.getPageNumber()+ "页",
                    (document.right() + document.left())/2, y, 0);  
            //页脚信息右面  
//            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_RIGHT,  
//                               "--",  
//                               document.right(), y, 0);  
            headAndFootPdfContent.endText();  
            headAndFootPdfContent.restoreState();  
        }catch(DocumentException de) {  
            de.printStackTrace();  
            System.err.println("pdf watermark font: " + de.getMessage());  
        }catch(IOException de) {  
            de.printStackTrace();  
            System.err.println("pdf watermark font: " + de.getMessage());  
        }  
    }  
}  

