package org.example.resources;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.CustomerDAO;
import org.example.models.CartItem;

import java.net.URI;
import java.util.List;
import org.example.models.Cart;

public class CartResource {

    private final String customerId;

    public CartResource(String customerId) {
        this.customerId = customerId;
    }

    @Path("/items")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCartItem( CartItem cartItem, @Context UriInfo uriInfo) {
        CustomerResource.customerDAO.addCartItem(customerId, cartItem);
        URI currentEndpoint = uriInfo.getAbsolutePath();
        String currentEndpointStr = currentEndpoint.toString();
        String modifiedUriStr = currentEndpointStr.substring(0, currentEndpointStr.lastIndexOf('/'));
        UriBuilder builder = UriBuilder.fromUri(modifiedUriStr);
        URI updatedUri = builder.build();
        return Response.created(updatedUri)
                .entity("{ \"info\" : \"CartItem succefully added to customer on  customerId: "+customerId+ " \" }").type(MediaType.APPLICATION_JSON).build();
    }

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart() {
        Cart cart = CustomerResource.customerDAO.getCart(customerId);
        return Response.ok(cart).type(MediaType.APPLICATION_JSON).build();
    }

    @Path("/items/{bookId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCartItem( @PathParam("bookId") String bookId, CartItem cartItem) {
        CustomerResource.customerDAO.updateCartItem(customerId, bookId, cartItem);
        return Response.ok(cartItem).type(MediaType.APPLICATION_JSON).build();
    }

    @Path("/items/{bookId}")
    @DELETE
    public Response removeCartItem( @PathParam("bookId") String bookId) {
        CustomerResource.customerDAO.removeCartItem(customerId, bookId);
        return Response.noContent().build();
    }


}
