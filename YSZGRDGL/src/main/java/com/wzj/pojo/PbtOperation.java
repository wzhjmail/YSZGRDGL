package com.wzj.pojo;

import java.util.Date;

import com.mysql.jdbc.Blob;

public class PbtOperation {
	 String f_finally_state;//
	 double f_advance_charge;// 预收金额 
	 String f_appoint_lab;// 指定实验室 
	 String f_appoint_person;// 指定人员 
	 String f_appoint_pnames;// 指定人员名称 
	 String f_appoint_rose;// 指定角色 
	 String f_auto_report;// 是否自动报告 
	 String f_auto_report_code;// 自动报告编号类型 
	 String f_auto_report_temp;// 自动报告模版 
	 String f_measure_alert;//
	 String f_before_date;//
	 String f_rectify_state;
	 String f_bill_commit;//t X寄送发票 
	 String f_bmzx_csylbh;// 测试用例合并编号 
	 String f_bmzx_csylbh_type;
	 String f_bmzx_csyljlbh;// 测试记录合并编号 
	 Date f_caduit_dodate;// 项目计划审核人审核时间 
	 String f_caduitpass_person;
	 String f_bmzx_csyljlbh_type;
	 String f_caduit_doperson;// 项目计划审核人 
	 Date f_caduitpass_dodate;// 项目计划批准人批准时间 
	 String f_caduitpass_doperson;// 项目计划批准人 
	 String f_charge_commit;// X费用到账 
	 String f_clicked_ht_result;// 查看评审记录 
	 String f_clicked_result;// 查看报告数据 
	 String f_caduit_ok;
	 String f_caduit_person;
	 Date f_commit_date;// 业务提交时间 
	 String f_commit_person;// 业务提交人 
	 String f_control_nextstp;// B是否评审 
	 Date f_cpassplan_dodate;// 项目测试计划批准人批准时间 
	 String f_cpassplan_person;// 项目测试计划批准人 
	 Date f_cplan_dodate;// 项目测试计划审核人审核时间 
	 String f_cplan_person;// 项目测试计划审核人 
	 Date f_cplando_dodate;// 项目测试计划编制人时间 
	 String f_cplando_person;// 项目测试计划编制人 
	 String f_enter_library;// 样品是否入库 
	 String f_appoint_again_person;//
	 String f_testsample_code;
	 Date f_examine_date;// 业务一审时间 
	 String f_examine_person;// 业务一审人 
	 double f_exigence_charge;// 加急费用 
	 double f_item_charge;// 检验费用 
	 String f_item_overdate;// 业务单完成是否超时 
	 Date f_judgment_date;// 业务终审时间 
	 String f_cell_template;
	 String f_judgment_person;// 业务终审人 
	 String f_look_cell;// 查看送检单 
	 String f_cell_person;
	 String f_cell_personname;
	 String f_cell_distag;
	 String f_note_bill;// 是否出具不符合通知单 
	 String f_operation_code;// 业务单用户编号 
	 String f_operation_completed;// 业务单是否完成 
	 String f_rectify_tag;
	 Date f_web_date;
	 String f_web_ip;
	 String f_web_person;
	 Date f_operation_date;// F业务受理时间 
	 String f_operation_dept;// F业务受理人所属部门 
	 String f_operation_itemtype;// 业务项目类别 
	 String f_error_count;//
	 String f_operation_key;// 输出单据提取数据条件 
	 String f_operation_lastcode;// 业务单用户编号后六位 
	 Blob f_cell_bz;
	 String f_operation_person;// 业务受理人 
	 String f_operation_state;// 业务单状态 
	 String f_operation_template;// 业务单模板代码 
	 String f_operation_type;// F业务单编号类型 
	 double f_other_charge;// 其他费用 
	 int f_rectify_count;// 整改次数 
	 Date f_require_date;// B要求完成日期 
	 Date f_second_date;
	 Date f_third_date;
	 Date f_four_date;
	 String f_second_person;
	 String f_third_person;
	 String f_four_person;
	 String f_sample_history;// 提取原样品编号项目数据标志 
	 String f_sampling_name;// 抽样人名称 
	 String f_seal_code;//
	 String f_seal_name;//
	 String f_sign_commit;// X打印盖章 
	 String f_tempoperation_code;// 临时编号 
	 String f_contant_code;
	 String f_temp_key;
	 String f_rebuild_tag;
	 String f_rebuild_oldkey;
	 String f_mail_tag;
	 String f_mail_person;
	 Date f_mail_date;
	 String f_email_context;
	 String f_testplan_type;
	 String f_spot_code;
	 String f_spot_group;
	 String f_testplan_code;// 测试计划编号 
	 String f_testrecored_adate;// 测试记录批准日期 
	 String f_testrecored_aperson;// 测试记录审批人 
	 String f_testrecored_date;// 测试记录审核日期 
	 String f_testrecored_person;// 测试记录审核人 
	 String f_testsample_adate;// 测试用例批准日期 
	 String f_testsample_aperson;// 测试用例审批人 
	 String f_testsample_date;// 测试用例审核日期 
	 String f_testsample_person;// 测试用例审核人 
	 double f_true_charge;// 实收金额 
	 Date f_true_date;// 业务单实际完成时间 
	 String f_web_code;// 预登记编号 
	 String f_web_key;
	 Blob f_web_blob;
	 String f_web_template;
	 String f_testjh_code;
	 String f_testxmjh_code;
	 String f_testjh_person;
	 Date f_testjh_date;
	 String f_testxmjh_person;
	 Date f_testxmjh_date;
	 String f_workflow_code;// B工作流程 
	 String f_yqwcsj_str;// 要求完成时间字符类型 
	 String f_client_code;//
	 String f_unusual_state;//
	 String f_level1;//
	 String f_level2;//
	 String f_level3;//
	 String f_level4;//
	 String f_area;
	 String f_webappoint_person;
	 String f_webpass_person;
	 String uf_00471;//业务单级别 
	 String uf_00472;// 合同号 
	 String f_person_dept;//
	 String f_temp_tag;
	 String uf_004730;// F备注 
	 String f_end_code;
	 String f_end_state;
	 String f_sampling_code;
	 String uf_004734;// F合同号 
	 String uf_004735;// F到站 
	 String uf_004736;// F发站 
	 String uf_004737;// F分批号 
	 String uf_004738;// F要求工作天数 
	 String uf_004739;// F客户编码 
	 String uf_004740;// F主检实验室 
	 String uf_004741;// F接收单位 
	 String uf_004742;// B联系人 
	 String uf_004743;// B电话 
	 String uf_004744;// B详细地址 
	 String uf_004745;// B传真 
	 String uf_004746;// B邮箱 
	 String uf_004747;// O制造商 
	 String uf_004748;// O生产厂 
	 String uf_004749;// O委托目的 
	 String uf_004750;// O检验依据 
	 String uf_004751;// O不合格时通知整改 
	 String uf_004752;// O样品的处理方式 
	 String uf_004753;// O报告类型 
	 String uf_004754;// O服务类型 
	 String uf_004755;// B邮编 
	 String uf_004756;// B委托方其它要求 
	 String uf_004757;// B检验类别 
	 String uf_004758;// B报告寄送方式 
	 String uf_004759;// B付款方式 
	 String uf_004760;// B检验费用 
	 String uf_004761;// BZ委托单位 
	 String uf_004762;// BZ通讯地址 
	 String uf_004763;// BZ联系人 
	 String uf_004764;// BZ电话 
	 String uf_004765;// BZ传真 
	 String uf_004766;// BZ邮箱 
	 String uf_004767;// B送检方式 
	 String uf_004768;// B证书领取的方式 
	 String uf_004769;// B仪器提取的方式 
	 String uf_00477;// B委托单位 
	 String f_plan_code;//
	 String f_add_control;
	 String f_control_gx;
	 String uf_004770;// B仪器邮寄投保 
	 String uf_004771;// B交通费 
	 String uf_004772;// B住宿费 
	 String uf_004773;// B邮寄费 
	 String uf_004774;// B发票单位名称 
	 String uf_004775;// b检测校准费 
	 String uf_004776;// BZ邮编 
	 String uf_004777;// B附件 
	 String uf_004778;// BY送样方式 
	 String uf_004779;// BY样品返还 
	 String uf_004780;// BY保密要求 
	 String uf_004781;// B总费用 
	 String uf_004782;// B其他费用 
	 String uf_004783;// B出口国家 
	 String uf_004784;// B报告数量 
	 String uf_004785;// B委托书检验项目 
	 String uf_004786;// B委托书检测依据 
	 String uf_004787;// B付款单位 
	 String uf_004788;// B发票类型 
	 String uf_004789;// B发票税务登记证 
	 String f_web_codetype;
	 String f_testrecored_code;
	public String getF_finally_state() {
		return f_finally_state;
	}
	public void setF_finally_state(String f_finally_state) {
		this.f_finally_state = f_finally_state;
	}
	public double getF_advance_charge() {
		return f_advance_charge;
	}
	public void setF_advance_charge(double f_advance_charge) {
		this.f_advance_charge = f_advance_charge;
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
	public String getF_appoint_pnames() {
		return f_appoint_pnames;
	}
	public void setF_appoint_pnames(String f_appoint_pnames) {
		this.f_appoint_pnames = f_appoint_pnames;
	}
	public String getF_appoint_rose() {
		return f_appoint_rose;
	}
	public void setF_appoint_rose(String f_appoint_rose) {
		this.f_appoint_rose = f_appoint_rose;
	}
	public String getF_auto_report() {
		return f_auto_report;
	}
	public void setF_auto_report(String f_auto_report) {
		this.f_auto_report = f_auto_report;
	}
	public String getF_auto_report_code() {
		return f_auto_report_code;
	}
	public void setF_auto_report_code(String f_auto_report_code) {
		this.f_auto_report_code = f_auto_report_code;
	}
	public String getF_auto_report_temp() {
		return f_auto_report_temp;
	}
	public void setF_auto_report_temp(String f_auto_report_temp) {
		this.f_auto_report_temp = f_auto_report_temp;
	}
	public String getF_measure_alert() {
		return f_measure_alert;
	}
	public void setF_measure_alert(String f_measure_alert) {
		this.f_measure_alert = f_measure_alert;
	}
	public String getF_before_date() {
		return f_before_date;
	}
	public void setF_before_date(String f_before_date) {
		this.f_before_date = f_before_date;
	}
	public String getF_rectify_state() {
		return f_rectify_state;
	}
	public void setF_rectify_state(String f_rectify_state) {
		this.f_rectify_state = f_rectify_state;
	}
	public String getF_bill_commit() {
		return f_bill_commit;
	}
	public void setF_bill_commit(String f_bill_commit) {
		this.f_bill_commit = f_bill_commit;
	}
	public String getF_bmzx_csylbh() {
		return f_bmzx_csylbh;
	}
	public void setF_bmzx_csylbh(String f_bmzx_csylbh) {
		this.f_bmzx_csylbh = f_bmzx_csylbh;
	}
	public String getF_bmzx_csylbh_type() {
		return f_bmzx_csylbh_type;
	}
	public void setF_bmzx_csylbh_type(String f_bmzx_csylbh_type) {
		this.f_bmzx_csylbh_type = f_bmzx_csylbh_type;
	}
	public String getF_bmzx_csyljlbh() {
		return f_bmzx_csyljlbh;
	}
	public void setF_bmzx_csyljlbh(String f_bmzx_csyljlbh) {
		this.f_bmzx_csyljlbh = f_bmzx_csyljlbh;
	}
	public Date getF_caduit_dodate() {
		return f_caduit_dodate;
	}
	public void setF_caduit_dodate(Date f_caduit_dodate) {
		this.f_caduit_dodate = f_caduit_dodate;
	}
	public String getF_caduitpass_person() {
		return f_caduitpass_person;
	}
	public void setF_caduitpass_person(String f_caduitpass_person) {
		this.f_caduitpass_person = f_caduitpass_person;
	}
	public String getF_bmzx_csyljlbh_type() {
		return f_bmzx_csyljlbh_type;
	}
	public void setF_bmzx_csyljlbh_type(String f_bmzx_csyljlbh_type) {
		this.f_bmzx_csyljlbh_type = f_bmzx_csyljlbh_type;
	}
	public String getF_caduit_doperson() {
		return f_caduit_doperson;
	}
	public void setF_caduit_doperson(String f_caduit_doperson) {
		this.f_caduit_doperson = f_caduit_doperson;
	}
	public Date getF_caduitpass_dodate() {
		return f_caduitpass_dodate;
	}
	public void setF_caduitpass_dodate(Date f_caduitpass_dodate) {
		this.f_caduitpass_dodate = f_caduitpass_dodate;
	}
	public String getF_caduitpass_doperson() {
		return f_caduitpass_doperson;
	}
	public void setF_caduitpass_doperson(String f_caduitpass_doperson) {
		this.f_caduitpass_doperson = f_caduitpass_doperson;
	}
	public String getF_charge_commit() {
		return f_charge_commit;
	}
	public void setF_charge_commit(String f_charge_commit) {
		this.f_charge_commit = f_charge_commit;
	}
	public String getF_clicked_ht_result() {
		return f_clicked_ht_result;
	}
	public void setF_clicked_ht_result(String f_clicked_ht_result) {
		this.f_clicked_ht_result = f_clicked_ht_result;
	}
	public String getF_clicked_result() {
		return f_clicked_result;
	}
	public void setF_clicked_result(String f_clicked_result) {
		this.f_clicked_result = f_clicked_result;
	}
	public String getF_caduit_ok() {
		return f_caduit_ok;
	}
	public void setF_caduit_ok(String f_caduit_ok) {
		this.f_caduit_ok = f_caduit_ok;
	}
	public String getF_caduit_person() {
		return f_caduit_person;
	}
	public void setF_caduit_person(String f_caduit_person) {
		this.f_caduit_person = f_caduit_person;
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
	public String getF_control_nextstp() {
		return f_control_nextstp;
	}
	public void setF_control_nextstp(String f_control_nextstp) {
		this.f_control_nextstp = f_control_nextstp;
	}
	public Date getF_cpassplan_dodate() {
		return f_cpassplan_dodate;
	}
	public void setF_cpassplan_dodate(Date f_cpassplan_dodate) {
		this.f_cpassplan_dodate = f_cpassplan_dodate;
	}
	public String getF_cpassplan_person() {
		return f_cpassplan_person;
	}
	public void setF_cpassplan_person(String f_cpassplan_person) {
		this.f_cpassplan_person = f_cpassplan_person;
	}
	public Date getF_cplan_dodate() {
		return f_cplan_dodate;
	}
	public void setF_cplan_dodate(Date f_cplan_dodate) {
		this.f_cplan_dodate = f_cplan_dodate;
	}
	public String getF_cplan_person() {
		return f_cplan_person;
	}
	public void setF_cplan_person(String f_cplan_person) {
		this.f_cplan_person = f_cplan_person;
	}
	public Date getF_cplando_dodate() {
		return f_cplando_dodate;
	}
	public void setF_cplando_dodate(Date f_cplando_dodate) {
		this.f_cplando_dodate = f_cplando_dodate;
	}
	public String getF_cplando_person() {
		return f_cplando_person;
	}
	public void setF_cplando_person(String f_cplando_person) {
		this.f_cplando_person = f_cplando_person;
	}
	public String getF_enter_library() {
		return f_enter_library;
	}
	public void setF_enter_library(String f_enter_library) {
		this.f_enter_library = f_enter_library;
	}
	public String getF_appoint_again_person() {
		return f_appoint_again_person;
	}
	public void setF_appoint_again_person(String f_appoint_again_person) {
		this.f_appoint_again_person = f_appoint_again_person;
	}
	public String getF_testsample_code() {
		return f_testsample_code;
	}
	public void setF_testsample_code(String f_testsample_code) {
		this.f_testsample_code = f_testsample_code;
	}
	public Date getF_examine_date() {
		return f_examine_date;
	}
	public void setF_examine_date(Date f_examine_date) {
		this.f_examine_date = f_examine_date;
	}
	public String getF_examine_person() {
		return f_examine_person;
	}
	public void setF_examine_person(String f_examine_person) {
		this.f_examine_person = f_examine_person;
	}
	public double getF_exigence_charge() {
		return f_exigence_charge;
	}
	public void setF_exigence_charge(double f_exigence_charge) {
		this.f_exigence_charge = f_exigence_charge;
	}
	public double getF_item_charge() {
		return f_item_charge;
	}
	public void setF_item_charge(double f_item_charge) {
		this.f_item_charge = f_item_charge;
	}
	public String getF_item_overdate() {
		return f_item_overdate;
	}
	public void setF_item_overdate(String f_item_overdate) {
		this.f_item_overdate = f_item_overdate;
	}
	public Date getF_judgment_date() {
		return f_judgment_date;
	}
	public void setF_judgment_date(Date f_judgment_date) {
		this.f_judgment_date = f_judgment_date;
	}
	public String getF_cell_template() {
		return f_cell_template;
	}
	public void setF_cell_template(String f_cell_template) {
		this.f_cell_template = f_cell_template;
	}
	public String getF_judgment_person() {
		return f_judgment_person;
	}
	public void setF_judgment_person(String f_judgment_person) {
		this.f_judgment_person = f_judgment_person;
	}
	public String getF_look_cell() {
		return f_look_cell;
	}
	public void setF_look_cell(String f_look_cell) {
		this.f_look_cell = f_look_cell;
	}
	public String getF_cell_person() {
		return f_cell_person;
	}
	public void setF_cell_person(String f_cell_person) {
		this.f_cell_person = f_cell_person;
	}
	public String getF_cell_personname() {
		return f_cell_personname;
	}
	public void setF_cell_personname(String f_cell_personname) {
		this.f_cell_personname = f_cell_personname;
	}
	public String getF_cell_distag() {
		return f_cell_distag;
	}
	public void setF_cell_distag(String f_cell_distag) {
		this.f_cell_distag = f_cell_distag;
	}
	public String getF_note_bill() {
		return f_note_bill;
	}
	public void setF_note_bill(String f_note_bill) {
		this.f_note_bill = f_note_bill;
	}
	public String getF_operation_code() {
		return f_operation_code;
	}
	public void setF_operation_code(String f_operation_code) {
		this.f_operation_code = f_operation_code;
	}
	public String getF_operation_completed() {
		return f_operation_completed;
	}
	public void setF_operation_completed(String f_operation_completed) {
		this.f_operation_completed = f_operation_completed;
	}
	public String getF_rectify_tag() {
		return f_rectify_tag;
	}
	public void setF_rectify_tag(String f_rectify_tag) {
		this.f_rectify_tag = f_rectify_tag;
	}
	public Date getF_web_date() {
		return f_web_date;
	}
	public void setF_web_date(Date f_web_date) {
		this.f_web_date = f_web_date;
	}
	public String getF_web_ip() {
		return f_web_ip;
	}
	public void setF_web_ip(String f_web_ip) {
		this.f_web_ip = f_web_ip;
	}
	public String getF_web_person() {
		return f_web_person;
	}
	public void setF_web_person(String f_web_person) {
		this.f_web_person = f_web_person;
	}
	public Date getF_operation_date() {
		return f_operation_date;
	}
	public void setF_operation_date(Date f_operation_date) {
		this.f_operation_date = f_operation_date;
	}
	public String getF_operation_dept() {
		return f_operation_dept;
	}
	public void setF_operation_dept(String f_operation_dept) {
		this.f_operation_dept = f_operation_dept;
	}
	public String getF_operation_itemtype() {
		return f_operation_itemtype;
	}
	public void setF_operation_itemtype(String f_operation_itemtype) {
		this.f_operation_itemtype = f_operation_itemtype;
	}
	public String getF_error_count() {
		return f_error_count;
	}
	public void setF_error_count(String f_error_count) {
		this.f_error_count = f_error_count;
	}
	public String getF_operation_key() {
		return f_operation_key;
	}
	public void setF_operation_key(String f_operation_key) {
		this.f_operation_key = f_operation_key;
	}
	public String getF_operation_lastcode() {
		return f_operation_lastcode;
	}
	public void setF_operation_lastcode(String f_operation_lastcode) {
		this.f_operation_lastcode = f_operation_lastcode;
	}
	public Blob getF_cell_bz() {
		return f_cell_bz;
	}
	public void setF_cell_bz(Blob f_cell_bz) {
		this.f_cell_bz = f_cell_bz;
	}
	public String getF_operation_person() {
		return f_operation_person;
	}
	public void setF_operation_person(String f_operation_person) {
		this.f_operation_person = f_operation_person;
	}
	public String getF_operation_state() {
		return f_operation_state;
	}
	public void setF_operation_state(String f_operation_state) {
		this.f_operation_state = f_operation_state;
	}
	public String getF_operation_template() {
		return f_operation_template;
	}
	public void setF_operation_template(String f_operation_template) {
		this.f_operation_template = f_operation_template;
	}
	public String getF_operation_type() {
		return f_operation_type;
	}
	public void setF_operation_type(String f_operation_type) {
		this.f_operation_type = f_operation_type;
	}
	public double getF_other_charge() {
		return f_other_charge;
	}
	public void setF_other_charge(double f_other_charge) {
		this.f_other_charge = f_other_charge;
	}
	public int getF_rectify_count() {
		return f_rectify_count;
	}
	public void setF_rectify_count(int f_rectify_count) {
		this.f_rectify_count = f_rectify_count;
	}
	public Date getF_require_date() {
		return f_require_date;
	}
	public void setF_require_date(Date f_require_date) {
		this.f_require_date = f_require_date;
	}
	public Date getF_second_date() {
		return f_second_date;
	}
	public void setF_second_date(Date f_second_date) {
		this.f_second_date = f_second_date;
	}
	public Date getF_third_date() {
		return f_third_date;
	}
	public void setF_third_date(Date f_third_date) {
		this.f_third_date = f_third_date;
	}
	public Date getF_four_date() {
		return f_four_date;
	}
	public void setF_four_date(Date f_four_date) {
		this.f_four_date = f_four_date;
	}
	public String getF_second_person() {
		return f_second_person;
	}
	public void setF_second_person(String f_second_person) {
		this.f_second_person = f_second_person;
	}
	public String getF_third_person() {
		return f_third_person;
	}
	public void setF_third_person(String f_third_person) {
		this.f_third_person = f_third_person;
	}
	public String getF_four_person() {
		return f_four_person;
	}
	public void setF_four_person(String f_four_person) {
		this.f_four_person = f_four_person;
	}
	public String getF_sample_history() {
		return f_sample_history;
	}
	public void setF_sample_history(String f_sample_history) {
		this.f_sample_history = f_sample_history;
	}
	public String getF_sampling_name() {
		return f_sampling_name;
	}
	public void setF_sampling_name(String f_sampling_name) {
		this.f_sampling_name = f_sampling_name;
	}
	public String getF_seal_code() {
		return f_seal_code;
	}
	public void setF_seal_code(String f_seal_code) {
		this.f_seal_code = f_seal_code;
	}
	public String getF_seal_name() {
		return f_seal_name;
	}
	public void setF_seal_name(String f_seal_name) {
		this.f_seal_name = f_seal_name;
	}
	public String getF_sign_commit() {
		return f_sign_commit;
	}
	public void setF_sign_commit(String f_sign_commit) {
		this.f_sign_commit = f_sign_commit;
	}
	public String getF_tempoperation_code() {
		return f_tempoperation_code;
	}
	public void setF_tempoperation_code(String f_tempoperation_code) {
		this.f_tempoperation_code = f_tempoperation_code;
	}
	public String getF_contant_code() {
		return f_contant_code;
	}
	public void setF_contant_code(String f_contant_code) {
		this.f_contant_code = f_contant_code;
	}
	public String getF_temp_key() {
		return f_temp_key;
	}
	public void setF_temp_key(String f_temp_key) {
		this.f_temp_key = f_temp_key;
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
	public String getF_mail_tag() {
		return f_mail_tag;
	}
	public void setF_mail_tag(String f_mail_tag) {
		this.f_mail_tag = f_mail_tag;
	}
	public String getF_mail_person() {
		return f_mail_person;
	}
	public void setF_mail_person(String f_mail_person) {
		this.f_mail_person = f_mail_person;
	}
	public Date getF_mail_date() {
		return f_mail_date;
	}
	public void setF_mail_date(Date f_mail_date) {
		this.f_mail_date = f_mail_date;
	}
	public String getF_email_context() {
		return f_email_context;
	}
	public void setF_email_context(String f_email_context) {
		this.f_email_context = f_email_context;
	}
	public String getF_testplan_type() {
		return f_testplan_type;
	}
	public void setF_testplan_type(String f_testplan_type) {
		this.f_testplan_type = f_testplan_type;
	}
	public String getF_spot_code() {
		return f_spot_code;
	}
	public void setF_spot_code(String f_spot_code) {
		this.f_spot_code = f_spot_code;
	}
	public String getF_spot_group() {
		return f_spot_group;
	}
	public void setF_spot_group(String f_spot_group) {
		this.f_spot_group = f_spot_group;
	}
	public String getF_testplan_code() {
		return f_testplan_code;
	}
	public void setF_testplan_code(String f_testplan_code) {
		this.f_testplan_code = f_testplan_code;
	}
	public String getF_testrecored_adate() {
		return f_testrecored_adate;
	}
	public void setF_testrecored_adate(String f_testrecored_adate) {
		this.f_testrecored_adate = f_testrecored_adate;
	}
	public String getF_testrecored_aperson() {
		return f_testrecored_aperson;
	}
	public void setF_testrecored_aperson(String f_testrecored_aperson) {
		this.f_testrecored_aperson = f_testrecored_aperson;
	}
	public String getF_testrecored_date() {
		return f_testrecored_date;
	}
	public void setF_testrecored_date(String f_testrecored_date) {
		this.f_testrecored_date = f_testrecored_date;
	}
	public String getF_testrecored_person() {
		return f_testrecored_person;
	}
	public void setF_testrecored_person(String f_testrecored_person) {
		this.f_testrecored_person = f_testrecored_person;
	}
	public String getF_testsample_adate() {
		return f_testsample_adate;
	}
	public void setF_testsample_adate(String f_testsample_adate) {
		this.f_testsample_adate = f_testsample_adate;
	}
	public String getF_testsample_aperson() {
		return f_testsample_aperson;
	}
	public void setF_testsample_aperson(String f_testsample_aperson) {
		this.f_testsample_aperson = f_testsample_aperson;
	}
	public String getF_testsample_date() {
		return f_testsample_date;
	}
	public void setF_testsample_date(String f_testsample_date) {
		this.f_testsample_date = f_testsample_date;
	}
	public String getF_testsample_person() {
		return f_testsample_person;
	}
	public void setF_testsample_person(String f_testsample_person) {
		this.f_testsample_person = f_testsample_person;
	}
	public double getF_true_charge() {
		return f_true_charge;
	}
	public void setF_true_charge(double f_true_charge) {
		this.f_true_charge = f_true_charge;
	}
	public Date getF_true_date() {
		return f_true_date;
	}
	public void setF_true_date(Date f_true_date) {
		this.f_true_date = f_true_date;
	}
	public String getF_web_code() {
		return f_web_code;
	}
	public void setF_web_code(String f_web_code) {
		this.f_web_code = f_web_code;
	}
	public String getF_web_key() {
		return f_web_key;
	}
	public void setF_web_key(String f_web_key) {
		this.f_web_key = f_web_key;
	}
	public Blob getF_web_blob() {
		return f_web_blob;
	}
	public void setF_web_blob(Blob f_web_blob) {
		this.f_web_blob = f_web_blob;
	}
	public String getF_web_template() {
		return f_web_template;
	}
	public void setF_web_template(String f_web_template) {
		this.f_web_template = f_web_template;
	}
	public String getF_testjh_code() {
		return f_testjh_code;
	}
	public void setF_testjh_code(String f_testjh_code) {
		this.f_testjh_code = f_testjh_code;
	}
	public String getF_testxmjh_code() {
		return f_testxmjh_code;
	}
	public void setF_testxmjh_code(String f_testxmjh_code) {
		this.f_testxmjh_code = f_testxmjh_code;
	}
	public String getF_testjh_person() {
		return f_testjh_person;
	}
	public void setF_testjh_person(String f_testjh_person) {
		this.f_testjh_person = f_testjh_person;
	}
	public Date getF_testjh_date() {
		return f_testjh_date;
	}
	public void setF_testjh_date(Date f_testjh_date) {
		this.f_testjh_date = f_testjh_date;
	}
	public String getF_testxmjh_person() {
		return f_testxmjh_person;
	}
	public void setF_testxmjh_person(String f_testxmjh_person) {
		this.f_testxmjh_person = f_testxmjh_person;
	}
	public Date getF_testxmjh_date() {
		return f_testxmjh_date;
	}
	public void setF_testxmjh_date(Date f_testxmjh_date) {
		this.f_testxmjh_date = f_testxmjh_date;
	}
	public String getF_workflow_code() {
		return f_workflow_code;
	}
	public void setF_workflow_code(String f_workflow_code) {
		this.f_workflow_code = f_workflow_code;
	}
	public String getF_yqwcsj_str() {
		return f_yqwcsj_str;
	}
	public void setF_yqwcsj_str(String f_yqwcsj_str) {
		this.f_yqwcsj_str = f_yqwcsj_str;
	}
	public String getF_client_code() {
		return f_client_code;
	}
	public void setF_client_code(String f_client_code) {
		this.f_client_code = f_client_code;
	}
	public String getF_unusual_state() {
		return f_unusual_state;
	}
	public void setF_unusual_state(String f_unusual_state) {
		this.f_unusual_state = f_unusual_state;
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
	public String getF_area() {
		return f_area;
	}
	public void setF_area(String f_area) {
		this.f_area = f_area;
	}
	public String getF_webappoint_person() {
		return f_webappoint_person;
	}
	public void setF_webappoint_person(String f_webappoint_person) {
		this.f_webappoint_person = f_webappoint_person;
	}
	public String getF_webpass_person() {
		return f_webpass_person;
	}
	public void setF_webpass_person(String f_webpass_person) {
		this.f_webpass_person = f_webpass_person;
	}
	public String getUf_00471() {
		return uf_00471;
	}
	public void setUf_00471(String uf_00471) {
		this.uf_00471 = uf_00471;
	}
	public String getUf_00472() {
		return uf_00472;
	}
	public void setUf_00472(String uf_00472) {
		this.uf_00472 = uf_00472;
	}
	public String getF_person_dept() {
		return f_person_dept;
	}
	public void setF_person_dept(String f_person_dept) {
		this.f_person_dept = f_person_dept;
	}
	public String getF_temp_tag() {
		return f_temp_tag;
	}
	public void setF_temp_tag(String f_temp_tag) {
		this.f_temp_tag = f_temp_tag;
	}
	public String getUf_004730() {
		return uf_004730;
	}
	public void setUf_004730(String uf_004730) {
		this.uf_004730 = uf_004730;
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
	public String getF_sampling_code() {
		return f_sampling_code;
	}
	public void setF_sampling_code(String f_sampling_code) {
		this.f_sampling_code = f_sampling_code;
	}
	public String getUf_004734() {
		return uf_004734;
	}
	public void setUf_004734(String uf_004734) {
		this.uf_004734 = uf_004734;
	}
	public String getUf_004735() {
		return uf_004735;
	}
	public void setUf_004735(String uf_004735) {
		this.uf_004735 = uf_004735;
	}
	public String getUf_004736() {
		return uf_004736;
	}
	public void setUf_004736(String uf_004736) {
		this.uf_004736 = uf_004736;
	}
	public String getUf_004737() {
		return uf_004737;
	}
	public void setUf_004737(String uf_004737) {
		this.uf_004737 = uf_004737;
	}
	public String getUf_004738() {
		return uf_004738;
	}
	public void setUf_004738(String uf_004738) {
		this.uf_004738 = uf_004738;
	}
	public String getUf_004739() {
		return uf_004739;
	}
	public void setUf_004739(String uf_004739) {
		this.uf_004739 = uf_004739;
	}
	public String getUf_004740() {
		return uf_004740;
	}
	public void setUf_004740(String uf_004740) {
		this.uf_004740 = uf_004740;
	}
	public String getUf_004741() {
		return uf_004741;
	}
	public void setUf_004741(String uf_004741) {
		this.uf_004741 = uf_004741;
	}
	public String getUf_004742() {
		return uf_004742;
	}
	public void setUf_004742(String uf_004742) {
		this.uf_004742 = uf_004742;
	}
	public String getUf_004743() {
		return uf_004743;
	}
	public void setUf_004743(String uf_004743) {
		this.uf_004743 = uf_004743;
	}
	public String getUf_004744() {
		return uf_004744;
	}
	public void setUf_004744(String uf_004744) {
		this.uf_004744 = uf_004744;
	}
	public String getUf_004745() {
		return uf_004745;
	}
	public void setUf_004745(String uf_004745) {
		this.uf_004745 = uf_004745;
	}
	public String getUf_004746() {
		return uf_004746;
	}
	public void setUf_004746(String uf_004746) {
		this.uf_004746 = uf_004746;
	}
	public String getUf_004747() {
		return uf_004747;
	}
	public void setUf_004747(String uf_004747) {
		this.uf_004747 = uf_004747;
	}
	public String getUf_004748() {
		return uf_004748;
	}
	public void setUf_004748(String uf_004748) {
		this.uf_004748 = uf_004748;
	}
	public String getUf_004749() {
		return uf_004749;
	}
	public void setUf_004749(String uf_004749) {
		this.uf_004749 = uf_004749;
	}
	public String getUf_004750() {
		return uf_004750;
	}
	public void setUf_004750(String uf_004750) {
		this.uf_004750 = uf_004750;
	}
	public String getUf_004751() {
		return uf_004751;
	}
	public void setUf_004751(String uf_004751) {
		this.uf_004751 = uf_004751;
	}
	public String getUf_004752() {
		return uf_004752;
	}
	public void setUf_004752(String uf_004752) {
		this.uf_004752 = uf_004752;
	}
	public String getUf_004753() {
		return uf_004753;
	}
	public void setUf_004753(String uf_004753) {
		this.uf_004753 = uf_004753;
	}
	public String getUf_004754() {
		return uf_004754;
	}
	public void setUf_004754(String uf_004754) {
		this.uf_004754 = uf_004754;
	}
	public String getUf_004755() {
		return uf_004755;
	}
	public void setUf_004755(String uf_004755) {
		this.uf_004755 = uf_004755;
	}
	public String getUf_004756() {
		return uf_004756;
	}
	public void setUf_004756(String uf_004756) {
		this.uf_004756 = uf_004756;
	}
	public String getUf_004757() {
		return uf_004757;
	}
	public void setUf_004757(String uf_004757) {
		this.uf_004757 = uf_004757;
	}
	public String getUf_004758() {
		return uf_004758;
	}
	public void setUf_004758(String uf_004758) {
		this.uf_004758 = uf_004758;
	}
	public String getUf_004759() {
		return uf_004759;
	}
	public void setUf_004759(String uf_004759) {
		this.uf_004759 = uf_004759;
	}
	public String getUf_004760() {
		return uf_004760;
	}
	public void setUf_004760(String uf_004760) {
		this.uf_004760 = uf_004760;
	}
	public String getUf_004761() {
		return uf_004761;
	}
	public void setUf_004761(String uf_004761) {
		this.uf_004761 = uf_004761;
	}
	public String getUf_004762() {
		return uf_004762;
	}
	public void setUf_004762(String uf_004762) {
		this.uf_004762 = uf_004762;
	}
	public String getUf_004763() {
		return uf_004763;
	}
	public void setUf_004763(String uf_004763) {
		this.uf_004763 = uf_004763;
	}
	public String getUf_004764() {
		return uf_004764;
	}
	public void setUf_004764(String uf_004764) {
		this.uf_004764 = uf_004764;
	}
	public String getUf_004765() {
		return uf_004765;
	}
	public void setUf_004765(String uf_004765) {
		this.uf_004765 = uf_004765;
	}
	public String getUf_004766() {
		return uf_004766;
	}
	public void setUf_004766(String uf_004766) {
		this.uf_004766 = uf_004766;
	}
	public String getUf_004767() {
		return uf_004767;
	}
	public void setUf_004767(String uf_004767) {
		this.uf_004767 = uf_004767;
	}
	public String getUf_004768() {
		return uf_004768;
	}
	public void setUf_004768(String uf_004768) {
		this.uf_004768 = uf_004768;
	}
	public String getUf_004769() {
		return uf_004769;
	}
	public void setUf_004769(String uf_004769) {
		this.uf_004769 = uf_004769;
	}
	public String getUf_00477() {
		return uf_00477;
	}
	public void setUf_00477(String uf_00477) {
		this.uf_00477 = uf_00477;
	}
	public String getF_plan_code() {
		return f_plan_code;
	}
	public void setF_plan_code(String f_plan_code) {
		this.f_plan_code = f_plan_code;
	}
	public String getF_add_control() {
		return f_add_control;
	}
	public void setF_add_control(String f_add_control) {
		this.f_add_control = f_add_control;
	}
	public String getF_control_gx() {
		return f_control_gx;
	}
	public void setF_control_gx(String f_control_gx) {
		this.f_control_gx = f_control_gx;
	}
	public String getUf_004770() {
		return uf_004770;
	}
	public void setUf_004770(String uf_004770) {
		this.uf_004770 = uf_004770;
	}
	public String getUf_004771() {
		return uf_004771;
	}
	public void setUf_004771(String uf_004771) {
		this.uf_004771 = uf_004771;
	}
	public String getUf_004772() {
		return uf_004772;
	}
	public void setUf_004772(String uf_004772) {
		this.uf_004772 = uf_004772;
	}
	public String getUf_004773() {
		return uf_004773;
	}
	public void setUf_004773(String uf_004773) {
		this.uf_004773 = uf_004773;
	}
	public String getUf_004774() {
		return uf_004774;
	}
	public void setUf_004774(String uf_004774) {
		this.uf_004774 = uf_004774;
	}
	public String getUf_004775() {
		return uf_004775;
	}
	public void setUf_004775(String uf_004775) {
		this.uf_004775 = uf_004775;
	}
	public String getUf_004776() {
		return uf_004776;
	}
	public void setUf_004776(String uf_004776) {
		this.uf_004776 = uf_004776;
	}
	public String getUf_004777() {
		return uf_004777;
	}
	public void setUf_004777(String uf_004777) {
		this.uf_004777 = uf_004777;
	}
	public String getUf_004778() {
		return uf_004778;
	}
	public void setUf_004778(String uf_004778) {
		this.uf_004778 = uf_004778;
	}
	public String getUf_004779() {
		return uf_004779;
	}
	public void setUf_004779(String uf_004779) {
		this.uf_004779 = uf_004779;
	}
	public String getUf_004780() {
		return uf_004780;
	}
	public void setUf_004780(String uf_004780) {
		this.uf_004780 = uf_004780;
	}
	public String getUf_004781() {
		return uf_004781;
	}
	public void setUf_004781(String uf_004781) {
		this.uf_004781 = uf_004781;
	}
	public String getUf_004782() {
		return uf_004782;
	}
	public void setUf_004782(String uf_004782) {
		this.uf_004782 = uf_004782;
	}
	public String getUf_004783() {
		return uf_004783;
	}
	public void setUf_004783(String uf_004783) {
		this.uf_004783 = uf_004783;
	}
	public String getUf_004784() {
		return uf_004784;
	}
	public void setUf_004784(String uf_004784) {
		this.uf_004784 = uf_004784;
	}
	public String getUf_004785() {
		return uf_004785;
	}
	public void setUf_004785(String uf_004785) {
		this.uf_004785 = uf_004785;
	}
	public String getUf_004786() {
		return uf_004786;
	}
	public void setUf_004786(String uf_004786) {
		this.uf_004786 = uf_004786;
	}
	public String getUf_004787() {
		return uf_004787;
	}
	public void setUf_004787(String uf_004787) {
		this.uf_004787 = uf_004787;
	}
	public String getUf_004788() {
		return uf_004788;
	}
	public void setUf_004788(String uf_004788) {
		this.uf_004788 = uf_004788;
	}
	public String getUf_004789() {
		return uf_004789;
	}
	public void setUf_004789(String uf_004789) {
		this.uf_004789 = uf_004789;
	}
	public String getF_web_codetype() {
		return f_web_codetype;
	}
	public void setF_web_codetype(String f_web_codetype) {
		this.f_web_codetype = f_web_codetype;
	}
	public String getF_testrecored_code() {
		return f_testrecored_code;
	}
	public void setF_testrecored_code(String f_testrecored_code) {
		this.f_testrecored_code = f_testrecored_code;
	}
	 
}
