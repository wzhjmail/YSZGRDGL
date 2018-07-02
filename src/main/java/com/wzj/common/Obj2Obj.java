package com.wzj.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.CompanyInfo;
import com.wzj.pojo.FormChange;

public class Obj2Obj {
	public ApplicationForm Com2App(CompanyInfo com){
		ApplicationForm app = new ApplicationForm();
		app.setTitleno(com.getTitleno());
		app.setEnterprisename(com.getEnterprisename());
		app.setEnglishname(com.getEnglishname());
		app.setFax(com.getFax());
		app.setAddress(com.getAddress());
		app.setPostalcode(com.getPostalcode());
		app.setBusinessno(com.getBusinessno());
		app.setEnterprisekind(com.getEnterprisekind());
		app.setRegistercapital(com.getRegistercapital());
		app.setArtificialperson(com.getArtificialperson());
		app.setApjob(com.getApjob());
		app.setAptel(com.getAptel());
		app.setLinkman(com.getLinkman());
		app.setLjob(com.getLjob());
		app.setLtel(com.getLtel());
		app.setMainbusiness(com.getMainbusiness());
		app.setRestbusiness(com.getRestbusiness());
		app.setEmployeetotal(com.getEmployeetotal());
		app.setTechniciantotal(com.getTechniciantotal());
		app.setBcprincipal(com.getBcprincipal());
		app.setTpbusiness(com.getTpbusiness());
		app.setTpopost(com.getTpopost());
		app.setFlat(com.getFlat());
		app.setGravure(com.getGravure());
		app.setWebby(com.getWebby());
		app.setFlexible(com.getFlexible());
		app.setPapery(com.getPapery());
		app.setPastern(com.getPastern());
		app.setFrilling(com.getFrilling());
		app.setMetal(com.getMetal());
		app.setPlastic(com.getPlastic());
		app.setElsematerial(com.getElsematerial());
		app.setOffshootorganiz(com.getOffshootorganiz());
		app.setPressworkEquipment(com.getPressworkEquipment());
		app.setCheckEquipment(com.getCheckEquipment());
		app.setRemarks(com.getRemarks());
		app.setEvaluatingresult(com.getEvaluatingresult());
		app.setSyndicResult(com.getSyndicResult());
		app.setCertificateno(com.getCertificateno());
		app.setCertappdate(com.getCertappdate());
		app.setCerttodate(com.getCerttodate());
		app.setCreatedate(com.getCertappdate());
		app.setIsrepeat(com.getIsrepeat());
		app.setStatus(com.getStatus());
		app.setEmail(com.getEmail());
		app.setBakdate(com.getBakdate());
		app.setOldcertcode(com.getOldcertcode());
		app.setCdate(com.getCdate());
		app.setAppdate(com.getAppdate());
		app.setZhuxiao(com.getZhuxiao());
		app.setZhuxiaodate(com.getZhuxiaodate());
		app.setPdate(com.getPdate());
		app.setLastid(com.getLastid());
		app.setPrintEquipment(com.getPrintEquipment());
		app.setTestEquipment(com.getTestEquipment());
		app.setRemarks(com.getRemarks());
		app.setBranchId(com.getBranchId());
		app.setBranchName(com.getBranchName());
		app.setQuality(com.getQuality());
		app.setCompanyId(com.getId());
		app.setElsetype(com.getElsetype());
		app.setQualityno(com.getQualityno());
		return app;
	}
	public CompanyInfo App2Com(ApplicationForm com){
		CompanyInfo app = new CompanyInfo();
		app.setTitleno(com.getTitleno());
		app.setEnterprisename(com.getEnterprisename());
		app.setEnglishname(com.getEnglishname());
		app.setFax(com.getFax());
		app.setAddress(com.getAddress());
		app.setPostalcode(com.getPostalcode());
		app.setBusinessno(com.getBusinessno());
		app.setEnterprisekind(com.getEnterprisekind());
		app.setRegistercapital(com.getRegistercapital());
		app.setArtificialperson(com.getArtificialperson());
		app.setApjob(com.getApjob());
		app.setAptel(com.getAptel());
		app.setLinkman(com.getLinkman());
		app.setLjob(com.getLjob());
		app.setLtel(com.getLtel());
		app.setMainbusiness(com.getMainbusiness());
		app.setRestbusiness(com.getRestbusiness());
		app.setEmployeetotal(com.getEmployeetotal());
		app.setTechniciantotal(com.getTechniciantotal());
		app.setBcprincipal(com.getBcprincipal());
		app.setTpbusiness(com.getTpbusiness());
		app.setTpopost(com.getTpopost());
		app.setFlat(com.getFlat());
		app.setGravure(com.getGravure());
		app.setWebby(com.getWebby());
		app.setFlexible(com.getFlexible());
		app.setPapery(com.getPapery());
		app.setPastern(com.getPastern());
		app.setFrilling(com.getFrilling());
		app.setMetal(com.getMetal());
		app.setPlastic(com.getPlastic());
		app.setElsematerial(com.getElsematerial());
		app.setOffshootorganiz(com.getOffshootorganiz());
		app.setPressworkEquipment(com.getPressworkEquipment());
		app.setCheckEquipment(com.getCheckEquipment());
		app.setRemarks(com.getRemarks());
		app.setEvaluatingresult(com.getEvaluatingresult());
		app.setSyndicResult(com.getSyndicResult());
		app.setCertificateno(com.getCertificateno());
		app.setCertappdate(com.getCertappdate());
		app.setCerttodate(com.getCerttodate());
		app.setCreatedate(com.getCertappdate());
		app.setIsrepeat(com.getIsrepeat());
		app.setStatus(com.getStatus());
		app.setEmail(com.getEmail());
		app.setBakdate(com.getBakdate());
		app.setOldcertcode(com.getOldcertcode());
		app.setCdate(com.getCdate());
		app.setAppdate(com.getAppdate());
		app.setZhuxiao(com.getZhuxiao());
		app.setZhuxiaodate(com.getZhuxiaodate());
		app.setPdate(com.getPdate());
		app.setLastid(com.getLastid());
		app.setPrintEquipment(com.getPrintEquipment());
		app.setTestEquipment(com.getTestEquipment());
		app.setRemarks(com.getRemarks());
		app.setBranchId(com.getBranchId());
		app.setBranchName(com.getBranchName());
		app.setQuality(com.getQuality()); 
		app.setElsetype(com.getElsetype());
		app.setId(com.getCompanyId());
		app.setQualityno(com.getQualityno());
		return app;
	}
	public ApplicationForm Change2App(FormChange form){
		ApplicationForm af = new ApplicationForm();
		af.setId(form.getPid());
		af.setTitleno(form.getTitleno());
		if(StringUtils.isNotBlank(form.getRemark())){
			af.setRemarks(form.getRemark());
		}
		if(StringUtils.isNotBlank(form.getCompanynameNew())){
			af.setEnterprisename(form.getCompanynameNew());
		}
		if(StringUtils.isNotBlank(form.getAddressNew())){
			af.setAddress(form.getAddressNew());
		}
		
		if(StringUtils.isNotBlank(form.getLinkmanNew())){
			af.setLinkman(form.getLinkmanNew());
		}
		String materials=form.getMaterialNew();
		if(StringUtils.isNotBlank(materials)){
			if(materials.contains("÷Ω÷ ")){
				af.setPapery(true);
			}else{
				af.setPapery(false);
			}
			if(materials.contains("≤ª∏…Ω∫")){
				af.setPastern(true);
			}else{
				af.setPastern(false);
			}
			if(materials.contains("Õﬂ¿„÷Ω")){
				af.setFrilling(true);
			}else{
				af.setFrilling(false);
			}
			if(materials.contains("Ω Ù")){
				af.setMetal(true);
			}else{
				af.setMetal(false);
			}
			if(materials.contains("À‹¡œ")){
				af.setPlastic(true);
			}else{
				af.setPlastic(false);
			}
			String others[]=materials.split(",");
			String other=others[others.length-1];
			if((!other.equals("÷Ω÷ "))&&(!other.equals("≤ª∏…Ω∫"))&&(!other.equals("Õﬂ¿„÷Ω"))
					&&(!other.equals("Ω Ù"))&&(!other.equals("À‹¡œ"))){
				af.setElsematerial(other);
			}
		}
		String types=form.getPrinttypeNew();
		if(StringUtils.isNotBlank(types)){
			if(types.contains("∆Ω∞ÂΩ∫”°")){
				af.setFlat(true);
			}else{
				af.setFlat(false);
			}
			if(types.contains("∞º∞Ê”°À¢")){
				af.setGravure(true);
			}else{
				af.setGravure(false);
			}
			if(types.contains("ÀøÕ¯”°À¢")){
				af.setWebby(true);
			}else{
				af.setWebby(false);
			}
			if(types.contains("»·–‘∞Ê”°À¢")){
				af.setFlexible(true);
			}else{
				af.setFlexible(false);
			}
			String others[]=types.split(",");
			String other=others[others.length-1];
			if((!other.equals("∆Ω∞ÂΩ∫”°"))&&(!other.equals("∞º∞Ê”°À¢"))
					&&(!other.equals("ÀøÕ¯”°À¢"))&&(!other.equals("»·–‘∞Ê”°À¢"))){
				af.setElsetype(other);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
		try {
			if (StringUtils.isNotBlank(form.getCertappdateNew())) {
				Date date = sdf.parse(form.getCertappdateNew());
				af.setCertappdate(date);
			}
			if (StringUtils.isNotBlank(form.getCerttodateNew())) {
				Date date = sdf.parse(form.getCerttodateNew());
				af.setCerttodate(date);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		af.setStatus(form.getStatus());
		if(StringUtils.isNotBlank(form.getCoporationNew())){
			af.setArtificialperson(form.getCoporationNew());
		}
		if(StringUtils.isNotBlank(form.getLinkmantelNew())){
			af.setLtel(form.getLinkmantelNew());
		}
		if(StringUtils.isNotBlank(form.getCoporationtelNew())){
			af.setAptel(form.getCoporationtelNew());
		}
		if(StringUtils.isNotBlank(form.getPostcodeNew())){
			af.setPostalcode(form.getPostcodeNew());
		}
		return af;
	}
	public FormChange App2Change(CompanyInfo com) {
		FormChange fc = new FormChange();
		fc.setPid(com.getId());
		if(StringUtils.isNotBlank(com.getRemarks())){
			fc.setRemark(com.getRemarks());
		}
		if(StringUtils.isNotBlank(com.getEnterprisename())){
			fc.setCompanynameOld(com.getEnterprisename());
		}
		if(StringUtils.isNotBlank(com.getAddress())){
			fc.setAddressOld(com.getAddress());
		}
		if(StringUtils.isNotBlank(com.getLinkman())){
			fc.setLinkmanOld(com.getLinkman());
		}
		
		String materialOld="";
		if(com.getPapery()!=null&&com.getPapery()){
			materialOld+="÷Ω÷  ";
		}
		if(com.getPastern()!=null&&com.getPastern()){
			materialOld+="≤ª∏…Ω∫ ";
		}
		if(com.getFrilling()!=null&&com.getFrilling()){
			materialOld+="Õﬂ¿„÷Ω  ";
		}
		if(com.getMetal()!=null&&com.getMetal()){
			materialOld+="Ω Ù ";
		}
		if(com.getPlastic()!=null&&com.getPlastic()){
			materialOld+="À‹¡œ ";
		}
		if(StringUtils.isNotBlank(com.getElsematerial())){
			materialOld+=com.getElsematerial();
		}
		fc.setMaterialOld(materialOld);
		
		String printtypeOld="";
		if(com.getFlat()!=null&&com.getFlat()){
			printtypeOld+="∆Ω∞ÂΩ∫”° ";
		}
		if(com.getGravure()!=null&&com.getGravure()){
			printtypeOld+="∞º∞Ê”°À¢ ";
		}
		if(com.getWebby()!=null&&com.getWebby()){
			printtypeOld+="ÀøÕ¯”°À¢   ";
		}
		if(com.getFlexible()!=null&&com.getFlexible()){
			printtypeOld+="»·–‘∞Ê”°À¢ ";
		}

		if(StringUtils.isNotBlank(com.getElsetype())){
			printtypeOld+=com.getElsetype();
		}
		fc.setPrinttypeOld(printtypeOld);

		if(StringUtils.isNotBlank((new SimpleDateFormat("yyyy-MM-dd")).format(com.getCertappdate()))){
			fc.setCertappdateOld((new SimpleDateFormat("yyyy-MM-dd")).format(com.getCertappdate()));
		}
		
		if(StringUtils.isNotBlank((new SimpleDateFormat("yyyy-MM-dd")).format(com.getCerttodate()))){
			fc.setCerttodateOld((new SimpleDateFormat("yyyy-MM-dd")).format(com.getCerttodate()));
		}
		
		fc.setStatus(1);
		
		if(StringUtils.isNotBlank(com.getBranchId())){
			fc.setOffshootorganiz(com.getBranchId());
		}
		
		if(StringUtils.isNotBlank((new SimpleDateFormat("yyyy-MM-dd")).format(com.getCreatedate()))){
			fc.setCreatedate(com.getCreatedate());
		}
		
		if(StringUtils.isNotBlank(com.getArtificialperson())){
			fc.setCoporationOld(com.getArtificialperson());
		}
		
		if(StringUtils.isNotBlank(com.getLtel())){
			fc.setLinkmantelOld(com.getLtel());
		}
		
		if(StringUtils.isNotBlank(com.getAptel())){
			fc.setCoporationtelOld(com.getAptel());
		}
		
		if(StringUtils.isNotBlank(com.getPostalcode())){
			fc.setPostcodeOld(com.getPostalcode());
		}
		
		return fc;
	}
}
