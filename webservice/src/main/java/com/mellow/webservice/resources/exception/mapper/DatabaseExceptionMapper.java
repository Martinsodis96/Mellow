package com.mellow.webservice.resources.exception.mapper;

import com.mellow.application.jpaservice.service.exception.DatabaseException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
public final class DatabaseExceptionMapper implements ExceptionMapper<DatabaseException> {

    @Override
    public Response toResponse(DatabaseException e) {
        return Response.status(INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
}