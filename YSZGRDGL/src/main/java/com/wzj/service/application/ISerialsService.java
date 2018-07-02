package com.wzj.service.application;

import com.wzj.pojo.Serials;

public interface ISerialsService {
	public int insert(Serials serials);
	public int update(Serials serials);
	public Serials getSerialsById(int id);
	public int deleteById(int id);
}
