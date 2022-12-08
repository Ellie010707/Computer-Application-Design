package com.ap.user.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {
	
	//비밀번호 업데이트
	public void updateNewPassword(String mb_id, String mb_name, String encoded_new_pw, String mb_email);
}
