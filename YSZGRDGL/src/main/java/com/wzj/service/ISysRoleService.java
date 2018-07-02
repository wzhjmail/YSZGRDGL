package com.wzj.service;

import java.util.List;

import com.wzj.pojo.SysPermission;
import com.wzj.pojo.SysRole;
import com.wzj.pojo.SysRolePermission;
import com.wzj.pojo.SysUser;

public interface ISysRoleService {
	public int add(SysRole sysRole);
	public SysRole findByName(String name);
	public int addPermission(SysRolePermission srp);
	public List<SysRole> findAll();
	public int deleteById(int id); 
	public int deleteRoleInUserByRoleId(int id);
	public List<SysPermission> findPmsByRoleId(int id);
	public SysRole findRoleById(Integer id);
	public int update(SysRole sysRole);
	public void deleteAllPms(int id);
	public List<SysRole> findRoleByUserId(Integer userId);
	public SysRole findRoleByRoleId(Integer roleId);
	public List<SysUser> findUsersByRoleName(String name);
	public boolean exportRecord(String title, String filePath);
	public String findRole(Integer userId);
	public String findRoleIdByUserId(Integer userId);
	public List<SysRole> findRoleByRoleIds(SysRole sysRole);
}
