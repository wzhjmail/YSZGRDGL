package com.wzj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.TestingEquipMapper;
import com.wzj.pojo.TestingEquip;

@Service("testingEquipService")
public class TestingEquipService {
	@Autowired
	private TestingEquipMapper testingEquipMapper;
	
	public int insert(TestingEquip record){
		return testingEquipMapper.insert(record);
	}
	
	public int updateById(TestingEquip record){
		return testingEquipMapper.updateById(record);
	}
	
	public TestingEquip getById(int id){
		return testingEquipMapper.getById(id);
	}
	
	public List<TestingEquip> getByCompanyId(int companyId){
		return testingEquipMapper.getByCompanyId(companyId);
	}

	public int deleteById(int id) {
		return testingEquipMapper.deleteById(id);
	}

	public List<TestingEquip> getByCompanyName(String companyName) {
		return testingEquipMapper.getByCompanyName(companyName);
	}
	
	public int updateByCompanyName(String companyName,int companyId) {
		return testingEquipMapper.updateByCompanyName(companyName,companyId);
	}

	public void deleteByComName(String companyName) {
		testingEquipMapper.deleteByComName(companyName);
	}

	public void updateCompanyName(String oldName, String companyName) {
		testingEquipMapper.updateCompanyName(oldName,companyName);
	}

}
