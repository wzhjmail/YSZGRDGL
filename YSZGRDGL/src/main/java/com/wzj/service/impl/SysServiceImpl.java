package com.wzj.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.Customer;
import com.wzj.pojo.SysPermission;
import com.wzj.pojo.SysUser;
import com.wzj.service.ISysService;
import com.wzj.util.CommonUtil;
import com.wzj.exception.CustomException;

/**
 * @author Administrator
 * 认证过程：
 * 根据用户身份（账号）查询数据库，如果查询不到用户不存在
 * 对输入的密码 和数据库密码 进行比对，如果一致，认证通过
 */
@Service("sysServiceImpl")
public class SysServiceImpl implements ISysService{
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysPermissionService sysPermissionService;
	@Override
	public ActiveUser anthenticat(String username,String password) 
			throws Exception{
		Customer customer = this.findAdminByUsername(username);
		SysUser sysUser = this.findUserByUsername(username);
		/*if(customer == null){
			throw new CustomException("用户账号不存在");
		}
		String password_db = customer.getUserpassword();
		
		//对输入的密码 和数据库密码 进行比对，如果一致，认证通过
		//对页面输入的密码 进行md5加密 
		String password_input_md5 = CommonUtil.getInstance().bytesToMD5(password_db.getBytes());
		if(!password_input_md5.equalsIgnoreCase(password_db)){
			//抛出异常
			throw new CustomException("用户名或密码 错误");
		}
		
		//认证通过，返回用户身份信息
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(customer.getId());
		activeUser.setUsercode(customer.getUsercode().toString());
		activeUser.setUsername(username);*/
		if(sysUser == null){
			throw new CustomException("用户账号不存在");
		}
		String password_db = sysUser.getPassword();
		
		//对输入的密码 和数据库密码 进行比对，如果一致，认证通过
		//对页面输入的密码 进行md5加密 
		String password_input_md5 = CommonUtil.getInstance().bytesToMD5(password_db.getBytes());
		if(!password_input_md5.equalsIgnoreCase(password_db)){
			//抛出异常
			throw new CustomException("用户名或密码 错误");
		}
		
		//认证通过，返回用户身份信息
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(sysUser.getId());
		activeUser.setUsercode(sysUser.getUsercode().toString());
		activeUser.setUsername(username);
		activeUser.setRealname(sysUser.getUsername());
		return activeUser;
	}

	public Customer findAdminByUsername(String username) throws Exception {
		return customerService.getByUserName(username);
	}
	
	public SysUser findUserByUsername(String username) throws Exception{
		return sysUserService.getByUserName(username);
	}
	
	public SysUser findUserByUserId(int id) throws Exception{
		return sysUserService.getByUserId(id);
	}

	public List<SysPermission> getPermissionsByUserId(int userId) {
		return sysPermissionService.getPermissionsByUserId(userId);
	}

}
