package com.wzj.service.application;

import com.wzj.pojo.ReviewForm;

public interface IReviewFormService {

	public int insertRev(ReviewForm review);
	public ReviewForm getReviewById(Integer id);
//	public int updateRev(ReviewForm review);
	//根据公司id获取评审表信息
	public ReviewForm getReviewByBId(int bId);
}
