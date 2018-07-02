package com.wzj.pojo;

import java.util.Date;

public class ReviewForm {
    private Integer id;

    private Integer pid;

    private String titleno;

    private String enterpriseName;

    private String chiCenter;

    private Date createDate;

    private Integer status;

    private String psman;

    private String fzman;

    private String pzman;

    private String hzman;

    private Date psdate;

    private Date fzdate;

    private Date pzdate;

    private String expertIds;

    private String syndic;

    private String mavinIdea;

    private String frameworkIdea;

    private String centerIdea;

    public String getSyndic() {
        return syndic;
    }

    public void setSyndic(String syndic) {
        this.syndic = syndic == null ? null : syndic.trim();
    }

    public String getMavinIdea() {
        return mavinIdea;
    }

    public void setMavinIdea(String mavinIdea) {
        this.mavinIdea = mavinIdea == null ? null : mavinIdea.trim();
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
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPsman() {
        return psman;
    }

    public void setPsman(String psman) {
        this.psman = psman == null ? null : psman.trim();
    }

    public String getFzman() {
        return fzman;
    }

    public void setFzman(String fzman) {
        this.fzman = fzman == null ? null : fzman.trim();
    }

    public String getPzman() {
        return pzman;
    }

    public void setPzman(String pzman) {
        this.pzman = pzman == null ? null : pzman.trim();
    }

    public String getHzman() {
        return hzman;
    }

    public void setHzman(String hzman) {
        this.hzman = hzman == null ? null : hzman.trim();
    }

    public Date getPsdate() {
        return psdate;
    }

    public void setPsdate(Date psdate) {
        this.psdate = psdate;
    }

    public Date getFzdate() {
        return fzdate;
    }

    public void setFzdate(Date fzdate) {
        this.fzdate = fzdate;
    }

    public Date getPzdate() {
        return pzdate;
    }

    public void setPzdate(Date pzdate) {
        this.pzdate = pzdate;
    }

    public String getExpertIds() {
        return expertIds;
    }

    public void setExpertIds(String expertIds) {
        this.expertIds = expertIds == null ? null : expertIds.trim();
    }

	public String getTitleno() {
		return titleno;
	}

	public void setTitleno(String titleno) {
		this.titleno = titleno;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getChiCenter() {
		return chiCenter;
	}

	public void setChiCenter(String chiCenter) {
		this.chiCenter = chiCenter;
	}

	public String getFrameworkIdea() {
		return frameworkIdea;
	}

	public void setFrameworkIdea(String frameworkIdea) {
		this.frameworkIdea = frameworkIdea;
	}

	public String getCenterIdea() {
		return centerIdea;
	}

	public void setCenterIdea(String centerIdea) {
		this.centerIdea = centerIdea;
	}
    
}