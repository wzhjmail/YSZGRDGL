package com.wzj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wzj.pojo.ActiveUser;
import com.wzj.pojo.Log;
import com.wzj.pojo.SysUser;
import com.wzj.service.impl.SysUserService;
import com.wzj.util.AddLog;

import net.sf.json.JSONObject;


/**
 * @author zxm 处理用户登录逻辑
 *
 */
@Controller
public class LoginController extends LogoutFilter {
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("/toLoginJSP")
	public String ToLoginJSP(){//用于，从主页跳转到登录界面
		return "login";
	}
	
	@RequestMapping("/checkCode")
	@ResponseBody //处理 AJAX请求，返回响应的内容，而不是 View Name
	//通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。
	//使用时机：返回的数据不是html标签的页面，而是其他某种格式的数据是（json、xml等）使用。
	public JSONObject checkCode(HttpServletRequest request){
		System.out.println("执行方法：checkCode");
		HttpSession session = request.getSession();
		//取出session的验证码（正确的验证码）
		String validateCode = (String) session.getAttribute("validateCode");
		//取出页面的验证码，输入的验证和session中的验证进行对比
		String randomcode = request.getParameter("randomcode");
		JSONObject jsonObj = new JSONObject();
		if(randomcode!=null && validateCode!=null && !randomcode.equalsIgnoreCase(validateCode)){
			jsonObj.put("result", "false");
		}else{
			jsonObj.put("result", "true");
		}
		return jsonObj;
	}
	
	@RequestMapping("/checkAudit")
	@ResponseBody 
	public JSONObject checkAudit(HttpServletRequest request){
		System.out.println("执行方法：checkAudit");
		String username = request.getParameter("username");
		JSONObject jsonObj = new JSONObject();
		SysUser sysuser=null;
		try{
			sysuser=sysUserService.getByUserName(username);
			if(sysuser.getLocked()==1){
				jsonObj.put("result", "false");
			}else{
				jsonObj.put("result", "true");
			}
		}catch(Exception e){
			jsonObj.put("result", "false");
			return jsonObj;
		}
		return jsonObj;
	}
	
	// 登陆提交地址，和applicationContext-shiro.xml中配置的loginurl一致
	@RequestMapping("/login")
	// to 返回json 然后后端根据json判断是成功还是失败 ，成功就跳转first，失败就login
	public String login(HttpServletRequest request,ModelAndView model) throws Exception {
		// 如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
		/*CustomException customException=(CustomException)request.getSession().getAttribute("error");
		if(customException!=null){
			System.out.println(customException.getMessage());
		}*/
		// 根据shiro返回的异常类路径判断，抛出指定异常信息
		if (exceptionClassName != null) {
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				// 最终会抛给异常处理器
				//throw new CustomException("用户不存在");
			}else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
				//throw new CustomException("用户名/密码错误");
			}/*else{
				throw new Exception();//最终在异常处理器生成未知错误 }
			}*/
		}else{
			Subject subject = SecurityUtils.getSubject();
			ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
			String bool=request.getParameter("username");
			System.out.println(bool);
			if(activeUser!=null)
				return "redirect:first";
		}
		// 此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
		// 登陆失败还到login页面
		return "login";
	}
	
	@RequestMapping("/logouts")
	public String logout(HttpServletRequest request,HttpServletResponse response) throws Exception{
		AddLog.addLog(Log.LOGOUT,"退出系统");
		Subject subject = getSubject(request,response);
		subject.logout();
		return "redirect:/first.action";
	}
}
