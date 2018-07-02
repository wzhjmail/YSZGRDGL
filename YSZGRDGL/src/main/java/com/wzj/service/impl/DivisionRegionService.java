package com.wzj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.DTO.DivRegionDTO;
import com.wzj.dao.DivisionRegionMapper;
import com.wzj.dao.RegionMapper;
import com.wzj.service.IDivisionRegionService;
@Service("divisionRegionService")
public class DivisionRegionService implements IDivisionRegionService{

	@Autowired
	private DivisionRegionMapper divisionRegionMapper;

	public DivRegionDTO getDivRegion(String regionCode) {
		return divisionRegionMapper.getDivRegion(regionCode);
	}

	public List<DivRegionDTO> findAll() {
		return divisionRegionMapper.findAll();
	}

	public DivRegionDTO getDivRegionById(int id) {
		return divisionRegionMapper.getDivRegionById(id);
	}

	public void updateDivRegById(DivRegionDTO divregDTO) {
		divisionRegionMapper.updateDivRegById(divregDTO);
	}

	//查找多方根据分中心编码
	public List<DivRegionDTO> findByDivcode(String code) {
		code="%" + code + "%";
		return divisionRegionMapper.findByDivcode(code);
	}

	//删除中间表中先前存在的
	public void deletePre(String branchcode) {
		divisionRegionMapper.deletePre(branchcode);
	}

	//向中间表中插入修改后的数据
	public void insertDivReg(String regioncode, String code) {
		divisionRegionMapper.insertDivReg(regioncode,code);
	}

	public List<DivRegionDTO> getDivRegByCode(String code) {
		code="%"+code+"%";
		return divisionRegionMapper.getDivRegByCode(code);
	}
}
