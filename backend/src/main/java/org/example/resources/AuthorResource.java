package org.example.resources;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.AuthorDAO;
import org.example.models.Author;
import org.example.models.Book;

import java.net.URI;
import java.util.List;
import java.util.MissingFormatArgumentException;
import org.example.exceptions.InvalidInputException;
import org.example.exceptions.MissingRequiredObjectPropertyValueException;
import org.example.exceptions.UnauthorizedFieldAssignmentException;

@Path("/authors")
public class AuthorResource {
   static  AuthorDAO authorDAO = new AuthorDAO();

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAuthor(Author author, @Context UriInfo uriInfo) {
       
        authorDAO.addAuthor(author);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(author.getAuthorId());
        URI newAuthorUri = builder.build();

        return Response.created(newAuthorUri).entity("{ \"info\" : \"Author succefully created on authorId: "+author.getAuthorId() + " \" }").
                type(MediaType.APPLICATION_JSON).build();
        
    }


    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors() {

       List<Author> authorList =   authorDAO.getAllAuthors();

           return Response.ok(authorList).type(MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthor(@PathParam("id") String authorId) {
       Author author =  authorDAO.getAuthorByID(authorId);
       return Response.ok(author).type(MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAuthor(@PathParam("id") String authorId, Author updatedAuthor) {
    
        authorDAO.updateAuthor(authorId, updatedAuthor);
        return Response.ok(updatedAuthor).type(MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}")
    @DELETE
    public Response removeAuthor(@PathParam("id") String authorId) {
        authorDAO.removeAuthor(authorId);
        return Response.noContent().build();
    }

    @Path("/{id}/books")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(@PathParam("id") String authorId) {
        List<Book>booksByAuthor =authorDAO.getAllBooksByAuthor(authorId);
           return Response.ok(booksByAuthor).type(MediaType.APPLICATION_JSON).build();
    }

}
