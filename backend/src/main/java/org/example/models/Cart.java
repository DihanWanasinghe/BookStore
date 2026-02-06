package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    // Book object as the key and the quantity needed to purchase  as the value
    private List<CartItem> items = new ArrayList<>() ;
    //total value for the items on the cart
    private double cartTotal=0;

    public Cart(List<CartItem> items, double cartTotal) {
        this.items = items;
        this.cartTotal = cartTotal;
    }
    public  Cart(){

    }



    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(double cartTotal) {
        this.cartTotal = cartTotal;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                ", cartTotal=" + cartTotal +
                '}';
    }
}
