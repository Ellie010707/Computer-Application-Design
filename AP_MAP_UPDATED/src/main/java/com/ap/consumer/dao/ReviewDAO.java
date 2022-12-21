package com.ap.consumer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ap.consumer.mvo.ReviewVO;

@Mapper
public interface ReviewDAO {

	public List<ReviewVO> getReview(String NM, String RD);
}
