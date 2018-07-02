package com.wzj.service;

import java.util.List;

import com.wzj.DTO.SysUserDTO;
import com.wzj.pojo.SysRole;
import com.wzj.pojo.SysUser;

public interface ISysUserService {
	public SysUser getByUserName(String username);
	public SysUser getByUserId(int id);
	public int insert(SysUser sysUser);
	public List<SysUser> findAllByUser(SysUser sysuser);
	public List<SysUser> findAll();
	public int deleteById(Integer id);
	public int update(SysUser sysUser);
	public SysUser findUser(String username, String pwd,int locked);
	public int setRoleId(Integer userId, String roleId);
	public int updateRoleId(int userId, String roleId); 
	public List<SysRole> findRoleByUserId(Integer userid);
	public boolean exportRecord(String name,String filePath,String dept,String ramusCenter);
	public int importExcel(String absolutePath);
	public int add(SysUser sysUser);
	public List<SysUser> getUsersByRoleName(String roleName);
	public int getSysUserCountByUsername(String usercode);
	public int audit(Integer id,Integer locked);
	public List<SysUser> getZjByBranchId(String branchId);
}
