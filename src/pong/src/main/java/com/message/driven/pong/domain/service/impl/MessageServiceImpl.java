package com.message.driven.pong.domain.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.message.driven.pong.domain.data.PingMessage;
import com.message.driven.pong.domain.data.PongMessage;
import com.message.driven.pong.domain.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	private int messageNumber=0;
	
	@Value("${com.message.driven.pong.domain.service.impl.message}")
	private String message;
	
	@Override
	public PongMessage processRequestMessase(PingMessage pingMessage) {
		try {
			increaseMessageNumber();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PongMessage pongMessage=new PongMessage(this.message);
		return pongMessage;
	}
	
	@Override
	public void increaseMessageNumber() {
		this.messageNumber+=1;
	}
	
	@Override
	public int getMessageNumber() {
		return this.messageNumber;
	}
}