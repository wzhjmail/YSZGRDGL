package com.wzj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.CertpositionMapper;
import com.wzj.pojo.Certposition;
import com.wzj.service.ICertpositionService;

@Service("certpositionService")
public class CertpositionService implements ICertpositionService {
	@Autowired
	private CertpositionMapper certMapper;
	
	@Override
	public List<Certposition> getAll() {
		return certMapper.getAll();
	}

	@Override
	public Certposition getById(int id) {
		return certMapper.select(id);
	}

	@Override
	public int update(Certposition cp) {
		return certMapper.update(cp);
	}

	@Override
	public int setUsed(int id) {
		return certMapper.changeUsed(id);
	}

	@Override
	public int insert(Certposition cert) {
		return certMapper.insert(cert);
	}

	@Override
	public int delete(int id) {
		return certMapper.delete(id);
	}

	public Certposition getUsing() {
		return certMapper.getUsing();
	}

	public int getCount(String name) {
		return certMapper.getCount(name);
	}

}
