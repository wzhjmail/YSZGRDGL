package com.wzj.pojo;

/**
 * @author zxm
 *用于存储用户身份信息 
 */
public class ActiveUser implements java.io.Serializable{
	
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Integer userid;
	private String usercode;
	private String username;
	private String realname; //添加真实姓名属性ymx
	private String dept; //
	private String ramusCenter;//所属分中心
	private String branchName;//所属分中心名称
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getRamusCenter() {
		return ramusCenter;
	}
	public void setRamusCenter(String ramusCenter) {
		this.ramusCenter = ramusCenter;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
