package com.wzj.service;

import java.util.List;

import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.Customer;
import com.wzj.pojo.SysPermission;
import com.wzj.pojo.SysUser;


/**
 * @author Administrator
 * 登录验证接口
 *
 */
public interface ISysService {
	
	//根据用户的身份和密码 进行认证，如果认证通过，返回用户身份信息
	public ActiveUser anthenticat(String username,String password) throws Exception;
	
	//根据用户名查询用户信息    用于管理员登录
	public Customer findAdminByUsername(String username)throws Exception;
	
	public SysUser findUserByUsername(String username)throws Exception;
	
	public SysUser findUserByUserId(int id)throws Exception;
	
	public List<SysPermission> getPermissionsByUserId(int userId)throws Exception;
	
	
}
