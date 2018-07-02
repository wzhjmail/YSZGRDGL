package com.wzj.pojo;

public class ReviewFormPart {
    private Integer id;

    private Integer num;

    private String psjl;

    private String psjg;

    private Integer syndicid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getPsjl() {
        return psjl;
    }

    public void setPsjl(String psjl) {
        this.psjl = psjl == null ? null : psjl.trim();
    }

    public String getPsjg() {
        return psjg;
    }

    public void setPsjg(String psjg) {
        this.psjg = psjg == null ? null : psjg.trim();
    }

    public Integer getSyndicid() {
        return syndicid;
    }

    public void setSyndicid(Integer syndicid) {
        this.syndicid = syndicid;
    }
}