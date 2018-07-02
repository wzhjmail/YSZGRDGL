package com.wzj.service;

import java.util.List;

import com.wzj.pojo.Region;

public interface IRegionService {
	
	public Region getRegionByCode(String code);
	
	public Region getRegionByName(String name);
	
	public int getLevel(Region region);
	
	public List<Region> getChildren(Region parent);
	
	public List<Region> getRegionByDivision(String citycode);
	
}