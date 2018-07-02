package com.wzj.pojo;

public class PrintEquipment {
    private int id;                 //id
    private int companyId;          //��˾id
    private String companyName;     //��˾����
    private String printName;       //ӡˢ�豸����
    private String printModel;      //ӡˢ�豸�ͺ�
    private String printPlace;      //ӡˢ�豸����
    private int printNumber;        //ӡˢ�豸����
    private String printNotes;      //��ע

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
