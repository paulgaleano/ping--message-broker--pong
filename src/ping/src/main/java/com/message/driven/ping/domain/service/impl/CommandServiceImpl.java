package com.message.driven.ping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import com.message.driven.ping.application.adapter.impl.RabbitMQAdapterImpl;
import com.message.driven.ping.application.dto.PingDto;
import com.message.driven.ping.domain.data.PingMessage;
import com.message.driven.ping.domain.service.CommandService;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;


@Service
public class CommandServiceImpl implements CommandService {
	
	@Autowired
	private RabbitMQAdapterImpl rabbitMqAdapterImpl;
	
	@Override
	public Flowable processPingMessage(PingMessage pingMessage) {
		return Observable.create(inSource -> {
            try {
            	PingDto pingDto=new PingDto();
        		BeanUtils.copyProperties(pingMessage, pingDto);
        		this.rabbitMqAdapterImpl.sendMessage(pingDto);
                inSource.onComplete();
            } catch (final Exception theException) {
                inSource.onError(theException);
            }
        }).toFlowable(BackpressureStrategy.BUFFER);	
	}
}
