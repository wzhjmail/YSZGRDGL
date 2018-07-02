package com.wzj.service.alternation;

import com.wzj.pojo.FormChange;

public interface IFormChangeService {
	public int insertFormChange(FormChange form);
	public FormChange getForm(int id);
	//ÐÞ¸Ä×´Ì¬
	public int updateStatus(int id, int status);
	
	public int update(FormChange form);
}
