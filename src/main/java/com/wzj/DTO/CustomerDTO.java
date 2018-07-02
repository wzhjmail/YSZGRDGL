package com.wzj.DTO;

public class CustomerDTO {

	private Integer ID;//id
	private String userName;//用户名
	private String realName;//真实姓名
	private String remark;//备注 
	public CustomerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerDTO(Integer iD, String userName, String realName, String remark) {
		super();
		ID = iD;
		this.userName = userName;
		this.realName = realName;
		this.remark = remark;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
