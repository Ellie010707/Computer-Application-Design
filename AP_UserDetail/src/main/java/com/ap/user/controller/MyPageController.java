package com.ap.user.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ap.user.vo.Member;
import com.ap.user.service.MemberService;

@Controller
public class MyPageController {
	
	@Autowired
	MemberService mService;
	
	String MB_ID = null;
	
	@GetMapping("/")
	public String mypage(HttpServletRequest request, Model model) {
		Member member = new Member(null, null, null, null);
		model.addAttribute("member", member);
		
		Cookie[] getCookie = request.getCookies(); // 모든 쿠키 가져오기
        if(getCookie != null){ 
        	for (int i=0; i<getCookie.length; i++) {
        		Cookie c = getCookie[i]; // 객체 생성
        	
	        	if(c.getName().equals("mb_id")) {
	        		String mb_id = c.getValue(); // 쿠키 id 가져오기
	        		MB_ID = mb_id;
	        		request.setAttribute("mb_id", MB_ID);
	         	}
        	}
        }
		return "mypage";
	}
	
	@PostMapping("/update")
	public String findPwByIdAndNameAndMail(@Valid@ModelAttribute Member member, BindingResult bResult, HttpServletRequest request,
								  @RequestParam(value="mb_id", required=false) String mb_id,
	                              @RequestParam("mb_name") String mb_name,
	                              @RequestParam("mb_email") String mb_email,
	                              @RequestParam("mb_pw") String mb_pw,
	                              Model model) throws Exception {
		if(bResult.hasErrors()) {
    		request.setAttribute("mb_id", MB_ID);
			return "mypage";
		}
		
		model.addAttribute("member", member);
		mService.updateNewPassword(MB_ID, mb_name, mb_pw, mb_email);
		      
		return "mypage";
	}
}
