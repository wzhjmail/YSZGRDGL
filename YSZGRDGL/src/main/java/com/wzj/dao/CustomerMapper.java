package com.wzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.wzj.pojo.Customer;

public interface CustomerMapper {
	//根据用户名查询密码 验证登录
	@Select({ "select", "ID, UserCode, UserName, UserPassword, RealName, RegisterTime, Remark", "from customer",
	"where UserName = #{username,jdbcType=VARCHAR}" })
	@ResultMap("BaseResultMap")
	Customer selectByUserName(String username);


}