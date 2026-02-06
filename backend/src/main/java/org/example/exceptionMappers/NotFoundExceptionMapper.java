/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.exceptionMappers;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author Hans
 */
// Intercepts when an invalid endpoint is used
// eg - bookstore/api/employees
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException>{

    @Override
    public Response toResponse(NotFoundException e) {
        
      return  Response.status(Response.Status.NOT_FOUND).
              entity("{\"error\": \"Invalid endpoint, Resource not available\"}").type(MediaType.APPLICATION_JSON).build();
    }
    
    
    
}
