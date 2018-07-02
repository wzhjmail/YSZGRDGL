package com.wzj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzj.dao.DictionaryMapper;
import com.wzj.pojo.Dictionary;
import com.wzj.service.IDictionaryService;

@Service("dictionaryService")
public class DictionaryService implements IDictionaryService {
	
	@Autowired
	private DictionaryMapper dictionaryMapper;
	
	@Override
	public int insert(Dictionary dic) {
		return dictionaryMapper.insert(dic);
	}

	public List<Dictionary> findAll() {
		return dictionaryMapper.findAll();
	}

	public Dictionary find(int id) {
		return dictionaryMapper.find(id);
	}

	public int update(Dictionary dic) {
		return dictionaryMapper.update(dic);
	}

	public int delete(int id) {
		return dictionaryMapper.delete(id);
	}

	public PageInfo<Dictionary> selectDicByPage(int currentPage,int pageSize){
		PageHelper.startPage(currentPage, pageSize);
        List<Dictionary> docs = dictionaryMapper.selectByPageAndSelections();
        PageInfo<Dictionary> pageInfo = new PageInfo<>(docs);
        return pageInfo;
	}
}
