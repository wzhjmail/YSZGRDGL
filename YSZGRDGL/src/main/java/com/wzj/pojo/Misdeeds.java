package com.wzj.pojo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
//��ҵ�Ӽ���¼
public class Misdeeds {
	
    private Integer id;
    private String branchid;//��֧����id
    private Integer companyid;//��˾id
    private String qrecord;//������¼
    private Date qtime;//������¼ʱ��
    private Boolean varify;//�Ƿ��ʵ
    private Boolean dealed;//�Ƿ���
    private String result;//������
    private String opinion;//�������
    private Date rtime;//����ʱ��
    private String enclosure;//����·��
    private Boolean available;//�Ƿ����
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