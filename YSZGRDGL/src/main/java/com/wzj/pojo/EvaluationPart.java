package com.wzj.pojo;

public class EvaluationPart {
    private Integer id;

    private String result;

    private String resultnote;

    private Integer evalid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getResultnote() {
        return resultnote;
    }

    public void setResultnote(String resultnote) {
        this.resultnote = resultnote == null ? null : resultnote.trim();
    }

    public Integer getEvalid() {
        return evalid;
    }

    public void setEvalid(Integer evalid) {
        this.evalid = evalid;
    }

	public EvaluationPart(String result, Integer evalid) {
		super();
		this.result = result;
		this.evalid = evalid;
	}

	public EvaluationPart() {
		super();
	}
}