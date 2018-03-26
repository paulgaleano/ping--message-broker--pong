package com.message.driven.ping.application.configuration;


import org.springframework.stereotype.Component;

import com.message.driven.ping.application.controller.CommandController;

import org.glassfish.jersey.server.ResourceConfig;

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
        register(CommandController.class);
    }
}
