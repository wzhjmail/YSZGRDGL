package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.DTO.AppDTO;
import com.wzj.pojo.FormChange;

public interface FormChangeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FormChange record);

    FormChange selectByPrimaryKey(Integer id);

    int update(FormChange record);

    @Update({"update ys_change set status=#{status,jdbcType=INTEGER}",
    	"where id=#{id,jdbcType=INTEGER}"})
	int updateStatus(@Param("id")int id, @Param("status")int status);
    				 
	List<FormChange> findByIds(@Param("list")List<String> businessIds,@Param("branchId")String branchId);
	
	List<FormChange> findByIds2(@Param("list")List<String> businessIds);

	FormChange getChangeFormById(int id);

	List<FormChange> getOngoing(AppDTO appdto);

	@Select({"select * from ys_change where PID=#{pid,jdbcType=INTEGER}"})
	@ResultMap("BaseResultMap")
	FormChange getChangeFormByPId(Integer pid);

	List<FormChange> getOngoingByBranch(AppDTO appdto);

	List<FormChange> getOngoingAll(AppDTO appdto);

	List<FormChange> getOngoingByStatus(AppDTO appdto);
	
	List<FormChange> getOngoingById(@Param("status")int status,@Param("branchId")String branchId);
	
	@Select({"select * from ys_change where titleno=#{titleNo,jdbcType=VARCHAR}"})
	@ResultMap("BaseResultMap")
	FormChange getByTaskId(String titleNo);
}