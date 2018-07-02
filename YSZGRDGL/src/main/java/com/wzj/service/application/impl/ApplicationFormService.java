package com.wzj.service.application.impl;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

import com.wzj.DTO.AppDTO;
import com.wzj.dao.ApplicationFormMapper;
import com.wzj.dao.PbtSampleMapper;
import com.wzj.pojo.Application;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.CompanyInfo;
import com.wzj.pojo.Log;
import com.wzj.pojo.PbtSample;
import com.wzj.pojo.UploadFile;
import com.wzj.service.application.IApplicationFormService;

@Service("applicationFormService")
public class ApplicationFormService implements IApplicationFormService {
	
	@Autowired
	private ApplicationFormMapper applicationFormMapper;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private PbtSampleMapper pbtSampleMapper;
	@Override
	public int insertApp(ApplicationForm app) {
		return applicationFormMapper.insert(app);
	}
	public List<ApplicationForm> getFRCount(String companyId){
		return applicationFormMapper.getFRByCId(companyId);
	}
	public ApplicationForm getAppFormById(int id){
		return applicationFormMapper.getAppFormById(id);
	}

	public ApplicationForm getAppFormByBussinessNo(String bussinessNo) {
		return applicationFormMapper.getAppFormByBussinessNo(bussinessNo);
	}

	public int updateApp(ApplicationForm app) {
		return  applicationFormMapper.updateByPrimaryKey(app);
	}

	public List<ApplicationForm> findBusinessByTasks(List<String> businessIds,String branchId) {
		 return applicationFormMapper.findByIds(businessIds,branchId);
	}
	
	//只能查询本中心的业务
	public List<ApplicationForm> findBusinessByTasks(Set<String> businessIds,String branchId) {
		List<String> list = new ArrayList<>();
		list.addAll(businessIds);//将set转化为list集合
		list.add("");//为了避免使用sql查询的时候in中有多余逗号
		 return applicationFormMapper.findByIds(list,branchId);
	}
	
	//查询所有的业务
	public List<ApplicationForm> findBusinessByTasks(Set<String> businessIds) {
		List<String> list = new ArrayList<>();
		list.addAll(businessIds);//将set转化为list集合
		list.add("");//为了避免使用sql查询的时候in中有多余逗号
		 return applicationFormMapper.findByIds2(list);
	}

	public List<ApplicationForm> getAppByStatus(int status,String branchId) {
		return applicationFormMapper.getAppByStatus(status,branchId);
	}

	public int deleteById(int id) {
		return applicationFormMapper.deleteByPrimaryKey(id);
	}

	public int updateStatus(int status, int id) {
		return applicationFormMapper.updateStatus(status,id);
	}

	public UploadFile getUploadFileByCode(String code) {
		return applicationFormMapper.getUploadFileByCode(code);
	}

	@Override
	public int updateDate(int id,Date date1, Date date2) {
		return applicationFormMapper.updateDate(id,date1,date2);
	}

	public List<ApplicationForm> getOngoing(AppDTO appdto) {
		return applicationFormMapper.getOngoing(appdto);
	}
	public List<ApplicationForm> getOngoingAll(AppDTO appdto) {
		return applicationFormMapper.getOngoingAll(appdto);
	}
	
	public List<ApplicationForm> getAll(){
		return applicationFormMapper.getAll();
	}
	
	public List<ApplicationForm> getByBranchId(String branchId){
		return applicationFormMapper.getByBranchId(branchId);
	}

	public int cancel(int id) {
		return applicationFormMapper.cancel(id);
	}
	
	public int activate(int id) {
		return applicationFormMapper.activate(id);
	}

	public ApplicationForm getLastApp() {
		return applicationFormMapper.getLastApp();
	}
	
	public boolean exportRecord(Boolean zhuxiao,String title, String filePath, String branchId) {
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
			cell.setCellValue("发证日期");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(5);
			cell.setCellValue("到期日期");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(6);
			cell.setCellValue("企业性质");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(7);
			cell.setCellValue("详细地址");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(8);
			cell.setCellValue("邮政编码");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(9);
			cell.setCellValue("证书编号");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(10);
			cell.setCellValue("营业执照号码");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(11);
			cell.setCellValue("注册资本");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(12);
			cell.setCellValue("法人代表");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(13);
			cell.setCellValue("法人职务");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(14);
			cell.setCellValue("传真");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(15);
			cell.setCellValue("法人电话");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(16);
			cell.setCellValue("联系人");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(17);
			cell.setCellValue("联系人职务");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(18);
			cell.setCellValue("联系人电话");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(19);
			cell.setCellValue("主营");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(20);
			cell.setCellValue("兼营");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(21);
			cell.setCellValue("职工人数");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(22);
			cell.setCellValue("技术人员数");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(23);
			cell.setCellValue("条码印刷技术负责人");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(24);
			cell.setCellValue("技术负责人职务");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(25);
			cell.setCellValue("技术负责人职称");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(26);
			cell.setCellValue("平板印刷");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(27);
			cell.setCellValue("凹版印刷");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(28);
			cell.setCellValue("丝网印刷");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(29);
			cell.setCellValue("柔性版印刷");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(30);
			cell.setCellValue("其他印刷");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(31);
			cell.setCellValue("纸质");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(32);
			cell.setCellValue("不干胶");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(33);
			cell.setCellValue("瓦楞纸");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(34);
			cell.setCellValue("金属");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(35);
			cell.setCellValue("塑料");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(36);
			cell.setCellValue("其他材料");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(37);
			cell.setCellValue("主要印刷设备");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(38);
			cell.setCellValue("条码检测设备");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(39);
			cell.setCellValue("注销");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(40);
			cell.setCellValue("注销时间");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(41);
			cell.setCellValue("备注");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(42);
			cell.setCellValue("创建日期");
			cell.setCellStyle(style);
			//-------------表头完------------
			//获取数据集合
			List<CompanyInfo> appFormList = new ArrayList<>();
			if(zhuxiao){
				appFormList = companyInfoService.getCancel(branchId);
			}else{
				appFormList = companyInfoService.getAll(branchId);
			}
			int index = 1;
			CompanyInfo record = new CompanyInfo();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//遍历数据，写入到excel中

			if(appFormList.size()>0){
				for(int i=0;i<appFormList.size();i++){
					index++;//行号++，从2开始
					record = appFormList.get(i);
					//生成一个新行
					XSSFRow row = sheet.createRow(index);
					XSSFCell rowCell = row.createCell(0);//第一列
					rowCell.setCellValue(i+1);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(1);//第二列
					rowCell.setCellValue("初次申请");
					rowCell.setCellStyle(style);
					rowCell = row.createCell(2);//第3列
					rowCell.setCellValue(record.getBranchName());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(3);//第4列
					rowCell.setCellValue(record.getEnterprisename());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(4);
					rowCell.setCellValue(record.getCertappdate() == null ? "" : format.format(record.getCertappdate()));
					rowCell.setCellStyle(style);
					rowCell = row.createCell(5);
					rowCell.setCellValue(record.getCerttodate() == null ? "" : format.format(record.getCerttodate()));
					rowCell.setCellStyle(style);
					
					rowCell = row.createCell(6);
					rowCell.setCellValue(record.getEnterprisekind());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(7);
					rowCell.setCellValue(record.getAddress());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(8);
					rowCell.setCellValue(record.getPostalcode());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(9);
					rowCell.setCellValue(record.getCertificateno());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(10);
					rowCell.setCellValue(record.getBusinessno());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(11);
					rowCell.setCellValue(record.getRegistercapital());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(12);
					rowCell.setCellValue(record.getArtificialperson());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(13);
					rowCell.setCellValue(record.getApjob());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(14);
					rowCell.setCellValue(record.getFax());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(15);
					rowCell.setCellValue(record.getAptel());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(16);
					rowCell.setCellValue(record.getLinkman());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(17);
					rowCell.setCellValue(record.getLjob());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(18);
					rowCell.setCellValue(record.getLtel());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(19);
					rowCell.setCellValue(record.getMainbusiness());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(20);
					rowCell.setCellValue(record.getRestbusiness());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(21);
					rowCell.setCellValue(record.getEmployeetotal() == null ? 0 : record.getEmployeetotal());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(22);
					rowCell.setCellValue(record.getTechniciantotal() == null ? 0 : record.getTechniciantotal());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(23);
					rowCell.setCellValue(record.getBcprincipal());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(24);
					rowCell.setCellValue(record.getTpbusiness());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(25);
					rowCell.setCellValue(record.getTpopost());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(26);
					rowCell.setCellValue(record.getFlat() == null ? "" : record.getFlat() == true ? "是" :"");
					rowCell.setCellStyle(style);
					rowCell = row.createCell(27);
					rowCell.setCellValue(record.getGravure() == null ? "" : record.getGravure() == true ? "是" :"");
					rowCell.setCellStyle(style);
					rowCell = row.createCell(28);
					rowCell.setCellValue(record.getWebby() == null ? "" : record.getWebby() == true ? "是" :"");
					rowCell.setCellStyle(style);
					rowCell = row.createCell(29);
					rowCell.setCellValue(record.getFlexible() == null ? "" : record.getFlexible() == true ? "是" :"");
					rowCell.setCellStyle(style);
					rowCell = row.createCell(30);
					rowCell.setCellValue(record.getElsetype());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(31);
					rowCell.setCellValue(record.getPapery() == null ? "" : record.getPapery() == true ? "是" :"");
					rowCell.setCellStyle(style);
					rowCell = row.createCell(32);
					rowCell.setCellValue(record.getPastern() == null ? "" : record.getPastern() == true ? "是" :"");
					rowCell.setCellStyle(style);
					rowCell = row.createCell(33);
					rowCell.setCellValue(record.getFrilling() == null ? "" : record.getFrilling() == true ? "是" :"");
					rowCell.setCellStyle(style);
					rowCell = row.createCell(34);
					rowCell.setCellValue(record.getMetal() == null ? "" : record.getMetal() == true ? "是" :"");
					rowCell.setCellStyle(style);
					rowCell = row.createCell(35);
					rowCell.setCellValue(record.getPlastic() == null ? "" : record.getPlastic() == true ? "是" :"");
					rowCell.setCellStyle(style);
					rowCell = row.createCell(36);
					rowCell.setCellValue(record.getElsematerial());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(37);
					rowCell.setCellValue(record.getPrintEquipment());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(38);
					rowCell.setCellValue(record.getTestEquipment());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(39);
					rowCell.setCellValue(record.getZhuxiao());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(40);
					rowCell.setCellValue(record.getZhuxiaodate() == null ? "" : format.format(record.getZhuxiaodate()));
					rowCell.setCellStyle(style);
					rowCell = row.createCell(41);
					rowCell.setCellValue(record.getRemarks());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(42);
					rowCell.setCellValue(record.getCreatedate() == null ? "" : format.format(record.getCreatedate()));
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

	public ApplicationForm getLastAppFormByBussinessNo(String businessno) {
		return applicationFormMapper.getLastAppByBussinessNo(businessno);
	}

	public ApplicationForm getMaxQualityNo() {
		return applicationFormMapper.getMaxQualityNo();
	}

	public ApplicationForm getLastAppBycid(int id) {
		return applicationFormMapper.getLastAppBycid(id);
	}

	public List<ApplicationForm> getAppBycid(int id) {
		return applicationFormMapper.getAppBycid(id);
	}

	public ApplicationForm getLastAppByBussinessNo(String bussinessNo) {
		return applicationFormMapper.getLastAppByBussinessNo(bussinessNo);
	}

	public PbtSample getSamByCode(String sampleCode) {
		return pbtSampleMapper.getSampleByCode(sampleCode);
	}

	public ApplicationForm getLastAppFormBytitleNo(String titleNo) {
		return applicationFormMapper.getLastAppFormBytitleNo(titleNo);
	}

	public int getCountByComName(String enterpriseName) {
		return applicationFormMapper.getCountByComName(enterpriseName);
	}
	
	public int getCountByComName2(String enterpriseName) {
		return applicationFormMapper.getCountByComName2(enterpriseName);
	}

	public UploadFile getUploadFile(String titleNo, String describe) {
		return applicationFormMapper.getUploadFile(titleNo,describe);
	}
	
	public ApplicationForm getAppFormByName(String name,String type) {
		return applicationFormMapper.getAppFormByName(name,type);
	}
	
	public ApplicationForm getAppFormByName(String name) {
		return applicationFormMapper.getByName(name);
	}

	public List<ApplicationForm> getOngoingByStatus(AppDTO appdto) {
		return applicationFormMapper.getOngoingByStatus(appdto);
	}

	public List<ApplicationForm> getOngoingByBranch(AppDTO appdto) {
		return applicationFormMapper.getOngoingByBranch(appdto);
	}
}
