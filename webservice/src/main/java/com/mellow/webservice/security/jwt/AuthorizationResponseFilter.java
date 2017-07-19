package com.mellow.webservice.security.jwt;

import com.mellow.application.jpaservice.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

public final class AuthorizationResponseFilter implements ContainerResponseFilter {

    private final AuthenticationService authenticationService;
    private UriInfo uriInfo;

    @Autowired
    public AuthorizationResponseFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (statusIsSuccess(responseContext.getStatus())) {
            if (uriShouldReturnRefreshToken(requestContext)) {
                responseContext.getHeaders().add("refresh_token", authenticationService.createRefreshToken());
            }
            responseContext.getHeaders().add("access_token", authenticationService.createAccessToken());
        }
    }

    private boolean uriShouldReturnRefreshToken(ContainerRequestContext containerRequestContext) {
        this.uriInfo = containerRequestContext.getUriInfo();
        return "register".equals(uriInfo.getPath()) || "login".equals(uriInfo.getPath());
    }

    private boolean statusIsSuccess(int status) {
        return String.valueOf(status).startsWith("2");
    }
}
