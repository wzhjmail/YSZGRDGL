package com.wzj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.EvaluationPartMapper;
import com.wzj.pojo.EvaluationPart;

@Service("evaluationPartService")
public class EvaluationPartService {

	@Autowired
	private EvaluationPartMapper mapper;
	
	public List<EvaluationPart> getByEId(int eid){
		return mapper.getByEId(eid);
	}

	public EvaluationPart getById(int id) {
		return mapper.selectById(id);
	}

	public int update(EvaluationPart record) {
		return mapper.updateById(record);
	}

	public int delete(int id) {
		return mapper.deleteById(id);
	}

	public int insert(EvaluationPart part) {
		return mapper.insert(part);
	}

	public EvaluationPart getResultNote(EvaluationPart ep) {
		return mapper.getResultNote(ep);
	}
}
