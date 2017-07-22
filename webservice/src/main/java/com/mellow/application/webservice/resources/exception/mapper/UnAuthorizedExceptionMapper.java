package com.mellow.application.webservice.resources.exception.mapper;

import com.mellow.application.jpaservice.service.exception.UnAuthorizedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Provider
public final class UnAuthorizedExceptionMapper implements ExceptionMapper<UnAuthorizedException> {

    @Override
    public Response toResponse(UnAuthorizedException e) {
        return Response.status(UNAUTHORIZED).entity(e.getMessage()).build();
    }
}