package com.wzj.pojo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
//企业劣迹记录
public class Misdeeds {
	
    private Integer id;
    private String branchid;//分支机构id
    private Integer companyid;//公司id
    private String qrecord;//质量记录
    private Date qtime;//质量记录时间
    private Boolean varify;//是否核实
    private Boolean dealed;//是否处理
    private String result;//处理结果
    private String opinion;//处理意见
    private Date rtime;//处理时间
    private String enclosure;//附件路径
    private Boolean available;//是否可用
    private MultipartFile mfile;
    
    public MultipartFile getMfile() {
		return mfile;
	}

	public void setMfile(MultipartFile mfile) {
		this.mfile = mfile;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid == null ? null : branchid.trim();
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getQrecord() {
        return qrecord;
    }

    public void setQrecord(String qrecord) {
        this.qrecord = qrecord == null ? null : qrecord.trim();
    }

    public Date getQtime() {
        return qtime;
    }

    public void setQtime(Date qtime) {
        this.qtime = qtime;
    }

    public Boolean getVarify() {
        return varify;
    }

    public void setVarify(Boolean varify) {
        this.varify = varify;
    }

    public Boolean getDealed() {
        return dealed;
    }

    public void setDealed(Boolean dealed) {
        this.dealed = dealed;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
    }

    public Date getRtime() {
        return rtime;
    }

    public void setRtime(Date rtime) {
        this.rtime = rtime;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure == null ? null : enclosure.trim();
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}