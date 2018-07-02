package com.wzj.pojo;

import java.io.Serializable;

public class SysRolePermission implements Serializable{
	int id;
	int roleId;
	int pmsId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getPmsId() {
		return pmsId;
	}
	public void setPmsId(int pmsId) {
		this.pmsId = pmsId;
	}
}
