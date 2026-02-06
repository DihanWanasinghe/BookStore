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

/**
 *
 * @author Hans
 */
//Intercepts when  the request body is empty , for a POST method endpoint that accepts arguments passed from thr request body 
// eg - POST bookstore/api/customers requires a  Customer json object , however user havent provided anything in the request body
@Provider
public class NullPointerExceptionMapper implements ExceptionMapper<NullPointerException>{

    @Override
    public Response toResponse(NullPointerException e) {
       return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"Object Required in the Request Body\"}").type(MediaType.APPLICATION_JSON).build();
    }
    
    
    
}
