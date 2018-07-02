package com.wzj.DTO;

public class AppDTO {

	private int begin;
	private int end;
	private String branchId;
	private int status;
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public AppDTO(int begin, int end) {
		super();
		this.begin = begin;
		this.end = end;
	}
	public AppDTO() {}
}
