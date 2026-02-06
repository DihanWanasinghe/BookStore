package org.example.exceptions;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String customerId) {
        super("{ \"error\" : \" Customer with the customerId:"+customerId +" does not exist \" }");
    }
}
