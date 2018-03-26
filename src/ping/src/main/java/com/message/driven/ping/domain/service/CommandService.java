package com.message.driven.ping.domain.service;

import com.message.driven.ping.domain.data.PingMessage;

import io.reactivex.Flowable;

public interface CommandService {
	public Flowable processPingMessage(PingMessage pingMessage);
}
