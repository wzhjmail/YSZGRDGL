package com.wzj.pojo;

public class Certificate {
    private Integer certno;

    private Integer startno;
    
    private String comName;
    
    private Integer num;//ÏÔÊ¾µÄĞòºÅ
    
    public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public Integer getCertno() {
        return certno;
    }

    public void setCertno(Integer certno) {
        this.certno = certno;
    }

    public Integer getStartno() {
        return startno;
    }

    public void setStartno(Integer startno) {
        this.startno = startno;
    }
}