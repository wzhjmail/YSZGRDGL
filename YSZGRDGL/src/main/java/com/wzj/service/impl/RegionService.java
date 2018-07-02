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
		
	//通过码获取地区对象
	@Override
	public Region getRegionByCode(String code){
		return RegionMapper.getRegionByCode(code);
	}
	
	//通过名获取地区对象
	@Override
	public Region getRegionByName(String name){
		return RegionMapper.getRegionByName(name);
	}

	//获取地区级别
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
	
	//获取地区子节点
	@Override
	public List<Region> getChildren(Region parent){
		String code=parent.getRegioncode();
		return RegionMapper.getChildren(code);
	}
	
	//通过分支机构获取地区
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