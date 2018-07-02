package com.wzj.DTO;

public class DivisionDTO{

	private String code;
	private String name;
	private String address;
	private String postcode;
	private String linkman;
	private String phone1;
	private String phone2;
	private String fax;
	private String regionCode;
	private String branchcode;
	private String[] region;
	
	public String[] getRegion() {
		return region;
	}
	public void setRegion(String[] region) {
		this.region = region;
	}
	public String getBranchcode() {
		return branchcode;
	}
	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}

	public DivisionDTO() {
		super();
	}
	
	public DivisionDTO(String code, String name, String address, String postcode, String linkman, String phone1,
			String phone2, String fax, String regionCode, String branchcode, String[] region) {
		super();
		this.code = code;
		this.name = name;
		this.address = address;
		this.postcode = postcode;
		this.linkman = linkman;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.fax = fax;
		this.regionCode = regionCode;
		this.branchcode = branchcode;
		this.region = region;
	}
	public DivisionDTO(String name, String address, String postcode, String linkman, String phone1, String phone2,
			String fax, String regionCode) {
		super();
		this.name = name;
		this.address = address;
		this.postcode = postcode;
		this.linkman = linkman;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.fax = fax;
		this.regionCode = regionCode;
	}
	public DivisionDTO(String code, String name, String address, String postcode, String linkman, String phone1,
			String phone2, String fax, String regionCode, String branchcode) {
		super();
		this.code = code;
		this.name = name;
		this.address = address;
		this.postcode = postcode;
		this.linkman = linkman;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.fax = fax;
		this.regionCode = regionCode;
		this.branchcode = branchcode;
	}
	
}
