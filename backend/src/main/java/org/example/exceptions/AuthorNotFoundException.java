package org.example.exceptions;


public class AuthorNotFoundException  extends  RuntimeException{

    
    public AuthorNotFoundException(String authorId) {
    super("{ \"error\" : \"Author with the authorId: "+authorId+ " does not exist\"}");
}

     
}
