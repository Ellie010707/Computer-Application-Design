package com.ap.user.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ap.user.validation.FieldMatch;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@FieldMatch(first = "mb_pw", second = "mb_pw_confirm", message = "입력하신 비밀번호와 일치하지 않습니다")
public class Member {
	
	@NotBlank(message = "비밀번호를 입력해주세요")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$", message = "영문자,숫자 혼합 8자~16자로 입력해주세요")
	private String mb_pw;
	
	@NotBlank(message = "비밀번호를 한번 더 입력해주세요")
	private String mb_pw_confirm;
	
	@NotBlank(message = "이름을 입력해주세요")
	private String mb_name;
	
	@NotBlank(message = "이메일을 입력해주세요")
	@Email
	private String mb_email;

}
