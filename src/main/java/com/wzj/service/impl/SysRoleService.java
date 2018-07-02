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
		//����excel����д������
		try{
			//����һ��������
			XSSFWorkbook workbook = new XSSFWorkbook();
			//���һ��sheet��sheet��
			XSSFSheet sheet = workbook.createSheet(title);
			//�ϲ���Ԫ�񣬵�һ�С����һ�С���һ�С����һ��
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
			//������һ��
			XSSFRow titleRow = sheet.createRow(0);
			//�������ⵥԪ���ʽ
			XSSFCellStyle titleStyle = workbook.createCellStyle();
			//����һ�����и�ʽ
			titleStyle.setAlignment(HorizontalAlignment.CENTER);
			//����һ������
			XSSFFont titlefont = workbook.createFont();
			titlefont.setFontHeight(16);//��������Ϊ16��
			//������Ӧ�õ���ǰ�ĸ�ʽ��
			titleStyle.setFont(titlefont);
			//�ڵ�һ���д���һ����Ԫ��
			XSSFCell titleCell = titleRow.createCell(0);
			//����ֵ����ʽ�ͱ���
			titleCell.setCellValue(title);
			titleCell.setCellStyle(titleStyle);
			//---------�����ǵ�һ��--------------
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.setColumnWidth(2,10 * 256);
			sheet.setColumnWidth(3,40 * 256);
			//�����������ĵ�Ԫ���ʽ
			XSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			//���õڶ��б�ͷ
			XSSFRow rowHeader = sheet.createRow(1);
			XSSFCell cell = rowHeader.createCell(0);
			cell.setCellValue("���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);
			cell.setCellValue("���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);
			cell.setCellValue("��ɫ��");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);
			cell.setCellValue("Ȩ��");
			cell.setCellStyle(style);
			//------------��ͷ��---------------
			//��ȡ���ݼ���
			List<SysRole> roleList = sysRoleMapper.findAll();
			int index = 1;
			SysRole record = new SysRole();
			//�������ݼ��ϣ�д�뵽excel��
			if(roleList.size()>0){
				for(int i=0;i<roleList.size();i++){
					index++;//�к�++����2��ʼ
					record = roleList.get(i);
					//����һ������
					XSSFRow row = sheet.createRow(index);
					XSSFCell rowCell = row.createCell(0);//��һ��
					rowCell.setCellValue(i+1);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(1);//��2��
					rowCell.setCellValue(record.getId());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(2);//��3��
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
					rowCell = row.createCell(3);//��4��
					rowCell.setCellValue(result);
					rowCell.setCellStyle(style);
				}
			}
			//���ļ����浽ָ����λ��
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
