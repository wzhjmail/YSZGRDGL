package com.wzj.dao;

import java.sql.Blob;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wzj.pojo.Stu;
import com.wzj.pojo.SysRole;
import com.wzj.pojo.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
  //根据用户名查询密码 验证登录
  	@Select({ "select * from sys_user",
  	"where usercode = #{username,jdbcType=VARCHAR}" })
  	@ResultMap("BaseResultMap")
    SysUser selectByUserName(String username);

  	@Select({ "select * from sys_user where id = #{id,jdbcType=INTEGER}" })
  	@ResultMap("BaseResultMap")
	SysUser getUserById(int id);
  	
  /*	@Insert({"insert into sys_user (usercode,username,password,dept,locked,RamusCenter,gender,birthday,phone,mobile,email,unit,post)",
  		"values (#{usercode,jdbcType=VARCHAR},#{username,jdbcType=VARCHAR},"
  		+ "#{password,jdbcType=VARCHAR},#{dept,jdbcType=VARCHAR},#{locked,jdbcType=INTEGER},",
  		"#{ramusCenter,jdbcType=VARCHAR},",
  		"#{gender,jdbcType=VARCHAR},#{birthday,jdbcType=DATE},#{phone,jdbcType=VARCHAR},#{mobile,jdbcType=VARCHAR},#{email,jdbcType=VARCHAR},#{unit,jdbcType=VARCHAR},#{post,jdbcType=VARCHAR})"})
    int insert(SysUser sysUser);*/
  	
  	@Insert({"insert into sys_user (id,usercode,username,password,dept,locked,RamusCenter,status,IsCut,gender,birthday,phone,mobile,email,cid,unit,post)",
  		"values (#{id,jdbcType=INTEGER},#{usercode,jdbcType=VARCHAR},"
  		+ "#{username,jdbcType=VARCHAR},#{password,jdbcType=VARCHAR},"
  		+ "#{dept,jdbcType=VARCHAR},#{locked,jdbcType=INTEGER},",
  	"#{ramusCenter,jdbcType=VARCHAR},#{status,jdbcType=INTEGER},#{isCut,jdbcType=BIT},",
  	"#{gender,jdbcType=VARCHAR},#{birthday,jdbcType=DATE},#{phone,jdbcType=VARCHAR},#{mobile,jdbcType=VARCHAR},#{email,jdbcType=VARCHAR},#{cid,jdbcType=INTEGER},#{unit,jdbcType=VARCHAR},#{post,jdbcType=VARCHAR})"})
  	int add(SysUser sysUser);

	List<SysUser> findAllByUser(SysUser sysuser);

  	@Delete({"delete from sys_user ","where id=#{id,jdbcType=INTEGER}"})
	int deleteById(Integer id);

	int update(SysUser sysUser);

  	@Select({ "select * from sys_user ",
	  	"where usercode = #{usercode,jdbcType=VARCHAR}",
	  	" and password = #{password,jdbcType=VARCHAR}",
	  	" and locked=#{locked,jdbcType=INTEGER}"})
	SysUser findUser(@Param("usercode")String usercode,@Param("password")String password,@Param("locked")Integer locked);

  	@Insert({"insert into sys_user_role (sys_user_id,sys_role_id)",
  		"values(#{userId,jdbcType=INTEGER},#{roleId,jdbcType=VARCHAR})"})
	int setRoleId(@Param("userId")Integer userId,@Param("roleId")String roleId);

  	@Update({"update sys_user_role set sys_role_id=#{roleId,jdbcType=VARCHAR} ",
  		"where sys_user_id = #{userId,jdbcType=INTEGER}"})
	int updateRoleId(@Param("userId")int userId, @Param("roleId")String roleId);

  	@Delete({"delete from sys_user_role ","where sys_user_id=#{id,jdbcType=INTEGER}"})
	int deleteUserRole(Integer id);

  	@Select({"select * from sys_role where id=",
  		"(select sys_role_id from sys_user_role where sys_user_id=",
  		"#{userId,jdbcType=INTEGER})"})
	List<SysRole> findRoleByUserId(Integer userId);

  	@Select({"select * from sys_user where id in (",""
  			+ " select sys_user_id from sys_user_role",
  			" where sys_role_id = #{roleId,jdbcType=INTEGER})"})
	List<SysUser> findUsersByRoleId(int roleId);

  	@Select({"select count(*) from sys_user where usercode =#{usercode,jdbcType=VARCHAR}"})
	int getSysUserCountByUsername(String usercode);

  	@Update({"update sys_user set locked=#{locked,jdbcType=INTEGER} ",
		"where id = #{id,jdbcType=INTEGER}"})
	int audit(@Param("id")Integer id,@Param("locked")Integer locked);

  	//@Select({"select * from sys_user where dept = '分中心用户' order by id desc"})
	List<SysUser> findAllFZ(SysUser sysUser);
  	
  	@Select({"select * from sys_user order by id desc"})
	List<SysUser> findAll();

  	@Select({"select * from sys_user where dept = '专家用户' and RamusCenter=#{branchId,jdbcType=VARCHAR} and locked=0"})
	List<SysUser> getZjByBranchId(@Param("branchId")String branchId);

  	@Select({"select * from sys_user where cid=#{cid,jdbcType=INTEGER}"})
	SysUser findByCid(@Param("cid")Integer cid);

  	@Select({"select * from sys_user where instr(#{ids,jdbcType=VARCHAR},id)"})
	List<SysUser> getByUserIds(String ids);

  	@Select({"select * from sys_user where usercode=#{usercode,jdbcType=VARCHAR}"})
	SysUser getByUserCode(@Param("usercode")String usercode);

	List<SysUser> getZJUserByIds(@Param("result")String result);

	@Select({"select * from sys_user where RamusCenter=#{ramusCenter,jdbcType=VARCHAR}"})
	List<SysUser> findAllByBranchId(String ramusCenter);

	@Select({"select * from sys_user where dept='分中心用户' and ",
		"RamusCenter=#{divisionCode,jdbcType=VARCHAR}"})
	List<SysUser> findFZLikeDivisionCode(String divisionCode);

	@Select({"select count(*) from sys_user where usercode =#{usercode,jdbcType=VARCHAR}",
		"and id!=#{userId,jdbcType=INTEGER}"})
	int getSysUserCount(@Param("userId")int userId, @Param("usercode")String userCode);

	@Select({"select * from sys_user where dept=#{dept,jdbcType=VARCHAR} and(RamusCenter=#{code,jdbcType=VARCHAR}",
		" or concat_ws(usercode,username,unit) like #{sSearch,jdbcType=VARCHAR})"})
	List<SysUser> findLike(@Param("dept")String dept, @Param("code")String code, @Param("sSearch")String sSearch);

	@Select({"select name from Stud where id=1"})
	String test();

	@Update({"update Stud set mfile =#{fbate,jdbcType=ARRAY} where id=1"})
	int testFile(@Param("fbate")byte[] fbate);

	@Select({"select mfile from stud where id=1"})
	Blob getFile();
	@Select({"select * from stud where id=1"})
	Stu getName();
}