package com.ap.consumer.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ap.consumer.dao.ReviewDAO;
import com.ap.consumer.mvo.AniVO;
import com.ap.consumer.mvo.RowVO;
import com.ap.consumer.mvo.ReviewVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@Service
public class MapService {
	
		
		@Autowired
		private ReviewDAO dao;
		
		public ArrayList<RowVO> findAll() throws ParseException {
			ArrayList<RowVO> Arows = new ArrayList<RowVO>();
			RestTemplate rt = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
			
			int pSize = 1000;
			for (int pIndex=1; pIndex<5; pIndex++) {
				String Url=
						"https://openapi.gg.go.kr/AnimalPharmacy?" +
						"KEY=5ec8756bf58f41979de53fbe8f36abba" +
						"&Type=json" +
						"&pIndex=" + pIndex +
						"&pSize=" + pSize +
						"&";
			
				ResponseEntity<String> response = rt.exchange(Url, HttpMethod.GET, entity, String.class);
				// response : url로 통해 받아온 data
		        List<RowVO> rows = parser1(response.getBody());
	
				Arows.addAll(rows);
			}
			
			return Arows;
		}
		
		public List<RowVO> getApi(String si) throws ParseException {
			ArrayList<RowVO> Arows = new ArrayList<RowVO>();
			RestTemplate rt = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
			for (int pIndex=1; pIndex<5; pIndex++) {
				int pSize = 1000;
				String Url=
						"https://openapi.gg.go.kr/AnimalPharmacy?" +
						"KEY=5ec8756bf58f41979de53fbe8f36abba" +
						"&Type=json" +
						"&pIndex=" + pIndex +
						"&pSize=" + pSize +
						"&SIGUN_NM="+si;

					ResponseEntity<String> response = rt.exchange(Url, HttpMethod.GET, entity, String.class);
					// response : url로 통해 받아온 data
					List<RowVO> rows = parser1(response.getBody());
					Arows.addAll(rows);
			}
			return Arows;
		}
		
		// 파싱
		  public List<RowVO> parser1(String Json) { 
		      ObjectMapper objectMapper = new ObjectMapper();
		      AniVO ani = null;
		      //System.out.println("Json      : " + Json);
		      if (Json.indexOf("해당하는 데이터가 없습니다") == -1) {
			      try {
			         ani = objectMapper.readValue(Json, AniVO.class);
			      } catch (JsonProcessingException e) {
			         e.printStackTrace();
			      }
		      }
		      List<RowVO> rows = new ArrayList<RowVO>();
		      if (ani != null) {
		    	  rows = ani.AnimalPharmacy.get(1).getRow();
		      }
		      
		      return rows;
		   }
		  
		  // 파싱.및 이름 서치
		  	public List<RowVO> parser2(List<RowVO> rowsL, String search) { 
		         
		         ArrayList<RowVO> list = new ArrayList<RowVO>();
		         RowVO None = new RowVO();
		         None.setBIZPLC_NM("결과 값 없음");
		         
		         // search값 비교해서 데이터 list 만들기
		         for(int i = 0; i < rowsL.toArray().length; i++) {
			         String row_nm = rowsL.get(i).getBIZPLC_NM();
			         	// search = 값
		         	if(row_nm.indexOf(search) != -1) {
			        	 list.add(rowsL.get(i));
			         } 
		         } 
		         // search 값이 있지만 일치하는 데이터 없을 경우
		         if (!search.equals("") && list.toArray().length==0) {
		         		list.add(None);
		         }
		      return list;
		  }
		  	
		  	// 리뷰 정보 가져오기
		  	public List<ReviewVO> getReview(List<RowVO> data) {
		  		List<ReviewVO> list = new ArrayList<ReviewVO>();
		  		System.out.println(data.get(0).BIZPLC_NM);
		  		System.out.println(data.get(0).REFINE_ROADNM_ADDR);
	  			for(int i = 0; i < data.toArray().length; i++) {
	  				String BIZ = data.get(i).BIZPLC_NM;
	  				String RD = data.get(i).REFINE_ROADNM_ADDR;
	  				if (BIZ != null && RD != null) {
	  					list.addAll(dao.getReview(BIZ, RD));
	  				}
		         }
		  		
		  		return list;
		  	}
		  	
		  	// 중복 값 제거
		  	public List<String> Listset(List<RowVO> Arows) {
		  		ArrayList<String> Arows_NM = new ArrayList<String>();
		    	 for (int i=0; i<Arows.toArray().length; i++) {
		         	String row_NM = Arows.get(i).getSIGUN_NM();
		         	Arows_NM.add(row_NM);
		         }
		         HashSet<String> set = new HashSet<String>(Arows_NM);
		         ArrayList<String> NM = new ArrayList<String>(set);
		         NM.remove(null);
		         Collections.sort(NM);
		         
		         return NM;
		  	}
		  	
		  	// 페이징해서 데이터 넘기기
		  	public List<RowVO> findListPaging(List<RowVO> data, int page, int pageSize) { //현재 페이지
		        ArrayList<RowVO> list = new ArrayList<RowVO>();
		        System.out.println("데이터 길이 출력" + data.toArray().length);
		        int totaldata = data.toArray().length; // 데이터 값
		        //총데이터 5개 미만일때
		        if(totaldata < 10) {
		           for(int i = 0; i < totaldata; i++) {
		              list.add(data.get(i));
		           }
		        //첫페이지일때   
		        }else if(page == 1) {
		           for(int i = 0; i < pageSize; i++) {
		              list.add(data.get(i));
		           }
		        //마지막페이지일때
		        }else if(totaldata - page * pageSize <= 0) {
		           for(int i = (pageSize * page) - pageSize; i < totaldata; i++) {
		              list.add(data.get(i));
		           }
		        //그외
		        }else{
		           for(int i = (pageSize * page) - pageSize; i < pageSize * page; i++) {
		              list.add(data.get(i));
		           }
		        }
		        return list;
		     }
		  	
		  	
}
		
