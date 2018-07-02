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
		//创建excel，并写入数据
		try{
			//创建一个工作薄
			XSSFWorkbook workbook = new XSSFWorkbook();
			//添加一个sheet,sheet名
			XSSFSheet sheet = workbook.createSheet(title);
			//合并单元格，参数含义：第一行、最后一行、第一列、最后一列
			//id,usercode,username,rolename,password
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
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
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			//设置其他正文单元格格式
			XSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);//设置文字居中
			//设置第二行表头
			XSSFRow rowHeader = sheet.createRow(1);
			XSSFCell cell = rowHeader.createCell(0);//第1列
			cell.setCellValue("序号");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(1);//第2列
			cell.setCellValue("编号");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(2);//第3列
			cell.setCellValue("用户名");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(3);//第4列
			cell.setCellValue("真实姓名");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(4);//第5列
			cell.setCellValue("角色名");
			cell.setCellStyle(style);
			cell = rowHeader.createCell(5);//第6列
			cell.setCellValue("密码");
			cell.setCellStyle(style);
			//表头完----------------------------------------
			//获取数据集合
			List<SysUser> userList = new ArrayList<SysUser>();
			SysUser sysuser=new SysUser();
			sysuser.setDept(dept);
			sysuser.setRamusCenter(ramusCenter);
			if(ramusCenter.equals("0000")&&dept.equals("分中心用户")){//如果所属分支机构是中心用户，则查询所有
				userList=findAllFZ(sysuser);
			}else{//如果所属分支机构是分支机构，则返回：
				userList=findAllByUser(sysuser);
			}
			int index = 1;//行序号，应从第三行开始，每次循环进行++
			SysUser record = new SysUser();
			//遍历数据集合，写入到excel中
			if(userList.size()>0){
				for(int i=0;i<userList.size();i++){
					index++;//行号++，从2开始
					record = userList.get(i);
					//生成一个新行
					XSSFRow row = sheet.createRow(index);
					XSSFCell rowCell = row.createCell(0);//第1列
					rowCell.setCellValue(i+1);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(1);//第二列
					rowCell.setCellValue(record.getId());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(2);//第三列
					rowCell.setCellValue(record.getUsercode());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(3);//第四列
					rowCell.setCellValue(record.getUsername());
					rowCell.setCellStyle(style);
					rowCell = row.createCell(4);//第五列
					List<SysRole> roles=findRoleByUserId(record.getId());
					String ro="";
					for(SysRole role:roles){
						ro=ro+role.getName()+",";
					}
					if(!("".equals(ro)))
						ro=ro.substring(0,ro.length()-1);
					rowCell.setCellValue(ro);
					rowCell.setCellStyle(style);
					rowCell = row.createCell(5);//第六列
					rowCell.setCellValue(record.getPassword());
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
	
	@Transactional
	public int importExcel(String fileName) {
		//文件流
		int flag=0;
		InputStream inputStream;
		try {
			inputStream =new FileInputStream(fileName);
			//创建XSSFWorkbook对象
			XSSFWorkbook xssFWorkbook=new XSSFWorkbook(inputStream);
			//读取工作薄第一个sheet里的内容，规定第一个sheet里面为用户信息
			XSSFSheet sheet=xssFWorkbook.getSheetAt(0);
			//定义行对象
			XSSFRow row;
			//获取最后一行的值
			int rowCount=sheet.getLastRowNum();
			//获取每一行有多少列，以第二行的列数基准
			int cellCount=sheet.getRow(1).getPhysicalNumberOfCells();
			//每一行为FillblankDTO对象
			SysUser user=new SysUser();
			//循环遍历每一行，从第三行开始才为数据，所以i从2开始
			for(int i=2;i<=rowCount;i++){
				//获取每一行
				row=sheet.getRow(i);
				//定义一个单元格变量
				XSSFCell cell=null;
				//单元格对象的值
				String cellValue=null;
				//单元格值的类型
				int cellType;
				//定义一个存放一行数据的字符串
				String[] s=new String[5];
				//对每一行进行遍历，从第二列开始遍历
				for(int j=1;j<cellCount;j++){
					//获取每一行的单元格
					cell=row.getCell(j);
					cellType=cell.getCellType();
					switch(cellType){
					case Cell.CELL_TYPE_STRING://文本类型
						cellValue=cell.getStringCellValue();
					break;
					case Cell.CELL_TYPE_NUMERIC://如果是数字的话首先也要转换为文本类型
						cellValue=String.valueOf((int)cell.getNumericCellValue());
						break;
					}
					//将内容存放到字符串数组内
					s[j-1]=cellValue;
				}
				//将获取到的一行数据的值转换为SysUser对象
				user.setId(Integer.parseInt(s[0]));
				user.setUsercode(s[1]);
				user.setUsername(s[2]);
				user.setRole(s[3]);
				user.setPassword(s[4]);
				//调用保存数据库的方法
				flag=add(user);
				//如果没有保存成功返回0则直接退出
				if(flag==0){
					return flag;
				}
			}
			xssFWorkbook.close();//关闭
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
	}

	public int add(SysUser sysUser) {
		int result=sysUserMapper.add(sysUser);
		String roleId = sysRoleMapper.findByName(sysUser.getRole()).getId();
		//维护用户和角色的关系
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
