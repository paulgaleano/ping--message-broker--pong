package com.message.driven.pong.application.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.message.driven.pong.domain.service.impl.MessageServiceImpl;

@Path("/pong")
@Controller
@Produces("application/json")
@Consumes("application/json")
public class PongController {
	
	@Autowired
	private MessageServiceImpl messageServiceImpl;

	@Path("/ping_message_processed")
	@GET
    public String receiveCommand() {
		int messageNumber=this.messageServiceImpl.getMessageNumber();
		return String.valueOf(messageNumber);
    }
}
