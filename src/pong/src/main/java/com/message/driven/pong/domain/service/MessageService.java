package com.message.driven.pong.domain.service;

import com.message.driven.pong.domain.data.PingMessage;
import com.message.driven.pong.domain.data.PongMessage;

public interface MessageService {
	public PongMessage processRequestMessase(PingMessage pingMessage);
	public void increaseMessageNumber();
	public int getMessageNumber();
}
