package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.CustomerDAO;
import org.example.models.Order;

import java.net.URI;
import java.util.List;

public class OrderResource {

   private final String customerId;

   public OrderResource(String customerId) {
       this.customerId = customerId;
   }


    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOrder(  @Context UriInfo uriInfo) {
        Order order=  CustomerResource.customerDAO.addOrder(customerId);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(order.getOrderId().toString());
        URI newOrderUri = builder.build();

        return Response.created(newOrderUri).entity(order).type(MediaType.APPLICATION_JSON).build();
    }

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() {
        List<Order> orders = CustomerResource.customerDAO.getAllOrders(customerId);
        return Response.ok(orders).type(MediaType.APPLICATION_JSON).build();
    }

    @Path("/{orderId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById( @PathParam("orderId") String orderId) {
        Order order = CustomerResource.customerDAO.getOrderByID(customerId, orderId);
        return Response.ok(order).type(MediaType.APPLICATION_JSON).build();
    }

}
