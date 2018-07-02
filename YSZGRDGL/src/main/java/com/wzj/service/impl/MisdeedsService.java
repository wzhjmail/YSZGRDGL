package com.wzj.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wzj.dao.ApplicationFormMapper;
import com.wzj.dao.MisdeedsMapper;
import com.wzj.pojo.ApplicationForm;
import com.wzj.pojo.Misdeeds;
import com.wzj.service.IMisdeedsService;
import com.wzj.util.UploadFileUtil;
@Service("misdeedsService")
public class MisdeedsService implements IMisdeedsService {
	@Autowired
	private MisdeedsMapper misdeedsMapper;
	@Autowired
	private ApplicationFormMapper appMapper;
	
	@Override
	public List<Misdeeds> getMisByCompanyId(int companyId) {
		return misdeedsMapper.getMisByCompanyId(companyId);
	}

	public void insertMis(Misdeeds mis,HttpServletRequest request) {
		misdeedsMapper.insert(mis);
		int misId=mis.getId();
		//文件上传
		int comId=mis.getCompanyid();
		ApplicationForm  app=appMapper.getLastAppBycid(comId);
		String titleNo=app.getTitleno();
		titleNo=titleNo.replaceFirst("XB", "MI");
		titleNo=titleNo.replaceFirst("FR", "MI");
		titleNo=titleNo.replaceFirst("BG", "MI");
		//String path="/upload/img/XB/"+titleNo;
		MultipartFile file=mis.getMfile();
		if(file!=null){//如果存在文件
			UploadFileUtil uf = new UploadFileUtil();
			Map<String,String> map=uf.uploadFile(file,titleNo,request);
			String newpath=map.get("path");
			//更新misdeed,传入文件路径
			misdeedsMapper.setEnclosure(misId,newpath);
		}
	}
	
	public void updateMis(Misdeeds mis,HttpServletRequest request) {
		misdeedsMapper.update(mis);
		int misId=mis.getId();
		//文件上传
		int comId=mis.getCompanyid();
		ApplicationForm  app=appMapper.getLastAppBycid(comId);
		String titleNo=app.getTitleno().replace(app.getTitleno().substring(0, 2), "MI");
		MultipartFile file=mis.getMfile();
		if(!file.isEmpty()){//如果存在文件
			UploadFileUtil uf = new UploadFileUtil();
			Map<String,String> map=uf.uploadFile(file,titleNo,request);
			String newpath=map.get("path");
			//更新misdeed,传入文件路径
			misdeedsMapper.setEnclosure(misId,newpath);
		}
	}
	
	public Misdeeds getMisById(int misdeedId) {
		return misdeedsMapper.getMisById(misdeedId);
	}

	public void deleteMisdeedById(int misdeedId) {
		misdeedsMapper.deleteById(misdeedId);
	}
}
