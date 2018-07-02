package com.wzj.service;

import java.util.List;
import com.wzj.pojo.Log;

public interface ILogService {
	public int insert(Log log);
	public List<Log> findAll();
	public List<Log> findByTime(String sTime, String eTime);
	public void deleteBefore(String timePoint);
	public boolean exportRecord(String title, String filePath);
	public Log getFirstLog();
}
