package com.wzj.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.DTO.CustomerDTO;
import com.wzj.dao.CustomerMapper;
import com.wzj.pojo.Customer;
import com.wzj.service.ICustomerService;
import com.wzj.util.CommonUtil;

@Service("adminService")
public class CustomerService implements ICustomerService {
	@Autowired
	private CustomerMapper customerMapper;

	public Customer getByUserName(String username) {
		return customerMapper.selectByUserName(username);
	}

}
