package com.wzj.service;

import java.util.List;

import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.Customer;
import com.wzj.pojo.SysPermission;
import com.wzj.pojo.SysUser;


/**
 * @author Administrator
 * ��¼��֤�ӿ�
 *
 */
public interface ISysService {
	
	//�����û�����ݺ����� ������֤�������֤ͨ���������û������Ϣ
	public ActiveUser anthenticat(String username,String password) throws Exception;
	
	//�����û�����ѯ�û���Ϣ    ���ڹ���Ա��¼
	public Customer findAdminByUsername(String username)throws Exception;
	
	public SysUser findUserByUsername(String username)throws Exception;
	
	public SysUser findUserByUserId(int id)throws Exception;
	
	public List<SysPermission> getPermissionsByUserId(int userId)throws Exception;
	
	
}
