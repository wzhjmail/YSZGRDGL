package com.wzj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.RegionMapper;
import com.wzj.pojo.Region;
import com.wzj.service.IRegionService;

@Service("RegionService")
public class RegionService implements IRegionService{
	
	@Autowired
	private RegionMapper RegionMapper;
		
	//ͨ�����ȡ��������
	@Override
	public Region getRegionByCode(String code){
		return RegionMapper.getRegionByCode(code);
	}
	
	//ͨ������ȡ��������
	@Override
	public Region getRegionByName(String name){
		return RegionMapper.getRegionByName(name);
	}

	//��ȡ��������
	@Override
	public int getLevel(Region region){
		String parent=region.getParentcode();
		String province=region.getProvincecode();
		if(parent.equals("000000000000"))
			return 0;
		else if(parent.equals(province))
			return 1;
		else 
			return 2;
	}
	
	//��ȡ�����ӽڵ�
	@Override
	public List<Region> getChildren(Region parent){
		String code=parent.getRegioncode();
		return RegionMapper.getChildren(code);
	}
	
	//ͨ����֧������ȡ����
	@Override
	public List<Region> getRegionByDivision(String divname){
		String citycode=RegionMapper.getCityCodeByDivisionName(divname);
		return RegionMapper.getRegionByDivision(citycode);
	}

	public List<Region> getAllRegion() {
		return RegionMapper.getAllRegion();
	}

	public List<Region> getAllRegionByList(List<String> list) {
		return RegionMapper.getAllRegionByList(list);
	}
}