package com.wzj.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class CustomExceptionResolver implements HandlerExceptionResolver{

	//ǰ�˿�����DispatcherServlet�ڽ���HandlerMapping������HandlerAdapterִ��Handler�����У���������쳣�ͻ�ִ�д˷���
	//handler����Ҫִ�е�Handler��������ʵ�����HandlerMethod
	//Exception ex���ǽ��յ��쳣��Ϣ
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		//����쳣
		//ex.printStackTrace();
		
		//ͳһ�쳣�������
		//���ϵͳ�Զ����CustomException�쳣���Ϳ���ֱ�Ӵ��쳣���л�ȡ�쳣��Ϣ�����쳣�����ڴ���ҳ��չʾ
		//�쳣��Ϣ
		String message = null;
		CustomException customException = null;
		//���ex��ϵͳ �Զ�����쳣��ֱ��ȡ���쳣��Ϣ
		if(ex instanceof CustomException){
			customException = (CustomException)ex;
		}else{
			//��Է�CustomException�쳣�����������¹����һ��CustomException���쳣��ϢΪ��δ֪����
			customException = new CustomException("δ֪����");
		}
		//���� ��Ϣ
		message = customException.getMessage();
		System.out.println("ִ���˸ô��������:"+message);
		request.setAttribute("message", message);
		try {
			//ת�򵽴��� ҳ��
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView();
	}

}
