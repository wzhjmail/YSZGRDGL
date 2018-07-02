package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wzj.pojo.SysPermission;

public interface SysPermissionMapper {
    int deleteByPrimaryKey(int id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
    
   /* @Select({"select * from sys_permission","where id in (",
    	"select sys_permission_id from sys_role_permission",
    	"where sys_role_id in (",
    	" select sys_role_id from sys_user_role",
    	" where sys_user_id=#{userId,jdbcType=INTEGER}))"})*/
    @Select({"select * from sys_permission","where id in (",
    	"select sys_permission_id from sys_role_permission",
    	"where sys_role_id exists (",
    	"select sys_role_id from sys_user_role",
    	" where sys_user_id=#{userId,jdbcType=INTEGER}))"})
    List<SysPermission> getPermissionsByUserId(int userId);

    @Select({"select * from sys_permission"})
	List<SysPermission> findAll();
    
    @Select({"select * from sys_permission where name = #{name,jdbcType=VARCHAR}"})
    SysPermission findByName(String name);
    
    @Delete({"delete from sys_role_permission",
    	" where sys_permission_id = #{id,jdbcType=INTEGER}"})
	int deleteRolePmsById(int id);
//    where available=1
    @Select({"select id from sys_permission"})
	List<Integer> findIds();
    
	@Select({"select * from sys_permission where ",
		"parentId=#{id,jdbcType=INTEGER}  and available=1 order by sortstring asc"})
	List<SysPermission> getPmsByMenuId(int id);

	List<SysPermission> getMenusByIds(List<Integer> ids);
	
	@Select({"select * from sys_permission where type='menu' and available=1 order by sortstring asc"})
	List<SysPermission> getMenus();

	@Select({"select * from sys_permission where url=#{url,jdbcType=VARCHAR}"})
	SysPermission getPermissionByUrl(@Param("url")String url);

	@Select({"select * from sys_permission where id=#{id,jdbcType=INTEGER}"})
	SysPermission getById(int id);

	@Delete({"delete from sys_role_permission",
	" where sys_role_id = #{id,jdbcType=INTEGER}"})
	void deleteRolePerByRoleId(int id);

	@Update({"update sys_permission set available = 0 where id=#{id,jdbcType=INTEGER}"})
	int updateAvailabie(int id);
	
}