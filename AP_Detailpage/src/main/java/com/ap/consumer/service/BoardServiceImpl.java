package com.ap.consumer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ap.consumer.dao.StarDAO;
import com.ap.consumer.vo.Criteria;
import com.ap.consumer.vo.StarVO;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{
	@Setter(onMethod_ = @Autowired)
	private StarDAO mapper;
	
	@Override
	public List<StarVO> getList(Criteria cri) {
		 
		log.info("[SERVICE]getList..."+cri);
        return mapper.getAllInfo(cri);
    }
	@Override
	public int getTotal(String REFINE_ROADNM_ADDR) {
		return mapper.getTotalCount(REFINE_ROADNM_ADDR);
	}
}
