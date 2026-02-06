package org.example.models;

import java.util.*;

public class Order {
    //randomly generated ID for the order
    private UUID orderId;
    //total value paid on the order
    private double total;
    //the date and time when the order was made
    private String dateTime;
    //key is the Book and the value is the purchased quantity of that book
    private List<CartItem> items = new ArrayList<>();

    public Order(UUID orderId, double total, String dateTime, List<CartItem> items) {
        this.orderId = orderId;
        this.total = total;
        this.dateTime = dateTime;
        this.items = items;
    }
    public Order(){}

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", total=" + total +
                ", date=" + dateTime +
                ", items=" + items +
                '}';
    }
    
//     @Override
//    public boolean equals(Object comparedObject) {
//        if (this == comparedObject) return true;
//        if (!(comparedObject instanceof Order)) return false;
//        Order otherComparedOrder = (Order) comparedObject;
//        return orderId.equals(otherComparedOrder.getOrderId() )&&
//               Objects.equals(dateTime, otherComparedOrder.getDateTime())
//                && total == otherComparedOrder.getTotal() && items.equals(otherComparedOrder.getItems())
//                ;
//    }
    
}
