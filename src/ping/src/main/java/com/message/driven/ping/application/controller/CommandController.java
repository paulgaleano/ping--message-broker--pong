package com.message.driven.ping.application.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.Suspended;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.message.driven.ping.application.dto.PongDto;
import com.message.driven.ping.domain.data.PingMessage;
import com.message.driven.ping.domain.service.impl.CommandServiceImpl;

import java.util.concurrent.BlockingQueue;
import javax.ws.rs.container.AsyncResponse;
import java.util.concurrent.ArrayBlockingQueue;

@Path("/command")
@Controller
@Produces("application/json")
@Consumes("application/json")
public class CommandController{
	@Autowired
	private CommandServiceImpl commandServiceImpl;
	
	private static final BlockingQueue<AsyncResponse> suspended =
            new ArrayBlockingQueue<AsyncResponse>(5);
	
	@GET
    public void receiveCommand(@Suspended AsyncResponse ar, @QueryParam("command") String command) throws InterruptedException, CommandException {
		if(!CommandType.CommandValidator(command)) {
			throw new CommandException("Command sent is incorrect"); 	
		}
		suspended.put(ar);
    		
		PingMessage pingMessage=commandMapper(command);
		if(pingMessage.equals(null)) {
			throw new CommandException("Command is imposible to cast to PingMessage"); 	
		}
		
		commandServiceImpl.processPingMessage(pingMessage).subscribe();
    }
	
	public void responseCommand(PongDto pongDto) throws InterruptedException{
		final AsyncResponse ar = suspended.take();
        ar.resume(pongDto.toJson());
	} 
	
	private PingMessage commandMapper(String command) {
		PingMessage pingMessage=null;
		if(CommandType.PING_MESSAGE.toString().equals(command)){
			pingMessage=new PingMessage(command);
		}
		return pingMessage;
	}
}
