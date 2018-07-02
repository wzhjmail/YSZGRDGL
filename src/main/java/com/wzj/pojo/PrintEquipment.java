package com.wzj.pojo;

public class PrintEquipment {
    private int id;                 //id
    private int companyId;          //公司id
    private String companyName;     //公司名字
    private String printName;       //印刷设备名称
    private String printModel;      //印刷设备型号
    private String printPlace;      //印刷设备产地
    private int printNumber;        //印刷设备数量
    private String printNotes;      //备注

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getPrintModel() {
        return printModel;
    }

    public void setPrintModel(String printModel) {
        this.printModel = printModel;
    }

    public String getPrintPlace() {
        return printPlace;
    }

    public void setPrintPlace(String printPlace) {
        this.printPlace = printPlace;
    }

    public int getPrintNumber() {
        return printNumber;
    }

    public void setPrintNumber(int printNumber) {
        this.printNumber = printNumber;
    }

    public String getPrintNotes() {
        return printNotes;
    }

    public void setPrintNotes(String printNotes) {
        this.printNotes = printNotes;
    }

	public PrintEquipment(int id, int companyId, String companyName, String printName, String printModel,
			String printPlace, int printNumber, String printNotes) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.companyName = companyName;
		this.printName = printName;
		this.printModel = printModel;
		this.printPlace = printPlace;
		this.printNumber = printNumber;
		this.printNotes = printNotes;
	}

	public PrintEquipment() {
		super();
	}

	public PrintEquipment(int id, String companyName) {
		super();
		this.id = id;
		this.companyName = companyName;
	}
    
}
