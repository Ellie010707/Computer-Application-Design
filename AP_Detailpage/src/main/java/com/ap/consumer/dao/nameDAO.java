package com.ap.consumer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ap.consumer.vo.NameVO;

@Mapper
public interface nameDAO {

	public List<NameVO> getname2(String REFINE_ROADNM_ADDR);

}
