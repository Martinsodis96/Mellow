package com.mellow.config;

import com.mellow.resources.ProfileResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public final class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(ProfileResource.class);
	}
	
}
