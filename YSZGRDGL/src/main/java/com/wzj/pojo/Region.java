package com.wzj.pojo;

import java.sql.Timestamp;

public class Region{

	private String regioncode;//地区码
	private String regionname;//地区名
	private String parentcode;//父本码
	private String provincecode;//省码
	private String citycode;//城市码
	private String countrycode;//地区码
	private String towncode;
	private Timestamp updatetime;
	private String allregionname;
	
	
	public String getRegioncode(){
		return regioncode;
	}
	
	public void setRegioncode(String regioncode){
		this.regioncode = regioncode == null ? null : regioncode.trim();
	}
	
	public String getRegionname(){
		return regionname;
	}
	
	public void setRegionname(String regionname){
		this.regionname=regionname == null ? null : regionname.trim();
	}
	
	public String getParentcode(){
		return parentcode;
	}
	
	public void setParentcode(String parentcode){
		this.parentcode=parentcode == null ? null : parentcode.trim();
	}
	
	public String getProvincecode(){
		return provincecode;
	}
	
	public void setProvincecode(String provincecode){
		this.provincecode=provincecode == null ? null : provincecode.trim();
	}
	
	public String getCitycode(){
		return citycode;
	}
	
	public void setCitycode(String citycode){
		this.citycode=citycode == null ? null : citycode.trim();
	}
	
	public String getCountrycode(){
		return countrycode;
	}
	
	public void setCountrycode(String countrycode){
		this.countrycode=countrycode == null ? null : countrycode.trim();
	}
	
	public String getTowncode(){
		return towncode;
	}
	
	public void setTowncode(String towncode){
		this.towncode=towncode == null ? null : towncode.trim();
	}
	
	public Timestamp getUpdatetime(){
		return updatetime;
	}
	
	public void setUpdatetime(Timestamp updatetime){
		this.updatetime=updatetime;
	}
	
	public String getAllregionname(){
		return allregionname;
	}
	
	public void setAllregionname(String allregionname){
		this.allregionname=allregionname == null ? null : allregionname.trim();
	}
}