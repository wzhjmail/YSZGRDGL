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

	@PostConstruct  //@PostConstruct和@PreDestroy方法实现初始化和销毁bean之前进行操作
    public void init() {  
		divisionService=divisionServices;
		certService=certServices;
		sysUserService=sysUserServices;
		comUserService=comUserServices;
		reviewFormPartService=reviewFormPartServices;
		
    } 
	
	public static final String[] YSJS = { "平板胶印", "凹版印刷", "丝网印刷", "柔性版印刷"};
	public static final String[] YSZT = { "纸质", "不干胶", "瓦楞纸", "金属","塑料"};
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
		//输出路径
		String fileDesc = "";
		// 创建输出流	
		FileOutputStream outr = null;	
		fileDesc=filePath;
		//大标题
		 int numD=20;
		 
		 //小标题
		 int numX=14;
		 
		 //表格大
		 int numTD=14;
		 
		 //表格小
		 int numTX=12;
		 
		 //表格中
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
         
         paragraph =new Paragraph("中  国  商  品  条  码  系  统  成  员",setFont(numD,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
	     document.add(par0);
         
	     paragraph=new Paragraph("注 册 申 请 表",setFont(numX,false));
	     paragraph.setAlignment(Element.ALIGN_CENTER);
	     document.add(paragraph);
         
	     //空行
         for(int i=0;i<16;i++)document.add(par0);
         
         //约定事项
         PdfPTable table0=new PdfPTable(1);;
	     //table.setWidth(210);
         PdfPCell cell11=new PdfPCell(new Phrase("约定事项：\n"+
	        		"1.厂商识别代码有效期为二年。有效期满后需继续使用厂商识别代码的应"
	        		+"在《中国商品条码系统成员证书》有效期满前的三个月内,持企业法人营"
	        		+"业执照或营业执照及《中国商品条码系统成员证书》到所在地编码分支"
	        		+"机构办理续展手续；\n"
	        		+"2.企业不履行《商品条码管理办法》等有关规定所造成的一切损失由企业"
	        		+"自行承担地编码分支机构办理续展手续。逾期未办理续展手续的，注销"
	        		+"其厂商识别代码；\n"
	        		+"3. 为了帮助系统成员更好地应用商品条码，提供相关产品或电子商务等"
	        		+"技术服务及贸易流通服务，中国物品编码中心对系统成员信息（除注册"
	        		+"资金外）可向从事物品编码或 EDI 领域的组织或商贸公司公开。如果贵"
	        		+"公司负责人不希望公开企业信息，不接收除中国物品编码中心以外单位"
	        		+"的信件、电子邮件或电话，请在下面□中划“√” 。如果下面□中无"
	        		+"“√” ，表示贵公司负责人对上述内容不反对。",setFont(numM,false)));
         cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
         cell11.setVerticalAlignment(Element.ALIGN_CENTER);
	        
         table0.addCell(cell11);
         document.add(table0);
	        
         paragraph=new Paragraph("中国物品编码中心制",setFont(numX,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //第二页
         document.newPage();
         
         //空行
         for(int i=0;i<1;i++)document.add(par0);
         
         //一、企业信息
         paragraph=new Paragraph("一、企业信息",setFont(numX,true));
         	paragraph.setAlignment(Element.ALIGN_LEFT);
         	document.add(paragraph);
         	document.add(par0);
         	
         	PdfPTable table1 = new PdfPTable(12);
         	
         	table1.setTotalWidth((float)530.0);
            table1.setLockedWidth(true);
            //------------- 企业名称(中文)
            cell = new PdfPCell(new Phrase("企业名称(中文)",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 企业名称(中文)值
            cell = new PdfPCell(new Phrase(app.getEnterprisename(),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 企业名称(英文)
            cell = new PdfPCell(new Phrase("企业名称(英文)",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 企业名称(英文)值
            cell = new PdfPCell(new Phrase(app.getEnglishname(),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 详细地址
            cell = new PdfPCell(new Phrase("详细地址",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 详细地址值
            cell = new PdfPCell(new Phrase(app.getAddress(),setFont(numTX,false)));
            	cell.setColspan(10);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 印刷经营许可证号码
            cell = new PdfPCell(new Phrase("印刷经营许可证号码",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 印刷经营许可证号码值
            cell = new PdfPCell(new Phrase(app.getCertificateno(),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 邮政编码
            cell = new PdfPCell(new Phrase("邮政编码",setFont(numTX,false)));
            	cell.setColspan(1);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 邮政编码值
            cell = new PdfPCell(new Phrase(app.getPostalcode(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 传真
            cell = new PdfPCell(new Phrase("传真",setFont(numTX,false)));
            	cell.setColspan(1);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 传真值
            cell = new PdfPCell(new Phrase(app.getFax(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
          //------------- 法人代表
            cell = new PdfPCell(new Phrase("法人代表",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 法人代表值
            cell = new PdfPCell(new Phrase(app.getArtificialperson(),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务
            cell = new PdfPCell(new Phrase("职务",setFont(numTX,false)));
            	cell.setColspan(1);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务值
            cell = new PdfPCell(new Phrase(app.getApjob(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 电话
            cell = new PdfPCell(new Phrase("电话",setFont(numTX,false)));
            	cell.setColspan(1);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 电话值
            cell = new PdfPCell(new Phrase(app.getAptel(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 联系人
            cell = new PdfPCell(new Phrase("联系人",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 联系人值
            cell = new PdfPCell(new Phrase(app.getLinkman(),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务
            cell = new PdfPCell(new Phrase("职务",setFont(numTX,false)));
            	cell.setColspan(1);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务值
            cell = new PdfPCell(new Phrase(app.getLjob(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 电话
            cell = new PdfPCell(new Phrase("电话",setFont(numTX,false)));
            	cell.setColspan(1);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 电话值
            cell = new PdfPCell(new Phrase(app.getLtel(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
         document.add(table1);   
         
         //空行
         for(int i=0;i<3;i++)document.add(par0);
         
         //二、经营范围
         paragraph=new Paragraph("二、经营范围",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            
            PdfPTable table2 = new PdfPTable(12);
         	table2.setTotalWidth((float)530.0);
            table2.setLockedWidth(true);
            
            //------------- 主营
            cell = new PdfPCell(new Phrase("主营",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table2.addCell(cell);
            //------------- 主营值
            cell = new PdfPCell(new Phrase(app.getMainbusiness(),setFont(numTX,false)));
            	cell.setColspan(10);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table2.addCell(cell);
            //------------- 兼营
            cell = new PdfPCell(new Phrase("兼营",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table2.addCell(cell);
            //------------- 兼营值
            cell = new PdfPCell(new Phrase(app.getRestbusiness(),setFont(numTX,false)));
            	cell.setColspan(10);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table2.addCell(cell);
         document.add(table2);
         
         //空行
         for(int i=0;i<3;i++)document.add(par0);
         
         //三、人员情况
         paragraph=new Paragraph("三、人员情况",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            
            PdfPTable table3 = new PdfPTable(12);
         	table3.setTotalWidth((float)530.0);
            table3.setLockedWidth(true);
            
            //------------- 职工总数
            cell = new PdfPCell(new Phrase("职工总数",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
            //------------- 职工总数值
            cell = new PdfPCell(new Phrase(app.getEmployeetotal() == null?" ":Integer.toString(app.getEmployeetotal()),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
            //------------- 技术人员
            cell = new PdfPCell(new Phrase("技术人员",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
                //------------- 技术人员值
            cell = new PdfPCell(new Phrase(app.getTechniciantotal() == null?" ":Integer.toString(app.getTechniciantotal()),setFont(numTX,false)));
                cell.setColspan(4);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
            //------------- 条码印刷技术负责人
            cell = new PdfPCell(new Phrase("条码印刷技术负责人",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
                //------------- 条码印刷技术负责人值
            cell = new PdfPCell(new Phrase(app.getBcprincipal(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
            //------------- 职务
            cell = new PdfPCell(new Phrase("职务",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
                //------------- 职务值
            cell = new PdfPCell(new Phrase(app.getTpbusiness(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
            //------------- 职称
            cell = new PdfPCell(new Phrase("职称",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
                //------------- 职称值
            cell = new PdfPCell(new Phrase(app.getTpopost(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table3.addCell(cell);
         document.add(table3);
         
         document.newPage();
         
         //空行
         for(int i=0;i<1;i++)document.add(par0);
         
         //四、条码印刷技术类型
         paragraph=new Paragraph("四、条码印刷技术类型",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            
            PdfPTable table4 = new PdfPTable(12);
         	table4.setTotalWidth((float)530.0);
            table4.setLockedWidth(true);
            
            //------------- 各种技术类型
            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
                cell.setColspan(12);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table4.addCell(cell);
            //------------- 其他技术类型
            cell = new PdfPCell(new Phrase("其他(简述)",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table4.addCell(cell);
                //------------- 其他材料值
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

            
            
         //空行
         for(int i=0;i<3;i++)document.add(par0);
               
         //五、印刷载体材料
         paragraph=new Paragraph("五、印刷载体材料",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            PdfPTable table5 = new PdfPTable(12);
         	table5.setTotalWidth((float)530.0);
            table5.setLockedWidth(true);
            
            //------------- 各种材料
            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
                cell.setColspan(12);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table5.addCell(cell);
            //------------- 其他材料
            cell = new PdfPCell(new Phrase("其他(简述)",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table5.addCell(cell);
            //------------- 其他材料值
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
            
         //空行
            for(int i=0;i<3;i++)document.add(par0);
            
         /*提交材料
         paragraph=new Paragraph("提交材料",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);*/
           
         //六、主要印刷设备
         paragraph=new Paragraph("六、主要印刷设备",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            
            PdfPTable table6 = new PdfPTable(12);
         	table6.setTotalWidth((float)530.0);
            table6.setLockedWidth(true);            
            //------------- 主要印刷设备值
            cell = new PdfPCell(new Phrase(app.getPrintEquipment(),setFont(numTX,false)));
            	cell.setColspan(12);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(90f);
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table6.addCell(cell);
         document.add(table6);
         
         //空行
         for(int i=0;i<3;i++)document.add(par0);
         
         //七、条码检测设备
         paragraph=new Paragraph("七、条码检测设备",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            PdfPTable table7 = new PdfPTable(12);
         	table7.setTotalWidth((float)530.0);
            table7.setLockedWidth(true);            
            //------------- 条码检测设备值
            cell = new PdfPCell(new Phrase(app.getTestEquipment(),setFont(numTX,false)));
            	cell.setColspan(12);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(90f);
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table7.addCell(cell);
         document.add(table7);
              
         document.newPage();
         //空行
         for(int i=0;i<1;i++)document.add(par0);
         
         //八、备注
         paragraph=new Paragraph("八、备注",setFont(numX,true));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(par0);
            PdfPTable table8 = new PdfPTable(12);
         	table8.setTotalWidth((float)530.0);
            table8.setLockedWidth(true);            
            //------------- 备注值
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
		//输出路径
		String fileDesc = "";
		// 创建输出流	
		FileOutputStream outr = null;
		//fileDesc="D:/wmq/wtf.pdf";	
		fileDesc=filePath;
		//大标题
		 int numD=20;
		 
		 //小标题
		 int numX=14;
		 
		 //表格大
		 int numTD=14;
		 
		 //表格小
		 int numTX=12;
		 
		 //表格中
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
         
	     paragraph =new Paragraph("编号 "+app.getTitleno(),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_RIGHT);
         document.add(paragraph);
	     
         //空行
         for(int i=0;i<4;i++)document.add(par0);
         
         
         //商  品  条  码  印  刷  资  格
         paragraph =new Paragraph("商  品  条  码  印  刷  资  格",setFont(numD,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
	     document.add(par0);
         
	     //申 请 表
	     paragraph=new Paragraph("申 请 表",setFont(26,true));
	     paragraph.setAlignment(Element.ALIGN_CENTER);
	     document.add(paragraph);
         
	     //空行
         for(int i=0;i<12;i++)document.add(par0);
         
         //申请企业名称
         String entName = String.format("%-20s", app.getEnterprisename());
         
         paragraph=new Paragraph("                                                     申请企业名称：  ",setFont(numX,false));
	       
         chunk = new Chunk(entName,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
         chunk = new Chunk("(盖章)",setFontUnderline(numX,false));
         paragraph.add(chunk);
         
         paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	     
	     document.add(par0);
	     
	     //申   请   日   期
	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");   
         String str = String.format("%-28s", sdf.format(app.getCreatedate()));
	     
	     //paragraph=new Paragraph("                                                      申   请   日   期：  "+str,setFontUnderline(numX,true));
	     paragraph=new Paragraph("                                                      申   请   日   期：  ",setFont(numX,false));
         
         chunk = new Chunk(str,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
	     paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	        
	     //空行
         for(int i=0;i<10;i++)document.add(par0);
	     
         paragraph=new Paragraph("中国物品编码中心制",setFont(numX,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //第二页
         document.newPage();
         
         //空行
         for(int i=0;i<1;i++)document.add(par0);
         
         	PdfPTable table1 = new PdfPTable(12);
         	table1.setTotalWidth((float)480.0);
            table1.setLockedWidth(true);
            //------------- 企业名称(中文)
            cell = new PdfPCell(new Phrase("企业名称(中文)",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 企业名称(中文)值
            cell = new PdfPCell(new Phrase(app.getEnterprisename(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 传真
            cell = new PdfPCell(new Phrase("传真",setFont(numTX,false)));
            	cell.setRowspan(2);
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 传真值
            cell = new PdfPCell(new Phrase(app.getFax(),setFont(numTX,false)));
            	cell.setRowspan(2);
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 企业名称(英文)
            cell = new PdfPCell(new Phrase("企业名称(英文)",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 企业名称(英文)值
            cell = new PdfPCell(new Phrase(app.getEnglishname(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            //------------- 详细地址
            cell = new PdfPCell(new Phrase("详细地址",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 详细地址值
            cell = new PdfPCell(new Phrase(app.getAddress(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 邮政编码
            cell = new PdfPCell(new Phrase("邮政编码",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 邮政编码值
            cell = new PdfPCell(new Phrase(app.getPostalcode(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            /*
            //------------- 印刷经营许可证号码
            cell = new PdfPCell(new Phrase("印刷经营许可证号码",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 印刷经营许可证号码值
            cell = new PdfPCell(new Phrase(app.getCertificateno(),setFont(numTX,false)));
            	cell.setColspan(4);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(50f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
			*/
            //------------- 营业执照号码
            cell = new PdfPCell(new Phrase("营业执照号码",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 营业执照号码值
            cell = new PdfPCell(new Phrase(app.getBusinessno(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 企业性质
            cell = new PdfPCell(new Phrase("企业性质",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 企业性质值
            cell = new PdfPCell(new Phrase(app.getEnterprisekind(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 注册资本
            cell = new PdfPCell(new Phrase("注册资本",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 注册资本值
            cell = new PdfPCell(new Phrase(app.getRegistercapital(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);          
            //------------- 法人代表
            cell = new PdfPCell(new Phrase("法人代表",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 法人代表值
            cell = new PdfPCell(new Phrase(app.getArtificialperson(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务
            cell = new PdfPCell(new Phrase("职务",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务值
            cell = new PdfPCell(new Phrase(app.getApjob(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 电话
            cell = new PdfPCell(new Phrase("电话",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 电话值
            cell = new PdfPCell(new Phrase(app.getAptel(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 联系人
            cell = new PdfPCell(new Phrase("联系人",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 联系人值
            cell = new PdfPCell(new Phrase(app.getLinkman(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务
            cell = new PdfPCell(new Phrase("职务",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务值
            cell = new PdfPCell(new Phrase(app.getLjob(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 电话
            cell = new PdfPCell(new Phrase("电话",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 电话值
            cell = new PdfPCell(new Phrase(app.getLtel(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);  
            /*
            //------------- 经营范围
            cell = new PdfPCell(new Phrase("经营范围",setFont(numTX,false)));
         		cell.setRowspan(2);
         		cell.setColspan(1);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	*/
         	//------------- 主营
         	cell = new PdfPCell(new Phrase("主营",setFont(numTX,false)));
         		cell.setColspan(2);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	//------------- 主营值
         	cell = new PdfPCell(new Phrase(app.getMainbusiness(),setFont(numTX,false)));
         		cell.setColspan(10);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	//------------- 兼营
         	cell = new PdfPCell(new Phrase("兼营",setFont(numTX,false)));
         		cell.setColspan(2);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	//------------- 兼营值
         	cell = new PdfPCell(new Phrase(app.getRestbusiness(),setFont(numTX,false)));
         		cell.setColspan(10);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
            /*
         	//------------- 人员情况
            cell = new PdfPCell(new Phrase("人员情况",setFont(numTX,false)));
         		cell.setRowspan(2);
         		cell.setColspan(1);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	*/
         	//------------- 职工总数
            cell = new PdfPCell(new Phrase("职工总数",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职工总数值
            cell = new PdfPCell(new Phrase(app.getEmployeetotal() == null?" ":Integer.toString(app.getEmployeetotal()),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 技术人员
            cell = new PdfPCell(new Phrase("技术人员",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- 技术人员值
            cell = new PdfPCell(new Phrase(app.getTechniciantotal() == null?" ":Integer.toString(app.getTechniciantotal()),setFont(numTX,false)));
                cell.setColspan(6);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 条码印刷技术负责人
            cell = new PdfPCell(new Phrase("条码印刷技术负责人",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- 条码印刷技术负责人值
            cell = new PdfPCell(new Phrase(app.getBcprincipal(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务
            cell = new PdfPCell(new Phrase("职务",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- 职务值
            cell = new PdfPCell(new Phrase(app.getTpbusiness(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职称
            cell = new PdfPCell(new Phrase("职称",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职称值
            cell = new PdfPCell(new Phrase(app.getTpopost(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 条码印刷技术类型
            cell = new PdfPCell(new Phrase("条码印刷\n技术类型",setFont(numTX,false)));
                cell.setRowspan(2);
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(50f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- 各种技术类型
            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
                cell.setColspan(10);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- 其他技术类型
            cell = new PdfPCell(new Phrase("其他(简述)",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- 其他材料值
            cell = new PdfPCell(new Phrase(app.getElsetype(),setFont(numTX,false)));
                cell.setColspan(8);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 条码载体材料
            cell = new PdfPCell(new Phrase("条码载体材料",setFont(numTX,false)));
                cell.setRowspan(2);
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- 各种载体类型
            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
                cell.setColspan(10);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- 其他载体类型
            cell = new PdfPCell(new Phrase("其他(简述)",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- 其他载体值
            cell = new PdfPCell(new Phrase(app.getElsetype(),setFont(numTX,false)));
                cell.setColspan(8);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 主要印刷设备
            cell = new PdfPCell(new Phrase("主要印刷设备",setFont(numTX,false)));
			 	cell.setRowspan(printEquipments.size()+1);
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- 主要印刷设备表头名
			cell = new PdfPCell(new Phrase("名称", setFont(numTX,false)));
				cell.setColspan(2);
				cell.setUseAscender(true);
				cell.setFixedHeight(25f);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell);
			cell = new PdfPCell(new Phrase("型号", setFont(numTX,false)));
				cell.setColspan(2);
			 	cell.setUseAscender(true);
			 	cell.setFixedHeight(25f);
			 	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table1.addCell(cell);
			cell = new PdfPCell(new Phrase("产地", setFont(numTX,false)));
			 	cell.setColspan(2);
			 	cell.setUseAscender(true);
			 	cell.setFixedHeight(25f);
			 	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 table1.addCell(cell);
			 cell = new PdfPCell(new Phrase("数量", setFont(numTX,false)));
			 	cell.setColspan(1);
			 	cell.setUseAscender(true);
			 	cell.setFixedHeight(25f);
			 	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 table1.addCell(cell);
			 cell = new PdfPCell(new Phrase("备注", setFont(numTX,false)));
			 	cell.setColspan(3);
			 	cell.setUseAscender(true);
			 	cell.setFixedHeight(25f);
			 	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 table1.addCell(cell);
			 //------------- 主要印刷设备值
			for(int i = 0 ; i < printEquipments.size() ; i++) {
				//名称值
				cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintName(),setFont(numTX,false)));
				cell.setColspan(2);
				cell.setUseAscender(true);
				cell.setFixedHeight(25f);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table1.addCell(cell);
				//型号值
				cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintModel(),setFont(numTX,false)));
				cell.setColspan(2);
				cell.setUseAscender(true);
				cell.setFixedHeight(25f);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table1.addCell(cell);
				//产地值
				cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintPlace(),setFont(numTX,false)));
				cell.setColspan(2);
				cell.setUseAscender(true);
				cell.setFixedHeight(25f);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table1.addCell(cell);
				//数量值
				cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintNumber()+"",setFont(numTX,false)));
				cell.setColspan(1);
				cell.setUseAscender(true);
				cell.setFixedHeight(25f);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table1.addCell(cell);
				//备注值
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
            //------------- 条码检测设备
            cell = new PdfPCell(new Phrase("条码检测设备",setFont(numTX,false)));
			 	cell.setRowspan(testingEquips.size()+1);
				cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- 条码检测设备表头名
			 if(testingEquips.size() <= 0) {
				 cell = new PdfPCell(new Phrase("无", setFont(numTX,false)));
					 cell.setColspan(10);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
			 } else {
				 cell = new PdfPCell(new Phrase("名称", setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("型号", setFont(numTX,false)));
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("数量", setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("最近校准日期", setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
					 cell = new PdfPCell(new Phrase("备注", setFont(numTX,false)));
					 cell.setColspan(4);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 for(int j = 0; j < testingEquips.size(); j++) {
				 	//名称值
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getName(),setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setFixedHeight(25f);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					//型号值
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getTestingModel(),setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setFixedHeight(25f);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //数量值
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getCount()+"",setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setFixedHeight(25f);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //最近一次校准日期值
					 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
					 cell = new PdfPCell(new Phrase(df.format(testingEquips.get(j).getTime()),setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setFixedHeight(25f);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //备注值
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
            //------------- 备注
            cell = new PdfPCell(new Phrase("备注",setFont(numTX,false)));
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(75f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- 备注值
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
		//输出路径
		String fileDesc = "";
		// 创建输出流	
		FileOutputStream outr = null;
		//fileDesc="D:/wmq/wtf.pdf";	
		fileDesc=filePath;
		//大标题
		 int numD=20;
		 
		 //小标题
		 int numX=14;
		 
		 //表格大
		 int numTD=14;
		 
		 //表格小
		 int numTX=12;
		 
		 //表格中
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
         
	     paragraph =new Paragraph("编号 "+app.getTitleno(),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_RIGHT);
         document.add(paragraph);
	     
         //空行
         for(int i=0;i<4;i++)document.add(par0);
         
         
         //商  品  条  码  印  刷  资  格
         paragraph =new Paragraph("商  品  条  码  印  刷  资  格",setFont(numD,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
	     document.add(par0);
         
	     //申 请 表
	     paragraph=new Paragraph("复 认 申 请 表",setFont(26,true));
	     paragraph.setAlignment(Element.ALIGN_CENTER);
	     document.add(paragraph);
         
	     //空行
         for(int i=0;i<12;i++)document.add(par0);
         
         //申请企业名称
         String entName = String.format("%-20s", app.getEnterprisename());
         
         paragraph=new Paragraph("                                                 申请复认企业名称：  ",setFont(numX,false));
	       
         chunk = new Chunk(entName,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
         chunk = new Chunk("(盖章)",setFontUnderline(numX,false));
         paragraph.add(chunk);
         
         paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	     
	     document.add(par0);
	     
	     //申   请   日   期
	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");   
         String str = String.format("%-28s", sdf.format(app.getCreatedate()));
	     
	     paragraph=new Paragraph("                                                 申  请  复  认  日  期：  ",setFont(numX,false));
         
         chunk = new Chunk(str,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
	     paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	        
	     //空行
         for(int i=0;i<10;i++)document.add(par0);
	     
         paragraph=new Paragraph("中国物品编码中心制",setFont(numX,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //第二页
         document.newPage();
         
         //空行
         for(int i=0;i<1;i++)document.add(par0);
         
         	PdfPTable table1 = new PdfPTable(12);
         	table1.setTotalWidth((float)480.0);
            table1.setLockedWidth(true);
            //------------- 企业名称(中文)
            cell = new PdfPCell(new Phrase("企业名称(中文)",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 企业名称(中文)值
            cell = new PdfPCell(new Phrase(app.getEnterprisename(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 证书有效期
            cell = new PdfPCell(new Phrase("证书有效期",setFont(numTX,false)));
            	cell.setRowspan(2);
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 证书有效期值
            String yxq;
            if (app.getCertappdate()==null||app.getCerttodate()==null)
				yxq = "有效期无效";
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
            //------------- 企业名称(英文)
            cell = new PdfPCell(new Phrase("企业名称(英文)",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 企业名称(英文)值
            cell = new PdfPCell(new Phrase(app.getEnglishname(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            //------------- 详细地址
            cell = new PdfPCell(new Phrase("详细地址",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 详细地址值
            cell = new PdfPCell(new Phrase(app.getAddress(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 邮政编码
            cell = new PdfPCell(new Phrase("邮政编码",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 邮政编码值
            cell = new PdfPCell(new Phrase(app.getPostalcode(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 印刷经营许可证号码
            cell = new PdfPCell(new Phrase("印刷经营许可证号码",setFont(numTX,false)));
            	cell.setColspan(3);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 印刷经营许可证号码值
            cell = new PdfPCell(new Phrase(app.getCertificateno(),setFont(numTX,false)));
            	cell.setColspan(5);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 传真
            cell = new PdfPCell(new Phrase("传真",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 传真值
            cell = new PdfPCell(new Phrase(app.getFax(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 营业执照号码
            cell = new PdfPCell(new Phrase("营业执照号码",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 营业执照号码值
            cell = new PdfPCell(new Phrase(app.getBusinessno(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 企业性质
            cell = new PdfPCell(new Phrase("企业性质",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 企业性质值
            cell = new PdfPCell(new Phrase(app.getEnterprisekind(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 注册资本
            cell = new PdfPCell(new Phrase("注册资本",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 注册资本值
            cell = new PdfPCell(new Phrase(app.getRegistercapital(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);          
            //------------- 法人代表
            cell = new PdfPCell(new Phrase("法人代表",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 法人代表值
            cell = new PdfPCell(new Phrase(app.getArtificialperson(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务
            cell = new PdfPCell(new Phrase("职务",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务值
            cell = new PdfPCell(new Phrase(app.getApjob(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 电话
            cell = new PdfPCell(new Phrase("电话",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 电话值
            cell = new PdfPCell(new Phrase(app.getAptel(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 联系人
            cell = new PdfPCell(new Phrase("联系人",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 联系人值
            cell = new PdfPCell(new Phrase(app.getLinkman(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务
            cell = new PdfPCell(new Phrase("职务",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务值
            cell = new PdfPCell(new Phrase(app.getLjob(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 电话
            cell = new PdfPCell(new Phrase("电话",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 电话值
            cell = new PdfPCell(new Phrase(app.getLtel(),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);  
            /*
            //------------- 经营范围
            cell = new PdfPCell(new Phrase("经营范围",setFont(numTX,false)));
         		cell.setRowspan(2);
         		cell.setColspan(1);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	*/
         	//------------- 主营
         	cell = new PdfPCell(new Phrase("主营",setFont(numTX,false)));
         		cell.setColspan(2);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	//------------- 主营值
         	cell = new PdfPCell(new Phrase(app.getMainbusiness(),setFont(numTX,false)));
         		cell.setColspan(10);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	//------------- 兼营
         	cell = new PdfPCell(new Phrase("兼营",setFont(numTX,false)));
         		cell.setColspan(2);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	//------------- 兼营值
         	cell = new PdfPCell(new Phrase(app.getRestbusiness(),setFont(numTX,false)));
         		cell.setColspan(10);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
            /*
         	//------------- 人员情况
            cell = new PdfPCell(new Phrase("人员情况",setFont(numTX,false)));
         		cell.setRowspan(2);
         		cell.setColspan(1);
         		cell.setUseAscender(true);
         		cell.setFixedHeight(25f); 
         		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	table1.addCell(cell);
         	*/
         	//------------- 职工总数
            cell = new PdfPCell(new Phrase("职工总数",setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职工总数值
            cell = new PdfPCell(new Phrase(app.getEmployeetotal() == null?" ":Integer.toString(app.getEmployeetotal()),setFont(numTX,false)));
            	cell.setColspan(2);
            	cell.setUseAscender(true);
            	cell.setFixedHeight(25f); 
            	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 技术人员
            cell = new PdfPCell(new Phrase("技术人员",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- 技术人员值
            cell = new PdfPCell(new Phrase(app.getTechniciantotal() == null?" ":Integer.toString(app.getTechniciantotal()),setFont(numTX,false)));
                cell.setColspan(6);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 条码印刷技术负责人
            cell = new PdfPCell(new Phrase("条码印刷技术负责人",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- 条码印刷技术负责人值
            cell = new PdfPCell(new Phrase(app.getBcprincipal(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职务
            cell = new PdfPCell(new Phrase("职务",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- 职务值
            cell = new PdfPCell(new Phrase(app.getTpbusiness(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true); 
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职称
            cell = new PdfPCell(new Phrase("职称",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 职称值
            cell = new PdfPCell(new Phrase(app.getTpopost(),setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 条码印刷技术类型
            cell = new PdfPCell(new Phrase("条码印刷\n技术类型",setFont(numTX,false)));
                cell.setRowspan(2);
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- 各种技术类型
            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
                cell.setColspan(10);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- 其他技术类型
            cell = new PdfPCell(new Phrase("其他(简述)",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- 其他材料值
            cell = new PdfPCell(new Phrase(app.getElsetype(),setFont(numTX,false)));
                cell.setColspan(8);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 条码载体材料
            cell = new PdfPCell(new Phrase("条码载体材料",setFont(numTX,false)));
                cell.setRowspan(2);
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- 各种载体类型
            cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
                cell.setColspan(10);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- 其他载体类型
            cell = new PdfPCell(new Phrase("其他(简述)",setFont(numTX,false)));
                cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
                //------------- 其他载体值
            cell = new PdfPCell(new Phrase(app.getElsetype(),setFont(numTX,false)));
                cell.setColspan(8);
                cell.setUseAscender(true);
                cell.setFixedHeight(25f);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);
            //------------- 主要印刷设备
			 cell = new PdfPCell(new Phrase("主要印刷设备",setFont(numTX,false)));
				 cell.setRowspan(printEquipments.size()+1);
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 rect = new Rectangle(20,20,20,20);
			 table1.addCell(cell);
			 if(printEquipments.size() <= 0) {
				 cell = new PdfPCell(new Phrase("无", setFont(numTX,false)));
				 cell.setColspan(10);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
			 } else {
				 //------------- 主要印刷设备表头名
				 cell = new PdfPCell(new Phrase("名称", setFont(numTX,false)));
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("型号", setFont(numTX,false)));
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("产地", setFont(numTX,false)));
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("数量", setFont(numTX,false)));
				 cell.setColspan(1);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("备注", setFont(numTX,false)));
				 cell.setColspan(3);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 //------------- 主要印刷设备值
				 for(int i = 0 ; i < printEquipments.size() ; i++) {
					 //名称值
					 cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintName(),setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //型号值
					 cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintModel(),setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //产地值
					 cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintPlace(),setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //数量值
					 cell = new PdfPCell(new Phrase(printEquipments.get(i).getPrintNumber()+"",setFont(numTX,false)));
					 cell.setColspan(1);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //备注值
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
            //------------- 条码检测设备
			 cell = new PdfPCell(new Phrase("条码检测设备",setFont(numTX,false)));
				 cell.setRowspan(testingEquips.size()+1);
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 rect = new Rectangle(20,20,20,20);
			 table1.addCell(cell);
            //------------- 条码检测设备表头名
			 if(testingEquips.size() <= 0) {
				 cell = new PdfPCell(new Phrase("无", setFont(numTX,false)));
				 cell.setColspan(10);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
			 } else {
				 cell = new PdfPCell(new Phrase("名称", setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("型号", setFont(numTX,false)));
				 cell.setColspan(2);
				 cell.setUseAscender(true);
				 cell.setFixedHeight(25f);
				 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("数量", setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("最近校准日期", setFont(numTX,false)));
					 cell.setColspan(2);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 cell = new PdfPCell(new Phrase("备注", setFont(numTX,false)));
					 cell.setColspan(4);
					 cell.setUseAscender(true);
					 cell.setFixedHeight(25f);
					 cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 for(int j = 0; j < testingEquips.size(); j++) {
					 //名称值
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getName(),setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					//型号值
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getTestingModel(),setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //数量值
					 cell = new PdfPCell(new Phrase(testingEquips.get(j).getCount()+"",setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setFixedHeight(25f);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //最近一次校准日期值
					 cell = new PdfPCell(new Phrase(df.format(testingEquips.get(j).getTime()),setFont(numTX,false)));
						 cell.setColspan(2);
						 cell.setUseAscender(true);
						 cell.setFixedHeight(25f);
						 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					 table1.addCell(cell);
					 //备注值
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
            //------------- 备注
            cell = new PdfPCell(new Phrase("备注",setFont(numTX,false)));
            	cell.setColspan(2);
                cell.setUseAscender(true);
                cell.setFixedHeight(75f); 
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                rect = new Rectangle(20,20,20,20);
            table1.addCell(cell);
            //------------- 备注值
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
		//输出路径
		String fileDesc = "";
		// 创建输出流	
		FileOutputStream outr = null;
		fileDesc=filePath;
		//大标题
		 int numD=20;
		 
		 //小标题
		 int numX=14;
		 
		 //表格大
		 int numTD=14;
		 
		 //表格小
		 int numTX=12;
		 
		 //表格中
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
         
	     paragraph =new Paragraph("编号 "+rev.getTitleno(),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_RIGHT);
         document.add(paragraph);
	     
         //空行
         for(int i=0;i<4;i++)document.add(par0);
         
         
         //商  品  条  码  印  刷  资  格
         paragraph =new Paragraph("商  品  条  码  印  刷  资  格",setFont(numD,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
	     document.add(par0);
         
	     switch(rev.getTitleno().substring(0,2)){
	     case "XB" :
	     //申 请 表
	    	 paragraph=new Paragraph("申   请   评   审   表",setFont(26,true));
	    	 break;
         
	     case "FR" : 
	    	 //复 认 表
	    	 paragraph=new Paragraph("复   认   评   审   表",setFont(26,true));
	    	 break;
	     default :
	    	 paragraph=new Paragraph("?   ?   评   审   表",setFont(26,true));
		     break;
	     }
  		 paragraph.setAlignment(Element.ALIGN_CENTER);
  		 document.add(paragraph);
  		 
	     //空行
         for(int i=0;i<12;i++)document.add(par0);
         
         //申请企业名称
         String entName = String.format("%-34s", rev.getEnterpriseName());
         
         paragraph=new Paragraph("                                                     申请企业名称：  ",setFont(numX,false));
	       
         chunk = new Chunk(entName,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
         paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	     
	     document.add(par0);
	     
	     //评审机构
         String chiCenter = String.format("%-28s", rev.getChiCenter());
         
         paragraph=new Paragraph("                                                     评   审   机   构：  ",setFont(numX,false));
	       
         chunk = new Chunk(chiCenter,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
         paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	     
	     document.add(par0);
	     
	     //评   审   日   期
	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	     String str;
	     if(rev.getPsdate()!=null)
         str = String.format("%-32s", sdf.format(rev.getCreateDate()));
	     else
	     str = String.format("%-32s", " ");
	     //paragraph=new Paragraph("                                                      创建时间：  "+str,setFontUnderline(numX,true));
	     paragraph=new Paragraph("                                                     创   建   时   间：  ",setFont(numX,false));
         
         chunk = new Chunk(str,setFontUnderline(numX,false));
         paragraph.add(chunk);
         
	     paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	        
	     //空行
         for(int i=0;i<8;i++)document.add(par0);
	     
         paragraph=new Paragraph("中国物品编码中心制",setFont(numX,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //第二页
         document.newPage();
         
         //商品条码印刷资格现场评审表
         paragraph =new Paragraph("商品条码印刷资格现场评审表",setFont(16,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         for(int i=0;i<2;i++)document.add(par0);
         
         PdfPTable table1 = new PdfPTable(12);
      	 table1.setTotalWidth((float)480.0);
         table1.setLockedWidth(true);
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("评审项目与要求",setFont(numTX,true)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("评审方法",setFont(numTX,true)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
         cell = new PdfPCell(new Phrase("评审记录",setFont(numTX,true)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
         cell = new PdfPCell(new Phrase("单项评审结果",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);

         String required="";
         String PSJL="";
         String PSJG="";

        //根据设置显示
//		 Class<? extends ReviewForm> userClass = rev.getClass();
//		 java.lang.reflect.Field[] fields = userClass.getDeclaredFields();
		 ReviewFormPart reviewFormPart;

		 for (Evaluation evaluation : evalua) {
			 if(evaluation.getAvailable()){
				 //------------- 评审项目与要求
				 required=evaluation.getRequired();
				 cell = new PdfPCell(new Phrase(required.replace("<br>","\n"),setFont(numTX,false)));
					 cell.setColspan(4);
					 cell.setUseAscender(true);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 //------------- 评审方法
				 cell = new PdfPCell(new Phrase(" "+evaluation.getMethod(),setFont(numTX,false)));
					 cell.setColspan(3);
					 cell.setUseAscender(true);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 //------------- 得到相应的数据
				 PSJL="";
				 PSJG="";
				 reviewFormPart = reviewFormPartService.getByReviewFormNumId(rev.getId(),evaluation.getNum());
				 PSJL = reviewFormPart.getPsjl();
				 PSJG = reviewFormPart.getPsjg();
				 //------------- 评审记录
				 cell = new PdfPCell(new Phrase(PSJL,setFont(numTX,false)));
					 cell.setColspan(3);
					 cell.setUseAscender(true);
					 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				 table1.addCell(cell);
				 //------------- 单项评审结果
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
//				//------------- 评审项目与要求
//				required=evaluation.getRequired();
//		         cell = new PdfPCell(new Phrase(required.replace("<br>","\n"),setFont(numTX,false)));
//		         	cell.setColspan(4);
//		         	cell.setUseAscender(true);
//		         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//		         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		         table1.addCell(cell);
//		         //------------- 评审方法
//		         cell = new PdfPCell(new Phrase(" "+evaluation.getMethod(),setFont(numTX,false)));
//		         	cell.setColspan(3);
//		         	cell.setUseAscender(true);
//		         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//		         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		         table1.addCell(cell);
//		         //------------- 评审记录
//		         PSJL="PSJL"+evaluation.getNum();
//		         PSJG="PSJG"+evaluation.getNum();
//		 		for (java.lang.reflect.Field field1 : fields) {
//		 			// 获得字符串名字
//		 			String userFieldName = field1.getName();
//		 			// 字符串首字母大写
//		 			char[] cs = userFieldName.toCharArray();
//		 			if (Character.isLowerCase(cs[0]))
//	 			   {
//
//		 				cs[0] -= 32;
//	 			   }
//		 			if(String.valueOf(cs).equals(PSJL)){
//		 			// 调用get方法
//		 			Method method = userClass.getMethod("get" + String.valueOf(cs));
//		 			// 得到值
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
//		         //------------- 单项评审结果
//		 		for (java.lang.reflect.Field field2 : fields) {
//		 			// 获得字符串名字
//		 			String userFieldName2 = field2.getName();
//		 			// 字符串首字母大写
//		 			char[] cs2 = userFieldName2.toCharArray();
//		 			if (Character.isLowerCase(cs2[0]))
//		 			   {
//			 				cs2[0] -= 32;
//		 			   }
//		 			if(String.valueOf(cs2).equals(PSJG)){
//			 			// 调用get方法
//			 			Method method = userClass.getMethod("get" + String.valueOf(cs2));
//			 			// 得到值
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
         
         //注：带*号的项为重点项
         paragraph =new Paragraph("                 注：带*号的项为重点项",setFont(8,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         document.add(paragraph);
         
	     //空行
         for(int i=0;i<1;i++)document.add(par0);
         
         //综合判定规则
         paragraph =new Paragraph("综合判定规则",setFont(14,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //综合判定规则(
         paragraph =new Paragraph("                评审结论分“认定”、“暂不认定”两种。\n"
+"                申请企业经评审同时满足下述要求的，评审结论为“认定”；否则，评审结论为“不认定”。\n"
+"                1、	重点项（含*号标记项）为A的项数不得少于五项，且无C项；\n"
+"                2、	全项出现B项的个数不得超过6项\n"
+"            非重点项出现C项的个数不得超过二项。当非重点项出现一个C项时，全项中出现B项的数目不得超过4项；当出现二个C项时，全项中出现B项的数目不得超过2项。",setFont(12,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         document.add(paragraph);
         
         document.add(par0);
       //评审员签名表
         paragraph =new Paragraph("评审员签名表",setFont(14,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         document.add(par0);
         
         PdfPTable table2 = new PdfPTable(12);
      	 table2.setTotalWidth((float)480.0);
         table2.setLockedWidth(true);
         //------------- 姓名
         cell = new PdfPCell(new Phrase("姓名",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setMinimumHeight(25f);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- 工作单位
         cell = new PdfPCell(new Phrase("工作单位",setFont(numTX,true)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- 职称/职务
         cell = new PdfPCell(new Phrase("职称/职务",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
			cell.setMinimumHeight(25f);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- 签名
         cell = new PdfPCell(new Phrase("签名",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
			cell.setMinimumHeight(25f);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
			 //------------- 日期
			 cell = new PdfPCell(new Phrase("日期",setFont(numTX,true)));
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
         //------------- 姓名
         cell = new PdfPCell(new Phrase(user.getUsername(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- 工作单位
         cell = new PdfPCell(new Phrase(user.getUnit(),setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- 职称/职务
         cell = new PdfPCell(new Phrase(user.getPost(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- 签名
         cell = new PdfPCell(new Phrase(" ",setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
			 //------------- 日期
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
         cell = new PdfPCell(new Phrase("评\n审\n组\n意\n见\n及\n结\n论",setFont(numTX,true)));
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
         cell = new PdfPCell(new Phrase("评审组长签名：",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //-------------
         cell = new PdfPCell(new Phrase("日期：",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("分\n支\n机\n构\n意\n见",setFont(numTX,true)));
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
         //------------- 盖章
		 cell = new PdfPCell(new Phrase("                                                                                分支机构(盖章)",setFont(numTX,false)));
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
         cell = new PdfPCell(new Phrase("负责人签名：",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("日期：",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("中\n国\n物\n品\n编\n码\n中\n心\n意\n见",setFont(numTX,true)));
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
         //-------------盖章
		 cell = new PdfPCell(new Phrase("                                                                               中国物品编码中心(盖章)",setFont(numTX,false)));
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
         cell = new PdfPCell(new Phrase("批准人签名：",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(30f);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("日期：",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(30f);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("核准人签名：",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(30f);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("日期：",setFont(numTX,false)));
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
		
		//输出路径
		String fileDesc = "";
		// 创建输出流	
		FileOutputStream outr = null;
		fileDesc=filePath;
		//大标题
		 int numD=20;
		 
		 //小标题
		 int numX=14;
		 
		 //表格大
		 int numTD=14;
		 
		 //表格小
		 int numTX=12;
		 
		 //表格中
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
         
	     paragraph =new Paragraph("编号 "+rev.getTitleno(),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_RIGHT);
         document.add(paragraph);
	     
         //空行
         for(int i=0;i<4;i++)document.add(par0);
         
         
         //商  品  条  码  印  刷  资  格
         paragraph =new Paragraph("商  品  条  码  印  刷  资  格",setFont(numD,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
	     document.add(par0);
         
	     switch(rev.getTitleno().substring(0,2)){
	     case "XB" :
	     //申 请 表
	    	 paragraph=new Paragraph("申   请   评   审   表",setFont(26,true));
	    	 break;
         
	     case "FR" : 
	    	 //复 认 表
	    	 paragraph=new Paragraph("复   认   评   审   表",setFont(26,true));
	    	 break;
	     default :
	    	 paragraph=new Paragraph("?   ?   评   审   表",setFont(26,true));
		     break;
	     }
  		 paragraph.setAlignment(Element.ALIGN_CENTER);
  		 document.add(paragraph);
  		 
	     //空行
         for(int i=0;i<12;i++)document.add(par0);
         
         //申请企业名称
         String entName = String.format("%-34s", rev.getEnterpriseName());
         
         paragraph=new Paragraph("                                                     申请企业名称：  ",setFont(numX,false));
	       
         chunk = new Chunk(entName,setFontUnderline(numX,true));
         paragraph.add(chunk);
         
         paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	     
	     document.add(par0);
	     
	     //评审机构
         String chiCenter = String.format("%-28s", rev.getChiCenter());
         
         paragraph=new Paragraph("                                                     评   审   机   构：  ",setFont(numX,false));
	       
         chunk = new Chunk(chiCenter,setFontUnderline(numX,true));
         paragraph.add(chunk);
         
         paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	     
	     document.add(par0);
	     
	     //评   审   日   期
	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	     String str;
	     if(rev.getPsdate()!=null)
         str = String.format("%-32s", sdf.format(rev.getCreateDate()));
	     else
	     str = String.format("%-32s", " ");
	     //paragraph=new Paragraph("                                                      创   建   时   间：  "+str,setFontUnderline(numX,true));
	     paragraph=new Paragraph("                                                     创   建   时   间：  ",setFont(numX,false));
         
         chunk = new Chunk(str,setFontUnderline(numX,true));
         paragraph.add(chunk);
         
	     paragraph.setAlignment(Element.ALIGN_LEFT);
	     document.add(paragraph);
	        
	     //空行
         for(int i=0;i<8;i++)document.add(par0);
	     
         paragraph=new Paragraph("中国物品编码中心制",setFont(numX,false));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //第二页
         document.newPage();
         
         //商品条码印刷资格现场评审表
         paragraph =new Paragraph("商品条码印刷资格现场评审表",setFont(16,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         for(int i=0;i<2;i++)document.add(par0);
         
         PdfPTable table1 = new PdfPTable(11);
      	 table1.setTotalWidth((float)480.0);
         table1.setLockedWidth(true);
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("评审项目与要求",setFont(numTX,true)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("评审方法",setFont(numTX,true)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
         cell = new PdfPCell(new Phrase("评审记录",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
         cell = new PdfPCell(new Phrase("单项评审结果",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("1.	质量方针和目标\n    企业应制定总体质量方针和质量目标，明示各工序各环节应达到的质量要求，并贯彻实施和有效运行",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    有质量方针和目标并贯彻实施记“A”，否则记“C”，有缺陷记“B”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
         //cell = new PdfPCell(new Phrase(rev.getPSJL1(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
         //cell = new PdfPCell(new Phrase(rev.getPSJG1(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("2.	组织机构\n"
    +"    企业应根据条码生产流程，明确主管领导、业务、技术、生产、检验、仓储等部门各自的职责、权限和相互关系，使条码印刷各环节衔接配套，有工作流程和组织机构框图，确保条码印刷品质量符合国家标准要求，并遵守国家有关条码管理的各项规定。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    机构设置合理，人员职责明确记“A”，反之记“C”，有缺陷记“B”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
         //cell = new PdfPCell(new Phrase(rev.getPSJL2(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
         //cell = new PdfPCell(new Phrase(rev.getPSJG2(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("3.	人员\n"
        		 +"3.1	主管领导\n"
        		 +"    企业内部应明确负责条码印刷管理工作的企业领导，该领导应了解条码基本知识、质量要求和国家有关条码管理的规定。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    符合要求记“A”，否则记“C”，部分符合记“B”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
        // cell = new PdfPCell(new Phrase(rev.getPSJL31(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
        // cell = new PdfPCell(new Phrase(rev.getPSJG31(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("3.2* 技术负责人\n"
+"    条码技术负责人应熟悉条码国家标准，掌握条码印刷技术，具备条码印刷过程质量控制能力。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    符合要求记“A”，否则记“C”，部分符合记“B”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
         //cell = new PdfPCell(new Phrase(rev.getPSJL32(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
        // cell = new PdfPCell(new Phrase(rev.getPSJG32(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("3.3  业务人员\n"
 +"    承接印刷业务的人员应了解条码基本知识和国家有关条码管理、企业有关规章制度的规定。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    符合要求记“A”，否则记“C”，部分符合记“B”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
        // cell = new PdfPCell(new Phrase(rev.getPSJL33(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
        // cell = new PdfPCell(new Phrase(rev.getPSJG33(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("3.4* 设计审查人员\n"
+"    熟悉条码国家标准，掌握条码位置、尺寸、颜色等设计技术要求。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    符合要求记“A”，否则记“C”，部分符合记“B”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
        // cell = new PdfPCell(new Phrase(rev.getPSJL34(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
         //cell = new PdfPCell(new Phrase(rev.getPSJG34(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("3.5 现场操作人员\n"
    +"    应了解条码的基本质量要求，并具备现场质量控制能力。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    符合要求记“A”，反之记“C”，部分符合记“B”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
        // cell = new PdfPCell(new Phrase(rev.getPSJL35(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
        // cell = new PdfPCell(new Phrase(rev.getPSJG35(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("3.6* 检验人员\n"
+"    企业应配备检验人员负责条码印刷品检验。检验人员应熟悉条码国家标准，掌握条码质量检验技术。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    有符合要求的人员记“A”，否则记“C”，部分符合记“B”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
        // cell = new PdfPCell(new Phrase(rev.getPSJL36(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
        // cell = new PdfPCell(new Phrase(rev.getPSJG36(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("4* 印刷设备\n"
   +"    用于印刷条码的主要设备适宜；能够满足条码印刷质量要求；运转正常。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    符合记“A”，否则记“C”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
         //cell = new PdfPCell(new Phrase(rev.getPSJL4(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
        // cell = new PdfPCell(new Phrase(rev.getPSJG4(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("5 适性试验\n"
    +"    通过印刷适性试验，能有效控制条宽平均增益或减少，满足条码质量要求",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    符合记“A”，有缺陷记“B”，否则记“C”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
        // cell = new PdfPCell(new Phrase(rev.getPSJL5(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
        // cell = new PdfPCell(new Phrase(rev.getPSJG5(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("6* 印刷品抽检\n"
    +"    抽检样品符合质量要求。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    合格为“A”，否则记“C”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
         //cell = new PdfPCell(new Phrase(rev.getPSJL6(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
        // cell = new PdfPCell(new Phrase(rev.getPSJG6(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("7.企业内部检验\n"
+"7.1* 条码检验\n"
    +"    拥有必要的条码符号检验设备。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    符合记“A”，部分符合记“B”，否则记“C”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
         //cell = new PdfPCell(new Phrase(rev.getPSJL71(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
         //cell = new PdfPCell(new Phrase(rev.getPSJG71(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("7.2 检验工作程序及要求\n"
  +"    按标准进行抽样和检验；检验记录与合格证应规范统一、项目齐全、字迹工整。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    符合记“A”，否则记“C”，有缺陷记“B”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
         //cell = new PdfPCell(new Phrase(rev.getPSJL72(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
         //cell = new PdfPCell(new Phrase(rev.getPSJG72(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("8.规章制度\n"
+"8.1验证制度\n"
    +"    在承接条码印刷业务时，必须向委托单位索取有关证明，核查证明的有效性，并将证明复印件与核查记录一起存档备查，保留期不得少于二年。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    有制度并严格执行记“A”，无制度记“C”，有制度，执行不严记“B”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
         //cell = new PdfPCell(new Phrase(rev.getPSJL81(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
         //cell = new PdfPCell(new Phrase(rev.getPSJG81(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("8.2条码设计审查制度\n"
    +"    印刷条码前要对设计稿样进行审查，做到设计不合格的稿样不投入制版印刷\n",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    有制度并严格执行记“A”，无制度记“C”，有制度，执行不严记“B”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
         //cell = new PdfPCell(new Phrase(rev.getPSJL82(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
         //cell = new PdfPCell(new Phrase(rev.getPSJG82(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("8.3 条码印刷品、印版、胶片管理制度\n"
        		   +"    明确条码印刷合格品、不合格品、残次品、印版、胶片的出入库登记、保管、移交、监销程序和负责人员。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    有制度并严格执行记“A”，无制度记“C”，有制度，执行不严记“B”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
         //cell = new PdfPCell(new Phrase(rev.getPSJL83(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
         //cell = new PdfPCell(new Phrase(rev.getPSJG83(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         //------------- 评审项目与要求
         cell = new PdfPCell(new Phrase("8.4* 条码印刷品质量检验制度\n"
  +"    明确检验合格放行程序，做到检验不合格的半成品不投入下道生产工序；明确负责出厂检验人员，做到未经检验合格的条码印刷品不出厂。",setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审方法
         cell = new PdfPCell(new Phrase("    有制度并严格执行记“A”，无制度记“C”，有制度，执行不严记“B”。",setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 评审记录
         //cell = new PdfPCell(new Phrase(rev.getPSJL84(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 单项评审结果
         //cell = new PdfPCell(new Phrase(rev.getPSJG84(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         
         document.add(table1);
         
         //注：带*号的项为重点项
         paragraph =new Paragraph("                 注：带*号的项为重点项",setFont(8,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         document.add(paragraph);
         
	     //空行
         for(int i=0;i<1;i++)document.add(par0);
         
         //综合判定规则
         paragraph =new Paragraph("综合判定规则",setFont(14,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         //综合判定规则(
         paragraph =new Paragraph("                评审结论分为“认定”和“不认定”两种。\n"
+"                申请企业经评审同时满足下述要求的，评审结论为“认定”；否则，评审结论为“不认定”。\n"
+"                1、	重点项（含*号标记项）为A的项数不得少于五项，且无C项；\n"
+"                2、	全项出现B项的个数不得超过6项\n"
+"            非重点项出现C项的个数不得超过二项。当非重点项出现一个C项时，全项中出现B项的数目不得超过4项；当出现二个C项时，全项中出现B项的数目不得超过2项。",setFont(12,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         document.add(paragraph);
         
         document.add(par0);
         //评审员签名表
         paragraph =new Paragraph("评审员签名表",setFont(14,true));
         paragraph.setAlignment(Element.ALIGN_CENTER);
         document.add(paragraph);
         
         document.add(par0);
         
         PdfPTable table2 = new PdfPTable(12);
      	 table2.setTotalWidth((float)480.0);
         table2.setLockedWidth(true);
         //------------- 姓名
         cell = new PdfPCell(new Phrase("姓名",setFont(numTX,true)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- 工作单位
         cell = new PdfPCell(new Phrase("工作单位",setFont(numTX,true)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- 职称/职务
         cell = new PdfPCell(new Phrase("职称/职务",setFont(numTX,true)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- 签名
         cell = new PdfPCell(new Phrase("签名",setFont(numTX,true)));
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
         //------------- 姓名
         cell = new PdfPCell(new Phrase(user.getUsername(),setFont(numTX,false)));
         	cell.setColspan(2);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- 工作单位
         cell = new PdfPCell(new Phrase(user.getUnit(),setFont(numTX,false)));
         	cell.setColspan(4);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- 职称/职务
         cell = new PdfPCell(new Phrase(user.getPost(),setFont(numTX,false)));
         	cell.setColspan(3);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(25f); 
         	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table2.addCell(cell);
         //------------- 签名
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
         cell = new PdfPCell(new Phrase("评\n审\n组\n意\n见\n及\n结\n论",setFont(numTX,true)));
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
         cell = new PdfPCell(new Phrase("评审组长签名：",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("日期：",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("分\n支\n机\n构\n意\n见",setFont(numTX,true)));
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
         cell = new PdfPCell(new Phrase("负责人签名：",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("分支机构盖章：          年    月    日",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("中\n国\n物\n品\n编\n码\n中\n心\n意\n见",setFont(numTX,true)));
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
         cell = new PdfPCell(new Phrase("批准人签名：",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(40f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("编码中心盖章：",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(40f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("核准人签名：",setFont(numTX,false)));
         	cell.setColspan(5);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(40f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table3.addCell(cell);
         //------------- 
         cell = new PdfPCell(new Phrase("年    月    日",setFont(numTX,false)));
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
		//输出路径
		String fileDesc = "";
		// 创建输出流	
		FileOutputStream outr = null;
		fileDesc=filePath;
		 
		
		 Certposition cert = certService.getUsing();
		 //控制内容方向
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
         //企业名称
         Font font1= new Font(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED),40,Font.BOLD,BaseColor.BLACK);
         //分中心名称
         Font font2= new Font(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED),25,Font.BOLD,BaseColor.BLACK);
         //其他
         Font font3= new Font(BaseFont.createFont("Times-Roman","Cp1252", BaseFont.NOT_EMBEDDED),25);
         //日期
         Font font5= new Font(BaseFont.createFont("Times-Roman","Cp1252", BaseFont.NOT_EMBEDDED),25,Font.BOLD,BaseColor.BLACK);
         //英文名称
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
	     //空行
         for(int i=0;i<11;i++)document.add(par0);
	     
         //证书号
//	     paragraph =new Paragraph(app.getCertificateno(),setFont(24,false));
	     paragraph =new Paragraph(app.getRealSerial(),font5);
	     paragraph.setSpacingBefore(5);
         paragraph.setAlignment(Element.ALIGN_RIGHT);
         document.add(paragraph);   
         //企业名称
//         paragraph =new Paragraph(app.getEnterprisename(),setFont(37,true));
         paragraph =new Paragraph(app.getEnterprisename(),font1);
         paragraph.setAlignment(Element.ALIGN_LEFT);
         //向右缩进
         paragraph.setIndentationLeft(10); 
         document.add(paragraph);
         //英文名称
         paragraph =new Paragraph(app.getEnglishname(),font4);
         paragraph.setAlignment(Element.ALIGN_LEFT);
         //向右缩进
         paragraph.setIndentationLeft(10);
         document.add(paragraph);
//         for(int i=0;i<11;i++)document.add(par1);
         for(int i=0;i<23;i++)document.add(par1);
         //日期
         //空行
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
         //空行
         //for(int i=0;i<1;i++)document.add(par0);
         //分中心
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
         //向右缩进
         paragraph.setIndentationLeft(700);  
         document.add(paragraph);
         
         document.close();
	     outr.close();
		 }catch (Exception e) {
				e.printStackTrace();
		 }
	}

	public void createPDFComCertFB(CompanyInfo app,String filePath){
		//输出路径
		String fileDesc = "";
		// 创建输出流	
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
       //其他
         Font font1= new Font(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED),14);
         //编号+日期
         Font font3= new Font(BaseFont.createFont("Times-Roman","Cp1252", BaseFont.NOT_EMBEDDED),12);
         //英文名称
         Font font4= new Font(BaseFont.createFont("Times-Roman","Cp1252", BaseFont.NOT_EMBEDDED),10,Font.BOLD,BaseColor.BLACK);
         //中文名称
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
         //------------- 企业名称
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
         //------------- 证书号
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
         //------------- 地址
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
         //------------- 法人
         cell = new PdfPCell(new Phrase(app.getArtificialperson(),font1));
         	cell.setColspan(4);
         	cell.setBorderWidth(border);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(80f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         	cell.setPaddingBottom(12f);
         table1.addCell(cell);
         
         //------------- 有效期
         //日期
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
         //------------- 空
         cell = new PdfPCell(new Phrase(" ",font1));
         	cell.setRowspan(2);
         	cell.setColspan(4);
         	cell.setBorderWidth(border);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(160f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);
         //------------- 技术
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
         //------------- 材料
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
         //------------- 发证日期
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
         //------------- 空
         cell = new PdfPCell(new Phrase(" ",font1));
         	cell.setRowspan(2);
         	cell.setColspan(3);
         	cell.setBorderWidth(border);
         	cell.setUseAscender(true);
         	cell.setFixedHeight(100f); 
         	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
         table1.addCell(cell);

         //------------- 分支机构
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
         //------------- 空
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
		//输出路径
				String fileDesc = "";
				// 创建输出流	
				FileOutputStream outr = null;
	
				fileDesc=filePath;
				//大标题
				 int numD=20;
				 
				 //小标题
				 int numX=14;
				 
				 //表格大
				 int numTD=14;
				 
				 //表格小
				 int numTX=14;
				 
				 //表格中
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
			     
			     paragraph =new Paragraph("编号 "+form.getTitleno(),setFont(10,false));
		         paragraph.setAlignment(Element.ALIGN_RIGHT);
		         document.add(paragraph);
			     
		         //空行
		         for(int i=0;i<4;i++)document.add(par0);
		         
		         
		         //商  品  条  码  印  刷  资  格
		         paragraph =new Paragraph("商  品  条  码  印  刷  资  格",setFont(numD,false));
		         paragraph.setAlignment(Element.ALIGN_CENTER);
		         document.add(paragraph);
		         
			     document.add(par0);
		         
			     //申 请 表
			     paragraph=new Paragraph("变 更 申 请 表",setFont(26,true));
			     paragraph.setAlignment(Element.ALIGN_CENTER);
			     document.add(paragraph);
		         
			     //空行
		         for(int i=0;i<12;i++)document.add(par0);
		         
		         //申请企业名称
		         String entName = String.format("%-20s", form.getCompanynameOld());
		         paragraph=new Paragraph("                                                     申请企业名称：  ",setFont(numX,false));
			       
		         chunk = new Chunk(entName,setFontUnderline(numX,false));
		         paragraph.add(chunk);
		         
		         chunk = new Chunk("(盖章)",setFontUnderline(numX,false));
		         paragraph.add(chunk);
		         
		         paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
			     
			     document.add(par0);
			     
			     //申   请   日   期
			     SimpleDateFormat sdf0=new SimpleDateFormat("yyyy-MM-dd");   
		         String str = String.format("%-28s", sdf0.format(form.getCreatedate()));
			     
			     //paragraph=new Paragraph("                                                      申   请   日   期：  "+str,setFontUnderline(numX,true));
			     paragraph=new Paragraph("                                                      申   请   日   期：  ",setFont(numX,false));
		         
		         chunk = new Chunk(str,setFontUnderline(numX,false));
		         paragraph.add(chunk);
		         
			     paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
			        
			     //空行
		         for(int i=0;i<10;i++)document.add(par0);
			     
		         paragraph=new Paragraph("中国物品编码中心制",setFont(numX,false));
		         paragraph.setAlignment(Element.ALIGN_CENTER);
		         document.add(paragraph);
		         
		         //第二页
		         document.newPage();
			     
			     
			     
			     
			     
		         //空行
		         for(int i=0;i<4;i++)document.add(par0);
		         
		         
		         //条码印刷资格认定企业信息变更登记表
		         paragraph =new Paragraph("条码印刷资格认定企业信息变更登记表",setFont(numD,false));
		         paragraph.setAlignment(Element.ALIGN_CENTER);
		         document.add(paragraph);
		         
			     document.add(par0);
		         
			     //证书号
			     paragraph=new Paragraph("证书号: 物编印证第 "+certNo+" 号",setFont(14,false));
			     paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
			     document.add(par0);
			     //发证有效期
			     SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日"); 
			     paragraph=new Paragraph("发证有效期: "+form.getCertappdateOld()+"至"+form.getCerttodateOld(),setFont(14,false));
			     paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
			     document.add(par0);
			     //填表日期
			     paragraph=new Paragraph("填表日期: "+sdf.format(form.getCreatedate()),setFont(14,false));
			     paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
			     document.add(par0);
		         	PdfPTable table1 = new PdfPTable(12);
		         	table1.setTotalWidth((float)520.0);
		            table1.setLockedWidth(true);
		            //------------- 变更项目
		            cell = new PdfPCell(new Phrase("变更项目",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 原内容
		            cell = new PdfPCell(new Phrase("原内容",setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 变更内容
		            cell = new PdfPCell(new Phrase("变更内容",setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- 企业名称
		            cell = new PdfPCell(new Phrase("企业名称",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 原内容
		            cell = new PdfPCell(new Phrase(form.getCompanynameOld(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 变更内容
		            cell = new PdfPCell(new Phrase(form.getCompanynameNew(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- 法人代表
		            cell = new PdfPCell(new Phrase("法人代表",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 原内容
		            cell = new PdfPCell(new Phrase(form.getCoporationOld(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 变更内容
		            cell = new PdfPCell(new Phrase(form.getCoporationNew(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- 地址
		            cell = new PdfPCell(new Phrase("地址",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 原内容
		            cell = new PdfPCell(new Phrase(form.getAddressOld(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 变更内容
		            cell = new PdfPCell(new Phrase(form.getAddressNew(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- 邮编
		            cell = new PdfPCell(new Phrase("邮编",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 原内容
		            cell = new PdfPCell(new Phrase(form.getPostcodeOld(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 变更内容
		            cell = new PdfPCell(new Phrase(form.getPostcodeNew(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- 电话
		            cell = new PdfPCell(new Phrase("电话",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 原内容
		            cell = new PdfPCell(new Phrase(form.getLinkmantelOld(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 变更内容
		            cell = new PdfPCell(new Phrase(form.getLinkmantelNew(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- 传真
		            cell = new PdfPCell(new Phrase("传真",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 原内容
		            cell = new PdfPCell(new Phrase("",setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 变更内容
		            cell = new PdfPCell(new Phrase("",setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- 联系人
		            cell = new PdfPCell(new Phrase("联系人",setFont(numTX,false)));
		            	cell.setColspan(2);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 原内容
		            cell = new PdfPCell(new Phrase(form.getLinkmanOld(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		            //------------- 变更内容
		            cell = new PdfPCell(new Phrase(form.getLinkmanNew(),setFont(numTX,false)));
		            	cell.setColspan(5);
		            	cell.setUseAscender(true);
		            	cell.setFixedHeight(25f); 
		            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table1.addCell(cell);
		          //------------- 填表人签字
		            cell = new PdfPCell(new Phrase("填表人签字",setFont(numTX,false)));
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
		            //------------- 负责人签字
		            cell = new PdfPCell(new Phrase("负责人签字",setFont(numTX,false)));
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
		          //------------- 备注
		            cell = new PdfPCell(new Phrase("备注",setFont(numTX,false)));
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
		       //申请企业公章
			     paragraph=new Paragraph("申请企业公章                                 年        月        日",setFont(14,false));
			     paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
			     
			     document.add(par0);
			     document.add(par0);
			     document.add(par0);
			   //分支机构盖章
			     paragraph=new Paragraph("分支机构盖章                                 年        月        日",setFont(14,false));
			     paragraph.setAlignment(Element.ALIGN_LEFT);
			     document.add(paragraph);
		       
		         document.close();
			     outr.close();
				 }catch (Exception e) {
						e.printStackTrace();
				 }
		
	}
//打印寄送信息单
//	public void createPDFSendmsg(ExpressInfo express, String filePath,String companyName) {
//		//输出路径
//		String fileDesc = "";
//		// 创建输出流	
//		FileOutputStream outr = null;
//
//		fileDesc=filePath;
//		//大标题
//		 int numD=20;
//		 
//		 //小标题
//		 int numX=14;
//		 
//		 //表格大
//		 int numTD=14;
//		 
//		 //表格小
//		 int numTX=14;
//		 
//		 //表格中
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
//         //寄送信息
//         paragraph =new Paragraph(companyName+"寄送信息",setFont(numD,false));
//         paragraph.setAlignment(Element.ALIGN_CENTER);
//         document.add(paragraph);
//         
//	     document.add(par0);
//         
//	     //打印时间
//	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日"); 
//	     paragraph=new Paragraph("时间: "+sdf.format(new Date()),setFont(14,false));
//	     paragraph.setAlignment(Element.ALIGN_LEFT);
//	     document.add(paragraph);
//	     document.add(par0);
//	     //寄件人：
//	     paragraph=new Paragraph("寄件人: ",setFont(14,false));
//	     paragraph.setAlignment(Element.ALIGN_LEFT);
//	     document.add(paragraph);
//	     document.add(par0);
//	     //第一个表格
//         	PdfPTable table1 = new PdfPTable(12);
//         	table1.setTotalWidth((float)520.0);
//            table1.setLockedWidth(true);
//            //------------- 快递名称
//            cell = new PdfPCell(new Phrase("快递名称",setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//            //------------- 订单号
//            cell = new PdfPCell(new Phrase("订单号",setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//            //------------- 联系人
//            cell = new PdfPCell(new Phrase("联系人",setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//          //------------- 联系人电话
//            cell = new PdfPCell(new Phrase("联系人电话",setFont(numTX,false)));
//        	cell.setColspan(2);
//        	cell.setUseAscender(true);
//        	cell.setFixedHeight(25f); 
//        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        	table1.addCell(cell);
//         //------------- 联系人地址
//	        cell = new PdfPCell(new Phrase("联系人地址",setFont(numTX,false)));
//	    	cell.setColspan(4);
//	    	cell.setUseAscender(true);
//	    	cell.setFixedHeight(25f); 
//	    	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	    	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	    	table1.addCell(cell);
//          //------------- 名称
//            cell = new PdfPCell(new Phrase(express.getName(),setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//            //------------- 订单号
//            cell = new PdfPCell(new Phrase(express.getNumber(),setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//            //------------- 联系人
//            cell = new PdfPCell(new Phrase(express.getContact(),setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//          //------------- 联系人电话
//            cell = new PdfPCell(new Phrase(express.getPhoneNumber(),setFont(numTX,false)));
//            	cell.setColspan(2);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//          //------------- 联系人地址
//            cell = new PdfPCell(new Phrase(express.getConAddress(),setFont(numTX,false)));
//            	cell.setColspan(4);
//            	cell.setUseAscender(true);
//            	cell.setFixedHeight(25f); 
//            	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table1.addCell(cell);
//            document.add(table1);
//            document.add(par0);
//          //收件人：
//   	     paragraph=new Paragraph("收件人: ",setFont(14,false));
//   	     paragraph.setAlignment(Element.ALIGN_LEFT);
//   	     document.add(paragraph);
//   	     document.add(par0);
//   	     //第二个表格
//        PdfPTable table2 = new PdfPTable(12);
//     	table1.setTotalWidth((float)520.0);
//        table1.setLockedWidth(true);
//      //------------- 收件人
//        cell = new PdfPCell(new Phrase("收件人姓名",setFont(numTX,false)));
//        	cell.setColspan(4);
//        	cell.setUseAscender(true);
//        	cell.setFixedHeight(25f); 
//        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table2.addCell(cell);
//        //------------- 收件人电话
//        cell = new PdfPCell(new Phrase("收件人电话",setFont(numTX,false)));
//        	cell.setColspan(4);
//        	cell.setUseAscender(true);
//        	cell.setFixedHeight(25f); 
//        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table2.addCell(cell);
//        //------------- 收件人地址
//        cell = new PdfPCell(new Phrase("收件人地址",setFont(numTX,false)));
//        	cell.setColspan(4);
//        	cell.setUseAscender(true);
//        	cell.setFixedHeight(25f); 
//        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table2.addCell(cell);
//      //------------- 收件人
//        cell = new PdfPCell(new Phrase(express.getReciveName(),setFont(numTX,false)));
//        	cell.setColspan(4);
//        	cell.setUseAscender(true);
//        	cell.setFixedHeight(25f); 
//        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table2.addCell(cell);
//        //------------- 收件人电话
//        cell = new PdfPCell(new Phrase(express.getRecivePhoneNum(),setFont(numTX,false)));
//        	cell.setColspan(4);
//        	cell.setUseAscender(true);
//        	cell.setFixedHeight(25f); 
//        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        	cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table2.addCell(cell);
//        //------------- 收件人地址
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
	
	
	
	//寄送信息打印
	public void createPDFSendmsg(ExpressInfo express, String filePath,String companyName, String sendUnit, String reciveUnit) {
		//输出路径
		String fileDesc = "";
		// 创建输出流	
		FileOutputStream outr = null;
		fileDesc=filePath;
		
//		 Certposition cert = certService.getUsing();
		//设置边距(左右上下)
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
         
	     //空8行
//         for(int i=0;i<8;i++)document.add(par0);
	     
         //寄件人姓名+电话
	     paragraph =new Paragraph(express.getContact()+"                                                       "+express.getPhoneNumber(),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         document.add(paragraph);   
         
         //空1行
         for(int i=0;i<1;i++)document.add(par0);
         
         //寄件人单位+寄件人地址
         String str=sendUnit+"\n"+express.getConAddress();
         
         paragraph =new Paragraph(str,setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         document.add(paragraph); 
         
//         paragraph =new Paragraph(sendUnit,setFont(16,false));
//         paragraph.setAlignment(Element.ALIGN_LEFT);
//         document.add(paragraph); 
         
         //空1行
//         for(int i=0;i<1;i++)document.add(par0);
         
         //寄件人地址
//         paragraph =new Paragraph(express.getConAddress(),setFont(16,false));
//         paragraph.setAlignment(Element.ALIGN_LEFT);
//         document.add(paragraph);
         
         //空5行
         for(int i=0;i<3;i++)document.add(par0);
         
         //收件人姓名+电话
         paragraph =new Paragraph(express.getReciveName()+"                                                        "+express.getRecivePhoneNum(),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
         paragraph.setSpacingAfter(0);
         document.add(paragraph);
         
//        //空1行
//         for(int i=0;i<1;i++)document.add(par0);
         //时间
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy          MM          dd          hh"); 
         paragraph=new Paragraph(sdf.format(new Date()),setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_RIGHT);
//         paragraph.setIndentationLeft(355); 
         paragraph.setSpacingAfter(0);
         paragraph.setSpacingBefore(0); 
         document.add(paragraph);
         
         //收件人单位
         paragraph=new Paragraph(sendUnit+reciveUnit,setFont(10,false));
         paragraph.setAlignment(Element.ALIGN_LEFT);
//         paragraph.setPaddingTop(-5f);
         document.add(paragraph);
         
         //收件人地址
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
		 System.out.println(sdf.format(new Date())+"时间");
	}
	
}