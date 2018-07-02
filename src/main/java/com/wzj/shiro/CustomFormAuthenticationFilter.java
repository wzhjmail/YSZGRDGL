package com.wzj.shiro;

import java.util.Collection;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.wzj.pojo.ActiveUser;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {
	@Autowired
	private SessionDAO sessionDao;
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String usertype = httpServletRequest.getParameter("dept");
		// 将取出的用户身份存入shiro的session中
		System.out.println("用户类型："+usertype);
		String username=request.getParameter("username");
		String pass=request.getParameter("password");
		Collection<Session> sessions = sessionDao.getActiveSessions();
        for (Session session : sessions) {
        	//获取session中已经登录用户的名称
        	ActiveUser activeUser=(ActiveUser)session.getAttribute("activeUser");
        	if(activeUser!=null){
        		String loginUsername=String.valueOf(activeUser.getUsername());
	            if(username!=null&&username.equals(loginUsername)){//重复登录
	            	session.setTimeout(0);
	            	session.stop();
	            }
        	}
        }
		/*if(usertype!=null){
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String code=request.getParameter("randomcode");
			String str1=CommonUtil.getInstance().bytesToMD5(password.getBytes());
			String md51=CommonUtil.getInstance().bytesToMD5((str1+code).getBytes());
			
			String pwd=sysService.findUserByUsername(username).getPassword();
			String sCode=(String)httpServletRequest.getSession().getAttribute("validateCode");
			String md52=CommonUtil.getInstance().bytesToMD5((pwd+sCode).getBytes());
			if(!(md51.equals(md52)))
				return false;
			httpServletRequest.getSession().setAttribute("validateCode", "");
		}*/
		return super.onAccessDenied(request, response);
	}

}
