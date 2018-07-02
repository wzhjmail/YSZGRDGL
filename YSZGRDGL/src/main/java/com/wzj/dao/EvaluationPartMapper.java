package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzj.pojo.EvaluationPart;

public interface EvaluationPartMapper {
    int deleteById(Integer id);

    int insert(EvaluationPart record);

    EvaluationPart selectById(Integer id);

    int updateById(EvaluationPart record);
    
    @Select({"select * from sys_eval_part where evalId=#{eid,jdbcType=INTEGER}"})
	List<EvaluationPart> getByEId(Integer eid);

    @Select({"select * from sys_eval_part where evalId=#{evalid,jdbcType=INTEGER} and result=#{result,jdbcType=VARCHAR}"})
	@RequestMapping("BaseResultMap")
    EvaluationPart getResultNote(EvaluationPart ep);
}