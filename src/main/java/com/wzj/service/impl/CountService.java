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
	//获取开始时间
		private Timestamp getStime(Date start){
			Date stime = null;
			if(start==null){//如果开始时间为null,则使用本年度的第一天作为开始时间
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
		//获取结束时间
		private  Timestamp getEtime(Date edate){
			Date etime=null;
			if(edate==null){//如果没有结束时间，则取现在的时间作为结束时间
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
		//根据年月获得当月天数
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
		sheet.setColumnWidth(0,25 * 256);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
//		sheet.autoSizeColumn(4);
//		sheet.setColumnWidth(5,25 * 256);
		//设置其他正文单元格格式
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		//设置第二行表头
		XSSFRow rowHeader = sheet.createRow(1);
		XSSFCell cell = rowHeader.createCell(0);
		cell.setCellValue("");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(1);
		cell.setCellValue("新办");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(2);
		cell.setCellValue("复认");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(3);
		cell.setCellValue("变更");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(4);
		
		//-------------表头完------------
		//设置第一列为所有分中心
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
					index++;//行号++，从2开始
					app=countMapper.getCountByType(divList.get(i).getDivisioncode(),"%XB%");
					recog=countMapper.getCountByType(divList.get(i).getDivisioncode(),"%FR%");
					change=countMapper.getCountByType(divList.get(i).getDivisioncode(),"%BG%");
					countApp+=Integer.parseInt(app);
					countRecog+=Integer.parseInt(recog);
					countChange+=Integer.parseInt(change);
					//生成一个新行
					XSSFRow row = sheet.createRow(index);
					XSSFCell rowCell = row.createCell(0);//第一列
					rowCell.setCellValue(divList.get(i).getDivisionname());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(1);//第二列
					rowCell.setCellValue(app);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(2);//第三列
					rowCell.setCellValue(recog);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(3);//第四列
					rowCell.setCellValue(change);
					rowCell.setCellStyle(style);
				}
			}
			
			//生成一个新行
			XSSFRow row = sheet.createRow(index);
			XSSFCell rowCell = row.createCell(0);//第一列
			rowCell.setCellValue("总计");
			rowCell.setCellStyle(style);
			rowCell = row.createCell(1);//第二列
			rowCell.setCellValue(countApp);
			rowCell.setCellStyle(style);
			rowCell = row.createCell(2);//第三列
			rowCell.setCellValue(countRecog);
			rowCell.setCellStyle(style);
			rowCell = row.createCell(3);//第四列
			rowCell.setCellValue(countChange);
			rowCell.setCellStyle(style);
			
			
			
			
		
			
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

	public boolean exportByTime(String title, String filePath, Timestamp start, Timestamp end) {
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
			sheet.setColumnWidth(0,25 * 256);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			//设置其他正文单元格格式
			XSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//设置第二行表头
			XSSFRow rowHeader = sheet.createRow(1);
			XSSFCell cell = rowHeader.createCell(0);
			cell.setCellValue("");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);
			cell.setCellValue("新办");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);
			cell.setCellValue("复认");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);
			cell.setCellValue("变更");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(4);
			
			//-------------表头完------------
			//设置第一列为所有分中心
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
					index++;//行号++，从2开始
					app=countMapper.getCount(divList.get(i).getDivisioncode(),"%XB%",start,end);
					recog=countMapper.getCount(divList.get(i).getDivisioncode(),"%FR%",start,end);
					change=countMapper.getCount(divList.get(i).getDivisioncode(),"%BG%",start,end);
					countApp+=app;
					countRecog+=recog;
					countChange+=change;
					//生成一个新行
					XSSFRow row = sheet.createRow(index);
					XSSFCell rowCell = row.createCell(0);//第一列
					rowCell.setCellValue(divList.get(i).getDivisionname());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(1);//第二列
					rowCell.setCellValue(app);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(2);//第三列
					rowCell.setCellValue(recog);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(3);//第四列
					rowCell.setCellValue(change);
					rowCell.setCellStyle(style);
				}
				
				//生成一个新行
				XSSFRow row = sheet.createRow(index);
				XSSFCell rowCell = row.createCell(0);//第一列
				rowCell.setCellValue("总计");
				rowCell.setCellStyle(style);
				rowCell = row.createCell(1);//第二列
				rowCell.setCellValue(countApp);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(2);//第三列
				rowCell.setCellValue(countRecog);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(3);//第四列
				rowCell.setCellValue(countChange);
				rowCell.setCellStyle(style);
				
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
	//创建一个工作薄
	XSSFWorkbook  workbook= new XSSFWorkbook();
	public boolean exportByBranch(String filePath, String time, String branchId) {
		XSSFWorkbook  workbook= new XSSFWorkbook();
		//创建excel，并写入数据
		String[] branch=branchId.split(",");
		String title="";
		try{
			for (int j=0;j<branch.length;j++) {
			Division div=divisionMapper.getDivisionByCode(branch[j]);
			title=div.getDivisionname();
			//创建一个工作薄
//				XSSFWorkbook workbook = new XSSFWorkbook();
			//添加一个sheet,sheet名
			XSSFSheet sheet=workbook.createSheet(title+"业务统计数据");
			//合并单元格，第一行、最后一行、第一列、最后一列
			 sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
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
			titleCell.setCellValue(title+"业务统计数据");
			titleCell.setCellStyle(titleStyle);
			//---------------以上是第一行-----------------
			sheet.setColumnWidth(0,25 * 256);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			//设置其他正文单元格格式
			XSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//设置第二行表头
			XSSFRow rowHeader = sheet.createRow(1);
			XSSFCell cell = rowHeader.createCell(0);
			cell.setCellValue("时间");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);
			cell.setCellValue("新办");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);
			cell.setCellValue("复认");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);
			cell.setCellValue("变更");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(4);
			
			//-------------表头完------------
			//设置第一列为所有分中心
			int index = 1;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int app=0;
			int recog=0;
			int change=0;
			int countApp=0;
			int countRecog=0;
			int countChange=0;
//				当月最后一天
			int endDay=0;
//				本月起始时间
			String month1="";
//				本月终止时间
			String month2="";
		for(int i=1;i<=12;i++){
			endDay=getDaysofMonth(Integer.parseInt(time),i);
			month1=time+"-"+String.valueOf(i)+"-"+"01";
			month2=time+"-"+String.valueOf(i)+"-"+String.valueOf(endDay);
			Date sd=sdf.parse(month1);
	        Timestamp s = getStime(sd);//获取起始时间
	        Date ed=sdf.parse(month2);
			Timestamp e = getEtime(ed);//获取终止时间
				index++;//行号++，从2开始
				app=countMapper.getCount(branch[j],"%XB%",s,e);
				recog=countMapper.getCount(branch[j],"%FR%",s,e);
				change=countMapper.getCount(branch[j],"%BG%",s,e);
				countApp+=app;
				countRecog+=recog;
				countChange+=change;
				//生成一个新行
				XSSFRow row = sheet.createRow(index);
				XSSFCell rowCell = row.createCell(0);//第一列
				rowCell.setCellValue(i+"月");
				rowCell.setCellStyle(style);
				rowCell = row.createCell(1);//第二列
				rowCell.setCellValue(app);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(2);//第三列
				rowCell.setCellValue(recog);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(3);//第四列
				rowCell.setCellValue(change);
				rowCell.setCellStyle(style);
			}
					
			//生成一个新行
			XSSFRow row = sheet.createRow(index);
			XSSFCell rowCell = row.createCell(0);//第一列
			rowCell.setCellValue("总计");
			rowCell.setCellStyle(style);
			rowCell = row.createCell(1);//第二列
			rowCell.setCellValue(countApp);
			rowCell.setCellStyle(style);
			rowCell = row.createCell(2);//第三列
			rowCell.setCellValue(countRecog);
			rowCell.setCellStyle(style);
			rowCell = row.createCell(3);//第四列
			rowCell.setCellValue(countChange);
			rowCell.setCellStyle(style);
			
			//新办
			//添加一个sheet,sheet名
			sheet = workbook.createSheet(title+"新办");
			//合并单元格，第一行、最后一行、第一列、最后一列
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));
			//创建第一行
			titleRow = sheet.createRow(0);
			//创建标题单元格格式
			titleStyle= workbook.createCellStyle();
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			//创建一个字体
			titlefont= workbook.createFont();
			titlefont.setFontHeight(16);//标题字体16号
			//将字体应用到当前的格式中
			titleStyle.setFont(titlefont);
			//在第一行中创建一个单元格
			titleCell = titleRow.createCell(0);
			//设置值、样式和标题
			titleCell.setCellValue(title+"的新办业务企业信息");
			titleCell.setCellStyle(titleStyle);
			//---------------以上是第一行-----------------
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
			
			//设置其他正文单元格格式
			style= workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//设置第二行表头
			rowHeader = sheet.createRow(1);
			cell = rowHeader.createCell(0);
			cell.setCellValue("编号");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);
			cell.setCellValue("状态");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);
			cell.setCellValue("分支机构");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);
			cell.setCellValue("企业名称");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(4);
			cell.setCellValue("证书编号");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(5);
			cell.setCellValue("发证日期");
			cell.setCellStyle(style);
			cell= rowHeader.createCell(6);
			cell.setCellValue("到期日期");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(7);
			cell.setCellValue("更新日期");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(8);
			cell.setCellValue("企业性质");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(9);
			cell.setCellValue("注册资本");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(10);
			cell.setCellValue("邮政编码");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(11);
			cell.setCellValue("传真");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(12);
			cell.setCellValue("法人代表");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(13);
			cell.setCellValue("法人电话");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(14);
			cell.setCellValue("联系人");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(15);
			cell.setCellValue("联系人电话");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(16);
			cell.setCellValue("条码印刷技术负责人");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(17);
			cell.setCellValue("条码印刷技术类型");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(18);
			cell.setCellValue("印刷载体材料");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(19);
			cell.setCellValue("备注");
			cell.setCellStyle(style);
			
			//-------------表头完------------
			//设置第一列为状态
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
			index1++;//行号++，从2开始
			//生成一个新行
			row = sheet.createRow(index1);
			//首列  编号
			rowCell = row.createCell(0);
			rowCell.setCellValue((i+1)+"");
			rowCell.setCellStyle(style);
			//第一列  状态
			rowCell = row.createCell(1);
			ywStatus=coms.get(i).getStatus();
			if(ywStatus!=null){
				if(ywStatus==37){
					status="复认中";
				}else if(ywStatus==34){
					status="复认完成";
				}else if(ywStatus==36){
					status="变更中";
				}else if(ywStatus==35){
					status="变更完成";
				}else if(ywStatus==17){
					status="申请完成";
				}
			}
			rowCell.setCellValue(status);
			rowCell.setCellStyle(style);
			//第二列 分支机构名称
			rowCell = row.createCell(2);
			rowCell.setCellValue(coms.get(i).getBranchName());
			rowCell.setCellStyle(style);
			//第三列  企业名称
			rowCell = row.createCell(3);
			rowCell.setCellValue(coms.get(i).getEnterprisename());
			rowCell.setCellStyle(style);
			//第四列  证书编号
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
			//第五列  发证日期
			rowCell = row.createCell(5);
			if(coms.get(i).getCertappdate()!=null){
				certappdate = sdf.format(coms.get(i).getCertappdate());
				rowCell.setCellValue(certappdate);
			}else{
				rowCell.setCellValue("");
			}
			rowCell.setCellStyle(style);
			//第六列  到期日期
			rowCell = row.createCell(6);
			if(coms.get(i).getCerttodate()!=null){
				certtodate=sdf.format(coms.get(i).getCerttodate());
				rowCell.setCellValue(certtodate);
			}else{
				rowCell.setCellValue("");
			}
			rowCell.setCellStyle(style);
			//第七列  创建日期
			rowCell = row.createCell(7);
			if(coms.get(i).getCreatedate()!=null){
				createdate=sdf.format(coms.get(i).getCreatedate());
				rowCell.setCellValue(createdate);
			}else{
				rowCell.setCellValue("");
			}
			rowCell.setCellStyle(style);
			//第八列  企业性质
			rowCell = row.createCell(8);
			rowCell.setCellValue(coms.get(i).getEnterprisekind());
			rowCell.setCellStyle(style);
			//第九列  注册资本
			rowCell = row.createCell(9);
			rowCell.setCellValue(coms.get(i).getRegistercapital());
			rowCell.setCellStyle(style);
			//第十列  邮政编码
			rowCell = row.createCell(10);
			rowCell.setCellValue(coms.get(i).getPostalcode());
			rowCell.setCellStyle(style);
			//第十一列 传真
			rowCell = row.createCell(11);
			rowCell.setCellValue(coms.get(i).getFax());
			rowCell.setCellStyle(style);
			//十二列  法人代表
			rowCell = row.createCell(12);
			rowCell.setCellValue(coms.get(i).getArtificialperson());
			rowCell.setCellStyle(style);
			//第十三列  法人电话
			rowCell = row.createCell(13);
			rowCell.setCellValue(coms.get(i).getAptel());
			rowCell.setCellStyle(style);
			//第十四列  联系人
			rowCell = row.createCell(14);
			rowCell.setCellValue(coms.get(i).getLinkman());
			rowCell.setCellStyle(style);
			//第十五列  联系人电话
			rowCell = row.createCell(15);
			rowCell.setCellValue(coms.get(i).getLtel());
			rowCell.setCellStyle(style);
			//第十六列  条码印刷技术负责人
			rowCell = row.createCell(16);
			rowCell.setCellValue(coms.get(i).getBcprincipal());
			rowCell.setCellStyle(style);
			//第十七列  条码印刷技术类型
			rowCell = row.createCell(17);
			if(coms.get(i).getFlat())Typ+="、平版胶印";
			if(coms.get(i).getGravure())Typ+="、凹版印刷";
			if(coms.get(i).getWebby())Typ+="、丝网印刷";
			if(coms.get(i).getFlexible())Typ+="、柔性版印刷";
			if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="、"+coms.get(i).getElsetype();
//			Typ=Typ.substring(1, Typ.length());
			if(Typ.length()==0){
				rowCell.setCellValue("");
			}else{
				rowCell.setCellValue(Typ.substring(1, Typ.length()));
			}
			rowCell.setCellStyle(style);
			//第十八列  印刷载体材料
			rowCell = row.createCell(18);
			if(coms.get(i).getPapery())Mat+="、纸质";
			if(coms.get(i).getPastern())Mat+="、不干胶";
			if(coms.get(i).getFrilling())Mat+="、瓦楞纸";
			if(coms.get(i).getMetal())Mat+="、金属";
			if(coms.get(i).getPlastic())Mat+="、塑料";
			if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="、"+coms.get(i).getElsematerial();
			if(Mat.length()==0){
				rowCell.setCellValue("");
			}else{
				rowCell.setCellValue(Mat.substring(1, Mat.length()));
			}
			rowCell.setCellStyle(style);
			//第十九列  备注
			rowCell = row.createCell(19);
			rowCell.setCellValue(coms.get(i).getRemarks());
			rowCell.setCellStyle(style);
		}
				
				
				
		//复认
		//添加一个sheet,sheet名
		sheet = workbook.createSheet(title+"复认");
		//合并单元格，第一行、最后一行、第一列、最后一列
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));
		//创建第一行
		titleRow = sheet.createRow(0);
		//创建标题单元格格式
		titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		//创建一个字体
		titlefont = workbook.createFont();
		titlefont.setFontHeight(16);//标题字体16号
		//将字体应用到当前的格式中
		titleStyle.setFont(titlefont);
		//在第一行中创建一个单元格
		titleCell = titleRow.createCell(0);
		//设置值、样式和标题
		titleCell.setCellValue(title+"的复认业务企业信息");
		titleCell.setCellStyle(titleStyle);
		//---------------以上是第一行-----------------
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
		//设置其他正文单元格格式
		style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		//设置第二行表头
		rowHeader = sheet.createRow(1);
		cell = rowHeader.createCell(0);
		cell.setCellValue("编号");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(1);
		cell.setCellValue("状态");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(2);
		cell.setCellValue("分支机构");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(3);
		cell.setCellValue("企业名称");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(4);
		cell.setCellValue("证书编号");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(5);
		cell.setCellValue("发证日期");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(6);
		cell.setCellValue("到期日期");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(7);
		cell.setCellValue("更新日期");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(8);
		cell.setCellValue("企业性质");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(9);
		cell.setCellValue("注册资本");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(10);
		cell.setCellValue("邮政编码");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(11);
		cell.setCellValue("传真");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(12);
		cell.setCellValue("法人代表");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(13);
		cell.setCellValue("法人电话");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(14);
		cell.setCellValue("联系人");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(15);
		cell.setCellValue("联系人电话");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(16);
		cell.setCellValue("条码印刷技术负责人");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(17);
		cell.setCellValue("条码印刷技术类型");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(18);
		cell.setCellValue("印刷载体材料");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(19);
		cell.setCellValue("备注");
		cell.setCellStyle(style);
		
		//-------------表头完------------
		//设置第一列为状态
		int index2 = 1;
		coms=companyInfoMapper.getByLike(branch[j], "%FR%");
			for(int i=0;i<coms.size();i++){
				String Typ="";
				String Mat="";
			index2++;//行号++，从2开始
			//生成一个新行
			row = sheet.createRow(index2);
			//首列  编号
			rowCell = row.createCell(0);
			rowCell.setCellValue((i+1)+"");
			rowCell.setCellStyle(style);
			//第一列  状态
			rowCell = row.createCell(1);
			ywStatus=coms.get(i).getStatus();
			if(ywStatus!=null){
				if(ywStatus==37){
					status="复认中";
				}else if(ywStatus==34){
					status="复认完成";
				}else if(ywStatus==36){
					status="变更中";
				}else if(ywStatus==35){
					status="变更完成";
				}else if(ywStatus==17){
					status="申请完成";
				}
			}
			rowCell.setCellValue(status);
			rowCell.setCellStyle(style);
			//第二列 分支机构名称
			rowCell = row.createCell(2);
			rowCell.setCellValue(coms.get(i).getBranchName());
			rowCell.setCellStyle(style);
			//第三列  企业名称
			rowCell = row.createCell(3);
			rowCell.setCellValue(coms.get(i).getEnterprisename());
			rowCell.setCellStyle(style);
			//第四列  证书编号
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
			//第五列  发证日期
			rowCell = row.createCell(5);
			if(coms.get(i).getCertappdate()!=null){
				certappdate = sdf.format(coms.get(i).getCertappdate());
				rowCell.setCellValue(certappdate);
			}else{
				rowCell.setCellValue("");
			}
			rowCell.setCellStyle(style);
			//第六列  到期日期
			rowCell = row.createCell(6);
			if(coms.get(i).getCerttodate()!=null){
				certtodate=sdf.format(coms.get(i).getCerttodate());
				rowCell.setCellValue(certtodate);
			}else{
				rowCell.setCellValue("");
			}
			rowCell.setCellStyle(style);
			//第七列  创建日期
			rowCell = row.createCell(7);
			if(coms.get(i).getCreatedate()!=null){
				createdate=sdf.format(coms.get(i).getCreatedate());
				rowCell.setCellValue(createdate);
			}else{
				rowCell.setCellValue("");
			}
			rowCell.setCellStyle(style);
			//第八列  企业性质
			rowCell = row.createCell(8);
			rowCell.setCellValue(coms.get(i).getEnterprisekind());
			rowCell.setCellStyle(style);
			//第九列  注册资本
			rowCell = row.createCell(9);
			rowCell.setCellValue(coms.get(i).getRegistercapital());
			rowCell.setCellStyle(style);
			//第十列  邮政编码
			rowCell = row.createCell(10);
			rowCell.setCellValue(coms.get(i).getPostalcode());
			rowCell.setCellStyle(style);
			//第十一列 传真
			rowCell = row.createCell(11);
			rowCell.setCellValue(coms.get(i).getFax());
			rowCell.setCellStyle(style);
			//十二列  法人代表
			rowCell = row.createCell(12);
			rowCell.setCellValue(coms.get(i).getArtificialperson());
			rowCell.setCellStyle(style);
			//第十三列  法人电话
			rowCell = row.createCell(13);
			rowCell.setCellValue(coms.get(i).getAptel());
			rowCell.setCellStyle(style);
			//第十四列  联系人
			rowCell = row.createCell(14);
			rowCell.setCellValue(coms.get(i).getLinkman());
			rowCell.setCellStyle(style);
			//第十五列  联系人电话
			rowCell = row.createCell(15);
			rowCell.setCellValue(coms.get(i).getLtel());
			rowCell.setCellStyle(style);
			//第十六列  条码印刷技术负责人
			rowCell = row.createCell(16);
			rowCell.setCellValue(coms.get(i).getBcprincipal());
			rowCell.setCellStyle(style);
			//第十七列  条码印刷技术类型
			rowCell = row.createCell(17);
			if(coms.get(i).getFlat())Typ+="、平版胶印";
			if(coms.get(i).getGravure())Typ+="、凹版印刷";
			if(coms.get(i).getWebby())Typ+="、丝网印刷";
			if(coms.get(i).getFlexible())Typ+="、柔性版印刷";
			if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="、"+coms.get(i).getElsetype();
//			Typ=Typ.substring(1, Typ.length());
			if(Typ.length()==0){
				rowCell.setCellValue("");
			}else{
				rowCell.setCellValue(Typ.substring(1, Typ.length()));
			}
			rowCell.setCellStyle(style);
			//第十八列  印刷载体材料
			rowCell = row.createCell(18);
			if(coms.get(i).getPapery())Mat+="、纸质";
			if(coms.get(i).getPastern())Mat+="、不干胶";
			if(coms.get(i).getFrilling())Mat+="、瓦楞纸";
			if(coms.get(i).getMetal())Mat+="、金属";
			if(coms.get(i).getPlastic())Mat+="、塑料";
			if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="、"+coms.get(i).getElsematerial();
			if(Mat.length()==0){
				rowCell.setCellValue("");
			}else{
				rowCell.setCellValue(Mat.substring(1, Mat.length()));
			}
			rowCell.setCellStyle(style);
			//第十九列  备注
			rowCell = row.createCell(19);
			rowCell.setCellValue(coms.get(i).getRemarks());
			rowCell.setCellStyle(style);
			}
					
			//变更
			//添加一个sheet,sheet名
			sheet = workbook.createSheet(title+"变更");
			//合并单元格，第一行、最后一行、第一列、最后一列
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));
			//创建第一行
			titleRow = sheet.createRow(0);
			//创建标题单元格格式
			titleStyle = workbook.createCellStyle();
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			//创建一个字体
			titlefont = workbook.createFont();
			titlefont.setFontHeight(16);//标题字体16号
			//将字体应用到当前的格式中
			titleStyle.setFont(titlefont);
			//在第一行中创建一个单元格
			titleCell = titleRow.createCell(0);
			//设置值、样式和标题
			titleCell.setCellValue(title+"的变更业务企业信息");
			titleCell.setCellStyle(titleStyle);
			//---------------以上是第一行-----------------
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
			//设置其他正文单元格格式
			style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//设置第二行表头
			rowHeader = sheet.createRow(1);
			cell = rowHeader.createCell(0);
			cell.setCellValue("编号");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);
			cell.setCellValue("状态");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);
			cell.setCellValue("分支机构");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);
			cell.setCellValue("企业名称");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(4);
			cell.setCellValue("证书编号");
			cell.setCellStyle(style);
			cell= rowHeader.createCell(5);
			cell.setCellValue("发证日期");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(6);
			cell.setCellValue("到期日期");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(7);
			cell.setCellValue("更新日期");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(8);
			cell.setCellValue("企业性质");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(9);
			cell.setCellValue("注册资本");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(10);
			cell.setCellValue("邮政编码");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(11);
			cell.setCellValue("传真");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(12);
			cell.setCellValue("法人代表");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(13);
			cell.setCellValue("法人电话");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(14);
			cell.setCellValue("联系人");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(15);
			cell.setCellValue("联系人电话");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(16);
			cell.setCellValue("条码印刷技术负责人");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(17);
			cell.setCellValue("条码印刷技术类型");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(18);
			cell.setCellValue("印刷载体材料");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(19);
			cell.setCellValue("备注");
			cell.setCellStyle(style);
			
			//-------------表头完------------
			//设置第一列为状态
			int index3 = 1;
			coms=companyInfoMapper.getByLike(branch[j], "%BG%");
				for(int i=0;i<coms.size();i++){
					String Typ="";
					String Mat="";
				index3++;//行号++，从2开始
				//生成一个新行
				row = sheet.createRow(index3);
				//首列  编号
				rowCell = row.createCell(0);
				rowCell.setCellValue((i+1)+"");
				rowCell.setCellStyle(style);
				//第一列  状态
				rowCell = row.createCell(1);
				ywStatus=coms.get(i).getStatus();
				if(ywStatus!=null){
					if(ywStatus==37){
						status="复认中";
					}else if(ywStatus==34){
						status="复认完成";
					}else if(ywStatus==36){
						status="变更中";
					}else if(ywStatus==35){
						status="变更完成";
					}else if(ywStatus==17){
						status="申请完成";
					}
				}
				rowCell.setCellValue(status);
				rowCell.setCellStyle(style);
				//第二列 分支机构名称
				rowCell = row.createCell(2);
				rowCell.setCellValue(coms.get(i).getBranchName());
				rowCell.setCellStyle(style);
				//第三列  企业名称
				rowCell = row.createCell(3);
				rowCell.setCellValue(coms.get(i).getEnterprisename());
				rowCell.setCellStyle(style);
				//第四列  证书编号
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
				//第五列  发证日期
				rowCell = row.createCell(5);
				if(coms.get(i).getCertappdate()!=null){
					certappdate = sdf.format(coms.get(i).getCertappdate());
					rowCell.setCellValue(certappdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//第六列  到期日期
				rowCell = row.createCell(6);
				if(coms.get(i).getCerttodate()!=null){
					certtodate=sdf.format(coms.get(i).getCerttodate());
					rowCell.setCellValue(certtodate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//第七列  创建日期
				rowCell = row.createCell(7);
				if(coms.get(i).getCreatedate()!=null){
					createdate=sdf.format(coms.get(i).getCreatedate());
					rowCell.setCellValue(createdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//第八列  企业性质
				rowCell = row.createCell(8);
				rowCell.setCellValue(coms.get(i).getEnterprisekind());
				rowCell.setCellStyle(style);
				//第九列  注册资本
				rowCell = row.createCell(9);
				rowCell.setCellValue(coms.get(i).getRegistercapital());
				rowCell.setCellStyle(style);
				//第十列  邮政编码
				rowCell = row.createCell(10);
				rowCell.setCellValue(coms.get(i).getPostalcode());
				rowCell.setCellStyle(style);
				//第十一列 传真
				rowCell = row.createCell(11);
				rowCell.setCellValue(coms.get(i).getFax());
				rowCell.setCellStyle(style);
				//十二列  法人代表
				rowCell = row.createCell(12);
				rowCell.setCellValue(coms.get(i).getArtificialperson());
				rowCell.setCellStyle(style);
				//第十三列  法人电话
				rowCell = row.createCell(13);
				rowCell.setCellValue(coms.get(i).getAptel());
				rowCell.setCellStyle(style);
				//第十四列  联系人
				rowCell = row.createCell(14);
				rowCell.setCellValue(coms.get(i).getLinkman());
				rowCell.setCellStyle(style);
				//第十五列  联系人电话
				rowCell = row.createCell(15);
				rowCell.setCellValue(coms.get(i).getLtel());
				rowCell.setCellStyle(style);
				//第十六列  条码印刷技术负责人
				rowCell = row.createCell(16);
				rowCell.setCellValue(coms.get(i).getBcprincipal());
				rowCell.setCellStyle(style);
				//第十七列  条码印刷技术类型
				rowCell = row.createCell(17);
				if(coms.get(i).getFlat())Typ+="、平版胶印";
				if(coms.get(i).getGravure())Typ+="、凹版印刷";
				if(coms.get(i).getWebby())Typ+="、丝网印刷";
				if(coms.get(i).getFlexible())Typ+="、柔性版印刷";
				if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="、"+coms.get(i).getElsetype();
//				Typ=Typ.substring(1, Typ.length());
				if(Typ.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Typ.substring(1, Typ.length()));
				}
				rowCell.setCellStyle(style);
				//第十八列  印刷载体材料
				rowCell = row.createCell(18);
				if(coms.get(i).getPapery())Mat+="、纸质";
				if(coms.get(i).getPastern())Mat+="、不干胶";
				if(coms.get(i).getFrilling())Mat+="、瓦楞纸";
				if(coms.get(i).getMetal())Mat+="、金属";
				if(coms.get(i).getPlastic())Mat+="、塑料";
				if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="、"+coms.get(i).getElsematerial();
				if(Mat.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Mat.substring(1, Mat.length()));
				}
				rowCell.setCellStyle(style);
				//第十九列  备注
				rowCell = row.createCell(19);
				rowCell.setCellValue(coms.get(i).getRemarks());
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
	//导出多个工作簿
	public boolean exportByTwo(String filePath, String d1, String d2, String branchId) {
		String[] branch=branchId.split(",");
		String title="";
		//创建excel，并写入数据
		try{
			for(int j=0;j<branch.length;j++){
				Division div=divisionMapper.getDivisionByCode(branch[j]);
				title=div.getDivisionname();
				//创建一个工作薄
//				XSSFWorkbook workbook = new XSSFWorkbook();
				//添加一个sheet,sheet名
				XSSFSheet sheet = workbook.createSheet(title+"业务统计数据");
				//合并单元格，第一行、最后一行、第一列、最后一列
				sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
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
				titleCell.setCellValue(title+"业务统计数据");
				titleCell.setCellStyle(titleStyle);
				//---------------以上是第一行-----------------
				sheet.setColumnWidth(0,25 * 512);
				sheet.autoSizeColumn(1);
				sheet.autoSizeColumn(2);
				sheet.autoSizeColumn(3);
				//设置其他正文单元格格式
				XSSFCellStyle style = workbook.createCellStyle();
				style.setAlignment(HorizontalAlignment.CENTER);
				//设置第二行表头
				XSSFRow rowHeader = sheet.createRow(1);
				XSSFCell cell = rowHeader.createCell(0);
				cell.setCellValue("创建时间段");
				cell.setCellStyle(style);
				cell = rowHeader.createCell(1);
				cell.setCellValue("新办");
				cell.setCellStyle(style);
				cell = rowHeader.createCell(2);
				cell.setCellValue("复认");
				cell.setCellStyle(style);
				cell = rowHeader.createCell(3);
				cell.setCellValue("变更");
				cell.setCellStyle(style);
				cell = rowHeader.createCell(4);
				
				//-------------表头完------------
				//设置第一列为所有分中心
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
//		//		当月最后一天
//				int endDay=0;
//		//		本月起始时间
//				String month1="";
//		//		本月终止时间
//				String month2="";
				
//		for(int i=startMon;i<=intendMon;i++){
//			endDay=getDaysofMonth(Integer.parseInt(year),i);
//			month1=year+"-"+String.valueOf(i)+"-"+"01";
//			month2=year+"-"+String.valueOf(i)+"-"+String.valueOf(endDay);
			Date sd=sdf.parse(d1);
		    Timestamp s = getStime(sd);//获取起始时间
		    Date ed=sdf.parse(d2);
			Timestamp e = getEtime(ed);//获取终止时间
				index++;//行号++，从2开始
				app=countMapper.getCount(branch[j],"%XB%",s,e);
				recog=countMapper.getCount(branch[j],"%FR%",s,e);
				change=countMapper.getCount(branch[j],"%BG%",s,e);
//				countApp+=app;
//				countRecog+=recog;
//				countChange+=change;
				//生成一个新行
				XSSFRow row = sheet.createRow(index);
				XSSFCell rowCell = row.createCell(0);//第一列
				rowCell.setCellValue(s+"~"+e);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(1);//第二列
				rowCell.setCellValue(app);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(2);//第三列
				rowCell.setCellValue(recog);
				rowCell.setCellStyle(style);
				rowCell = row.createCell(3);//第四列
				rowCell.setCellValue(change);
				rowCell.setCellStyle(style);
//			}
			//生成一个新行
//			row = sheet.createRow(index);
//			rowCell = row.createCell(0);//第一列
//			rowCell.setCellValue("总计");
//			rowCell.setCellStyle(style);
//			rowCell = row.createCell(1);//第二列
//			rowCell.setCellValue(countApp);
//			rowCell.setCellStyle(style);
//			rowCell = row.createCell(2);//第三列
//			rowCell.setCellValue(countRecog);
//			rowCell.setCellStyle(style);
//			rowCell = row.createCell(3);//第四列
//			rowCell.setCellValue(countChange);
//			rowCell.setCellStyle(style);
			
			//新办
			//添加一个sheet,sheet名
			sheet = workbook.createSheet(title+"新办");
			//合并单元格，第一行、最后一行、第一列、最后一列
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));
			//创建第一行
			XSSFRow titleRow1 = sheet.createRow(0);
			//创建标题单元格格式
			XSSFCellStyle titleStyle1 = workbook.createCellStyle();
			titleStyle1.setAlignment(HorizontalAlignment.CENTER);
			//创建一个字体
			XSSFFont titlefont1 = workbook.createFont();
			titlefont1.setFontHeight(16);//标题字体16号
			//将字体应用到当前的格式中
			titleStyle1.setFont(titlefont1);
			//在第一行中创建一个单元格
			XSSFCell titleCell1 = titleRow1.createCell(0);
			//设置值、样式和标题
			titleCell1.setCellValue(title+"的新办业务企业信息");
			titleCell1.setCellStyle(titleStyle1);
			//---------------以上是第一行-----------------
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
			
			//设置其他正文单元格格式
			style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//设置第二行表头
			XSSFRow rowHeader1 = sheet.createRow(1);
			cell = rowHeader1.createCell(0);
			cell.setCellValue("编号");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(1);
			cell.setCellValue("状态");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(2);
			cell.setCellValue("分支机构");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(3);
			cell.setCellValue("企业名称");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(4);
			cell.setCellValue("证书编号");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(5);
			cell.setCellValue("发证日期");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(6);
			cell.setCellValue("到期日期");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(7);
			cell.setCellValue("更新日期");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(8);
			cell.setCellValue("企业性质");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(9);
			cell.setCellValue("注册资本");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(10);
			cell.setCellValue("邮政编码");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(11);
			cell.setCellValue("传真");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(12);
			cell.setCellValue("法人代表");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(13);
			cell.setCellValue("法人电话");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(14);
			cell.setCellValue("联系人");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(15);
			cell.setCellValue("联系人电话");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(16);
			cell.setCellValue("条码印刷技术负责人");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(17);
			cell.setCellValue("条码印刷技术类型");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(18);
			cell.setCellValue("印刷载体材料");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(19);
			cell.setCellValue("备注");
			cell.setCellStyle(style);
			
			//-------------表头完------------
			//设置第一列为状态
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
//			    Timestamp s = getStime(sd);//获取起始时间
//			    Date ed=sdf.parse(month2);
//				Timestamp e = getEtime(ed);//获取终止时间
			coms=companyInfoMapper.getByTwo(branch[j], "%XB%",s,e);
//			}
			String certappdate ="";
			String certtodate="";
			String createdate="";
				for(int i=0;i<coms.size();i++){
					String Typ="";
					String Mat="";
				index1++;//行号++，从2开始
				//生成一个新行
				row = sheet.createRow(index1);
				//首列  编号
				rowCell = row.createCell(0);
				rowCell.setCellValue((i+1)+"");
				rowCell.setCellStyle(style);
				//第一列  状态
				rowCell = row.createCell(1);
				ywStatus=coms.get(i).getStatus();
				if(ywStatus!=null){
					if(ywStatus==37){
						status="复认中";
					}else if(ywStatus==34){
						status="复认完成";
					}else if(ywStatus==36){
						status="变更中";
					}else if(ywStatus==35){
						status="变更完成";
					}else if(ywStatus==17){
						status="申请完成";
					}
				}
				rowCell.setCellValue(status);
				rowCell.setCellStyle(style);
				//第二列 分支机构名称
				rowCell = row.createCell(2);
				rowCell.setCellValue(coms.get(i).getBranchName());
				rowCell.setCellStyle(style);
				//第三列  企业名称
				rowCell = row.createCell(3);
				rowCell.setCellValue(coms.get(i).getEnterprisename());
				rowCell.setCellStyle(style);
				//第四列  证书编号
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
				//第五列  发证日期
				rowCell = row.createCell(5);
				if(coms.get(i).getCertappdate()!=null){
					certappdate = sdf.format(coms.get(i).getCertappdate());
					rowCell.setCellValue(certappdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//第六列  到期日期
				rowCell = row.createCell(6);
				if(coms.get(i).getCerttodate()!=null){
					certtodate=sdf.format(coms.get(i).getCerttodate());
					rowCell.setCellValue(certtodate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//第七列  创建日期
				rowCell = row.createCell(7);
				if(coms.get(i).getCreatedate()!=null){
					createdate=sdf.format(coms.get(i).getCreatedate());
					rowCell.setCellValue(createdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//第八列  企业性质
				rowCell = row.createCell(8);
				rowCell.setCellValue(coms.get(i).getEnterprisekind());
				rowCell.setCellStyle(style);
				//第九列  注册资本
				rowCell = row.createCell(9);
				rowCell.setCellValue(coms.get(i).getRegistercapital());
				rowCell.setCellStyle(style);
				//第十列  邮政编码
				rowCell = row.createCell(10);
				rowCell.setCellValue(coms.get(i).getPostalcode());
				rowCell.setCellStyle(style);
				//第十一列 传真
				rowCell = row.createCell(11);
				rowCell.setCellValue(coms.get(i).getFax());
				rowCell.setCellStyle(style);
				//十二列  法人代表
				rowCell = row.createCell(12);
				rowCell.setCellValue(coms.get(i).getArtificialperson());
				rowCell.setCellStyle(style);
				//第十三列  法人电话
				rowCell = row.createCell(13);
				rowCell.setCellValue(coms.get(i).getAptel());
				rowCell.setCellStyle(style);
				//第十四列  联系人
				rowCell = row.createCell(14);
				rowCell.setCellValue(coms.get(i).getLinkman());
				rowCell.setCellStyle(style);
				//第十五列  联系人电话
				rowCell = row.createCell(15);
				rowCell.setCellValue(coms.get(i).getLtel());
				rowCell.setCellStyle(style);
				//第十六列  条码印刷技术负责人
				rowCell = row.createCell(16);
				rowCell.setCellValue(coms.get(i).getBcprincipal());
				rowCell.setCellStyle(style);
				//第十七列  条码印刷技术类型
				rowCell = row.createCell(17);
				if(coms.get(i).getFlat())Typ+="、平版胶印";
				if(coms.get(i).getGravure())Typ+="、凹版印刷";
				if(coms.get(i).getWebby())Typ+="、丝网印刷";
				if(coms.get(i).getFlexible())Typ+="、柔性版印刷";
				if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="、"+coms.get(i).getElsetype();
//				Typ=Typ.substring(1, Typ.length());
				if(Typ.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Typ.substring(1, Typ.length()));
				}
				rowCell.setCellStyle(style);
				//第十八列  印刷载体材料
				rowCell = row.createCell(18);
				if(coms.get(i).getPapery())Mat+="、纸质";
				if(coms.get(i).getPastern())Mat+="、不干胶";
				if(coms.get(i).getFrilling())Mat+="、瓦楞纸";
				if(coms.get(i).getMetal())Mat+="、金属";
				if(coms.get(i).getPlastic())Mat+="、塑料";
				if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="、"+coms.get(i).getElsematerial();
				if(Mat.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Mat.substring(1, Mat.length()));
				}
				rowCell.setCellStyle(style);
				//第十九列  备注
				rowCell = row.createCell(19);
				rowCell.setCellValue(coms.get(i).getRemarks());
				rowCell.setCellStyle(style);
				}
				
				
			//复认
			//添加一个sheet,sheet名
			sheet = workbook.createSheet(title+"复认");
			//合并单元格，第一行、最后一行、第一列、最后一列
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));
			//创建第一行
			titleRow1 = sheet.createRow(0);
			//创建标题单元格格式
			titleStyle1 = workbook.createCellStyle();
			titleStyle1.setAlignment(HorizontalAlignment.CENTER);
			//创建一个字体
			titlefont1 = workbook.createFont();
			titlefont1.setFontHeight(16);//标题字体16号
			//将字体应用到当前的格式中
			titleStyle1.setFont(titlefont1);
			//在第一行中创建一个单元格
			titleCell1 = titleRow1.createCell(0);
			//设置值、样式和标题
			titleCell1.setCellValue(title+"的复认业务企业信息");
			titleCell1.setCellStyle(titleStyle1);
			//---------------以上是第一行-----------------
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
			//设置其他正文单元格格式
			style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//设置第二行表头
			rowHeader1 = sheet.createRow(1);
			cell = rowHeader1.createCell(0);
			cell.setCellValue("编号");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(1);
			cell.setCellValue("状态");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(2);
			cell.setCellValue("分支机构");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(3);
			cell.setCellValue("企业名称");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(4);
			cell.setCellValue("证书编号");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(5);
			cell.setCellValue("发证日期");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(6);
			cell.setCellValue("到期日期");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(7);
			cell.setCellValue("更新日期");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(8);
			cell.setCellValue("企业性质");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(9);
			cell.setCellValue("注册资本");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(10);
			cell.setCellValue("邮政编码");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(11);
			cell.setCellValue("传真");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(12);
			cell.setCellValue("法人代表");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(13);
			cell.setCellValue("法人电话");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(14);
			cell.setCellValue("联系人");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(15);
			cell.setCellValue("联系人电话");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(16);
			cell.setCellValue("条码印刷技术负责人");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(17);
			cell.setCellValue("条码印刷技术类型");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(18);
			cell.setCellValue("印刷载体材料");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(19);
			cell.setCellValue("备注");
			cell.setCellStyle(style);
			//-------------表头完------------
			//设置第一列为状态
			int index2=1;
			coms=companyInfoMapper.getByTwo(branch[j], "%FR%",s,e);
				for(int i=0;i<coms.size();i++){
					String Typ="";
					String Mat="";
				index2++;//行号++，从2开始
				//生成一个新行
				row = sheet.createRow(index2);
				//首列  编号
				rowCell = row.createCell(0);
				rowCell.setCellValue((i+1)+"");
				rowCell.setCellStyle(style);
				//第一列  状态
				rowCell = row.createCell(1);
				ywStatus=coms.get(i).getStatus();
				if(ywStatus!=null){
					if(ywStatus==37){
						status="复认中";
					}else if(ywStatus==34){
						status="复认完成";
					}else if(ywStatus==36){
						status="变更中";
					}else if(ywStatus==35){
						status="变更完成";
					}else if(ywStatus==17){
						status="申请完成";
					}
				}
				rowCell.setCellValue(status);
				rowCell.setCellStyle(style);
				//第二列 分支机构名称
				rowCell = row.createCell(2);
				rowCell.setCellValue(coms.get(i).getBranchName());
				rowCell.setCellStyle(style);
				//第三列  企业名称
				rowCell = row.createCell(3);
				rowCell.setCellValue(coms.get(i).getEnterprisename());
				rowCell.setCellStyle(style);
				//第四列  证书编号
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
				//第五列  发证日期
				rowCell = row.createCell(5);
				if(coms.get(i).getCertappdate()!=null){
					certappdate = sdf.format(coms.get(i).getCertappdate());
					rowCell.setCellValue(certappdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//第六列  到期日期
				rowCell = row.createCell(6);
				if(coms.get(i).getCerttodate()!=null){
					certtodate=sdf.format(coms.get(i).getCerttodate());
					rowCell.setCellValue(certtodate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//第七列  创建日期
				rowCell = row.createCell(7);
				if(coms.get(i).getCreatedate()!=null){
					createdate=sdf.format(coms.get(i).getCreatedate());
					rowCell.setCellValue(createdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//第八列  企业性质
				rowCell = row.createCell(8);
				rowCell.setCellValue(coms.get(i).getEnterprisekind());
				rowCell.setCellStyle(style);
				//第九列  注册资本
				rowCell = row.createCell(9);
				rowCell.setCellValue(coms.get(i).getRegistercapital());
				rowCell.setCellStyle(style);
				//第十列  邮政编码
				rowCell = row.createCell(10);
				rowCell.setCellValue(coms.get(i).getPostalcode());
				rowCell.setCellStyle(style);
				//第十一列 传真
				rowCell = row.createCell(11);
				rowCell.setCellValue(coms.get(i).getFax());
				rowCell.setCellStyle(style);
				//十二列  法人代表
				rowCell = row.createCell(12);
				rowCell.setCellValue(coms.get(i).getArtificialperson());
				rowCell.setCellStyle(style);
				//第十三列  法人电话
				rowCell = row.createCell(13);
				rowCell.setCellValue(coms.get(i).getAptel());
				rowCell.setCellStyle(style);
				//第十四列  联系人
				rowCell = row.createCell(14);
				rowCell.setCellValue(coms.get(i).getLinkman());
				rowCell.setCellStyle(style);
				//第十五列  联系人电话
				rowCell = row.createCell(15);
				rowCell.setCellValue(coms.get(i).getLtel());
				rowCell.setCellStyle(style);
				//第十六列  条码印刷技术负责人
				rowCell = row.createCell(16);
				rowCell.setCellValue(coms.get(i).getBcprincipal());
				rowCell.setCellStyle(style);
				//第十七列  条码印刷技术类型
				rowCell = row.createCell(17);
				if(coms.get(i).getFlat())Typ+="、平版胶印";
				if(coms.get(i).getGravure())Typ+="、凹版印刷";
				if(coms.get(i).getWebby())Typ+="、丝网印刷";
				if(coms.get(i).getFlexible())Typ+="、柔性版印刷";
				if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="、"+coms.get(i).getElsetype();
//				Typ=Typ.substring(1, Typ.length());
				if(Typ.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Typ.substring(1, Typ.length()));
				}
				rowCell.setCellStyle(style);
				//第十八列  印刷载体材料
				rowCell = row.createCell(18);
				if(coms.get(i).getPapery())Mat+="、纸质";
				if(coms.get(i).getPastern())Mat+="、不干胶";
				if(coms.get(i).getFrilling())Mat+="、瓦楞纸";
				if(coms.get(i).getMetal())Mat+="、金属";
				if(coms.get(i).getPlastic())Mat+="、塑料";
				if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="、"+coms.get(i).getElsematerial();
				if(Mat.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Mat.substring(1, Mat.length()));
				}
				rowCell.setCellStyle(style);
				//第十九列  备注
				rowCell = row.createCell(19);
				rowCell.setCellValue(coms.get(i).getRemarks());
				rowCell.setCellStyle(style);
				}
				
			//变更
			//添加一个sheet,sheet名
			sheet = workbook.createSheet(title+"变更");
			//合并单元格，第一行、最后一行、第一列、最后一列
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,19));
			//创建第一行
			titleRow1 = sheet.createRow(0);
			//创建标题单元格格式
			titleStyle1 = workbook.createCellStyle();
			titleStyle1.setAlignment(HorizontalAlignment.CENTER);
			//创建一个字体
			titlefont1 = workbook.createFont();
			titlefont1.setFontHeight(16);//标题字体16号
			//将字体应用到当前的格式中
			titleStyle1.setFont(titlefont1);
			//在第一行中创建一个单元格
			titleCell1 = titleRow1.createCell(0);
			//设置值、样式和标题
			titleCell1.setCellValue(title+"的变更业务企业信息");
			titleCell1.setCellStyle(titleStyle1);
			//---------------以上是第一行-----------------
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
			//设置其他正文单元格格式
			style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//设置第二行表头
			rowHeader1 = sheet.createRow(1);
			cell = rowHeader1.createCell(0);
			cell.setCellValue("编号");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(1);
			cell.setCellValue("状态");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(2);
			cell.setCellValue("分支机构");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(3);
			cell.setCellValue("企业名称");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(4);
			cell.setCellValue("证书编号");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(5);
			cell.setCellValue("发证日期");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(6);
			cell.setCellValue("到期日期");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(7);
			cell.setCellValue("更新日期");
			cell.setCellStyle(style);
			cell = rowHeader1.createCell(8);
			cell.setCellValue("企业性质");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(9);
			cell.setCellValue("注册资本");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(10);
			cell.setCellValue("邮政编码");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(11);
			cell.setCellValue("传真");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(12);
			cell.setCellValue("法人代表");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(13);
			cell.setCellValue("法人电话");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(14);
			cell.setCellValue("联系人");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(15);
			cell.setCellValue("联系人电话");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(16);
			cell.setCellValue("条码印刷技术负责人");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(17);
			cell.setCellValue("条码印刷技术类型");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(18);
			cell.setCellValue("印刷载体材料");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(19);
			cell.setCellValue("备注");
			cell.setCellStyle(style);
			//-------------表头完------------
			//设置第一列为状态
			int index3=1;
			coms=companyInfoMapper.getByTwo(branch[j], "%BG%",s,e);
				for(int i=0;i<coms.size();i++){
					String Typ="";
					String Mat="";
				index3++;//行号++，从2开始
				//生成一个新行
				row = sheet.createRow(index3);
				//首列  编号
				rowCell = row.createCell(0);
				rowCell.setCellValue((i+1)+"");
				rowCell.setCellStyle(style);
				//第一列  状态
				rowCell = row.createCell(1);
				ywStatus=coms.get(i).getStatus();
				if(ywStatus!=null){
					if(ywStatus==37){
						status="复认中";
					}else if(ywStatus==34){
						status="复认完成";
					}else if(ywStatus==36){
						status="变更中";
					}else if(ywStatus==35){
						status="变更完成";
					}else if(ywStatus==17){
						status="申请完成";
					}
				}
				rowCell.setCellValue(status);
				rowCell.setCellStyle(style);
				//第二列 分支机构名称
				rowCell = row.createCell(2);
				rowCell.setCellValue(coms.get(i).getBranchName());
				rowCell.setCellStyle(style);
				//第三列  企业名称
				rowCell = row.createCell(3);
				rowCell.setCellValue(coms.get(i).getEnterprisename());
				rowCell.setCellStyle(style);
				//第四列  证书编号
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
				//第五列  发证日期
				rowCell = row.createCell(5);
				if(coms.get(i).getCertappdate()!=null){
					certappdate = sdf.format(coms.get(i).getCertappdate());
					rowCell.setCellValue(certappdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//第六列  到期日期
				rowCell = row.createCell(6);
				if(coms.get(i).getCerttodate()!=null){
					certtodate=sdf.format(coms.get(i).getCerttodate());
					rowCell.setCellValue(certtodate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//第七列  创建日期
				rowCell = row.createCell(7);
				if(coms.get(i).getCreatedate()!=null){
					createdate=sdf.format(coms.get(i).getCreatedate());
					rowCell.setCellValue(createdate);
				}else{
					rowCell.setCellValue("");
				}
				rowCell.setCellStyle(style);
				//第八列  企业性质
				rowCell = row.createCell(8);
				rowCell.setCellValue(coms.get(i).getEnterprisekind());
				rowCell.setCellStyle(style);
				//第九列  注册资本
				rowCell = row.createCell(9);
				rowCell.setCellValue(coms.get(i).getRegistercapital());
				rowCell.setCellStyle(style);
				//第十列  邮政编码
				rowCell = row.createCell(10);
				rowCell.setCellValue(coms.get(i).getPostalcode());
				rowCell.setCellStyle(style);
				//第十一列 传真
				rowCell = row.createCell(11);
				rowCell.setCellValue(coms.get(i).getFax());
				rowCell.setCellStyle(style);
				//十二列  法人代表
				rowCell = row.createCell(12);
				rowCell.setCellValue(coms.get(i).getArtificialperson());
				rowCell.setCellStyle(style);
				//第十三列  法人电话
				rowCell = row.createCell(13);
				rowCell.setCellValue(coms.get(i).getAptel());
				rowCell.setCellStyle(style);
				//第十四列  联系人
				rowCell = row.createCell(14);
				rowCell.setCellValue(coms.get(i).getLinkman());
				rowCell.setCellStyle(style);
				//第十五列  联系人电话
				rowCell = row.createCell(15);
				rowCell.setCellValue(coms.get(i).getLtel());
				rowCell.setCellStyle(style);
				//第十六列  条码印刷技术负责人
				rowCell = row.createCell(16);
				rowCell.setCellValue(coms.get(i).getBcprincipal());
				rowCell.setCellStyle(style);
				//第十七列  条码印刷技术类型
				rowCell = row.createCell(17);
				if(coms.get(i).getFlat())Typ+="、平版胶印";
				if(coms.get(i).getGravure())Typ+="、凹版印刷";
				if(coms.get(i).getWebby())Typ+="、丝网印刷";
				if(coms.get(i).getFlexible())Typ+="、柔性版印刷";
				if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="、"+coms.get(i).getElsetype();
//				Typ=Typ.substring(1, Typ.length());
				if(Typ.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Typ.substring(1, Typ.length()));
				}
				rowCell.setCellStyle(style);
				//第十八列  印刷载体材料
				rowCell = row.createCell(18);
				if(coms.get(i).getPapery())Mat+="、纸质";
				if(coms.get(i).getPastern())Mat+="、不干胶";
				if(coms.get(i).getFrilling())Mat+="、瓦楞纸";
				if(coms.get(i).getMetal())Mat+="、金属";
				if(coms.get(i).getPlastic())Mat+="、塑料";
				if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="、"+coms.get(i).getElsematerial();
				if(Mat.length()==0){
					rowCell.setCellValue("");
				}else{
					rowCell.setCellValue(Mat.substring(1, Mat.length()));
				}
				rowCell.setCellStyle(style);
				//第十九列  备注
				rowCell = row.createCell(19);
				rowCell.setCellValue(coms.get(i).getRemarks());
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
	//导出一个工作簿
//	public boolean exportByTwo(String title,String filePath, String d1, String d2, String branchId) {
//	String[] branch=branchId.split(",");
//	//创建excel，并写入数据
//	try{
//		//创建一个工作薄
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		//添加一个sheet,sheet名
//		XSSFSheet sheet = workbook.createSheet(title);
//		//合并单元格，第一行、最后一行、第一列、最后一列
//		sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
//		//创建第一行
//		XSSFRow titleRow = sheet.createRow(0);
//		//创建标题单元格格式
//		XSSFCellStyle titleStyle = workbook.createCellStyle();
//		titleStyle.setAlignment(HorizontalAlignment.CENTER);
//		//创建一个字体
//		XSSFFont titlefont = workbook.createFont();
//		titlefont.setFontHeight(16);//标题字体16号
//		//将字体应用到当前的格式中
//		titleStyle.setFont(titlefont);
//		//在第一行中创建一个单元格
//		XSSFCell titleCell = titleRow.createCell(0);
//		//设置值、样式和标题
//		titleCell.setCellValue(title);
//		titleCell.setCellStyle(titleStyle);
//		//---------------以上是第一行-----------------
//		sheet.setColumnWidth(0,25 * 256);
//		sheet.autoSizeColumn(1);
//		sheet.autoSizeColumn(2);
//		sheet.autoSizeColumn(3);
//		//设置其他正文单元格格式
//		XSSFCellStyle style = workbook.createCellStyle();
//		style.setAlignment(HorizontalAlignment.CENTER);
//		//设置第二行表头
//		XSSFRow rowHeader = sheet.createRow(1);
//		XSSFCell cell = rowHeader.createCell(0);
//		cell.setCellValue("");
//		cell.setCellStyle(style);
//		cell = rowHeader.createCell(1);
//		cell.setCellValue("新办");
//		cell.setCellStyle(style);
//		cell = rowHeader.createCell(2);
//		cell.setCellValue("复认");
//		cell.setCellStyle(style);
//		cell = rowHeader.createCell(3);
//		cell.setCellValue("变更");
//		cell.setCellStyle(style);
//		cell = rowHeader.createCell(4);
//		
//		//-------------表头完------------
//		//设置第一列为所有分中心
//		int index = 1;
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		int app=0;
//		int recog=0;
//		int change=0;
//		int countApp=0;
//		int countRecog=0;
//		int countChange=0;
//		Date sd=sdf.parse(d1);
//	    Timestamp s = getStime(sd);//获取起始时间
//	    Date ed=sdf.parse(d2);
//		Timestamp e = getEtime(ed);//获取终止时间
//	for(int j=0;j<branch.length;j++){
//		index++;//行号++，从2开始
//		app=countMapper.getCount(branch[j],"%XB%",s,e);
//		recog=countMapper.getCount(branch[j],"%FR%",s,e);
//		change=countMapper.getCount(branch[j],"%BG%",s,e);
//		countApp+=app;
//		countRecog+=recog;
//		countChange+=change;
//		//生成一个新行
//		XSSFRow row = sheet.createRow(index);
//		XSSFCell rowCell = row.createCell(0);//第一列
//		rowCell.setCellValue(divisionMapper.getDivisionByCode(branch[j]).getDivisionname());
//		rowCell.setCellStyle(style);
//		rowCell = row.createCell(1);//第二列
//		rowCell.setCellValue(app);
//		rowCell.setCellStyle(style);
//		rowCell = row.createCell(2);//第三列
//		rowCell.setCellValue(recog);
//		rowCell.setCellStyle(style);
//		rowCell = row.createCell(3);//第四列
//		rowCell.setCellValue(change);
//		rowCell.setCellStyle(style);
//		}
//		//生成一个新行
//		XSSFRow row = sheet.createRow(index);
//		XSSFCell rowCell = row.createCell(0);//第一列
//		rowCell.setCellValue("总计");
//		rowCell.setCellStyle(style);
//		rowCell = row.createCell(1);//第二列
//		rowCell.setCellValue(countApp);
//		rowCell.setCellStyle(style);
//		rowCell = row.createCell(2);//第三列
//		rowCell.setCellValue(countRecog);
//		rowCell.setCellStyle(style);
//		rowCell = row.createCell(3);//第四列
//		rowCell.setCellValue(countChange);
//		rowCell.setCellStyle(style);
//		//将文件保存到指定的位置
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
