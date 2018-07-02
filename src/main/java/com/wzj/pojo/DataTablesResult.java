package com.wzj.pojo;

public class DataTablesResult {
	
    private String 	sEcho;
    private Object aaData;
    private int iTotalRecords;
    private int iTotalDisplayRecords;
    
    public void setSEcho(String sEcho){
        this.sEcho = sEcho;
    }
    
    public void setAaData(Object aaData){
        this.aaData = aaData;
    }
    
    public void setITotalRecords(int iTotalRecords){
        this.iTotalRecords = iTotalRecords;
    }
    
    public void setITotalDisplayRecords(int iTotalDisplayRecords){
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }
    
    public String getSEcho(){
        return this.sEcho;
    }
    
    public Object getAaData(){
        return this.aaData;
    }
    
    public int getITotalRecords(){
        return this.iTotalRecords;
    }
    
    public int getITotalDisplayRecords(){
        return this.iTotalDisplayRecords;
    }
}