package com.wzj.service.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.SerialsMapper;
import com.wzj.pojo.Serials;
import com.wzj.service.application.ISerialsService;

@Service("serialsService")
public class SerialsService implements ISerialsService {
	@Autowired
	SerialsMapper serialsMapper;
	
	@Override
	public int insert(Serials serials) {
		return serialsMapper.insert(serials);
	}

	@Override
	public int update(Serials serials) {
		return serialsMapper.update(serials);
	}

	@Override
	public Serials getSerialsById(int id) {
		return serialsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteById(int id) {
		return serialsMapper.deleteByPrimaryKey(id);
	}

	public Serials getBySerial(int serial){
		return serialsMapper.getBySerial(serial);
	}
}
