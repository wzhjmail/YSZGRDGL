package com.wzj.pojo;

import java.util.Date;

public class ExpressInfo {
	private int id;
	private String branch;//��֧����
	private String companyId;//��˾
	private String name;//�����
	private String number;//������
	private String contact;//��ϵ��
	private String phoneNumber;//��ϵ�˵绰
	private Date sendDate;//����ʱ��
	private String conAddress;//��ϵ�˵�ַ
	private String reciveName;//�ռ�������
	private String recivePhoneNum;//�ռ��˵绰
	private String reciveAddress;//�ռ��˵�ַ
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public ExpressInfo() {
		super();
	}
	public String getConAddress() {
		return conAddress;
	}
	public void setConAddress(String conAddress) {
		this.conAddress = conAddress;
	}
	public String getReciveName() {
		return reciveName;
	}
	public void setReciveName(String reciveName) {
		this.reciveName = reciveName;
	}
	public String getRecivePhoneNum() {
		return recivePhoneNum;
	}
	public void setRecivePhoneNum(String recivePhoneNum) {
		this.recivePhoneNum = recivePhoneNum;
	}
	public String getReciveAddress() {
		return reciveAddress;
	}
	public void setReciveAddress(String reciveAddress) {
		this.reciveAddress = reciveAddress;
	}
}
