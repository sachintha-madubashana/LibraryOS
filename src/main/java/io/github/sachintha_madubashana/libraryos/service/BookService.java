package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.repository.BookRepository;

import java.util.List;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Result<Book> getBookById(String code ) {
        if (code == null || code.isBlank()) {
            return Result.error(
                    "Invalid book code",
                    "Please provide a valid book code."
            );
        }

        return bookRepository.findById(code)
                .map(book -> Result.success(
                        "Book found",
                        "The requested book was found.",
                        book
                ))
                .orElseGet(() -> Result.error(
                        "Book not found ",
                        "No book exists with Code: " + code
                ));
    }

    public Result<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        if (books == null) {
            return Result.error(
                    "Unable to retrieve Books",
                    "The Book list could not be loaded."
            );
        }

        return Result.success(
                "Books retrieved successfully",
                "The book list has been loaded.",
                books
        );
    }

    public Result<Book> addBook(Book book) {
        if (book == null) {
            return Result.error(
                    "Invalid book",
                    "Book information is required."
            );
        }

        if (bookRepository.existsById(book.getCode())) {
            return Result.error(
                    "Duplicate book Code",
                    "A book with this Code already exists."
            );
        }

        boolean isSaved = bookRepository.save(book);

        if (!isSaved) {
            return Result.error(
                    "Unable to add book",
                    "The book could not be saved."
            );
        }

        return Result.success(
                "Book added successfully",
                "The new book has been added to the library.",
                book
        );
    }

    public Result<Book> updateBook(Book book) {
        if (book == null) {
            return Result.error(
                    "Invalid book",
                    "Book information is required."
            );
        }

        if (!bookRepository.existsById(book.getCode())) {
            return Result.error(
                    "Book not found",
                    "The book with the provided ID does not exist."
            );
        }

        boolean isUpdated = bookRepository.update(book);

        if (!isUpdated) {
            return Result.error(
                    "Unable to update book",
                    "The book could not be isUpdated."
            );
        }

        return Result.success(
                "Book isUpdated successfully",
                "The book has been isUpdated.",
                book
        );

    }

    public Result<Void> deleteBook(String code) {
        if (code == null) {
            return Result.error(
                    "Invalid book",
                    "Book Code is required."
            );
        }

        if (!bookRepository.existsById(code)) {
            return Result.error(
                    "Book not found",
                    "The book with the provided ID does not exist."
            );
        }

        boolean isDeleted = bookRepository.deleteById(code);

        if (!isDeleted) {
            return Result.error(
                    "Unable to delete book",
                    "The book could not be deleted."
            );
        }

        return Result.success(
                "Book deleted successfully",
                "The book has been deleted."
        );
    }

    public Result<String> generateBookId() {
        List<Book> books = bookRepository.findAll();
        String newBookId = getNewBookId(0);

        if (!books.isEmpty()) {
            Book lastBook = books.get(books.size() - 1);
            int bookCount = Integer.parseInt(lastBook.getCode().substring(2));
            newBookId = getNewBookId(bookCount);
        }

        return Result.success(
                "Book id generated successfully",
                "The book id has been generated.",
                newBookId
        );
    }

    private String getNewBookId(int number) {
        return "bk" + String.format("%05d", (number + 1));
    }
}
