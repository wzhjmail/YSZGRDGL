package com.wzj.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.Division;
import com.wzj.pojo.SysUser;
import com.wzj.service.impl.BranchCenterService;
import com.wzj.service.impl.SysUserService;

public class RememberMeFilter extends FormAuthenticationFilter {
	@Autowired
	private SysUserService sysService;
	@Autowired
	private BranchCenterService branchCenterService;
	protected boolean isAccessAllowed(ServletRequest request,ServletResponse response,Object mappedValue){
		Subject subject = getSubject(request,response);
		Session session = subject.getSession();
		//��ס�ҹ�����isAuthenticated�϶���false,��isRememberedΪtrue
		//session.getAttribute("user")Ϊ�յ������ȥ��ȡ����Ȼ�ͻ�Ƶ��ִ������Ĵ���
		if(!subject.isAuthenticated()&&subject.isRemembered()&&session.getAttribute("activeUser")==null){
			//˵���Ǽ�ס�ҹ���
			ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
			//��Ϊ����ȥ��ȡ�û�����
			if(activeUser!=null){
				String username = activeUser.getUsercode();
				SysUser sysUser = sysService.getByUserName(username);
				activeUser.setUserid(sysUser.getId());
				activeUser.setRealname(sysUser.getUsername());
				activeUser.setUsercode(sysUser.getUsercode());
				activeUser.setUsername(username);
				activeUser.setDept(sysUser.getDept());
				Division divisionByCode = branchCenterService.getDivisionByCode(activeUser.getRamusCenter());
				String branchName=divisionByCode.getDivisionname();
				activeUser.setBranchName(branchName);
				session.setAttribute("activeUser", activeUser);
			}			
		}
		return subject.isAuthenticated()||subject.isRemembered();
	}
}
