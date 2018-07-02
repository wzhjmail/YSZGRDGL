package com.wzj.service;

import java.util.List;

import com.wzj.pojo.SysPermission;

public interface ISysPermissionService {
	public List<SysPermission> getPermissionsByUserId(int userId); 
	public SysPermission findByName(String name);
}
