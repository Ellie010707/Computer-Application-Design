package com.ap.consumer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ap.consumer.dao.MemberDAO;
import com.ap.consumer.vo.Member;
import com.google.common.base.Optional;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDAO memberDAO;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	//사용자 정보 조회
	@Override
	public Member findByMbid(String mb_id) {
		
		Member member = new Member(mb_id, null, null, null, null);
		member = memberDAO.findByMbid(mb_id);
		
		return member;
	}

	//회원가입
	@Override
	public void insertNewAccount(Member member) {
		
		member.setMb_pw(passwordEncoder.encode(member.getMb_pw()));
		member.setMb_pw_confirm(passwordEncoder.encode(member.getMb_pw()));
		
		memberDAO.insertNewAccount(member);
	}

	//아이디 찾기
	@Override
	public String findIdByNameAndEmail(String mb_name, String mb_email) {
		
		String mb_id = "";
		mb_id = memberDAO.findIdByNameAndEmail(mb_name, mb_email);
		
		return mb_id;
	}
	
	//비밀번호 찾기
	@Override
	public int findPwByIdAndNameAndEmail(String mb_id, String mb_name, String mb_email) {
		
		int mb_pw = 0;
		mb_pw = memberDAO.findPwByIdAndNameAndEmail(mb_id, mb_name, mb_email);
		
		return mb_pw;
	}

	//로그인
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, InternalAuthenticationServiceException {
		
		Member member = memberDAO.findByMbid(username);
		
		if(member == null) {
			throw new UsernameNotFoundException(username + "is not exist");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
		
		return new User(member.getMb_id(), member.getMb_pw(), authorities);
	}

	//비밀번호 업데이트
	@Override
	public void updateNewPassword(String mb_id, String new_pw) {
		
		String encoded_new_pw = passwordEncoder.encode(new_pw);
		
		memberDAO.updateNewPassword(mb_id, encoded_new_pw);
	}
	
	
	
}
