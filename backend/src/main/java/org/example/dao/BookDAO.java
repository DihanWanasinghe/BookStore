package org.example.dao;

import org.example.exceptions.AuthorNotFoundException;
import org.example.exceptions.BookNotFoundException;
import org.example.exceptions.InvalidInputException;
import org.example.exceptions.ObjectAlreadyExistsException;
import org.example.models.Author;
import org.example.models.Book;
import org.example.models.BookDTO;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.example.exceptions.MissingRequiredObjectPropertyValueException;
import org.example.exceptions.UnauthorizedFieldAssignmentException;

public class BookDAO {

    private static final AuthorDAO authorDAO = new AuthorDAO();

    private static List<Book> books = new ArrayList<>();

    public BookDAO() {
    }

    // returns a list of every book available
    public List<Book> getAllBooks() {

        return books;
    }
    // adds a book to the bookList

    public void addBook(BookDTO bookDTO) throws AuthorNotFoundException {

        // checks if the authorId,ISBNm,title and Price fields are assigned
        if (bookDTO.getAuthorId() == null || bookDTO.getIsbn() == null
                || bookDTO.getTitle() == null || bookDTO.getPrice() == 0) {
            throw new MissingRequiredObjectPropertyValueException(
                    "authorId,isbn,title and  price should be provided for the Book");
            // checks whether the bookId field is already assigned with a value , whcih
            // shouldnt be
        } else if (bookDTO.getBookId() != null) {
            throw new UnauthorizedFieldAssignmentException(
                    "{ \"error\" : \"bookID shouldn'tbe provided , It is randomely assigned by the Server\"   }");
        } // validate the price
          // validate the price,quantity and the publication year of the book
        else if (bookDTO.getPrice() < 0 || bookDTO.getStockQuantity() < 0
                || bookDTO.getPublicationYear() > Year.now().getValue()) {
            throw new InvalidInputException("Book");
        }

        // checks whether the book is already present in the list
        for (Book book : books) {

            if (book.getIsbn().equals(bookDTO.getIsbn())) {
                throw new ObjectAlreadyExistsException("Book");
            }

        }
        // asigns the bookId
        UUID uuid = UUID.randomUUID();
        bookDTO.setBookId(uuid.toString());
        // check whether the author with the given Id exists , if exits get the author
        // from it
        Author author = authorDAO.getAuthorByID(bookDTO.getAuthorId());
        if (bookDTO.getStockQuantity() == 0) {
            bookDTO.setStockQuantity(1);
        }

        Book newbook = new Book(bookDTO.getBookId(), bookDTO.getTitle(), author, bookDTO.getIsbn(),
                bookDTO.getPublicationYear(), bookDTO.getPrice(), bookDTO.getStockQuantity());
        books.add(newbook);

    }

    // removes a book from the available book list
    public void removeBook(String bookId) {
        boolean removed = books.removeIf(book -> book.getBookId().equals(bookId));
        if (!removed) {
            throw new BookNotFoundException(bookId);
        }

    }

    // retrieves the book object mentioned by the book object
    public Book getBookById(String bookId) {

        for (Book book : books) {

            if (book.getBookId().equals(bookId)) {
                return book;
            }

        }
        throw new BookNotFoundException(bookId);
    }

    // updates the book object properties of the book mentioned by the bookId
    public Book updateBook(String bookId, BookDTO updatedBookDTO)
            throws BookNotFoundException, AuthorNotFoundException {

        // checks whether a book exist with the provided book Id in the URI
        Book book = getBookById(bookId);

        // checks whther the updatedBook has a bookId provided , and its value is equal
        // to the bookId in the URI
        if ((updatedBookDTO.getBookId() == null) || (!updatedBookDTO.getBookId().equals(bookId))) {
            throw new InvalidInputException("updatedBook", "bookId");
        }

        // checks if the authorId,ISBNm,title and Price fields are assigned
        if (updatedBookDTO.getAuthorId() == null || updatedBookDTO.getIsbn() == null
                || updatedBookDTO.getTitle() == null || updatedBookDTO.getPrice() == 0) {
            throw new MissingRequiredObjectPropertyValueException(
                    "authorId,isbn,title and  price should be provided for the Book");
        }

        // check whether the author with the given Id exists , if exits get the author
        // from it
        Author author = authorDAO.getAuthorByID(updatedBookDTO.getAuthorId());

        // validate the price ,stock quantity and publication year
        if (updatedBookDTO.getPrice() < 0 || updatedBookDTO.getStockQuantity() < 0
                || updatedBookDTO.getPublicationYear() > Year.now().getValue()) {
            throw new InvalidInputException("updatedBook");
        }

        Book updatedBook = new Book(updatedBookDTO.getBookId(), updatedBookDTO.getTitle(),
                author, updatedBookDTO.getIsbn(), updatedBookDTO.getPublicationYear(), updatedBookDTO.getPrice(),
                updatedBookDTO.getStockQuantity());

        for (int i = 0; i < books.size(); i++) {

            if (books.get(i).getBookId().equals(bookId)) {
                books.set(i, updatedBook);
                return updatedBook;
            }
        }
        return null;
    }
}
