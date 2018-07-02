package com.wzj.pojo;

import java.util.Date;

public class FormChange {
    private Integer id;
    private Integer pid;//对应的ys_company中的id
    private String remark;
    private String companynameOld;
    private String companynameNew;
    private String addressOld;
    private String addressNew;
    private String linkmanOld;
    private String linkmanNew;
    private String materialOld;
    private String materialNew;
    private String printtypeOld;
    private String printtypeNew;
    private String certappdateOld;
    private String certappdateNew;
    private String certtodateOld;
    private String certtodateNew;
    private Integer status;
    private String offshootorganiz;//分支机构id
    private Date createdate;
    private Date checkdate;
    private String chekconceit;
    private String checkman;
    private String checkremark;
    private String updatecause;
    private String coporationOld;
    private String coporationNew;
    private String linkmantelOld;
    private String linkmantelNew;
    private String coporationtelOld;
    private String coporationtelNew;
    private String postcodeOld;
    private String postcodeNew;
    private String operator;
    
    //新添加字段
    private String taskId;
    //private String branchId;//分支机构id
    private String branchName;//分支机构名称
    private String titleno;//流水号
    private String changeType;
    
    
    public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getTitleno() {
		return titleno;
	}

	public void setTitleno(String titleno) {
		this.titleno = titleno;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCompanynameOld() {
        return companynameOld;
    }

    public void setCompanynameOld(String companynameOld) {
        this.companynameOld = companynameOld == null ? null : companynameOld.trim();
    }

    public String getCompanynameNew() {
        return companynameNew;
    }

    public void setCompanynameNew(String companynameNew) {
        this.companynameNew = companynameNew == null ? null : companynameNew.trim();
    }

    public String getAddressOld() {
        return addressOld;
    }

    public void setAddressOld(String addressOld) {
        this.addressOld = addressOld == null ? null : addressOld.trim();
    }

    public String getAddressNew() {
        return addressNew;
    }

    public void setAddressNew(String addressNew) {
        this.addressNew = addressNew == null ? null : addressNew.trim();
    }

    public String getLinkmanOld() {
        return linkmanOld;
    }

    public void setLinkmanOld(String linkmanOld) {
        this.linkmanOld = linkmanOld == null ? null : linkmanOld.trim();
    }

    public String getLinkmanNew() {
        return linkmanNew;
    }

    public void setLinkmanNew(String linkmanNew) {
        this.linkmanNew = linkmanNew == null ? null : linkmanNew.trim();
    }

    public String getMaterialOld() {
        return materialOld;
    }

    public void setMaterialOld(String materialOld) {
        this.materialOld = materialOld == null ? null : materialOld.trim();
    }

    public String getMaterialNew() {
        return materialNew;
    }

    public void setMaterialNew(String materialNew) {
        this.materialNew = materialNew == null ? null : materialNew.trim();
    }

    public String getPrinttypeOld() {
        return printtypeOld;
    }

    public void setPrinttypeOld(String printtypeOld) {
        this.printtypeOld = printtypeOld == null ? null : printtypeOld.trim();
    }

    public String getPrinttypeNew() {
        return printtypeNew;
    }

    public void setPrinttypeNew(String printtypeNew) {
        this.printtypeNew = printtypeNew == null ? null : printtypeNew.trim();
    }

    public String getCertappdateOld() {
        return certappdateOld;
    }

    public void setCertappdateOld(String certappdateOld) {
        this.certappdateOld = certappdateOld == null ? null : certappdateOld.trim();
    }

    public String getCertappdateNew() {
        return certappdateNew;
    }

    public void setCertappdateNew(String certappdateNew) {
        this.certappdateNew = certappdateNew == null ? null : certappdateNew.trim();
    }

    public String getCerttodateOld() {
        return certtodateOld;
    }

    public void setCerttodateOld(String certtodateOld) {
        this.certtodateOld = certtodateOld == null ? null : certtodateOld.trim();
    }

    public String getCerttodateNew() {
        return certtodateNew;
    }

    public void setCerttodateNew(String certtodateNew) {
        this.certtodateNew = certtodateNew == null ? null : certtodateNew.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOffshootorganiz() {
        return offshootorganiz;
    }

    public void setOffshootorganiz(String offshootorganiz) {
        this.offshootorganiz = offshootorganiz == null ? null : offshootorganiz.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(Date checkdate) {
        this.checkdate = checkdate;
    }

    public String getChekconceit() {
        return chekconceit;
    }

    public void setChekconceit(String chekconceit) {
        this.chekconceit = chekconceit == null ? null : chekconceit.trim();
    }

    public String getCheckman() {
        return checkman;
    }

    public void setCheckman(String checkman) {
        this.checkman = checkman == null ? null : checkman.trim();
    }

    public String getCheckremark() {
        return checkremark;
    }

    public void setCheckremark(String checkremark) {
        this.checkremark = checkremark == null ? null : checkremark.trim();
    }

    public String getUpdatecause() {
        return updatecause;
    }

    public void setUpdatecause(String updatecause) {
        this.updatecause = updatecause == null ? null : updatecause.trim();
    }

    public String getCoporationOld() {
        return coporationOld;
    }

    public void setCoporationOld(String coporationOld) {
        this.coporationOld = coporationOld == null ? null : coporationOld.trim();
    }

    public String getCoporationNew() {
        return coporationNew;
    }

    public void setCoporationNew(String coporationNew) {
        this.coporationNew = coporationNew == null ? null : coporationNew.trim();
    }

    public String getLinkmantelOld() {
        return linkmantelOld;
    }

    public void setLinkmantelOld(String linkmantelOld) {
        this.linkmantelOld = linkmantelOld == null ? null : linkmantelOld.trim();
    }

    public String getLinkmantelNew() {
        return linkmantelNew;
    }

    public void setLinkmantelNew(String linkmantelNew) {
        this.linkmantelNew = linkmantelNew == null ? null : linkmantelNew.trim();
    }

    public String getCoporationtelOld() {
        return coporationtelOld;
    }

    public void setCoporationtelOld(String coporationtelOld) {
        this.coporationtelOld = coporationtelOld == null ? null : coporationtelOld.trim();
    }

    public String getCoporationtelNew() {
        return coporationtelNew;
    }

    public void setCoporationtelNew(String coporationtelNew) {
        this.coporationtelNew = coporationtelNew == null ? null : coporationtelNew.trim();
    }

    public String getPostcodeOld() {
        return postcodeOld;
    }

    public void setPostcodeOld(String postcodeOld) {
        this.postcodeOld = postcodeOld == null ? null : postcodeOld.trim();
    }

    public String getPostcodeNew() {
        return postcodeNew;
    }

    public void setPostcodeNew(String postcodeNew) {
        this.postcodeNew = postcodeNew == null ? null : postcodeNew.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

	/*public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}*/

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
    
}