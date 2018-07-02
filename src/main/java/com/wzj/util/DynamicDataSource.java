package com.wzj.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;  

/* 
 * 配置多数据源 
 */  
  
public class DynamicDataSource extends AbstractRoutingDataSource{  
  
    public static final String DATASOURCEONE = "dataSourceOne";  
      
    public static final String DATASOURCETWO = "dataSourceTwo";  
    //本地线程，获取当前正在执行的currentThread  
    public static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();   
      
    public static void setCustomerType(String customerType) {  
    	System.out.println("当前切换的数据源 = " + customerType);
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
