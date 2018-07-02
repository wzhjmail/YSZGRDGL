package com.wzj.dao;

import com.wzj.pojo.PrintEquipment;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PrintEquipmentMapper {
    int insert(PrintEquipment record);
    int updateById(PrintEquipment record);

    /*@Insert({"insert into ys_print (id,companyId,companyName,printModel,printPlace,printNumber,printNotes) ",
            "values (#{id,jdbcType=INTEGER},#{companyId,jdbcType=INTEGER}," +
                    "#{companyName,jdbcType=VARCHAR},#{printModel,jdbcType=VARCHAR}," +
                    "#{printPlace,jdbcType=VARCHAR},#{printNumber,jdbcType=INTEGER}," +
                    "#{printNotes,jdbcType=VARCHAR}"})
    int insertPrint(PrintEquipment printEquipment);*/

    @Select({"select * from ys_print where id=#{id,jdbcType=INTEGER}"})
    PrintEquipment getById(int id);

    @Select({"select * from ys_print where companyId=#{companyId,jdbcType=INTEGER}"})
    List<PrintEquipment> getByCompanyId(int companyId);

    @Delete({"delete from ys_print where id=#{id,jdbcType=INTEGER}"})
    int deleteById(int id);

    @Select({"select * from ys_print where companyName=#{companyName,jdbcType=VARCHAR}"})
    List<PrintEquipment> getByCompanyName(String companyName);

    @Update({"update ys_print set companyId=#{companyId,jdbcType=INTEGER}",
            "where companyName=#{companyName,jdbcType=VARCHAR}"})
    int updateByCompanyName(@Param("companyName")String companyName, @Param("companyId")int companyId);
	
    @Delete({"delete from ys_print where companyName=#{companyName,jdbcType=VARCHAR}"})
    void deleteByCompanyName(@Param("companyName")String companyName);
    
    @Update({"update ys_print set companyName=#{companyName,jdbcType=VARCHAR}",
    "where companyName=#{oldName,jdbcType=VARCHAR}"})
    void updateCompanyName(@Param("oldName")String oldName, @Param("companyName")String companyName);
}
