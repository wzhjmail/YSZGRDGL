package com.wzj.service;

import java.util.List;

import com.wzj.pojo.Equipment;

public interface IEquipmentService {
	public int deleteById(int id);
	public int insert(Equipment equipment);
	public Equipment selectById(int id);
	public int update(Equipment equipment);
	//根据分支机构id查询改机构下的所有设备信息
	public List<Equipment> selectByBId(String bId);
}
