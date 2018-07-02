package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.wzj.DTO.EvaluationDTO;
import com.wzj.pojo.Evaluation;
import com.wzj.pojo.EvaluationPart;

public interface EvaluationMapper {
    int deleteById(Integer id);

    int insert(Evaluation record);

    Evaluation selectById(Integer id);

    int update(Evaluation record);
    
    @Select({"select * from sys_eval"})
    List<Evaluation> getAll();

    @Select({"select * from sys_eval where available=true"})
	List<Evaluation> getUsing();

    @Select({"select * from sys_eval where available=true"})
	List<EvaluationDTO> getAllEva();

    @Select({"select max(num) from sys_eval"})
	int getLastId();

    @Select({"select count(*) from sys_eval_part where evalId=#{evalId,jdbcType=INTEGER}"})
	int countResult(int evalId);

    @Select({"select * from sys_eval where num=#{evalId,jdbcType=INTEGER}"})
	Evaluation getByNum(int evalId);
    
}