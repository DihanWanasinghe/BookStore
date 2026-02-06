package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.CustomerDAO;
import org.example.models.Customer;
import java.net.URI;
import java.util.List;
import org.example.exceptions.MissingRequiredObjectPropertyValueException;
import org.example.exceptions.UnauthorizedFieldAssignmentException;
import org.example.models.CustomerDTO;


@Path("/customers")
public class CustomerResource {

    public static CustomerDAO customerDAO = new CustomerDAO();

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCustomer(Customer customer, @Context UriInfo uriInfo) {
        
        
        
        
        
        customerDAO.addCustomer(customer);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(customer.getCustomerId());
        URI newCustomerUri = builder.build();

        return Response.created(newCustomerUri).entity(
        "{ \"info\" : \"Customer succefully created on customerId: "+customer.getCustomerId()+ " \" }").type(MediaType.APPLICATION_JSON).build();
        
    }
    @Path("/")
    @GET
    public Response getAllCustomers() {

        List<Customer> customerList= customerDAO.getAllCustomers();
        return Response.ok(customerList).type(MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") String customerId) {
        Customer customer = customerDAO.getCustomerById(customerId);

        return Response.ok(customer).type(MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") String customerId, CustomerDTO updatedCustomerDTO) {
        customerDAO.updateCustomer(customerId, updatedCustomerDTO);
        return Response.ok(updatedCustomerDTO).type(MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}")
    @DELETE
    public Response removeCustomer(@PathParam("id") String customerID){
        customerDAO.removeCustomer(customerID);
        return  Response.noContent().build();
    }

    @Path("/{customerId}/cart")
    public CartResource getCartResource(@PathParam("customerId") String customerId) {
        return new CartResource(customerId);
    }

    @Path("/{customerId}/orders")
    public OrderResource getOrderResource(@PathParam("customerId") String customerId) {
        return new OrderResource(customerId);
    }



}

