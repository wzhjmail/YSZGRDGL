package com.wzj.DTO;

import java.util.Date;

public class EnterpriseSearchDTO {

	private String branchId="";//�����ı��
	private String term1="or";//����1
	private Date appStartTime=null;//���뿪ʼʱ��
	private Date appEndTime=null;//�������ʱ��
	private String term2="or";//����2
	private Date certStartTime=null;//��֤��ʼʱ��
	private Date certEndTime=null;//��֤����ʱ��
	private String term3="or";//����3
	private Date expireStartTime=null;//���ڿ�ʼʱ��
	private Date expireEndTime=null;//���ڽ���ʱ��
	private String term4="or";//����4
	private String type="";//ҵ������
	private String enterpriseName="";//��ҵ����
	private String term5="or";//����5
	private String address="";//���ڵ���
	private String term6="or";//����6
	public String getTerm4() {
		return term4;
	}
	public void setTerm4(String term4) {
		this.term4 = term4;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public Date getAppStartTime() {
		return appStartTime;
	}
	public void setAppStartTime(Date appStartTime) {
		this.appStartTime = appStartTime;
	}
	public Date getAppEndTime() {
		return appEndTime;
	}
	public void setAppEndTime(Date appEndTime) {
		this.appEndTime = appEndTime;
	}
	public Date getCertStartTime() {
		return certStartTime;
	}
	public void setCertStartTime(Date certStartTime) {
		this.certStartTime = certStartTime;
	}
	public Date getCertEndTime() {
		return certEndTime;
	}
	public void setCertEndTime(Date certEndTime) {
		this.certEndTime = certEndTime;
	}
	public Date getExpireStartTime() {
		return expireStartTime;
	}
	public void setExpireStartTime(Date expireStartTime) {
		this.expireStartTime = expireStartTime;
	}
	public Date getExpireEndTime() {
		return expireEndTime;
	}
	public void setExpireEndTime(Date expireEndTime) {
		this.expireEndTime = expireEndTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTerm1() {
		return term1;
	}
	public void setTerm1(String term1) {
		   this.term1 = term1;
	}
	public String getTerm2() {
		return term2;
	}
	public void setTerm2(String term2) {
		this.term2 = term2;
	}
	public String getTerm3() {
		return term3;
	}
	public void setTerm3(String term3) {
		this.term3 = term3;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getTerm5() {
		return term5;
	}
	public void setTerm5(String term5) {
		this.term5 = term5;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTerm6() {
		return term6;
	}
	public void setTerm6(String term6) {
		this.term6 = term6;
	}
	
}
