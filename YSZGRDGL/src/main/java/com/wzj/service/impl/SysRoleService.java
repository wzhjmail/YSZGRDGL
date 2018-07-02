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

import com.wzj.dao.SysRoleMapper;
import com.wzj.pojo.SysPermission;
import com.wzj.pojo.SysRole;
import com.wzj.pojo.SysRolePermission;
import com.wzj.pojo.SysUser;
import com.wzj.service.ISysRoleService;

@Service("sysRoleService")
public class SysRoleService implements ISysRoleService{
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Override
	public int add(SysRole sysRole) {
		return sysRoleMapper.insert(sysRole);
	}

	public SysRole findByName(String name) {
		return sysRoleMapper.findByName(name);
	}
	
	public int addPermission(SysRolePermission srp){
		return sysRoleMapper.addPermission(srp);
	}

	public List<SysRole> findAll() {
		return sysRoleMapper.findAll();
	}

	public int deleteById(int id) {
		return sysRoleMapper.deleteByPrimaryKey(id);
	}

	public List<SysPermission> findPmsByRoleId(int id) {
		return sysRoleMapper.findPmsByRoleId(id);
	}

	public SysRole findRoleById(Integer id) {
		return sysRoleMapper.selectByPrimaryKey(id);
	}

	public int update(SysRole sysRole) {
		return sysRoleMapper.updateByPrimaryKey(sysRole);
	}

	public void deleteAllPms(int id) {
		sysRoleMapper.deleteAllPms(id);
	}

	public List<SysRole> findRoleByUserId(Integer userId) {
		return sysRoleMapper.findRoleByUserId(userId);
	}

	public int deleteRoleInUserByRoleId(int id) {
		return sysRoleMapper.deleteRoleInUserByRoleId(id);
	}
	
	public boolean exportRecord(String title, String filePath) {
		//创建excel，并写入数据
		try{
			//创建一个工作薄
			XSSFWorkbook workbook = new XSSFWorkbook();
			//添加一个sheet，sheet名
			XSSFSheet sheet = workbook.createSheet(title);
			//合并单元格，第一行、最后一行、第一列、最后一列
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
			//创建第一行
			XSSFRow titleRow = sheet.createRow(0);
			//创建标题单元格格式
			XSSFCellStyle titleStyle = workbook.createCellStyle();
			//创建一个居中格式
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			//创建一个字体
			XSSFFont titlefont = workbook.createFont();
			titlefont.setFontHeight(16);//标题字体为16号
			//将字体应用到当前的格式中
			titleStyle.setFont(titlefont);
			//在第一行中创建一个单元格
			XSSFCell titleCell = titleRow.createCell(0);
			//设置值、样式和标题
			titleCell.setCellValue(title);
			titleCell.setCellStyle(titleStyle);
			//---------以上是第一行--------------
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.setColumnWidth(2,10 * 256);
			sheet.setColumnWidth(3,40 * 256);
			//设置其他正文单元格格式
			XSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//设置第二行表头
			XSSFRow rowHeader = sheet.createRow(1);
			XSSFCell cell = rowHeader.createCell(0);
			cell.setCellValue("序号");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);
			cell.setCellValue("编号");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);
			cell.setCellValue("角色名");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);
			cell.setCellValue("权限");
			cell.setCellStyle(style);
			//------------表头完---------------
			//获取数据集合
			List<SysRole> roleList = sysRoleMapper.findAll();
			int index = 1;
			SysRole record = new SysRole();
			//遍历数据集合，写入到excel中
			if(roleList.size()>0){
				for(int i=0;i<roleList.size();i++){
					index++;//行号++，从2开始
					record = roleList.get(i);
					//生成一个新行
					XSSFRow row = sheet.createRow(index);
					XSSFCell rowCell = row.createCell(0);//第一列
					rowCell.setCellValue(i+1);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(1);//第2列
					rowCell.setCellValue(record.getId());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(2);//第3列
					rowCell.setCellValue(record.getName());
					rowCell.setCellStyle(style);
					List<SysPermission> pms = findPmsByRoleId(Integer.parseInt(record.getId()));
					String result="";
					if(pms.size()>0){
						int j=0;
						for(;j<pms.size()-1;j++){
							result+=pms.get(j).getName()+",";
						}
						result+=pms.get(j).getName();
					}
					rowCell = row.createCell(3);//第4列
					rowCell.setCellValue(result);
					rowCell.setCellStyle(style);
				}
			}
			//将文件保存到指定的位置
			FileOutputStream out = new FileOutputStream(filePath);
			workbook.write(out);
			workbook.close();
			out.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public SysRole findRoleByRoleId(Integer roleId) {
		return sysRoleMapper.findRoleByRoleId(roleId);
	}

	@Override
	public List<SysUser> findUsersByRoleName(String name) {
		SysRole sysRole = findByName(name);
		String sysRoleId = sysRole.getId();
		return sysRoleMapper.findUsersByRoleId("%"+sysRoleId+"%");
	}

	public String findRole(Integer userId) {
		return sysRoleMapper.findRole(userId);
	}

	public String findRoleIdByUserId(Integer userId) {
		return sysRoleMapper.findRoleIdByUserId(userId);
	}

	public List<SysRole> findRoleByRoleIds(SysRole sysrole) {
		return sysRoleMapper.findRoleByRoleIds(sysrole);
	}

}
