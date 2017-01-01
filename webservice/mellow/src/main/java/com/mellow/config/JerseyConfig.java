package com.mellow.config;

import com.mellow.resources.*;
import com.mellow.resources.exception.mapper.InvalidUserExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public final class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(UserResource.class);
		register(InvalidUserExceptionMapper.class);
		register(ChatResource.class);
		register(CommentResource.class);
		register(LikeResource.class);
		register(MessageResource.class);
		register(PostResource.class);
	}
	
}
