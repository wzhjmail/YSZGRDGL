package com.wzj.pojo;

import java.util.Date;

public class Equipment {
	private int id;
	private String brand;//Ʒ���ͺ�
	private String model;//�������
	private Date buyDate;//����ʱ��
	private Date calibrationDate;//ĩ��У׼ʱ��
	private  String calibrationName;//У׼��������
	private int calibrationCycle;//У׼����
	private boolean checked;//�Ƿ����豸�ڼ�˲�
	private String branchId;//����������id
	private Date nextCailDate;//�´�У׼ʱ��
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	public Date getCalibrationDate() {
		return calibrationDate;
	}
	public void setCalibrationDate(Date calibrationDate) {
		this.calibrationDate = calibrationDate;
	}
	public String getCalibrationName() {
		return calibrationName;
	}
	public void setCalibrationName(String calibrationName) {
		this.calibrationName = calibrationName;
	}
	
	public int getCalibrationCycle() {
		return calibrationCycle;
	}
	public void setCalibrationCycle(int calibrationCycle) {
		this.calibrationCycle = calibrationCycle;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public Date getNextCailDate() {
		return nextCailDate;
	}
	public void setNextCailDate(Date nextCailDate) {
		this.nextCailDate = nextCailDate;
	}
	public Equipment(int id, String brand, String model, Date buyDate, String calibrationName, int calibrationCycle,
			boolean checked, String branchId, Date nextCailDate) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.buyDate = buyDate;
		this.calibrationName = calibrationName;
		this.calibrationCycle = calibrationCycle;
		this.checked = checked;
		this.branchId = branchId;
		this.nextCailDate = nextCailDate;
	}
	public Equipment() {
		super();
	}
}
