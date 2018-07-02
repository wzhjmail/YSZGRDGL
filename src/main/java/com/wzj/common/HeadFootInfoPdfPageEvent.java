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
            //�ĵ�ҳͷ��Ϣ����  
            float x = document.top(-20);  
            //ҳͷ��Ϣ����  
//            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT,"���ݹ���",document.left(), x, 0);  
            //ҳͷ��Ϣ�м�  
//            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER, "��Ա���뱨��",(document.right() + document.left())/2,x, 0);  
            //ҳͷ��Ϣ����  
            //����һҳ����ˮ��
            if(writer.getPageNumber()!=1){
            	headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_RIGHT,  
                		"���"+this.getTitleno(),document.right(), x, 0);  
            }
            //�ĵ�ҳ����Ϣ����  
            float y = document.bottom(-20);  
            //ҳ����Ϣ����  
//            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_LEFT,  
//                               "--",  
//                               document.left(), y, 0);  
            //ҳ����Ϣ�м�  
//            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER,  
//                                "-",  
//                               (document.right() + document.left())/2,  
//                               y, 0);  
            headAndFootPdfContent.showTextAligned(PdfContentByte.ALIGN_CENTER,  
                    "��"+writer.getPageNumber()+ "ҳ",
                    (document.right() + document.left())/2, y, 0);  
            //ҳ����Ϣ����  
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

