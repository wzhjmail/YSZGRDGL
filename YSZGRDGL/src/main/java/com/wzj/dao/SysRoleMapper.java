package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.SysPermission;
import com.wzj.pojo.SysRole;
import com.wzj.pojo.SysRolePermission;
import com.wzj.pojo.SysUser;

public interface SysRoleMapper {
    int deleteByPrimaryKey(int id);

    int insert(SysRole sysRole);

    SysRole selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
    @Select({"select id,name,available,belonged from sys_role ",
    	"where name = #{name,jdbcType=VARCHAR}"})
	SysRole findByName(String name);
    
    @Insert({"insert into sys_role_permission (sys_role_id,sys_permission_id)",
	"values (#{roleId,jdbcType=INTEGER},#{pmsId,jdbcType=INTEGER})"})
    int addPermission(SysRolePermission srp);

    @Select({"select * from sys_role"})
	List<SysRole> findAll();
    
    @Select({"select * from sys_permission where id in(",
    	"select sys_permission_id from sys_role_permission",
    	" where sys_role_id=#{id,jdbcType=INTEGER}) ORDER BY sortstring"})
	List<SysPermission> findPmsByRoleId(int id);

    @Delete({"delete from sys_role_permission ",
    		"where sys_role_id = #{id,jdbcType=INTEGER}"})
	void deleteAllPms(int id);
    
    /*@Select({"select * from sys_role where id in (",
    	"select sys_role_id from sys_user_role",
    	" where sys_user_id =#{userId,jdbcType=INTEGER})"})*/
	List<SysRole> findRoleByUserId(Integer userId);
    
    @Delete({"delete from sys_user_role where sys_role_id=#{id,jdbcType=INTEGER}"})
	int deleteRoleInUserByRoleId(int id);
    
    @Select({"select * from sys_role where id=#{roleId,jdbcType=INTEGER}"})
	SysRole findRoleByRoleId(Integer roleId);
    
    @Select({"select * from sys_user where id in (",
    	"select sys_user_id from sys_user_role where ",
    	"sys_role_id like #{roleId,jdbcType=VARCHAR})"})
	List<SysUser> findUsersByRoleId(String roleId);

    @Select({"select sys_role_id from sys_user_role",
	" where sys_user_id =#{userId,jdbcType=INTEGER}"})
	String findRole(Integer userId);

    @Select({"select sys_role_id from sys_user_role",
    	" where sys_user_id =#{userId,jdbcType=INTEGER}"})
	String findRoleIdByUserId(Integer userId);

    @Select({"select * from sys_role where id in (#{id})"})
	List<SysRole> findRoleByRoleIds(SysRole sysrole);

}