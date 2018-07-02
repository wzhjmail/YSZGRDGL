package com.wzj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.DTO.DivRegionDTO;
import com.wzj.DTO.DivisionDTO;
import com.wzj.dao.BranchCenterMapper;
import com.wzj.pojo.Division;
import com.wzj.pojo.Region;
import com.wzj.service.IBranchCenterService;
@Service("branchCenterService")
public class BranchCenterService implements IBranchCenterService{

	@Autowired
	private BranchCenterMapper branchCenterMapper;

	@Override
	public List<Division> findAll() {
		return branchCenterMapper.findAll();
	}
	
	public Division findByDId(String divisionId) {
		return branchCenterMapper.getDivisionByCode(divisionId);
	}
	
	public List<Region> getAllRegion() {
		return branchCenterMapper.getAllRegion();
	}

	public Division getDivisionByCode(String code) {
		return branchCenterMapper.getDivisionByCode(code);
	}

	@SuppressWarnings("unchecked")
	public List<String> getregionCodeByCode(String code) {
		code="%"+code+"%";
		return branchCenterMapper.getregionCodeByCode(code);
	}

	public int getDivRegionCount(String regionCode) {
		return branchCenterMapper.getDivRegionCount(regionCode);
	}

	public void updateDivRegion(DivRegionDTO drDTO1) {
		branchCenterMapper.updateDivRegion(drDTO1);
	}
}
