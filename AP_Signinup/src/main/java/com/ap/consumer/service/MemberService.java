package com.ap.consumer.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ap.consumer.vo.Member;

public interface MemberService extends UserDetailsService {
		//사용자 정보 조회
		public Member findByMbid(String mb_id);
		
		//회원가입
		public void insertNewAccount(Member member);
		
		//아이디 찾기
		public String findIdByNameAndEmail(String mb_name, String mb_email);
		
		//비밀번호 찾기
		public int findPwByIdAndNameAndEmail(String mb_id, String mb_name, String mb_email);
		
		//비밀번호 업데이트
		public void updateNewPassword(String mb_id, String new_pw);
}
