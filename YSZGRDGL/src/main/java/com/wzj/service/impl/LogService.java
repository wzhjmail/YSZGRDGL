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
		//先转成Date型
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
		if(sTamp==null){//如果开始时间为空，设置2017-07-09为开始时间
			try {
				Date myDate1 = df.parse("2017-07-09");
				sTamp = new Timestamp(myDate1.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(eTamp==null)//如果结束时间为空，设置当前时间为结束时间
			eTamp = new Timestamp(new Date().getTime());
		
		if(eTamp.before(sTamp)){//如果结束时间小于开始时间，对调
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
		//创建excel，并写入数据
		try{
			//创建一个工作薄
			XSSFWorkbook workbook = new XSSFWorkbook();
			//添加一个sheet,sheet名
			XSSFSheet sheet = workbook.createSheet(title);
			//合并单元格，第一行、最后一行、第一列、最后一列
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
			//创建第一行
			XSSFRow titleRow = sheet.createRow(0);
			//创建标题单元格格式
			XSSFCellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			//创建一个字体
			XSSFFont titlefont = workbook.createFont();
			titlefont.setFontHeight(16);//标题字体16号
			//将字体应用到当前的格式中
			titleStyle.setFont(titlefont);
			//在第一行中创建一个单元格
			XSSFCell titleCell = titleRow.createCell(0);
			//设置值、样式和标题
			titleCell.setCellValue(title);
			titleCell.setCellStyle(titleStyle);
			//---------------以上是第一行-----------------
			sheet.autoSizeColumn(0);
			sheet.setColumnWidth(1,25 * 256);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.setColumnWidth(5,25 * 256);
			//设置其他正文单元格格式
			XSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//设置第二行表头
			XSSFRow rowHeader = sheet.createRow(1);
			XSSFCell cell = rowHeader.createCell(0);
			cell.setCellValue("序号");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);
			cell.setCellValue("时间");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);
			cell.setCellValue("操作人");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);
			cell.setCellValue("所属中心");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(4);
			cell.setCellValue("操作类型");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(5);
			cell.setCellValue("操作内容");
			cell.setCellStyle(style);
			//-------------表头完------------
			//获取数据集合
			List<Log> logList = logMapper.findAll();
			int index = 1;
			Log record = new Log();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
			//遍历数据，写入到excel中
			if(logList.size()>0){
				for(int i=0;i<logList.size();i++){
					index++;//行号++，从2开始
					record = logList.get(i);
					//生成一个新行
					XSSFRow row = sheet.createRow(index);
					XSSFCell rowCell = row.createCell(0);//第一列
					rowCell.setCellValue(i+1);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(1);//第二列
					rowCell.setCellValue(format.format(record.getTime()));
					rowCell.setCellStyle(style);
					rowCell = row.createCell(2);//第3列
					rowCell.setCellValue(record.getPerson());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(3);//第4列
					rowCell.setCellValue(record.getDepartment());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(4);//第5列
					rowCell.setCellValue(record.getOperationType());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(5);//第6列
					rowCell.setCellValue(record.getOperation());
					rowCell.setCellStyle(style);
				}
			}
			//将文件保存到指定的位置
			FileOutputStream out = new FileOutputStream(filePath);
			workbook.write(out);
			workbook.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//获取第一条数据
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
