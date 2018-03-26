package com.message.driven.pong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages={"com.message.driven.pong.application.configuration","com.message.driven.pong.domain.service.impl","com.message.driven.pong.application.adapter.impl","com.message.driven.pong.application.controller"})
public class PongApplication {	
	public static void main(String[] args) {
		SpringApplication.run(PongApplication.class, args);
	}
}
