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
	
	// ����realm������
	/*@Override
	public void setName(String name) {
		super.setName("customRealm");
	}*/
	
	//realm����֤�����������ݿ��ѯ�û���Ϣ
		@Override//��Ȩ����
		protected AuthenticationInfo doGetAuthenticationInfo(
				AuthenticationToken token) throws AuthenticationException{
			System.out.println("�û���֤");
			//token���û�������û���������
			String username = (String) token.getPrincipal();
			
			/**���ݲ�ͬ����ݲ���ͬ������
			 * ��shiro��session�л�ȡ��¼�û����
			Subject currentUserType = SecurityUtils.getSubject();
			Session session1 = currentUserType.getSession();
            String userType = (String)session1.getAttribute("dept");
            System.out.println("�û����Ϊ:"+userType);*/
            
			//char[] password=(char[])token.getCredentials();
			ActiveUser activeUser = new ActiveUser();
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
			
			
            /*if(userType.equals("��ҵ�û�")){
				
			}else if(userType.equals("�������û�")){
				
			}else if(userType.equals("ר��")){
				
			}else if(userType.equals("�����û�")){
				
			}
			Subject subject = SecurityUtils.getSubject();
			ActiveUser activeUser1 = (ActiveUser) subject.getPrincipal();
			if(username.equals(activeUser1.getUsercode()))
					Session.setAttribute("","");*/
			
			//�����ݿ��в�ѯ
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
			System.out.println("���ݿ��е�password��"+password);
			Subject subject = SecurityUtils.getSubject();
			Session session=(Session)subject.getSession();
			String validateCode=(String)session.getAttribute("validateCode");
			password=password+validateCode.toLowerCase();
			password=CommonUtil.getInstance().bytesToMD5(password.getBytes());
			session.setAttribute("validateCode","");
			//���û������Ϣ����activeUser����
			activeUser.setUserid(sysUser.getId());
			activeUser.setUsercode(sysUser.getUsercode());
			activeUser.setUsername(username);
			activeUser.setDept(sysUser.getDept());
			activeUser.setRealname(sysUser.getUsername());
			activeUser.setRamusCenter(sysUser.getRamusCenter());
			String branchName=branchCenterService.getDivisionByCode(sysUser.getRamusCenter()).getDivisionname();
			activeUser.setBranchName(branchName);
			//���з��طŹ�������֤
			
			PrincipalCollection principals = new SimplePrincipalCollection(activeUser, this.getName());
			simpleAuthenticationInfo.setPrincipals(principals);
			simpleAuthenticationInfo.setCredentials(password);
			AddLog.addLog(branchName,username,Log.LOGIN,"��¼��ϵͳ");
			return simpleAuthenticationInfo;
		}
       //������Ȩ
		@Override
		protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
			//��principles��ȡ�������Ϣ
			//��getPrimaryPrincipal��������ֵתΪ����������ͣ��������doGetAuthenticationInfo
			//��֤ͨ����䵽SimpleAuthentication��������ͣ�
			ActiveUser activeUser = (ActiveUser)principals.getPrimaryPrincipal();
			List<SysPermission> permission = new ArrayList<SysPermission>();
			List<String> perm = new ArrayList<String>();
			
			try {
				//������ݻ�ȡȨ����Ϣ
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
			
			//�鵽Ȩ�����ݣ�������Ȩ��Ϣ(Ҫ�����ϱߵ�perm)
			SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
			//���ϱ߲�ѯ����Ȩ��Ϣ��䵽simpleAuthorizationInfo������
			simpleAuthorizationInfo.addStringPermissions(perm);
			//��ӽ�ɫ
			List<SysRole> roles= roleService.findRoleByUserId(activeUser.getUserid());
			for(int i=0;i<roles.size();i++){
				String roleName = roles.get(i).getName();
				simpleAuthorizationInfo.addRole(roleName);
			}
			return simpleAuthorizationInfo;
		}
		
		//�������
		public void clearCached() {
			PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
			super.clearCache(principals);
		}
		
}
