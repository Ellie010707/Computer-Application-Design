package com.ap.consumer.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ap.consumer.dao.ReviewDAO;
import com.ap.consumer.mvo.RowVO;
import com.ap.consumer.mvo.ReviewVO;
import com.ap.consumer.pagination.Pagination;
import com.ap.consumer.service.MapService;

import lombok.extern.slf4j.Slf4j;

@RestController
public class MapController {
	List<RowVO> Arows;
	List<String> NM;
	
	@Autowired
	private MapService mapService;
	
    public MapController(MapService mapService){
        this.mapService = mapService;
    }
    
    // 웹 연결시 전체 리스트 보내기
    @GetMapping("/")
    public ModelAndView start(@RequestParam(defaultValue = "1") int page,
    							HttpServletRequest request) throws ParseException {
    	ModelAndView mView = new ModelAndView();
        mView.setViewName("index.html");
        
        Cookie[] getCookie = request.getCookies(); // 모든 쿠키 가져오기
        if(getCookie != null){ 

        	for (int i=0; i<getCookie.length; i++) {

        		Cookie c = getCookie[i]; // 객체 생성
        	
	        	if(c.getName().equals("mb_id")) {
	        		String value = c.getValue(); // 쿠키 값 가져오기
	        		System.out.println(value);
	        	}
        	}
        }
        //ResponseEntity<String> responseEntity = mapService.findAll();
        List<RowVO> Arows = mapService.findAll();
        this.Arows = Arows;
        int Acnt = Arows.toArray().length;
        
        // 시,군 중복제거 및 정렬
    	List<String> NM = mapService.Listset(Arows);
    	this.NM = NM;
        ////////////////////////////////////////////////////
        
        // 생성인자로  총 게시물 수, 현재 페이지를 전달
        Pagination pagination = new Pagination(Acnt, page);
        // 페이지 당 보여지는 게시글의 최대 개수
        int pageSize = pagination.getPageSize();
        System.out.println(pageSize);
        System.out.println(page);
		System.out.println(Acnt);
        List<RowVO> boardList = mapService.findListPaging(Arows, page, 10);
        List<ReviewVO> review = mapService.getReview(boardList);
        System.out.println(review.toString());

        System.out.println("count"+boardList.toArray().length);

    	mView.addObject("Allcnt", Acnt);
		mView.addObject("boardList", boardList);
		mView.addObject("pagination", pagination);
		mView.addObject("NM", NM);
		mView.addObject("review", review);
		mView.addObject("pageNo", page);

		
        return mView;
    }
    
    // seach 할 경우 검색어 리스트 보내기
    @GetMapping("/search") // api 호출시 data pasing
    public ModelAndView get(HttpServletRequest req,
    						@RequestParam(defaultValue = "1") int page) throws ParseException{
    	String si = req.getParameter("si"); // ex ) 고양시 값 받아서 조회
    	String search = req.getParameter("search"); // 검색 값 
    	List<RowVO> Arows = this.Arows;
    	// 시,군 중복제거 및 정렬
    	List<String> NM = this.NM;
    	// 시/군 선택 안하면 공백으로
    	if (si.equals("시/군/구")) {
    		si = "";
    	}
        ModelAndView mView = new ModelAndView();
        mView.setViewName("index.html");
        
        // si 검색 list 중복 제거 ////////////
        List<RowVO> All = mapService.getApi(si);
        HashSet<RowVO> set = new HashSet<RowVO>(All);
        List<RowVO> rows = new ArrayList<RowVO>(set);
        ///////////////////////////////////
        
        List<RowVO> Srows = mapService.parser2(rows, search);
        int Scnt = Srows.toArray().length;
        
		Pagination pagination = new Pagination(Scnt, page);
        List<RowVO> boardList = mapService.findListPaging(Srows, page, 10);
        List<ReviewVO> review = mapService.getReview(boardList);
        
    	mView.addObject("Allcnt", Scnt);
        mView.addObject("boardList", boardList);
    	mView.addObject("pagination", pagination);
    	mView.addObject("si", si);
    	mView.addObject("keyword", search);
		mView.addObject("NM", NM);
		mView.addObject("review", review);
		mView.addObject("pageNo", page);

    	
		// 시,군 & 검색란 입력이 없을 경우
        if (si.equals("") && search.equals("")) {
            int Acnt = Arows.toArray().length;
            // 결과 갯수
            System.out.println("search cnt" + Acnt);
            
        	pagination = new Pagination(Acnt, page);
            List<RowVO> AllList = mapService.findListPaging(Arows, page, 10);
            List<ReviewVO> review2 = mapService.getReview(AllList);

        	mView.addObject("Allcnt", Acnt);
            mView.addObject("boardList", AllList);
        	mView.addObject("pagination", pagination);
        	mView.addObject("si", si);
        	mView.addObject("keyword", search);
    		mView.addObject("NM", NM);
    		mView.addObject("review", review2);
    		mView.addObject("pageNo", page);

        }
        return mView;
    }
    
    
}  
    
    