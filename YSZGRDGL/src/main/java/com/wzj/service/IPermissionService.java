package com.wzj.service;

import java.util.List;

import com.wzj.pojo.SysPermission;

public interface IPermissionService {
	public List<SysPermission> findAll();
	public int update(SysPermission pms); 
	public int add(SysPermission pms); 
	public int deleteById(long id);
	public int deleteRolePmsById(int id); 
	public List<Integer> findIds();
	public boolean exportRecord(String title, String filePath);
}
