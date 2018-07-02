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
		AddLog.addLog(Log.QUERY,"��ѯ���з�������Ϣ");
		return divisionService.getAllDivision();
	}
	
	//ͨ�����ȡ��֧��������
	@RequestMapping("/getDivisionByCode")
	@ResponseBody
	public Object getDivisionByCode(String code){
		Division division=divisionService.getDivisionByCode(code);
		return division;
	}
	
	//ͨ������ȡ��֧��������
	@RequestMapping("/getDivisionByName")
	@ResponseBody
	public Object getDivisionByName(String name){
		Division division=divisionService.getDivisionByName(name);
		return division;
	}
	
	//ͨ�����ȡ��������
	@RequestMapping("/getRegionByCode")
	@ResponseBody
	public Object getRegionByCode(String code){
		Region region=RegionService.getRegionByCode(code);
		return region;
	}
	
	//ͨ������ȡ��������
	@RequestMapping("/getRegionByName")
	@ResponseBody
	public Object getRegionByName(String name){
		Region region=RegionService.getRegionByName(name);
		return region;
	}
	
	//��ȡ��������
	@RequestMapping("/getRegionLevel")
	@ResponseBody
	public int getRegionLevel(String regioncode){
		Region region=RegionService.getRegionByCode(regioncode);
		int level=RegionService.getLevel(region);
		return level;
	}
	
	//��ȡ�����ӽڵ�
	@RequestMapping("/getRegionChildren")
	@ResponseBody
	public List<Region> getRegionChildren(String regioncode){
		Region region=RegionService.getRegionByCode(regioncode);
		List<Region> children = RegionService.getChildren(region);
		return children;
	}
	
	//ͨ��������ȡ��֧����
	@RequestMapping("/getDivisionByRegion")
	@ResponseBody
	public List<Division> getDivisionByRegion(String regioncode){
		Region region=RegionService.getRegionByCode(regioncode);
		String provincename=RegionService.getRegionByCode(region.getProvincecode()).getRegionname();
		String cityname=RegionService.getRegionByCode(region.getCitycode()).getRegionname();
		List<Division> divisions = divisionService.getDivisionByRegion(provincename,cityname);
		return divisions;
	}
	
	//ͨ����֧������ȡ����
	@RequestMapping("/getRegionByDivision")
	@ResponseBody
	public List<Region> getRegionByDivision(String divisioncode){
		Division division=divisionService.getDivisionByCode(divisioncode);
		List<Region> regions = RegionService.getRegionByDivision(division.getDivisionname());
		return regions;
	}
	
	/*
	//ʡ
	@RequestMapping("/getProvince")
	@ResponseBody
	public List<Region> getProvince(){
		String regioncode="000000000000";
		Region region=RegionService.getRegionByCode(regioncode);
		List<Region> children = RegionService.getChildren(region);
		return children;
	}
	//��
		@RequestMapping("/getCity")
		@ResponseBody
		public List<Region> getCity(String regioncode){
			Region region=RegionService.getRegionByCode(regioncode);
			List<Region> children = RegionService.getChildren(region);
			return children;
		}
		//��
		@RequestMapping("/getCountry")
		@ResponseBody
		public List<Region> getCountry(String regioncode){
			Region region=RegionService.getRegionByCode(regioncode);
			List<Region> children = RegionService.getChildren(region);
			return children;
		}
	*/
}