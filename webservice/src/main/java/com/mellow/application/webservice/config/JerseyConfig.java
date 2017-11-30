package com.mellow.application.webservice.config;


import com.mellow.application.webservice.resources.AuthenticationResource;
import com.mellow.application.webservice.resources.CommentResource;
import com.mellow.application.webservice.resources.LikeResource;
import com.mellow.application.webservice.resources.PostResource;
import com.mellow.application.webservice.resources.UserResource;
import com.mellow.application.webservice.resources.exception.mapper.DatabaseExceptionMapper;
import com.mellow.application.webservice.resources.exception.mapper.InvalidInputExceptionMapper;
import com.mellow.application.webservice.resources.exception.mapper.NoSearchResultExceptionMapper;
import com.mellow.application.webservice.resources.exception.mapper.UnAuthorizedExceptionMapper;
import com.mellow.application.webservice.security.jwt.AuthorizationRequestFilter;
import com.mellow.application.webservice.security.jwt.AuthorizationResponseFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public final class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(DatabaseExceptionMapper.class);
        register(InvalidInputExceptionMapper.class);
        register(NoSearchResultExceptionMapper.class);
        register(UnAuthorizedExceptionMapper.class);

        register(UserResource.class);
        register(AuthenticationResource.class);
        register(CommentResource.class);
        register(LikeResource.class);
        register(PostResource.class);

        register(AuthorizationRequestFilter.class);
        register(AuthorizationResponseFilter.class);
    }

}
