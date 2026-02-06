package org.example.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String orderId){
        super("{ \"error\" : \" Order not Found on orderId:" + orderId + " \" }" );
    }
}
