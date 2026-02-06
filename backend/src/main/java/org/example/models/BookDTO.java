package org.example.models;

public class BookDTO {

    // Unique identifier for the book
    private String bookId;

    // Title of the book
    private String title;

    // Author of the book
    private String authorId;

    // International Standard Book Number (ISBN) of the book
    private String isbn;

    // Year the book was published
    private int publicationYear;

    // Price of the book
    private double price;

    // Quantity of the book available in stock
    private int stockQuantity;

    public BookDTO(String title, String authorId, String isbn, int publicationYear, double price, int stockQuantity) {
        this.title = title;
        this.authorId = authorId;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public BookDTO() {
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                ", bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", authorId='" + authorId + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
