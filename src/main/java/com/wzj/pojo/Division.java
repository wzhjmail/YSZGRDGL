package com.wzj.pojo;

public class Division{
	
	private String divisioncode;
	private String divisionname;
	private String divisionaddress;
	private String divisionpostcode;
	private String divisionlinkman;
	private String divisionphone1;
	private String divisionphone2;
	private String divisionfax;
	
	public String getDivisioncode(){
		return divisioncode;
	}
	
	public void setDivisioncode(String divisioncode){
		this.divisioncode=divisioncode;
	}
	
	public String getDivisionname(){
		return divisionname;
	}
	
	public void setDivisionname(String divisionname){
		this.divisionname=divisionname;
	}
	
	public String getDivisionaddress(){
		return divisionaddress;
	}
	
	public void setDivisionaddress(String divisionaddress){
		this.divisionaddress=divisionaddress;
	}
	
	public String getDivisionpostcode(){
		return divisionpostcode;
	}
	
	public void setDivisionpostcode(String divisionpostcode){
		this.divisionpostcode=divisionpostcode;
	}
	
	public String getDivisionlinkman(){
		return divisionlinkman;
	}
	
	public void setDivisionlinkman(String divisionlinkman){
		this.divisionlinkman=divisionlinkman;
	}
	
	public String getDivisionphone1(){
		return divisionphone1;
	}
	
	public void setDivisonphone1(String divisionphone1){
		this.divisionphone1=divisionphone1;
	}
	
	public String getDivisionphone2(){
		return divisionphone2;
	}
	
	public void setDivisonphone2(String divisionphone2){
		this.divisionphone2=divisionphone2;
	}
	
	public String getDivisionfax(){
		return divisionfax;
	}
	
	public void setDivisonfax(String divisionfax){
		this.divisionfax=divisionfax;
	}

	public Division(String divisioncode, String divisionname, String divisionaddress, String divisionpostcode,
			String divisionlinkman, String divisionphone1, String divisionphone2, String divisionfax) {
		super();
		this.divisioncode = divisioncode;
		this.divisionname = divisionname;
		this.divisionaddress = divisionaddress;
		this.divisionpostcode = divisionpostcode;
		this.divisionlinkman = divisionlinkman;
		this.divisionphone1 = divisionphone1;
		this.divisionphone2 = divisionphone2;
		this.divisionfax = divisionfax;
	}

	public Division() {
		super();
	}
	
}