package com.mellow.resources.exception.mapper;

import com.mellow.exception.InvalidUserException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public final class InvalidUserExceptionMapper implements ExceptionMapper<InvalidUserException>{

    @Override
    public Response toResponse(InvalidUserException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}