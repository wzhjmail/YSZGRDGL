package com.wzj.util;

import java.lang.reflect.Field;

public class FilterNull {
	public static Object filterNull(Object bo) {  
	    if(bo == null){  
	        return null;  
	    }  
	    Class clz = bo.getClass();  
	    Field[] f = clz.getDeclaredFields();  
	    try {  
	        for (int i = 0; i < f.length; i++) {  
	            f[i].setAccessible(true);  
	            if(f[i].get(bo) == null){
	            	String type=f[i].getGenericType().toString();
	            	if(type.equals("class java.lang.String")){
	            		f[i].set(bo, ""); 
	            	}
	            	if(type.equals("class java.lang.Integer")){
	            		f[i].set(bo,0); 
	            	}
	            }
	        }  
	    } catch (IllegalArgumentException e) {  
	        e.printStackTrace();  
	    } catch (IllegalAccessException e) {  
	        e.printStackTrace();  
	    }  
	    return bo;  
	}  

}
