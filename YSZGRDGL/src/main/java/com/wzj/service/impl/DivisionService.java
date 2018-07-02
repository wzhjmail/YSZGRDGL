package com.wzj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.DTO.DivisionDTO;
import com.wzj.dao.DivisionMapper;
import com.wzj.pojo.Division;
import com.wzj.service.IDivisionService;

@Service("DivisionService")
public class DivisionService implements IDivisionService{
	
	@Autowired
	private DivisionMapper divisionMapper;
	
	@Override
	public List<Division> getAllDivision(){
		return divisionMapper.getAllDivision();
	}
	
	
	//ͨ�����ȡ��֧��������
	@Override
	public Division getDivisionByCode(String code){
		return divisionMapper.getDivisionByCode(code);
	}
	
	//ͨ������ȡ��֧��������
	@Override
	public Division getDivisionByName(String name){
		return divisionMapper.getDivisionByName(name);
	}
	
	//ͨ��������ȡ��֧����
	@Override
	public List<Division> getDivisionByRegion(String provincename,String cityname){
		return divisionMapper.getDivisionByRegion(provincename,cityname);
	}

	public int getDivCountByCode(String code) {
		return divisionMapper.getDivCountByCode(code);
	}

	public void insert(Division div) {
		divisionMapper.insert(div);
	}

	public Division getByCode(String code) {
		return divisionMapper.getByCode(code);
	}

	public void setRegionId(String code, String regionCode) {
		divisionMapper.setRegionId(code,regionCode);
	}

	public void deleteBranch(String code) {
		divisionMapper.deleteBranch(code);
	}

	public int deleteBranchByCode(String code) {
		return divisionMapper.deleteBranchByCode(code);
	}

	public int updateBranchByCode(DivisionDTO divdto) {
		return divisionMapper.updateBranchByCode(divdto);
	}

	public void updateBranch(String branchcode, String regionCode, String code) {
		divisionMapper.updateBranch(branchcode,regionCode,code);
	}
	
	public void setBname(String id,String name){
		divisionMapper.setBname(id,name);
	}


	public Division getDivisionLikeName(String name) {
		return divisionMapper.getDivisionLikeName("%"+name+"%");
	}


	public int countBranch(String name) {
		return divisionMapper.countBranch(name);
	}


	public Division getByDivCode(String code) {
		return divisionMapper.getByDivCode(code);
	}

}