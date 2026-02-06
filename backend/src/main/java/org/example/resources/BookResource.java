package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.BookDAO;
import org.example.exceptions.BookNotFoundException;
import org.example.models.Book;
import org.example.models.BookDTO;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.example.exceptions.InvalidInputException;
import org.example.exceptions.MissingRequiredObjectPropertyValueException;

@Path("/books")
public class BookResource {
    
    public BookResource() {
    System.out.println("BookResource initialized");
}

    static BookDAO bookDAO = new BookDAO();

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {

//        return Response.ok("Works").build();
        List<Book> bookList= bookDAO.getAllBooks();
        
            return Response.ok(bookList).type(MediaType.APPLICATION_JSON).build();
        
    }

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    
    public Response addBook(BookDTO bookDTO, @Context UriInfo uriInfo) {
        
      
        bookDAO.addBook(bookDTO);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(bookDTO.getBookId());
        URI newBookUri = builder.build();
        return Response.created(newBookUri).
                entity("{ \"info\" : \"Book succefully created on bookId: "
                        +bookDTO.getBookId()+ " \" }").type(MediaType.APPLICATION_JSON).build();
        
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("id") String bookID) {
        Book book = bookDAO.getBookById(bookID);
        return Response.ok(book).build();
    }


    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") String bookID, BookDTO updatedBookDTO) {
        
        
        
       Book updatedBook = bookDAO.updateBook(bookID, updatedBookDTO);
        return Response.ok(updatedBook).type(MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}")
    @DELETE
    public Response removeBook(@PathParam("id") String bookID) {
        bookDAO.removeBook(bookID);
      return   Response.noContent().build();
    }
}
