package com.ap.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ap.user.dao.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDAO memberDAO;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	

	//비밀번호 업데이트
	@Override
	public void updateNewPassword(String mb_id, String mb_name, String new_pw, String mb_email) {
		
		String encoded_new_pw = passwordEncoder.encode(new_pw);
		
		memberDAO.updateNewPassword(mb_id, mb_name, encoded_new_pw, mb_email);
	}
	
	
	
}
