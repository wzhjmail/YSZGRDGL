package com.wzj.service.impl;

import java.io.FileOutputStream;
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

import com.wzj.dao.SysPermissionMapper;
import com.wzj.pojo.SysPermission;
import com.wzj.pojo.SysUser;
import com.wzj.service.IPermissionService;

@Service("permissionService")
public class PermissionService implements IPermissionService{
	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	
	@Override
	public List<SysPermission> findAll() {
		
		return sysPermissionMapper.findAll();
	}

	public int update(SysPermission pms) {
		return sysPermissionMapper.updateByPrimaryKey(pms);
	}

	public int add(SysPermission pms) {
		return sysPermissionMapper.insert(pms);
	}

	public int deleteById(int id) {
		return sysPermissionMapper.deleteByPrimaryKey(id);
	}

	public int deleteRolePmsById(int id) {
		return sysPermissionMapper.deleteRolePmsById(id);
	}

	public List<Integer> findIds() {
		return sysPermissionMapper.findIds();
	}

	public boolean exportRecord(String title, String filePath) {
		//创建excel，并写入数据
				try{
					//创建一个工作薄
					XSSFWorkbook workbook = new XSSFWorkbook();
					//添加一个sheet,sheet名
					XSSFSheet sheet = workbook.createSheet(title);
					//合并单元格，参数含义：第一行、最后一行、第一列、最后一列
					//id,usercode,username,rolename,password
					sheet.addMergedRegion(new CellRangeAddress(0,0,0,9));
					//创建第一行
					XSSFRow titleRow = sheet.createRow(0);
					//创建标题单元格格式
					XSSFCellStyle titleStyle = workbook.createCellStyle();
					//创建一个居中格式
					titleStyle.setAlignment(HorizontalAlignment.CENTER);
					//创建一个字体
					XSSFFont titleFont = workbook.createFont();
					titleFont.setFontHeight(16);//标题字体为16号
					//将字体应用到当前的格式中
					titleStyle.setFont(titleFont);
					//在第一行中创建一个单元格
					XSSFCell titleCell = titleRow.createCell(0);
					//设置值、样式和标题
					titleCell.setCellValue(title);
					titleCell.setCellStyle(titleStyle);
					//--------------以上为第一行------------------
					//在合适位置调整自适应
					for(int i=0;i<10;i++)
						sheet.autoSizeColumn(i);
					sheet.setColumnWidth(3,15*256);
					sheet.setColumnWidth(4,30*256);
					sheet.setColumnWidth(5,15*256);
					//设置其他正文单元格格式
					XSSFCellStyle style = workbook.createCellStyle();
					style.setAlignment(HorizontalAlignment.CENTER);//设置文字居中
					//设置第二行表头
					XSSFRow rowHeader = sheet.createRow(1);
					XSSFCell cell = rowHeader.createCell(0);//第1列
					cell.setCellValue("序号");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(1);//第2列
					cell.setCellValue("id");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(2);//第3列
					cell.setCellValue("name");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(3);//第4列
					cell.setCellValue("type");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(4);//第5列
					cell.setCellValue("url");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(5);//第6列
					cell.setCellValue("percode");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(6);//第6列
					cell.setCellValue("parentid");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(7);//第6列
					cell.setCellValue("parentids");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(8);//第6列
					cell.setCellValue("sortstring");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(9);//第6列
					cell.setCellValue("available");
					cell.setCellStyle(style);
					//表头完----------------------------------------
					//获取数据集合
					List<SysPermission> pms = findAll();
					int index = 1;//行序号，应从第三行开始，每次循环进行++
					SysPermission record = new SysPermission();
					//遍历数据集合，写入到excel中
					if(pms.size()>0){
						for(int i=0;i<pms.size();i++){
							index++;//行号++，从2开始
							record = pms.get(i);
							//生成一个新行
							XSSFRow row = sheet.createRow(index);
							XSSFCell rowCell = row.createCell(0);//第1列
							rowCell.setCellValue(i+1);
							rowCell.setCellStyle(style);
							rowCell = row.createCell(1);//第二列
							rowCell.setCellValue(record.getId());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(2);//第三列
							rowCell.setCellValue(record.getName());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(3);//第四列
							rowCell.setCellValue(record.getType());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(4);//第五列
							rowCell.setCellValue(record.getUrl());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(5);//第六列
							rowCell.setCellValue(record.getPercode());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(6);//第7列
							rowCell.setCellValue(record.getParentid());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(7);//第8列
							rowCell.setCellValue(record.getParentids());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(8);//第9列
							rowCell.setCellValue(record.getSortstring());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(9);//第10列
							rowCell.setCellValue(record.getAvailable());
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

	public SysPermission getById(int id) {
		return sysPermissionMapper.getById(id);
	}

	@Override
	public int deleteById(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void deleteRolePerByRoleId(int id) {
		sysPermissionMapper.deleteRolePerByRoleId(id);
	}

	public int updateAvailabie(int id) {
		return sysPermissionMapper.updateAvailabie(id);
	}

}
