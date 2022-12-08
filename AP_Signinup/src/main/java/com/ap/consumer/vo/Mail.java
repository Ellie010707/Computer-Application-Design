package com.ap.consumer.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Mail {
	private String address;
	private String title;
	private String message;
}
