package com.wzj.DTO;

public class EvaluationDTO {
    private Integer id;

    private String required;

    private String method;

    private Integer num;

    private Boolean available;

    private String result;
    
    private String resultnote;
    
    public String getResultnote() {
		return resultnote;
	}

	public void setResultnote(String resultnote) {
		this.resultnote = resultnote;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required == null ? null : required.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}