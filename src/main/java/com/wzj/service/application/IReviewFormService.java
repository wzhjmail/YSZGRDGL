package com.wzj.service.application;

import com.wzj.pojo.ReviewForm;

public interface IReviewFormService {

	public int insertRev(ReviewForm review);
	public ReviewForm getReviewById(Integer id);
//	public int updateRev(ReviewForm review);
	//���ݹ�˾id��ȡ�������Ϣ
	public ReviewForm getReviewByBId(int bId);
}
