package com.message.driven.ping.application.adapter.impl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.message.driven.ping.application.adapter.MQAdapter;
import com.message.driven.ping.application.controller.CommandController;
import com.message.driven.ping.application.dto.PingDto;
import com.message.driven.ping.application.dto.PongDto;

@Component
public class RabbitMQAdapterImpl implements MQAdapter{
	@Autowired
	private CommandController commandController;
		
	@Value("${com.message.driven.ping.adapter.mq.host}")
	private String mqHost;
	
	@Value("${com.message.driven.ping.adapter.mq.exchange.name}")
	private String mqExchangeName;
	
	@Value("${com.message.driven.ping.adapter.mq.routingkey}")
	private String mqRoutingkey;
	
	public RabbitMQAdapterImpl(@Value("${com.message.driven.pong.adapter.mq.host}") String mqHost, @Value("${com.message.driven.pong.adapter.mq.queue.name}") String mqQueueName){
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
	
	private void messageDeliveryProcess(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
		String message;
		try {
			message = new String(body, "UTF-8");
			PongDto pongDto=jsonToPongMessage(message);
			this.commandController.responseCommand(pongDto);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private PongDto jsonToPongMessage(String json){
		ObjectMapper mapper = new ObjectMapper();
		PongDto pongDto=null;
		try {
			pongDto = mapper.readValue(json, PongDto.class);
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
		return pongDto;
	}
	
	public void sendMessage(PingDto pingDto) {
		ConnectionFactory connectionFactory=new ConnectionFactory();
		connectionFactory.setHost(this.mqHost);
		try{
			Connection connection=connectionFactory.newConnection();
			Channel channel=connection.createChannel();	
			channel.basicPublish(this.mqExchangeName, this.mqRoutingkey, null, pingDto.toJson().getBytes());
			channel.close();
			connection.close();
		}catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
