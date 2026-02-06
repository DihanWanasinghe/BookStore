package org.example.dao;

import org.example.exceptions.AuthorNotFoundException;
import org.example.exceptions.InvalidInputException;
import org.example.exceptions.ObjectAlreadyExistsException;
import org.example.models.Author;
import org.example.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.exceptions.MissingRequiredObjectPropertyValueException;
import org.example.exceptions.UnauthorizedFieldAssignmentException;

public class AuthorDAO {

    private static final BookDAO bookDAO = new BookDAO();
    private static List<Author> authors = new ArrayList<>();
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public AuthorDAO() {
    }

    //return a list  of authors , be it authors of books available now or previously available
    public List<Author> getAllAuthors() {

        return authors;
    }

    // adds an author to the authors list
    public void addAuthor(Author newAuthor) {

        //checks if the email and name fields are not assigned  on the author as they are mandatory dields for an author
        if (newAuthor.getEmail() == null || newAuthor.getName() == null) {
            throw new MissingRequiredObjectPropertyValueException("Email and Name should be provided for the Author");
            //checks if the authorId field is assgined a value on the newAuthor, which shouldnt be assigned
        } else if (newAuthor.getAuthorId() != null) {

            throw new UnauthorizedFieldAssignmentException("{ \"error\" : \"authorID shouldn'tbe provided , It is randomely assigned by the Server\"   }");
        }

        String newAuthorName = newAuthor.getName().toLowerCase();
        String newAuthorEmail = newAuthor.getEmail();

        //validate the email
        if (!isValidEmail(newAuthorEmail)) {
            throw new InvalidInputException("Author", "email");
        }
        //check whether an author already exists with that name or the email
        for (Author author : authors) {
            if (author.getEmail().equals(newAuthorEmail) || author.getName().toLowerCase().equals(newAuthorName)) {
                throw new ObjectAlreadyExistsException("Author");
            }
        }
        UUID uuid = UUID.randomUUID();
        //set an authorID for the newAuthor
        newAuthor.setAuthorId(uuid.toString());
        //new Author is added to the authors list
        authors.add(newAuthor);
    }

    // removes an author from the authors list based on the authorId
    public void removeAuthor(String authorId) {
        boolean removed = authors.removeIf(author -> author.getAuthorId().equals(authorId));
        if (!removed) {
            throw new AuthorNotFoundException(authorId);
        }
    }

    //retrieves an author by the authorId
    public Author getAuthorByID(String authorId) {
        for (Author author : authors) {

            if (author.getAuthorId().equals(authorId)) {
                return author;
            }
        }
        throw new AuthorNotFoundException(authorId);
    }

    //updates the properties of an already registered author based on the mentioned authorId
    //however it is not possible to update the authorId
    public void updateAuthor(String authorId, Author updatedAuthor) throws AuthorNotFoundException {

        //checks whether an author with the given authorId in the URI exists
        //if exists,  retrives the author object
        Author author = getAuthorByID(authorId);

        //making sure that the updatedAuthor has an authorId assigned and  author details would be updated on the same AuthorId
        if ((updatedAuthor.getAuthorId() == null) || (!updatedAuthor.getAuthorId().equals(authorId))) {
            throw new InvalidInputException("updatedAuthor", "authorId");

        }

        //checks if the email and name fields are  assigned  on the updatedAuthor as they are mandatory fields required for an author
        if (updatedAuthor.getEmail() == null || updatedAuthor.getName() == null) {
            throw new MissingRequiredObjectPropertyValueException("Email and Name should be provided for the updatedAuthor");
        }

        String updatedAuthorEmail = updatedAuthor.getEmail();

        //validate the email
        if (!isValidEmail(updatedAuthorEmail)) {
            throw new InvalidInputException("updatedAuthor", "email");
        }

        for (int i = 0; i < authors.size(); i++) {

            if (authors.get(i).getAuthorId().equals(authorId)) {
                updatedAuthor.setAuthorId(authorId);
                authors.set(i, updatedAuthor);
                return;
            }
        }
    }

    // retrieves a list of every book written by the author mentioned by the authorId
    public List<Book> getAllBooksByAuthor(String authorId) throws AuthorNotFoundException {
        // if the author isnt available throws AuthorNotFoundException
        Author author = getAuthorByID(authorId);

        List<Book> authorBooks = bookDAO.getAllBooks()
                .stream()
                .filter(book -> book.getAuthor().getAuthorId().equals(authorId))
                .toList();

        return authorBooks;

    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
