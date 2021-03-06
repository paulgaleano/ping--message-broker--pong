package com.message.driven.pong.application.adapter.impl;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.message.driven.pong.application.adapter.MQAdapter;
import com.message.driven.pong.application.dto.PingDto;
import com.message.driven.pong.application.dto.PongDto;
import com.message.driven.pong.domain.data.PingMessage;
import com.message.driven.pong.domain.data.PongMessage;
import com.message.driven.pong.domain.service.impl.MessageServiceImpl;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Component
public class RabbitMQAdapterImpl implements MQAdapter {
	@Autowired
	private MessageServiceImpl messageServiceImpl;
		
	@Value("${com.message.driven.pong.adapter.mq.host}")
	private String mqHost;
	
	@Value("${com.message.driven.pong.adapter.mq.exchange.name}")
	private String mqExchangeName;
	
	@Value("${com.message.driven.pong.adapter.mq.routingkey}")
	private String mqRoutingkey;
		
	public RabbitMQAdapterImpl(@Value("${com.message.driven.pong.adapter.mq.host}") String mqHost, @Value("${com.message.driven.ping.adapter.mq.queue.name}") String mqQueueName){
		ConnectionFactory connectionFactory=new ConnectionFactory();
		connectionFactory.setHost(mqHost);
	    try {
	    		Connection connection = connectionFactory.newConnection();
	    		Channel channel = connection.createChannel();	
		    channel.queueDeclare(mqQueueName, false, false, false, null);
		    
		    Consumer consumer = new DefaultConsumer(channel) {
		        @Override
		        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
		            throws IOException {
		        		messageDeliveryProcess(consumerTag,envelope,properties,body);
		        }
		      };
		      channel.basicConsume(mqQueueName, true, consumer);
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private PingDto jsonToPingMessage(String json){
		ObjectMapper mapper = new ObjectMapper();
		PingDto pingDto=null;
		try {
			pingDto = mapper.readValue(json, PingDto.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pingDto;
	}
	
	private void messageDeliveryProcess(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
		String message;
		try {
			message = new String(body, "UTF-8");
			PingDto pingDto=jsonToPingMessage(message);
			
			PingMessage pingMessage=new PingMessage();
			BeanUtils.copyProperties(pingDto, pingMessage);
			
			PongMessage pongMessage=this.messageServiceImpl.processRequestMessase(pingMessage);
			
			
			PongDto pongDto=new PongDto();
			BeanUtils.copyProperties(pongMessage, pongDto);
			
			this.sendMessage(pongDto);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void sendMessage(PongDto pongDto) {
		ConnectionFactory connectionFactory=new ConnectionFactory();
		connectionFactory.setHost(this.mqHost);
		try {
			Connection connection=connectionFactory.newConnection();
			Channel channel=connection.createChannel();	
			channel.basicPublish(this.mqExchangeName, this.mqRoutingkey, null, pongDto.toJson().getBytes());
			channel.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
