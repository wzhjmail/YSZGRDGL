package com.wzj.pojo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

//import org.springframework.web.multipart.MultipartFile;

public class CompanyInfo {
	private int id;
	private String titleno;
	private String enterprisename;//��ҵ����
	private String englishname;//Ӣ������
	private String fax;//����
	private String address;//��ַ
	private String postalcode;//�ʱ�
	private String businessno;//Ӫҵִ�պ���
	private String enterprisekind;//��ҵ����
	private String registercapital;//ע���ʱ�
    private String artificialperson;//���˴���
    private String apjob;//����ְ��
    private String aptel;//���˵绰
    private String linkman;//��ϵ��
    private String ljob;//��ϵ��ְ��
    private String ltel;//��ϵ�˵绰
    private String mainbusiness;//��Ӫ
    private String restbusiness;//��Ӫ
    private Integer employeetotal;//ְ��������
    private Integer techniciantotal;//������Ա����
    private String bcprincipal;//����ӡˢ����������
    private String tpbusiness;//ְ��
    private String tpopost;//ְ��
    private Boolean flat=false;//ƽ�彺ӡ
    private Boolean gravure=false;//����ӡˢ
    private Boolean webby=false;//˿��ӡˢ 
    private Boolean flexible=false;//���԰�ӡˢ
    private String elsetype;//��������ӡˢ��������
    private Boolean papery=false;//ֽ��
    private Boolean pastern=false;//���ɽ�
    private Boolean frilling=false;//����ֽ 
    private Boolean metal=false;//����
    private Boolean plastic=false;//����
    private String elsematerial;//����ӡˢ�������
    private String offshootorganiz;//
    private String evaluatingresult;//
    private String certificateno;//ӡˢ��Ӫ���֤����
    private Date certappdate;//ӡˢ��Ӫ���֤��ʼ����
    private Date certtodate;//ӡˢ��Ӫ���֤��������
    private Date createdate;//����ʱ��
    private Integer isrepeat;//
    private Integer status;//״̬
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
    //����Ϊ�����ֶ�
    private String printEquipment;//��Ҫӡˢ�豸
    private String testEquipment;//�������豸
    private String remarks;//��ע
    //private MultipartFile business;//Ӫҵִ��
    //private MultipartFile certificate;//ӡˢ��Ӫ���֤
    private String branchId;//��֧����id
    //private String taskId;//����Id 
	private String branchName;//��֧�������� 
	private Integer serial;//��Ʒ����ӡˢ�ʸ�֤�����
	private int qualityno;//�����ֲ����
	private String realSerial;//��֤֤������λ
	
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
