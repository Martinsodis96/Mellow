package com.mellow.security.jwt;

import com.mellow.service.AuthenticationService;
import com.mellow.service.exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

public class AuthorizationRequestFilter implements ContainerRequestFilter {

    @Autowired
    private AuthenticationService authenticationService;
    private UriInfo uriInfo;
    private String authorizationHeader;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if(uriRequiresToken(requestContext)){
            if (authenticationHeaderIsPresent(requestContext)) {
                String token = authorizationHeader.substring("Bearer".length()).trim();
                authenticationService.validateToken(token);
            }else{
                throw new UnAuthorizedException("Authorization header must be provided");
            }
        }
    }

    private boolean uriRequiresToken(ContainerRequestContext containerRequestContext){
        this.uriInfo = containerRequestContext.getUriInfo();
        return !"register".equals(uriInfo.getPath()) && !"login".equals(uriInfo.getPath());
    }

    private boolean authenticationHeaderIsPresent(ContainerRequestContext containerRequestContext){
        this.authorizationHeader = containerRequestContext.getHeaderString("Authorization");
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }
}