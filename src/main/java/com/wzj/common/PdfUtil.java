package com.wzj.common;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.wzj.dao.DivisionMapper;
import com.wzj.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.awt.DefaultFontMapper.BaseFontParameters;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.DocumentException;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfAppearance;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;

import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RadioCheckField;

import com.wzj.service.impl.CertpositionService;
import com.wzj.service.impl.ComUserService;
import com.wzj.service.impl.DivisionService;
import com.wzj.service.impl.LogService;
import com.wzj.service.impl.SysUserService;
import com.wzj.service.impl.EvaluationService;
import com.wzj.service.application.impl.ReviewFormPartService;
import com.wzj.util.PropertyUtil;
@Component
public class PdfUtil {
	@Autowired
	private CertpositionService certServices;
	@Autowired
	private SysUserService sysUserServices;
	@Autowired
	private ComUserService comUserServices;
	@Autowired
	private ReviewFormPartService reviewFormPartServices;
	@Autowired
	private DivisionService divisionServices;
	
	private static CertpositionService certService;
	private static SysUserService sysUserService;
	private static ComUserService comUserService;
	private static ReviewFormPartService reviewFormPartService;
	private static DivisionService divisionService;

	@PostConstruct  //@PostConstruct��@PreDestroy����ʵ�ֳ�ʼ��������bean֮ǰ���в���
    public void init() {  
		divisionService=divisionServices;
		certService=certServices;
		sysUserService=sysUserServices;
		comUserService=comUserServices;
		reviewFormPartService=reviewFormPartServices;
		
    } 
	
	public static final String[] YSJS = { "ƽ�彺ӡ", "����ӡˢ", "˿��ӡˢ", "���԰�ӡˢ"};
	public static final String[] YSZT = { "ֽ��", "���ɽ�", "����ֽ", "����","����"};
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	
	public Font setFont(int fontSize,boolean flag){
		
	    BaseFont baseFontChinese=null;
		try {
			baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",   
	                 BaseFont.NOT_EMBEDDED); 
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	     Font fontChinese =null;
	     if(flag==true){
	    	 fontChinese=new Font(baseFontChinese , fontSize , Font.BOLD, BaseColor.BLACK);
	     }
	     else{
	    	 fontChinese=new Font(baseFontChinese , fontSize , Font.NORMAL, BaseColor.BLACK);
	     }
	     return fontChinese;
	}
	
	public Font setFontUnderline(int fontSize,boolean flag){
		
	    BaseFont baseFontChinese=null;
		try {
			baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",   
	                 BaseFont.NOT_EMBEDDED); 
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	     Font fontChinese =null;
	     if(flag==true){
	    	 fontChinese=new Font(baseFontChinese , fontSize , Font.UNDERLINE, BaseColor.BLACK);
	     }
	     else{
	    	 fontChinese=new Font(baseFontChinese , fontSize , Font.NORMAL, BaseColor.BLACK);
	     }
	     return fontChinese;
	}
	
	public void createPDFPrototype(ApplicationForm app,String filePath){
		//���·��
		String fileDesc = "";
		// ���������	
		FileOutputStream outr = null;	
		fileDesc=filePath;
		//�����
		 int numD=20;
		 
		 //С����
		 int numX=14;
		 
		 //����
		 int numTD=14;
		 
		 //���С
		 int numTX=12;
		 
		 //�����
		 int numM=13;
		 
		 Document document=new Document();
		 
		 try{
		 outr= new FileOutputStream(fileDesc);
         PdfWriter writer = PdfWriter.getInstance(document, outr);
         document.open();
         //document.newPage();
         
         PdfContentByte canvas = writer.getDirectContent();
         Rectangle rect;
         PdfFormField field;
         //Font font = new Font(baseFontChinese , fontSize , Font.NORMAL, BaseColor.BLACK);
         
         
         Paragraph paragraph;
         
         PdfPCell cell;
         
         Paragraph par0=new Paragraph("\r\n",setFont(numX,true));
	     par0.setAlignment(Element.ALIGN_CENTER);
         
         paragraph =new Paragraph("��  ��  ��  Ʒ  ��  ��  ϵ  ͳ  ��  Ա",setFont(numD,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
	     document.add(par0);
         
	     paragraph=new Paragraph("ע �� �� �� ��",setFont(numX,false));
	     paragraph.setAlignment(Element.ALIGN_CENTER);
	     document.add(paragraph);
         
	     //����
         for(int i=0;i<16;i++)document.add(par0);
         
         //Լ������
         PdfPTable table0=new PdfPTable(1);;
	     //table.setWidth(210);
         PdfPCell cell11=new PdfPCell(new Phrase("Լ�����\n"+
	        		"1.����ʶ�������Ч��Ϊ���ꡣ��Ч�����������ʹ�ó���ʶ������Ӧ"
	        		+"�ڡ��й���Ʒ����ϵͳ��Ա֤�顷��Ч����ǰ����������,����ҵ����Ӫ"
	        		+"ҵִ�ջ�Ӫҵִ�ռ����й���Ʒ����ϵͳ��Ա֤�顷�����ڵر����֧"
	        		+"����������չ������\n"
	        		+"2.��ҵ�����С���Ʒ�������취�����йع涨����ɵ�һ����ʧ����ҵ"
	        		+"���ге��ر����֧����������չ����������δ������չ�����ģ�ע��"
	        		+"�䳧��ʶ����룻\n"
	        		+"3. Ϊ�˰���ϵͳ��Ա���õ�Ӧ����Ʒ���룬�ṩ��ز�Ʒ����������"
	        		+"��������ó����ͨ�����й���Ʒ�������Ķ�ϵͳ��Ա��Ϣ����ע��"
	        		+"�ʽ��⣩���������Ʒ����� EDI �������֯����ó��˾�����������"
	        		+"��˾�����˲�ϣ��������ҵ��Ϣ�������ճ��й���Ʒ�����������ⵥλ"
	        		+"���ż��������ʼ���绰������������л����̡� ��������������"
	        		+"���̡� ����ʾ��˾�����˶��������ݲ����ԡ�",setFont(numM,false)));
         cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
         cell11.setVerticalAlignment(Element.ALIGN_CENTER);
	        
         table0.addCell(cell11);
         document.add(table0);
	        
         paragraph=new Paragraph("�й���Ʒ����������",setFont(numX,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //�ڶ�ҳ
         document.newPage();
         
         //����
         for(int i=0;i<1;i++)document.add(par0);
         
         //һ����ҵ��Ϣ
         paragraph=new Paragraph("һ����ҵ��Ϣ",setFont(numX,true));
         	paragraph.setAlignment(Element.ALIGN_LEFT);
         	document.add(paragraph);
         	document.add(par0);
         	
         	PdfPTable table1 = new PdfPTable(12);
         	
         	table1.setTotalWidth((float)530.0);
            table1.setLockedWidth(true);
            //------------- ��ҵ����(����)
            cell = new PdfPCell(new Phrase("��ҵ����(����)",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ҵ����(����)ֵ
            cell = new PdfPCell(new Phrase(app.getEnterprisename(),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ҵ����(Ӣ��)
            cell = new PdfPCell(new Phrase("��ҵ����(Ӣ��)",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ҵ����(Ӣ��)ֵ
            cell = new PdfPCell(new Phrase(app.getEnglishname(),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ϸ��ַ
            cell = new PdfPCell(new Phrase("��ϸ��ַ",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ϸ��ֵַ
            cell = new PdfPCell(new Phrase(app.getAddress(),setFont(numTX,false)));
            	cell.setColspan(10);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ӡˢ��Ӫ���֤����
            cell = new PdfPCell(new Phrase("ӡˢ��Ӫ���֤����",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ӡˢ��Ӫ���֤����ֵ
            cell = new PdfPCell(new Phrase(app.getCertificateno(),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��������
            cell = new PdfPCell(new Phrase("��������",setFont(numTX,false)));
            	cell.setColspan(1);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��������ֵ
            cell = new PdfPCell(new Phrase(app.getPostalcode(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ����
            cell = new PdfPCell(new Phrase("����",setFont(numTX,false)));
            	cell.setColspan(1);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ����ֵ
            cell = new PdfPCell(new Phrase(app.getFax(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
          //------------- ���˴���
            cell = new PdfPCell(new Phrase("���˴���",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ���˴���ֵ
            cell = new PdfPCell(new Phrase(app.getArtificialperson(),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��
            cell = new PdfPCell(new Phrase("ְ��",setFont(numTX,false)));
            	cell.setColspan(1);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��ֵ
            cell = new PdfPCell(new Phrase(app.getApjob(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �绰
            cell = new PdfPCell(new Phrase("�绰",setFont(numTX,false)));
            	cell.setColspan(1);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �绰ֵ
            cell = new PdfPCell(new Phrase(app.getAptel(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ϵ��
            cell = new PdfPCell(new Phrase("��ϵ��",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ϵ��ֵ
            cell = new PdfPCell(new Phrase(app.getLinkman(),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��
            cell = new PdfPCell(new Phrase("ְ��",setFont(numTX,false)));
            	cell.setColspan(1);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��ֵ
            cell = new PdfPCell(new Phrase(app.getLjob(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �绰
            cell = new PdfPCell(new Phrase("�绰",setFont(numTX,false)));
            	cell.setColspan(1);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �绰ֵ
            cell = new PdfPCell(new Phrase(app.getLtel(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
         document.add(table1);   
         
         //����
         for(int i=0;i<3;i++)document.add(par0);
         
         //������Ӫ��Χ
         paragraph=new Paragraph("������Ӫ��Χ",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            
            PdfPTable table2 = new PdfPTable(12);
         	table2.setTotalWidth((float)530.0);
            table2.setLockedWidth(true);
            
            //------------- ��Ӫ
            cell = new PdfPCell(new Phrase("��Ӫ",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table2.addCell(cell);
            //------------- ��Ӫֵ
            cell = new PdfPCell(new Phrase(app.getMainbusiness(),setFont(numTX,false)));
            	cell.setColspan(10);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table2.addCell(cell);
            //------------- ��Ӫ
            cell = new PdfPCell(new Phrase("��Ӫ",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table2.addCell(cell);
            //------------- ��Ӫֵ
            cell = new PdfPCell(new Phrase(app.getRestbusiness(),setFont(numTX,false)));
            	cell.setColspan(10);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table2.addCell(cell);
         document.add(table2);
         
         //����
         for(int i=0;i<3;i++)document.add(par0);
         
         //������Ա���
         paragraph=new Paragraph("������Ա���",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            
            PdfPTable table3 = new PdfPTable(12);
         	table3.setTotalWidth((float)530.0);
            table3.setLockedWidth(true);
            
            //------------- ְ������
            cell = new PdfPCell(new Phrase("ְ������",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
            //------------- ְ������ֵ
            cell = new PdfPCell(new Phrase(app.getEmployeetotal() == null?" ":Integer.toString(app.getEmployeetotal()),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
            //------------- ������Ա
            cell = new PdfPCell(new Phrase("������Ա",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
                //------------- ������Աֵ
            cell = new PdfPCell(new Phrase(app.getTechniciantotal() == null?" ":Integer.toString(app.getTechniciantotal()),setFont(numTX,false)));
                cell.setColspan(4);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
            //------------- ����ӡˢ����������
            cell = new PdfPCell(new Phrase("����ӡˢ����������",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
                //------------- ����ӡˢ����������ֵ
            cell = new PdfPCell(new Phrase(app.getBcprincipal(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
            //------------- ְ��
            cell = new PdfPCell(new Phrase("ְ��",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
                //------------- ְ��ֵ
            cell = new PdfPCell(new Phrase(app.getTpbusiness(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
            //------------- ְ��
            cell = new PdfPCell(new Phrase("ְ��",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
                //------------- ְ��ֵ
            cell = new PdfPCell(new Phrase(app.getTpopost(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
         document.add(table3);
         
         document.newPage();
         
         //����
         for(int i=0;i<1;i++)document.add(par0);
         
         //�ġ�����ӡˢ��������
         paragraph=new Paragraph("�ġ�����ӡˢ��������",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            
            PdfPTable table4 = new PdfPTable(12);
         	table4.setTotalWidth((float)530.0);
            table4.setLockedWidth(true);
            
            //------------- ���ּ�������
            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
                cell.setColspan(12);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table4.addCell(cell);
            //------------- ������������
            cell = new PdfPCell(new Phrase("����(����)",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table4.addCell(cell);
                //------------- ��������ֵ
            cell = new PdfPCell(new Phrase(app.getElsetype(),setFont(numTX,false)));
                cell.setColspan(10);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table4.addCell(cell);
            
         document.add(table4);
            
            PdfAppearance[] onOff = new PdfAppearance[2];
            onOff[0] = canvas.createAppearance(10, 10);
            onOff[0].rectangle(0, 0, 10, 10);
            onOff[0].stroke();
            onOff[1] = canvas.createAppearance(10, 10);
            onOff[1].setRGBColorFill(255, 255, 255);
            onOff[1].rectangle(0, 0, 10, 10);
            onOff[1].fillStroke();
            onOff[1].moveTo(0, 0);
            onOff[1].lineTo(10, 10);
            onOff[1].stroke();
            RadioCheckField checkbox;
            
            boolean[] js = new boolean[4];
            js[0]=app.getFlat()==null?false:app.getFlat();
            js[1]=app.getGravure()==null?false:app.getGravure();
            js[2]=app.getWebby()==null?false:app.getWebby();
            js[3]=app.getFlexible()==null?false:app.getFlexible();
            
            for (int i = 0; i < YSJS.length; i++) {
                rect = new Rectangle(120 + i * 100, 725, 130  + i * 100, 715);
                checkbox = new RadioCheckField(writer, rect, YSJS[i], "On");
                
                if(js[i])checkbox.setChecked(true);  
                else checkbox.setChecked(false); 
                
                field = checkbox.getCheckField();
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", onOff[0]);
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "On", onOff[1]);
                writer.addAnnotation(field);
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                    new Phrase(YSJS[i], setFont(12,false)), 140 + i * 100, 716, 0);
            }

            
            
         //����
         for(int i=0;i<3;i++)document.add(par0);
               
         //�塢ӡˢ�������
         paragraph=new Paragraph("�塢ӡˢ�������",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            PdfPTable table5 = new PdfPTable(12);
         	table5.setTotalWidth((float)530.0);
            table5.setLockedWidth(true);
            
            //------------- ���ֲ���
            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
                cell.setColspan(12);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table5.addCell(cell);
            //------------- ��������
            cell = new PdfPCell(new Phrase("����(����)",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table5.addCell(cell);
            //------------- ��������ֵ
            cell = new PdfPCell(new Phrase(app.getElsematerial(),setFont(numTX,false)));
                cell.setColspan(10);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table5.addCell(cell);
            
         document.add(table5);
            
         	boolean[] zt = new boolean[5];
         	zt[0]=app.getPapery()==null?false:app.getPapery();
         	zt[1]=app.getPastern()==null?false:app.getPastern();
         	zt[2]=app.getFrilling()==null?false:app.getFrilling();
         	zt[3]=app.getMetal()==null?false:app.getMetal();
         	zt[4]=app.getPlastic()==null?false:app.getPlastic();
         	
         	
            for (int i = 0; i < YSZT.length; i++) {
                rect = new Rectangle(80 + i * 100, 520, 90  + i * 100, 510);
                checkbox = new RadioCheckField(writer, rect, YSZT[i], "Yes");
                
                if(zt[i])checkbox.setChecked(true);  
                else checkbox.setChecked(false);
                
                field = checkbox.getCheckField();
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", onOff[0]);
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Yes", onOff[1]);
                writer.addAnnotation(field);
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                    new Phrase(YSZT[i], setFont(12,false)), 100 + i * 100, 511, 0);
            } 
            
         //����
            for(int i=0;i<3;i++)document.add(par0);
            
         /*�ύ����
         paragraph=new Paragraph("�ύ����",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);*/
           
         //������Ҫӡˢ�豸
         paragraph=new Paragraph("������Ҫӡˢ�豸",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            
            PdfPTable table6 = new PdfPTable(12);
         	table6.setTotalWidth((float)530.0);
            table6.setLockedWidth(true);            
            //------------- ��Ҫӡˢ�豸ֵ
            cell = new PdfPCell(new Phrase(app.getPrintEquipment(),setFont(numTX,false)));
            	cell.setColspan(12);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(90f);
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table6.addCell(cell);
         document.add(table6);
         
         //����
         for(int i=0;i<3;i++)document.add(par0);
         
         //�ߡ��������豸
         paragraph=new Paragraph("�ߡ��������豸",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            PdfPTable table7 = new PdfPTable(12);
         	table7.setTotalWidth((float)530.0);
            table7.setLockedWidth(true);            
            //------------- �������豸ֵ
            cell = new PdfPCell(new Phrase(app.getTestEquipment(),setFont(numTX,false)));
            	cell.setColspan(12);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(90f);
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table7.addCell(cell);
         document.add(table7);
              
         document.newPage();
         //����
         for(int i=0;i<1;i++)document.add(par0);
         
         //�ˡ���ע
         paragraph=new Paragraph("�ˡ���ע",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            PdfPTable table8 = new PdfPTable(12);
         	table8.setTotalWidth((float)530.0);
            table8.setLockedWidth(true);            
            //------------- ��עֵ
            cell = new PdfPCell(new Phrase(app.getRemarks(),setFont(numTX,false)));
            	cell.setColspan(12);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(90f);
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table8.addCell(cell);
         document.add(table8);
         
         
         
         document.close();
	     outr.close();
		 }catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
		 }
	}
	
	public void createPDFApplication(ApplicationForm app, String filePath, List<PrintEquipment> printEquipments, List<TestingEquip> testingEquips){
		//���·��
		String fileDesc = "";
		// ���������	
		FileOutputStream outr = null;
		//fileDesc="D:/wmq/wtf.pdf";	
		fileDesc=filePath;
		//�����
		 int numD=20;
		 
		 //С����
		 int numX=14;
		 
		 //����
		 int numTD=14;
		 
		 //���С
		 int numTX=12;
		 
		 //�����
		 int numM=13;
		 
		 Document document=new Document();
		 
		 try{
		 outr= new FileOutputStream(fileDesc);
         PdfWriter writer = PdfWriter.getInstance(document, outr);
         document.open();
         //document.newPage();
         
         PdfContentByte canvas = writer.getDirectContent();
         Rectangle rect;
         PdfFormField field;
         //Font font = new Font(baseFontChinese , fontSize , Font.NORMAL, BaseColor.BLACK);
         
         Chunk chunk;
         
         Paragraph paragraph;
         
         PdfPCell cell;
         
         Paragraph par0=new Paragraph("\r\n",setFont(numX,true));
	     par0.setAlignment(Element.ALIGN_CENTER);
         
	     paragraph =new Paragraph("��� "+app.getTitleno(),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_RIGHT);
         document.add(paragraph);
	     
         //����
         for(int i=0;i<4;i++)document.add(par0);
         
         
         //��  Ʒ  ��  ��  ӡ  ˢ  ��  ��
         paragraph =new Paragraph("��  Ʒ  ��  ��  ӡ  ˢ  ��  ��",setFont(numD,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
	     document.add(par0);
         
	     //�� �� ��
	     paragraph=new Paragraph("�� �� ��",setFont(26,true));
	     paragraph.setAlignment(Element.ALIGN_CENTER);
	     document.add(paragraph);
         
	     //����
         for(int i=0;i<12;i++)document.add(par0);
         
         //������ҵ����
         String entName = String.format("%-20s", app.getEnterprisename());
         
         paragraph=new Paragraph("                                                     ������ҵ���ƣ�  ",setFont(numX,false));
	       
         chunk = new Chunk(entName,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
         chunk = new Chunk("(����)",setFontUnderline(numX,false));
         paragraph.add(chunk);
         
         paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	     
	     document.add(par0);
	     
	     //��   ��   ��   ��
	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");   
         String str = String.format("%-28s", sdf.format(app.getCreatedate()));
	     
	     //paragraph=new Paragraph("                                                      ��   ��   ��   �ڣ�  "+str,setFontUnderline(numX,true));
	     paragraph=new Paragraph("                                                      ��   ��   ��   �ڣ�  ",setFont(numX,false));
         
         chunk = new Chunk(str,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
	     paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	        
	     //����
         for(int i=0;i<10;i++)document.add(par0);
	     
         paragraph=new Paragraph("�й���Ʒ����������",setFont(numX,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //�ڶ�ҳ
         document.newPage();
         
         //����
         for(int i=0;i<1;i++)document.add(par0);
         
         	PdfPTable table1 = new PdfPTable(12);
         	table1.setTotalWidth((float)480.0);
            table1.setLockedWidth(true);
            //------------- ��ҵ����(����)
            cell = new PdfPCell(new Phrase("��ҵ����(����)",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ҵ����(����)ֵ
            cell = new PdfPCell(new Phrase(app.getEnterprisename(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ����
            cell = new PdfPCell(new Phrase("����",setFont(numTX,false)));
            	cell.setRowspan(2);
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ����ֵ
            cell = new PdfPCell(new Phrase(app.getFax(),setFont(numTX,false)));
            	cell.setRowspan(2);
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ҵ����(Ӣ��)
            cell = new PdfPCell(new Phrase("��ҵ����(Ӣ��)",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ҵ����(Ӣ��)ֵ
            cell = new PdfPCell(new Phrase(app.getEnglishname(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            //------------- ��ϸ��ַ
            cell = new PdfPCell(new Phrase("��ϸ��ַ",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ϸ��ֵַ
            cell = new PdfPCell(new Phrase(app.getAddress(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��������
            cell = new PdfPCell(new Phrase("��������",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��������ֵ
            cell = new PdfPCell(new Phrase(app.getPostalcode(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            /*
            //------------- ӡˢ��Ӫ���֤����
            cell = new PdfPCell(new Phrase("ӡˢ��Ӫ���֤����",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ӡˢ��Ӫ���֤����ֵ
            cell = new PdfPCell(new Phrase(app.getCertificateno(),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
			*/
            //------------- Ӫҵִ�պ���
            cell = new PdfPCell(new Phrase("Ӫҵִ�պ���",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- Ӫҵִ�պ���ֵ
            cell = new PdfPCell(new Phrase(app.getBusinessno(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ҵ����
            cell = new PdfPCell(new Phrase("��ҵ����",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ҵ����ֵ
            cell = new PdfPCell(new Phrase(app.getEnterprisekind(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ע���ʱ�
            cell = new PdfPCell(new Phrase("ע���ʱ�",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ע���ʱ�ֵ
            cell = new PdfPCell(new Phrase(app.getRegistercapital(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);          
            //------------- ���˴���
            cell = new PdfPCell(new Phrase("���˴���",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ���˴���ֵ
            cell = new PdfPCell(new Phrase(app.getArtificialperson(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��
            cell = new PdfPCell(new Phrase("ְ��",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��ֵ
            cell = new PdfPCell(new Phrase(app.getApjob(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �绰
            cell = new PdfPCell(new Phrase("�绰",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �绰ֵ
            cell = new PdfPCell(new Phrase(app.getAptel(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ϵ��
            cell = new PdfPCell(new Phrase("��ϵ��",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ϵ��ֵ
            cell = new PdfPCell(new Phrase(app.getLinkman(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��
            cell = new PdfPCell(new Phrase("ְ��",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��ֵ
            cell = new PdfPCell(new Phrase(app.getLjob(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �绰
            cell = new PdfPCell(new Phrase("�绰",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �绰ֵ
            cell = new PdfPCell(new Phrase(app.getLtel(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);  
            /*
            //------------- ��Ӫ��Χ
            cell = new PdfPCell(new Phrase("��Ӫ��Χ",setFont(numTX,false)));
         		cell.setRowspan(2);
         		cell.setColspan(1);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	*/
         	//------------- ��Ӫ
         	cell = new PdfPCell(new Phrase("��Ӫ",setFont(numTX,false)));
         		cell.setColspan(2);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	//------------- ��Ӫֵ
         	cell = new PdfPCell(new Phrase(app.getMainbusiness(),setFont(numTX,false)));
         		cell.setColspan(10);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	//------------- ��Ӫ
         	cell = new PdfPCell(new Phrase("��Ӫ",setFont(numTX,false)));
         		cell.setColspan(2);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	//------------- ��Ӫֵ
         	cell = new PdfPCell(new Phrase(app.getRestbusiness(),setFont(numTX,false)));
         		cell.setColspan(10);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
            /*
         	//------------- ��Ա���
            cell = new PdfPCell(new Phrase("��Ա���",setFont(numTX,false)));
         		cell.setRowspan(2);
         		cell.setColspan(1);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	*/
         	//------------- ְ������
            cell = new PdfPCell(new Phrase("ְ������",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ������ֵ
            cell = new PdfPCell(new Phrase(app.getEmployeetotal() == null?" ":Integer.toString(app.getEmployeetotal()),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ������Ա
            cell = new PdfPCell(new Phrase("������Ա",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- ������Աֵ
            cell = new PdfPCell(new Phrase(app.getTechniciantotal() == null?" ":Integer.toString(app.getTechniciantotal()),setFont(numTX,false)));
                cell.setColspan(6);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ����ӡˢ����������
            cell = new PdfPCell(new Phrase("����ӡˢ����������",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- ����ӡˢ����������ֵ
            cell = new PdfPCell(new Phrase(app.getBcprincipal(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��
            cell = new PdfPCell(new Phrase("ְ��",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- ְ��ֵ
            cell = new PdfPCell(new Phrase(app.getTpbusiness(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��
            cell = new PdfPCell(new Phrase("ְ��",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��ֵ
            cell = new PdfPCell(new Phrase(app.getTpopost(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ����ӡˢ��������
            cell = new PdfPCell(new Phrase("����ӡˢ\n��������",setFont(numTX,false)));
                cell.setRowspan(2);
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- ���ּ�������
            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
                cell.setColspan(10);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- ������������
            cell = new PdfPCell(new Phrase("����(����)",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- ��������ֵ
            cell = new PdfPCell(new Phrase(app.getElsetype(),setFont(numTX,false)));
                cell.setColspan(8);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �����������
            cell = new PdfPCell(new Phrase("�����������",setFont(numTX,false)));
                cell.setRowspan(2);
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- ������������
            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
                cell.setColspan(10);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- ������������
            cell = new PdfPCell(new Phrase("����(����)",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- ��������ֵ
            cell = new PdfPCell(new Phrase(app.getElsetype(),setFont(numTX,false)));
                cell.setColspan(8);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��Ҫӡˢ�豸
            cell = new PdfPCell(new Phrase("��Ҫӡˢ�豸",setFont(numTX,false)));
			 	cell.setRowspan(printEquipments.size()+1);
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- ��Ҫӡˢ�豸��ͷ��
			cell = new PdfPCell(new Phrase("����", setFont(numTX,false)));
				cell.setColspan(2);
				cell.setUseAscender(true);
				cell.setFixedHeight(25f);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell);
			cell = new PdfPCell(new Phrase("�ͺ�", setFont(numTX,false)));
				cell.setColspan(2);
			 	cell.setUseAscender(true);
			 	cell.setFixedHeight(25f);
			 	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell);
			cell = new PdfPCell(new Phrase("����", setFont(numTX,false)));
			 	cell.setColspan(2);
			 	cell.setUseAscender(true);
			 	cell.setFixedHeight(25f);
			 	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 table1.addCell(cell);
			 cell = new PdfPCell(new Phrase("����", setFont(numTX,false)));
			 	cell.setColspan(1);
			 	cell.setUseAscender(true);
			 	cell.setFixedHeight(25f);
			 	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 table1.addCell(cell);
			 cell = new PdfPCell(new Phrase("��ע", setFont(numTX,false)));
			 	cell.setColspan(3);
			 	cell.setUseAscender(true);
			 	cell.setFixedHeight(25f);
			 	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 table1.addCell(cell);
			 //------------- ��Ҫӡˢ�豸ֵ
			for(int i = 0 ; i < printEquipments.size() ; i++) {
				//����ֵ
				cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintName(),setFont(numTX,false)));
				cell.setColspan(2);
				cell.setUseAscender(true);
				cell.setFixedHeight(25f);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table1.addCell(cell);
				//�ͺ�ֵ
				cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintModel(),setFont(numTX,false)));
				cell.setColspan(2);
				cell.setUseAscender(true);
				cell.setFixedHeight(25f);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table1.addCell(cell);
				//����ֵ
				cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintPlace(),setFont(numTX,false)));
				cell.setColspan(2);
				cell.setUseAscender(true);
				cell.setFixedHeight(25f);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table1.addCell(cell);
				//����ֵ
				cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintNumber()+"",setFont(numTX,false)));
				cell.setColspan(1);
				cell.setUseAscender(true);
				cell.setFixedHeight(25f);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table1.addCell(cell);
				//��עֵ
				cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintNotes(),setFont(numTX,false)));
				cell.setColspan(3);
				cell.setUseAscender(true);
				cell.setFixedHeight(25f);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table1.addCell(cell);
			}
//            cell = new PdfPCell(new Phrase(app.getPrintEquipment(),setFont(numTX,false)));
//            	cell.setColspan(8);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(75f);
//            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
            //------------- �������豸
            cell = new PdfPCell(new Phrase("�������豸",setFont(numTX,false)));
			 	cell.setRowspan(testingEquips.size()+1);
				cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- �������豸��ͷ��
			 if(testingEquips.size() <= 0) {
				 cell = new PdfPCell(new Phrase("��", setFont(numTX,false)));
					 cell.setColspan(10);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
			 } else {
				 cell = new PdfPCell(new Phrase("����", setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("�ͺ�", setFont(numTX,false)));
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("����", setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("���У׼����", setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
					 cell = new PdfPCell(new Phrase("��ע", setFont(numTX,false)));
					 cell.setColspan(4);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 for(int j = 0; j < testingEquips.size(); j++) {
				 	//����ֵ
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getName(),setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setFixedHeight(25f);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					//�ͺ�ֵ
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getTestingModel(),setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setFixedHeight(25f);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //����ֵ
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getCount()+"",setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setFixedHeight(25f);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //���һ��У׼����ֵ
					 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
					 cell = new PdfPCell(new Phrase(df.format(testingEquips.get(j).getTime()),setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setFixedHeight(25f);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //��עֵ
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getRemark(),setFont(numTX,false)));
						 cell.setColspan(4);
						 cell.setUseAscender(true);
						 cell.setFixedHeight(25f);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
				 }
			 }

//            cell = new PdfPCell(new Phrase(app.getTestEquipment(),setFont(numTX,false)));
//            	cell.setColspan(8);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(75f);
//            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
            //------------- ��ע
            cell = new PdfPCell(new Phrase("��ע",setFont(numTX,false)));
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(75f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- ��עֵ
            cell = new PdfPCell(new Phrase(app.getRemarks(),setFont(numTX,false)));
            	cell.setColspan(10);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(75f);
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
         	document.add(table1); 
         
            
            PdfAppearance[] onOff = new PdfAppearance[2];
            onOff[0] = canvas.createAppearance(10, 10);
            onOff[0].rectangle(0, 0, 10, 10);
            onOff[0].stroke();
            onOff[1] = canvas.createAppearance(10, 10);
            onOff[1].setRGBColorFill(255, 255, 255);
            onOff[1].rectangle(0, 0, 10, 10);
            onOff[1].fillStroke();
            onOff[1].moveTo(4, 0);
            onOff[1].lineTo(10, 10);
            onOff[1].moveTo(4, 0);
            onOff[1].lineTo(0, 5);
            onOff[1].stroke();
            RadioCheckField checkbox;
            
            boolean[] js = new boolean[4];
            js[0]=app.getFlat()==null?false:app.getFlat();
            js[1]=app.getGravure()==null?false:app.getGravure();
            js[2]=app.getWebby()==null?false:app.getWebby();
            js[3]=app.getFlexible()==null?false:app.getFlexible();
            for (int i = 0; i < YSJS.length; i++) {
                rect = new Rectangle(245 + i * 65, 526, 255  + i * 65, 516);
                checkbox = new RadioCheckField(writer, rect, YSJS[i], "On");
                
                if(js[i])checkbox.setChecked(true);  
                else checkbox.setChecked(false); 
                
                field = checkbox.getCheckField();
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", onOff[0]);
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "On", onOff[1]);
                writer.addAnnotation(field);
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                    new Phrase(YSJS[i], setFont(10,false)), 260 + i * 65, 518, 0);
            }
            
         	boolean[] zt = new boolean[5];
         	zt[0]=app.getPapery()==null?false:app.getPapery();
         	zt[1]=app.getPastern()==null?false:app.getPastern();
         	zt[2]=app.getFrilling()==null?false:app.getFrilling();
         	zt[3]=app.getMetal()==null?false:app.getMetal();
         	zt[4]=app.getPlastic()==null?false:app.getPlastic();
         	
            for (int i = 0; i < YSZT.length; i++) {
                rect = new Rectangle(240 + i * 60, 476, 250  + i * 60, 466);
                checkbox = new RadioCheckField(writer, rect, YSZT[i], "Yes");
                
                if(zt[i])checkbox.setChecked(true);  
                else checkbox.setChecked(false);
                
                field = checkbox.getCheckField();
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", onOff[0]);
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Yes", onOff[1]);
                writer.addAnnotation(field);
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                    new Phrase(YSZT[i], setFont(10,false)), 255 + i * 60, 468, 0);
            } 
       
         document.close();
	     outr.close();
		 }catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
		 }
	}

	public void createPDFRecognition(ApplicationForm app,String filePath, List<PrintEquipment> printEquipments, List<TestingEquip> testingEquips){
		//���·��
		String fileDesc = "";
		// ���������	
		FileOutputStream outr = null;
		//fileDesc="D:/wmq/wtf.pdf";	
		fileDesc=filePath;
		//�����
		 int numD=20;
		 
		 //С����
		 int numX=14;
		 
		 //����
		 int numTD=14;
		 
		 //���С
		 int numTX=12;
		 
		 //�����
		 int numM=13;
		 
		 Document document=new Document();
		 
		 try{
		 outr= new FileOutputStream(fileDesc);
         PdfWriter writer = PdfWriter.getInstance(document, outr);
         document.open();
         //document.newPage();
         
         PdfContentByte canvas = writer.getDirectContent();
         Rectangle rect;
         PdfFormField field;
         //Font font = new Font(baseFontChinese , fontSize , Font.NORMAL, BaseColor.BLACK);
         
         Chunk chunk;
         
         Paragraph paragraph;
         
         PdfPCell cell;
         
         Paragraph par0=new Paragraph("\r\n",setFont(numX,true));
	     par0.setAlignment(Element.ALIGN_CENTER);
         
	     paragraph =new Paragraph("��� "+app.getTitleno(),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_RIGHT);
         document.add(paragraph);
	     
         //����
         for(int i=0;i<4;i++)document.add(par0);
         
         
         //��  Ʒ  ��  ��  ӡ  ˢ  ��  ��
         paragraph =new Paragraph("��  Ʒ  ��  ��  ӡ  ˢ  ��  ��",setFont(numD,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
	     document.add(par0);
         
	     //�� �� ��
	     paragraph=new Paragraph("�� �� �� �� ��",setFont(26,true));
	     paragraph.setAlignment(Element.ALIGN_CENTER);
	     document.add(paragraph);
         
	     //����
         for(int i=0;i<12;i++)document.add(par0);
         
         //������ҵ����
         String entName = String.format("%-20s", app.getEnterprisename());
         
         paragraph=new Paragraph("                                                 ���븴����ҵ���ƣ�  ",setFont(numX,false));
	       
         chunk = new Chunk(entName,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
         chunk = new Chunk("(����)",setFontUnderline(numX,false));
         paragraph.add(chunk);
         
         paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	     
	     document.add(par0);
	     
	     //��   ��   ��   ��
	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");   
         String str = String.format("%-28s", sdf.format(app.getCreatedate()));
	     
	     paragraph=new Paragraph("                                                 ��  ��  ��  ��  ��  �ڣ�  ",setFont(numX,false));
         
         chunk = new Chunk(str,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
	     paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	        
	     //����
         for(int i=0;i<10;i++)document.add(par0);
	     
         paragraph=new Paragraph("�й���Ʒ����������",setFont(numX,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //�ڶ�ҳ
         document.newPage();
         
         //����
         for(int i=0;i<1;i++)document.add(par0);
         
         	PdfPTable table1 = new PdfPTable(12);
         	table1.setTotalWidth((float)480.0);
            table1.setLockedWidth(true);
            //------------- ��ҵ����(����)
            cell = new PdfPCell(new Phrase("��ҵ����(����)",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ҵ����(����)ֵ
            cell = new PdfPCell(new Phrase(app.getEnterprisename(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ֤����Ч��
            cell = new PdfPCell(new Phrase("֤����Ч��",setFont(numTX,false)));
            	cell.setRowspan(2);
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ֤����Ч��ֵ
            String yxq;
            if (app.getCertappdate()==null||app.getCerttodate()==null)
				yxq = "��Ч����Ч";
			else
				yxq=sdf.format(app.getCertappdate())+"\n"+"        ~"+"\n"+sdf.format(app.getCerttodate());
            cell = new PdfPCell(new Phrase(yxq,setFont(numTX,false)));
            	cell.setRowspan(2);
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ҵ����(Ӣ��)
            cell = new PdfPCell(new Phrase("��ҵ����(Ӣ��)",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ҵ����(Ӣ��)ֵ
            cell = new PdfPCell(new Phrase(app.getEnglishname(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            //------------- ��ϸ��ַ
            cell = new PdfPCell(new Phrase("��ϸ��ַ",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ϸ��ֵַ
            cell = new PdfPCell(new Phrase(app.getAddress(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��������
            cell = new PdfPCell(new Phrase("��������",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��������ֵ
            cell = new PdfPCell(new Phrase(app.getPostalcode(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ӡˢ��Ӫ���֤����
            cell = new PdfPCell(new Phrase("ӡˢ��Ӫ���֤����",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ӡˢ��Ӫ���֤����ֵ
            cell = new PdfPCell(new Phrase(app.getCertificateno(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ����
            cell = new PdfPCell(new Phrase("����",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ����ֵ
            cell = new PdfPCell(new Phrase(app.getFax(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- Ӫҵִ�պ���
            cell = new PdfPCell(new Phrase("Ӫҵִ�պ���",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- Ӫҵִ�պ���ֵ
            cell = new PdfPCell(new Phrase(app.getBusinessno(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ҵ����
            cell = new PdfPCell(new Phrase("��ҵ����",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ҵ����ֵ
            cell = new PdfPCell(new Phrase(app.getEnterprisekind(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ע���ʱ�
            cell = new PdfPCell(new Phrase("ע���ʱ�",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ע���ʱ�ֵ
            cell = new PdfPCell(new Phrase(app.getRegistercapital(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);          
            //------------- ���˴���
            cell = new PdfPCell(new Phrase("���˴���",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ���˴���ֵ
            cell = new PdfPCell(new Phrase(app.getArtificialperson(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��
            cell = new PdfPCell(new Phrase("ְ��",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��ֵ
            cell = new PdfPCell(new Phrase(app.getApjob(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �绰
            cell = new PdfPCell(new Phrase("�绰",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �绰ֵ
            cell = new PdfPCell(new Phrase(app.getAptel(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ϵ��
            cell = new PdfPCell(new Phrase("��ϵ��",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��ϵ��ֵ
            cell = new PdfPCell(new Phrase(app.getLinkman(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��
            cell = new PdfPCell(new Phrase("ְ��",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��ֵ
            cell = new PdfPCell(new Phrase(app.getLjob(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �绰
            cell = new PdfPCell(new Phrase("�绰",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �绰ֵ
            cell = new PdfPCell(new Phrase(app.getLtel(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);  
            /*
            //------------- ��Ӫ��Χ
            cell = new PdfPCell(new Phrase("��Ӫ��Χ",setFont(numTX,false)));
         		cell.setRowspan(2);
         		cell.setColspan(1);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	*/
         	//------------- ��Ӫ
         	cell = new PdfPCell(new Phrase("��Ӫ",setFont(numTX,false)));
         		cell.setColspan(2);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	//------------- ��Ӫֵ
         	cell = new PdfPCell(new Phrase(app.getMainbusiness(),setFont(numTX,false)));
         		cell.setColspan(10);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	//------------- ��Ӫ
         	cell = new PdfPCell(new Phrase("��Ӫ",setFont(numTX,false)));
         		cell.setColspan(2);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	//------------- ��Ӫֵ
         	cell = new PdfPCell(new Phrase(app.getRestbusiness(),setFont(numTX,false)));
         		cell.setColspan(10);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
            /*
         	//------------- ��Ա���
            cell = new PdfPCell(new Phrase("��Ա���",setFont(numTX,false)));
         		cell.setRowspan(2);
         		cell.setColspan(1);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	*/
         	//------------- ְ������
            cell = new PdfPCell(new Phrase("ְ������",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ������ֵ
            cell = new PdfPCell(new Phrase(app.getEmployeetotal() == null?" ":Integer.toString(app.getEmployeetotal()),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ������Ա
            cell = new PdfPCell(new Phrase("������Ա",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- ������Աֵ
            cell = new PdfPCell(new Phrase(app.getTechniciantotal() == null?" ":Integer.toString(app.getTechniciantotal()),setFont(numTX,false)));
                cell.setColspan(6);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ����ӡˢ����������
            cell = new PdfPCell(new Phrase("����ӡˢ����������",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- ����ӡˢ����������ֵ
            cell = new PdfPCell(new Phrase(app.getBcprincipal(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��
            cell = new PdfPCell(new Phrase("ְ��",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- ְ��ֵ
            cell = new PdfPCell(new Phrase(app.getTpbusiness(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��
            cell = new PdfPCell(new Phrase("ְ��",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ְ��ֵ
            cell = new PdfPCell(new Phrase(app.getTpopost(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ����ӡˢ��������
            cell = new PdfPCell(new Phrase("����ӡˢ\n��������",setFont(numTX,false)));
                cell.setRowspan(2);
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- ���ּ�������
            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
                cell.setColspan(10);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- ������������
            cell = new PdfPCell(new Phrase("����(����)",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- ��������ֵ
            cell = new PdfPCell(new Phrase(app.getElsetype(),setFont(numTX,false)));
                cell.setColspan(8);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- �����������
            cell = new PdfPCell(new Phrase("�����������",setFont(numTX,false)));
                cell.setRowspan(2);
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- ������������
            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
                cell.setColspan(10);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- ������������
            cell = new PdfPCell(new Phrase("����(����)",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- ��������ֵ
            cell = new PdfPCell(new Phrase(app.getElsetype(),setFont(numTX,false)));
                cell.setColspan(8);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- ��Ҫӡˢ�豸
			 cell = new PdfPCell(new Phrase("��Ҫӡˢ�豸",setFont(numTX,false)));
				 cell.setRowspan(printEquipments.size()+1);
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 rect = new Rectangle(20,20,20,20);
			 table1.addCell(cell);
			 if(printEquipments.size() <= 0) {
				 cell = new PdfPCell(new Phrase("��", setFont(numTX,false)));
				 cell.setColspan(10);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
			 } else {
				 //------------- ��Ҫӡˢ�豸��ͷ��
				 cell = new PdfPCell(new Phrase("����", setFont(numTX,false)));
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("�ͺ�", setFont(numTX,false)));
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("����", setFont(numTX,false)));
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("����", setFont(numTX,false)));
				 cell.setColspan(1);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("��ע", setFont(numTX,false)));
				 cell.setColspan(3);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 //------------- ��Ҫӡˢ�豸ֵ
				 for(int i = 0 ; i < printEquipments.size() ; i++) {
					 //����ֵ
					 cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintName(),setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //�ͺ�ֵ
					 cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintModel(),setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //����ֵ
					 cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintPlace(),setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //����ֵ
					 cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintNumber()+"",setFont(numTX,false)));
					 cell.setColspan(1);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //��עֵ
					 cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintNotes(),setFont(numTX,false)));
					 cell.setColspan(3);
					 cell.setUseAscender(true);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
				 }
			 }

//            cell = new PdfPCell(new Phrase(app.getPrintEquipment(),setFont(numTX,false)));
//            	cell.setColspan(8);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(75f);
//            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//            cell = new PdfPCell(new Phrase(app.getPrintEquipment(),setFont(numTX,false)));
//            	cell.setColspan(8);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(75f);
//            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
            //------------- �������豸
			 cell = new PdfPCell(new Phrase("�������豸",setFont(numTX,false)));
				 cell.setRowspan(testingEquips.size()+1);
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 rect = new Rectangle(20,20,20,20);
			 table1.addCell(cell);
            //------------- �������豸��ͷ��
			 if(testingEquips.size() <= 0) {
				 cell = new PdfPCell(new Phrase("��", setFont(numTX,false)));
				 cell.setColspan(10);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
			 } else {
				 cell = new PdfPCell(new Phrase("����", setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("�ͺ�", setFont(numTX,false)));
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("����", setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("���У׼����", setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("��ע", setFont(numTX,false)));
					 cell.setColspan(4);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 for(int j = 0; j < testingEquips.size(); j++) {
					 //����ֵ
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getName(),setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					//�ͺ�ֵ
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getTestingModel(),setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //����ֵ
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getCount()+"",setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setFixedHeight(25f);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //���һ��У׼����ֵ
					 cell = new PdfPCell(new Phrase(df.format(testingEquips.get(j).getTime()),setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setFixedHeight(25f);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //��עֵ
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getRemark(),setFont(numTX,false)));
						 cell.setColspan(4);
						 cell.setUseAscender(true);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
				 }
			 }
//            cell = new PdfPCell(new Phrase(app.getTestEquipment(),setFont(numTX,false)));
//            	cell.setColspan(8);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(75f);
//            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
            //------------- ��ע
            cell = new PdfPCell(new Phrase("��ע",setFont(numTX,false)));
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(75f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- ��עֵ
            cell = new PdfPCell(new Phrase(app.getRemarks(),setFont(numTX,false)));
            	cell.setColspan(10);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(75f);
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
         	document.add(table1); 
         
            
            PdfAppearance[] onOff = new PdfAppearance[2];
            onOff[0] = canvas.createAppearance(10, 10);
            onOff[0].rectangle(0, 0, 10, 10);
            onOff[0].stroke();
            onOff[1] = canvas.createAppearance(10, 10);
            onOff[1].setRGBColorFill(255, 255, 255);
            onOff[1].rectangle(0, 0, 10, 10);
            onOff[1].fillStroke();
            onOff[1].moveTo(4, 0);
            onOff[1].lineTo(10, 10);
            onOff[1].moveTo(4, 0);
            onOff[1].lineTo(0, 5);
            onOff[1].stroke();
            RadioCheckField checkbox;
            
            boolean[] js = new boolean[4];
            js[0]=app.getFlat()==null?false:app.getFlat();
            js[1]=app.getGravure()==null?false:app.getGravure();
            js[2]=app.getWebby()==null?false:app.getWebby();
            js[3]=app.getFlexible()==null?false:app.getFlexible();
            for (int i = 0; i < YSJS.length; i++) {
                rect = new Rectangle(245 + i * 65, 501, 255  + i * 65, 491);
                checkbox = new RadioCheckField(writer, rect, YSJS[i], "On");
                
                if(js[i])checkbox.setChecked(true);  
                else checkbox.setChecked(false); 
                
                field = checkbox.getCheckField();
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", onOff[0]);
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "On", onOff[1]);
                writer.addAnnotation(field);
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                    new Phrase(YSJS[i], setFont(10,false)), 260 + i * 65, 493, 0);
            }
            
         	boolean[] zt = new boolean[5];
         	zt[0]=app.getPapery()==null?false:app.getPapery();
         	zt[1]=app.getPastern()==null?false:app.getPastern();
         	zt[2]=app.getFrilling()==null?false:app.getFrilling();
         	zt[3]=app.getMetal()==null?false:app.getMetal();
         	zt[4]=app.getPlastic()==null?false:app.getPlastic();
         	
            for (int i = 0; i < YSZT.length; i++) {
                rect = new Rectangle(240 + i * 60, 451, 250  + i * 60, 441);
                checkbox = new RadioCheckField(writer, rect, YSZT[i], "Yes");
                
                if(zt[i])checkbox.setChecked(true);  
                else checkbox.setChecked(false);
                
                field = checkbox.getCheckField();
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Off", onOff[0]);
                field.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, "Yes", onOff[1]);
                writer.addAnnotation(field);
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                    new Phrase(YSZT[i], setFont(10,false)), 255 + i * 60, 443, 0);
            } 
       
         document.close();
	     outr.close();
		 }catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
		 }
	}
	public void createPDFPS(ReviewForm rev,String filePath,List<Evaluation> evalua){
		//���·��
		String fileDesc = "";
		// ���������	
		FileOutputStream outr = null;
		fileDesc=filePath;
		//�����
		 int numD=20;
		 
		 //С����
		 int numX=14;
		 
		 //����
		 int numTD=14;
		 
		 //���С
		 int numTX=12;
		 
		 //�����
		 int numM=13;
		 
		 Document document=new Document();
		 
		 try{
		 outr= new FileOutputStream(fileDesc);
         PdfWriter writer = PdfWriter.getInstance(document, outr);
         HeadFootInfoPdfPageEvent headFootInfoPdfPageEvent = new HeadFootInfoPdfPageEvent();
         headFootInfoPdfPageEvent.setTitleno(rev.getTitleno());
         writer.setPageEvent(headFootInfoPdfPageEvent);  
         document.open();
         //document.newPage();
         
         PdfContentByte canvas = writer.getDirectContent();
         Rectangle rect;
         PdfFormField field;
         //Font font = new Font(baseFontChinese , fontSize , Font.NORMAL, BaseColor.BLACK);
         
         Chunk chunk;
         
         Paragraph paragraph;
         
         PdfPCell cell;
         
         Paragraph par0=new Paragraph("\r\n",setFont(numX,true));
	     par0.setAlignment(Element.ALIGN_CENTER);
         
	     paragraph =new Paragraph("��� "+rev.getTitleno(),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_RIGHT);
         document.add(paragraph);
	     
         //����
         for(int i=0;i<4;i++)document.add(par0);
         
         
         //��  Ʒ  ��  ��  ӡ  ˢ  ��  ��
         paragraph =new Paragraph("��  Ʒ  ��  ��  ӡ  ˢ  ��  ��",setFont(numD,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
	     document.add(par0);
         
	     switch(rev.getTitleno().substring(0,2)){
	     case "XB" :
	     //�� �� ��
	    	 paragraph=new Paragraph("��   ��   ��   ��   ��",setFont(26,true));
	    	 break;
         
	     case "FR" : 
	    	 //�� �� ��
	    	 paragraph=new Paragraph("��   ��   ��   ��   ��",setFont(26,true));
	    	 break;
	     default :
	    	 paragraph=new Paragraph("?   ?   ��   ��   ��",setFont(26,true));
		     break;
	     }
  		 paragraph.setAlignment(Element.ALIGN_CENTER);
  		 document.add(paragraph);
  		 
	     //����
         for(int i=0;i<12;i++)document.add(par0);
         
         //������ҵ����
         String entName = String.format("%-34s", rev.getEnterpriseName());
         
         paragraph=new Paragraph("                                                     ������ҵ���ƣ�  ",setFont(numX,false));
	       
         chunk = new Chunk(entName,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
         paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	     
	     document.add(par0);
	     
	     //�������
         String chiCenter = String.format("%-28s", rev.getChiCenter());
         
         paragraph=new Paragraph("                                                     ��   ��   ��   ����  ",setFont(numX,false));
	       
         chunk = new Chunk(chiCenter,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
         paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	     
	     document.add(par0);
	     
	     //��   ��   ��   ��
	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	     String str;
	     if(rev.getPsdate()!=null)
         str = String.format("%-32s", sdf.format(rev.getCreateDate()));
	     else
	     str = String.format("%-32s", " ");
	     //paragraph=new Paragraph("                                                      ����ʱ�䣺  "+str,setFontUnderline(numX,true));
	     paragraph=new Paragraph("                                                     ��   ��   ʱ   �䣺  ",setFont(numX,false));
         
         chunk = new Chunk(str,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
	     paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	        
	     //����
         for(int i=0;i<8;i++)document.add(par0);
	     
         paragraph=new Paragraph("�й���Ʒ����������",setFont(numX,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //�ڶ�ҳ
         document.newPage();
         
         //��Ʒ����ӡˢ�ʸ��ֳ������
         paragraph =new Paragraph("��Ʒ����ӡˢ�ʸ��ֳ������",setFont(16,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         for(int i=0;i<2;i++)document.add(par0);
         
         PdfPTable table1 = new PdfPTable(12);
      	 table1.setTotalWidth((float)480.0);
         table1.setLockedWidth(true);
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("������Ŀ��Ҫ��",setFont(numTX,true)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("���󷽷�",setFont(numTX,true)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
         cell = new PdfPCell(new Phrase("�����¼",setFont(numTX,true)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
         cell = new PdfPCell(new Phrase("����������",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);

         String required="";
         String PSJL="";
         String PSJG="";

        //����������ʾ
//		 Class<? extends ReviewForm> userClass = rev.getClass();
//		 java.lang.reflect.Field[] fields = userClass.getDeclaredFields();
		 ReviewFormPart reviewFormPart;

		 for (Evaluation evaluation : evalua) {
			 if(evaluation.getAvailable()){
				 //------------- ������Ŀ��Ҫ��
				 required=evaluation.getRequired();
				 cell = new PdfPCell(new Phrase(required.replace("<br>","\n"),setFont(numTX,false)));
					 cell.setColspan(4);
					 cell.setUseAscender(true);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 //------------- ���󷽷�
				 cell = new PdfPCell(new Phrase(" "+evaluation.getMethod(),setFont(numTX,false)));
					 cell.setColspan(3);
					 cell.setUseAscender(true);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 //------------- �õ���Ӧ������
				 PSJL="";
				 PSJG="";
				 reviewFormPart = reviewFormPartService.getByReviewFormNumId(rev.getId(),evaluation.getNum());
				 PSJL = reviewFormPart.getPsjl();
				 PSJG = reviewFormPart.getPsjg();
				 //------------- �����¼
				 cell = new PdfPCell(new Phrase(PSJL,setFont(numTX,false)));
					 cell.setColspan(3);
					 cell.setUseAscender(true);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 //------------- ����������
				 cell = new PdfPCell(new Phrase(PSJG,setFont(numTX,true)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
			 }
		 }
// 		Class<? extends ReviewForm> userClass = rev.getClass();
// 		java.lang.reflect.Field[] fields = userClass.getDeclaredFields();
//         for (Evaluation evaluation : evalua) {
//			if(evaluation.getAvailable()){
//				//------------- ������Ŀ��Ҫ��
//				required=evaluation.getRequired();
//		         cell = new PdfPCell(new Phrase(required.replace("<br>","\n"),setFont(numTX,false)));
//		         	cell.setColspan(4);
//		         	cell.setUseAscender(true);
//		         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//		         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		         table1.addCell(cell);
//		         //------------- ���󷽷�
//		         cell = new PdfPCell(new Phrase(" "+evaluation.getMethod(),setFont(numTX,false)));
//		         	cell.setColspan(3);
//		         	cell.setUseAscender(true);
//		         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//		         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		         table1.addCell(cell);
//		         //------------- �����¼
//		         PSJL="PSJL"+evaluation.getNum();
//		         PSJG="PSJG"+evaluation.getNum();
//		 		for (java.lang.reflect.Field field1 : fields) {
//		 			// ����ַ�������
//		 			String userFieldName = field1.getName();
//		 			// �ַ�������ĸ��д
//		 			char[] cs = userFieldName.toCharArray();
//		 			if (Character.isLowerCase(cs[0]))
//	 			   {
//
//		 				cs[0] -= 32;
//	 			   }
//		 			if(String.valueOf(cs).equals(PSJL)){
//		 			// ����get����
//		 			Method method = userClass.getMethod("get" + String.valueOf(cs));
//		 			// �õ�ֵ
//		 			Object invoke = method.invoke(rev);
//		 			cell = new PdfPCell(new Phrase("    "+(String) invoke,setFont(numTX,false)));
//		         	cell.setColspan(2);
//		         	cell.setUseAscender(true);
//		         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//		         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		         	table1.addCell(cell);
//		 			}
//
//		 	}
//		         //------------- ����������
//		 		for (java.lang.reflect.Field field2 : fields) {
//		 			// ����ַ�������
//		 			String userFieldName2 = field2.getName();
//		 			// �ַ�������ĸ��д
//		 			char[] cs2 = userFieldName2.toCharArray();
//		 			if (Character.isLowerCase(cs2[0]))
//		 			   {
//			 				cs2[0] -= 32;
//		 			   }
//		 			if(String.valueOf(cs2).equals(PSJG)){
//			 			// ����get����
//			 			Method method = userClass.getMethod("get" + String.valueOf(cs2));
//			 			// �õ�ֵ
//			 			Object invoke = method.invoke(rev);
//			 			cell = new PdfPCell(new Phrase("             "+(String) invoke,setFont(numTX,false)));
//			         	cell.setColspan(2);
//			         	cell.setUseAscender(true);
//			         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//			         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//			         	table1.addCell(cell);
//			 			}
//		 		}
//			}
//		}
         
         document.add(table1);
         
         //ע����*�ŵ���Ϊ�ص���
         paragraph =new Paragraph("                 ע����*�ŵ���Ϊ�ص���",setFont(8,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         document.add(paragraph);
         
	     //����
         for(int i=0;i<1;i++)document.add(par0);
         
         //�ۺ��ж�����
         paragraph =new Paragraph("�ۺ��ж�����",setFont(14,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //�ۺ��ж�����(
         paragraph =new Paragraph("                ������۷֡��϶��������ݲ��϶������֡�\n"
+"                ������ҵ������ͬʱ��������Ҫ��ģ��������Ϊ���϶����������������Ϊ�����϶�����\n"
+"                1��	�ص����*�ű���ΪA���������������������C�\n"
+"                2��	ȫ�����B��ĸ������ó���6��\n"
+"            ���ص������C��ĸ������ó�����������ص������һ��C��ʱ��ȫ���г���B�����Ŀ���ó���4������ֶ���C��ʱ��ȫ���г���B�����Ŀ���ó���2�",setFont(12,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         document.add(paragraph);
         
         document.add(par0);
       //����Աǩ����
         paragraph =new Paragraph("����Աǩ����",setFont(14,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         document.add(par0);
         
         PdfPTable table2 = new PdfPTable(12);
      	 table2.setTotalWidth((float)480.0);
         table2.setLockedWidth(true);
         //------------- ����
         cell = new PdfPCell(new Phrase("����",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setMinimumHeight(25f);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- ������λ
         cell = new PdfPCell(new Phrase("������λ",setFont(numTX,true)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- ְ��/ְ��
         cell = new PdfPCell(new Phrase("ְ��/ְ��",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
			cell.setMinimumHeight(25f);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- ǩ��
         cell = new PdfPCell(new Phrase("ǩ��",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
			cell.setMinimumHeight(25f);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
			 //------------- ����
			 cell = new PdfPCell(new Phrase("����",setFont(numTX,true)));
			 cell.setColspan(2);
			 cell.setUseAscender(true);
			 cell.setMinimumHeight(25f);
			 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		 table2.addCell(cell);
         
         String[] id = null;  
 		 id = rev.getExpertIds().split(",");
 		 
 		 if(id != null)
 		 for(int i=0;i<id.length;i++){
 			 
 		 System.out.println(id[i]);
 		 int userId=Integer.parseInt(id[i]);
 		 System.out.println(Integer.parseInt(id[i]));
 		 System.out.println(userId);
 		 ComUser user=comUserService.getByUserId(userId);
         //------------- ����
         cell = new PdfPCell(new Phrase(user.getUsername(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- ������λ
         cell = new PdfPCell(new Phrase(user.getUnit(),setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- ְ��/ְ��
         cell = new PdfPCell(new Phrase(user.getPost(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- ǩ��
         cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
			 //------------- ����
			 cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
			 cell.setColspan(2);
			 cell.setUseAscender(true);
			 cell.setFixedHeight(25f);
			 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		 table2.addCell(cell);
 		}
         
         document.add(table2);
         
         document.newPage();
         
         PdfPTable table3 = new PdfPTable(12);
      	 table3.setTotalWidth((float)480.0);
         table3.setLockedWidth(true);
         //------------- 
         cell = new PdfPCell(new Phrase("��\n��\n��\n��\n��\n��\n��\n��",setFont(numTX,true)));
         	cell.setRowspan(2);
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(250f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase(rev.getMavinIdea(),setFont(numTX,false)));
         	cell.setColspan(10);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(200f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("�����鳤ǩ����",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //-------------
         cell = new PdfPCell(new Phrase("���ڣ�",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("��\n֧\n��\n��\n��\n��",setFont(numTX,true)));
         	cell.setRowspan(3);
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(250f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase(rev.getFrameworkIdea(),setFont(numTX,false)));
         	cell.setColspan(10);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(170f);
         	cell.setBorderWidthBottom(0f);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- ����
		 cell = new PdfPCell(new Phrase("                                                                                ��֧����(����)",setFont(numTX,false)));
			 cell.setColspan(10);
			 cell.setUseAscender(true);
			 cell.setFixedHeight(30f);
			 cell.setBorderWidthTop(0f);
			 cell.setBorderWidthBottom(0f);
			 cell.setPaddingRight(10f);
			 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 cell.setVerticalAlignment(Element.ALIGN_TOP);
		 table3.addCell(cell);
         //-------------
         cell = new PdfPCell(new Phrase("������ǩ����",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("���ڣ�",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("��\n��\n��\nƷ\n��\n��\n��\n��\n��\n��",setFont(numTX,true)));
         	cell.setRowspan(4);
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(250f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase(rev.getCenterIdea(),setFont(numTX,false)));
         	cell.setColspan(10);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(160f);
         	cell.setBorderWidthBottom(0f);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //-------------����
		 cell = new PdfPCell(new Phrase("                                                                               �й���Ʒ��������(����)",setFont(numTX,false)));
			 cell.setColspan(10);
			 cell.setUseAscender(true);
			 cell.setFixedHeight(30f);
			 cell.setBorderWidthTop(0f);
			 cell.setBorderWidthBottom(0f);
			 cell.setPaddingRight(10f);
			 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 cell.setVerticalAlignment(Element.ALIGN_TOP);
		 table3.addCell(cell);
		 //-------------
         cell = new PdfPCell(new Phrase("��׼��ǩ����",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(30f);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("���ڣ�",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(30f);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("��׼��ǩ����",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(30f);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("���ڣ�",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(30f);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         document.add(table3);
       
         
         document.close();
	     outr.close();
		 }catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
		 }
	}
	
	public void createPDFPSComment(ReviewForm rev,String filePath){
		
		//���·��
		String fileDesc = "";
		// ���������	
		FileOutputStream outr = null;
		fileDesc=filePath;
		//�����
		 int numD=20;
		 
		 //С����
		 int numX=14;
		 
		 //����
		 int numTD=14;
		 
		 //���С
		 int numTX=12;
		 
		 //�����
		 int numM=13;
		 
		 Document document=new Document();
		 
		 try{
		 outr= new FileOutputStream(fileDesc);
         PdfWriter writer = PdfWriter.getInstance(document, outr);
         document.open();
         //document.newPage();
         
         PdfContentByte canvas = writer.getDirectContent();
         Rectangle rect;
         PdfFormField field;
         //Font font = new Font(baseFontChinese , fontSize , Font.NORMAL, BaseColor.BLACK);
         
         Chunk chunk;
         
         Paragraph paragraph;
         
         PdfPCell cell;
         
         Paragraph par0=new Paragraph("\r\n",setFont(numX,true));
	     par0.setAlignment(Element.ALIGN_CENTER);
         
	     paragraph =new Paragraph("��� "+rev.getTitleno(),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_RIGHT);
         document.add(paragraph);
	     
         //����
         for(int i=0;i<4;i++)document.add(par0);
         
         
         //��  Ʒ  ��  ��  ӡ  ˢ  ��  ��
         paragraph =new Paragraph("��  Ʒ  ��  ��  ӡ  ˢ  ��  ��",setFont(numD,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
	     document.add(par0);
         
	     switch(rev.getTitleno().substring(0,2)){
	     case "XB" :
	     //�� �� ��
	    	 paragraph=new Paragraph("��   ��   ��   ��   ��",setFont(26,true));
	    	 break;
         
	     case "FR" : 
	    	 //�� �� ��
	    	 paragraph=new Paragraph("��   ��   ��   ��   ��",setFont(26,true));
	    	 break;
	     default :
	    	 paragraph=new Paragraph("?   ?   ��   ��   ��",setFont(26,true));
		     break;
	     }
  		 paragraph.setAlignment(Element.ALIGN_CENTER);
  		 document.add(paragraph);
  		 
	     //����
         for(int i=0;i<12;i++)document.add(par0);
         
         //������ҵ����
         String entName = String.format("%-34s", rev.getEnterpriseName());
         
         paragraph=new Paragraph("                                                     ������ҵ���ƣ�  ",setFont(numX,false));
	       
         chunk = new Chunk(entName,setFontUnderline(numX,true));
         paragraph.add(chunk);
         
         paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	     
	     document.add(par0);
	     
	     //�������
         String chiCenter = String.format("%-28s", rev.getChiCenter());
         
         paragraph=new Paragraph("                                                     ��   ��   ��   ����  ",setFont(numX,false));
	       
         chunk = new Chunk(chiCenter,setFontUnderline(numX,true));
         paragraph.add(chunk);
         
         paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	     
	     document.add(par0);
	     
	     //��   ��   ��   ��
	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	     String str;
	     if(rev.getPsdate()!=null)
         str = String.format("%-32s", sdf.format(rev.getCreateDate()));
	     else
	     str = String.format("%-32s", " ");
	     //paragraph=new Paragraph("                                                      ��   ��   ʱ   �䣺  "+str,setFontUnderline(numX,true));
	     paragraph=new Paragraph("                                                     ��   ��   ʱ   �䣺  ",setFont(numX,false));
         
         chunk = new Chunk(str,setFontUnderline(numX,true));
         paragraph.add(chunk);
         
	     paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	        
	     //����
         for(int i=0;i<8;i++)document.add(par0);
	     
         paragraph=new Paragraph("�й���Ʒ����������",setFont(numX,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //�ڶ�ҳ
         document.newPage();
         
         //��Ʒ����ӡˢ�ʸ��ֳ������
         paragraph =new Paragraph("��Ʒ����ӡˢ�ʸ��ֳ������",setFont(16,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         for(int i=0;i<2;i++)document.add(par0);
         
         PdfPTable table1 = new PdfPTable(11);
      	 table1.setTotalWidth((float)480.0);
         table1.setLockedWidth(true);
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("������Ŀ��Ҫ��",setFont(numTX,true)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("���󷽷�",setFont(numTX,true)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
         cell = new PdfPCell(new Phrase("�����¼",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
         cell = new PdfPCell(new Phrase("����������",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("1.	���������Ŀ��\n    ��ҵӦ�ƶ������������������Ŀ�꣬��ʾ�����������Ӧ�ﵽ������Ҫ�󣬲��᳹ʵʩ����Ч����",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    �����������Ŀ�겢�᳹ʵʩ�ǡ�A��������ǡ�C������ȱ�ݼǡ�B����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
         //cell = new PdfPCell(new Phrase(rev.getPSJL1(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
         //cell = new PdfPCell(new Phrase(rev.getPSJG1(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("2.	��֯����\n"
    +"    ��ҵӦ���������������̣���ȷ�����쵼��ҵ�񡢼��������������顢�ִ��Ȳ��Ÿ��Ե�ְ��Ȩ�޺��໥��ϵ��ʹ����ӡˢ�������ν����ף��й������̺���֯������ͼ��ȷ������ӡˢƷ�������Ϲ��ұ�׼Ҫ�󣬲����ع����й��������ĸ���涨��",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    �������ú�����Աְ����ȷ�ǡ�A������֮�ǡ�C������ȱ�ݼǡ�B����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
         //cell = new PdfPCell(new Phrase(rev.getPSJL2(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
         //cell = new PdfPCell(new Phrase(rev.getPSJG2(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("3.	��Ա\n"
        		 +"3.1	�����쵼\n"
        		 +"    ��ҵ�ڲ�Ӧ��ȷ��������ӡˢ����������ҵ�쵼�����쵼Ӧ�˽��������֪ʶ������Ҫ��͹����й��������Ĺ涨��",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    ����Ҫ��ǡ�A��������ǡ�C�������ַ��ϼǡ�B����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
        // cell = new PdfPCell(new Phrase(rev.getPSJL31(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
        // cell = new PdfPCell(new Phrase(rev.getPSJG31(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("3.2* ����������\n"
+"    ���뼼��������Ӧ��Ϥ������ұ�׼����������ӡˢ�������߱�����ӡˢ������������������",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    ����Ҫ��ǡ�A��������ǡ�C�������ַ��ϼǡ�B����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
         //cell = new PdfPCell(new Phrase(rev.getPSJL32(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
        // cell = new PdfPCell(new Phrase(rev.getPSJG32(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("3.3  ҵ����Ա\n"
 +"    �н�ӡˢҵ�����ԱӦ�˽��������֪ʶ�͹����й����������ҵ�йع����ƶȵĹ涨��",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    ����Ҫ��ǡ�A��������ǡ�C�������ַ��ϼǡ�B����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
        // cell = new PdfPCell(new Phrase(rev.getPSJL33(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
        // cell = new PdfPCell(new Phrase(rev.getPSJG33(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("3.4* ��������Ա\n"
+"    ��Ϥ������ұ�׼����������λ�á��ߴ硢��ɫ����Ƽ���Ҫ��",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    ����Ҫ��ǡ�A��������ǡ�C�������ַ��ϼǡ�B����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
        // cell = new PdfPCell(new Phrase(rev.getPSJL34(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
         //cell = new PdfPCell(new Phrase(rev.getPSJG34(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("3.5 �ֳ�������Ա\n"
    +"    Ӧ�˽�����Ļ�������Ҫ�󣬲��߱��ֳ���������������",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    ����Ҫ��ǡ�A������֮�ǡ�C�������ַ��ϼǡ�B����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
        // cell = new PdfPCell(new Phrase(rev.getPSJL35(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
        // cell = new PdfPCell(new Phrase(rev.getPSJG35(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("3.6* ������Ա\n"
+"    ��ҵӦ�䱸������Ա��������ӡˢƷ���顣������ԱӦ��Ϥ������ұ�׼�����������������鼼����",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    �з���Ҫ�����Ա�ǡ�A��������ǡ�C�������ַ��ϼǡ�B����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
        // cell = new PdfPCell(new Phrase(rev.getPSJL36(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
        // cell = new PdfPCell(new Phrase(rev.getPSJG36(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("4* ӡˢ�豸\n"
   +"    ����ӡˢ�������Ҫ�豸���ˣ��ܹ���������ӡˢ����Ҫ����ת������",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    ���ϼǡ�A��������ǡ�C����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
         //cell = new PdfPCell(new Phrase(rev.getPSJL4(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
        // cell = new PdfPCell(new Phrase(rev.getPSJG4(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("5 ��������\n"
    +"    ͨ��ӡˢ�������飬����Ч��������ƽ���������٣�������������Ҫ��",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    ���ϼǡ�A������ȱ�ݼǡ�B��������ǡ�C����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
        // cell = new PdfPCell(new Phrase(rev.getPSJL5(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
        // cell = new PdfPCell(new Phrase(rev.getPSJG5(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("6* ӡˢƷ���\n"
    +"    �����Ʒ��������Ҫ��",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    �ϸ�Ϊ��A��������ǡ�C����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
         //cell = new PdfPCell(new Phrase(rev.getPSJL6(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
        // cell = new PdfPCell(new Phrase(rev.getPSJG6(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("7.��ҵ�ڲ�����\n"
+"7.1* �������\n"
    +"    ӵ�б�Ҫ��������ż����豸��",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    ���ϼǡ�A�������ַ��ϼǡ�B��������ǡ�C����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
         //cell = new PdfPCell(new Phrase(rev.getPSJL71(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
         //cell = new PdfPCell(new Phrase(rev.getPSJG71(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("7.2 ���鹤������Ҫ��\n"
  +"    ����׼���г����ͼ��飻�����¼��ϸ�֤Ӧ�淶ͳһ����Ŀ��ȫ���ּ�������",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    ���ϼǡ�A��������ǡ�C������ȱ�ݼǡ�B����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
         //cell = new PdfPCell(new Phrase(rev.getPSJL72(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
         //cell = new PdfPCell(new Phrase(rev.getPSJG72(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("8.�����ƶ�\n"
+"8.1��֤�ƶ�\n"
    +"    �ڳн�����ӡˢҵ��ʱ��������ί�е�λ��ȡ�й�֤�����˲�֤������Ч�ԣ�����֤����ӡ����˲��¼һ��浵���飬�����ڲ������ڶ��ꡣ",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    ���ƶȲ��ϸ�ִ�мǡ�A�������ƶȼǡ�C�������ƶȣ�ִ�в��ϼǡ�B����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
         //cell = new PdfPCell(new Phrase(rev.getPSJL81(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
         //cell = new PdfPCell(new Phrase(rev.getPSJG81(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("8.2�����������ƶ�\n"
    +"    ӡˢ����ǰҪ����Ƹ���������飬������Ʋ��ϸ�ĸ�����Ͷ���ư�ӡˢ\n",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    ���ƶȲ��ϸ�ִ�мǡ�A�������ƶȼǡ�C�������ƶȣ�ִ�в��ϼǡ�B����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
         //cell = new PdfPCell(new Phrase(rev.getPSJL82(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
         //cell = new PdfPCell(new Phrase(rev.getPSJG82(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("8.3 ����ӡˢƷ��ӡ�桢��Ƭ�����ƶ�\n"
        		   +"    ��ȷ����ӡˢ�ϸ�Ʒ�����ϸ�Ʒ���д�Ʒ��ӡ�桢��Ƭ�ĳ����Ǽǡ����ܡ��ƽ�����������͸�����Ա��",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    ���ƶȲ��ϸ�ִ�мǡ�A�������ƶȼǡ�C�������ƶȣ�ִ�в��ϼǡ�B����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
         //cell = new PdfPCell(new Phrase(rev.getPSJL83(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
         //cell = new PdfPCell(new Phrase(rev.getPSJG83(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- ������Ŀ��Ҫ��
         cell = new PdfPCell(new Phrase("8.4* ����ӡˢƷ���������ƶ�\n"
  +"    ��ȷ����ϸ���г����������鲻�ϸ�İ��Ʒ��Ͷ���µ�����������ȷ�������������Ա������δ������ϸ������ӡˢƷ��������",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ���󷽷�
         cell = new PdfPCell(new Phrase("    ���ƶȲ��ϸ�ִ�мǡ�A�������ƶȼǡ�C�������ƶȣ�ִ�в��ϼǡ�B����",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- �����¼
         //cell = new PdfPCell(new Phrase(rev.getPSJL84(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����������
         //cell = new PdfPCell(new Phrase(rev.getPSJG84(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         document.add(table1);
         
         //ע����*�ŵ���Ϊ�ص���
         paragraph =new Paragraph("                 ע����*�ŵ���Ϊ�ص���",setFont(8,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         document.add(paragraph);
         
	     //����
         for(int i=0;i<1;i++)document.add(par0);
         
         //�ۺ��ж�����
         paragraph =new Paragraph("�ۺ��ж�����",setFont(14,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //�ۺ��ж�����(
         paragraph =new Paragraph("                ������۷�Ϊ���϶����͡����϶������֡�\n"
+"                ������ҵ������ͬʱ��������Ҫ��ģ��������Ϊ���϶����������������Ϊ�����϶�����\n"
+"                1��	�ص����*�ű���ΪA���������������������C�\n"
+"                2��	ȫ�����B��ĸ������ó���6��\n"
+"            ���ص������C��ĸ������ó�����������ص������һ��C��ʱ��ȫ���г���B�����Ŀ���ó���4������ֶ���C��ʱ��ȫ���г���B�����Ŀ���ó���2�",setFont(12,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         document.add(paragraph);
         
         document.add(par0);
         //����Աǩ����
         paragraph =new Paragraph("����Աǩ����",setFont(14,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         document.add(par0);
         
         PdfPTable table2 = new PdfPTable(12);
      	 table2.setTotalWidth((float)480.0);
         table2.setLockedWidth(true);
         //------------- ����
         cell = new PdfPCell(new Phrase("����",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- ������λ
         cell = new PdfPCell(new Phrase("������λ",setFont(numTX,true)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- ְ��/ְ��
         cell = new PdfPCell(new Phrase("ְ��/ְ��",setFont(numTX,true)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- ǩ��
         cell = new PdfPCell(new Phrase("ǩ��",setFont(numTX,true)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         
         String[] id = null;  
 		 id = rev.getExpertIds().split(",");
 		 
 		 if(id!=null)
 		 for(int i=0;i<id.length;i++){
 			 
 		 System.out.println(id[i]);
 		 int userId=Integer.parseInt(id[i]);
 		 System.out.println(Integer.parseInt(id[i]));
 		 System.out.println(userId);
 		 ComUser user=comUserService.getByUserId(userId);
         //------------- ����
         cell = new PdfPCell(new Phrase(user.getUsername(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- ������λ
         cell = new PdfPCell(new Phrase(user.getUnit(),setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- ְ��/ְ��
         cell = new PdfPCell(new Phrase(user.getPost(),setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- ǩ��
         cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
 		}
         
         document.add(table2);

         
         document.newPage();
         
         PdfPTable table3 = new PdfPTable(12);
      	 table3.setTotalWidth((float)480.0);
         table3.setLockedWidth(true);
         //------------- 
         cell = new PdfPCell(new Phrase("��\n��\n��\n��\n��\n��\n��\n��",setFont(numTX,true)));
         	cell.setRowspan(2);
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(250f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase(rev.getMavinIdea(),setFont(numTX,false)));
         	cell.setColspan(10);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(200f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("�����鳤ǩ����",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("���ڣ�",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("��\n֧\n��\n��\n��\n��",setFont(numTX,true)));
         	cell.setRowspan(2);
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(250f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase(rev.getFrameworkIdea(),setFont(numTX,false)));
         	cell.setColspan(10);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(200f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("������ǩ����",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("��֧�������£�          ��    ��    ��",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("��\n��\n��\nƷ\n��\n��\n��\n��\n��\n��",setFont(numTX,true)));
         	cell.setRowspan(3);
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(250f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase(PropertyUtil.getT(),setFont(numTX,false)));
         	cell.setColspan(10);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(170f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("��׼��ǩ����",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(40f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("�������ĸ��£�",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(40f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("��׼��ǩ����",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(40f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("��    ��    ��",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(40f); 
         	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         document.add(table3);
       
         
         document.close();
	     outr.close();
		 }catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
		 }
	}
	
	
	public void createPDFComCert(CompanyInfo app,String filePath){
		//���·��
		String fileDesc = "";
		// ���������	
		FileOutputStream outr = null;
		fileDesc=filePath;
		 
		
		 Certposition cert = certService.getUsing();
		 //�������ݷ���
		 RectangleReadOnly rectangleReadOnly = new RectangleReadOnly(1191f, 842f);
		 Document document=new Document(rectangleReadOnly,195+cert.getLeftPadding(), 250+cert.getRightPadding(),200+cert.getUp(), 180+cert.getDown() );
		 try{
		 outr= new FileOutputStream(fileDesc);
         PdfWriter writer = PdfWriter.getInstance(document, outr);
         document.open();
         //document.newPage();
         PdfContentByte canvas = writer.getDirectContent();
         Rectangle rect;
         PdfFormField field;
         //Font font = new Font(baseFontChinese , fontSize , Font.NORMAL, BaseColor.BLACK);
         //��ҵ����
         Font font1= new Font(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED),40,Font.BOLD,BaseColor.BLACK);
         //����������
         Font font2= new Font(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED),25,Font.BOLD,BaseColor.BLACK);
         //����
         Font font3= new Font(BaseFont.createFont("Times-Roman","Cp1252", BaseFont.NOT_EMBEDDED),25);
         //����
         Font font5= new Font(BaseFont.createFont("Times-Roman","Cp1252", BaseFont.NOT_EMBEDDED),25,Font.BOLD,BaseColor.BLACK);
         //Ӣ������
         Font font4= new Font(BaseFont.createFont("Times-Roman","Cp1252", BaseFont.NOT_EMBEDDED),17,Font.BOLD,BaseColor.BLACK);
         Chunk chunk;
         Paragraph paragraph;
         
         PdfPCell cell;
         
         Paragraph par0=new Paragraph("\r\n",setFont(10,true));
	     par0.setAlignment(Element.ALIGN_CENTER);
	     Paragraph parh=new Paragraph("\r\n",setFont(5,true));
	     parh.setAlignment(Element.ALIGN_CENTER);
	     Paragraph par1=new Paragraph("\r\n",setFont(5,true));
	     par1.setAlignment(Element.ALIGN_CENTER);
	     //����
         for(int i=0;i<11;i++)document.add(par0);
	     
         //֤���
//	     paragraph =new Paragraph(app.getCertificateno(),setFont(24,false));
	     paragraph =new Paragraph(app.getRealSerial(),font5);
	     paragraph.setSpacingBefore(5);
         paragraph.setAlignment(Element.ALIGN_RIGHT);
         document.add(paragraph);   
         //��ҵ����
//         paragraph =new Paragraph(app.getEnterprisename(),setFont(37,true));
         paragraph =new Paragraph(app.getEnterprisename(),font1);
         paragraph.setAlignment(Element.ALIGN_LEFT);
         //��������
         paragraph.setIndentationLeft(10); 
         document.add(paragraph);
         //Ӣ������
         paragraph =new Paragraph(app.getEnglishname(),font4);
         paragraph.setAlignment(Element.ALIGN_LEFT);
         //��������
         paragraph.setIndentationLeft(10);
         document.add(paragraph);
//         for(int i=0;i<11;i++)document.add(par1);
         for(int i=0;i<23;i++)document.add(par1);
         //����
         //����
//         for(int i=0;i<1;i++)document.add(par0);
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy        MM      dd"); 
         String str="                              ";
         if(app.getCertappdate()!=null)
             str+= sdf.format(app.getCertappdate());
    	 else
    		 str+= " ";
         str+="   ";
         if(app.getCerttodate()!=null)
             str+= "    "+sdf.format(app.getCerttodate());
    	 else
    		 str+= "   ";
//         paragraph=new Paragraph(str,setFont(24,false));
         paragraph=new Paragraph(str,font5);
         paragraph.setAlignment(Element.ALIGN_LEFT);
         paragraph.setPaddingTop(15);
         document.add(paragraph);
         //����
         //for(int i=0;i<1;i++)document.add(par0);
         //������
         Division div=divisionService.getByDivCode("0000");
         paragraph=new Paragraph("                                            "+div.getDivisionname()+app.getBranchName(),font2);
         paragraph.setAlignment(Element.ALIGN_LEFT);
         paragraph.setSpacingBefore(15);
         document.add(paragraph);
         
         for(int i=0;i<7;i++)document.add(par1);
         
         str="";
         if(app.getCreatedate()!=null)
             str = sdf.format(app.getCreatedate());
    	 else
    		 str = " ";
//         paragraph=new Paragraph(str,setFont(24,false));
         paragraph=new Paragraph(str,font5);
//         paragraph.setAlignment(Element.ALIGN_RIGHT);
         //��������
         paragraph.setIndentationLeft(700);  
         document.add(paragraph);
         
         document.close();
	     outr.close();
		 }catch (Exception e) {
				e.printStackTrace();
		 }
	}

	public void createPDFComCertFB(CompanyInfo app,String filePath){
		//���·��
		String fileDesc = "";
		// ���������	
		FileOutputStream outr = null;
		fileDesc=filePath;
		 
		 Certposition cert = certService.getUsing();
		
		 
//		 Document document=new Document(new RectangleReadOnly(708F,498F), 100+cert.getLeftPadding(), 100+cert.getRightPadding(),350+cert.getUp(), 150+cert.getDown());
		 Document document=new Document(new RectangleReadOnly(708F,498F), 110+cert.getLeftPadding(), 90+cert.getRightPadding(),140+cert.getUp(), 150+cert.getDown());
		 try{
		 outr= new FileOutputStream(fileDesc);
         PdfWriter writer = PdfWriter.getInstance(document, outr);
         document.open();
         //document.newPage();
         
         PdfContentByte canvas = writer.getDirectContent();
         Rectangle rect;
         PdfFormField field;
         //Font font = new Font(baseFontChinese , fontSize , Font.NORMAL, BaseColor.BLACK);
       //����
         Font font1= new Font(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED),14);
         //���+����
         Font font3= new Font(BaseFont.createFont("Times-Roman","Cp1252", BaseFont.NOT_EMBEDDED),12);
         //Ӣ������
         Font font4= new Font(BaseFont.createFont("Times-Roman","Cp1252", BaseFont.NOT_EMBEDDED),10,Font.BOLD,BaseColor.BLACK);
         //��������
         Font font2= new Font(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED),14,Font.BOLD,BaseColor.BLACK);
         Chunk chunk;
         int border=0;
         Paragraph paragraph;
         
         PdfPCell cell;
         
         Paragraph par0=new Paragraph("\r\n",setFont(10,true));
	     par0.setAlignment(Element.ALIGN_CENTER);
	     
         PdfPTable table1 = new PdfPTable(12);
      	 table1.setTotalWidth((float)660.0);
         table1.setLockedWidth(true);
         //------------- ��ҵ����
         Phrase phrase1= new Phrase(app.getEnterprisename()+"\n"+app.getEnglishname(),font2);
         Phrase phrase2= new Phrase();
         phrase2.add(new Chunk(app.getEnterprisename(), font2));  
         phrase2.add("\n");  
         phrase2.add(new Chunk(app.getEnglishname(), font4));  
         cell = new PdfPCell(phrase2);
         	cell.setRowspan(2);
         	cell.setColspan(4);
         	cell.setBorderWidth(border);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(160f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
         	cell.setPaddingLeft(15f);
         table1.addCell(cell);
         //------------- ֤���
         cell = new PdfPCell(new Phrase(app.getRealSerial(),font3));
         	cell.setRowspan(2);
         	cell.setColspan(4);
         	cell.setBorderWidth(border);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(160f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
         	cell.setPaddingBottom(32f);
         	cell.setPaddingLeft(10f);
         table1.addCell(cell);
         //------------- ��ַ
         String address=app.getAddress();
         System.out.println(address.length());
         if(address.length()>16) {
        	 	address=address.substring(0, 14)+Print.hh+address.substring(14,address.length());
         }
         cell = new PdfPCell(new Phrase(address,font1));
         	cell.setColspan(4);
         	cell.setBorderWidth(border);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(80f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	cell.setPaddingBottom(4f);
         table1.addCell(cell);
         //------------- ����
         cell = new PdfPCell(new Phrase(app.getArtificialperson(),font1));
         	cell.setColspan(4);
         	cell.setBorderWidth(border);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(80f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	cell.setPaddingBottom(12f);
         table1.addCell(cell);
         
         //------------- ��Ч��
         //����
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy       MM     dd    "); 
         String str="";
         if(app.getCertappdate()!=null)
             str+= sdf.format(app.getCertappdate());
    	 else
    		 str+= " ";
         str+="\n\n";
         if(app.getCerttodate()!=null)
             str+= sdf.format(app.getCerttodate());
    	 else
    		 str+= " ";
         
         cell = new PdfPCell(new Phrase(str,font3));
         	cell.setRowspan(2);
         	cell.setColspan(4);
         	cell.setBorderWidth(border);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(160f); 
         	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
         	cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
         	cell.setPaddingTop(120f);
         	cell.setPaddingRight(15f);
         table1.addCell(cell);
         //------------- ��
         cell = new PdfPCell(new Phrase(" ",font1));
         	cell.setRowspan(2);
         	cell.setColspan(4);
         	cell.setBorderWidth(border);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(160f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- ����
         String tec="";
         if(app.getFlat()!=null)tec+=Print.pbys;
         if(app.getGravure()!=null)tec+=Print.abys;
         if(app.getWebby()!=null)tec+=Print.swys;
         if(app.getFlexible()!=null)tec+=Print.rxys;
         if(app.getElsetype()!="")tec+=Print.qt+app.getElsetype();
         cell = new PdfPCell(new Phrase(tec,font1));
         	cell.setColspan(4);
         	cell.setBorderWidth(border);
         	cell.setFixedHeight(80f); 
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	cell.setPaddingBottom(8f);
         table1.addCell(cell);
         //------------- ����
         String mat="";
         if(app.getPapery()!=null)mat+=Print.zz;
         if(app.getPastern()!=null)mat+=Print.bgj;
         if(app.getFrilling()!=null)mat+=Print.wlz;
         if(app.getMetal()!=null)mat+=Print.js;
         if(app.getPlastic()!=null)mat+=Print.sl;
         if(app.getElsematerial()!="")mat+=Print.qqt+app.getElsematerial();
         cell = new PdfPCell(new Phrase(mat,font1));
         	cell.setColspan(4);
         	cell.setBorderWidth(border);
         	cell.setFixedHeight(80f); 
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	cell.setPaddingBottom(10f);
         table1.addCell(cell);
         //------------- ��֤����
         str="";
         SimpleDateFormat sdf2=new SimpleDateFormat("yyyy          MM         dd  "); 
         if(app.getCreatedate()!=null)
             str = sdf2.format(app.getCreatedate());
    	 else
    		 str = " ";
         cell = new PdfPCell(new Phrase(str,font3));
         	cell.setRowspan(2);
         	cell.setColspan(5);
         	cell.setBorderWidth(border);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(100f); 
         	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
         	cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
         	cell.setPaddingBottom(18f);
         	cell.setPaddingRight(4f);
         table1.addCell(cell);
         //------------- ��
         cell = new PdfPCell(new Phrase(" ",font1));
         	cell.setRowspan(2);
         	cell.setColspan(3);
         	cell.setBorderWidth(border);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(100f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);

         //------------- ��֧����
         Division div=divisionService.getByDivCode("0000");
         cell = new PdfPCell(new Phrase(div.getDivisionname()+app.getBranchName(),font1));
         	cell.setColspan(4);
         	cell.setBorderWidth(border);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(80f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	cell.setPaddingBottom(26f);
         table1.addCell(cell);
         //------------- ��
         cell = new PdfPCell(new Phrase(" ",font1));
         	cell.setColspan(4);
         	cell.setBorderWidth(border);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(20f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         document.add(table1);
         
         document.close();
	     outr.close();
		 }catch (Exception e) {
				e.printStackTrace();
		 }
	}
	public void concatPDFs(List<InputStream> streamOfPDFFiles,
			 OutputStream outputStream, boolean paginate,int type) {
//		Certposition cert = certService.getUsing();
        Document document = null;
        if(type==1){
        	document =new Document(new RectangleReadOnly(1191F,842F));
        }else{
        	document =new Document(new RectangleReadOnly(708F,498F));
        }
        
        try {
            List<InputStream> pdfs = streamOfPDFFiles;
            List<PdfReader> readers = new ArrayList<PdfReader>();
            int totalPages = 0;
            Iterator<InputStream> iteratorPDFs = pdfs.iterator();

            // Create Readers for the pdfs.
            while (iteratorPDFs.hasNext()) {
                InputStream pdf = iteratorPDFs.next();
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
            // Create a writer for the outputstream
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            document.open();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
                    BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
            // data

            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();

            // Loop through the PDF files and add to the output.
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();

                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader,
                            pageOfCurrentReaderPDF);
                    cb.addTemplate(page, 0, 0);

                    // Code for pagination.
                    if (paginate) {
                        cb.beginText();
                        cb.setFontAndSize(bf, 9);
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, ""
                                + currentPageNumber + " of " + totalPages, 520,
                                5, 0);
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            outputStream.flush();
            document.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen())
                document.close();
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

	public void createPDFAlteration(FormChange form, String filePath,int certNo) {
		//���·��
				String fileDesc = "";
				// ���������	
				FileOutputStream outr = null;
	
				fileDesc=filePath;
				//�����
				 int numD=20;
				 
				 //С����
				 int numX=14;
				 
				 //����
				 int numTD=14;
				 
				 //���С
				 int numTX=14;
				 
				 //�����
				 int numM=13;
				 
				 Document document=new Document();
				 
				 try{
				 outr= new FileOutputStream(fileDesc);
		         PdfWriter writer = PdfWriter.getInstance(document, outr);
		         document.open();
		         //document.newPage();
		         
		         PdfContentByte canvas = writer.getDirectContent();
		         Rectangle rect;
		         PdfFormField field;
		         //Font font = new Font(baseFontChinese , fontSize , Font.NORMAL, BaseColor.BLACK);
		         
		         Chunk chunk;
		         
		         Paragraph paragraph;
		         
		         PdfPCell cell;
		         
		         Paragraph par0=new Paragraph("\r\n",setFont(numX,true));
			     par0.setAlignment(Element.ALIGN_CENTER);
			     
			     paragraph =new Paragraph("��� "+form.getTitleno(),setFont(10,false));
		         paragraph.setAlignment(Element.ALIGN_RIGHT);
		         document.add(paragraph);
			     
		         //����
		         for(int i=0;i<4;i++)document.add(par0);
		         
		         
		         //��  Ʒ  ��  ��  ӡ  ˢ  ��  ��
		         paragraph =new Paragraph("��  Ʒ  ��  ��  ӡ  ˢ  ��  ��",setFont(numD,false));
		         paragraph.setAlignment(Element.ALIGN_CENTER);
		         document.add(paragraph);
		         
			     document.add(par0);
		         
			     //�� �� ��
			     paragraph=new Paragraph("�� �� �� �� ��",setFont(26,true));
			     paragraph.setAlignment(Element.ALIGN_CENTER);
			     document.add(paragraph);
		         
			     //����
		         for(int i=0;i<12;i++)document.add(par0);
		         
		         //������ҵ����
		         String entName = String.format("%-20s", form.getCompanynameOld());
		         paragraph=new Paragraph("                                                     ������ҵ���ƣ�  ",setFont(numX,false));
			       
		         chunk = new Chunk(entName,setFontUnderline(numX,false));
		         paragraph.add(chunk);
		         
		         chunk = new Chunk("(����)",setFontUnderline(numX,false));
		         paragraph.add(chunk);
		         
		         paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
			     
			     document.add(par0);
			     
			     //��   ��   ��   ��
			     SimpleDateFormat sdf0=new SimpleDateFormat("yyyy-MM-dd");   
		         String str = String.format("%-28s", sdf0.format(form.getCreatedate()));
			     
			     //paragraph=new Paragraph("                                                      ��   ��   ��   �ڣ�  "+str,setFontUnderline(numX,true));
			     paragraph=new Paragraph("                                                      ��   ��   ��   �ڣ�  ",setFont(numX,false));
		         
		         chunk = new Chunk(str,setFontUnderline(numX,false));
		         paragraph.add(chunk);
		         
			     paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
			        
			     //����
		         for(int i=0;i<10;i++)document.add(par0);
			     
		         paragraph=new Paragraph("�й���Ʒ����������",setFont(numX,false));
		         paragraph.setAlignment(Element.ALIGN_CENTER);
		         document.add(paragraph);
		         
		         //�ڶ�ҳ
		         document.newPage();
			     
			     
			     
			     
			     
		         //����
		         for(int i=0;i<4;i++)document.add(par0);
		         
		         
		         //����ӡˢ�ʸ��϶���ҵ��Ϣ����ǼǱ�
		         paragraph =new Paragraph("����ӡˢ�ʸ��϶���ҵ��Ϣ����ǼǱ�",setFont(numD,false));
		         paragraph.setAlignment(Element.ALIGN_CENTER);
		         document.add(paragraph);
		         
			     document.add(par0);
		         
			     //֤���
			     paragraph=new Paragraph("֤���: ���ӡ֤�� "+certNo+" ��",setFont(14,false));
			     paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
			     document.add(par0);
			     //��֤��Ч��
			     SimpleDateFormat sdf=new SimpleDateFormat("yyyy��MM��dd��"); 
			     paragraph=new Paragraph("��֤��Ч��: "+form.getCertappdateOld()+"��"+form.getCerttodateOld(),setFont(14,false));
			     paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
			     document.add(par0);
			     //�������
			     paragraph=new Paragraph("�������: "+sdf.format(form.getCreatedate()),setFont(14,false));
			     paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
			     document.add(par0);
		         	PdfPTable table1 = new PdfPTable(12);
		         	table1.setTotalWidth((float)520.0);
		            table1.setLockedWidth(true);
		            //------------- �����Ŀ
		            cell = new PdfPCell(new Phrase("�����Ŀ",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- ԭ����
		            cell = new PdfPCell(new Phrase("ԭ����",setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- �������
		            cell = new PdfPCell(new Phrase("�������",setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- ��ҵ����
		            cell = new PdfPCell(new Phrase("��ҵ����",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- ԭ����
		            cell = new PdfPCell(new Phrase(form.getCompanynameOld(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- �������
		            cell = new PdfPCell(new Phrase(form.getCompanynameNew(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- ���˴���
		            cell = new PdfPCell(new Phrase("���˴���",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- ԭ����
		            cell = new PdfPCell(new Phrase(form.getCoporationOld(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- �������
		            cell = new PdfPCell(new Phrase(form.getCoporationNew(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- ��ַ
		            cell = new PdfPCell(new Phrase("��ַ",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- ԭ����
		            cell = new PdfPCell(new Phrase(form.getAddressOld(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- �������
		            cell = new PdfPCell(new Phrase(form.getAddressNew(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- �ʱ�
		            cell = new PdfPCell(new Phrase("�ʱ�",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- ԭ����
		            cell = new PdfPCell(new Phrase(form.getPostcodeOld(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- �������
		            cell = new PdfPCell(new Phrase(form.getPostcodeNew(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- �绰
		            cell = new PdfPCell(new Phrase("�绰",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- ԭ����
		            cell = new PdfPCell(new Phrase(form.getLinkmantelOld(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- �������
		            cell = new PdfPCell(new Phrase(form.getLinkmantelNew(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- ����
		            cell = new PdfPCell(new Phrase("����",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- ԭ����
		            cell = new PdfPCell(new Phrase("",setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- �������
		            cell = new PdfPCell(new Phrase("",setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- ��ϵ��
		            cell = new PdfPCell(new Phrase("��ϵ��",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- ԭ����
		            cell = new PdfPCell(new Phrase(form.getLinkmanOld(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- �������
		            cell = new PdfPCell(new Phrase(form.getLinkmanNew(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- �����ǩ��
		            cell = new PdfPCell(new Phrase("�����ǩ��",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 
		            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- ������ǩ��
		            cell = new PdfPCell(new Phrase("������ǩ��",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //-------------
		            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
		            	cell.setColspan(3);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- ��ע
		            cell = new PdfPCell(new Phrase("��ע",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 
		            cell = new PdfPCell(new Phrase("",setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 
		            cell = new PdfPCell(new Phrase("",setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);

		         	document.add(table1); 
		         document.add(par0);
		         document.add(par0);
		       //������ҵ����
			     paragraph=new Paragraph("������ҵ����                                 ��        ��        ��",setFont(14,false));
			     paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
			     
			     document.add(par0);
			     document.add(par0);
			     document.add(par0);
			   //��֧��������
			     paragraph=new Paragraph("��֧��������                                 ��        ��        ��",setFont(14,false));
			     paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
		       
		         document.close();
			     outr.close();
				 }catch (Exception e) {
						e.printStackTrace();
				 }
		
	}
//��ӡ������Ϣ��
//	public void createPDFSendmsg(ExpressInfo express, String filePath,String companyName) {
//		//���·��
//		String fileDesc = "";
//		// ���������	
//		FileOutputStream outr = null;
//
//		fileDesc=filePath;
//		//�����
//		 int numD=20;
//		 
//		 //С����
//		 int numX=14;
//		 
//		 //����
//		 int numTD=14;
//		 
//		 //���С
//		 int numTX=14;
//		 
//		 //�����
//		 int numM=13;
//		 
//		 Document document=new Document();
//		 
//		 try{
//		 outr= new FileOutputStream(fileDesc);
//         PdfWriter writer = PdfWriter.getInstance(document, outr);
//         document.open();
//         //document.newPage();
//         
//         PdfContentByte canvas = writer.getDirectContent();
//         Rectangle rect;
//         PdfFormField field;
//         //Font font = new Font(baseFontChinese , fontSize , Font.NORMAL, BaseColor.BLACK);
//         
//         Chunk chunk;
//         
//         Paragraph paragraph;
//         
//         PdfPCell cell;
//         
//         Paragraph par0=new Paragraph("\r\n",setFont(numX,true));
//	     par0.setAlignment(Element.ALIGN_CENTER);
//	     
//         for(int i=0;i<4;i++)document.add(par0);
//         
//         
//         //������Ϣ
//         paragraph =new Paragraph(companyName+"������Ϣ",setFont(numD,false));
//         paragraph.setAlignment(Element.ALIGN_CENTER);
//         document.add(paragraph);
//         
//	     document.add(par0);
//         
//	     //��ӡʱ��
//	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy��MM��dd��"); 
//	     paragraph=new Paragraph("ʱ��: "+sdf.format(new Date()),setFont(14,false));
//	     paragraph.setAlignment(Element.ALIGN_LEFT);
//	     document.add(paragraph);
//	     document.add(par0);
//	     //�ļ��ˣ�
//	     paragraph=new Paragraph("�ļ���: ",setFont(14,false));
//	     paragraph.setAlignment(Element.ALIGN_LEFT);
//	     document.add(paragraph);
//	     document.add(par0);
//	     //��һ�����
//         	PdfPTable table1 = new PdfPTable(12);
//         	table1.setTotalWidth((float)520.0);
//            table1.setLockedWidth(true);
//            //------------- �������
//            cell = new PdfPCell(new Phrase("�������",setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//            //------------- ������
//            cell = new PdfPCell(new Phrase("������",setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//            //------------- ��ϵ��
//            cell = new PdfPCell(new Phrase("��ϵ��",setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//          //------------- ��ϵ�˵绰
//            cell = new PdfPCell(new Phrase("��ϵ�˵绰",setFont(numTX,false)));
//        	cell.setColspan(2);
//        	cell.setUseAscender(true);
//        	cell.setFixedHeight(25f); 
//        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        	table1.addCell(cell);
//         //------------- ��ϵ�˵�ַ
//	        cell = new PdfPCell(new Phrase("��ϵ�˵�ַ",setFont(numTX,false)));
//	    	cell.setColspan(4);
//	    	cell.setUseAscender(true);
//	    	cell.setFixedHeight(25f); 
//	    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	    	table1.addCell(cell);
//          //------------- ����
//            cell = new PdfPCell(new Phrase(express.getName(),setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//            //------------- ������
//            cell = new PdfPCell(new Phrase(express.getNumber(),setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//            //------------- ��ϵ��
//            cell = new PdfPCell(new Phrase(express.getContact(),setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//          //------------- ��ϵ�˵绰
//            cell = new PdfPCell(new Phrase(express.getPhoneNumber(),setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//          //------------- ��ϵ�˵�ַ
//            cell = new PdfPCell(new Phrase(express.getConAddress(),setFont(numTX,false)));
//            	cell.setColspan(4);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//            document.add(table1);
//            document.add(par0);
//          //�ռ��ˣ�
//   	     paragraph=new Paragraph("�ռ���: ",setFont(14,false));
//   	     paragraph.setAlignment(Element.ALIGN_LEFT);
//   	     document.add(paragraph);
//   	     document.add(par0);
//   	     //�ڶ������
//        PdfPTable table2 = new PdfPTable(12);
//     	table1.setTotalWidth((float)520.0);
//        table1.setLockedWidth(true);
//      //------------- �ռ���
//        cell = new PdfPCell(new Phrase("�ռ�������",setFont(numTX,false)));
//        	cell.setColspan(4);
//        	cell.setUseAscender(true);
//        	cell.setFixedHeight(25f); 
//        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table2.addCell(cell);
//        //------------- �ռ��˵绰
//        cell = new PdfPCell(new Phrase("�ռ��˵绰",setFont(numTX,false)));
//        	cell.setColspan(4);
//        	cell.setUseAscender(true);
//        	cell.setFixedHeight(25f); 
//        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table2.addCell(cell);
//        //------------- �ռ��˵�ַ
//        cell = new PdfPCell(new Phrase("�ռ��˵�ַ",setFont(numTX,false)));
//        	cell.setColspan(4);
//        	cell.setUseAscender(true);
//        	cell.setFixedHeight(25f); 
//        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table2.addCell(cell);
//      //------------- �ռ���
//        cell = new PdfPCell(new Phrase(express.getReciveName(),setFont(numTX,false)));
//        	cell.setColspan(4);
//        	cell.setUseAscender(true);
//        	cell.setFixedHeight(25f); 
//        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table2.addCell(cell);
//        //------------- �ռ��˵绰
//        cell = new PdfPCell(new Phrase(express.getRecivePhoneNum(),setFont(numTX,false)));
//        	cell.setColspan(4);
//        	cell.setUseAscender(true);
//        	cell.setFixedHeight(25f); 
//        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table2.addCell(cell);
//        //------------- �ռ��˵�ַ
//        cell = new PdfPCell(new Phrase(express.getReciveAddress(),setFont(numTX,false)));
//        	cell.setColspan(4);
//        	cell.setUseAscender(true);
//        	cell.setFixedHeight(25f); 
//        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table2.addCell(cell);
//         document.add(table2);
//         document.add(par0);
//         document.add(par0);
//       
//         document.close();
//	     outr.close();
//		 }catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//		 }
//	}
	
	
	
	//������Ϣ��ӡ
	public void createPDFSendmsg(ExpressInfo express, String filePath,String companyName, String sendUnit, String reciveUnit) {
		//���·��
		String fileDesc = "";
		// ���������	
		FileOutputStream outr = null;
		fileDesc=filePath;
		
//		 Certposition cert = certService.getUsing();
		//���ñ߾�(��������)
//		Rectangle pageSize = new Rectangle(1180,630);
//		 Document document=new Document(pageSize, 180, 120,0,0);
		 Rectangle pageSize = new Rectangle(595f,367f);
		 Document document=new Document(pageSize, 96f, 23f,79f,123f);
		 
		 try{
		 outr= new FileOutputStream(fileDesc);
         PdfWriter writer = PdfWriter.getInstance(document, outr);
         document.open();
         //document.newPage();
         
         PdfContentByte canvas = writer.getDirectContent();
         Rectangle rect;
         PdfFormField field;
         //Font font = new Font(baseFontChinese , fontSize , Font.NORMAL, BaseColor.BLACK);
         
         Chunk chunk;
         
         Paragraph paragraph;
         
         PdfPCell cell;
         
         Paragraph par0=new Paragraph("\r\n",setFont(10,true));
	     par0.setAlignment(Element.ALIGN_CENTER);
         
	     //��8��
//         for(int i=0;i<8;i++)document.add(par0);
	     
         //�ļ�������+�绰
	     paragraph =new Paragraph(express.getContact()+"                                                       "+express.getPhoneNumber(),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         document.add(paragraph);   
         
         //��1��
         for(int i=0;i<1;i++)document.add(par0);
         
         //�ļ��˵�λ+�ļ��˵�ַ
         String str=sendUnit+"\n"+express.getConAddress();
         
         paragraph =new Paragraph(str,setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         document.add(paragraph); 
         
//         paragraph =new Paragraph(sendUnit,setFont(16,false));
//         paragraph.setAlignment(Element.ALIGN_LEFT);
//         document.add(paragraph); 
         
         //��1��
//         for(int i=0;i<1;i++)document.add(par0);
         
         //�ļ��˵�ַ
//         paragraph =new Paragraph(express.getConAddress(),setFont(16,false));
//         paragraph.setAlignment(Element.ALIGN_LEFT);
//         document.add(paragraph);
         
         //��5��
         for(int i=0;i<3;i++)document.add(par0);
         
         //�ռ�������+�绰
         paragraph =new Paragraph(express.getReciveName()+"                                                        "+express.getRecivePhoneNum(),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         paragraph.setSpacingAfter(0);
         document.add(paragraph);
         
//        //��1��
//         for(int i=0;i<1;i++)document.add(par0);
         //ʱ��
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy          MM          dd          hh"); 
         paragraph=new Paragraph(sdf.format(new Date()),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_RIGHT);
//         paragraph.setIndentationLeft(355); 
         paragraph.setSpacingAfter(0);
         paragraph.setSpacingBefore(0); 
         document.add(paragraph);
         
         //�ռ��˵�λ
         paragraph=new Paragraph(sendUnit+reciveUnit,setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
//         paragraph.setPaddingTop(-5f);
         document.add(paragraph);
         
         //�ռ��˵�ַ
         paragraph =new Paragraph(express.getReciveAddress(),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         document.add(paragraph);
	     
         document.close();
	     outr.close();
		 }catch (Exception e) {
				e.printStackTrace();
		 }
	}
	public static void main(String[] args) {
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy        MM     dd    "); 
		 System.out.println(sdf.format(new Date())+"ʱ��");
	}
	
}