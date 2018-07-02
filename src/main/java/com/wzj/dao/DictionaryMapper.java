package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.pojo.Dictionary;

public interface DictionaryMapper {
 
	@Insert({"insert into sys_dictionary(type,number,meaning) "
			+ "values(#{type,jdbcType=VARCHAR},#{number,jdbcType=INTEGER},"
			+ "#{meaning,jdbcType=VARCHAR})"})
	int insert(Dictionary dictionary);
	
	@Select({"select * from sys_dictionary"})
	List<Dictionary> findAll();

	@Select({"select * from sys_dictionary where id=#{id,jdbcType=INTEGER}"})
	Dictionary find(int id);

	@Update({"update sys_dictionary set type=#{type,jdbcType=VARCHAR},",
				"number=#{number,jdbcType=INTEGER},meaning=#{meaning,jdbcType=VARCHAR}",
				"where id=#{id,jdbcType=INTEGER}"})
	int update(Dictionary dic);

	@Delete({"delete from sys_dictionary where id=#{id,jdbcType=INTEGER}"})
	int delete(int id);
	
	public List<Dictionary> selectByPageAndSelections();
}
