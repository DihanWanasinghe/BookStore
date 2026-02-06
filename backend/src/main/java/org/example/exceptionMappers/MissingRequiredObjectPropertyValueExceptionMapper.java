/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.exceptionMappers;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;
import org.example.exceptions.MissingRequiredObjectPropertyValueException;

/**
 *
 * @author Hans
 */
@Provider
public class MissingRequiredObjectPropertyValueExceptionMapper implements ExceptionMapper<MissingRequiredObjectPropertyValueException>{
    
    

    @Override
    public Response toResponse(MissingRequiredObjectPropertyValueException e) {
            Map<String,String> errorMap = new HashMap<>();
        errorMap.put("error", e.getMessage());
        
      return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).type(MediaType.APPLICATION_JSON).build();
    }
    
    
    
}
