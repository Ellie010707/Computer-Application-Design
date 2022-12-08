package com.ap.consumer.service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ap.consumer.vo.Mail;

import lombok.AllArgsConstructor;

@Service
public class MailServiceImpl implements MailService{

	@Autowired
	MemberService mService;
	
	@Autowired
	private JavaMailSender mailSender;
	
    private static final String HOST_ADDRESS = "ghj8617@naver.com";
	
    //임시 비밀번호 변경 이메일 생성
	public MimeMessage createPwChangeEmail(String mb_id, String mb_name, String mb_email) throws Exception {

		String tempPw = createTempPw();
		MimeMessage email = mailSender.createMimeMessage();
		
		String title = "동물약국🐶🐱🐹 임시 비밀번호 안내 이메일 입니다.";
		String message = "<div style='margin:100px;'>"+
					"<h1>안녕하세요 "+mb_name+ "님! 동물약국🐶🐱🐹 입니다</h1>"+
					"<br><p>임시 비밀번호를 발급해드렸습니다."+
					"<br>임시 비밀번호로 로그인 후 비밀번호를 반드시 변경해주세요!</p>"+
					"<br><div align='center' style='border:1px solid black;'>"+
					"<h2>임시 비밀번호 : <strong>"+tempPw+"</strong></h2>"+
					"</div></div>";
		
		email.addRecipients(RecipientType.TO, mb_email);
		email.setSubject(title);
		email.setText(message, "utf-8", "html");
		email.setFrom(new InternetAddress(HOST_ADDRESS,"AnimalPharmacy"));
		
		updateTempPw(mb_id, tempPw);
		
		return email;
	}
	
	//임시 비밀번호 생성
	public static String createTempPw() {
		
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		
		String tempPw = "";
		
		int index = 0;
		for (int i=0; i<10; i++ ) {
			index = (int) (charSet.length * Math.random());
			tempPw += charSet[index];
		}
		
		return tempPw;
	}
	
	//임시 비밀번호 업데이트
	public void updateTempPw(String mb_id, String tempPw) {
		mService.updateNewPassword(mb_id, tempPw);
	}
	
	//임시 비밀번호 변경 메일 전송
	@Override
	public void sendPwChangeEmail(String mb_id, String mb_name, String mb_email) throws Exception {
		
		MimeMessage email = createPwChangeEmail(mb_id, mb_name, mb_email);
		
		try {
			mailSender.send(email);
		} catch (MailException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		
		System.out.println("emailsender : email send succeed");
	}

}
 