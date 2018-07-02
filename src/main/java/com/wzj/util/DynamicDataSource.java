package com.wzj.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;  

/* 
 * ���ö�����Դ 
 */  
  
public class DynamicDataSource extends AbstractRoutingDataSource{  
  
    public static final String DATASOURCEONE = "dataSourceOne";  
      
    public static final String DATASOURCETWO = "dataSourceTwo";  
    //�����̣߳���ȡ��ǰ����ִ�е�currentThread  
    public static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();   
      
    public static void setCustomerType(String customerType) {  
    	System.out.println("��ǰ�л�������Դ = " + customerType);
        contextHolder.set(customerType);  
          
    }  
  
    public static String getCustomerType() {  
  
        return contextHolder.get();  
           
    }  
  
    public static void clearCustomerType() {  
  
        contextHolder.remove();  
      
    }  
  
    @Override  
    protected Object determineCurrentLookupKey() {  
          
        return getCustomerType();  
          
    }  
}
