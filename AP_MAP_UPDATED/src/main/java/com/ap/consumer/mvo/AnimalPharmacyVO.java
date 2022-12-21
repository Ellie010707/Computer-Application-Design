package com.ap.consumer.mvo;

import java.util.List;

import lombok.Data;

@Data
public class AnimalPharmacyVO {

	private List<HeadVO> Head;
	private List<RowVO> row;
}
