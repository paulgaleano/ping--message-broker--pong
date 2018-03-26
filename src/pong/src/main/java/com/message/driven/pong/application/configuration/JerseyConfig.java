package com.message.driven.pong.application.configuration;


import org.springframework.stereotype.Component;

import com.message.driven.pong.application.controller.PongController;

import org.glassfish.jersey.server.ResourceConfig;

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
        register(PongController.class);
    }
}
