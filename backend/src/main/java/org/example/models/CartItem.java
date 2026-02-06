package org.example.models;

import java.util.Objects;

public class CartItem {

    private String bookId;
    private int quantity;

    public CartItem(String bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
    
    public CartItem(){}

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "book=" + bookId +
                ", quantity=" + quantity +
                '}';
    }
//     @Override
//    public boolean equals(Object comparedObject) {
//        if (this == comparedObject) return true;
//        if (!(comparedObject instanceof CartItem)) return false;
//        CartItem otherComparedCartItem = (CartItem) comparedObject;
//        return quantity == otherComparedCartItem.quantity &&
//               Objects.equals(bookId, otherComparedCartItem.bookId);
//    }
//    
}
