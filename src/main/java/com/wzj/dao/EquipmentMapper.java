package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.Equipment;

public interface EquipmentMapper {
    int deleteById(Integer id);

    int insert(Equipment record);

    Equipment selectById(Integer id);

    int update(Equipment record);
    
    @Select({"select * from ys_equip where ",
    	"branchId=#{bId,jdbcType=VARCHAR}"})
    List<Equipment> selectByBId(String bId);
    
    @Select({"select * from ys_equip"})
    List<Equipment> getAll();
}