package com.wzj.pojo;

public class Application {
	private String id;
	private String name;
	private Integer state=0;//申请表的状态：0申请；1审批；2待确认
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
