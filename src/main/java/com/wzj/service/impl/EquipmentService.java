package com.wzj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.EquipmentMapper;
import com.wzj.pojo.Equipment;
import com.wzj.service.IEquipmentService;

@Service("equipmentService")
public class EquipmentService implements IEquipmentService {
	@Autowired
	private EquipmentMapper equipmentMapper;
	
	@Override
	public int deleteById(int id) {
		return equipmentMapper.deleteById(id);
	}

	@Override
	public int insert(Equipment equipment) {
		return equipmentMapper.insert(equipment);
	}

	@Override
	public Equipment selectById(int id) {
		return equipmentMapper.selectById(id);
	}

	@Override
	public int update(Equipment equipment) {
		return equipmentMapper.update(equipment);
	}

	@Override
	public List<Equipment> selectByBId(String bId) {
		return equipmentMapper.selectByBId(bId);
	}
	
	public List<Equipment> getAll() {
		return equipmentMapper.getAll();
	}

}
