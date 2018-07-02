package com.wzj.pojo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class PbtSample{
	private String	f_sample_name; //	B检测对象
	private String	uf_0047233; //	B条码号
	private String	uf_0047232; //	B条码类型
	private String	uf_0047330; //	B应用场景
	private String	uf_0047234; //	B样品来源
	private int	f_sample_count; //	B样品数量
	private String	uf_0047236; //	B保密要求
	private String	f_batch_type; //	B批次编号类型
	private String	uf_0047238; //	B送样人
	private String	uf_0047220; //	B商标
	private String	uf_0047331 ; //	B包装上条码的数量
	
	private String	uf_0047228; //	B样品名称
	private String  uf_sample_code; //  B样品编号((新建！
	private String	uf_0047251; //	B产品描述
	private String  uf_code_type;  //  B样品编号类型((新建！
	private Date	uf_004711; //	B来样日期
	private String	uf_0047332 ; //	B样品状态描述
	private String	uf_0047231; //	B印刷载体
	private String	f_batch_code; //	B样品批次编号
	private String	f_spot_code; //	B业务类别
	private String	uf_0047218; //	B型号规格
	private String	uf_0047250; //	B样品备注
	
	private String	uf_0047225; //	B厂商
	private String uf_attach; //附件路径((新建！
	private MultipartFile mfile; //附件((新建！
	private String uf_report;//检测报告路径((新建!
	private boolean uf_result;//检测报告审核结论((新建!
	
	private String	f_add_control; //暂定为：是否已读。0未读，1已读
	private String	f_bzpbh; //暂定为：样品问题描述
	
	private String	f_appoint_lab; //	指定检测室
	private String	f_appoint_person; //
	private String	f_appoint_rose; //
	private String	f_area; //
	private Date	f_archives_date; //
	private Date	f_auditing_date; //	样品一审人时间
	private String	f_auditing_person; //	样品一审人
	private String	f_bar_code; //	条形码数据
	private String	f_batch_jmcode; //	F样品批次加密编号
	private String	f_batch_jmtype; //	F样品批次加密编号类型
	private int	f_batch_multiple; //	F样品批次倍数
	private String	f_code_type; //	O样品编号类型
	private Date	f_commit_date; //	样品提交日期
	private String	f_commit_person; //	样品提交人
	private String	f_compute_date; //	登记日期统计时间
	private String	f_compute_day; //	登记日期统计日
	private String	f_compute_month; //	登记日期统计月
	private String	f_compute_week; //	登记日期统计周
	private String	f_compute_year; //	登记日期统计年
	private String	f_control_gx; //
	private Date	f_csample_date; //	清样日期
	private String	f_csample_person; //	清样人
	private String	f_csample_result; //
	private String	f_display_date; //	登记日期(交叉左表头)
	private String	f_display_day; //	登记日期日(交叉左表头)
	private String	f_display_month; //	登记日期月(交叉左表头)
	private String	f_display_week; //	登记日期周(交叉左表头)
	private String	f_display_year; //	登记日期年(交叉左表头)
	private Date	f_docsample_date; //	应清样日期
	private String	f_encrypt_code; //	F用户样品加密编号
	private String	f_encrypt_person; //	加密编号操作人
	private String	f_encrypt_tag; //
	private Date	f_encrypt_date; //
	private String	f_enter_library; //
	private String	f_end_code; //
	private String	f_end_state; //
	private String	f_error_count; //
	private Date	f_final_date; //	样品二审时间
	private String	f_final_person; //	样品二审人
	private String	f_gather_person; //
	private String	f_ggxx_findcode; //	型号识别码
	private String	f_gsjq_item; //
	private String	f_gsjq_parm; //	过筛加权参数
	private String	f_gsjq_value; //	过筛加权系数
	private String	f_hand_just; //
	private Date	f_inlib_date; //
	private String	f_inlib_person; //
	private String	f_is_pp; //
	private Date	f_jq_date; //	样品加权时间
	private String	f_jq_item; //
	private String	f_jq_parm; //	加权系数
	private String	f_jq_person; //	样品加权人
	private String	f_jq_sample; //
	private String	f_jq_state; //
	private String	f_jq_value; //	加权参数
	private String	f_level1; //
	private String	f_level2; //
	private String	f_level3; //
	private String	f_level4; //
	private String	f_library_count; //	样品库存量
	private String	f_library_state; //	样品出入库状态
	private String	f_main_ppsample; //
	private String	f_name_code; //	样品名称代码(查询条件)
	private String	f_old_sample_code; //	样品原编号
	private String	f_operation_dept; //
	private String	f_operation_person; //
	private String	f_temp_tag; //
	private String	f_operation_key; //
	private String	f_operation_code; //	业务单编号
	private String	f_person_name; //	采样人
	private String	f_plan_code; //	
	private String	f_poto_a; //	样品照片上面
	private String	f_poto_b; //	样品照片下面
	private String	f_poto_c; //	样品照片前面
	private String	f_poto_d; //	样品照片后面
	private String	f_poto_e; //	样品照片左侧
	private String	f_poto_f; //	样品照片右侧
	private Date	f_pp_date; //	样品配批时间
	private String	f_pp_parm; //	F配批批号
	private String	f_pp_person; //	样品配批人
	private String	f_pp_sample; //	
	private String	f_pp_state; //
	private String	f_produce_stand; //	产品标准
	private String	f_produce_type; //	样品类别
	private String	f_quality_type; //
	private String	f_rebuild_tag; //
	private String	f_rebuild_oldkey; //
	private String	f_rectify_tag; //
	private String	f_rectify_sample; //
	private String	f_ryjq_item; //
	private String	f_ryjq_parm; //	熔样加权参数
	private String	f_ryjq_value; //	熔样加权系数
	private String	f_sample_code; //	F样品编号
	private Date	f_sample_date; //	F样品登记日期
	private String	f_sample_explain; //	产品等级
	private String	f_sample_key; //	提取样品数据条件
	private String	f_sample_level; //	对象指标等级判断（初始化)
	private String	f_sample_new; //
	private String	f_sample_person; //	F样品登记人
	private String	f_sample_state; //	样品流程状态
	private String	f_sample_template; //	样品模板
	private String	f_samplefj; //	样品附件
	private String	f_sampleitems; //	样品项目统计
	private String	f_sampletemp_code; //
	private Date	f_sampling_date; //	采样时间111
	private String	f_sampling_person; //	采样人代码
	private String	f_sampling_site; //	计量地点
	private String	f_save_sample; //	留样标志
	private String	f_sh_bdtag; //	比对样品标志
	private String	f_sh_sample; //	比对样品标志
	private String	f_spot_name; //
	private String	f_start_isvaild; //	样品统计是否有效
	private Date	f_test_date; //
	private String	f_testlib_out; //
	private String	f_temp_key; //
	private String	f_tacceptperson; //
	private Date	f_tdateperson; //
	private String	f_tdispersons; //
	private String	f_tsendperson; //
	private String	f_unusual_state; //
	private int	f_untread_count; //
	private String	f_untread_person; //
	private Date	f_untread_date; //
	private String	f_workflow_code; //	流程名称
	private String	f_year_week; //	登记日期本年度第几周
	private String	uf_00471; //	H样品数量
	private String	uf_0047126; //	修改
	private String	uf_0047141; //	F备注
	private String	uf_0047145; //	F供货商
	private String	uf_0047158; //	F出厂编号
	private String	uf_0047160; //	F处理环节
	private String	uf_0047161; //	F采购单位
	private String	uf_0047162; //	F样品性质
	private String	uf_0047163; //	F产地
	private String	uf_0047164; //	F包装方式
	private String	uf_0047165; //	F外商是否参与
	private String	uf_0047166; //	F组批方式
	private String	uf_0047167; //	F运输方式
	private String	uf_0047168; //	F班次
	private int	uf_0047169; //	F车数
	private String	uf_0047170; //	F去向厂矿名称
	private String	uf_0047171; //	F来源厂矿名称
	private String	uf_0047172; //	F去向生产系统名称
	private String	uf_0047173; //	F来源生产系统名称
	private String	uf_0047174; //	F去向车间名称
	private String	uf_0047175; //	F去向工序名称
	private String	uf_0047176; //	F来源工序名称
	private String	uf_0047177; //	F批号
	private String	uf_0047178; //	F来源车间名称
	private String	uf_0047179; //	F采购类别
	private String	uf_0047182; //	F总数（片/见/块）
	private String	uf_0047192; //	F外观等级初判
	private String	uf_0047194; //	F样品类别
	private String	uf_00472; //	剩余量
	private String	uf_0047208; //	F班别
	private String	uf_0047211; //	采样车号
	private String	uf_0047212; //	测量点
	private String	uf_0047213; //	测量次数
	private String	uf_0047214; //	抽样编号
	private String	uf_0047215; //	船名
	private String	uf_0047216; //	仓号
	private Date	uf_0047217; //	采样时间
	private String	uf_0047221; //	O样品状况
	private String	uf_0047226; //	O制造商
	private String	uf_0047227; //	O产品类别
	private String	uf_0047230; //	附件
	private String	uf_0047235; //	B抽样方式
	private String	uf_0047237; //	B样品返还
	private String	uf_0047239; //	B仪器状态
	private String	uf_0047240; //	B出厂编号
	private String	uf_0047241; //	B自校准板编号
	private String	uf_0047242; //	设备状态
	private String	uf_0047243; //	B软件版本号
	private String	uf_0047244; //	B附件清单
	private String	uf_0047245; //	B一致性校准选择
	private String	uf_0047246; //	B检定校准选择
	private String	uf_0047247; //	b调整仪器参数
	private String	uf_0047248; //	B仪器及附件外观
	private String	uf_0047249; //	b设备状态
	private String	uf_0047252; //	B商品名称
	private String	uf_0047253; //	B温度-条码仪
	private String	uf_0047254; //	B湿度-条码仪
	private String	uf_0047255; //	X符号等级
	private String	uf_0047256; //	X空白区尺寸左
	private String	uf_0047257; //	X空白区尺寸右
	private String	uf_0047258; //	X条高
	private String	uf_0047259; //	X译码数据
	private String	uf_0047260; //	XZ尺寸
	private String	uf_0047261; //	X放大系数
	private String	uf_0047262; //	X印刷位置
	private String	uf_0047263; //	X商品代码有效性
	private String	uf_0047264; //	X编码唯一性
	private String	uf_0047265; //	X条码类型
	private String	uf_0047266; //	X参考译码
	private String	uf_0047267; //	X最低最高反射率
	private String	uf_0047268; //	X符号反差
	private String	uf_0047269; //	X最小边缘反差
	private String	uf_0047270; //	X调制比
	private String	uf_0047271; //	X缺陷度
	private String	uf_0047272; //	X可译码度
	private String	uf_0047273; //	X符号等级P
	private String	uf_0047274; //	X空白区尺寸左P
	private String	uf_0047275; //	X空白区尺寸右P
	private String	uf_0047276; //	X条高P
	private String	uf_0047277; //	X译码数据P
	private String	uf_0047278; //	XZ尺寸P
	private String	uf_0047279; //	X放大系数P
	private String	uf_0047280; //	X印刷位置P
	private String	uf_0047281; //	X商品代码有效性P
	private String	uf_0047282; //	X编码唯一性P
	private String	uf_0047283; //	X条码类型P
	private String	uf_0047284; //	X参考译码P
	private String	uf_0047285; //	X最低最高反射率P
	private String	uf_0047286; //	X符号反差P
	private String	uf_0047287; //	X最小边缘反差P
	private String	uf_0047288; //	X调制比P
	private String	uf_0047289; //	X缺陷度P
	private String	uf_0047290; //	X可译码度P
	private String	uf_0047291; //	Y温度
	private String	uf_0047292; //	Y湿度
	private String	uf_0047293; //	X符号等级J
	private String	uf_0047294; //	X空白区尺寸左J
	private String	uf_0047295; //	X空白区尺寸右J
	private String	uf_0047296; //	X条高J
	private String	uf_0047298; //	XZ尺寸J
	private String	uf_0047299; //	X放大系数J
	private String	uf_00473; //	出库量
	private String	uf_0047300; //	X印刷位置J
	private String	uf_0047301; //	X商品代码有效性J
	private String	uf_0047302; //	X编码唯一性J
	private String	uf_0047303; //	X条码类型J
	private String	uf_0047304; //	X参考译码J
	private String	uf_0047305; //	X最低最高反射率J
	private String	uf_0047306; //	X符号反差J
	private String	uf_0047307; //	X最小边缘反差J
	private String	uf_0047308; //	X调制比J
	private String	uf_0047309; //	X缺陷度J
	private String	uf_0047310; //	X可译码度J
	private String	uf_0047312; //	X符号等级B
	private String	uf_0047313; //	X空白区尺寸左B
	private String	uf_0047314; //	X空白区尺寸右B
	private String	uf_0047315; //	X条高B
	private String	uf_0047316; //	X译码数据B
	private String	uf_0047317; //	XZ尺寸B
	private String	uf_0047318; //	X放大系数B
	private String	uf_0047319; //	X印刷位置B
	private String	uf_0047320; //	X商品代码有效性B
	private String	uf_0047321; //	X编码唯一性B
	private String	uf_0047322; //	X条码类型B
	private String	uf_0047323; //	X参考译码B
	private String	uf_0047324; //	X最低最高反射率B
	private String	uf_0047325; //	X符号反差B
	private String	uf_0047326; //	X最小边缘反差B
	private String	uf_0047327; //	X调制比B
	private String	uf_0047328; //	X缺陷度B
	private String	uf_0047329; //	X可译码度B
	private String	uf_00474; //	F型号规格
	private String	uf_00479; //	T样品状态（废除不能用）
	private String	uf_05281 ; //	X宽窄比
	private String	uf_052810 ; //	X校验码及条码校验符B
	private String	uf_052811 ; //	X校验码及条码校验符J
	private String	uf_052812 ; //	X校验码及条码校验符P
	private String	uf_05282 ; //	X宽窄比B
	private String	uf_05283 ; //	X宽窄比J
	private String	uf_05284 ; //	X宽窄比P
	private String	uf_05285 ; //	X保护框
	private String	uf_05286 ; //	X保护框B
	private String	uf_05287 ; //	X保护框J
	private String	uf_05288 ; //	X保护框P
	private String	uf_05289 ; //	X校验码及条码校验符
	private String	uf_10001; //	样品图片
	
	public boolean getUf_result(){
		return uf_result;
	}
	
	public void setUf_result(boolean uf_result){
		this.uf_result = uf_result;
	}
	
	public String getF_add_control() {
		return f_add_control;
	}
	public void setF_add_control(String f_add_control) {
		this.f_add_control = f_add_control;
	}
	public String getF_appoint_lab() {
		return f_appoint_lab;
	}
	public void setF_appoint_lab(String f_appoint_lab) {
		this.f_appoint_lab = f_appoint_lab;
	}
	public String getF_appoint_person() {
		return f_appoint_person;
	}
	public void setF_appoint_person(String f_appoint_person) {
		this.f_appoint_person = f_appoint_person;
	}
	public String getF_appoint_rose() {
		return f_appoint_rose;
	}
	public void setF_appoint_rose(String f_appoint_rose) {
		this.f_appoint_rose = f_appoint_rose;
	}
	public String getF_area() {
		return f_area;
	}
	public void setF_area(String f_area) {
		this.f_area = f_area;
	}
	public Date getF_archives_date() {
		return f_archives_date;
	}
	public void setF_archives_date(Date f_archives_date) {
		this.f_archives_date = f_archives_date;
	}
	public Date getF_auditing_date() {
		return f_auditing_date;
	}
	public void setF_auditing_date(Date f_auditing_date) {
		this.f_auditing_date = f_auditing_date;
	}
	public String getF_auditing_person() {
		return f_auditing_person;
	}
	public void setF_auditing_person(String f_auditing_person) {
		this.f_auditing_person = f_auditing_person;
	}
	public String getF_bar_code() {
		return f_bar_code;
	}
	public void setF_bar_code(String f_bar_code) {
		this.f_bar_code = f_bar_code;
	}
	public String getF_batch_code() {
		return f_batch_code;
	}
	public void setF_batch_code(String f_batch_code) {
		this.f_batch_code = f_batch_code;
	}
	public String getF_batch_jmcode() {
		return f_batch_jmcode;
	}
	public void setF_batch_jmcode(String f_batch_jmcode) {
		this.f_batch_jmcode = f_batch_jmcode;
	}
	public String getF_batch_jmtype() {
		return f_batch_jmtype;
	}
	public void setF_batch_jmtype(String f_batch_jmtype) {
		this.f_batch_jmtype = f_batch_jmtype;
	}
	public int getF_batch_multiple() {
		return f_batch_multiple;
	}
	public void setF_batch_multiple(int f_batch_multiple) {
		this.f_batch_multiple = f_batch_multiple;
	}
	public String getF_batch_type() {
		return f_batch_type;
	}
	public void setF_batch_type(String f_batch_type) {
		this.f_batch_type = f_batch_type;
	}
	public String getF_bzpbh() {
		return f_bzpbh;
	}
	public void setF_bzpbh(String f_bzpbh) {
		this.f_bzpbh = f_bzpbh;
	}
	public String getF_code_type() {
		return f_code_type;
	}
	public void setF_code_type(String f_code_type) {
		this.f_code_type = f_code_type;
	}
	public Date getF_commit_date() {
		return f_commit_date;
	}
	public void setF_commit_date(Date f_commit_date) {
		this.f_commit_date = f_commit_date;
	}
	public String getF_commit_person() {
		return f_commit_person;
	}
	public void setF_commit_person(String f_commit_person) {
		this.f_commit_person = f_commit_person;
	}
	public String getF_compute_date() {
		return f_compute_date;
	}
	public void setF_compute_date(String f_compute_date) {
		this.f_compute_date = f_compute_date;
	}
	public String getF_compute_day() {
		return f_compute_day;
	}
	public void setF_compute_day(String f_compute_day) {
		this.f_compute_day = f_compute_day;
	}
	public String getF_compute_month() {
		return f_compute_month;
	}
	public void setF_compute_month(String f_compute_month) {
		this.f_compute_month = f_compute_month;
	}
	public String getF_compute_week() {
		return f_compute_week;
	}
	public void setF_compute_week(String f_compute_week) {
		this.f_compute_week = f_compute_week;
	}
	public String getF_compute_year() {
		return f_compute_year;
	}
	public void setF_compute_year(String f_compute_year) {
		this.f_compute_year = f_compute_year;
	}
	public String getF_control_gx() {
		return f_control_gx;
	}
	public void setF_control_gx(String f_control_gx) {
		this.f_control_gx = f_control_gx;
	}
	public Date getF_csample_date() {
		return f_csample_date;
	}
	public void setF_csample_date(Date f_csample_date) {
		this.f_csample_date = f_csample_date;
	}
	public String getF_csample_person() {
		return f_csample_person;
	}
	public void setF_csample_person(String f_csample_person) {
		this.f_csample_person = f_csample_person;
	}
	public String getF_csample_result() {
		return f_csample_result;
	}
	public void setF_csample_result(String f_csample_result) {
		this.f_csample_result = f_csample_result;
	}
	public String getF_display_date() {
		return f_display_date;
	}
	public void setF_display_date(String f_display_date) {
		this.f_display_date = f_display_date;
	}
	public String getF_display_day() {
		return f_display_day;
	}
	public void setF_display_day(String f_display_day) {
		this.f_display_day = f_display_day;
	}
	public String getF_display_month() {
		return f_display_month;
	}
	public void setF_display_month(String f_display_month) {
		this.f_display_month = f_display_month;
	}
	public String getF_display_week() {
		return f_display_week;
	}
	public void setF_display_week(String f_display_week) {
		this.f_display_week = f_display_week;
	}
	public String getF_display_year() {
		return f_display_year;
	}
	public void setF_display_year(String f_display_year) {
		this.f_display_year = f_display_year;
	}
	public Date getF_docsample_date() {
		return f_docsample_date;
	}
	public void setF_docsample_date(Date f_docsample_date) {
		this.f_docsample_date = f_docsample_date;
	}
	public String getF_encrypt_code() {
		return f_encrypt_code;
	}
	public void setF_encrypt_code(String f_encrypt_code) {
		this.f_encrypt_code = f_encrypt_code;
	}
	public String getF_encrypt_person() {
		return f_encrypt_person;
	}
	public void setF_encrypt_person(String f_encrypt_person) {
		this.f_encrypt_person = f_encrypt_person;
	}
	public String getF_encrypt_tag() {
		return f_encrypt_tag;
	}
	public void setF_encrypt_tag(String f_encrypt_tag) {
		this.f_encrypt_tag = f_encrypt_tag;
	}
	public Date getF_encrypt_date() {
		return f_encrypt_date;
	}
	public void setF_encrypt_date(Date f_encrypt_date) {
		this.f_encrypt_date = f_encrypt_date;
	}
	public String getF_enter_library() {
		return f_enter_library;
	}
	public void setF_enter_library(String f_enter_library) {
		this.f_enter_library = f_enter_library;
	}
	public String getF_end_code() {
		return f_end_code;
	}
	public void setF_end_code(String f_end_code) {
		this.f_end_code = f_end_code;
	}
	public String getF_end_state() {
		return f_end_state;
	}
	public void setF_end_state(String f_end_state) {
		this.f_end_state = f_end_state;
	}
	public String getF_error_count() {
		return f_error_count;
	}
	public void setF_error_count(String f_error_count) {
		this.f_error_count = f_error_count;
	}
	public Date getF_final_date() {
		return f_final_date;
	}
	public void setF_final_date(Date f_final_date) {
		this.f_final_date = f_final_date;
	}
	public String getF_final_person() {
		return f_final_person;
	}
	public void setF_final_person(String f_final_person) {
		this.f_final_person = f_final_person;
	}
	public String getF_gather_person() {
		return f_gather_person;
	}
	public void setF_gather_person(String f_gather_person) {
		this.f_gather_person = f_gather_person;
	}
	public String getF_ggxx_findcode() {
		return f_ggxx_findcode;
	}
	public void setF_ggxx_findcode(String f_ggxx_findcode) {
		this.f_ggxx_findcode = f_ggxx_findcode;
	}
	public String getF_gsjq_item() {
		return f_gsjq_item;
	}
	public void setF_gsjq_item(String f_gsjq_item) {
		this.f_gsjq_item = f_gsjq_item;
	}
	public String getF_gsjq_parm() {
		return f_gsjq_parm;
	}
	public void setF_gsjq_parm(String f_gsjq_parm) {
		this.f_gsjq_parm = f_gsjq_parm;
	}
	public String getF_gsjq_value() {
		return f_gsjq_value;
	}
	public void setF_gsjq_value(String f_gsjq_value) {
		this.f_gsjq_value = f_gsjq_value;
	}
	public String getF_hand_just() {
		return f_hand_just;
	}
	public void setF_hand_just(String f_hand_just) {
		this.f_hand_just = f_hand_just;
	}
	public Date getF_inlib_date() {
		return f_inlib_date;
	}
	public void setF_inlib_date(Date f_inlib_date) {
		this.f_inlib_date = f_inlib_date;
	}
	public String getF_inlib_person() {
		return f_inlib_person;
	}
	public void setF_inlib_person(String f_inlib_person) {
		this.f_inlib_person = f_inlib_person;
	}
	public String getF_is_pp() {
		return f_is_pp;
	}
	public void setF_is_pp(String f_is_pp) {
		this.f_is_pp = f_is_pp;
	}
	public Date getF_jq_date() {
		return f_jq_date;
	}
	public void setF_jq_date(Date f_jq_date) {
		this.f_jq_date = f_jq_date;
	}
	public String getF_jq_item() {
		return f_jq_item;
	}
	public void setF_jq_item(String f_jq_item) {
		this.f_jq_item = f_jq_item;
	}
	public String getF_jq_parm() {
		return f_jq_parm;
	}
	public void setF_jq_parm(String f_jq_parm) {
		this.f_jq_parm = f_jq_parm;
	}
	public String getF_jq_person() {
		return f_jq_person;
	}
	public void setF_jq_person(String f_jq_person) {
		this.f_jq_person = f_jq_person;
	}
	public String getF_jq_sample() {
		return f_jq_sample;
	}
	public void setF_jq_sample(String f_jq_sample) {
		this.f_jq_sample = f_jq_sample;
	}
	public String getF_jq_state() {
		return f_jq_state;
	}
	public void setF_jq_state(String f_jq_state) {
		this.f_jq_state = f_jq_state;
	}
	public String getF_jq_value() {
		return f_jq_value;
	}
	public void setF_jq_value(String f_jq_value) {
		this.f_jq_value = f_jq_value;
	}
	public String getF_level1() {
		return f_level1;
	}
	public void setF_level1(String f_level1) {
		this.f_level1 = f_level1;
	}
	public String getF_level2() {
		return f_level2;
	}
	public void setF_level2(String f_level2) {
		this.f_level2 = f_level2;
	}
	public String getF_level3() {
		return f_level3;
	}
	public void setF_level3(String f_level3) {
		this.f_level3 = f_level3;
	}
	public String getF_level4() {
		return f_level4;
	}
	public void setF_level4(String f_level4) {
		this.f_level4 = f_level4;
	}
	public String getF_library_count() {
		return f_library_count;
	}
	public void setF_library_count(String f_library_count) {
		this.f_library_count = f_library_count;
	}
	public String getF_library_state() {
		return f_library_state;
	}
	public void setF_library_state(String f_library_state) {
		this.f_library_state = f_library_state;
	}
	public String getF_main_ppsample() {
		return f_main_ppsample;
	}
	public void setF_main_ppsample(String f_main_ppsample) {
		this.f_main_ppsample = f_main_ppsample;
	}
	public String getF_name_code() {
		return f_name_code;
	}
	public void setF_name_code(String f_name_code) {
		this.f_name_code = f_name_code;
	}
	public String getF_old_sample_code() {
		return f_old_sample_code;
	}
	public void setF_old_sample_code(String f_old_sample_code) {
		this.f_old_sample_code = f_old_sample_code;
	}
	public String getF_operation_dept() {
		return f_operation_dept;
	}
	public void setF_operation_dept(String f_operation_dept) {
		this.f_operation_dept = f_operation_dept;
	}
	public String getF_operation_person() {
		return f_operation_person;
	}
	public void setF_operation_person(String f_operation_person) {
		this.f_operation_person = f_operation_person;
	}
	public String getF_temp_tag() {
		return f_temp_tag;
	}
	public void setF_temp_tag(String f_temp_tag) {
		this.f_temp_tag = f_temp_tag;
	}
	public String getF_operation_key() {
		return f_operation_key;
	}
	public void setF_operation_key(String f_operation_key) {
		this.f_operation_key = f_operation_key;
	}
	public String getF_operation_code() {
		return f_operation_code;
	}
	public void setF_operation_code(String f_operation_code) {
		this.f_operation_code = f_operation_code;
	}
	public String getF_person_name() {
		return f_person_name;
	}
	public void setF_person_name(String f_person_name) {
		this.f_person_name = f_person_name;
	}
	public String getF_plan_code() {
		return f_plan_code;
	}
	public void setF_plan_code(String f_plan_code) {
		this.f_plan_code = f_plan_code;
	}
	public String getF_poto_a() {
		return f_poto_a;
	}
	public void setF_poto_a(String f_poto_a) {
		this.f_poto_a = f_poto_a;
	}
	public String getF_poto_b() {
		return f_poto_b;
	}
	public void setF_poto_b(String f_poto_b) {
		this.f_poto_b = f_poto_b;
	}
	public String getF_poto_c() {
		return f_poto_c;
	}
	public void setF_poto_c(String f_poto_c) {
		this.f_poto_c = f_poto_c;
	}
	public String getF_poto_d() {
		return f_poto_d;
	}
	public void setF_poto_d(String f_poto_d) {
		this.f_poto_d = f_poto_d;
	}
	public String getF_poto_e() {
		return f_poto_e;
	}
	public void setF_poto_e(String f_poto_e) {
		this.f_poto_e = f_poto_e;
	}
	public String getF_poto_f() {
		return f_poto_f;
	}
	public void setF_poto_f(String f_poto_f) {
		this.f_poto_f = f_poto_f;
	}
	public Date getF_pp_date() {
		return f_pp_date;
	}
	public void setF_pp_date(Date f_pp_date) {
		this.f_pp_date = f_pp_date;
	}
	public String getF_pp_parm() {
		return f_pp_parm;
	}
	public void setF_pp_parm(String f_pp_parm) {
		this.f_pp_parm = f_pp_parm;
	}
	public String getF_pp_person() {
		return f_pp_person;
	}
	public void setF_pp_person(String f_pp_person) {
		this.f_pp_person = f_pp_person;
	}
	public String getF_pp_sample() {
		return f_pp_sample;
	}
	public void setF_pp_sample(String f_pp_sample) {
		this.f_pp_sample = f_pp_sample;
	}
	public String getF_pp_state() {
		return f_pp_state;
	}
	public void setF_pp_state(String f_pp_state) {
		this.f_pp_state = f_pp_state;
	}
	public String getF_produce_stand() {
		return f_produce_stand;
	}
	public void setF_produce_stand(String f_produce_stand) {
		this.f_produce_stand = f_produce_stand;
	}
	public String getF_produce_type() {
		return f_produce_type;
	}
	public void setF_produce_type(String f_produce_type) {
		this.f_produce_type = f_produce_type;
	}
	public String getF_quality_type() {
		return f_quality_type;
	}
	public void setF_quality_type(String f_quality_type) {
		this.f_quality_type = f_quality_type;
	}
	public String getF_rebuild_tag() {
		return f_rebuild_tag;
	}
	public void setF_rebuild_tag(String f_rebuild_tag) {
		this.f_rebuild_tag = f_rebuild_tag;
	}
	public String getF_rebuild_oldkey() {
		return f_rebuild_oldkey;
	}
	public void setF_rebuild_oldkey(String f_rebuild_oldkey) {
		this.f_rebuild_oldkey = f_rebuild_oldkey;
	}
	public String getF_rectify_tag() {
		return f_rectify_tag;
	}
	public void setF_rectify_tag(String f_rectify_tag) {
		this.f_rectify_tag = f_rectify_tag;
	}
	public String getF_rectify_sample() {
		return f_rectify_sample;
	}
	public void setF_rectify_sample(String f_rectify_sample) {
		this.f_rectify_sample = f_rectify_sample;
	}
	public String getF_ryjq_item() {
		return f_ryjq_item;
	}
	public void setF_ryjq_item(String f_ryjq_item) {
		this.f_ryjq_item = f_ryjq_item;
	}
	public String getF_ryjq_parm() {
		return f_ryjq_parm;
	}
	public void setF_ryjq_parm(String f_ryjq_parm) {
		this.f_ryjq_parm = f_ryjq_parm;
	}
	public String getF_ryjq_value() {
		return f_ryjq_value;
	}
	public void setF_ryjq_value(String f_ryjq_value) {
		this.f_ryjq_value = f_ryjq_value;
	}
	public String getF_sample_code() {
		return f_sample_code;
	}
	public void setF_sample_code(String f_sample_code) {
		this.f_sample_code = f_sample_code;
	}
	public int getF_sample_count() {
		return f_sample_count;
	}
	public void setF_sample_count(int f_sample_count) {
		this.f_sample_count = f_sample_count;
	}
	public Date getF_sample_date() {
		return f_sample_date;
	}
	public void setF_sample_date(Date f_sample_date) {
		this.f_sample_date = f_sample_date;
	}
	public String getF_sample_explain() {
		return f_sample_explain;
	}
	public void setF_sample_explain(String f_sample_explain) {
		this.f_sample_explain = f_sample_explain;
	}
	public String getF_sample_key() {
		return f_sample_key;
	}
	public void setF_sample_key(String f_sample_key) {
		this.f_sample_key = f_sample_key;
	}
	public String getF_sample_level() {
		return f_sample_level;
	}
	public void setF_sample_level(String f_sample_level) {
		this.f_sample_level = f_sample_level;
	}
	public String getF_sample_name() {
		return f_sample_name;
	}
	public void setF_sample_name(String f_sample_name) {
		this.f_sample_name = f_sample_name;
	}
	public String getF_sample_new() {
		return f_sample_new;
	}
	public void setF_sample_new(String f_sample_new) {
		this.f_sample_new = f_sample_new;
	}
	public String getF_sample_person() {
		return f_sample_person;
	}
	public void setF_sample_person(String f_sample_person) {
		this.f_sample_person = f_sample_person;
	}
	public String getF_sample_state() {
		return f_sample_state;
	}
	public void setF_sample_state(String f_sample_state) {
		this.f_sample_state = f_sample_state;
	}
	public String getF_sample_template() {
		return f_sample_template;
	}
	public void setF_sample_template(String f_sample_template) {
		this.f_sample_template = f_sample_template;
	}
	public String getF_samplefj() {
		return f_samplefj;
	}
	public void setF_samplefj(String f_samplefj) {
		this.f_samplefj = f_samplefj;
	}
	public String getF_sampleitems() {
		return f_sampleitems;
	}
	public void setF_sampleitems(String f_sampleitems) {
		this.f_sampleitems = f_sampleitems;
	}
	public String getF_sampletemp_code() {
		return f_sampletemp_code;
	}
	public void setF_sampletemp_code(String f_sampletemp_code) {
		this.f_sampletemp_code = f_sampletemp_code;
	}
	public Date getF_sampling_date() {
		return f_sampling_date;
	}
	public void setF_sampling_date(Date f_sampling_date) {
		this.f_sampling_date = f_sampling_date;
	}
	public String getF_sampling_person() {
		return f_sampling_person;
	}
	public void setF_sampling_person(String f_sampling_person) {
		this.f_sampling_person = f_sampling_person;
	}
	public String getF_sampling_site() {
		return f_sampling_site;
	}
	public void setF_sampling_site(String f_sampling_site) {
		this.f_sampling_site = f_sampling_site;
	}
	public String getF_save_sample() {
		return f_save_sample;
	}
	public void setF_save_sample(String f_save_sample) {
		this.f_save_sample = f_save_sample;
	}
	public String getF_sh_bdtag() {
		return f_sh_bdtag;
	}
	public void setF_sh_bdtag(String f_sh_bdtag) {
		this.f_sh_bdtag = f_sh_bdtag;
	}
	public String getF_sh_sample() {
		return f_sh_sample;
	}
	public void setF_sh_sample(String f_sh_sample) {
		this.f_sh_sample = f_sh_sample;
	}
	public String getF_spot_code() {
		return f_spot_code;
	}
	public void setF_spot_code(String f_spot_code) {
		this.f_spot_code = f_spot_code;
	}
	public String getF_spot_name() {
		return f_spot_name;
	}
	public void setF_spot_name(String f_spot_name) {
		this.f_spot_name = f_spot_name;
	}
	public String getF_start_isvaild() {
		return f_start_isvaild;
	}
	public void setF_start_isvaild(String f_start_isvaild) {
		this.f_start_isvaild = f_start_isvaild;
	}
	public Date getF_test_date() {
		return f_test_date;
	}
	public void setF_test_date(Date f_test_date) {
		this.f_test_date = f_test_date;
	}
	public String getF_testlib_out() {
		return f_testlib_out;
	}
	public String getUf_report() {
		return uf_report;
	}
	public void setUf_report(String uf_report) {
		this.uf_report = uf_report;
	}
	public void setF_testlib_out(String f_testlib_out) {
		this.f_testlib_out = f_testlib_out;
	}
	public String getF_temp_key() {
		return f_temp_key;
	}
	public void setF_temp_key(String f_temp_key) {
		this.f_temp_key = f_temp_key;
	}
	public String getF_tacceptperson() {
		return f_tacceptperson;
	}
	public void setF_tacceptperson(String f_tacceptperson) {
		this.f_tacceptperson = f_tacceptperson;
	}
	public Date getF_tdateperson() {
		return f_tdateperson;
	}
	public void setF_tdateperson(Date f_tdateperson) {
		this.f_tdateperson = f_tdateperson;
	}
	public String getF_tdispersons() {
		return f_tdispersons;
	}
	public void setF_tdispersons(String f_tdispersons) {
		this.f_tdispersons = f_tdispersons;
	}
	public String getF_tsendperson() {
		return f_tsendperson;
	}
	public void setF_tsendperson(String f_tsendperson) {
		this.f_tsendperson = f_tsendperson;
	}
	public String getF_unusual_state() {
		return f_unusual_state;
	}
	public void setF_unusual_state(String f_unusual_state) {
		this.f_unusual_state = f_unusual_state;
	}
	public int getF_untread_count() {
		return f_untread_count;
	}
	public void setF_untread_count(int f_untread_count) {
		this.f_untread_count = f_untread_count;
	}
	public String getF_untread_person() {
		return f_untread_person;
	}
	public void setF_untread_person(String f_untread_person) {
		this.f_untread_person = f_untread_person;
	}
	public Date getF_untread_date() {
		return f_untread_date;
	}
	public void setF_untread_date(Date f_untread_date) {
		this.f_untread_date = f_untread_date;
	}
	public String getF_workflow_code() {
		return f_workflow_code;
	}
	public void setF_workflow_code(String f_workflow_code) {
		this.f_workflow_code = f_workflow_code;
	}
	public String getF_year_week() {
		return f_year_week;
	}
	public void setF_year_week(String f_year_week) {
		this.f_year_week = f_year_week;
	}
	public String getUf_00471() {
		return uf_00471;
	}
	public void setUf_00471(String uf_00471) {
		this.uf_00471 = uf_00471;
	}
	public Date getUf_004711() {
		return uf_004711;
	}
	public void setUf_004711(Date uf_004711) {
		this.uf_004711 = uf_004711;
	}
	public String getUf_0047126() {
		return uf_0047126;
	}
	public void setUf_0047126(String uf_0047126) {
		this.uf_0047126 = uf_0047126;
	}
	public String getUf_0047141() {
		return uf_0047141;
	}
	public void setUf_0047141(String uf_0047141) {
		this.uf_0047141 = uf_0047141;
	}
	public String getUf_0047145() {
		return uf_0047145;
	}
	public void setUf_0047145(String uf_0047145) {
		this.uf_0047145 = uf_0047145;
	}
	public String getUf_0047158() {
		return uf_0047158;
	}
	public void setUf_0047158(String uf_0047158) {
		this.uf_0047158 = uf_0047158;
	}
	public String getUf_0047160() {
		return uf_0047160;
	}
	public void setUf_0047160(String uf_0047160) {
		this.uf_0047160 = uf_0047160;
	}
	public String getUf_0047161() {
		return uf_0047161;
	}
	public void setUf_0047161(String uf_0047161) {
		this.uf_0047161 = uf_0047161;
	}
	public String getUf_0047162() {
		return uf_0047162;
	}
	public void setUf_0047162(String uf_0047162) {
		this.uf_0047162 = uf_0047162;
	}
	public String getUf_0047163() {
		return uf_0047163;
	}
	public void setUf_0047163(String uf_0047163) {
		this.uf_0047163 = uf_0047163;
	}
	public String getUf_0047164() {
		return uf_0047164;
	}
	public void setUf_0047164(String uf_0047164) {
		this.uf_0047164 = uf_0047164;
	}
	public String getUf_0047165() {
		return uf_0047165;
	}
	public void setUf_0047165(String uf_0047165) {
		this.uf_0047165 = uf_0047165;
	}
	public String getUf_0047166() {
		return uf_0047166;
	}
	public void setUf_0047166(String uf_0047166) {
		this.uf_0047166 = uf_0047166;
	}
	public String getUf_0047167() {
		return uf_0047167;
	}
	public void setUf_0047167(String uf_0047167) {
		this.uf_0047167 = uf_0047167;
	}
	public String getUf_0047168() {
		return uf_0047168;
	}
	public void setUf_0047168(String uf_0047168) {
		this.uf_0047168 = uf_0047168;
	}
	public int getUf_0047169() {
		return uf_0047169;
	}
	public void setUf_0047169(int uf_0047169) {
		this.uf_0047169 = uf_0047169;
	}
	public String getUf_0047170() {
		return uf_0047170;
	}
	public void setUf_0047170(String uf_0047170) {
		this.uf_0047170 = uf_0047170;
	}
	public String getUf_0047171() {
		return uf_0047171;
	}
	public void setUf_0047171(String uf_0047171) {
		this.uf_0047171 = uf_0047171;
	}
	public String getUf_0047172() {
		return uf_0047172;
	}
	public void setUf_0047172(String uf_0047172) {
		this.uf_0047172 = uf_0047172;
	}
	public String getUf_0047173() {
		return uf_0047173;
	}
	public void setUf_0047173(String uf_0047173) {
		this.uf_0047173 = uf_0047173;
	}
	public String getUf_0047174() {
		return uf_0047174;
	}
	public void setUf_0047174(String uf_0047174) {
		this.uf_0047174 = uf_0047174;
	}
	public String getUf_0047175() {
		return uf_0047175;
	}
	public void setUf_0047175(String uf_0047175) {
		this.uf_0047175 = uf_0047175;
	}
	public String getUf_0047176() {
		return uf_0047176;
	}
	public void setUf_0047176(String uf_0047176) {
		this.uf_0047176 = uf_0047176;
	}
	public String getUf_0047177() {
		return uf_0047177;
	}
	public void setUf_0047177(String uf_0047177) {
		this.uf_0047177 = uf_0047177;
	}
	public String getUf_0047178() {
		return uf_0047178;
	}
	public void setUf_0047178(String uf_0047178) {
		this.uf_0047178 = uf_0047178;
	}
	public String getUf_0047179() {
		return uf_0047179;
	}
	public void setUf_0047179(String uf_0047179) {
		this.uf_0047179 = uf_0047179;
	}
	public String getUf_0047182() {
		return uf_0047182;
	}
	public void setUf_0047182(String uf_0047182) {
		this.uf_0047182 = uf_0047182;
	}
	public String getUf_0047192() {
		return uf_0047192;
	}
	public void setUf_0047192(String uf_0047192) {
		this.uf_0047192 = uf_0047192;
	}
	public String getUf_0047194() {
		return uf_0047194;
	}
	public void setUf_0047194(String uf_0047194) {
		this.uf_0047194 = uf_0047194;
	}
	public String getUf_00472() {
		return uf_00472;
	}
	public void setUf_00472(String uf_00472) {
		this.uf_00472 = uf_00472;
	}
	public String getUf_0047208() {
		return uf_0047208;
	}
	public void setUf_0047208(String uf_0047208) {
		this.uf_0047208 = uf_0047208;
	}
	public String getUf_0047211() {
		return uf_0047211;
	}
	public void setUf_0047211(String uf_0047211) {
		this.uf_0047211 = uf_0047211;
	}
	public String getUf_0047212() {
		return uf_0047212;
	}
	public void setUf_0047212(String uf_0047212) {
		this.uf_0047212 = uf_0047212;
	}
	public String getUf_0047213() {
		return uf_0047213;
	}
	public void setUf_0047213(String uf_0047213) {
		this.uf_0047213 = uf_0047213;
	}
	public String getUf_0047214() {
		return uf_0047214;
	}
	public void setUf_0047214(String uf_0047214) {
		this.uf_0047214 = uf_0047214;
	}
	public String getUf_0047215() {
		return uf_0047215;
	}
	public void setUf_0047215(String uf_0047215) {
		this.uf_0047215 = uf_0047215;
	}
	public String getUf_0047216() {
		return uf_0047216;
	}
	public void setUf_0047216(String uf_0047216) {
		this.uf_0047216 = uf_0047216;
	}
	public Date getUf_0047217() {
		return uf_0047217;
	}
	public void setUf_0047217(Date uf_0047217) {
		this.uf_0047217 = uf_0047217;
	}
	public String getUf_0047218() {
		return uf_0047218;
	}
	public void setUf_0047218(String uf_0047218) {
		this.uf_0047218 = uf_0047218;
	}
	public String getUf_0047220() {
		return uf_0047220;
	}
	public void setUf_0047220(String uf_0047220) {
		this.uf_0047220 = uf_0047220;
	}
	public String getUf_0047221() {
		return uf_0047221;
	}
	public void setUf_0047221(String uf_0047221) {
		this.uf_0047221 = uf_0047221;
	}
	public String getUf_0047225() {
		return uf_0047225;
	}
	public void setUf_0047225(String uf_0047225) {
		this.uf_0047225 = uf_0047225;
	}
	public String getUf_0047226() {
		return uf_0047226;
	}
	public void setUf_0047226(String uf_0047226) {
		this.uf_0047226 = uf_0047226;
	}
	public String getUf_0047227() {
		return uf_0047227;
	}
	public void setUf_0047227(String uf_0047227) {
		this.uf_0047227 = uf_0047227;
	}
	public String getUf_0047228() {
		return uf_0047228;
	}
	public void setUf_0047228(String uf_0047228) {
		this.uf_0047228 = uf_0047228;
	}
	public String getUf_0047230() {
		return uf_0047230;
	}
	public void setUf_0047230(String uf_0047230) {
		this.uf_0047230 = uf_0047230;
	}
	public String getUf_0047231() {
		return uf_0047231;
	}
	public void setUf_0047231(String uf_0047231) {
		this.uf_0047231 = uf_0047231;
	}
	public String getUf_0047232() {
		return uf_0047232;
	}
	public void setUf_0047232(String uf_0047232) {
		this.uf_0047232 = uf_0047232;
	}
	public String getUf_0047233() {
		return uf_0047233;
	}
	public void setUf_0047233(String uf_0047233) {
		this.uf_0047233 = uf_0047233;
	}
	public String getUf_0047234() {
		return uf_0047234;
	}
	public void setUf_0047234(String uf_0047234) {
		this.uf_0047234 = uf_0047234;
	}
	public String getUf_0047235() {
		return uf_0047235;
	}
	public void setUf_0047235(String uf_0047235) {
		this.uf_0047235 = uf_0047235;
	}
	public String getUf_0047236() {
		return uf_0047236;
	}
	public void setUf_0047236(String uf_0047236) {
		this.uf_0047236 = uf_0047236;
	}
	public String getUf_0047237() {
		return uf_0047237;
	}
	public void setUf_0047237(String uf_0047237) {
		this.uf_0047237 = uf_0047237;
	}
	public String getUf_0047238() {
		return uf_0047238;
	}
	public void setUf_0047238(String uf_0047238) {
		this.uf_0047238 = uf_0047238;
	}
	public String getUf_0047239() {
		return uf_0047239;
	}
	public void setUf_0047239(String uf_0047239) {
		this.uf_0047239 = uf_0047239;
	}
	public String getUf_0047240() {
		return uf_0047240;
	}
	public void setUf_0047240(String uf_0047240) {
		this.uf_0047240 = uf_0047240;
	}
	public String getUf_0047241() {
		return uf_0047241;
	}
	public void setUf_0047241(String uf_0047241) {
		this.uf_0047241 = uf_0047241;
	}
	public String getUf_0047242() {
		return uf_0047242;
	}
	public void setUf_0047242(String uf_0047242) {
		this.uf_0047242 = uf_0047242;
	}
	public String getUf_0047243() {
		return uf_0047243;
	}
	public void setUf_0047243(String uf_0047243) {
		this.uf_0047243 = uf_0047243;
	}
	public String getUf_0047244() {
		return uf_0047244;
	}
	public void setUf_0047244(String uf_0047244) {
		this.uf_0047244 = uf_0047244;
	}
	public String getUf_0047245() {
		return uf_0047245;
	}
	public void setUf_0047245(String uf_0047245) {
		this.uf_0047245 = uf_0047245;
	}
	public String getUf_0047246() {
		return uf_0047246;
	}
	public void setUf_0047246(String uf_0047246) {
		this.uf_0047246 = uf_0047246;
	}
	public String getUf_0047247() {
		return uf_0047247;
	}
	public void setUf_0047247(String uf_0047247) {
		this.uf_0047247 = uf_0047247;
	}
	public String getUf_0047248() {
		return uf_0047248;
	}
	public void setUf_0047248(String uf_0047248) {
		this.uf_0047248 = uf_0047248;
	}
	public String getUf_0047249() {
		return uf_0047249;
	}
	public void setUf_0047249(String uf_0047249) {
		this.uf_0047249 = uf_0047249;
	}
	public String getUf_0047250() {
		return uf_0047250;
	}
	public void setUf_0047250(String uf_0047250) {
		this.uf_0047250 = uf_0047250;
	}
	public String getUf_0047251() {
		return uf_0047251;
	}
	public void setUf_0047251(String uf_0047251) {
		this.uf_0047251 = uf_0047251;
	}
	public String getUf_0047252() {
		return uf_0047252;
	}
	public void setUf_0047252(String uf_0047252) {
		this.uf_0047252 = uf_0047252;
	}
	public String getUf_0047253() {
		return uf_0047253;
	}
	public void setUf_0047253(String uf_0047253) {
		this.uf_0047253 = uf_0047253;
	}
	public String getUf_0047254() {
		return uf_0047254;
	}
	public void setUf_0047254(String uf_0047254) {
		this.uf_0047254 = uf_0047254;
	}
	public String getUf_0047255() {
		return uf_0047255;
	}
	public void setUf_0047255(String uf_0047255) {
		this.uf_0047255 = uf_0047255;
	}
	public String getUf_0047256() {
		return uf_0047256;
	}
	public void setUf_0047256(String uf_0047256) {
		this.uf_0047256 = uf_0047256;
	}
	public String getUf_0047257() {
		return uf_0047257;
	}
	public void setUf_0047257(String uf_0047257) {
		this.uf_0047257 = uf_0047257;
	}
	public String getUf_0047258() {
		return uf_0047258;
	}
	public void setUf_0047258(String uf_0047258) {
		this.uf_0047258 = uf_0047258;
	}
	public String getUf_0047259() {
		return uf_0047259;
	}
	public void setUf_0047259(String uf_0047259) {
		this.uf_0047259 = uf_0047259;
	}
	public String getUf_0047260() {
		return uf_0047260;
	}
	public void setUf_0047260(String uf_0047260) {
		this.uf_0047260 = uf_0047260;
	}
	public String getUf_0047261() {
		return uf_0047261;
	}
	public void setUf_0047261(String uf_0047261) {
		this.uf_0047261 = uf_0047261;
	}
	public String getUf_0047262() {
		return uf_0047262;
	}
	public void setUf_0047262(String uf_0047262) {
		this.uf_0047262 = uf_0047262;
	}
	public String getUf_0047263() {
		return uf_0047263;
	}
	public void setUf_0047263(String uf_0047263) {
		this.uf_0047263 = uf_0047263;
	}
	public String getUf_0047264() {
		return uf_0047264;
	}
	public void setUf_0047264(String uf_0047264) {
		this.uf_0047264 = uf_0047264;
	}
	public String getUf_0047265() {
		return uf_0047265;
	}
	public void setUf_0047265(String uf_0047265) {
		this.uf_0047265 = uf_0047265;
	}
	public String getUf_0047266() {
		return uf_0047266;
	}
	public void setUf_0047266(String uf_0047266) {
		this.uf_0047266 = uf_0047266;
	}
	public String getUf_0047267() {
		return uf_0047267;
	}
	public void setUf_0047267(String uf_0047267) {
		this.uf_0047267 = uf_0047267;
	}
	public String getUf_0047268() {
		return uf_0047268;
	}
	public void setUf_0047268(String uf_0047268) {
		this.uf_0047268 = uf_0047268;
	}
	public String getUf_0047269() {
		return uf_0047269;
	}
	public void setUf_0047269(String uf_0047269) {
		this.uf_0047269 = uf_0047269;
	}
	public String getUf_0047270() {
		return uf_0047270;
	}
	public void setUf_0047270(String uf_0047270) {
		this.uf_0047270 = uf_0047270;
	}
	public String getUf_0047271() {
		return uf_0047271;
	}
	public void setUf_0047271(String uf_0047271) {
		this.uf_0047271 = uf_0047271;
	}
	public String getUf_0047272() {
		return uf_0047272;
	}
	public void setUf_0047272(String uf_0047272) {
		this.uf_0047272 = uf_0047272;
	}
	public String getUf_0047273() {
		return uf_0047273;
	}
	public void setUf_0047273(String uf_0047273) {
		this.uf_0047273 = uf_0047273;
	}
	public String getUf_0047274() {
		return uf_0047274;
	}
	public void setUf_0047274(String uf_0047274) {
		this.uf_0047274 = uf_0047274;
	}
	public String getUf_0047275() {
		return uf_0047275;
	}
	public void setUf_0047275(String uf_0047275) {
		this.uf_0047275 = uf_0047275;
	}
	public String getUf_0047276() {
		return uf_0047276;
	}
	public void setUf_0047276(String uf_0047276) {
		this.uf_0047276 = uf_0047276;
	}
	public String getUf_0047277() {
		return uf_0047277;
	}
	public void setUf_0047277(String uf_0047277) {
		this.uf_0047277 = uf_0047277;
	}
	public String getUf_0047278() {
		return uf_0047278;
	}
	public void setUf_0047278(String uf_0047278) {
		this.uf_0047278 = uf_0047278;
	}
	public String getUf_0047279() {
		return uf_0047279;
	}
	public void setUf_0047279(String uf_0047279) {
		this.uf_0047279 = uf_0047279;
	}
	public String getUf_0047280() {
		return uf_0047280;
	}
	public void setUf_0047280(String uf_0047280) {
		this.uf_0047280 = uf_0047280;
	}
	public String getUf_0047281() {
		return uf_0047281;
	}
	public void setUf_0047281(String uf_0047281) {
		this.uf_0047281 = uf_0047281;
	}
	public String getUf_0047282() {
		return uf_0047282;
	}
	public void setUf_0047282(String uf_0047282) {
		this.uf_0047282 = uf_0047282;
	}
	public String getUf_0047283() {
		return uf_0047283;
	}
	public void setUf_0047283(String uf_0047283) {
		this.uf_0047283 = uf_0047283;
	}
	public String getUf_0047284() {
		return uf_0047284;
	}
	public void setUf_0047284(String uf_0047284) {
		this.uf_0047284 = uf_0047284;
	}
	public String getUf_0047285() {
		return uf_0047285;
	}
	public void setUf_0047285(String uf_0047285) {
		this.uf_0047285 = uf_0047285;
	}
	public String getUf_0047286() {
		return uf_0047286;
	}
	public void setUf_0047286(String uf_0047286) {
		this.uf_0047286 = uf_0047286;
	}
	public String getUf_0047287() {
		return uf_0047287;
	}
	public void setUf_0047287(String uf_0047287) {
		this.uf_0047287 = uf_0047287;
	}
	public String getUf_0047288() {
		return uf_0047288;
	}
	public void setUf_0047288(String uf_0047288) {
		this.uf_0047288 = uf_0047288;
	}
	public String getUf_0047289() {
		return uf_0047289;
	}
	public void setUf_0047289(String uf_0047289) {
		this.uf_0047289 = uf_0047289;
	}
	public String getUf_0047290() {
		return uf_0047290;
	}
	public void setUf_0047290(String uf_0047290) {
		this.uf_0047290 = uf_0047290;
	}
	public String getUf_0047291() {
		return uf_0047291;
	}
	public void setUf_0047291(String uf_0047291) {
		this.uf_0047291 = uf_0047291;
	}
	public String getUf_0047292() {
		return uf_0047292;
	}
	public void setUf_0047292(String uf_0047292) {
		this.uf_0047292 = uf_0047292;
	}
	public String getUf_0047293() {
		return uf_0047293;
	}
	public void setUf_0047293(String uf_0047293) {
		this.uf_0047293 = uf_0047293;
	}
	public String getUf_0047294() {
		return uf_0047294;
	}
	public void setUf_0047294(String uf_0047294) {
		this.uf_0047294 = uf_0047294;
	}
	public String getUf_0047295() {
		return uf_0047295;
	}
	public void setUf_0047295(String uf_0047295) {
		this.uf_0047295 = uf_0047295;
	}
	public String getUf_0047296() {
		return uf_0047296;
	}
	public void setUf_0047296(String uf_0047296) {
		this.uf_0047296 = uf_0047296;
	}
	public String getUf_0047298() {
		return uf_0047298;
	}
	public void setUf_0047298(String uf_0047298) {
		this.uf_0047298 = uf_0047298;
	}
	public String getUf_0047299() {
		return uf_0047299;
	}
	public void setUf_0047299(String uf_0047299) {
		this.uf_0047299 = uf_0047299;
	}
	public String getUf_00473() {
		return uf_00473;
	}
	public void setUf_00473(String uf_00473) {
		this.uf_00473 = uf_00473;
	}
	public String getUf_0047300() {
		return uf_0047300;
	}
	public void setUf_0047300(String uf_0047300) {
		this.uf_0047300 = uf_0047300;
	}
	public String getUf_0047301() {
		return uf_0047301;
	}
	public void setUf_0047301(String uf_0047301) {
		this.uf_0047301 = uf_0047301;
	}
	public String getUf_0047302() {
		return uf_0047302;
	}
	public void setUf_0047302(String uf_0047302) {
		this.uf_0047302 = uf_0047302;
	}
	public String getUf_0047303() {
		return uf_0047303;
	}
	public void setUf_0047303(String uf_0047303) {
		this.uf_0047303 = uf_0047303;
	}
	public String getUf_0047304() {
		return uf_0047304;
	}
	public void setUf_0047304(String uf_0047304) {
		this.uf_0047304 = uf_0047304;
	}
	public String getUf_0047305() {
		return uf_0047305;
	}
	public void setUf_0047305(String uf_0047305) {
		this.uf_0047305 = uf_0047305;
	}
	public String getUf_0047306() {
		return uf_0047306;
	}
	public void setUf_0047306(String uf_0047306) {
		this.uf_0047306 = uf_0047306;
	}
	public String getUf_0047307() {
		return uf_0047307;
	}
	public void setUf_0047307(String uf_0047307) {
		this.uf_0047307 = uf_0047307;
	}
	public String getUf_0047308() {
		return uf_0047308;
	}
	public void setUf_0047308(String uf_0047308) {
		this.uf_0047308 = uf_0047308;
	}
	public String getUf_0047309() {
		return uf_0047309;
	}
	public void setUf_0047309(String uf_0047309) {
		this.uf_0047309 = uf_0047309;
	}
	public String getUf_0047310() {
		return uf_0047310;
	}
	public void setUf_0047310(String uf_0047310) {
		this.uf_0047310 = uf_0047310;
	}
	public String getUf_0047312() {
		return uf_0047312;
	}
	public void setUf_0047312(String uf_0047312) {
		this.uf_0047312 = uf_0047312;
	}
	public String getUf_0047313() {
		return uf_0047313;
	}
	public void setUf_0047313(String uf_0047313) {
		this.uf_0047313 = uf_0047313;
	}
	public String getUf_0047314() {
		return uf_0047314;
	}
	public void setUf_0047314(String uf_0047314) {
		this.uf_0047314 = uf_0047314;
	}
	public String getUf_0047315() {
		return uf_0047315;
	}
	public void setUf_0047315(String uf_0047315) {
		this.uf_0047315 = uf_0047315;
	}
	public String getUf_0047316() {
		return uf_0047316;
	}
	public void setUf_0047316(String uf_0047316) {
		this.uf_0047316 = uf_0047316;
	}
	public String getUf_0047317() {
		return uf_0047317;
	}
	public void setUf_0047317(String uf_0047317) {
		this.uf_0047317 = uf_0047317;
	}
	public String getUf_0047318() {
		return uf_0047318;
	}
	public void setUf_0047318(String uf_0047318) {
		this.uf_0047318 = uf_0047318;
	}
	public String getUf_0047319() {
		return uf_0047319;
	}
	public void setUf_0047319(String uf_0047319) {
		this.uf_0047319 = uf_0047319;
	}
	public String getUf_0047320() {
		return uf_0047320;
	}
	public void setUf_0047320(String uf_0047320) {
		this.uf_0047320 = uf_0047320;
	}
	public String getUf_0047321() {
		return uf_0047321;
	}
	public void setUf_0047321(String uf_0047321) {
		this.uf_0047321 = uf_0047321;
	}
	public String getUf_0047322() {
		return uf_0047322;
	}
	public void setUf_0047322(String uf_0047322) {
		this.uf_0047322 = uf_0047322;
	}
	public String getUf_0047323() {
		return uf_0047323;
	}
	public void setUf_0047323(String uf_0047323) {
		this.uf_0047323 = uf_0047323;
	}
	public String getUf_0047324() {
		return uf_0047324;
	}
	public void setUf_0047324(String uf_0047324) {
		this.uf_0047324 = uf_0047324;
	}
	public String getUf_0047325() {
		return uf_0047325;
	}
	public void setUf_0047325(String uf_0047325) {
		this.uf_0047325 = uf_0047325;
	}
	public String getUf_0047326() {
		return uf_0047326;
	}
	public void setUf_0047326(String uf_0047326) {
		this.uf_0047326 = uf_0047326;
	}
	public String getUf_0047327() {
		return uf_0047327;
	}
	public void setUf_0047327(String uf_0047327) {
		this.uf_0047327 = uf_0047327;
	}
	public String getUf_0047328() {
		return uf_0047328;
	}
	public void setUf_0047328(String uf_0047328) {
		this.uf_0047328 = uf_0047328;
	}
	public String getUf_0047329() {
		return uf_0047329;
	}
	public void setUf_0047329(String uf_0047329) {
		this.uf_0047329 = uf_0047329;
	}
	public String getUf_0047330() {
		return uf_0047330;
	}
	public void setUf_0047330(String uf_0047330) {
		this.uf_0047330 = uf_0047330;
	}
	public String getUf_0047331() {
		return uf_0047331;
	}
	public void setUf_0047331(String uf_0047331) {
		this.uf_0047331 = uf_0047331;
	}
	public String getUf_0047332() {
		return uf_0047332;
	}
	public void setUf_0047332(String uf_0047332) {
		this.uf_0047332 = uf_0047332;
	}
	public String getUf_00474() {
		return uf_00474;
	}
	public void setUf_00474(String uf_00474) {
		this.uf_00474 = uf_00474;
	}
	public String getUf_00479() {
		return uf_00479;
	}
	public void setUf_00479(String uf_00479) {
		this.uf_00479 = uf_00479;
	}
	public String getUf_05281() {
		return uf_05281;
	}
	public void setUf_05281(String uf_05281) {
		this.uf_05281 = uf_05281;
	}
	public String getUf_052810() {
		return uf_052810;
	}
	public void setUf_052810(String uf_052810) {
		this.uf_052810 = uf_052810;
	}
	public String getUf_052811() {
		return uf_052811;
	}
	public void setUf_052811(String uf_052811) {
		this.uf_052811 = uf_052811;
	}
	public String getUf_052812() {
		return uf_052812;
	}
	public void setUf_052812(String uf_052812) {
		this.uf_052812 = uf_052812;
	}
	public String getUf_05282() {
		return uf_05282;
	}
	public void setUf_05282(String uf_05282) {
		this.uf_05282 = uf_05282;
	}
	public String getUf_05283() {
		return uf_05283;
	}
	public void setUf_05283(String uf_05283) {
		this.uf_05283 = uf_05283;
	}
	public String getUf_05284() {
		return uf_05284;
	}
	public void setUf_05284(String uf_05284) {
		this.uf_05284 = uf_05284;
	}
	public String getUf_05285() {
		return uf_05285;
	}
	public void setUf_05285(String uf_05285) {
		this.uf_05285 = uf_05285;
	}
	public String getUf_05286() {
		return uf_05286;
	}
	public void setUf_05286(String uf_05286) {
		this.uf_05286 = uf_05286;
	}
	public String getUf_05287() {
		return uf_05287;
	}
	public void setUf_05287(String uf_05287) {
		this.uf_05287 = uf_05287;
	}
	public String getUf_05288() {
		return uf_05288;
	}
	public void setUf_05288(String uf_05288) {
		this.uf_05288 = uf_05288;
	}
	public String getUf_05289() {
		return uf_05289;
	}
	public void setUf_05289(String uf_05289) {
		this.uf_05289 = uf_05289;
	}
	public String getUf_10001() {
		return uf_10001;
	}
	public void setUf_10001(String uf_10001) {
		this.uf_10001 = uf_10001;
	}
	
	public String getUf_sample_code() {
		return uf_sample_code;
	}
	public void setUf_sample_code(String uf_sample_code) {
		this.uf_sample_code = uf_sample_code;
	}
	
	public String getUf_code_type() {
		return uf_code_type;
	}
	public void setUf_code_type(String uf_code_type) {
		this.uf_code_type = uf_code_type;
	}
	
	public MultipartFile getMfile() {
		return mfile;
	}

	public void setMfile(MultipartFile mfile) {
		this.mfile = mfile;
	}
	
	public String getUf_attach() {
		return uf_attach;
	}
	public void setUf_attach(String uf_attach) {
		this.uf_attach = uf_attach;
	}
}