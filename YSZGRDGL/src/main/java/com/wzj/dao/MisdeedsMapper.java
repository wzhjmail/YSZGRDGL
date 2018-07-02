package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.pojo.Misdeeds;

public interface MisdeedsMapper {
    int deleteById(Integer id);

    int insert(Misdeeds record);

    Misdeeds selectById(Integer id);

    int update(Misdeeds record);

    @Select({"select * from ys_misdeeds where companyId=",
    	"#{companyId,jdbcType=INTEGER}"})
	List<Misdeeds> getMisByCompanyId(int companyId);
    
    @Update({"update ys_misdeeds set enclosure=#{newpath,jdbcType=VARCHAR} ",
    		"where id=#{misId,jdbcType=INTEGER}"})
	void setEnclosure(@Param("misId")int misId, @Param("newpath")String newpath);
    
    @Select({"select * from ys_misdeeds where id=",
	"#{misdeedId,jdbcType=INTEGER}"})
    Misdeeds getMisById(int misdeedId);
}