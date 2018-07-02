package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.pojo.PbtReport;

public interface PbtReportMapper {

	@Select({"select * from lims_report where downed=0"})
	List<PbtReport> getReports();

	@Update({"update lims_report set downed=1 where f_report_key=",
		"#{f_report_key,jdbcType=VARCHAR}"})
	int chageStatus(String f_report_key);

}
