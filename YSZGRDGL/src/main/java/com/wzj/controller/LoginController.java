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
 * @author zxm �����û���¼�߼�
 *
 */
@Controller
public class LoginController extends LogoutFilter {
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("/toLoginJSP")
	public String ToLoginJSP(){//���ڣ�����ҳ��ת����¼����
		return "login";
	}
	
	@RequestMapping("/checkCode")
	@ResponseBody //���� AJAX���󣬷�����Ӧ�����ݣ������� View Name
	//ͨ���ʵ���HttpMessageConverterת��Ϊָ����ʽ��д�뵽Response�����body��������
	//ʹ��ʱ�������ص����ݲ���html��ǩ��ҳ�棬��������ĳ�ָ�ʽ�������ǣ�json��xml�ȣ�ʹ�á�
	public JSONObject checkCode(HttpServletRequest request){
		System.out.println("ִ�з�����checkCode");
		HttpSession session = request.getSession();
		//ȡ��session����֤�루��ȷ����֤�룩
		String validateCode = (String) session.getAttribute("validateCode");
		//ȡ��ҳ�����֤�룬�������֤��session�е���֤���жԱ�
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
		System.out.println("ִ�з�����checkAudit");
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
	
	// ��½�ύ��ַ����applicationContext-shiro.xml�����õ�loginurlһ��
	@RequestMapping("/login")
	// to ����json Ȼ���˸���json�ж��ǳɹ�����ʧ�� ���ɹ�����תfirst��ʧ�ܾ�login
	public String login(HttpServletRequest request,ModelAndView model) throws Exception {
		// �����½ʧ�ܴ�request�л�ȡ��֤�쳣��Ϣ��shiroLoginFailure����shiro�쳣���ȫ�޶���
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
		/*CustomException customException=(CustomException)request.getSession().getAttribute("error");
		if(customException!=null){
			System.out.println(customException.getMessage());
		}*/
		// ����shiro���ص��쳣��·���жϣ��׳�ָ���쳣��Ϣ
		if (exceptionClassName != null) {
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				// ���ջ��׸��쳣������
				//throw new CustomException("�û�������");
			}else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
				//throw new CustomException("�û���/�������");
			}/*else{
				throw new Exception();//�������쳣����������δ֪���� }
			}*/
		}else{
			Subject subject = SecurityUtils.getSubject();
			ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
			String bool=request.getParameter("username");
			System.out.println(bool);
			if(activeUser!=null)
				return "redirect:first";
		}
		// �˷����������½�ɹ�����֤�ɹ�����shiro��֤�ɹ����Զ���ת����һ������·��
		// ��½ʧ�ܻ���loginҳ��
		return "login";
	}
	
	@RequestMapping("/logouts")
	public String logout(HttpServletRequest request,HttpServletResponse response) throws Exception{
		AddLog.addLog(Log.LOGOUT,"�˳�ϵͳ");
		Subject subject = getSubject(request,response);
		subject.logout();
		return "redirect:/first.action";
	}
}
