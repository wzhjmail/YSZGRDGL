package com.wzj.service;

import java.util.List;

import com.wzj.pojo.Dictionary;

public interface IDictionaryService {
	public int insert(Dictionary dic);
	public List<Dictionary> findAll();
	public Dictionary find(int id);
	public int update(Dictionary dic);
	public int delete(int id);
}
