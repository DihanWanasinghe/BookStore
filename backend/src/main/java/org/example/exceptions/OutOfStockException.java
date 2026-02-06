package org.example.exceptions;

public class OutOfStockException  extends RuntimeException{

    public OutOfStockException(String bookId, int stockQuantity) {
        super("{ \"error\" : \"Maximum Quantity Selection on bookId:"+bookId + " is "+ stockQuantity+" \" }");
    }
}
