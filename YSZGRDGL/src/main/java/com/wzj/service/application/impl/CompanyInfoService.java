package com.wzj.service.application.impl;

import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.spi.CalendarDataProvider;

import org.apache.ibatis.annotations.Update;
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

import com.wzj.dao.CompanyInfoMapper;
import com.wzj.pojo.CompanyInfo;
import com.wzj.pojo.Division;
import com.wzj.service.application.ICompanyInfoService;

@Service("companyInfoServices")
public class CompanyInfoService implements ICompanyInfoService {
	@Autowired
	private CompanyInfoMapper companyInfoMapper;
	
	@Override
	public int insertById(int titleId) {
		return companyInfoMapper.insertById(titleId);
	}
	public int delete(int id) {
		return companyInfoMapper.delete(id);
	}
	public int setStatus(int id, int status) {
		return companyInfoMapper.setStatus(id,status);
	}
	public void changeZhuxiao(int id, String zhuxiao) {
		companyInfoMapper.changeZhuxiao(id,zhuxiao);
	}
	public List<CompanyInfo> getComByStatus(int status, String branchId) {
		return companyInfoMapper.getComByStatus(status,branchId);
	}
	@Override
	public int insert(CompanyInfo com) {
		return companyInfoMapper.insert(com);
	}
	public int update(CompanyInfo com) {
		return companyInfoMapper.update(com);
	}
	public List<CompanyInfo> getAll(String branchId) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getAll();
		}else{
			return companyInfoMapper.getByBranchId(branchId);
		}
	}
	@Override
	public int certificateExpired() {
		List<CompanyInfo> coms = companyInfoMapper.getAll();
		int result=0;
		Calendar calendar = Calendar.getInstance();
		for(CompanyInfo com:coms){
			Date toDate=com.getCerttodate();
			int over=toDate.compareTo(new Date());
			//boolean bool=((com.getStatus()==11)||(com.getStatus()==12)||com.getStatus()==5));
			boolean bool=(com.getStatus()==17)||(com.getStatus()==34)||(com.getStatus()==5);
			if(over<0&&bool){//时间过了，而且状态处于（新申请成功、复认成功、变更成功的）
				companyInfoMapper.setStatus(com.getId(),18);
				result++;
			}
			//如果到期半年后仍然没有复认，则注销
			calendar.add(Calendar.MONTH, -6);
			Date half=calendar.getTime();
			if((toDate.compareTo(half)<0)&&bool){//如果半年前的今天在过期时间前,自动注销
				companyInfoMapper.setZhuxiao(com.getId());
			}
		}
		return result;
	}
	public int cancel(int id) {
		return companyInfoMapper.cancel(id);
	}
	public int activate(int id) {
		return companyInfoMapper.activate(id);
		
	}
	public CompanyInfo getComById(int id) {
		return companyInfoMapper.getComById(id);
	}
	public List<CompanyInfo> getInTime(String sTime, String eTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//先转成Date型
		Date sDate;
		Date eDate;
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
		return companyInfoMapper.getInTime(sTamp,eTamp);
	}
	public CompanyInfo getComByBussino(String xbId) {
		return companyInfoMapper.getComByBussino(xbId);
	}
	public List<CompanyInfo> getCancel(String branchId) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getAllCancel();
		}else{
			return companyInfoMapper.getCancelByBranchId(branchId);
		}
	}
	public int reActivate(int id) {
		return companyInfoMapper.reActivate(id);
	}
	public List<CompanyInfo> getAllBySql(String result) {
		return companyInfoMapper.getAllBySql(result);
	}
	public int reChange(int id) {
		return companyInfoMapper.reChange(id);
		
	}
	public CompanyInfo getComByName(String companyName) {
		return companyInfoMapper.getComByName(companyName);
	}
	public List<CompanyInfo> getRecentCompany(String defaultStartDate, String defaultEndDate, String branchId, String sSearch) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getRecentCompany(defaultStartDate,defaultEndDate,sSearch);
		}else{
			return companyInfoMapper.getRecentCompany2(defaultStartDate,defaultEndDate,branchId,sSearch);
		}
		
	}
	public List<CompanyInfo> getByAddress(String address, String branchId) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getByAddress("%"+address+"%");
		}else{
			return companyInfoMapper.getByaddress2("%"+address+"%",branchId);
		}
		
	}
	public List<CompanyInfo> getByAddress2(String address, String branchId) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getByAddress3("%"+address+"%");
		}else{
			return companyInfoMapper.getByaddress4("%"+address+"%",branchId);
		}
	}
	public List<CompanyInfo> getAllByLike(String branchId, String sSearch) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getAll2(sSearch);
		}else{
			return companyInfoMapper.getAllByLike(branchId,sSearch);
		}
	}
	public List<CompanyInfo> getCancel2(String branchId, String sSearch) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getAllCancel1(sSearch);
		}else{
			return companyInfoMapper.getCancelByBranchId1(branchId,sSearch);
		}
	}
	public List<CompanyInfo> getRecentCompany1(String defaultStartDate, String defaultEndDate, String branchId) {
		if("0000".equals(branchId)){
			return companyInfoMapper.getRecentCompany3(defaultStartDate,defaultEndDate);
		}else{
			return companyInfoMapper.getRecentCompany4(defaultStartDate,defaultEndDate,branchId);
		}
	}
	public boolean exportExcel(String title, String filePath, List<CompanyInfo> coms) {
	//创建excel，并写入数据
	try{
		//创建一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		//添加一个sheet,sheet名
		XSSFSheet sheet = workbook.createSheet(title);
		//合并单元格，第一行、最后一行、第一列、最后一列
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,18));
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
//		sheet.setColumnWidth(0,25 * 256);
		sheet.setColumnWidth(0,25 * 256);
		sheet.setColumnWidth(1,25 * 256);
		sheet.setColumnWidth(2,25 * 512);
		sheet.autoSizeColumn(3);
		sheet.setColumnWidth(4,25 * 256);
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
		sheet.setColumnWidth(15,25 * 512);
		sheet.setColumnWidth(16,25 * 512);
		sheet.setColumnWidth(17,25 * 512);
		sheet.setColumnWidth(18,25 * 512);
		//设置其他正文单元格格式
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		//设置第二行表头
		XSSFRow rowHeader = sheet.createRow(1);
		XSSFCell cell = rowHeader.createCell(0);
		cell.setCellValue("状态");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(1);
		cell.setCellValue("分支机构");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(2);
		cell.setCellValue("企业名称");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(3);
		cell.setCellValue("证书编号");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(4);
		cell.setCellValue("发证日期");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(5);
		cell.setCellValue("到期日期");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(6);
		cell.setCellValue("更新日期");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(7);
		cell.setCellValue("企业性质");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(8);
		cell.setCellValue("注册资本");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(9);
		cell.setCellValue("邮政编码");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(10);
		cell.setCellValue("传真");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(11);
		cell.setCellValue("法人代表");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(12);
		cell.setCellValue("法人电话");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(13);
		cell.setCellValue("联系人");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(14);
		cell.setCellValue("联系人电话");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(15);
		cell.setCellValue("条码印刷技术负责人");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(16);
		cell.setCellValue("条码印刷技术类型");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(17);
		cell.setCellValue("印刷载体材料");
		cell.setCellStyle(style);
		cell = rowHeader.createCell(18);
		cell.setCellValue("备注");
		cell.setCellStyle(style);
		//-------------表头完------------
		//设置第一列为状态
		int index = 1;
		String status="";
		Integer ywStatus;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String certappdate ="";
		String certtodate="";
		String createdate="";

			for(int i=0;i<coms.size();i++){
				String Typ="";
				String Mat="";
					index++;//行号++，从2开始
					//生成一个新行
					XSSFRow row = sheet.createRow(index);
					//第一列  状态
					XSSFCell rowCell = row.createCell(0);
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
					rowCell = row.createCell(1);
					rowCell.setCellValue(coms.get(i).getBranchName());
					rowCell.setCellStyle(style);
					//第三列  企业名称
					rowCell = row.createCell(2);
					rowCell.setCellValue(coms.get(i).getEnterprisename());
					rowCell.setCellStyle(style);
					//第四列  证书编号
					rowCell = row.createCell(3);
					String str = "";
					if(coms.get(i).getSerial()!=null){
						str=coms.get(i).getSerial()+"";
					int len = coms.get(i).getSerial().toString().length();
                      len = 6-len;
                      for(int j=0;j<len;j++){
                    	  str = "0"+str;
		              }
                     }
					rowCell.setCellValue(str);
					rowCell.setCellStyle(style);
					//第五列  发证日期
					rowCell = row.createCell(4);
					if(coms.get(i).getCertappdate()!=null){
						certappdate = formatter.format(coms.get(i).getCertappdate());
						rowCell.setCellValue(certappdate);
					}else{
						rowCell.setCellValue("");
					}
					rowCell.setCellStyle(style);
					//第六列  到期日期
					rowCell = row.createCell(5);
					if(coms.get(i).getCerttodate()!=null){
						certtodate=formatter.format(coms.get(i).getCerttodate());
						rowCell.setCellValue(certtodate);
					}else{
						rowCell.setCellValue("");
					}
					rowCell.setCellStyle(style);
					//第七列  创建日期
					rowCell = row.createCell(6);
					if(coms.get(i).getCreatedate()!=null){
						createdate=formatter.format(coms.get(i).getCreatedate());
						rowCell.setCellValue(createdate);
					}else{
						rowCell.setCellValue("");
					}
					rowCell.setCellStyle(style);
					//第八列  企业性质
					rowCell = row.createCell(7);
					rowCell.setCellValue(coms.get(i).getEnterprisekind());
					rowCell.setCellStyle(style);
					//第九列  注册资本
					rowCell = row.createCell(8);
					rowCell.setCellValue(coms.get(i).getRegistercapital());
					rowCell.setCellStyle(style);
					//第十列  邮政编码
					rowCell = row.createCell(9);
					rowCell.setCellValue(coms.get(i).getPostalcode());
					rowCell.setCellStyle(style);
					//第十一列 传真
					rowCell = row.createCell(10);
					rowCell.setCellValue(coms.get(i).getFax());
					rowCell.setCellStyle(style);
					//十二列  法人代表
					rowCell = row.createCell(11);
					rowCell.setCellValue(coms.get(i).getArtificialperson());
					rowCell.setCellStyle(style);
					//第十三列  法人电话
					rowCell = row.createCell(12);
					rowCell.setCellValue(coms.get(i).getAptel());
					rowCell.setCellStyle(style);
					//第十四列  联系人
					rowCell = row.createCell(13);
					rowCell.setCellValue(coms.get(i).getLinkman());
					rowCell.setCellStyle(style);
					//第十五列  联系人电话
					rowCell = row.createCell(14);
					rowCell.setCellValue(coms.get(i).getLtel());
					rowCell.setCellStyle(style);
					//第十六列  条码印刷技术负责人
					rowCell = row.createCell(15);
					rowCell.setCellValue(coms.get(i).getBcprincipal());
					rowCell.setCellStyle(style);
					//第十七列  条码印刷技术类型
					rowCell = row.createCell(16);
					if(coms.get(i).getFlat())Typ+="、平版胶印";
					if(coms.get(i).getGravure())Typ+="、凹版印刷";
					if(coms.get(i).getWebby())Typ+="、丝网印刷";
					if(coms.get(i).getFlexible())Typ+="、柔性版印刷";
					if(coms.get(i).getElsetype()!=""&&coms.get(i).getElsetype()!=null)Typ+="、"+coms.get(i).getElsetype();
//					Typ=Typ.substring(1, Typ.length());
					if(Typ.length()==0){
						rowCell.setCellValue("");
					}else{
						rowCell.setCellValue(Typ.substring(1, Typ.length()));
					}
					rowCell.setCellStyle(style);
					//第十八列  印刷载体材料
					rowCell = row.createCell(17);
					if(coms.get(i).getPapery())Mat+="、纸质";
					if(coms.get(i).getPastern())Mat+="、不干胶";
					if(coms.get(i).getFrilling())Mat+="、瓦楞纸";
					if(coms.get(i).getMetal())Mat+="、金属";
					if(coms.get(i).getPlastic()!=null)Mat+="、塑料";
					if(coms.get(i).getElsematerial()!=""&&coms.get(i).getElsematerial()!=null)Mat+="、"+coms.get(i).getElsematerial();
					if(Mat.length()==0){
						rowCell.setCellValue("");
					}else{
						rowCell.setCellValue(Mat.substring(1, Mat.length()));
					}
					rowCell.setCellStyle(style);
					//第十九列  备注
					rowCell = row.createCell(18);
					rowCell.setCellValue(coms.get(i).getRemarks());
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
}
