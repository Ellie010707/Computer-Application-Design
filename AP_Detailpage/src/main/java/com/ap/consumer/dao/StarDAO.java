package com.ap.consumer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ap.consumer.vo.Criteria;
import com.ap.consumer.vo.StarVO;

@Mapper
public interface StarDAO {
	
	public List<StarVO> getAllInfo(Criteria cri);
	public int starupdate(String MB_ID, String rv_score, String rv_comment, String BIZPLC_NM, String REFINE_ROADNM_ADDR);
	public int getTotalCount(String REFINE_ROADNM_ADDR);
	public int avg(String REFINE_ROADNM_ADDR);

}
