package com.wzj.service.impl;

import java.util.List;

import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.wzj.dao.ComUserMapper;
import com.wzj.pojo.ComUser;
import com.wzj.service.IComUserService;
@Service("comUserService")
public class ComUserService implements IComUserService{

	@Autowired
	private ComUserMapper comUserMapper;

	public List<ComUser> findAllUser(String ramusCenter) {
		return comUserMapper.findAllUser(ramusCenter);
	}

	public List<ComUser> findAll() {
		return comUserMapper.findAll();
	}

	public int insert(ComUser cUser) {
		return comUserMapper.insert(cUser);
	}

	public ComUser getByUserId(int ID) {
		return comUserMapper.getByUserId(ID);
	}

	public int update(ComUser cUser) {
		return comUserMapper.updateByForInsert(cUser);
	}

	public int deleteById(Integer id) {
		return comUserMapper.deleteById(id);
	}

	public List<ComUser> getZjByBranchId(String branchId) {
		return comUserMapper.getZjByBranchId(branchId);
	}

	public List<ComUser> getByUserIds(String ids) {
		List<ComUser> users=null;
		if(!StringUtils.isEmpty(ids)){
//			ids=ids.substring(0, ids.length()-1);
			String comuser="select * from com_user where id in("+ids+")";
			users=comUserMapper.getByUserIds(comuser);
		}
		return users;
	}

	public List<ComUser> findAllFZ(ComUser comUser) {
		return comUserMapper.findAllFZ(comUser);
	}

	public List<ComUser> findAllByUser(ComUser comUser) {
		return comUserMapper.findAllByUser(comUser);
	}

	public List<ComUser> findByCondition(String condition,String ramusCenter) {
		List<ComUser> users;
		if("0000".equals(ramusCenter)){
			users=comUserMapper.findByCondition("%"+condition+"%");
		}else{
			users=comUserMapper.findByCondition2("%"+condition+"%",ramusCenter);
		}
		return users;
	}

	public int audit(int id, int locked) {
		return comUserMapper.audit(id,locked);
	}

	public List<ComUser> findByDept(String dept) {
		return comUserMapper.findByDept(dept);
	}

	public List<ComUser> findAllUserByDept(String ramusCenter, String dept) {
		return comUserMapper.findAllUserByDept(ramusCenter,dept);
	}

	public List<ComUser> findByConditionAndDept(String sSearch, String ramusCenter, String dept) {
		List<ComUser> users;
		if("0000".equals(ramusCenter)){
			users=comUserMapper.findByConditionAndDept("%"+sSearch+"%",dept);
		}else{
			users=comUserMapper.findByConditionAndDept2("%"+sSearch+"%",ramusCenter,dept);
		}
		return users;
	}

	public List<ComUser> findZj(ComUser com) {
		return comUserMapper.findZj(com);
	}

	public List<ComUser> findZjByBranchId(ComUser com) {
		return comUserMapper.findZjByBranchId(com);
	}

	public int updatefzjgUser(ComUser cUser) {
		return comUserMapper.updatefzjgUser(cUser);
	}

	public int updatezj(ComUser cUser) {
		return comUserMapper.updatezj(cUser);
	}
}
