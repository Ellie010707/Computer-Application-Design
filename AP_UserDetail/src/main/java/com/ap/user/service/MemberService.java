package com.ap.user.service;

public interface MemberService {
		//비밀번호 업데이트
		public void updateNewPassword(String mb_id, String mb_name, String new_pw, String mb_email);
}
