package com.wzj.service.impl;

import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.ApplicationFormMapper;
import com.wzj.dao.CompanyInfoMapper;
import com.wzj.dao.DivisionMapper;
import com.wzj.pojo.CompanyInfo;
import com.wzj.pojo.Division;

@Service("countService")
public class CountService {
	@Autowired
	private ApplicationFormMapper countMapper;
	@Autowired
	private DivisionMapper divisionMapper;
	@Autowired
	private CompanyInfoMapper companyInfoMapper;
	public int getCount(String branchId,String type,Timestamp stime,Timestamp etime){
		type="%"+type+"%";
		return countMapper.getCount(branchId,type,stime,etime);
	}
	//��ȡ��ʼʱ��
		private Timestamp getStime(Date start){
			Date stime = null;
			if(start==null){//�����ʼʱ��Ϊnull,��ʹ�ñ���ȵĵ�һ����Ϊ��ʼʱ��
				Calendar calendar = Calendar.getInstance();  
		        calendar.clear();  
		        calendar.set(Calendar.YEAR,new Date().getYear());
		        stime = calendar.getTime();
			}else{
				stime=start;
			}
	        Timestamp s = new Timestamp(stime.getTime());
	        return s;
		}
		//��ȡ����ʱ��
		private  Timestamp getEtime(Date edate){
			Date etime=null;
			if(edate==null){//���û�н���ʱ�䣬��ȡ���ڵ�ʱ����Ϊ����ʱ��
				etime=new Date();
			}else{
				Calendar gregorian = new GregorianCalendar();
				gregorian.setTime(edate);
				gregorian.add(Calendar.DATE,1);
				etime=gregorian.getTime();
			}
			Timestamp end = new Timestamp(etime.getTime());
			return end;
		}
		//�������»�õ�������
		private int getDaysofMonth(int year,int m){
			if(m==1 ||m== 3 ||m== 5 ||m== 7 ||m== 8 ||m==10 ||m==12)
			return 31;
			else if(m == 4||m==6 ||m== 9 ||m== 11)
			return 30;
			else if(m==2){
			if(year %4 ==0 && year %100 !=0){
			return 29;
			}else if(year%400==0){
			return 29;
			}else{
			return 28;
			}
			}
			return 0;
			}
	public boolean exportAll(String title, String filePath) {
		//����excel����д������
	try{
		//����һ��������
		XSSFWorkbook workbook = new XSSFWorkbook();
		//���һ��sheet,sheet��
		XSSFSheet sheet = workbook.createSheet(title);
		//�ϲ���Ԫ�񣬵�һ�С����һ�С���һ�С����һ��
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
		//������һ��
		XSSFRow titleRow = sheet.createRow(0);
		//�������ⵥԪ���ʽ
		XSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		//����һ������
		XSSFFont titlefont = workbook.createFont();
		titlefont.setFontHeight(16);//��������16��
		//������Ӧ�õ���ǰ�ĸ�ʽ��
		titleStyle.setFont(titlefont);
		//�ڵ�һ���д���һ����Ԫ��
		XSSFCell titleCell = titleRow.createCell(0);
		//����ֵ����ʽ�ͱ���
		titleCell.setCellValue(title);
		titleCell.setCellStyle(titleStyle);
		//---------------�����ǵ�һ��-----------------
		sheet.setColumnWidth(0,25 * 256);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
//		sheet.autoSizeColumn(4);
//		sheet.setColumnWidth(5,25 * 256);
		//�����������ĵ�Ԫ���ʽ
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		//���õڶ��б�ͷ
		XSSFRow rowHeader = sheet.createRow(1);
		XSSFCell cell = rowHeader.createCell(0);
		cell.setCellValue("");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(1);
		cell.setCellValue("�°�");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(2);
		cell.setCellValue("����");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(3);
		cell.setCellValue("���");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(4);
		
		//-------------��ͷ��------------
		//���õ�һ��Ϊ���з�����
		int index = 1;
		List<Division> divList=divisionMapper.getAllDivision();
		String app="";
		String recog="";
		String change="";
		int countApp=0;
		int countRecog=0;
		int countChange=0;
		if(divList.size()>0){
			for(int i=0;i<divList.size();i++){
				if(!"0000".equals(divList.get(i).getDivisioncode())){
					index++;//�к�++����2��ʼ
					app=countMapper.getCountByType(divList.get(i).getDivisioncode(),"%XB%");
					recog=countMapper.getCountByType(divList.get(i).getDivisioncode(),"%FR%");
					change=countMapper.getCountByType(divList.get(i).getDivisioncode(),"%BG%");
					countApp+=Integer.parseInt(app);
					countRecog+=Integer.parseInt(recog);
					countChange+=Integer.parseInt(change);
					//����һ������
					XSSFRow row = sheet.createRow(index);
					XSSFCell rowCell = row.createCell(0);//��һ��
					rowCell.setCellValue(divList.get(i).getDivisionname());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(1);//�ڶ���
					rowCell.setCellValue(app);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(2);//������
					rowCell.setCellValue(recog);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(3);//������
					rowCell.setCellValue(change);
					rowCell.setCellStyle(style);
				}
			}
			
			//����һ������
			XSSFRow row = sheet.createRow(index);
			XSSFCell rowCell = row.createCell(0);//��һ��
			rowCell.setCellValue("�ܼ�");
			rowCell.setCellStyle(style);
			rowCell = row.createCell(1);//�ڶ���
			rowCell.setCellValue(countApp);
			rowCell.setCellStyle(style);
			rowCell = row.createCell(2);//������
			rowCell.setCellValue(countRecog);
			rowCell.setCellStyle(style);
			rowCell = row.createCell(3);//������
			rowCell.setCellValue(countChange);
			rowCell.setCellStyle(style);
			
			
			
			
		
			
		}
		
		//���ļ����浽ָ����λ��
		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);
		workbook.close();
		return true;
	}catch(Exception e){
		e.printStackTrace();
		return false;
	}
	}

	public boolean exportByTime(String title, String filePath, Timestamp start, Timestamp end) {
		//����excel����д������
		try{
			//����һ��������
			XSSFWorkbook workbook = new XSSFWorkbook();
			//���һ��sheet,sheet��
			XSSFSheet sheet = workbook.createSheet(title);
			//�ϲ���Ԫ�񣬵�һ�С����һ�С���һ�С����һ��
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
			//������һ��
			XSSFRow titleRow = sheet.createRow(0);
			//�������ⵥԪ���ʽ
			XSSFCellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			//����һ������
			XSSFFont titlefont = workbook.createFont();
			titlefont.setFontHeight(16);//��������16��
			//������Ӧ�õ���ǰ�ĸ�ʽ��
			titleStyle.setFont(titlefont);
			//�ڵ�һ���д���һ����Ԫ��
			XSSFCell titleCell = titleRow.createCell(0);
			//����ֵ����ʽ�ͱ���
			titleCell.setCellValue(title);
			titleCell.setCellStyle(titleStyle);
			//---------------�����ǵ�һ��-----------------
			sheet.setColumnWidth(0,25 * 256);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			//�����������ĵ�Ԫ���ʽ
			XSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//���õڶ��б�ͷ
			XSSFRow rowHeader = sheet.createRow(1);
			XSSFCell cell = rowHeader.createCell(0);
			cell.setCellValue("");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);
			cell.setCellValue("�°�");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);
			cell.setCellValue("����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);
			cell.setCellValue("���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(4);
			
			//-------------��ͷ��------------
			//���õ�һ��Ϊ���з�����
			int index = 1;
			List<Division> divList=divisionMapper.getAllDivision();
			int app=0;
			int recog=0;
			int change=0;
			int countApp=0;
			int countRecog=0;
			int countChange=0;
			if(divList.size()>0){
				for(int i=0;i<divList.size();i++){
					index++;//�к�++����2��ʼ
					app=countMapper.getCount(divList.get(i).getDivisioncode(),"%XB%",start,end);
					recog=countMapper.getCount(divList.get(i).getDivisioncode(),"%FR%",start,end);
					change=countMapper.getCount(divList.get(i).getDivisioncode(),"%BG%",start,end);
					countApp+=app;
					countRecog+=recog;
					countChange+=change;
					//����һ������
					XSSFRow row = sheet.createRow(index);
					XSSFCell rowCell = row.createCell(0);//��һ��
					rowCell.setCellValue(divList.get(i).getDivisionname());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(1);//�ڶ���
					rowCell.setCellValue(app);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(2);//������
					rowCell.setCellValue(recog);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(3);//������
					rowCell.setCellValue(change);
					rowCell.setCellStyle(style);
				}
				
				//����һ������
				XSSFRow row = sheet.createRow(index);
				XSSFCell rowCell = row.createCell(0);//��һ��
				rowCell.setCellValue("�ܼ�");
				rowCell.setCellStyle(style);
				rowCell = row.createCell(1);//�ڶ���
				rowCell.setCellValue(countApp);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(2);//������
				rowCell.setCellValue(countRecog);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(3);//������
				rowCell.setCellValue(countChange);
				rowCell.setCellStyle(style);
				
			}
			
			//���ļ����浽ָ����λ��
			FileOutputStream out = new FileOutputStream(filePath);
			workbook.write(out);
			workbook.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	//����һ��������
	XSSFWorkbook  workbook= new XSSFWorkbook();
	public boolean exportByBranch(String filePath, String time, String branchId) {
		XSSFWorkbook  workbook= new XSSFWorkbook();
		//����excel����д������
		String[] branch=branchId.split(",");
		String title="";
		try{
			for (int j=0;j<branch.length;j++) {
			Division div=divisionMapper.getDivisionByCode(branch[j]);
			title=div.getDivisionname();
			//����һ��������
//				XSSFWorkbook workbook = new XSSFWorkbook();
			//���һ��sheet,sheet��
			XSSFSheet sheet=workbook.createSheet(title+"ҵ��ͳ������");
			//�ϲ���Ԫ�񣬵�һ�С����һ�С���һ�С����һ��
			 sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
			//������һ��
			XSSFRow titleRow = sheet.createRow(0);
			//�������ⵥԪ���ʽ
			XSSFCellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			//����һ������
			XSSFFont titlefont = workbook.createFont();
			titlefont.setFontHeight(16);//��������16��
			//������Ӧ�õ���ǰ�ĸ�ʽ��
			titleStyle.setFont(titlefont);
			//�ڵ�һ���д���һ����Ԫ��
			XSSFCell titleCell = titleRow.createCell(0);
			//����ֵ����ʽ�ͱ���
			titleCell.setCellValue(title+"ҵ��ͳ������");
			titleCell.setCellStyle(titleStyle);
			//---------------�����ǵ�һ��-----------------
			sheet.setColumnWidth(0,25 * 256);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			//�����������ĵ�Ԫ���ʽ
			XSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//���õڶ��б�ͷ
			XSSFRow rowHeader = sheet.createRow(1);
			XSSFCell cell = rowHeader.createCell(0);
			cell.setCellValue("ʱ��");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);
			cell.setCellValue("�°�");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);
			cell.setCellValue("����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);
			cell.setCellValue("���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(4);
			
			//-------------��ͷ��------------
			//���õ�һ��Ϊ���з�����
			int index = 1;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int app=0;
			int recog=0;
			int change=0;
			int countApp=0;
			int countRecog=0;
			int countChange=0;
//				�������һ��
			int endDay=0;
//				������ʼʱ��
			String month1="";
//				������ֹʱ��
			String month2="";
		for(int i=1;i<=12;i++){
			endDay=getDaysofMonth(Integer.parseInt(time),i);
			month1=time+"-"+String.valueOf(i)+"-"+"01";
			month2=time+"-"+String.valueOf(i)+"-"+String.valueOf(endDay);
			Date sd=sdf.parse(month1);
	        Timestamp s = getStime(sd);//��ȡ��ʼʱ��
	        Date ed=sdf.parse(month2);
			Timestamp e = getEtime(ed);//��ȡ��ֹʱ��
				index++;//�к�++����2��ʼ
				app=countMapper.getCount(branch[j],"%XB%",s,e);
				recog=countMapper.getCount(branch[j],"%FR%",s,e);
				change=countMapper.getCount(branch[j],"%BG%",s,e);
				countApp+=app;
				countRecog+=recog;
				countChange+=change;
				//����һ������
				XSSFRow row = sheet.createRow(index);
				XSSFCell rowCell = row.createCell(0);//��һ��
				rowCell.setCellValue(i+"��");
				rowCell.setCellStyle(style);
				rowCell = row.createCell(1);//�ڶ���
				rowCell.setCellValue(app);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(2);//������
				rowCell.setCellValue(recog);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(3);//������
				rowCell.setCellValue(change);
				rowCell.setCellStyle(style);
			}
					
			//����һ������
			XSSFRow row = sheet.createRow(index);
			XSSFCell rowCell = row.createCell(0);//��һ��
			rowCell.setCellValue("�ܼ�");
			rowCell.setCellStyle(style);
			rowCell = row.createCell(1);//�ڶ���
			rowCell.setCellValue(countApp);
			rowCell.setCellStyle(style);
			rowCell = row.createCell(2);//������
			rowCell.setCellValue(countRecog);
			rowCell.setCellStyle(style);
			rowCell = row.createCell(3);//������
			rowCell.setCellValue(countChange);
			rowCell.setCellStyle(style);
			
			//�°�
			//���һ��sheet,sheet��
			sheet = workbook.createSheet(title+"�°�");
			//�ϲ���Ԫ�񣬵�һ�С����һ�С���һ�С����һ��
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));
			//������һ��
			titleRow = sheet.createRow(0);
			//�������ⵥԪ���ʽ
			titleStyle= workbook.createCellStyle();
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			//����һ������
			titlefont= workbook.createFont();
			titlefont.setFontHeight(16);//��������16��
			//������Ӧ�õ���ǰ�ĸ�ʽ��
			titleStyle.setFont(titlefont);
			//�ڵ�һ���д���һ����Ԫ��
			titleCell = titleRow.createCell(0);
			//����ֵ����ʽ�ͱ���
			titleCell.setCellValue(title+"���°�ҵ����ҵ��Ϣ");
			titleCell.setCellStyle(titleStyle);
			//---------------�����ǵ�һ��-----------------
			sheet.autoSizeColumn(0);
			sheet.setColumnWidth(1,25 * 256);
			sheet.setColumnWidth(2,25 * 256);
			sheet.setColumnWidth(3,25 * 512);
			sheet.autoSizeColumn(4);
			sheet.setColumnWidth(5,25 * 256);
			sheet.setColumnWidth(6,25 * 256);
			sheet.setColumnWidth(7,25 * 256);
			sheet.setColumnWidth(8,25 * 256);
			sheet.setColumnWidth(9,25 * 256);
			sheet.setColumnWidth(10,25 * 256);
			sheet.setColumnWidth(11,25 * 256);
			sheet.setColumnWidth(12,25 * 256);
			sheet.setColumnWidth(13,25 * 256);
			sheet.setColumnWidth(14,25 * 256);
			sheet.setColumnWidth(15,25 * 256);
			sheet.setColumnWidth(16,25 * 512);
			sheet.setColumnWidth(17,25 * 512);
			sheet.setColumnWidth(18,25 * 512);
			sheet.setColumnWidth(19,25 * 512);
			
			//�����������ĵ�Ԫ���ʽ
			style= workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//���õڶ��б�ͷ
			rowHeader = sheet.createRow(1);
			cell = rowHeader.createCell(0);
			cell.setCellValue("���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);
			cell.setCellValue("״̬");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);
			cell.setCellValue("��֧����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);
			cell.setCellValue("��ҵ����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(4);
			cell.setCellValue("֤����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(5);
			cell.setCellValue("��֤����");
			cell.setCellStyle(style);
			cell= rowHeader.createCell(6);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(7);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(8);
			cell.setCellValue("��ҵ����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(9);
			cell.setCellValue("ע���ʱ�");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(10);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(11);
			cell.setCellValue("����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(12);
			cell.setCellValue("���˴���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(13);
			cell.setCellValue("���˵绰");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(14);
			cell.setCellValue("��ϵ��");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(15);
			cell.setCellValue("��ϵ�˵绰");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(16);
			cell.setCellValue("����ӡˢ����������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(17);
			cell.setCellValue("����ӡˢ��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(18);
			cell.setCellValue("ӡˢ�������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(19);
			cell.setCellValue("��ע");
			cell.setCellStyle(style);
			
			//-------------��ͷ��------------
			//���õ�һ��Ϊ״̬
			int index1 = 1;
			String status="";
			Integer ywStatus;
			List<CompanyInfo> coms=companyInfoMapper.getByLike(branch[j], "%XB%");
			String certappdate ="";
			String certtodate="";
			String createdate="";
			for(int i=0;i<coms.size();i++){
				String Typ="";
				String Mat="";
			index1++;//�к�++����2��ʼ
			//����һ������
			row = sheet.createRow(index1);
			//����  ���
			rowCell = row.createCell(0);
			rowCell.setCellValue((i+1)+"");
			rowCell.setCellStyle(style);
			//��һ��  ״̬
			rowCell = row.createCell(1);
			ywStatus=coms.get(i).getStatus();
			if(ywStatus!=null){
				if(ywStatus==37){
					status="������";
				}else if(ywStatus==34){
					status="�������";
				}else if(ywStatus==36){
					status="�����";
				}else if(ywStatus==35){
					status="������";
				}else if(ywStatus==17){
					status="�������";
				}
			}
			rowCell.setCellValue(status);
			rowCell.setCellStyle(style);
			//�ڶ��� ��֧��������
			rowCell = row.createCell(2);
			rowCell.setCellValue(coms.get(i).getBranchName());
			rowCell.setCellStyle(style);
			//������  ��ҵ����
			rowCell = row.createCell(3);
			rowCell.setCellValue(coms.get(i).getEnterprisename());
			rowCell.setCellStyle(style);
			//������  ֤����
			rowCell = row.createCell(4);
			String str = "";
			if(coms.get(i).getSerial()!=null){
				str=coms.get(i).getSerial()+"";
			int len = coms.get(i).getSerial().toString().length();
              len = 6-len;
              for(int n=0;n<len;n++){
            	  str = "0"+str;
              }
             }
			rowCell.setCellValue(str);
			rowCell.setCellStyle(style);
			//������  ��֤����
			rowCell = row.createCell(5);
			if(coms.get(i).getCertappdate()!=null){
				certappdate = sdf.format(coms.get(i).getCertappdate());
				rowCell.setCellValue(certappdate);
			}else{
				rowCell.setCellValue("");
			}
			rowCell.setCellStyle(style);
			//������  ��������
			rowCell = row.createCell(6);
			if(coms.get(i).getCerttodate()!=null){
				certtodate=sdf.format(coms.get(i).getCerttodate());
				rowCell.setCellValue(certtodate);
			}else{
				rowCell.setCellValue("");
			}
			rowCell.setCellStyle(style);
			//������  ��������
			rowCell = row.createCell(7);
			if(coms.get(i).getCreatedate()!=null){
				createdate=sdf.format(coms.get(i).getCreatedate());
				rowCell.setCellValue(createdate);
			}else{
				rowCell.setCellValue("");
			}
			rowCell.setCellStyle(style);
			//�ڰ���  ��ҵ����
			rowCell = row.createCell(8);
			rowCell.setCellValue(coms.get(i).getEnterprisekind());
			rowCell.setCellStyle(style);
			//�ھ���  ע���ʱ�
			rowCell = row.createCell(9);
			rowCell.setCellValue(coms.get(i).getRegistercapital());
			rowCell.setCellStyle(style);
			//��ʮ��  ��������
			rowCell = row.createCell(10);
			rowCell.setCellValue(coms.get(i).getPostalcode());
			rowCell.setCellStyle(style);
			//��ʮһ�� ����
			rowCell = row.createCell(11);
			rowCell.setCellValue(coms.get(i).getFax());
			rowCell.setCellStyle(style);
			//ʮ����  ���˴���
			rowCell = row.createCell(12);
			rowCell.setCellValue(coms.get(i).getArtificialperson());
			rowCell.setCellStyle(style);
			//��ʮ����  ���˵绰
			rowCell = row.createCell(13);
			rowCell.setCellValue(coms.get(i).getAptel());
			rowCell.setCellStyle(style);
			//��ʮ����  ��ϵ��
			rowCell = row.createCell(14);
			rowCell.setCellValue(coms.get(i).getLinkman());
			rowCell.setCellStyle(style);
			//��ʮ����  ��ϵ�˵绰
			rowCell = row.createCell(15);
			rowCell.setCellValue(coms.get(i).getLtel());
			rowCell.setCellStyle(style);
			//��ʮ����  ����ӡˢ����������
			rowCell = row.createCell(16);
			rowCell.setCellValue(coms.get(i).getBcprincipal());
			rowCell.setCellStyle(style);
			//��ʮ����  ����ӡˢ��������
			rowCell = row.createCell(17);
			if(coms.get(i).getFlat())Typ+="��ƽ�潺ӡ";
			if(coms.get(i).getGravure())Typ+="������ӡˢ";
			if(coms.get(i).getWebby())Typ+="��˿��ӡˢ";
			if(coms.get(i).getFlexible())Typ+="�����԰�ӡˢ";
			if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="��"+coms.get(i).getElsetype();
//			Typ=Typ.substring(1, Typ.length());
			if(Typ.length()==0){
				rowCell.setCellValue("");
			}else{
				rowCell.setCellValue(Typ.substring(1, Typ.length()));
			}
			rowCell.setCellStyle(style);
			//��ʮ����  ӡˢ�������
			rowCell = row.createCell(18);
			if(coms.get(i).getPapery())Mat+="��ֽ��";
			if(coms.get(i).getPastern())Mat+="�����ɽ�";
			if(coms.get(i).getFrilling())Mat+="������ֽ";
			if(coms.get(i).getMetal())Mat+="������";
			if(coms.get(i).getPlastic())Mat+="������";
			if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="��"+coms.get(i).getElsematerial();
			if(Mat.length()==0){
				rowCell.setCellValue("");
			}else{
				rowCell.setCellValue(Mat.substring(1, Mat.length()));
			}
			rowCell.setCellStyle(style);
			//��ʮ����  ��ע
			rowCell = row.createCell(19);
			rowCell.setCellValue(coms.get(i).getRemarks());
			rowCell.setCellStyle(style);
		}
				
				
				
		//����
		//���һ��sheet,sheet��
		sheet = workbook.createSheet(title+"����");
		//�ϲ���Ԫ�񣬵�һ�С����һ�С���һ�С����һ��
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));
		//������һ��
		titleRow = sheet.createRow(0);
		//�������ⵥԪ���ʽ
		titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		//����һ������
		titlefont = workbook.createFont();
		titlefont.setFontHeight(16);//��������16��
		//������Ӧ�õ���ǰ�ĸ�ʽ��
		titleStyle.setFont(titlefont);
		//�ڵ�һ���д���һ����Ԫ��
		titleCell = titleRow.createCell(0);
		//����ֵ����ʽ�ͱ���
		titleCell.setCellValue(title+"�ĸ���ҵ����ҵ��Ϣ");
		titleCell.setCellStyle(titleStyle);
		//---------------�����ǵ�һ��-----------------
		sheet.autoSizeColumn(0);
		sheet.setColumnWidth(1,25 * 256);
		sheet.setColumnWidth(2,25 * 256);
		sheet.setColumnWidth(3,25 * 512);
		sheet.autoSizeColumn(4);
		sheet.setColumnWidth(5,25 * 256);
		sheet.setColumnWidth(6,25 * 256);
		sheet.setColumnWidth(7,25 * 256);
		sheet.setColumnWidth(8,25 * 256);
		sheet.setColumnWidth(9,25 * 256);
		sheet.setColumnWidth(10,25 * 256);
		sheet.setColumnWidth(11,25 * 256);
		sheet.setColumnWidth(12,25 * 256);
		sheet.setColumnWidth(13,25 * 256);
		sheet.setColumnWidth(14,25 * 256);
		sheet.setColumnWidth(15,25 * 256);
		sheet.setColumnWidth(16,25 * 512);
		sheet.setColumnWidth(17,25 * 512);
		sheet.setColumnWidth(18,25 * 512);
		sheet.setColumnWidth(19,25 * 512);
		//�����������ĵ�Ԫ���ʽ
		style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		//���õڶ��б�ͷ
		rowHeader = sheet.createRow(1);
		cell = rowHeader.createCell(0);
		cell.setCellValue("���");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(1);
		cell.setCellValue("״̬");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(2);
		cell.setCellValue("��֧����");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(3);
		cell.setCellValue("��ҵ����");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(4);
		cell.setCellValue("֤����");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(5);
		cell.setCellValue("��֤����");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(6);
		cell.setCellValue("��������");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(7);
		cell.setCellValue("��������");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(8);
		cell.setCellValue("��ҵ����");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(9);
		cell.setCellValue("ע���ʱ�");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(10);
		cell.setCellValue("��������");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(11);
		cell.setCellValue("����");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(12);
		cell.setCellValue("���˴���");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(13);
		cell.setCellValue("���˵绰");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(14);
		cell.setCellValue("��ϵ��");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(15);
		cell.setCellValue("��ϵ�˵绰");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(16);
		cell.setCellValue("����ӡˢ����������");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(17);
		cell.setCellValue("����ӡˢ��������");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(18);
		cell.setCellValue("ӡˢ�������");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(19);
		cell.setCellValue("��ע");
		cell.setCellStyle(style);
		
		//-------------��ͷ��------------
		//���õ�һ��Ϊ״̬
		int index2 = 1;
		coms=companyInfoMapper.getByLike(branch[j], "%FR%");
			for(int i=0;i<coms.size();i++){
				String Typ="";
				String Mat="";
			index2++;//�к�++����2��ʼ
			//����һ������
			row = sheet.createRow(index2);
			//����  ���
			rowCell = row.createCell(0);
			rowCell.setCellValue((i+1)+"");
			rowCell.setCellStyle(style);
			//��һ��  ״̬
			rowCell = row.createCell(1);
			ywStatus=coms.get(i).getStatus();
			if(ywStatus!=null){
				if(ywStatus==37){
					status="������";
				}else if(ywStatus==34){
					status="�������";
				}else if(ywStatus==36){
					status="�����";
				}else if(ywStatus==35){
					status="������";
				}else if(ywStatus==17){
					status="�������";
				}
			}
			rowCell.setCellValue(status);
			rowCell.setCellStyle(style);
			//�ڶ��� ��֧��������
			rowCell = row.createCell(2);
			rowCell.setCellValue(coms.get(i).getBranchName());
			rowCell.setCellStyle(style);
			//������  ��ҵ����
			rowCell = row.createCell(3);
			rowCell.setCellValue(coms.get(i).getEnterprisename());
			rowCell.setCellStyle(style);
			//������  ֤����
			rowCell = row.createCell(4);
			String str2 = "";
			if(coms.get(i).getSerial()!=null){
				str2=coms.get(i).getSerial()+"";
			int len = coms.get(i).getSerial().toString().length();
              len = 6-len;
              for(int n=0;n<len;n++){
            	  str2 = "0"+str2;
              }
             }
			rowCell.setCellValue(str2);
			rowCell.setCellStyle(style);
			//������  ��֤����
			rowCell = row.createCell(5);
			if(coms.get(i).getCertappdate()!=null){
				certappdate = sdf.format(coms.get(i).getCertappdate());
				rowCell.setCellValue(certappdate);
			}else{
				rowCell.setCellValue("");
			}
			rowCell.setCellStyle(style);
			//������  ��������
			rowCell = row.createCell(6);
			if(coms.get(i).getCerttodate()!=null){
				certtodate=sdf.format(coms.get(i).getCerttodate());
				rowCell.setCellValue(certtodate);
			}else{
				rowCell.setCellValue("");
			}
			rowCell.setCellStyle(style);
			//������  ��������
			rowCell = row.createCell(7);
			if(coms.get(i).getCreatedate()!=null){
				createdate=sdf.format(coms.get(i).getCreatedate());
				rowCell.setCellValue(createdate);
			}else{
				rowCell.setCellValue("");
			}
			rowCell.setCellStyle(style);
			//�ڰ���  ��ҵ����
			rowCell = row.createCell(8);
			rowCell.setCellValue(coms.get(i).getEnterprisekind());
			rowCell.setCellStyle(style);
			//�ھ���  ע���ʱ�
			rowCell = row.createCell(9);
			rowCell.setCellValue(coms.get(i).getRegistercapital());
			rowCell.setCellStyle(style);
			//��ʮ��  ��������
			rowCell = row.createCell(10);
			rowCell.setCellValue(coms.get(i).getPostalcode());
			rowCell.setCellStyle(style);
			//��ʮһ�� ����
			rowCell = row.createCell(11);
			rowCell.setCellValue(coms.get(i).getFax());
			rowCell.setCellStyle(style);
			//ʮ����  ���˴���
			rowCell = row.createCell(12);
			rowCell.setCellValue(coms.get(i).getArtificialperson());
			rowCell.setCellStyle(style);
			//��ʮ����  ���˵绰
			rowCell = row.createCell(13);
			rowCell.setCellValue(coms.get(i).getAptel());
			rowCell.setCellStyle(style);
			//��ʮ����  ��ϵ��
			rowCell = row.createCell(14);
			rowCell.setCellValue(coms.get(i).getLinkman());
			rowCell.setCellStyle(style);
			//��ʮ����  ��ϵ�˵绰
			rowCell = row.createCell(15);
			rowCell.setCellValue(coms.get(i).getLtel());
			rowCell.setCellStyle(style);
			//��ʮ����  ����ӡˢ����������
			rowCell = row.createCell(16);
			rowCell.setCellValue(coms.get(i).getBcprincipal());
			rowCell.setCellStyle(style);
			//��ʮ����  ����ӡˢ��������
			rowCell = row.createCell(17);
			if(coms.get(i).getFlat())Typ+="��ƽ�潺ӡ";
			if(coms.get(i).getGravure())Typ+="������ӡˢ";
			if(coms.get(i).getWebby())Typ+="��˿��ӡˢ";
			if(coms.get(i).getFlexible())Typ+="�����԰�ӡˢ";
			if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="��"+coms.get(i).getElsetype();
//			Typ=Typ.substring(1, Typ.length());
			if(Typ.length()==0){
				rowCell.setCellValue("");
			}else{
				rowCell.setCellValue(Typ.substring(1, Typ.length()));
			}
			rowCell.setCellStyle(style);
			//��ʮ����  ӡˢ�������
			rowCell = row.createCell(18);
			if(coms.get(i).getPapery())Mat+="��ֽ��";
			if(coms.get(i).getPastern())Mat+="�����ɽ�";
			if(coms.get(i).getFrilling())Mat+="������ֽ";
			if(coms.get(i).getMetal())Mat+="������";
			if(coms.get(i).getPlastic())Mat+="������";
			if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="��"+coms.get(i).getElsematerial();
			if(Mat.length()==0){
				rowCell.setCellValue("");
			}else{
				rowCell.setCellValue(Mat.substring(1, Mat.length()));
			}
			rowCell.setCellStyle(style);
			//��ʮ����  ��ע
			rowCell = row.createCell(19);
			rowCell.setCellValue(coms.get(i).getRemarks());
			rowCell.setCellStyle(style);
			}
					
			//���
			//���һ��sheet,sheet��
			sheet = workbook.createSheet(title+"���");
			//�ϲ���Ԫ�񣬵�һ�С����һ�С���һ�С����һ��
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));
			//������һ��
			titleRow = sheet.createRow(0);
			//�������ⵥԪ���ʽ
			titleStyle = workbook.createCellStyle();
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			//����һ������
			titlefont = workbook.createFont();
			titlefont.setFontHeight(16);//��������16��
			//������Ӧ�õ���ǰ�ĸ�ʽ��
			titleStyle.setFont(titlefont);
			//�ڵ�һ���д���һ����Ԫ��
			titleCell = titleRow.createCell(0);
			//����ֵ����ʽ�ͱ���
			titleCell.setCellValue(title+"�ı��ҵ����ҵ��Ϣ");
			titleCell.setCellStyle(titleStyle);
			//---------------�����ǵ�һ��-----------------
			sheet.autoSizeColumn(0);
			sheet.setColumnWidth(1,25 * 256);
			sheet.setColumnWidth(2,25 * 256);
			sheet.setColumnWidth(3,25 * 512);
			sheet.autoSizeColumn(4);
			sheet.setColumnWidth(5,25 * 256);
			sheet.setColumnWidth(6,25 * 256);
			sheet.setColumnWidth(7,25 * 256);
			sheet.setColumnWidth(8,25 * 256);
			sheet.setColumnWidth(9,25 * 256);
			sheet.setColumnWidth(10,25 * 256);
			sheet.setColumnWidth(11,25 * 256);
			sheet.setColumnWidth(12,25 * 256);
			sheet.setColumnWidth(13,25 * 256);
			sheet.setColumnWidth(14,25 * 256);
			sheet.setColumnWidth(15,25 * 256);
			sheet.setColumnWidth(16,25 * 512);
			sheet.setColumnWidth(17,25 * 512);
			sheet.setColumnWidth(18,25 * 512);
			sheet.setColumnWidth(19,25 * 512);
			//�����������ĵ�Ԫ���ʽ
			style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//���õڶ��б�ͷ
			rowHeader = sheet.createRow(1);
			cell = rowHeader.createCell(0);
			cell.setCellValue("���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);
			cell.setCellValue("״̬");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);
			cell.setCellValue("��֧����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);
			cell.setCellValue("��ҵ����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(4);
			cell.setCellValue("֤����");
			cell.setCellStyle(style);
			cell= rowHeader.createCell(5);
			cell.setCellValue("��֤����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(6);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(7);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(8);
			cell.setCellValue("��ҵ����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(9);
			cell.setCellValue("ע���ʱ�");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(10);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(11);
			cell.setCellValue("����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(12);
			cell.setCellValue("���˴���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(13);
			cell.setCellValue("���˵绰");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(14);
			cell.setCellValue("��ϵ��");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(15);
			cell.setCellValue("��ϵ�˵绰");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(16);
			cell.setCellValue("����ӡˢ����������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(17);
			cell.setCellValue("����ӡˢ��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(18);
			cell.setCellValue("ӡˢ�������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(19);
			cell.setCellValue("��ע");
			cell.setCellStyle(style);
			
			//-------------��ͷ��------------
			//���õ�һ��Ϊ״̬
			int index3 = 1;
			coms=companyInfoMapper.getByLike(branch[j], "%BG%");
				for(int i=0;i<coms.size();i++){
					String Typ="";
					String Mat="";
				index3++;//�к�++����2��ʼ
				//����һ������
				row = sheet.createRow(index3);
				//����  ���
				rowCell = row.createCell(0);
				rowCell.setCellValue((i+1)+"");
				rowCell.setCellStyle(style);
				//��һ��  ״̬
				rowCell = row.createCell(1);
				ywStatus=coms.get(i).getStatus();
				if(ywStatus!=null){
					if(ywStatus==37){
						status="������";
					}else if(ywStatus==34){
						status="�������";
					}else if(ywStatus==36){
						status="�����";
					}else if(ywStatus==35){
						status="������";
					}else if(ywStatus==17){
						status="�������";
					}
				}
				rowCell.setCellValue(status);
				rowCell.setCellStyle(style);
				//�ڶ��� ��֧��������
				rowCell = row.createCell(2);
				rowCell.setCellValue(coms.get(i).getBranchName());
				rowCell.setCellStyle(style);
				//������  ��ҵ����
				rowCell = row.createCell(3);
				rowCell.setCellValue(coms.get(i).getEnterprisename());
				rowCell.setCellStyle(style);
				//������  ֤����
				rowCell = row.createCell(4);
				String str3 = "";
				if(coms.get(i).getSerial()!=null){
					str3=coms.get(i).getSerial()+"";
				int len = coms.get(i).getSerial().toString().length();
                  len = 6-len;
                  for(int n=0;n<len;n++){
                	  str3 = "0"+str3;
	              }
                 }
				rowCell.setCellValue(str3);
				rowCell.setCellStyle(style);
				//������  ��֤����
				rowCell = row.createCell(5);
				if(coms.get(i).getCertappdate()!=null){
					certappdate = sdf.format(coms.get(i).getCertappdate());
					rowCell.setCellValue(certappdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//������  ��������
				rowCell = row.createCell(6);
				if(coms.get(i).getCerttodate()!=null){
					certtodate=sdf.format(coms.get(i).getCerttodate());
					rowCell.setCellValue(certtodate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//������  ��������
				rowCell = row.createCell(7);
				if(coms.get(i).getCreatedate()!=null){
					createdate=sdf.format(coms.get(i).getCreatedate());
					rowCell.setCellValue(createdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//�ڰ���  ��ҵ����
				rowCell = row.createCell(8);
				rowCell.setCellValue(coms.get(i).getEnterprisekind());
				rowCell.setCellStyle(style);
				//�ھ���  ע���ʱ�
				rowCell = row.createCell(9);
				rowCell.setCellValue(coms.get(i).getRegistercapital());
				rowCell.setCellStyle(style);
				//��ʮ��  ��������
				rowCell = row.createCell(10);
				rowCell.setCellValue(coms.get(i).getPostalcode());
				rowCell.setCellStyle(style);
				//��ʮһ�� ����
				rowCell = row.createCell(11);
				rowCell.setCellValue(coms.get(i).getFax());
				rowCell.setCellStyle(style);
				//ʮ����  ���˴���
				rowCell = row.createCell(12);
				rowCell.setCellValue(coms.get(i).getArtificialperson());
				rowCell.setCellStyle(style);
				//��ʮ����  ���˵绰
				rowCell = row.createCell(13);
				rowCell.setCellValue(coms.get(i).getAptel());
				rowCell.setCellStyle(style);
				//��ʮ����  ��ϵ��
				rowCell = row.createCell(14);
				rowCell.setCellValue(coms.get(i).getLinkman());
				rowCell.setCellStyle(style);
				//��ʮ����  ��ϵ�˵绰
				rowCell = row.createCell(15);
				rowCell.setCellValue(coms.get(i).getLtel());
				rowCell.setCellStyle(style);
				//��ʮ����  ����ӡˢ����������
				rowCell = row.createCell(16);
				rowCell.setCellValue(coms.get(i).getBcprincipal());
				rowCell.setCellStyle(style);
				//��ʮ����  ����ӡˢ��������
				rowCell = row.createCell(17);
				if(coms.get(i).getFlat())Typ+="��ƽ�潺ӡ";
				if(coms.get(i).getGravure())Typ+="������ӡˢ";
				if(coms.get(i).getWebby())Typ+="��˿��ӡˢ";
				if(coms.get(i).getFlexible())Typ+="�����԰�ӡˢ";
				if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="��"+coms.get(i).getElsetype();
//				Typ=Typ.substring(1, Typ.length());
				if(Typ.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Typ.substring(1, Typ.length()));
				}
				rowCell.setCellStyle(style);
				//��ʮ����  ӡˢ�������
				rowCell = row.createCell(18);
				if(coms.get(i).getPapery())Mat+="��ֽ��";
				if(coms.get(i).getPastern())Mat+="�����ɽ�";
				if(coms.get(i).getFrilling())Mat+="������ֽ";
				if(coms.get(i).getMetal())Mat+="������";
				if(coms.get(i).getPlastic())Mat+="������";
				if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="��"+coms.get(i).getElsematerial();
				if(Mat.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Mat.substring(1, Mat.length()));
				}
				rowCell.setCellStyle(style);
				//��ʮ����  ��ע
				rowCell = row.createCell(19);
				rowCell.setCellValue(coms.get(i).getRemarks());
				rowCell.setCellStyle(style);
				}
		}
			//���ļ����浽ָ����λ��
			FileOutputStream out = new FileOutputStream(filePath);
			workbook.write(out);
			workbook.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	//�������������
	public boolean exportByTwo(String filePath, String d1, String d2, String branchId) {
		String[] branch=branchId.split(",");
		String title="";
		//����excel����д������
		try{
			for(int j=0;j<branch.length;j++){
				Division div=divisionMapper.getDivisionByCode(branch[j]);
				title=div.getDivisionname();
				//����һ��������
//				XSSFWorkbook workbook = new XSSFWorkbook();
				//���һ��sheet,sheet��
				XSSFSheet sheet = workbook.createSheet(title+"ҵ��ͳ������");
				//�ϲ���Ԫ�񣬵�һ�С����һ�С���һ�С����һ��
				sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
				//������һ��
				XSSFRow titleRow = sheet.createRow(0);
				//�������ⵥԪ���ʽ
				XSSFCellStyle titleStyle = workbook.createCellStyle();
				titleStyle.setAlignment(HorizontalAlignment.CENTER);
				//����һ������
				XSSFFont titlefont = workbook.createFont();
				titlefont.setFontHeight(16);//��������16��
				//������Ӧ�õ���ǰ�ĸ�ʽ��
				titleStyle.setFont(titlefont);
				//�ڵ�һ���д���һ����Ԫ��
				XSSFCell titleCell = titleRow.createCell(0);
				//����ֵ����ʽ�ͱ���
				titleCell.setCellValue(title+"ҵ��ͳ������");
				titleCell.setCellStyle(titleStyle);
				//---------------�����ǵ�һ��-----------------
				sheet.setColumnWidth(0,25 * 512);
				sheet.autoSizeColumn(1);
				sheet.autoSizeColumn(2);
				sheet.autoSizeColumn(3);
				//�����������ĵ�Ԫ���ʽ
				XSSFCellStyle style = workbook.createCellStyle();
				style.setAlignment(HorizontalAlignment.CENTER);
				//���õڶ��б�ͷ
				XSSFRow rowHeader = sheet.createRow(1);
				XSSFCell cell = rowHeader.createCell(0);
				cell.setCellValue("����ʱ���");
				cell.setCellStyle(style);
				cell = rowHeader.createCell(1);
				cell.setCellValue("�°�");
				cell.setCellStyle(style);
				cell = rowHeader.createCell(2);
				cell.setCellValue("����");
				cell.setCellStyle(style);
				cell = rowHeader.createCell(3);
				cell.setCellValue("���");
				cell.setCellStyle(style);
				cell = rowHeader.createCell(4);
				
				//-------------��ͷ��------------
				//���õ�һ��Ϊ���з�����
				int index = 1;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				int app=0;
				int recog=0;
				int change=0;
//				int countApp=0;
//				int countRecog=0;
//				int countChange=0;
//				int startMon=Integer.parseInt(d1.substring(5, 7));
//				int	intendMon=Integer.parseInt(d2.substring(5, 7));
//				String year=d1.substring(0, 4);
//		//		�������һ��
//				int endDay=0;
//		//		������ʼʱ��
//				String month1="";
//		//		������ֹʱ��
//				String month2="";
				
//		for(int i=startMon;i<=intendMon;i++){
//			endDay=getDaysofMonth(Integer.parseInt(year),i);
//			month1=year+"-"+String.valueOf(i)+"-"+"01";
//			month2=year+"-"+String.valueOf(i)+"-"+String.valueOf(endDay);
			Date sd=sdf.parse(d1);
		    Timestamp s = getStime(sd);//��ȡ��ʼʱ��
		    Date ed=sdf.parse(d2);
			Timestamp e = getEtime(ed);//��ȡ��ֹʱ��
				index++;//�к�++����2��ʼ
				app=countMapper.getCount(branch[j],"%XB%",s,e);
				recog=countMapper.getCount(branch[j],"%FR%",s,e);
				change=countMapper.getCount(branch[j],"%BG%",s,e);
//				countApp+=app;
//				countRecog+=recog;
//				countChange+=change;
				//����һ������
				XSSFRow row = sheet.createRow(index);
				XSSFCell rowCell = row.createCell(0);//��һ��
				rowCell.setCellValue(s+"~"+e);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(1);//�ڶ���
				rowCell.setCellValue(app);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(2);//������
				rowCell.setCellValue(recog);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(3);//������
				rowCell.setCellValue(change);
				rowCell.setCellStyle(style);
//			}
			//����һ������
//			row = sheet.createRow(index);
//			rowCell = row.createCell(0);//��һ��
//			rowCell.setCellValue("�ܼ�");
//			rowCell.setCellStyle(style);
//			rowCell = row.createCell(1);//�ڶ���
//			rowCell.setCellValue(countApp);
//			rowCell.setCellStyle(style);
//			rowCell = row.createCell(2);//������
//			rowCell.setCellValue(countRecog);
//			rowCell.setCellStyle(style);
//			rowCell = row.createCell(3);//������
//			rowCell.setCellValue(countChange);
//			rowCell.setCellStyle(style);
			
			//�°�
			//���һ��sheet,sheet��
			sheet = workbook.createSheet(title+"�°�");
			//�ϲ���Ԫ�񣬵�һ�С����һ�С���һ�С����һ��
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));
			//������һ��
			XSSFRow titleRow1 = sheet.createRow(0);
			//�������ⵥԪ���ʽ
			XSSFCellStyle titleStyle1 = workbook.createCellStyle();
			titleStyle1.setAlignment(HorizontalAlignment.CENTER);
			//����һ������
			XSSFFont titlefont1 = workbook.createFont();
			titlefont1.setFontHeight(16);//��������16��
			//������Ӧ�õ���ǰ�ĸ�ʽ��
			titleStyle1.setFont(titlefont1);
			//�ڵ�һ���д���һ����Ԫ��
			XSSFCell titleCell1 = titleRow1.createCell(0);
			//����ֵ����ʽ�ͱ���
			titleCell1.setCellValue(title+"���°�ҵ����ҵ��Ϣ");
			titleCell1.setCellStyle(titleStyle1);
			//---------------�����ǵ�һ��-----------------
			sheet.autoSizeColumn(0);
			sheet.setColumnWidth(1,25 * 256);
			sheet.setColumnWidth(2,25 * 256);
			sheet.setColumnWidth(3,25 * 512);
			sheet.autoSizeColumn(4);
			sheet.setColumnWidth(5,25 * 256);
			sheet.setColumnWidth(6,25 * 256);
			sheet.setColumnWidth(7,25 * 256);
			sheet.setColumnWidth(8,25 * 256);
			sheet.setColumnWidth(9,25 * 256);
			sheet.setColumnWidth(10,25 * 256);
			sheet.setColumnWidth(11,25 * 256);
			sheet.setColumnWidth(12,25 * 256);
			sheet.setColumnWidth(13,25 * 256);
			sheet.setColumnWidth(14,25 * 256);
			sheet.setColumnWidth(15,25 * 256);
			sheet.setColumnWidth(16,25 * 512);
			sheet.setColumnWidth(17,25 * 512);
			sheet.setColumnWidth(18,25 * 512);
			sheet.setColumnWidth(19,25 * 512);
			
			//�����������ĵ�Ԫ���ʽ
			style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//���õڶ��б�ͷ
			XSSFRow rowHeader1 = sheet.createRow(1);
			cell = rowHeader1.createCell(0);
			cell.setCellValue("���");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(1);
			cell.setCellValue("״̬");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(2);
			cell.setCellValue("��֧����");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(3);
			cell.setCellValue("��ҵ����");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(4);
			cell.setCellValue("֤����");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(5);
			cell.setCellValue("��֤����");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(6);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(7);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(8);
			cell.setCellValue("��ҵ����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(9);
			cell.setCellValue("ע���ʱ�");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(10);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(11);
			cell.setCellValue("����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(12);
			cell.setCellValue("���˴���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(13);
			cell.setCellValue("���˵绰");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(14);
			cell.setCellValue("��ϵ��");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(15);
			cell.setCellValue("��ϵ�˵绰");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(16);
			cell.setCellValue("����ӡˢ����������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(17);
			cell.setCellValue("����ӡˢ��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(18);
			cell.setCellValue("ӡˢ�������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(19);
			cell.setCellValue("��ע");
			cell.setCellStyle(style);
			
			//-------------��ͷ��------------
			//���õ�һ��Ϊ״̬
			int index1 = 1;
			String status="";
			Integer ywStatus;
			List<CompanyInfo> coms=null;
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//			for(int i=startMon;i<=intendMon;i++){
//				endDay=getDaysofMonth(Integer.parseInt(year),i);
//				month1=year+"-"+String.valueOf(i)+"-"+"01";
//				month2=year+"-"+String.valueOf(i)+"-"+String.valueOf(endDay);
//				Date sd=sdf.parse(month1);
//			    Timestamp s = getStime(sd);//��ȡ��ʼʱ��
//			    Date ed=sdf.parse(month2);
//				Timestamp e = getEtime(ed);//��ȡ��ֹʱ��
			coms=companyInfoMapper.getByTwo(branch[j], "%XB%",s,e);
//			}
			String certappdate ="";
			String certtodate="";
			String createdate="";
				for(int i=0;i<coms.size();i++){
					String Typ="";
					String Mat="";
				index1++;//�к�++����2��ʼ
				//����һ������
				row = sheet.createRow(index1);
				//����  ���
				rowCell = row.createCell(0);
				rowCell.setCellValue((i+1)+"");
				rowCell.setCellStyle(style);
				//��һ��  ״̬
				rowCell = row.createCell(1);
				ywStatus=coms.get(i).getStatus();
				if(ywStatus!=null){
					if(ywStatus==37){
						status="������";
					}else if(ywStatus==34){
						status="�������";
					}else if(ywStatus==36){
						status="�����";
					}else if(ywStatus==35){
						status="������";
					}else if(ywStatus==17){
						status="�������";
					}
				}
				rowCell.setCellValue(status);
				rowCell.setCellStyle(style);
				//�ڶ��� ��֧��������
				rowCell = row.createCell(2);
				rowCell.setCellValue(coms.get(i).getBranchName());
				rowCell.setCellStyle(style);
				//������  ��ҵ����
				rowCell = row.createCell(3);
				rowCell.setCellValue(coms.get(i).getEnterprisename());
				rowCell.setCellStyle(style);
				//������  ֤����
				rowCell = row.createCell(4);
				String str = "";
				if(coms.get(i).getSerial()!=null){
					str=coms.get(i).getSerial()+"";
				int len = coms.get(i).getSerial().toString().length();
                  len = 6-len;
                  for(int n=0;n<len;n++){
                	  str = "0"+str;
	              }
                 }
				rowCell.setCellValue(str);
				rowCell.setCellStyle(style);
				//������  ��֤����
				rowCell = row.createCell(5);
				if(coms.get(i).getCertappdate()!=null){
					certappdate = sdf.format(coms.get(i).getCertappdate());
					rowCell.setCellValue(certappdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//������  ��������
				rowCell = row.createCell(6);
				if(coms.get(i).getCerttodate()!=null){
					certtodate=sdf.format(coms.get(i).getCerttodate());
					rowCell.setCellValue(certtodate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//������  ��������
				rowCell = row.createCell(7);
				if(coms.get(i).getCreatedate()!=null){
					createdate=sdf.format(coms.get(i).getCreatedate());
					rowCell.setCellValue(createdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//�ڰ���  ��ҵ����
				rowCell = row.createCell(8);
				rowCell.setCellValue(coms.get(i).getEnterprisekind());
				rowCell.setCellStyle(style);
				//�ھ���  ע���ʱ�
				rowCell = row.createCell(9);
				rowCell.setCellValue(coms.get(i).getRegistercapital());
				rowCell.setCellStyle(style);
				//��ʮ��  ��������
				rowCell = row.createCell(10);
				rowCell.setCellValue(coms.get(i).getPostalcode());
				rowCell.setCellStyle(style);
				//��ʮһ�� ����
				rowCell = row.createCell(11);
				rowCell.setCellValue(coms.get(i).getFax());
				rowCell.setCellStyle(style);
				//ʮ����  ���˴���
				rowCell = row.createCell(12);
				rowCell.setCellValue(coms.get(i).getArtificialperson());
				rowCell.setCellStyle(style);
				//��ʮ����  ���˵绰
				rowCell = row.createCell(13);
				rowCell.setCellValue(coms.get(i).getAptel());
				rowCell.setCellStyle(style);
				//��ʮ����  ��ϵ��
				rowCell = row.createCell(14);
				rowCell.setCellValue(coms.get(i).getLinkman());
				rowCell.setCellStyle(style);
				//��ʮ����  ��ϵ�˵绰
				rowCell = row.createCell(15);
				rowCell.setCellValue(coms.get(i).getLtel());
				rowCell.setCellStyle(style);
				//��ʮ����  ����ӡˢ����������
				rowCell = row.createCell(16);
				rowCell.setCellValue(coms.get(i).getBcprincipal());
				rowCell.setCellStyle(style);
				//��ʮ����  ����ӡˢ��������
				rowCell = row.createCell(17);
				if(coms.get(i).getFlat())Typ+="��ƽ�潺ӡ";
				if(coms.get(i).getGravure())Typ+="������ӡˢ";
				if(coms.get(i).getWebby())Typ+="��˿��ӡˢ";
				if(coms.get(i).getFlexible())Typ+="�����԰�ӡˢ";
				if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="��"+coms.get(i).getElsetype();
//				Typ=Typ.substring(1, Typ.length());
				if(Typ.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Typ.substring(1, Typ.length()));
				}
				rowCell.setCellStyle(style);
				//��ʮ����  ӡˢ�������
				rowCell = row.createCell(18);
				if(coms.get(i).getPapery())Mat+="��ֽ��";
				if(coms.get(i).getPastern())Mat+="�����ɽ�";
				if(coms.get(i).getFrilling())Mat+="������ֽ";
				if(coms.get(i).getMetal())Mat+="������";
				if(coms.get(i).getPlastic())Mat+="������";
				if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="��"+coms.get(i).getElsematerial();
				if(Mat.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Mat.substring(1, Mat.length()));
				}
				rowCell.setCellStyle(style);
				//��ʮ����  ��ע
				rowCell = row.createCell(19);
				rowCell.setCellValue(coms.get(i).getRemarks());
				rowCell.setCellStyle(style);
				}
				
				
			//����
			//���һ��sheet,sheet��
			sheet = workbook.createSheet(title+"����");
			//�ϲ���Ԫ�񣬵�һ�С����һ�С���һ�С����һ��
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));
			//������һ��
			titleRow1 = sheet.createRow(0);
			//�������ⵥԪ���ʽ
			titleStyle1 = workbook.createCellStyle();
			titleStyle1.setAlignment(HorizontalAlignment.CENTER);
			//����һ������
			titlefont1 = workbook.createFont();
			titlefont1.setFontHeight(16);//��������16��
			//������Ӧ�õ���ǰ�ĸ�ʽ��
			titleStyle1.setFont(titlefont1);
			//�ڵ�һ���д���һ����Ԫ��
			titleCell1 = titleRow1.createCell(0);
			//����ֵ����ʽ�ͱ���
			titleCell1.setCellValue(title+"�ĸ���ҵ����ҵ��Ϣ");
			titleCell1.setCellStyle(titleStyle1);
			//---------------�����ǵ�һ��-----------------
			sheet.autoSizeColumn(0);
			sheet.setColumnWidth(1,25 * 256);
			sheet.setColumnWidth(2,25 * 256);
			sheet.setColumnWidth(3,25 * 512);
			sheet.autoSizeColumn(4);
			sheet.setColumnWidth(5,25 * 256);
			sheet.setColumnWidth(6,25 * 256);
			sheet.setColumnWidth(7,25 * 256);
			sheet.setColumnWidth(8,25 * 256);
			sheet.setColumnWidth(9,25 * 256);
			sheet.setColumnWidth(10,25 * 256);
			sheet.setColumnWidth(11,25 * 256);
			sheet.setColumnWidth(12,25 * 256);
			sheet.setColumnWidth(13,25 * 256);
			sheet.setColumnWidth(14,25 * 256);
			sheet.setColumnWidth(15,25 * 256);
			sheet.setColumnWidth(16,25 * 512);
			sheet.setColumnWidth(17,25 * 512);
			sheet.setColumnWidth(18,25 * 512);
			sheet.setColumnWidth(19,25 * 512);
			//�����������ĵ�Ԫ���ʽ
			style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//���õڶ��б�ͷ
			rowHeader1 = sheet.createRow(1);
			cell = rowHeader1.createCell(0);
			cell.setCellValue("���");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(1);
			cell.setCellValue("״̬");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(2);
			cell.setCellValue("��֧����");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(3);
			cell.setCellValue("��ҵ����");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(4);
			cell.setCellValue("֤����");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(5);
			cell.setCellValue("��֤����");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(6);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(7);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(8);
			cell.setCellValue("��ҵ����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(9);
			cell.setCellValue("ע���ʱ�");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(10);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(11);
			cell.setCellValue("����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(12);
			cell.setCellValue("���˴���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(13);
			cell.setCellValue("���˵绰");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(14);
			cell.setCellValue("��ϵ��");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(15);
			cell.setCellValue("��ϵ�˵绰");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(16);
			cell.setCellValue("����ӡˢ����������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(17);
			cell.setCellValue("����ӡˢ��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(18);
			cell.setCellValue("ӡˢ�������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(19);
			cell.setCellValue("��ע");
			cell.setCellStyle(style);
			//-------------��ͷ��------------
			//���õ�һ��Ϊ״̬
			int index2=1;
			coms=companyInfoMapper.getByTwo(branch[j], "%FR%",s,e);
				for(int i=0;i<coms.size();i++){
					String Typ="";
					String Mat="";
				index2++;//�к�++����2��ʼ
				//����һ������
				row = sheet.createRow(index2);
				//����  ���
				rowCell = row.createCell(0);
				rowCell.setCellValue((i+1)+"");
				rowCell.setCellStyle(style);
				//��һ��  ״̬
				rowCell = row.createCell(1);
				ywStatus=coms.get(i).getStatus();
				if(ywStatus!=null){
					if(ywStatus==37){
						status="������";
					}else if(ywStatus==34){
						status="�������";
					}else if(ywStatus==36){
						status="�����";
					}else if(ywStatus==35){
						status="������";
					}else if(ywStatus==17){
						status="�������";
					}
				}
				rowCell.setCellValue(status);
				rowCell.setCellStyle(style);
				//�ڶ��� ��֧��������
				rowCell = row.createCell(2);
				rowCell.setCellValue(coms.get(i).getBranchName());
				rowCell.setCellStyle(style);
				//������  ��ҵ����
				rowCell = row.createCell(3);
				rowCell.setCellValue(coms.get(i).getEnterprisename());
				rowCell.setCellStyle(style);
				//������  ֤����
				rowCell = row.createCell(4);
				String str = "";
				if(coms.get(i).getSerial()!=null){
					str=coms.get(i).getSerial()+"";
				int len = coms.get(i).getSerial().toString().length();
                  len = 6-len;
                  for(int n=0;n<len;n++){
                	  str = "0"+str;
	              }
                 }
				rowCell.setCellValue(str);
				rowCell.setCellStyle(style);
				//������  ��֤����
				rowCell = row.createCell(5);
				if(coms.get(i).getCertappdate()!=null){
					certappdate = sdf.format(coms.get(i).getCertappdate());
					rowCell.setCellValue(certappdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//������  ��������
				rowCell = row.createCell(6);
				if(coms.get(i).getCerttodate()!=null){
					certtodate=sdf.format(coms.get(i).getCerttodate());
					rowCell.setCellValue(certtodate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//������  ��������
				rowCell = row.createCell(7);
				if(coms.get(i).getCreatedate()!=null){
					createdate=sdf.format(coms.get(i).getCreatedate());
					rowCell.setCellValue(createdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//�ڰ���  ��ҵ����
				rowCell = row.createCell(8);
				rowCell.setCellValue(coms.get(i).getEnterprisekind());
				rowCell.setCellStyle(style);
				//�ھ���  ע���ʱ�
				rowCell = row.createCell(9);
				rowCell.setCellValue(coms.get(i).getRegistercapital());
				rowCell.setCellStyle(style);
				//��ʮ��  ��������
				rowCell = row.createCell(10);
				rowCell.setCellValue(coms.get(i).getPostalcode());
				rowCell.setCellStyle(style);
				//��ʮһ�� ����
				rowCell = row.createCell(11);
				rowCell.setCellValue(coms.get(i).getFax());
				rowCell.setCellStyle(style);
				//ʮ����  ���˴���
				rowCell = row.createCell(12);
				rowCell.setCellValue(coms.get(i).getArtificialperson());
				rowCell.setCellStyle(style);
				//��ʮ����  ���˵绰
				rowCell = row.createCell(13);
				rowCell.setCellValue(coms.get(i).getAptel());
				rowCell.setCellStyle(style);
				//��ʮ����  ��ϵ��
				rowCell = row.createCell(14);
				rowCell.setCellValue(coms.get(i).getLinkman());
				rowCell.setCellStyle(style);
				//��ʮ����  ��ϵ�˵绰
				rowCell = row.createCell(15);
				rowCell.setCellValue(coms.get(i).getLtel());
				rowCell.setCellStyle(style);
				//��ʮ����  ����ӡˢ����������
				rowCell = row.createCell(16);
				rowCell.setCellValue(coms.get(i).getBcprincipal());
				rowCell.setCellStyle(style);
				//��ʮ����  ����ӡˢ��������
				rowCell = row.createCell(17);
				if(coms.get(i).getFlat())Typ+="��ƽ�潺ӡ";
				if(coms.get(i).getGravure())Typ+="������ӡˢ";
				if(coms.get(i).getWebby())Typ+="��˿��ӡˢ";
				if(coms.get(i).getFlexible())Typ+="�����԰�ӡˢ";
				if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="��"+coms.get(i).getElsetype();
//				Typ=Typ.substring(1, Typ.length());
				if(Typ.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Typ.substring(1, Typ.length()));
				}
				rowCell.setCellStyle(style);
				//��ʮ����  ӡˢ�������
				rowCell = row.createCell(18);
				if(coms.get(i).getPapery())Mat+="��ֽ��";
				if(coms.get(i).getPastern())Mat+="�����ɽ�";
				if(coms.get(i).getFrilling())Mat+="������ֽ";
				if(coms.get(i).getMetal())Mat+="������";
				if(coms.get(i).getPlastic())Mat+="������";
				if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="��"+coms.get(i).getElsematerial();
				if(Mat.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Mat.substring(1, Mat.length()));
				}
				rowCell.setCellStyle(style);
				//��ʮ����  ��ע
				rowCell = row.createCell(19);
				rowCell.setCellValue(coms.get(i).getRemarks());
				rowCell.setCellStyle(style);
				}
				
			//���
			//���һ��sheet,sheet��
			sheet = workbook.createSheet(title+"���");
			//�ϲ���Ԫ�񣬵�һ�С����һ�С���һ�С����һ��
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));
			//������һ��
			titleRow1 = sheet.createRow(0);
			//�������ⵥԪ���ʽ
			titleStyle1 = workbook.createCellStyle();
			titleStyle1.setAlignment(HorizontalAlignment.CENTER);
			//����һ������
			titlefont1 = workbook.createFont();
			titlefont1.setFontHeight(16);//��������16��
			//������Ӧ�õ���ǰ�ĸ�ʽ��
			titleStyle1.setFont(titlefont1);
			//�ڵ�һ���д���һ����Ԫ��
			titleCell1 = titleRow1.createCell(0);
			//����ֵ����ʽ�ͱ���
			titleCell1.setCellValue(title+"�ı��ҵ����ҵ��Ϣ");
			titleCell1.setCellStyle(titleStyle1);
			//---------------�����ǵ�һ��-----------------
			sheet.autoSizeColumn(0);
			sheet.setColumnWidth(1,25 * 256);
			sheet.setColumnWidth(2,25 * 256);
			sheet.setColumnWidth(3,25 * 512);
			sheet.autoSizeColumn(4);
			sheet.setColumnWidth(5,25 * 256);
			sheet.setColumnWidth(6,25 * 256);
			sheet.setColumnWidth(7,25 * 256);
			sheet.setColumnWidth(8,25 * 256);
			sheet.setColumnWidth(9,25 * 256);
			sheet.setColumnWidth(10,25 * 256);
			sheet.setColumnWidth(11,25 * 256);
			sheet.setColumnWidth(12,25 * 256);
			sheet.setColumnWidth(13,25 * 256);
			sheet.setColumnWidth(14,25 * 256);
			sheet.setColumnWidth(15,25 * 256);
			sheet.setColumnWidth(16,25 * 512);
			sheet.setColumnWidth(17,25 * 512);
			sheet.setColumnWidth(18,25 * 512);
			sheet.setColumnWidth(19,25 * 512);
			//�����������ĵ�Ԫ���ʽ
			style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//���õڶ��б�ͷ
			rowHeader1 = sheet.createRow(1);
			cell = rowHeader1.createCell(0);
			cell.setCellValue("���");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(1);
			cell.setCellValue("״̬");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(2);
			cell.setCellValue("��֧����");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(3);
			cell.setCellValue("��ҵ����");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(4);
			cell.setCellValue("֤����");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(5);
			cell.setCellValue("��֤����");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(6);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(7);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(8);
			cell.setCellValue("��ҵ����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(9);
			cell.setCellValue("ע���ʱ�");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(10);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(11);
			cell.setCellValue("����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(12);
			cell.setCellValue("���˴���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(13);
			cell.setCellValue("���˵绰");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(14);
			cell.setCellValue("��ϵ��");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(15);
			cell.setCellValue("��ϵ�˵绰");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(16);
			cell.setCellValue("����ӡˢ����������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(17);
			cell.setCellValue("����ӡˢ��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(18);
			cell.setCellValue("ӡˢ�������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(19);
			cell.setCellValue("��ע");
			cell.setCellStyle(style);
			//-------------��ͷ��------------
			//���õ�һ��Ϊ״̬
			int index3=1;
			coms=companyInfoMapper.getByTwo(branch[j], "%BG%",s,e);
				for(int i=0;i<coms.size();i++){
					String Typ="";
					String Mat="";
				index3++;//�к�++����2��ʼ
				//����һ������
				row = sheet.createRow(index3);
				//����  ���
				rowCell = row.createCell(0);
				rowCell.setCellValue((i+1)+"");
				rowCell.setCellStyle(style);
				//��һ��  ״̬
				rowCell = row.createCell(1);
				ywStatus=coms.get(i).getStatus();
				if(ywStatus!=null){
					if(ywStatus==37){
						status="������";
					}else if(ywStatus==34){
						status="�������";
					}else if(ywStatus==36){
						status="�����";
					}else if(ywStatus==35){
						status="������";
					}else if(ywStatus==17){
						status="�������";
					}
				}
				rowCell.setCellValue(status);
				rowCell.setCellStyle(style);
				//�ڶ��� ��֧��������
				rowCell = row.createCell(2);
				rowCell.setCellValue(coms.get(i).getBranchName());
				rowCell.setCellStyle(style);
				//������  ��ҵ����
				rowCell = row.createCell(3);
				rowCell.setCellValue(coms.get(i).getEnterprisename());
				rowCell.setCellStyle(style);
				//������  ֤����
				rowCell = row.createCell(4);
				String str = "";
				if(coms.get(i).getSerial()!=null){
					str=coms.get(i).getSerial()+"";
				int len = coms.get(i).getSerial().toString().length();
                  len = 6-len;
                  for(int n=0;n<len;n++){
                	  str = "0"+str;
	              }
                 }
				rowCell.setCellValue(str);
				rowCell.setCellStyle(style);
				//������  ��֤����
				rowCell = row.createCell(5);
				if(coms.get(i).getCertappdate()!=null){
					certappdate = sdf.format(coms.get(i).getCertappdate());
					rowCell.setCellValue(certappdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//������  ��������
				rowCell = row.createCell(6);
				if(coms.get(i).getCerttodate()!=null){
					certtodate=sdf.format(coms.get(i).getCerttodate());
					rowCell.setCellValue(certtodate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//������  ��������
				rowCell = row.createCell(7);
				if(coms.get(i).getCreatedate()!=null){
					createdate=sdf.format(coms.get(i).getCreatedate());
					rowCell.setCellValue(createdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//�ڰ���  ��ҵ����
				rowCell = row.createCell(8);
				rowCell.setCellValue(coms.get(i).getEnterprisekind());
				rowCell.setCellStyle(style);
				//�ھ���  ע���ʱ�
				rowCell = row.createCell(9);
				rowCell.setCellValue(coms.get(i).getRegistercapital());
				rowCell.setCellStyle(style);
				//��ʮ��  ��������
				rowCell = row.createCell(10);
				rowCell.setCellValue(coms.get(i).getPostalcode());
				rowCell.setCellStyle(style);
				//��ʮһ�� ����
				rowCell = row.createCell(11);
				rowCell.setCellValue(coms.get(i).getFax());
				rowCell.setCellStyle(style);
				//ʮ����  ���˴���
				rowCell = row.createCell(12);
				rowCell.setCellValue(coms.get(i).getArtificialperson());
				rowCell.setCellStyle(style);
				//��ʮ����  ���˵绰
				rowCell = row.createCell(13);
				rowCell.setCellValue(coms.get(i).getAptel());
				rowCell.setCellStyle(style);
				//��ʮ����  ��ϵ��
				rowCell = row.createCell(14);
				rowCell.setCellValue(coms.get(i).getLinkman());
				rowCell.setCellStyle(style);
				//��ʮ����  ��ϵ�˵绰
				rowCell = row.createCell(15);
				rowCell.setCellValue(coms.get(i).getLtel());
				rowCell.setCellStyle(style);
				//��ʮ����  ����ӡˢ����������
				rowCell = row.createCell(16);
				rowCell.setCellValue(coms.get(i).getBcprincipal());
				rowCell.setCellStyle(style);
				//��ʮ����  ����ӡˢ��������
				rowCell = row.createCell(17);
				if(coms.get(i).getFlat())Typ+="��ƽ�潺ӡ";
				if(coms.get(i).getGravure())Typ+="������ӡˢ";
				if(coms.get(i).getWebby())Typ+="��˿��ӡˢ";
				if(coms.get(i).getFlexible())Typ+="�����԰�ӡˢ";
				if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="��"+coms.get(i).getElsetype();
//				Typ=Typ.substring(1, Typ.length());
				if(Typ.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Typ.substring(1, Typ.length()));
				}
				rowCell.setCellStyle(style);
				//��ʮ����  ӡˢ�������
				rowCell = row.createCell(18);
				if(coms.get(i).getPapery())Mat+="��ֽ��";
				if(coms.get(i).getPastern())Mat+="�����ɽ�";
				if(coms.get(i).getFrilling())Mat+="������ֽ";
				if(coms.get(i).getMetal())Mat+="������";
				if(coms.get(i).getPlastic())Mat+="������";
				if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="��"+coms.get(i).getElsematerial();
				if(Mat.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Mat.substring(1, Mat.length()));
				}
				rowCell.setCellStyle(style);
				//��ʮ����  ��ע
				rowCell = row.createCell(19);
				rowCell.setCellValue(coms.get(i).getRemarks());
				rowCell.setCellStyle(style);
				}
			}
			//���ļ����浽ָ����λ��
			FileOutputStream out = new FileOutputStream(filePath);
			workbook.write(out);
			workbook.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	//����һ��������
//	public boolean exportByTwo(String title,String filePath, String d1, String d2, String branchId) {
//	String[] branch=branchId.split(",");
//	//����excel����д������
//	try{
//		//����һ��������
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		//���һ��sheet,sheet��
//		XSSFSheet sheet = workbook.createSheet(title);
//		//�ϲ���Ԫ�񣬵�һ�С����һ�С���һ�С����һ��
//		sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
//		//������һ��
//		XSSFRow titleRow = sheet.createRow(0);
//		//�������ⵥԪ���ʽ
//		XSSFCellStyle titleStyle = workbook.createCellStyle();
//		titleStyle.setAlignment(HorizontalAlignment.CENTER);
//		//����һ������
//		XSSFFont titlefont = workbook.createFont();
//		titlefont.setFontHeight(16);//��������16��
//		//������Ӧ�õ���ǰ�ĸ�ʽ��
//		titleStyle.setFont(titlefont);
//		//�ڵ�һ���д���һ����Ԫ��
//		XSSFCell titleCell = titleRow.createCell(0);
//		//����ֵ����ʽ�ͱ���
//		titleCell.setCellValue(title);
//		titleCell.setCellStyle(titleStyle);
//		//---------------�����ǵ�һ��-----------------
//		sheet.setColumnWidth(0,25 * 256);
//		sheet.autoSizeColumn(1);
//		sheet.autoSizeColumn(2);
//		sheet.autoSizeColumn(3);
//		//�����������ĵ�Ԫ���ʽ
//		XSSFCellStyle style = workbook.createCellStyle();
//		style.setAlignment(HorizontalAlignment.CENTER);
//		//���õڶ��б�ͷ
//		XSSFRow rowHeader = sheet.createRow(1);
//		XSSFCell cell = rowHeader.createCell(0);
//		cell.setCellValue("");
//		cell.setCellStyle(style);
//		cell = rowHeader.createCell(1);
//		cell.setCellValue("�°�");
//		cell.setCellStyle(style);
//		cell = rowHeader.createCell(2);
//		cell.setCellValue("����");
//		cell.setCellStyle(style);
//		cell = rowHeader.createCell(3);
//		cell.setCellValue("���");
//		cell.setCellStyle(style);
//		cell = rowHeader.createCell(4);
//		
//		//-------------��ͷ��------------
//		//���õ�һ��Ϊ���з�����
//		int index = 1;
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		int app=0;
//		int recog=0;
//		int change=0;
//		int countApp=0;
//		int countRecog=0;
//		int countChange=0;
//		Date sd=sdf.parse(d1);
//	    Timestamp s = getStime(sd);//��ȡ��ʼʱ��
//	    Date ed=sdf.parse(d2);
//		Timestamp e = getEtime(ed);//��ȡ��ֹʱ��
//	for(int j=0;j<branch.length;j++){
//		index++;//�к�++����2��ʼ
//		app=countMapper.getCount(branch[j],"%XB%",s,e);
//		recog=countMapper.getCount(branch[j],"%FR%",s,e);
//		change=countMapper.getCount(branch[j],"%BG%",s,e);
//		countApp+=app;
//		countRecog+=recog;
//		countChange+=change;
//		//����һ������
//		XSSFRow row = sheet.createRow(index);
//		XSSFCell rowCell = row.createCell(0);//��һ��
//		rowCell.setCellValue(divisionMapper.getDivisionByCode(branch[j]).getDivisionname());
//		rowCell.setCellStyle(style);
//		rowCell = row.createCell(1);//�ڶ���
//		rowCell.setCellValue(app);
//		rowCell.setCellStyle(style);
//		rowCell = row.createCell(2);//������
//		rowCell.setCellValue(recog);
//		rowCell.setCellStyle(style);
//		rowCell = row.createCell(3);//������
//		rowCell.setCellValue(change);
//		rowCell.setCellStyle(style);
//		}
//		//����һ������
//		XSSFRow row = sheet.createRow(index);
//		XSSFCell rowCell = row.createCell(0);//��һ��
//		rowCell.setCellValue("�ܼ�");
//		rowCell.setCellStyle(style);
//		rowCell = row.createCell(1);//�ڶ���
//		rowCell.setCellValue(countApp);
//		rowCell.setCellStyle(style);
//		rowCell = row.createCell(2);//������
//		rowCell.setCellValue(countRecog);
//		rowCell.setCellStyle(style);
//		rowCell = row.createCell(3);//������
//		rowCell.setCellValue(countChange);
//		rowCell.setCellStyle(style);
//		//���ļ����浽ָ����λ��
//		FileOutputStream out = new FileOutputStream(filePath);
//		workbook.write(out);
//		workbook.close();
//		return true;
//	}catch(Exception e){
//		e.printStackTrace();
//		return false;
//	}
//}
	public int getXBCountByTime(String result) {
		return countMapper.getXBCountByTime(result);
	}
	public int getFRCountByTime(String fRresult) {
		return countMapper.getFRCountByTime(fRresult);
	}
	public int getBGCountByTime(String bGresult) {
		return countMapper.getBGCountByTime(bGresult);
	}
	public Integer getxbCountByBranchs(String xbResult) {
		return countMapper.getxbCountByBranchs(xbResult);
	}
	public Integer getfrCountByBranchs(String frResult) {
		return countMapper.getfrCountByBranchs(frResult);
	}
	public Integer getbgCountByBranchs(String bgResult) {
		return countMapper.getbgCountByBranchs(bgResult);
	}
	public int getXBCountByBranchs(String result) {
		return countMapper.getXBCountByBranchs(result);
	}
	public int getFRCountByBranchs(String fRresult) {
		return countMapper.getFRCountByBranchs(fRresult);
	}
	public int getBGCountByBranchs(String bGresult) {
		return countMapper.getBGCountByBranchs(bGresult);
	}
	public Integer getYWCountByType(String branchId, String type) {
		type="%"+type+"%";
		return countMapper.getYWCountByType(branchId,type);
	}
	public int getXBCountByType(String result) {
		return countMapper.getXBCountByType(result);
	}
	public int getFRCountByType(String fRresult) {
		return countMapper.getFRCountByType(fRresult);
	}
	public int getBGCountByType(String bGresult) {
		return countMapper.getBGCountByType(bGresult);
	}
}
