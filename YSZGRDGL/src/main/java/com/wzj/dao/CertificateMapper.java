package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.pojo.Certificate;

public interface CertificateMapper {
    int insert(Certificate record);

    @Select({"call getCertNo"})
	int getCertNo();
    
    @Select({"select CertNo,StartNo from ys_cert order by certNo desc"})
    List<Certificate> findAll();

    @Select({"select * from ys_cert where CertNo=#{serial,jdbcType=INTEGER}"})
	Certificate checkCert(int serial);

    @Select({"select min(CertNo) from ys_cert"})
	int getFirstCertNo();

    @Update({"update ys_cert set StartNo=#{startno,jdbcType=INTEGER}",
	"where CertNo =#{certno,jdbcType=INTEGER}"})
	void setCertStart(Certificate cf);

    @Select({"select EnterpriseName from ys_company where ",
    	"serial=#{num,jdbcType=INTEGER} order by id desc limit 0,1"})
	String getNameByCertNo(int num);

    @Select({"select max(CertNo) from ys_cert"})
	int getMaxCertNo();

	List<Certificate> getAllName(List<Certificate> pageInfoList);

	@Select({"select serial as certno,enterprisename as comName from ys_company where",
		" concat(serial,enterprisename) like #{search,jdbcType=VARCHAR}"})
	List<Certificate> getCertsBySearch(String search);
}