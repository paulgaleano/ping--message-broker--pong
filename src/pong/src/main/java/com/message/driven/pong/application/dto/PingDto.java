package com.message.driven.pong.application.dto;

public class PingDto  {
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
}
