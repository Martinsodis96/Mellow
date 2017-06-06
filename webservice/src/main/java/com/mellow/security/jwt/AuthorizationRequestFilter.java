package com.mellow.security.jwt;

import com.mellow.service.AuthenticationService;
import com.mellow.service.exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

public final class AuthorizationRequestFilter implements ContainerRequestFilter {

    private final AuthenticationService authenticationService;
    private UriInfo uriInfo;
    private String authorizationHeader;

    @Autowired
    public AuthorizationRequestFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (uriRequiresToken(requestContext)) {
            if (authenticationHeaderIsPresent(requestContext)) {
                String token = authorizationHeader.substring("Bearer".length()).trim();
                if (refreshToken()) {
                    authenticationService.validateRefreshToken(token);
                } else {
                    authenticationService.validateAccessToken(token);
                }
            } else {
                throw new UnAuthorizedException("Authorization header must be provided");
            }
        }
    }

    private boolean uriRequiresToken(ContainerRequestContext containerRequestContext) {
        this.uriInfo = containerRequestContext.getUriInfo();
        return !"register".equals(uriInfo.getPath()) && !"login".equals(uriInfo.getPath());
    }

    private boolean refreshToken() {
        return "auth".equals(uriInfo.getPath());
    }

    private boolean authenticationHeaderIsPresent(ContainerRequestContext containerRequestContext) {
        this.authorizationHeader = containerRequestContext.getHeaderString("Authorization");
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }
}