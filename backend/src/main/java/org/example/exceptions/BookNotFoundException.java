package org.example.exceptions;


public class BookNotFoundException extends  RuntimeException{


    public BookNotFoundException(String bookId){
        super( " { \"error\" :  \" Book with the bookId: "+bookId+ " does not exist\" }");

    }
}
