package com.mellow.application.webservice.resources.exception.mapper;

import com.mellow.application.jpaservice.service.exception.InvalidInputException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public final class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {

    @Override
    public Response toResponse(InvalidInputException e) {
        return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
    }
}