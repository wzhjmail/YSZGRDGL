package com.wzj.pojo;

public class PbtReport {
	private String f_report_key;//����Ψһ�뺷
	private String f_report_code;//������
	private String f_pserson;//������
	private byte[] f_report_fj;//���渽��
	private String titleNo;//��ˮ��
	private char downed;
	
	public char getDowned() {
		return downed;
	}
	public void setDowned(char downed) {
		this.downed = downed;
	}
	public String getF_report_key() {
		return f_report_key;
	}
	public void setF_report_key(String f_report_key) {
		this.f_report_key = f_report_key;
	}
	public String getF_report_code() {
		return f_report_code;
	}
	public void setF_report_code(String f_report_code) {
		this.f_report_code = f_report_code;
	}
	public String getF_pserson() {
		return f_pserson;
	}
	public void setF_pserson(String f_pserson) {
		this.f_pserson = f_pserson;
	}
	public byte[] getF_report_fj() {
		return f_report_fj;
	}
	public void setF_report_fj(byte[] f_report_fj) {
		this.f_report_fj = f_report_fj;
	}
	public String getTitleNo() {
		return titleNo;
	}
	public void setTitleNo(String titleNo) {
		this.titleNo = titleNo;
	}
}
