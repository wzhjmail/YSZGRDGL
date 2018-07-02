package com.wzj.service.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wzj.dao.ReviewFormMapper;
import com.wzj.pojo.ReviewForm;
import com.wzj.service.application.IReviewFormService;

@Service("reviewService")
public class ReviewFormService implements IReviewFormService{

	@Autowired
	private ReviewFormMapper reviewFormMapper;
	@Override
	public int insertRev(ReviewForm review) {
		return reviewFormMapper.insert(review);
	}
	@Override
	public ReviewForm getReviewById(Integer id) {
		return reviewFormMapper.selectById(id);
	}
	
	public int updateRev(ReviewForm review) {
		return reviewFormMapper.update(review);
		
	}
	public ReviewForm getReviewByBId(int bId) {
		return reviewFormMapper.getReviewByBId(bId);
	}
	
	public ReviewForm getReviewByTitleNo(String titleNo) {
		return reviewFormMapper.getReviewByTitleNo(titleNo);
	}
}
