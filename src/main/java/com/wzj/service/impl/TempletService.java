package com.wzj.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wzj.service.ITempletService;
import com.wzj.util.UploadFileUtil;
import com.wzj.pojo.UploadFile;
import com.wzj.dao.UploadFileMapper;

@Service("templetService")
public class TempletService implements ITempletService{
	
	@Autowired
	private UploadFileMapper uploadFileMapper;
	
	@Override
	public List<UploadFile> getAll(){
		return uploadFileMapper.getAllByCode("MB0000");
	}
	
	public Integer getIdByDesId(String desId){
		return uploadFileMapper.getIdByDesId(desId);
	}
	
	public void delete(String id,HttpServletRequest request){
		UploadFile uf = uploadFileMapper.selectByPrimaryKey(Integer.parseInt(id));
		String filePath = request.getSession().getServletContext().getRealPath("./");
		File file = new File(filePath+"/"+uf.getUprul());
		uploadFileMapper.deleteByPrimaryKey(Integer.parseInt(id));
		file.delete();
	}
	
	public String download(String id){
		return uploadFileMapper.selectByPrimaryKey(Integer.parseInt(id)).getUprul();
	}
	
	public UploadFile getDesById(String id){
		return uploadFileMapper.selectByPrimaryKey(Integer.parseInt(id));
	}
}