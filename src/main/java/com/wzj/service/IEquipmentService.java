package com.wzj.service;

import java.util.List;

import com.wzj.pojo.Equipment;

public interface IEquipmentService {
	public int deleteById(int id);
	public int insert(Equipment equipment);
	public Equipment selectById(int id);
	public int update(Equipment equipment);
	//���ݷ�֧����id��ѯ�Ļ����µ������豸��Ϣ
	public List<Equipment> selectByBId(String bId);
}
