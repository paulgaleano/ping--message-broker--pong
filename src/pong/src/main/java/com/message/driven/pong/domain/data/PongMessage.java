package com.message.driven.pong.domain.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PongMessage {
	private String date;
	private String message;
	public PongMessage(String message) {
		this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
		this.message = message;
	}
	
	public String getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}
}
