package com.wzj.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.Log;
import com.wzj.pojo.SysPermission;
import com.wzj.pojo.SysRole;
import com.wzj.pojo.SysUser;
import com.wzj.service.impl.BranchCenterService;
import com.wzj.service.impl.SysRoleService;
import com.wzj.service.impl.SysServiceImpl;
import com.wzj.util.AddLog;
import com.wzj.util.CommonUtil;

@Component
public class CustomRealm extends AuthorizingRealm{
	@Autowired
	private SysServiceImpl sysService;
	@Autowired
	private SysRoleService roleService;
	@Autowired
	private BranchCenterService branchCenterService;
	
	// 设置realm的名称
	/*@Override
	public void setName(String name) {
		super.setName("customRealm");
	}*/
	
	//realm的认证方法，从数据库查询用户信息
		@Override//授权方法
		protected AuthenticationInfo doGetAuthenticationInfo(
				AuthenticationToken token) throws AuthenticationException{
			System.out.println("用户认证");
			//token是用户输入的用户名和密码
			String username = (String) token.getPrincipal();
			
			/**根据不同的身份插叙不同的数据
			 * 从shiro的session中获取登录用户身份
			Subject currentUserType = SecurityUtils.getSubject();
			Session session1 = currentUserType.getSession();
            String userType = (String)session1.getAttribute("dept");
            System.out.println("用户身份为:"+userType);*/
            
			//char[] password=(char[])token.getCredentials();
			ActiveUser activeUser = new ActiveUser();
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
			
			
            /*if(userType.equals("企业用户")){
				
			}else if(userType.equals("分中心用户")){
				
			}else if(userType.equals("专家")){
				
			}else if(userType.equals("中心用户")){
				
			}
			Subject subject = SecurityUtils.getSubject();
			ActiveUser activeUser1 = (ActiveUser) subject.getPrincipal();
			if(username.equals(activeUser1.getUsercode()))
					Session.setAttribute("","");*/
			
			//从数据库中查询
			SysUser sysUser = null;
			try {
				sysUser = sysService.findUserByUsername(username);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(sysUser==null){
				return null;
			}
			String password = sysUser.getPassword();
			System.out.println("数据库中的password："+password);
			Subject subject = SecurityUtils.getSubject();
			Session session=(Session)subject.getSession();
			String validateCode=(String)session.getAttribute("validateCode");
			password=password+validateCode.toLowerCase();
			password=CommonUtil.getInstance().bytesToMD5(password.getBytes());
			session.setAttribute("validateCode","");
			//将用户相关信息存入activeUser对象
			activeUser.setUserid(sysUser.getId());
			activeUser.setUsercode(sysUser.getUsercode());
			activeUser.setUsername(username);
			activeUser.setDept(sysUser.getDept());
			activeUser.setRealname(sysUser.getUsername());
			activeUser.setRamusCenter(sysUser.getRamusCenter());
			String branchName=branchCenterService.getDivisionByCode(sysUser.getRamusCenter()).getDivisionname();
			activeUser.setBranchName(branchName);
			//进行防重放攻击的验证
			
			PrincipalCollection principals = new SimplePrincipalCollection(activeUser, this.getName());
			simpleAuthenticationInfo.setPrincipals(principals);
			simpleAuthenticationInfo.setCredentials(password);
			AddLog.addLog(branchName,username,Log.LOGIN,"登录到系统");
			return simpleAuthenticationInfo;
		}
       //用于授权
		@Override
		protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
			//从principles获取主身份信息
			//将getPrimaryPrincipal方法返回值转为真是身份类型（在上面的doGetAuthenticationInfo
			//认证通过填充到SimpleAuthentication中身份类型）
			ActiveUser activeUser = (ActiveUser)principals.getPrimaryPrincipal();
			List<SysPermission> permission = new ArrayList<SysPermission>();
			List<String> perm = new ArrayList<String>();
			
			try {
				//根据身份获取权限信息
				permission=sysService.getPermissionsByUserId(activeUser.getUserid());
				if(permission!=null){
					for(SysPermission per:permission){
						//perm.add(per.getPercode());
						perm.add(per.getName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//查到权限数据，返回授权信息(要包括上边的perm)
			SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
			//将上边查询到授权信息填充到simpleAuthorizationInfo对象中
			simpleAuthorizationInfo.addStringPermissions(perm);
			//添加角色
			List<SysRole> roles= roleService.findRoleByUserId(activeUser.getUserid());
			for(int i=0;i<roles.size();i++){
				String roleName = roles.get(i).getName();
				simpleAuthorizationInfo.addRole(roleName);
			}
			return simpleAuthorizationInfo;
		}
		
		//清除缓存
		public void clearCached() {
			PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
			super.clearCache(principals);
		}
		
}
