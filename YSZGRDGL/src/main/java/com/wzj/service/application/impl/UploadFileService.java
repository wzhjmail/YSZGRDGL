package com.wzj.service.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.UploadFileMapper;
import com.wzj.pojo.UploadFile;
import com.wzj.service.application.IUploadFileService;

@Service("uploadFileService")
public class UploadFileService implements IUploadFileService{

	@Autowired
	private UploadFileMapper uploadFileMapper;
	
	@Override
	public int insert(UploadFile uf) {
		return uploadFileMapper.insert(uf);
	}

	public String getPathByCode(String cNo) {
		return uploadFileMapper.getPathByCode(cNo);
	}
	
	public int deleteByCode(String code){
		return uploadFileMapper.deleteByCode(code);
	}

	public String getPath(String titleNo, String type) {
		return uploadFileMapper.getPath(titleNo,type);
	}

	public String getPath(String titleNo, String describe, String type) {
		return uploadFileMapper.getPath2(titleNo,describe,type);
	}
	
	public void delete(String titleNo, String type) {
		uploadFileMapper.delete(titleNo,type);
	}
	
	public List<UploadFile> getSampleAttach(String titleNo,String sampleId){
		return uploadFileMapper.getSampleAttach(titleNo,sampleId);
	}
	
	public List<UploadFile> getSampleAttach(String titleNo,String sampleId,String type){
		return uploadFileMapper.getSampleAttach2(titleNo,sampleId,type);
	}

	public void delete(String titleNo, String describe, String type) {
		uploadFileMapper.delete2(titleNo,describe,type);
	}
	
	public void deleteById(String temId){
		uploadFileMapper.deleteByPrimaryKey(Integer.parseInt(temId));
	}

	public List<UploadFile> getSample(String titlenum, String describe,String code) {
		return uploadFileMapper.getSample(titlenum,describe,code);
	}

	public void deleteByDescribeId(String code) {
		uploadFileMapper.deleteByDescribeId(code);
	}

	public List<UploadFile> getByTitleNo(String titleNo) {
		return uploadFileMapper.getByTitleNo(titleNo);
	}

	public List<UploadFile> getBydesAndTitleNo(String titleNo, String desribe, String describe1) {
		return uploadFileMapper.getBydesAndTitleNo(titleNo,desribe,describe1);
	}

	public List<UploadFile> getByCode(String titleNO) {
		return uploadFileMapper.getByCode(titleNO);
	}

	public UploadFile getById(int id) {
		return uploadFileMapper.getById(id);
	}

	public List<UploadFile> checkUploadedReport(String titleNo,String UpDescribe, String simpleIds) {
		return uploadFileMapper.checkUploadedReport(titleNo,UpDescribe,simpleIds);
	}

	public List<UploadFile> getByType(String titleNO, String type) {
		return uploadFileMapper.getByType(titleNO,type);
	}

}
