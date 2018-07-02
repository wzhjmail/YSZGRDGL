package com.wzj.DTO;

import java.util.Date;

public class EnterpriseSearchDTO {

	private String branchId="";//分中心编号
	private String term1="or";//条件1
	private Date appStartTime=null;//申请开始时间
	private Date appEndTime=null;//申请结束时间
	private String term2="or";//条件2
	private Date certStartTime=null;//发证开始时间
	private Date certEndTime=null;//发证结束时间
	private String term3="or";//条件3
	private Date expireStartTime=null;//到期开始时间
	private Date expireEndTime=null;//到期结束时间
	private String term4="or";//条件4
	private String type="";//业务类型
	private String enterpriseName="";//企业名称
	private String term5="or";//条件5
	private String address="";//所在地区
	private String term6="or";//条件6
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
