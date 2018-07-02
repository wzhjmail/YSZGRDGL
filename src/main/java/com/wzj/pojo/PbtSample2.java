package com.wzj.pojo;

import java.util.Date;

public class PbtSample2{
	private String	f_sample_name; //检测对象
	private String	f_sample_code; //样品编号
	private String	uf_0047228; //样品名称
	private String	uf_0047251; //产品名称
	private String	uf_0047233; //条码号
	private String	uf_0047232; //条码类型
	private String uf_0047220;//商标
	private String	uf_0047218; //规格/包装
	private String	uf_0047331 ; //包装上条码的数量
	private String uf_0047231;//印刷载体
	private String uf_0047330;//应用场景
	private int	f_sample_count; //样品数量
	private String uf_0047335;//检测费用单价
	private String f_code_type;//样品编号类型
	private String f_batch_type;//批次编号类型
	private String uf_0047332;//样品状态描述
	private String f_batch_code;//样品批次编号
	private String uf_0047238;//送样人
	private String f_spot_code;//业务类别
	private String uf_0047234;//样品来源
	private Date uf_004711; //来样日期
	private String uf_0047141;//备注
	private String f_operation_key;//业务单唯一编号
	private String f_sample_key;//样品唯一编号
	private byte[] mfile;//样品附件
	private boolean hasRead;//是否已读
	private String wrong;//样品问题描述
	private String titleNo;//流水号
	private String filePath;//文件路径
	//委托单位信息:分支机构的信息
	private String brachName;//分支机构名称
	private String uf_004742;//联系人
	private String uf_004746;//邮箱
	private String uf_004744;//地址
	private String uf_004743;//电话
	private String postcode;//邮编
	//报告单位信息：企业信息
	private String uf_004761;//企业名称
	private String uf_004763;//联系人
	private String uf_004762;//地址
	private String uf_004764;//电话
	private String postalcode;//邮编
	
	private String uf_004757;//委托检验类别;表：004700854
	private String uf_004785;//检验项目，必填
	private String uf_004784;//报告数量，默认为'/'
	private String uf_004786;//检测报告依据
	private String uf_004791;//是否涉及法律纠纷。表：004702019
	private String uf_004778;//送样方式
	private String uf_004780;//保密要求004700856
	private String uf_004790;//检验周期，默认为‘/’
	private String uf_004779;//样品处置
	private String uf_004798;//报告扫描件，默认：‘需要’
	private String uf_004758;//报告交付，默认：‘自取’
	private String uf_004787;//付款单位和人,默认为‘/’
	private String uf_004759;//付款方式,默认为‘/’
	private String uf_004788;//发票类型,默认为‘/’
	//发票信息
	private String uf_004774;//发票单位名称,默认为‘/’
	private String uf_004789;//纳税人识别号,默认为‘/’
	private String uf_004794;//发票地址和电话,默认为‘/’
	private String uf_004795;//开户行以及账号,默认为‘/’
	
	private String uf_004760;//检验费小计,默认为‘/’
	private String uf_004773;//邮寄费,默认为‘/’
	private String uf_004782;//其它费用,默认为‘/’
	
	public String getBrachName() {
		return brachName;
	}
	public void setBrachName(String brachName) {
		this.brachName = brachName;
	}
	public String getUf_004742() {
		return uf_004742;
	}
	public void setUf_004742(String uf_004742) {
		this.uf_004742 = uf_004742;
	}
	public String getUf_004744() {
		return uf_004744;
	}
	public void setUf_004744(String uf_004744) {
		this.uf_004744 = uf_004744;
	}
	public String getUf_004743() {
		return uf_004743;
	}
	public void setUf_004743(String uf_004743) {
		this.uf_004743 = uf_004743;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getUf_004761() {
		return uf_004761;
	}
	public void setUf_004761(String uf_004761) {
		this.uf_004761 = uf_004761;
	}
	public String getUf_004763() {
		return uf_004763;
	}
	public String getUf_004746() {
		return uf_004746;
	}
	public void setUf_004746(String uf_004746) {
		this.uf_004746 = uf_004746;
	}
	public void setUf_004763(String uf_004763) {
		this.uf_004763 = uf_004763;
	}
	public String getUf_004762() {
		return uf_004762;
	}
	public void setUf_004762(String uf_004762) {
		this.uf_004762 = uf_004762;
	}
	public String getUf_004764() {
		return uf_004764;
	}
	public void setUf_004764(String uf_004764) {
		this.uf_004764 = uf_004764;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getUf_004757() {
		return uf_004757;
	}
	public void setUf_004757(String uf_004757) {
		this.uf_004757 = uf_004757;
	}
	public String getUf_004785() {
		return uf_004785;
	}
	public void setUf_004785(String uf_004785) {
		this.uf_004785 = uf_004785;
	}
	public String getUf_004784() {
		return uf_004784;
	}
	public void setUf_004784(String uf_004784) {
		this.uf_004784 = uf_004784;
	}
	public String getUf_004786() {
		return uf_004786;
	}
	public void setUf_004786(String uf_004786) {
		this.uf_004786 = uf_004786;
	}
	public String getUf_004791() {
		return uf_004791;
	}
	public void setUf_004791(String uf_004791) {
		this.uf_004791 = uf_004791;
	}
	public String getUf_004778() {
		return uf_004778;
	}
	public void setUf_004778(String uf_004778) {
		this.uf_004778 = uf_004778;
	}
	public String getUf_004780() {
		return uf_004780;
	}
	public void setUf_004780(String uf_004780) {
		this.uf_004780 = uf_004780;
	}
	public String getUf_004790() {
		return uf_004790;
	}
	public void setUf_004790(String uf_004790) {
		this.uf_004790 = uf_004790;
	}
	public String getUf_004779() {
		return uf_004779;
	}
	public void setUf_004779(String uf_004779) {
		this.uf_004779 = uf_004779;
	}
	public String getUf_004798() {
		return uf_004798;
	}
	public void setUf_004798(String uf_004798) {
		this.uf_004798 = uf_004798;
	}
	public String getUf_004758() {
		return uf_004758;
	}
	public void setUf_004758(String uf_004758) {
		this.uf_004758 = uf_004758;
	}
	public String getUf_004787() {
		return uf_004787;
	}
	public void setUf_004787(String uf_004787) {
		this.uf_004787 = uf_004787;
	}
	public String getUf_004759() {
		return uf_004759;
	}
	public void setUf_004759(String uf_004759) {
		this.uf_004759 = uf_004759;
	}
	public String getUf_004788() {
		return uf_004788;
	}
	public void setUf_004788(String uf_004788) {
		this.uf_004788 = uf_004788;
	}
	public String getUf_004774() {
		return uf_004774;
	}
	public void setUf_004774(String uf_004774) {
		this.uf_004774 = uf_004774;
	}
	public String getUf_004789() {
		return uf_004789;
	}
	public void setUf_004789(String uf_004789) {
		this.uf_004789 = uf_004789;
	}
	public String getUf_004794() {
		return uf_004794;
	}
	public void setUf_004794(String uf_004794) {
		this.uf_004794 = uf_004794;
	}
	public String getUf_004795() {
		return uf_004795;
	}
	public void setUf_004795(String uf_004795) {
		this.uf_004795 = uf_004795;
	}
	public String getUf_004760() {
		return uf_004760;
	}
	public void setUf_004760(String uf_004760) {
		this.uf_004760 = uf_004760;
	}
	public String getUf_004773() {
		return uf_004773;
	}
	public void setUf_004773(String uf_004773) {
		this.uf_004773 = uf_004773;
	}
	public String getUf_004782() {
		return uf_004782;
	}
	public void setUf_004782(String uf_004782) {
		this.uf_004782 = uf_004782;
	}
	public String getUf_004781() {
		return uf_004781;
	}
	public void setUf_004781(String uf_004781) {
		this.uf_004781 = uf_004781;
	}
	private String uf_004781;//总费用,默认为‘/’
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getF_sample_name() {
		return f_sample_name;
	}
	public void setF_sample_name(String f_sample_name) {
		this.f_sample_name = f_sample_name;
	}
	public String getF_sample_code() {
		return f_sample_code;
	}
	public void setF_sample_code(String f_sample_code) {
		this.f_sample_code = f_sample_code;
	}
	public String getUf_0047228() {
		return uf_0047228;
	}
	public void setUf_0047228(String uf_0047228) {
		this.uf_0047228 = uf_0047228;
	}
	public String getUf_0047251() {
		return uf_0047251;
	}
	public void setUf_0047251(String uf_0047251) {
		this.uf_0047251 = uf_0047251;
	}
	public String getUf_0047233() {
		return uf_0047233;
	}
	public void setUf_0047233(String uf_0047233) {
		this.uf_0047233 = uf_0047233;
	}
	public String getUf_0047232() {
		return uf_0047232;
	}
	public void setUf_0047232(String uf_0047232) {
		this.uf_0047232 = uf_0047232;
	}
	public String getUf_0047220() {
		return uf_0047220;
	}
	public void setUf_0047220(String uf_0047220) {
		this.uf_0047220 = uf_0047220;
	}
	public String getUf_0047218() {
		return uf_0047218;
	}
	public void setUf_0047218(String uf_0047218) {
		this.uf_0047218 = uf_0047218;
	}
	public String getUf_0047331() {
		return uf_0047331;
	}
	public void setUf_0047331(String uf_0047331) {
		this.uf_0047331 = uf_0047331;
	}
	public String getUf_0047231() {
		return uf_0047231;
	}
	public void setUf_0047231(String uf_0047231) {
		this.uf_0047231 = uf_0047231;
	}
	public String getUf_0047330() {
		return uf_0047330;
	}
	public void setUf_0047330(String uf_0047330) {
		this.uf_0047330 = uf_0047330;
	}
	public int getF_sample_count() {
		return f_sample_count;
	}
	public void setF_sample_count(int f_sample_count) {
		this.f_sample_count = f_sample_count;
	}
	public String getUf_0047335() {
		return uf_0047335;
	}
	public void setUf_0047335(String uf_0047335) {
		this.uf_0047335 = uf_0047335;
	}
	public String getF_code_type() {
		return f_code_type;
	}
	public void setF_code_type(String f_code_type) {
		this.f_code_type = f_code_type;
	}
	public String getF_batch_type() {
		return f_batch_type;
	}
	public void setF_batch_type(String f_batch_type) {
		this.f_batch_type = f_batch_type;
	}
	public String getUf_0047332() {
		return uf_0047332;
	}
	public void setUf_0047332(String uf_0047332) {
		this.uf_0047332 = uf_0047332;
	}
	public String getF_batch_code() {
		return f_batch_code;
	}
	public void setF_batch_code(String f_batch_code) {
		this.f_batch_code = f_batch_code;
	}
	public String getUf_0047238() {
		return uf_0047238;
	}
	public void setUf_0047238(String uf_0047238) {
		this.uf_0047238 = uf_0047238;
	}
	public String getF_spot_code() {
		return f_spot_code;
	}
	public void setF_spot_code(String f_spot_code) {
		this.f_spot_code = f_spot_code;
	}
	public String getUf_0047234() {
		return uf_0047234;
	}
	public void setUf_0047234(String uf_0047234) {
		this.uf_0047234 = uf_0047234;
	}
	public Date getUf_004711() {
		return uf_004711;
	}
	public void setUf_004711(Date uf_004711) {
		this.uf_004711 = uf_004711;
	}
	public String getUf_0047141() {
		return uf_0047141;
	}
	public void setUf_0047141(String uf_0047141) {
		this.uf_0047141 = uf_0047141;
	}
	public String getF_operation_key() {
		return f_operation_key;
	}
	public void setF_operation_key(String f_operation_key) {
		this.f_operation_key = f_operation_key;
	}
	public String getF_sample_key() {
		return f_sample_key;
	}
	public void setF_sample_key(String f_sample_key) {
		this.f_sample_key = f_sample_key;
	}
	public byte[] getMfile() {
		return mfile;
	}
	public void setMfile(byte[] mfile) {
		this.mfile = mfile;
	}
	public boolean isHasRead() {
		return hasRead;
	}
	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}
	public String getWrong() {
		return wrong;
	}
	public void setWrong(String wrong) {
		this.wrong = wrong;
	}
	public String getTitleNo() {
		return titleNo;
	}
	public void setTitleNo(String titleNo) {
		this.titleNo = titleNo;
	}
}