package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.pojo.ComUser;

public interface ComUserMapper {

	@Select({ "select * from com_user",
  	"where RamusCenter = #{ramusCenter,jdbcType=VARCHAR}" })
  	@ResultMap("BaseResultMap")
	List<ComUser> findAllUser(String ramusCenter);

	@Select({ "select * from com_user"})
  	@ResultMap("BaseResultMap")
	List<ComUser> findAll();

	@Insert({"insert into com_user (username,RamusCenter,gender,birthday,mobile,email,post,dept,unit)",
  		"values (#{username,jdbcType=VARCHAR},"
  		+"#{ramusCenter,jdbcType=VARCHAR},",
  		"#{gender,jdbcType=VARCHAR},#{birthday,jdbcType=DATE},#{mobile,jdbcType=VARCHAR},#{email,jdbcType=VARCHAR},#{post,jdbcType=VARCHAR},#{dept,jdbcType=VARCHAR},#{unit,jdbcType=VARCHAR})"})
	int insert(ComUser cUser);

	@Select({ "select * from com_user where id = #{id,jdbcType=VARCHAR}" })
  	@ResultMap("BaseResultMap")
	ComUser getByUserId(int id);

	int updateByPrimaryKey(ComUser cUser);

	@Delete({"delete from com_user ","where id=#{id,jdbcType=INTEGER}"})
	int deleteById(Integer id);

	@Select({"select * from com_user where dept = '专家用户' and RamusCenter=#{branchId,jdbcType=VARCHAR} and (locked=0 or locked is null)"})
	List<ComUser> getZjByBranchId(String branchId);

//	@Select({"select * from com_user where id in(#{ids,jdbcType=VARCHAR})"})
	List<ComUser> getByUserIds(@Param("comuser")String comuser);

	List<ComUser> findAllFZ(ComUser comUser);

	List<ComUser> findAllByUser(ComUser comUser);

	@Select({"select * from com_user where concat_ws(username,gender,birthday,RamusCenter,email,post,mobile)",
		"like #{condition,jdbcType=VARCHAR}"})
	//@ResultMap("BaseResultMap")
	List<ComUser> findByCondition(String condition);

	@Select({"select * from com_user where RamusCenter=#{ramusCenter,jdbcType=VARCHAR} and concat_ws(username,gender,birthday,RamusCenter,email,post,mobile)",
	"like #{condition,jdbcType=VARCHAR}"})
	List<ComUser> findByCondition2(@Param("condition")String condition,@Param("ramusCenter")String ramusCenter);

	@Update({"update com_user set locked=#{locked,jdbcType=INTEGER} ",
	"where id = #{id,jdbcType=INTEGER}"})
	int audit(@Param("id")int id,@Param("locked")int locked);

	@Select({"select * from com_user where dept=#{dept,jdbcType=VARCHAR}"})
	List<ComUser> findByDept(@Param("dept")String dept);

	@Select({"select * from com_user where RamusCenter=#{ramusCenter,jdbcType=VARCHAR} and dept= #{dept,jdbcType=VARCHAR}"})
	List<ComUser> findAllUserByDept(@Param("ramusCenter")String ramusCenter, @Param("dept")String dept);

	@Select({"select * from com_user where concat_ws(username,gender,birthday,RamusCenter,email,post,mobile)",
	"like #{condition,jdbcType=VARCHAR} and dept=#{dept,jdbcType=VARCHAR}"})
	List<ComUser> findByConditionAndDept(@Param("condition")String condition, @Param("dept")String dept);

	@Select({"select * from com_user where RamusCenter=#{ramusCenter,jdbcType=VARCHAR} and concat_ws(username,gender,birthday,RamusCenter,email,post,mobile)",
	"like #{condition,jdbcType=VARCHAR} and dept=#{dept,jdbcType=VARCHAR}"})
	List<ComUser> findByConditionAndDept2(@Param("condition")String condition, @Param("ramusCenter")String ramusCenter, @Param("dept")String dept);

	int updateByForInsert(ComUser cUser);

	List<ComUser> findZj(ComUser com);

	List<ComUser> findZjByBranchId(ComUser com);

	int updatefzjgUser(ComUser cUser);

	int updatezj(ComUser cUser);
}
