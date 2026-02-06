package org.example.exceptions;

public class InvalidInputException extends RuntimeException {

    public  InvalidInputException(String objectType ) {
        super("{ \"error\" : \" Invalid property value assigned  on the "+  objectType + " \" } " );
    }

    public InvalidInputException(String objectType, String propertyName) {
        super("{ \"error\":\"Invalid property value assigned  on the "+  objectType + " for " + propertyName + " . Maybe the property value is unassigned \" }");
    }
}
