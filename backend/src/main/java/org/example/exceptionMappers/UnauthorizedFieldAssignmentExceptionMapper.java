/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.exceptionMappers;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.example.exceptions.UnauthorizedFieldAssignmentException;

/**
 *
 * @author Hans
 */

@Provider
public class UnauthorizedFieldAssignmentExceptionMapper implements  ExceptionMapper<UnauthorizedFieldAssignmentException>{

    @Override
    public Response toResponse(UnauthorizedFieldAssignmentException e) {
      return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.APPLICATION_JSON).build();
    }
    
    
}
