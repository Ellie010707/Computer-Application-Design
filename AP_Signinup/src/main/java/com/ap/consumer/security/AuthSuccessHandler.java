package com.ap.consumer.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ap.consumer.service.MemberService;
import com.ap.consumer.vo.Member;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler{
	
	@Autowired
	MemberService memberService;
	
	private final String DEFAULT_SUCCESS_URL = "http://localhost:8000/map";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		/*사용자 정보를 세션에 담아 redirect*/
		 String mb_id = authentication.getName();
		 //Member member = memberService.findByMbid(mb_id);
		 
		 Cookie setCookie = new Cookie("mb_id", mb_id); // 쿠키 이름을 name으로 생성
		 setCookie.setPath("http://localhost:8000/");
		 setCookie.setMaxAge(60*60*24); // 기간을 하루로 지정(60초 * 60분 * 24시간)

		 response.addCookie(setCookie); // response에 Cookie 추가
		 response.sendRedirect(DEFAULT_SUCCESS_URL);
	}
	
	

}
