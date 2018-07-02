package com.wzj.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class UploadFile implements Serializable{
    private Integer id;

    private Date uploadtime;

    private String uprul;

    private String upsize;

    private String code;

    private Boolean availability;

    private Short softtype;

    private String updescribe;
    
    private String describeId;
    
    private MultipartFile mfile; //¸½¼þ((ÐÂ½¨£¡
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getUprul() {
        return uprul;
    }

    public void setUprul(String uprul) {
        this.uprul = uprul == null ? null : uprul.trim();
    }

    public String getUpsize() {
        return upsize;
    }

    public void setUpsize(String upsize) {
        this.upsize = upsize == null ? null : upsize.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Short getSofttype() {
        return softtype;
    }

    public void setSofttype(Short softtype) {
        this.softtype = softtype;
    }

    public String getUpdescribe() {
        return updescribe;
    }

    public void setUpdescribe(String updescribe) {
        this.updescribe = updescribe == null ? null : updescribe.trim();
    }

	public String getDescribeId() {
		return describeId;
	}

	public void setDescribeId(String describeId) {
		this.describeId = describeId;
	}
    
    
	public MultipartFile getMfile() {
		return mfile;
	}

	public void setMfile(MultipartFile mfile) {
		this.mfile = mfile;
	}
}