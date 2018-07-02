package com.wzj.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wzj.controller.CommonsController;
import com.wzj.dao.PbtSampleMapper;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.PbtSample;
import com.wzj.util.UploadFileUtil;

@Service("pbtSampleService")
public class PbtSampleService {
	@Autowired
	private PbtSampleMapper pbtSampleMapper;
	@Autowired
	private CommonsController commonsController;
	
	public void update(PbtSample ps) {
		pbtSampleMapper.update(ps);
		//样品编号
//				String code=ps.getUf_sample_code();
//				//文件上传
//				String path="/upload/img/XB/"+code;
//				MultipartFile file=ps.getMfile();
//				if(file!=null){//如果存在文件
//					UploadFileUtil uf = new UploadFileUtil();
//					Map<String,String> map=uf.uploadFile(file,code);
//					String newpath=map.get("path");
//					//更新pbtSample,传入文件路径
//					pbtSampleMapper.setUf_attach(code,newpath);
//				}
	}

	public void insert(PbtSample ps) {
		pbtSampleMapper.insert(ps);
	}

	public int delete(String code) {
		System.out.println("deleteService");
		return pbtSampleMapper.delete(code);
	}

	public List<PbtSample> getSampleByCom(String comName) {
		return pbtSampleMapper.getSampleByCom(comName);
	}
	
	public PbtSample getSampleByCode(String code,String comName) {
		return pbtSampleMapper.getSampleByCodeAndName(code,comName);
	}
	
	public PbtSample getSampleByStatus(String status,String comName) {
		return pbtSampleMapper.getSampleByStatus(status,comName);
	}

	public void uploadSampleReport(String comName, String code, String newpath) {
		pbtSampleMapper.uploadSampleReport(comName,code, newpath);
	}
	
	public void setSampleResult( String code,String comName, boolean result) {
		pbtSampleMapper.setSampleResult(code,comName,result);
	}
	
	public String getNum(String sampleId,String des){
		return pbtSampleMapper.getNum(sampleId,des);
	}

	public int getSampleCount(String code) {
		return pbtSampleMapper.getSampleCount(code);
	}

	public String getMaxId(String titleNo) {
		return pbtSampleMapper.getMaxId(titleNo+"%");
	}

	public void setStatus(String uf_sample_code, String status) {
		 pbtSampleMapper.setStatus(uf_sample_code,status);
	}
}
