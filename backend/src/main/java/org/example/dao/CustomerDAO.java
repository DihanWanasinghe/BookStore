package org.example.dao;

import java.text.SimpleDateFormat;
import org.example.exceptions.*;
import org.example.models.*;
import org.example.models.Customer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomerDAO {

    private BookDAO bookDAO = new BookDAO();
    private static List<Customer> customers = new ArrayList<>();
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public CustomerDAO() {
    }

    //returns a list of Bookstore customers
    public List<Customer> getAllCustomers() {

        return customers;
    }

    // add a customer to the Bookstore customers list
    public void addCustomer(Customer newCustomer) {

        //checks whether email and password are assigned on the newCustomer as they are mandatory fields for a customer
        if (newCustomer.getEmail() == null || newCustomer.getPassword() == null) {
            throw new MissingRequiredObjectPropertyValueException("Email and Password should be provided for the Customer");
            // checks whether customerId,orders,cart properties are already assigned - as it shoudnt be
        } else if (newCustomer.getCustomerId() != null || !newCustomer.getOrders().isEmpty()
                || newCustomer.getCart() != null) {
            throw new UnauthorizedFieldAssignmentException("{ \"error\" : \"customerID ,orders and cart shouldn'tbe provided , They are  assigned by the Server\"   }");
        }

        String newCustomerEmail = newCustomer.getEmail();

        //validates the email
        if (!isValidEmail(newCustomerEmail)) {
            throw new InvalidInputException("Customer", "email");
        }

        //checks whether a customer with that name or email already exists
        for (Customer customer : customers) {
            if (customer.getEmail().equals(newCustomerEmail)) {
                throw new ObjectAlreadyExistsException("Customer");
            }

        }

        UUID uuid = UUID.randomUUID();
        //assign a customerID
        newCustomer.setCustomerId(uuid.toString());
        //adds the new customer to the customers list
        customers.add(newCustomer);
    }
    // removes a Bookstore customer from the customer list based on the Id

    public void removeCustomer(String customerId) {
        boolean removed = customers.removeIf(customer -> customer.getCustomerId().equals(customerId));
        if (!removed) {
            throw new CustomerNotFoundException(customerId);
        }

    }

    //retrieves a customer based on the customerId provided
    public Customer getCustomerById(String customerId) {

        for (Customer customer : customers) {

            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }

        }
        throw new CustomerNotFoundException(customerId);
    }

    //updates the properties of an already existing customer identified by the provided customerId
    // however it is not possible to updated the customerId,cart and orders fields
    //CustomerDTO class is used for this
    public void updateCustomer(String customerId, CustomerDTO updatedCustomerDTO) {

        //checks whether a customer with the given cutomerId in the URI exists
        //if exists , retrives the customer object
        Customer existingCustomer = getCustomerById(customerId);

        // checks whether the updatedCustomerDTO object has its customerId assigned
        // If it is assigned the assigned customerId should be equal to the customerId provided within URI
        if ((updatedCustomerDTO.getCustomerId() == null) || (!updatedCustomerDTO.getCustomerId().equals(customerId))) {
            throw new InvalidInputException("updatedCustomer", "customerId");
        }
        //checks whether the updatedCustomerDTO has its email and password field assigned as they are mandatory fields for a Customer
        if ((updatedCustomerDTO.getEmail() == null) || (updatedCustomerDTO.getPassword() == null)) {
            throw new MissingRequiredObjectPropertyValueException("Email and Password should be provided for the updatedCustomer");

        }
        String updatedCustomerEmail = updatedCustomerDTO.getEmail();
        //checks the email validity of the updatedCustomerDTO
        if (!isValidEmail(updatedCustomerEmail)) {
            throw new InvalidInputException("Customer", "email");
        }
//

        // retreieves the cart and orders deatils from the existing customer details on the provided customerId
        Cart cart = existingCustomer.getCart();
        List<Order> orders = existingCustomer.getOrders();

        //Creates a new customer object based on the newly updated details and preserved details of cart,orders and customerId
        Customer updatedCustomer = new Customer(updatedCustomerDTO.getName(),
                updatedCustomerDTO.getEmail(), updatedCustomerDTO.getPassword());
        updatedCustomer.setCart(cart);
        updatedCustomer.setOrders(orders);
        updatedCustomer.setCustomerId(customerId);

        for (int i = 0; i < customers.size(); i++) {

            if (customers.get(i).getCustomerId().equals(customerId)) {
                customers.set(i, updatedCustomer);
                return;
            }

        }
    }

    // add an item to the cart of the customer and updates the totalPrice of the cart
    public void addCartItem(String customerId, CartItem cartItem) throws CustomerNotFoundException, BookNotFoundException {

        //checks whether  customerId provided are valid
        //if valdiation is successfull retieves them
        Customer customer = getCustomerById(customerId);

        //cheks whether the bookId and the quantity field are assigned on the cartItem , as they are mandatory fields on the cartItem
        //checks whether  the selected quantity for the item is valid
        if (cartItem.getBookId() == null || cartItem.getQuantity() == 0) {
            throw new MissingRequiredObjectPropertyValueException("BookId and quanityt should be provided for the cartItem");
        }
        //checks whther the bookId is valid
        Book book = bookDAO.getBookById(cartItem.getBookId());

        //quantity validation
        if (cartItem.getQuantity() < 0) {
            throw new InvalidInputException("cartItem", "quantity");
        } else if (book.getStockQuantity() < cartItem.getQuantity()) {
            throw new OutOfStockException(book.getBookId(), book.getStockQuantity());
        }

        Cart cart;

        // checks whether a cart already exists
        //if not creates one
        if (customer.getCart() == null) {
            cart = new Cart();
            customer.setCart(cart);
        } else {
            cart = customer.getCart();
        }

        for (CartItem existingCartItem : cart.getItems()) {

            if (existingCartItem.getBookId().equals(cartItem.getBookId())) {
                throw new ObjectAlreadyExistsException("CartItem");
            }
        }

        // the item/book is added to cart itemList with the book object and the required quantity by the customer
        cart.getItems().add(cartItem);
        // new Total is calculated and set
        cart.setCartTotal(cart.getCartTotal() + (book.getPrice() * cartItem.getQuantity()));
    }

    // retrieves every Item in the cart with the selected quantity
    public Cart getCart(String customerId) throws CustomerNotFoundException {
        //validates the customerId provided
        //if validated successfully , retrives it
        Customer customer = getCustomerById(customerId);
        Cart cart = customer.getCart();
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }

        return cart;
    }

    // updates the selected quantity for an Item in the cart , the item is identified by the bookId
    //however it is not possible to update the bookId on the cartItem
    public void updateCartItem(String customerId, String bookID, CartItem updatedCartItem) throws CustomerNotFoundException, BookNotFoundException {

        //validates the customerId proivded in the URI
        // if validated successfully , retrives the customer and the cart of the customer
        Customer customer = getCustomerById(customerId);
        Cart cart = customer.getCart();

        //for updated the cart , cart must be not empty
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        //cheks whether the updated cartItem has a bookId and a quantity provided ,as they are mandatory fields for a cartItem
        if (updatedCartItem.getBookId() == null || updatedCartItem.getQuantity() == 0) {
            throw new MissingRequiredObjectPropertyValueException("BookId and quanityt should be provided for the cartItem");
            //checks whetehr the updatedCartItem has the same bookID menntioned on the URI
        } else if (!bookID.equals(updatedCartItem.getBookId())) {
            throw new InvalidInputException("CartItem", "bookID");
        }

        Book book = bookDAO.getBookById(updatedCartItem.getBookId());

        //validates the quantity of the updatedCartItem
        if (updatedCartItem.getQuantity() < 0) {
            throw new InvalidInputException("cartItem", "quantity");

        } else if (book.getStockQuantity() < updatedCartItem.getQuantity()) {
            throw new OutOfStockException(updatedCartItem.getBookId(), book.getStockQuantity());
        }
        //get the current quantity of the existing cartItem
        int currentQuantity = 0;
        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getBookId().equals(bookID)) {
                currentQuantity = cartItem.getQuantity();
                break;
            }
        }

        // Subtract   the price contributed to the total price with previously assigned item quantity for the selected item ,
        // Add the new price based on the updatedQuantity, contributing to the total Price
        double newTotalPrice = cart.getCartTotal() - currentQuantity * book.getPrice()
                + book.getPrice() * updatedCartItem.getQuantity();

        //updates the Total price of the cart
        for (int i = 0; i < cart.getItems().size(); i++) {

            if (cart.getItems().get(i).getBookId().equals(bookID)) {
                cart.getItems().set(i, updatedCartItem);
                cart.setCartTotal(newTotalPrice);
                return;
            }
        }

    }

    //Removes a cart item based on the bookId provided
    public void removeCartItem(String customerId, String bookId) throws CustomerNotFoundException, BookNotFoundException {

        Customer customer = getCustomerById(customerId);
        Cart cart = customer.getCart();

        // checks whether the cart has items to be removed
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }

        //validates the bookId
        //if valdiated successfully retrieves the book object from it
        Book book = bookDAO.getBookById(bookId);

        //checks the book already exist within the customer's cart
        boolean bookAvailability = false;
        CartItem selectedCartItem = null;
        for (CartItem cartItem : cart.getItems()) {

            if (cartItem.getBookId().equals(bookId)) {
                bookAvailability = true;
                selectedCartItem = cartItem;

                break;
            }
        }
        if (!bookAvailability) {
            throw new BookNotFoundException(bookId);
        }

        //Recalculate the total price
        double newTotalPrice = cart.getCartTotal() - book.getPrice() * selectedCartItem.getQuantity();
        cart.getItems().removeIf(cartItem -> cartItem.getBookId().equals(bookId));
        //assigns the new total price
        cart.setCartTotal(newTotalPrice);

        //after removing the cartItems , if the cartItems list is empty
        //makes the cart null, indicating that the cart is empty
        if (cart.getItems().isEmpty()) {
            customer.setCart(null);
        }

    }

    // adds a new Order by checking out the customer's cart .Customer is identified by the provided customerId
    public Order addOrder(String customerId) throws CustomerNotFoundException {

        //valdiates the customerId
        //if suceesfully valdiated , retrives the customer object and the customer's cart
        Customer customer = getCustomerById(customerId);
        Cart cart = customer.getCart();

        //checks whether the cart has any items
        // to process an order , cart shouldnt be empty
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        //generates a random text as an orderId
        UUID orderId = UUID.randomUUID();

        //Date and time as of when you ckeckout of  the cart
        Date dateTime = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String formattedDate = formatter.format(dateTime);

        //makes the new order object based on the cart detailsorderId and dateTime
        Order order = new Order(orderId, cart.getCartTotal(), formattedDate, cart.getItems());
        //add the new order to customer's orders list
        customer.getOrders().add(order);

        List<CartItem> cartItemsAndQuantity = cart.getItems();

        //updates the stock Quantity of the purchased book items
        for (CartItem cartItem : cart.getItems()) {

            Book book = bookDAO.getBookById(cartItem.getBookId());
            int newStockQuantity = book.getStockQuantity() - cartItem.getQuantity();
            book.setStockQuantity(newStockQuantity);

        }
        // empties the cart
        customer.setCart(null);
        return order;

    }

    // retrieves a list of   orders of the customer mentioned by the customerId
    public List<Order> getAllOrders(String customerId) throws CustomerNotFoundException {

        //validates the customerId
        //retrives the customer , if successfully valdiated
        Customer customer = getCustomerById(customerId);
        return customer.getOrders();
    }

    // Retrieves and order based on the customerId and the orderId
    public Order getOrderByID(String customerId, String orderId) throws CustomerNotFoundException {
        // validates the customerId
        //retrives the customer , if successfully valdiated
        Customer customer = getCustomerById(customerId);

        for (Order order : customer.getOrders()) {

            if (order.getOrderId().toString().equals(orderId)) {
                return order;
            }
        }
        throw new OrderNotFoundException(orderId);
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

