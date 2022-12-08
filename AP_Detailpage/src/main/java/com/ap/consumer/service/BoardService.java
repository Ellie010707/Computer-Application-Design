package com.ap.consumer.service;

import java.util.List;

import com.ap.consumer.vo.Criteria;
import com.ap.consumer.vo.StarVO;

public interface BoardService {
	
//  public List<BoardVO> getList();
  public List<StarVO> getList(Criteria cri);
  public int getTotal(String REFINE_ROADNM_ADDR);	

}
