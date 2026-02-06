/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.exceptions;

/**
 *
 * @author Hans
 */
public class MissingRequiredObjectPropertyValueException extends RuntimeException{

    public MissingRequiredObjectPropertyValueException(String message) {
        
        super(message);
        //super("{ \"error\" : \" Essential information is missing  on the provided " + objectType+ " \"}");
    }
    
    
    
}
