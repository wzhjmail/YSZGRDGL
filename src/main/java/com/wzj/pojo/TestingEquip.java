package com.wzj.pojo;
//�������豸

import java.util.Date;

public class TestingEquip {
	int id;
	String name;//�豸����
	String testingModel;      //ӡˢ�豸�ͺ�
	int count;//����
	Date time;//���һ��У׼����
	String remark;//��ע
	int companyId;//������ҵid
	String companyName;//��ҵ����
	
	public String getTestingModel() {
		return testingModel;
	}
	public void setTestingModel(String testingModel) {
		this.testingModel = testingModel;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public TestingEquip(String name, int count, Date time, String remark, int companyId, String companyName) {
		super();
		this.name = name;
		this.count = count;
		this.time = time;
		this.remark = remark;
		this.companyId = companyId;
		this.companyName = companyName;
	}
	public TestingEquip() {
		super();
	}
	public TestingEquip(int id, String companyName) {
		super();
		this.id = id;
		this.companyName = companyName;
	}
	
}
