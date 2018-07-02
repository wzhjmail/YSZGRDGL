package com.wzj.service;

import com.wzj.pojo.Evaluation;

public interface IEvaluationService {
	int delete(Integer id);

    int insert(Evaluation record);

    Evaluation select(Integer id);

    int update(Evaluation record);
}
