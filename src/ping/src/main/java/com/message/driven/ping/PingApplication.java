	package com.message.driven.ping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@ComponentScan(basePackages={"com.message.driven.ping.application.configuration","com.message.driven.ping.application.controller", "com.message.driven.ping.domain.service.impl","com.message.driven.ping.application.adapter.impl"})
public class PingApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PingApplication.class, args);
	}
}
