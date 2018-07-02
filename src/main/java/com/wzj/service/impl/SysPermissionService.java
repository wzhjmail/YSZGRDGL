package com.wzj.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.wzj.dao.SysPermissionMapper;
import com.wzj.pojo.SysPermission;
import com.wzj.service.ISysPermissionService;

@Service("sysPermissionService")
public class SysPermissionService implements ISysPermissionService {
	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public List<SysPermission> getPermissionsByUserId(int userId) {
		//return sysPermissionMapper.getPermissionsByUserId(userId);
		String selectRoleId="select sys_role_id from sys_user_role where sys_user_id="+userId;
		String roleIds=jdbcTemplate.queryForMap(selectRoleId).get("sys_role_id").toString();
		String sql="select * from sys_permission where id in ("+
				"select sys_permission_id from sys_role_permission where sys_role_id in("+roleIds+"))";
		List<Map<String, Object>> queryForList=jdbcTemplate.queryForList(sql);
		List<SysPermission> list=new ArrayList<>();
		for(Map<String, Object> map:queryForList){
			SysPermission permission=new SysPermission();
			permission.setAvailable(map.get("available")==null?"":map.get("available").toString());
			permission.setId(map.get("id")==null?-1:(int)map.get("id"));
			permission.setName(map.get("name")==null?"":map.get("name").toString());
			permission.setParentid(map.get("parentid")==null?0:(long)map.get("parentid"));
			permission.setParentids(map.get("parentids")==null?"":map.get("parentids").toString());
			permission.setPercode(map.get("percode")==null?"":map.get("percode").toString());
			permission.setSortstring(map.get("sortstring")==null?"":map.get("sortstring").toString());
			permission.setType(map.get("type")==null?"":map.get("type").toString());
			permission.setUrl(map.get("url")==null?"":map.get("url").toString());
			list.add(permission);
		}
		return list;
	}
	
	@Override
	public SysPermission findByName(String name) {
		return sysPermissionMapper.findByName(name);
	}

	public List<SysPermission> getPmsByMenuId(int id) {
		return sysPermissionMapper.getPmsByMenuId(id);
	}

	public List<SysPermission> getMenusByIds(List<Integer> ids) {
		return sysPermissionMapper.getMenusByIds(ids);
	}

	public List<SysPermission> getMenus() {
		return sysPermissionMapper.getMenus();
	}
	
	public List<SysPermission> findAll() {
		return sysPermissionMapper.findAll();
	}

	public SysPermission getPermissionByUrl(String url) {
		return sysPermissionMapper.getPermissionByUrl(url);
	}

}
