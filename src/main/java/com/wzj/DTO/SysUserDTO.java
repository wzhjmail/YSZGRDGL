package com.wzj.DTO;

import java.sql.Date;

public class SysUserDTO {
	private Integer id;

    private String usercode;

    private String username;

    private String password;
    
    private String role;
    
    private String dept;
    
    private Integer locked;
    
    private String ramusCenter;
    
    private String gender;
    
    private Date birthday;
    
    private String phone;
    
    private String mobile;
    
    private String email;
    
    private String unit;
    
    private String post;
    
	public SysUserDTO(){
    	super();
    }
	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
	public String getRamusCenter(){
		return ramusCenter;
	}
	
	public void setRamusCenter(String ramusCenter){
		this.ramusCenter=ramusCenter;
	}
	
	
	public String getGender(){
		return gender;
	}
	
	public void setGender(String gender){
		this.gender=gender;
	}
	
	public Date getBirthday(){
		return birthday;
	}
	
	public void setBirthday(Date birthday){
		this.birthday=birthday;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public void setPhone(String phone){
		this.phone=phone;
	}
	
	public String getMobile(){
		return mobile;
	}
	
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email=email;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public SysUserDTO(Integer id, String usercode, String username, String password, String role, String dept,
			Integer locked, String ramusCenter, String gender, Date birthday, String phone, String mobile, String email,
			String unit, String post) {
		super();
		this.id = id;
		this.usercode = usercode;
		this.username = username;
		this.password = password;
		this.role = role;
		this.dept = dept;
		this.locked = locked;
		this.ramusCenter = ramusCenter;
		this.gender = gender;
		this.birthday = birthday;
		this.phone = phone;
		this.mobile = mobile;
		this.email = email;
		this.unit = unit;
		this.post = post;
	}
	public SysUserDTO(String usercode, String username, String password, String role, String dept, Integer locked,
			String ramusCenter, String gender, Date birthday, String phone, String mobile, String email, String unit,
			String post) {
		super();
		this.usercode = usercode;
		this.username = username;
		this.password = password;
		this.role = role;
		this.dept = dept;
		this.locked = locked;
		this.ramusCenter = ramusCenter;
		this.gender = gender;
		this.birthday = birthday;
		this.phone = phone;
		this.mobile = mobile;
		this.email = email;
		this.unit = unit;
		this.post = post;
	}
	public SysUserDTO(Integer id, String username, String ramusCenter, String gender, Date birthday, String mobile,
			String email, String post) {
		super();
		this.id = id;
		this.username = username;
		this.ramusCenter = ramusCenter;
		this.gender = gender;
		this.birthday = birthday;
		this.mobile = mobile;
		this.email = email;
		this.post = post;
	}
	
}
