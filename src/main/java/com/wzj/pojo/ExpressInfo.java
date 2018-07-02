package com.wzj.pojo;

import java.util.Date;

public class ExpressInfo {
	private int id;
	private String branch;//分支机构
	private String companyId;//公司
	private String name;//快递名
	private String number;//订单号
	private String contact;//联系人
	private String phoneNumber;//联系人电话
	private Date sendDate;//寄送时间
	private String conAddress;//联系人地址
	private String reciveName;//收件人姓名
	private String recivePhoneNum;//收件人电话
	private String reciveAddress;//收件人地址
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
