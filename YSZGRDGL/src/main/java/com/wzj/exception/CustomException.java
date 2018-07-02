package com.wzj.exception;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
//public class CustomException extends Exception{
public class CustomException extends AuthenticationException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//“Ï≥£–≈œ¢
		private String message;
		
		public CustomException(String message){
			super(message);
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
		public CustomException() {
			
		}
}
