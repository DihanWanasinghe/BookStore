package org.example.exceptionMappers;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.example.exceptions.ObjectAlreadyExistsException;

@Provider
public class ObjectAlreadyExistsExceptionMapper implements ExceptionMapper<ObjectAlreadyExistsException> {

    @Override
    public Response toResponse(ObjectAlreadyExistsException exception) {
        return Response.status(Response.Status.CONFLICT)
                .entity(exception.getMessage()).type(MediaType.APPLICATION_JSON)
                .build();
    }
}
