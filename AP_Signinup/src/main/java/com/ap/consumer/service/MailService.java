package com.ap.consumer.service;

import com.ap.consumer.vo.Mail;

public interface MailService {
	public void sendPwChangeEmail(String mb_id, String mb_name, String mb_email) throws Exception;
}
