package com.wzj.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Log implements Serializable {
	int id;
	Timestamp time;//时间
	String person;//操作人
	String department;//所属分中心
	String operationType;//操作类型:login,add,delete,modify,query,logout
	String operation;//具体操作
	
	public static final String ADD="添加";
	public static final String DELETE="删除";
	public static final String MODIFY="修改";
	public static final String QUERY="查询";
	public static final String LOGIN="登录";
	public static final String LOGOUT="退出";
	public static final String EXPORT="导出";
	public static final String IMPORT="导入";
	
	public Log(){}
	
	public Log(Timestamp time, String person, String department, String operationType, String operation) {
		super();
		this.time = time;
		this.person = person;
		this.department = department;
		this.operationType = operationType;
		this.operation = operation;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", time=" + time + ", person=" + person + ", department=" + department
				+ ", operationType=" + operationType + ", operation=" + operation + "]";
	}
	
}
