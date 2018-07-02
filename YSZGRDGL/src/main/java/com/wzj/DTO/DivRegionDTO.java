package com.wzj.DTO;

public class DivRegionDTO {

	private String divisioncode;
	private String regioncode;
	private int id;
	public String getDivisioncode() {
		return divisioncode;
	}
	public void setDivisioncode(String divisioncode) {
		this.divisioncode = divisioncode;
	}
	public String getRegioncode() {
		return regioncode;
	}
	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DivRegionDTO(int id, String regioncode, String divisioncode) {
		super();
		this.divisioncode = divisioncode;
		this.regioncode = regioncode;
		this.id = id;
	}
	public DivRegionDTO() {
		super();
	}
	public DivRegionDTO(String divisioncode, String regioncode) {
		super();
		this.divisioncode = divisioncode;
		this.regioncode = regioncode;
	}
}
