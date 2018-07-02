package com.wzj.service.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
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
import org.springframework.transaction.annotation.Transactional;

import com.wzj.dao.SysRoleMapper;
import com.wzj.dao.SysUserMapper;
import com.wzj.pojo.Stu;
import com.wzj.pojo.SysRole;
import com.wzj.pojo.SysUser;
import com.wzj.service.ISysUserService;
import com.wzj.util.DynamicDataSource;

@Service("sysUserService")
public class SysUserService implements ISysUserService{
	
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Override
	public SysUser getByUserName(String username) {
		return sysUserMapper.selectByUserName(username);
	}

	@Override
	public SysUser getByUserId(int id) {
		return sysUserMapper.getUserById(id);
	}

	public int insert(SysUser sysUser) {
		return sysUserMapper.add(sysUser);
	}

	public List<SysUser> findAllByUser(SysUser sysuser) {
		return sysUserMapper.findAllByUser(sysuser);
	}
	public List<SysUser> findAllFZ(SysUser sysUser) {
		return sysUserMapper.findAllFZ(sysUser);
	}
	public List<SysUser> findLikeDivisionCode(String divisionCode) {
		return sysUserMapper.findFZLikeDivisionCode(divisionCode);
	}
	@Override
	public List<SysUser> findAll() {
		return sysUserMapper.findAll();
	}
	@Override
	public int deleteById(Integer id) {
		return sysUserMapper.deleteById(id);
	}

	public int update(SysUser sysUser) {
		return sysUserMapper.update(sysUser);
	}

	public SysUser findUser(String username, String pwd,int locked) {
		return sysUserMapper.findUser(username,pwd,locked);
	}

	public int setRoleId(Integer userId, String roleId) {
		return sysUserMapper.setRoleId(userId,roleId);
	}

	public int updateRoleId(int userId, String roleId) {
		return sysUserMapper.updateRoleId(userId,roleId);
	}

	public int deleteUserRole(Integer id) {
		return sysUserMapper.deleteUserRole(id);
	}

	public List<SysRole> findRoleByUserId(Integer userid) {
		return sysUserMapper.findRoleByUserId(userid);
	}

	public boolean exportRecord(String title,String filePath,String dept,String ramusCenter) {
		//����excel����д������
		try{
			//����һ��������
			XSSFWorkbook workbook = new XSSFWorkbook();
			//���һ��sheet,sheet��
			XSSFSheet sheet = workbook.createSheet(title);
			//�ϲ���Ԫ�񣬲������壺��һ�С����һ�С���һ�С����һ��
			//id,usercode,username,rolename,password
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
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
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			//�����������ĵ�Ԫ���ʽ
			XSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);//�������־���
			//���õڶ��б�ͷ
			XSSFRow rowHeader = sheet.createRow(1);
			XSSFCell cell = rowHeader.createCell(0);//��1��
			cell.setCellValue("���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);//��2��
			cell.setCellValue("���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);//��3��
			cell.setCellValue("�û���");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);//��4��
			cell.setCellValue("��ʵ����");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(4);//��5��
			cell.setCellValue("��ɫ��");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(5);//��6��
			cell.setCellValue("����");
			cell.setCellStyle(style);
			//��ͷ��----------------------------------------
			//��ȡ���ݼ���
			List<SysUser> userList = new ArrayList<SysUser>();
			SysUser sysuser=new SysUser();
			sysuser.setDept(dept);
			sysuser.setRamusCenter(ramusCenter);
			if(ramusCenter.equals("0000")&&dept.equals("�������û�")){//���������֧�����������û������ѯ����
				userList=findAllFZ(sysuser);
			}else{//���������֧�����Ƿ�֧�������򷵻أ�
				userList=findAllByUser(sysuser);
			}
			int index = 1;//����ţ�Ӧ�ӵ����п�ʼ��ÿ��ѭ������++
			SysUser record = new SysUser();
			//�������ݼ��ϣ�д�뵽excel��
			if(userList.size()>0){
				for(int i=0;i<userList.size();i++){
					index++;//�к�++����2��ʼ
					record = userList.get(i);
					//����һ������
					XSSFRow row = sheet.createRow(index);
					XSSFCell rowCell = row.createCell(0);//��1��
					rowCell.setCellValue(i+1);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(1);//�ڶ���
					rowCell.setCellValue(record.getId());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(2);//������
					rowCell.setCellValue(record.getUsercode());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(3);//������
					rowCell.setCellValue(record.getUsername());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(4);//������
					List<SysRole> roles=findRoleByUserId(record.getId());
					String ro="";
					for(SysRole role:roles){
						ro=ro+role.getName()+",";
					}
					if(!("".equals(ro)))
						ro=ro.substring(0,ro.length()-1);
					rowCell.setCellValue(ro);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(5);//������
					rowCell.setCellValue(record.getPassword());
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
	
	@Transactional
	public int importExcel(String fileName) {
		//�ļ���
		int flag=0;
		InputStream inputStream;
		try {
			inputStream =new FileInputStream(fileName);
			//����XSSFWorkbook����
			XSSFWorkbook xssFWorkbook=new XSSFWorkbook(inputStream);
			//��ȡ��������һ��sheet������ݣ��涨��һ��sheet����Ϊ�û���Ϣ
			XSSFSheet sheet=xssFWorkbook.getSheetAt(0);
			//�����ж���
			XSSFRow row;
			//��ȡ���һ�е�ֵ
			int rowCount=sheet.getLastRowNum();
			//��ȡÿһ���ж����У��Եڶ��е�������׼
			int cellCount=sheet.getRow(1).getPhysicalNumberOfCells();
			//ÿһ��ΪFillblankDTO����
			SysUser user=new SysUser();
			//ѭ������ÿһ�У��ӵ����п�ʼ��Ϊ���ݣ�����i��2��ʼ
			for(int i=2;i<=rowCount;i++){
				//��ȡÿһ��
				row=sheet.getRow(i);
				//����һ����Ԫ�����
				XSSFCell cell=null;
				//��Ԫ������ֵ
				String cellValue=null;
				//��Ԫ��ֵ������
				int cellType;
				//����һ�����һ�����ݵ��ַ���
				String[] s=new String[5];
				//��ÿһ�н��б������ӵڶ��п�ʼ����
				for(int j=1;j<cellCount;j++){
					//��ȡÿһ�еĵ�Ԫ��
					cell=row.getCell(j);
					cellType=cell.getCellType();
					switch(cellType){
					case Cell.CELL_TYPE_STRING://�ı�����
						cellValue=cell.getStringCellValue();
					break;
					case Cell.CELL_TYPE_NUMERIC://��������ֵĻ�����ҲҪת��Ϊ�ı�����
						cellValue=String.valueOf((int)cell.getNumericCellValue());
						break;
					}
					//�����ݴ�ŵ��ַ���������
					s[j-1]=cellValue;
				}
				//����ȡ����һ�����ݵ�ֵת��ΪSysUser����
				user.setId(Integer.parseInt(s[0]));
				user.setUsercode(s[1]);
				user.setUsername(s[2]);
				user.setRole(s[3]);
				user.setPassword(s[4]);
				//���ñ������ݿ�ķ���
				flag=add(user);
				//���û�б���ɹ�����0��ֱ���˳�
				if(flag==0){
					return flag;
				}
			}
			xssFWorkbook.close();//�ر�
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
	}

	public int add(SysUser sysUser) {
		int result=sysUserMapper.add(sysUser);
		String roleId = sysRoleMapper.findByName(sysUser.getRole()).getId();
		//ά���û��ͽ�ɫ�Ĺ�ϵ
		setRoleId(sysUser.getId(), roleId);
		return result;
	}

	@Override
	public List<SysUser> getUsersByRoleName(String roleName) {
		List<SysUser> users = new ArrayList<SysUser>();
		SysRole role = sysRoleMapper.findByName(roleName);
		users = sysUserMapper.findUsersByRoleId(Integer.parseInt(role.getId()));
		return users;
	}
	
	public int getSysUserCountByUsername(String usercode) {
		return sysUserMapper.getSysUserCountByUsername(usercode);
	}

	public int audit(Integer id, Integer locked) {
		return sysUserMapper.audit(id,locked);
	}

	public String test() {
		DynamicDataSource.setCustomerType(DynamicDataSource.DATASOURCETWO);
		return sysUserMapper.test();
	}

	public List<SysUser> getZjByBranchId(String branchId) {
		return sysUserMapper.getZjByBranchId(branchId);
	}

	public SysUser findByCid(Integer cid) {
		return sysUserMapper.findByCid(cid);
	}

	public List<SysUser> getByUserIds(String ids) {
		return sysUserMapper.getByUserIds(ids);
	}

	public SysUser getByUserCode(String usercode) {
		return sysUserMapper.getByUserCode(usercode);
	}

	public List<SysUser> getZJUserByIds(String result) {
		return sysUserMapper.getZJUserByIds(result);
	}

	public List<SysUser> findAllByBranchId(String ramusCenter) {
		return sysUserMapper.findAllByBranchId(ramusCenter);
	}

	public int getSysUserCount(int userId, String userCode) {
		return sysUserMapper.getSysUserCount(userId,userCode);
	}

	public List<SysUser> findLike(String dept, String code, String sSearch) {
		return sysUserMapper.findLike(dept,code,"%"+sSearch+"%");
	}

	public int testFile(byte[] fbate) {
		DynamicDataSource.setCustomerType(DynamicDataSource.DATASOURCETWO);
		return sysUserMapper.testFile(fbate);
	}

	public Blob getFile() {
		DynamicDataSource.setCustomerType(DynamicDataSource.DATASOURCETWO);
		return sysUserMapper.getFile();
	}
	public Stu getName() {
		DynamicDataSource.setCustomerType(DynamicDataSource.DATASOURCETWO);
		return sysUserMapper.getName();
	}

}
