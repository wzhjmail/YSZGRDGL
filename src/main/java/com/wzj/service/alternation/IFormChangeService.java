package com.wzj.service.alternation;

import com.wzj.pojo.FormChange;

public interface IFormChangeService {
	public int insertFormChange(FormChange form);
	public FormChange getForm(int id);
	//�޸�״̬
	public int updateStatus(int id, int status);
	
	public int update(FormChange form);
}
