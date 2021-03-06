package com.message.driven.ping.application.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PingDto  {
	private String date;
	private String message;
	
	public PingDto() {
	}
		
	public String getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}
	
	public String toJson() {
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			json=mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
