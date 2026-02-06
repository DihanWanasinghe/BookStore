package org.example.exceptions;

public class ObjectAlreadyExistsException extends RuntimeException {



    public ObjectAlreadyExistsException( String objectType) {
                super("{ \"error\" : \" " +objectType + " already exists  \" }");

    }
}
