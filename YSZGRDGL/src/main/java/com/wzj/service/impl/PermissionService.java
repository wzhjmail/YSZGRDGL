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
		//����excel����д������
				try{
					//����һ��������
					XSSFWorkbook workbook = new XSSFWorkbook();
					//���һ��sheet,sheet��
					XSSFSheet sheet = workbook.createSheet(title);
					//�ϲ���Ԫ�񣬲������壺��һ�С����һ�С���һ�С����һ��
					//id,usercode,username,rolename,password
					sheet.addMergedRegion(new CellRangeAddress(0,0,0,9));
					//������һ��
					XSSFRow titleRow = sheet.createRow(0);
					//�������ⵥԪ���ʽ
					XSSFCellStyle titleStyle = workbook.createCellStyle();
					//����һ�����и�ʽ
					titleStyle.setAlignment(HorizontalAlignment.CENTER);
					//����һ������
					XSSFFont titleFont = workbook.createFont();
					titleFont.setFontHeight(16);//��������Ϊ16��
					//������Ӧ�õ���ǰ�ĸ�ʽ��
					titleStyle.setFont(titleFont);
					//�ڵ�һ���д���һ����Ԫ��
					XSSFCell titleCell = titleRow.createCell(0);
					//����ֵ����ʽ�ͱ���
					titleCell.setCellValue(title);
					titleCell.setCellStyle(titleStyle);
					//--------------����Ϊ��һ��------------------
					//�ں���λ�õ�������Ӧ
					for(int i=0;i<10;i++)
						sheet.autoSizeColumn(i);
					sheet.setColumnWidth(3,15*256);
					sheet.setColumnWidth(4,30*256);
					sheet.setColumnWidth(5,15*256);
					//�����������ĵ�Ԫ���ʽ
					XSSFCellStyle style = workbook.createCellStyle();
					style.setAlignment(HorizontalAlignment.CENTER);//�������־���
					//���õڶ��б�ͷ
					XSSFRow rowHeader = sheet.createRow(1);
					XSSFCell cell = rowHeader.createCell(0);//��1��
					cell.setCellValue("���");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(1);//��2��
					cell.setCellValue("id");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(2);//��3��
					cell.setCellValue("name");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(3);//��4��
					cell.setCellValue("type");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(4);//��5��
					cell.setCellValue("url");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(5);//��6��
					cell.setCellValue("percode");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(6);//��6��
					cell.setCellValue("parentid");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(7);//��6��
					cell.setCellValue("parentids");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(8);//��6��
					cell.setCellValue("sortstring");
					cell.setCellStyle(style);
					cell = rowHeader.createCell(9);//��6��
					cell.setCellValue("available");
					cell.setCellStyle(style);
					//��ͷ��----------------------------------------
					//��ȡ���ݼ���
					List<SysPermission> pms = findAll();
					int index = 1;//����ţ�Ӧ�ӵ����п�ʼ��ÿ��ѭ������++
					SysPermission record = new SysPermission();
					//�������ݼ��ϣ�д�뵽excel��
					if(pms.size()>0){
						for(int i=0;i<pms.size();i++){
							index++;//�к�++����2��ʼ
							record = pms.get(i);
							//����һ������
							XSSFRow row = sheet.createRow(index);
							XSSFCell rowCell = row.createCell(0);//��1��
							rowCell.setCellValue(i+1);
							rowCell.setCellStyle(style);
							rowCell = row.createCell(1);//�ڶ���
							rowCell.setCellValue(record.getId());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(2);//������
							rowCell.setCellValue(record.getName());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(3);//������
							rowCell.setCellValue(record.getType());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(4);//������
							rowCell.setCellValue(record.getUrl());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(5);//������
							rowCell.setCellValue(record.getPercode());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(6);//��7��
							rowCell.setCellValue(record.getParentid());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(7);//��8��
							rowCell.setCellValue(record.getParentids());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(8);//��9��
							rowCell.setCellValue(record.getSortstring());
							rowCell.setCellStyle(style);
							rowCell = row.createCell(9);//��10��
							rowCell.setCellValue(record.getAvailable());
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
