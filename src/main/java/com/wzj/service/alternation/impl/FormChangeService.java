package com.wzj.service.alternation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.DTO.AppDTO;
import com.wzj.dao.FormChangeMapper;
import com.wzj.pojo.FormChange;
import com.wzj.service.alternation.IFormChangeService;

@Service("formChangeService")
public class FormChangeService implements IFormChangeService {

	@Autowired
	private FormChangeMapper formChangeMapper;
	@Override
	public int insertFormChange(FormChange form) {
		return formChangeMapper.insert(form);
	}
	public int updateStatus(int id, int status) {
		return formChangeMapper.updateStatus(id,status);
	}
	public List<FormChange> findBusinessByTasks(Set<String> businessIds,String branchId) {
		List<String> list = new ArrayList<>();
		list.addAll(businessIds);
		list.add("");
		return formChangeMapper.findByIds(list,branchId);
	}
	public List<FormChange> findBusinessByTasks(Set<String> businessIds) {
		List<String> list = new ArrayList<>();
		list.addAll(businessIds);
		list.add("");
		return formChangeMapper.findByIds2(list);
	}
	public FormChange getForm(int id) {
		return formChangeMapper.selectByPrimaryKey(id);
	}
	public FormChange getChangeFormById(int id) {
		return formChangeMapper.getChangeFormById(id);
	}
	public int update(FormChange form) {
		return formChangeMapper.update(form);
	}
	public List<FormChange> getOngoing(AppDTO appdto) {
		return formChangeMapper.getOngoing(appdto);
	}
	public FormChange getChangeFormByPId(Integer pid) {
		return formChangeMapper.getChangeFormByPId(pid);
	}
	public List<FormChange> getOngoingByBranch(AppDTO appdto) {
		return formChangeMapper.getOngoingByBranch(appdto);
	}
	public List<FormChange> getOngoingAll(AppDTO appdto) {
		return formChangeMapper.getOngoingAll(appdto);
	}
	public List<FormChange> getOngoingByStatus(AppDTO appdto) {
		return formChangeMapper.getOngoingByStatus(appdto);
	}
	public List<FormChange> getOngoingByStatus(AppDTO appdto,String branchId) {
		if(branchId.equals("0000"))
			return formChangeMapper.getOngoingByStatus(appdto);
		else
			return formChangeMapper.getOngoingById(appdto.getStatus(),branchId);
	}
	public FormChange getByTaskId(String titleNo) {
		return formChangeMapper.getByTaskId(titleNo);
	}
	public int deleteById(int id) {
		return formChangeMapper.deleteByPrimaryKey(id);
	}

}
