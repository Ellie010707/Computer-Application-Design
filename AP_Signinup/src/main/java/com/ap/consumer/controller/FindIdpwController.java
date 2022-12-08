package com.ap.consumer.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.ap.consumer.service.MailService;
import com.ap.consumer.service.MemberService;
import com.ap.consumer.vo.Mail;
import com.ap.consumer.vo.Member;

@Controller
public class FindIdpwController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	MailService mailService;
	
	//아이디/비밀번호 찾기 화면
	@GetMapping("/findForm")
	public String findForm(Model model, HttpServletRequest req) {
		
		Member tempMember = new Member(null, null, null, null, null);
		
		model.addAttribute("idMember",tempMember);
		model.addAttribute("pwMember", tempMember);
		
		return "findForm";
	
	}
	
	//아이디 찾기
	@PostMapping("/findId")
	public String findIdByName(@RequestParam("mb_name") String mb_name,
							@RequestParam("mb_email") String mb_email,
							Model model) {
		
		String idResult = "success";
		
		Member idMember = new Member(null, null, null, mb_name, mb_email);
		String existId = memberService.findIdByNameAndEmail(mb_name, mb_email);
		
		//PwForm용 더미데이터
		Member pwMember = new Member(null, null, null, null, null);
		
		if(existId==null) {
			idResult = "error";
		} else {
			idMember.setMb_id(existId);
		}
		
		model.addAttribute("idResult", idResult);
		model.addAttribute("idMember", idMember);
		model.addAttribute("pwMember", pwMember);
		
		return "findForm";
	}
	
	//비밀번호 찾기
	@PostMapping("/findPw")
	public String findPwByIdAndNameAndMail(@RequestParam("mb_id") String mb_id,
										@RequestParam("mb_name") String mb_name,
										@RequestParam("mb_email") String mb_email,
										Model model) throws Exception {
		String pwResult = "success";
		
		Member pwMember = new Member(mb_id, null, null, mb_name, mb_email);
		int existPW = memberService.findPwByIdAndNameAndEmail(mb_id, mb_name, mb_email);
		
		
		//idForm용 더미데이터
		Member idMember = new Member(null, null, null, null, null);

		if(existPW==0) {
			pwResult = "error";
		} else {
			//임시 비밀번호 생성 및 이메일로 전송
			mailService.sendPwChangeEmail(mb_id, mb_name, mb_email);
		}
		
		model.addAttribute("pwResult", pwResult);
		model.addAttribute("pwMember", pwMember);
		model.addAttribute("idMember", idMember);
		
		return "findForm";
	}
}
