package com.wzj.service.impl;

import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.LogMapper;
import com.wzj.pojo.Log;
import com.wzj.service.ILogService;

@Service("logService")
public class LogService implements ILogService {

	@Autowired
	private LogMapper logMapper;
	
	@Override
	public int insert(Log log){
		return logMapper.insert(log);
	}
	
	@Override
	public List<Log> findAll() {
		return logMapper.findAll();
	}

	public List<Log> findByTime(String sTime, String eTime){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//��ת��Date��
		Date sDate;
		Date eDate;
		Date changeDate;
		Timestamp sTamp=null;
		Timestamp eTamp=null;
		try {
			sDate = df.parse(sTime);
			eDate = df.parse(eTime);
			sTamp = new Timestamp(sDate.getTime());
			eTamp = new Timestamp(eDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(sTamp==null){//�����ʼʱ��Ϊ�գ�����2017-07-09Ϊ��ʼʱ��
			try {
				Date myDate1 = df.parse("2017-07-09");
				sTamp = new Timestamp(myDate1.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(eTamp==null)//�������ʱ��Ϊ�գ����õ�ǰʱ��Ϊ����ʱ��
			eTamp = new Timestamp(new Date().getTime());
		
		if(eTamp.before(sTamp)){//�������ʱ��С�ڿ�ʼʱ�䣬�Ե�
			Timestamp ts = eTamp;
			eTamp=sTamp;
			sTamp=ts;
		}
		String stime=sTamp.toString();
		String etime=eTamp.toString().replace("00:00:00.0", "24:00:00.0");
		List<Log> logs = logMapper.findByTime(stime,etime);
        return logs;
		  
	}
	
	public void deleteBefore(String timePoint) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date;
		Timestamp ts = null;
		try {
			date = df.parse(timePoint);
			ts = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		logMapper.deleteBefore(ts);
	}
	
	public boolean exportRecord(String title, String filePath) {
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
			sheet.autoSizeColumn(0);
			sheet.setColumnWidth(1,25 * 256);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.setColumnWidth(5,25 * 256);
			//�����������ĵ�Ԫ���ʽ
			XSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//���õڶ��б�ͷ
			XSSFRow rowHeader = sheet.createRow(1);
			XSSFCell cell = rowHeader.createCell(0);
			cell.setCellValue("���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);
			cell.setCellValue("ʱ��");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);
			cell.setCellValue("������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(4);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(5);
			cell.setCellValue("��������");
			cell.setCellStyle(style);
			//-------------��ͷ��------------
			//��ȡ���ݼ���
			List<Log> logList = logMapper.findAll();
			int index = 1;
			Log record = new Log();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
			//�������ݣ�д�뵽excel��
			if(logList.size()>0){
				for(int i=0;i<logList.size();i++){
					index++;//�к�++����2��ʼ
					record = logList.get(i);
					//����һ������
					XSSFRow row = sheet.createRow(index);
					XSSFCell rowCell = row.createCell(0);//��һ��
					rowCell.setCellValue(i+1);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(1);//�ڶ���
					rowCell.setCellValue(format.format(record.getTime()));
					rowCell.setCellStyle(style);
					rowCell = row.createCell(2);//��3��
					rowCell.setCellValue(record.getPerson());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(3);//��4��
					rowCell.setCellValue(record.getDepartment());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(4);//��5��
					rowCell.setCellValue(record.getOperationType());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(5);//��6��
					rowCell.setCellValue(record.getOperation());
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

	//��ȡ��һ������
	public Log getFirstLog() {
		return logMapper.getFirstLog();
	}

	public List<Log> selectAll(String str) {
		return logMapper.selectAll("%"+str+"%");
	}

	public List<Log> findByStr(String sSearch) {
		return logMapper.findByStr("%"+sSearch+"%");
	}

}
