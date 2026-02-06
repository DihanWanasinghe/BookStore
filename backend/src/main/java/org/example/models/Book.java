package org.example.models;

/**
 * Represents a Book entity with various attributes such as title, author, isbn,
 * publication year, price, and stock quantity.
 */
public class Book {

    // Unique identifier for the book
    private String bookId;

    // Title of the book
    private String title;

    // Author of the book
    private Author author;

    // International Standard Book Number (isbn) of the book
    private String isbn;

    // Year the book was published
    private int publicationYear;

    // Price of the book
    private double price;

    // Quantity of the book available in stock
    private int stockQuantity;

    /**
     * Constructs a new Book instance with the specified attributes.
     *
     * @param title Title of the book
     * @param author Author of the book
     * @param ISBN isbn of the book
     * @param publicationYear Year the book was published
     * @param price Price of the book
     * @param stockQuantity Quantity of the book available in stock
     */
    public Book(String bookId, String title, Author author, String ISBN, int publicationYear, double price, int stockQuantity) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = ISBN;
        this.publicationYear = publicationYear;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Book() {
    }

    /**
     * Gets the unique identifier of the book.
     *
     * @return The book ID
     */
    public String getBookId() {
        return bookId;
    }

    /**
     * Sets the unique identifier of the book.
     *
     * @param bookId The book ID to set
     */
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    /**
     * Gets the title of the book.
     *
     * @return The title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author of the book.
     *
     * @return The author of the book
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author The author to set
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Gets the isbn of the book.
     *
     * @return The isbn of the book
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the isbn of the book.
     *
     * @param isbn The isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the publication year of the book.
     *
     * @return The publication year of the book
     */
    public int getPublicationYear() {
        return publicationYear;
    }

    /**
     * Sets the publication year of the book.
     *
     * @param publicationYear The publication year to set
     */
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    /**
     * Gets the price of the book.
     *
     * @return The price of the book
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the book.
     *
     * @param price The price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the stock quantity of the book.
     *
     * @return The stock quantity of the book
     */
    public int getStockQuantity() {
        return stockQuantity;
    }

    /**
     * Sets the stock quantity of the book.
     *
     * @param stockQuantity The stock quantity to set
     */
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * Returns a string representation of the Book object.
     *
     * @return A string containing the book's details
     */
    @Override
    public String toString() {
        return "Book{"
                + ", bookId='" + bookId + '\''
                + ", title='" + title + '\''
                + ", author=" + author
                + ", ISBN='" + isbn + '\''
                + ", publicationYear=" + publicationYear
                + ", price=" + price
                + ", stockQuantity=" + stockQuantity
                + '}';
    }
}
