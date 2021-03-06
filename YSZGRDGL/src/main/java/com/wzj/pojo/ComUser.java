package com.wzj.pojo;

import java.io.Serializable;
import java.sql.Date;

import javax.management.loading.PrivateClassLoader;

import org.aspectj.weaver.AjAttribute.PrivilegedAttribute;

public class ComUser implements Serializable{
    private Integer id;
    private String username;//姓名
    private String gender;//性别
    private Date birthday;//生日
    private String ramusCenter;//所属分中心
    private String email;//邮箱
    private String post;//职务
    private String mobile;//手机
   
    private String usercode;
    private String password;
    private String salt;
    private Integer locked;
    private String role;
    private String dept;//所属部门
    private Integer status;
    private boolean isCut;
    private String phone;
    private String unit;//所在单位
    private int userLable;//用户标号
    
	public int getUserLable() {
		return userLable;
	}

	public void setUserLable(int userLable) {
		this.userLable = userLable;
	}

	public String getRamusCenter() {
		return ramusCenter;
	}

	public void setRamusCenter(String ramusCenter) {
		this.ramusCenter = ramusCenter;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean isCut() {
		return isCut;
	}

	public void setCut(boolean isCut) {
		this.isCut = isCut;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
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

	public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

	@Override
	public String toString() {
		return "SysUser [id=" + id + ", usercode=" + usercode + ", username=" + username + ", password=" + password
				+ ", salt=" + salt + ", locked=" + locked + "]";
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
	
}