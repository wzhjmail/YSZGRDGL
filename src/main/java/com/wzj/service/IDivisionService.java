package com.wzj.service;

import java.util.List;
import com.wzj.pojo.Division;
import com.wzj.pojo.Region;

public interface IDivisionService{

	public List<Division> getAllDivision();
	public Division getDivisionByCode(String code);
	public Division getDivisionByName(String name);
	public List<Division> getDivisionByRegion(String provincename,String cityname);
	public Division getByDivCode(String code);
}