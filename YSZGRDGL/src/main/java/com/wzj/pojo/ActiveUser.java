package com.wzj.pojo;

/**
 * @author zxm
 *���ڴ洢�û������Ϣ 
 */
public class ActiveUser implements java.io.Serializable{
	
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	private Integer userid;
	private String usercode;
	private String username;
	private String realname; //�����ʵ��������ymx
	private String dept; //
	private String ramusCenter;//����������
	private String branchName;//��������������
	
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
