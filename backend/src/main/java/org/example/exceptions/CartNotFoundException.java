package org.example.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException( String customerId ) {
        super("{ \"error\" : \"  Cart Is Empty on customerId: " + customerId + "\"}");
    }
}
