package com.message.driven.ping.application.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PongDto {
	private String date;
	private String message;
	
	//Es necesario tener el metodo get para poder utilizar la serialización de jackson en json.
	public String getDate() {
		return date;
	}

	//Es necesario tener el metodo get para poder utilizar la serialización de jackson en json.
	public String getMessage() {
		return message;
	}
	
	//Es necesario tener el metodo set para poder utilizar BeanUtils.copyProperties.
	public void setDate(String date) {
		this.date = date;
	}
	
	//Es necesario tener el metodo set para poder utilizar BeanUtils.copyProperties.
	public void setMessage(String message) {
		this.message = message;
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
