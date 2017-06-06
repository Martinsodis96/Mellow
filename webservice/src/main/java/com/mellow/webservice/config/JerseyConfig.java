package com.mellow.webservice.config;

import com.mellow.webservice.resources.*;
import com.mellow.webservice.resources.exception.mapper.InvalidInputExceptionMapper;
import com.mellow.webservice.resources.exception.mapper.NoSearchResultExceptionMapper;
import com.mellow.webservice.resources.exception.mapper.UnAuthorizedExceptionMapper;
import com.mellow.webservice.security.jwt.AuthorizationRequestFilter;
import com.mellow.webservice.security.jwt.AuthorizationResponseFilter;
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

        register(AuthorizationRequestFilter.class);
        register(AuthorizationResponseFilter.class);
    }

}
