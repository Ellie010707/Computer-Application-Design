package com.ap.consumer.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.ap.consumer.dao.StarDAO;
import com.ap.consumer.dao.nameDAO;
import com.ap.consumer.vo.Criteria;
import com.ap.consumer.vo.PageDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class APStarController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	StarDAO sDAO;
	
	@Autowired
	nameDAO nDAO;
	
	String MB_ID = null;
	
	/* 약국이름과 주소 찾기 */
	@RequestMapping("star/{REFINE_ROADNM_ADDR}*")
	public String getallinfo(@PathVariable String REFINE_ROADNM_ADDR, Criteria cri,Model model1,
							HttpServletRequest req1) {
		// 쿠키로 아이디 정보 받기
		Cookie[] getCookie = req1.getCookies(); // 모든 쿠키 가져오기
        if(getCookie != null){ 
        	for (int i=0; i<getCookie.length; i++) {
        		Cookie c = getCookie[i]; // 객체 생성
        	
	        	if(c.getName().equals("mb_id")) {
	        		String mb_id = c.getValue(); // 쿠키 id 가져오기
	        		MB_ID = mb_id; // 전역변수에 넣기
	         	}
        	}
        }
        
		log.info("[CONTROLLER]get getAllInfo..." + cri);
		model1.addAttribute("page1",REFINE_ROADNM_ADDR);
		model1.addAttribute("getname2",nDAO.getname2(REFINE_ROADNM_ADDR));
		model1.addAttribute("P_NM",nDAO.getname2(REFINE_ROADNM_ADDR).get(0).getBIZPLC_NM());
		model1.addAttribute("getallinfo",sDAO.getAllInfo(cri));
		model1.addAttribute("pageMaker", new PageDTO(cri, sDAO.getTotalCount(REFINE_ROADNM_ADDR),REFINE_ROADNM_ADDR));
		model1.addAttribute("avg",sDAO.avg(REFINE_ROADNM_ADDR));
		
		return "Star";
	}

	/* Pagination */
    @RequestMapping("star/starupdate")
    public String updateinfo(HttpServletRequest req1, Model model1) throws UnsupportedEncodingException {
    	String rv_score = req1.getParameter("rating");
    	String rv_comment = req1.getParameter("text");
    	String BIZPLC_NM = req1.getParameter("P_NM");
    	String REFINE_ROADNM_ADDR = req1.getParameter("RD");
    	sDAO.starupdate(MB_ID, rv_score,rv_comment, BIZPLC_NM, REFINE_ROADNM_ADDR);
    	String encodedParam = URLEncoder.encode(REFINE_ROADNM_ADDR, "UTF-8");
    		

    	encodedParam = encodedParam.replaceAll("\\+", "%20");
    	
        return "redirect:http://localhost:8000/star/" + encodedParam;
	       
        
    }
    
	/* 헤더 버튼 클릭시 홈/지도메인으로 이동(미완) */
	@GetMapping("star/home")
	public String home() {
		return "redirect:http://localhost:8000/map";
	}

 
}