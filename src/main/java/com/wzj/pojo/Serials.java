package com.wzj.pojo;

import java.util.Date;
//证书
public class Serials {
    private Integer id;
    private String code;
    private Integer serial;
    private String years;
    private Date creatdate;
    private Integer type;
    public static final int NEWAPP=1;//新申请业务
    public static final int RECOG=2;//复认业务
    public static final int CHANGE=3;//变更业务
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years == null ? null : years.trim();
    }

    public Date getCreatdate() {
        return creatdate;
    }

    public void setCreatdate(Date creatdate) {
        this.creatdate = creatdate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}