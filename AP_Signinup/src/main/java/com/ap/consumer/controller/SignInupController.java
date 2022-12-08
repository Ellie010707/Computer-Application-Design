package com.ap.consumer.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.ap.consumer.service.MemberService;
import com.ap.consumer.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
public class SignInupController {
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	MemberService mService;
	
	@GetMapping("/")
	public String index() {
		return "login";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "login";
	}
	
	@GetMapping("/signupForm")
	public String signupForm(Model model) {
		
		Member member = new Member(null, null, null, null, null);
		model.addAttribute("member", member);
		
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid@ModelAttribute Member member,BindingResult bResult, Model model) {

		System.out.println(member.toString());
		
		Member existMember = mService.findByMbid(member.getMb_id());
		
		if(existMember != null) {
			bResult.rejectValue("mb_id", null, "이미 존재하는 아이디 입니다");
		}
		
		if(bResult.hasErrors()) {
			return "signup";
		} else {
			model.addAttribute("member", member);
			mService.insertNewAccount(member);
			
			return "redirect:http://localhost:8000/loginForm";
		}
	}
	
	@GetMapping("/map")
	public String success() {
		return "success";
	}
	
}