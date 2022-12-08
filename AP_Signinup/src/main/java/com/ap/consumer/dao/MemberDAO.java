package com.ap.consumer.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ap.consumer.vo.Member;
import com.google.common.base.Optional;

@Mapper
public interface MemberDAO {
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
