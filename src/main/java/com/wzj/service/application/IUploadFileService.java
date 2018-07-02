package com.wzj.service.application;

import com.wzj.pojo.UploadFile;

public interface IUploadFileService {
	public int insert(UploadFile uf);
	public String getPathByCode(String cNo);
	public int deleteByCode(String code);
}
