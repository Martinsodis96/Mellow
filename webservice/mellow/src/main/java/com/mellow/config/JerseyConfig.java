package com.mellow.config;

import com.mellow.resources.*;
import com.mellow.resources.exception.mapper.InvalidInputExceptionMapper;
import com.mellow.resources.exception.mapper.NoSearchResultExceptionMapper;
import com.mellow.resources.exception.mapper.UnAuthorizedExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public final class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(InvalidInputExceptionMapper.class);
		register(NoSearchResultExceptionMapper.class);
		register(UnAuthorizedExceptionMapper.class);

		register(UserResource.class);
		register(ChatResource.class);
		register(AuthenticationResource.class);
		register(CommentResource.class);
		register(LikeResource.class);
		register(MessageResource.class);
		register(PostResource.class);
	}
	
}
