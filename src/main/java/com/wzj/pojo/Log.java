package com.wzj.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Log implements Serializable {
	int id;
	Timestamp time;//ʱ��
	String person;//������
	String department;//����������
	String operationType;//��������:login,add,delete,modify,query,logout
	String operation;//�������
	
	public static final String ADD="���";
	public static final String DELETE="ɾ��";
	public static final String MODIFY="�޸�";
	public static final String QUERY="��ѯ";
	public static final String LOGIN="��¼";
	public static final String LOGOUT="�˳�";
	public static final String EXPORT="����";
	public static final String IMPORT="����";
	
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
