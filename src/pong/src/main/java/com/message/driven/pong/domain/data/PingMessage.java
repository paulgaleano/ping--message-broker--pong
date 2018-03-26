package com.message.driven.pong.domain.data;

public class PingMessage  {
	private String date;
	private String message;
	
	public String getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}
	
	//Es necesario tener el metodo get para poder utilizar la deserialización de jackson en json.
	public void setDate(String date) {
		this.date = date;
	}
	
	//Es necesario tener el metodo get para poder utilizar la deserialización de jackson en json.
	public void setMessage(String message) {
		this.message = message;
	}
}
