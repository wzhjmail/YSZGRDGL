package com.wzj.service.application.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.ReviewFormPartMapper;
import com.wzj.pojo.ReviewFormPart;

@Service("reviewFormPartService")
public class ReviewFormPartService {
	@Autowired
	private ReviewFormPartMapper mapper;
	
	public int insertList(List<ReviewFormPart> lists){
		return mapper.insertList(lists);
	}

	public List<ReviewFormPart> getByReviewFormId(int reviewFormId) {
		return mapper.getByReviewFormId(reviewFormId);
	}

	public ReviewFormPart getByReviewFormNumId(int reviewFormId, int reviewFormNum) {
		return mapper.getByReviewFormNumId(reviewFormId, reviewFormNum);
	}

	public int deleteByCId(int id) {
		return mapper.deleteByCId(id);
	}
}
