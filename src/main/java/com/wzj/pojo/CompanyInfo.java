package com.wzj.pojo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

//import org.springframework.web.multipart.MultipartFile;

public class CompanyInfo {
	private int id;
	private String titleno;
	private String enterprisename;//企业名称
	private String englishname;//英文名称
	private String fax;//传真
	private String address;//地址
	private String postalcode;//邮编
	private String businessno;//营业执照号码
	private String enterprisekind;//企业性质
	private String registercapital;//注册资本
    private String artificialperson;//法人代表
    private String apjob;//法人职务
    private String aptel;//法人电话
    private String linkman;//联系人
    private String ljob;//联系人职务
    private String ltel;//联系人电话
    private String mainbusiness;//主营
    private String restbusiness;//兼营
    private Integer employeetotal;//职工人总数
    private Integer techniciantotal;//技术人员总数
    private String bcprincipal;//条码印刷技术负责人
    private String tpbusiness;//职务
    private String tpopost;//职称
    private Boolean flat=false;//平板胶印
    private Boolean gravure=false;//凹版印刷
    private Boolean webby=false;//丝网印刷 
    private Boolean flexible=false;//柔性版印刷
    private String elsetype;//其他条码印刷技术类型
    private Boolean papery=false;//纸质
    private Boolean pastern=false;//不干胶
    private Boolean frilling=false;//瓦楞纸 
    private Boolean metal=false;//金属
    private Boolean plastic=false;//塑料
    private String elsematerial;//其他印刷载体材料
    private String offshootorganiz;//
    private String evaluatingresult;//
    private String certificateno;//印刷经营许可证号码
    private Date certappdate;//印刷经营许可证开始日期
    private Date certtodate;//印刷经营许可证到期日期
    private Date createdate;//创建时间
    private Integer isrepeat;//
    private Integer status;//状态
    private String email;//
    private Date bakdate;//
    private String oldcertcode;//
    private Date cdate;//
    private Date appdate;//
    private String zhuxiao;//
    private Date zhuxiaodate;//
    private Date pdate;//
    private Integer lastid;//
    private String pressworkEquipment;
    private String checkEquipment;
    private String syndicResult;
    private MultipartFile quality;
    //以下为新增字段
    private String printEquipment;//主要印刷设备
    private String testEquipment;//条码检测设备
    private String remarks;//备注
    //private MultipartFile business;//营业执照
    //private MultipartFile certificate;//印刷经营许可证
    private String branchId;//分支机构id
    //private String taskId;//任务Id 
	private String branchName;//分支机构名称 
	private Integer serial;//商品条码印刷资格证书编码
	private int qualityno;//质量手册号码
	private String realSerial;//保证证书编号六位
	
	public String getRealSerial() {
		return realSerial;
	}
	public void setRealSerial(String realSerial) {
		this.realSerial = realSerial;
	}
	public int getQualityno() {
		return qualityno;
	}
	public void setQualityno(int qualityno) {
		this.qualityno = qualityno;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public int getId() {
		return id;
	}
	public String getPressworkEquipment() {
		return pressworkEquipment;
	}
	public void setPressworkEquipment(String pressworkEquipment) {
		this.pressworkEquipment = pressworkEquipment;
	}
	public String getCheckEquipment() {
		return checkEquipment;
	}
	public void setCheckEquipment(String checkEquipment) {
		this.checkEquipment = checkEquipment;
	}
	public String getSyndicResult() {
		return syndicResult;
	}
	public void setSyndicResult(String syndicResult) {
		this.syndicResult = syndicResult;
	}
	public MultipartFile getQuality() {
		return quality;
	}
	public void setQuality(MultipartFile quality) {
		this.quality = quality;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitleno() {
		return titleno;
	}
	public void setTitleno(String titleno) {
		this.titleno = titleno;
	}
	public String getEnterprisename() {
		return enterprisename;
	}
	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}
	public String getEnglishname() {
		return englishname;
	}
	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getBusinessno() {
		return businessno;
	}
	public void setBusinessno(String businessno) {
		this.businessno = businessno;
	}
	public String getEnterprisekind() {
		return enterprisekind;
	}
	public void setEnterprisekind(String enterprisekind) {
		this.enterprisekind = enterprisekind;
	}
	public String getRegistercapital() {
		return registercapital;
	}
	public void setRegistercapital(String registercapital) {
		this.registercapital = registercapital;
	}
	public String getArtificialperson() {
		return artificialperson;
	}
	public void setArtificialperson(String artificialperson) {
		this.artificialperson = artificialperson;
	}
	public String getApjob() {
		return apjob;
	}
	public void setApjob(String apjob) {
		this.apjob = apjob;
	}
	public String getAptel() {
		return aptel;
	}
	public void setAptel(String aptel) {
		this.aptel = aptel;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLjob() {
		return ljob;
	}
	public void setLjob(String ljob) {
		this.ljob = ljob;
	}
	public String getLtel() {
		return ltel;
	}
	public void setLtel(String ltel) {
		this.ltel = ltel;
	}
	public Integer getEmployeetotal() {
		return employeetotal;
	}
	public void setEmployeetotal(Integer employeetotal) {
		this.employeetotal = employeetotal;
	}
	public Integer getTechniciantotal() {
		return techniciantotal;
	}
	public void setTechniciantotal(Integer techniciantotal) {
		this.techniciantotal = techniciantotal;
	}
	public String getBcprincipal() {
		return bcprincipal;
	}
	public void setBcprincipal(String bcprincipal) {
		this.bcprincipal = bcprincipal;
	}
	public String getTpbusiness() {
		return tpbusiness;
	}
	public void setTpbusiness(String tpbusiness) {
		this.tpbusiness = tpbusiness;
	}
	public String getTpopost() {
		return tpopost;
	}
	public void setTpopost(String tpopost) {
		this.tpopost = tpopost;
	}
	public Boolean getFlat() {
		return flat;
	}
	public void setFlat(Boolean flat) {
		this.flat = flat;
	}
	public Boolean getGravure() {
		return gravure;
	}
	public void setGravure(Boolean gravure) {
		this.gravure = gravure;
	}
	public Boolean getWebby() {
		return webby;
	}
	public void setWebby(Boolean webby) {
		this.webby = webby;
	}
	public Boolean getFlexible() {
		return flexible;
	}
	public void setFlexible(Boolean flexible) {
		this.flexible = flexible;
	}
	public String getElsetype() {
		return elsetype;
	}
	public void setElsetype(String elsetype) {
		this.elsetype = elsetype;
	}
	public Boolean getPapery() {
		return papery;
	}
	public void setPapery(Boolean papery) {
		this.papery = papery;
	}
	public Boolean getPastern() {
		return pastern;
	}
	public void setPastern(Boolean pastern) {
		this.pastern = pastern;
	}
	public Boolean getFrilling() {
		return frilling;
	}
	public void setFrilling(Boolean frilling) {
		this.frilling = frilling;
	}
	public Boolean getMetal() {
		return metal;
	}
	public void setMetal(Boolean metal) {
		this.metal = metal;
	}
	public Boolean getPlastic() {
		return plastic;
	}
	public void setPlastic(Boolean plastic) {
		this.plastic = plastic;
	}
	public String getElsematerial() {
		return elsematerial;
	}
	public void setElsematerial(String elsematerial) {
		this.elsematerial = elsematerial;
	}
	public String getOffshootorganiz() {
		return offshootorganiz;
	}
	public void setOffshootorganiz(String offshootorganiz) {
		this.offshootorganiz = offshootorganiz;
	}
	public String getEvaluatingresult() {
		return evaluatingresult;
	}
	public void setEvaluatingresult(String evaluatingresult) {
		this.evaluatingresult = evaluatingresult;
	}
	public String getCertificateno() {
		return certificateno;
	}
	public void setCertificateno(String certificateno) {
		this.certificateno = certificateno;
	}
	public Date getCertappdate() {
		return certappdate;
	}
	public void setCertappdate(Date certappdate) {
		this.certappdate = certappdate;
	}
	public Date getCerttodate() {
		return certtodate;
	}
	public void setCerttodate(Date certtodate) {
		this.certtodate = certtodate;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Integer getIsrepeat() {
		return isrepeat;
	}
	public void setIsrepeat(Integer isrepeat) {
		this.isrepeat = isrepeat;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBakdate() {
		return bakdate;
	}
	public void setBakdate(Date bakdate) {
		this.bakdate = bakdate;
	}
	public String getOldcertcode() {
		return oldcertcode;
	}
	public void setOldcertcode(String oldcertcode) {
		this.oldcertcode = oldcertcode;
	}
	public Date getCdate() {
		return cdate;
	}
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public Date getAppdate() {
		return appdate;
	}
	public void setAppdate(Date appdate) {
		this.appdate = appdate;
	}
	public String getZhuxiao() {
		return zhuxiao;
	}
	public void setZhuxiao(String zhuxiao) {
		this.zhuxiao = zhuxiao;
	}
	public Date getZhuxiaodate() {
		return zhuxiaodate;
	}
	public void setZhuxiaodate(Date zhuxiaodate) {
		this.zhuxiaodate = zhuxiaodate;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
	public Integer getLastid() {
		return lastid;
	}
	public void setLastid(Integer lastid) {
		this.lastid = lastid;
	}
	public String getMainbusiness() {
		return mainbusiness;
	}
	public void setMainbusiness(String mainbusiness) {
		this.mainbusiness = mainbusiness;
	}
	public String getRestbusiness() {
		return restbusiness;
	}
	public void setRestbusiness(String restbusiness) {
		this.restbusiness = restbusiness;
	}
	public String getPrintEquipment() {
		return printEquipment;
	}
	public void setPrintEquipment(String printEquipment) {
		this.printEquipment = printEquipment;
	}
	public String getTestEquipment() {
		return testEquipment;
	}
	public void setTestEquipment(String testEquipment) {
		this.testEquipment = testEquipment;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
}
