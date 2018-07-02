package com.wzj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wzj.pojo.Region;
import com.wzj.pojo.SysRole;
import com.wzj.pojo.Division;
import com.wzj.pojo.Log;
import com.wzj.service.impl.RegionService;
import com.wzj.util.AddLog;
import com.wzj.service.impl.DivisionService;

@Controller
@RequestMapping("/divisionRegion")
public class DivisionRegionController{

	@Autowired
	private RegionService RegionService;
	@Autowired
	private DivisionService divisionService;
	
	@RequestMapping("/getAllDivision")
	@ResponseBody
	public List<Division> getAllDivision(){
		AddLog.addLog(Log.QUERY,"查询所有分中心信息");
		return divisionService.getAllDivision();
	}
	
	//通过码获取分支机构对象
	@RequestMapping("/getDivisionByCode")
	@ResponseBody
	public Object getDivisionByCode(String code){
		Division division=divisionService.getDivisionByCode(code);
		return division;
	}
	
	//通过名获取分支机构对象
	@RequestMapping("/getDivisionByName")
	@ResponseBody
	public Object getDivisionByName(String name){
		Division division=divisionService.getDivisionByName(name);
		return division;
	}
	
	//通过码获取地区对象
	@RequestMapping("/getRegionByCode")
	@ResponseBody
	public Object getRegionByCode(String code){
		Region region=RegionService.getRegionByCode(code);
		return region;
	}
	
	//通过名获取地区对象
	@RequestMapping("/getRegionByName")
	@ResponseBody
	public Object getRegionByName(String name){
		Region region=RegionService.getRegionByName(name);
		return region;
	}
	
	//获取地区级别
	@RequestMapping("/getRegionLevel")
	@ResponseBody
	public int getRegionLevel(String regioncode){
		Region region=RegionService.getRegionByCode(regioncode);
		int level=RegionService.getLevel(region);
		return level;
	}
	
	//获取地区子节点
	@RequestMapping("/getRegionChildren")
	@ResponseBody
	public List<Region> getRegionChildren(String regioncode){
		Region region=RegionService.getRegionByCode(regioncode);
		List<Region> children = RegionService.getChildren(region);
		return children;
	}
	
	//通过地区获取分支机构
	@RequestMapping("/getDivisionByRegion")
	@ResponseBody
	public List<Division> getDivisionByRegion(String regioncode){
		Region region=RegionService.getRegionByCode(regioncode);
		String provincename=RegionService.getRegionByCode(region.getProvincecode()).getRegionname();
		String cityname=RegionService.getRegionByCode(region.getCitycode()).getRegionname();
		List<Division> divisions = divisionService.getDivisionByRegion(provincename,cityname);
		return divisions;
	}
	
	//通过分支机构获取地区
	@RequestMapping("/getRegionByDivision")
	@ResponseBody
	public List<Region> getRegionByDivision(String divisioncode){
		Division division=divisionService.getDivisionByCode(divisioncode);
		List<Region> regions = RegionService.getRegionByDivision(division.getDivisionname());
		return regions;
	}
	
	/*
	//省
	@RequestMapping("/getProvince")
	@ResponseBody
	public List<Region> getProvince(){
		String regioncode="000000000000";
		Region region=RegionService.getRegionByCode(regioncode);
		List<Region> children = RegionService.getChildren(region);
		return children;
	}
	//市
		@RequestMapping("/getCity")
		@ResponseBody
		public List<Region> getCity(String regioncode){
			Region region=RegionService.getRegionByCode(regioncode);
			List<Region> children = RegionService.getChildren(region);
			return children;
		}
		//区
		@RequestMapping("/getCountry")
		@ResponseBody
		public List<Region> getCountry(String regioncode){
			Region region=RegionService.getRegionByCode(regioncode);
			List<Region> children = RegionService.getChildren(region);
			return children;
		}
	*/
}