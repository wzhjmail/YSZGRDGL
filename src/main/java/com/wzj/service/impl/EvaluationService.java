package com.wzj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.DTO.EvaluationDTO;
import com.wzj.dao.EvaluationMapper;
import com.wzj.pojo.Evaluation;
import com.wzj.pojo.EvaluationPart;
import com.wzj.service.IEvaluationService;

@Service("evaluationService")
public class EvaluationService implements IEvaluationService {
	@Autowired
	private EvaluationMapper mapper;
	@Override
	public int delete(Integer id) {
		return mapper.deleteById(id);
	}

	@Override
	public int insert(Evaluation record) {
		return mapper.insert(record);
	}

	@Override
	public Evaluation select(Integer id) {
		return mapper.selectById(id);
	}

	@Override
	public int update(Evaluation record) {
		return mapper.update(record);
	}

	public List<Evaluation> getAll() {
		return mapper.getAll();
	}

	public List<Evaluation> getUsing() {
		return mapper.getUsing();
	}

	public int addEvaluation(Evaluation evaluation) {
		return mapper.insert(evaluation);
	}

	public List<EvaluationDTO> getAllEva() {
		return mapper.getAllEva();
	}

	public int getLastId() {
		return mapper.getLastId();
	}

	public int countResult(int evalId) {
		return mapper.countResult(evalId);
	}

	public Evaluation getByNum(int evalId) {
		return mapper.getByNum(evalId);
	}

}
