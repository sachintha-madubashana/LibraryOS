package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.model.Book;

import java.util.ArrayList;
import java.util.List;

public class SampleData {

    public static List<Book> createBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("BK001", "To Kill a Mockingbird", "Harper Lee", "Fiction", 10, 5));
        books.add(new Book("BK002", "1984", "George Orwell", "Fiction", 5, 3));
        books.add(new Book("BK003", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 8, 2));
        books.add(new Book("BK004", "Pride and Prejudice", "Jane Austen", "Fiction", 7, 4));
        books.add(new Book("BK005", "The Catcher in the Rye", "J.D. Salinger", "Fiction", 6, 1));
        books.add(new Book("BK006", "The Hobbit", "J.R.R. Tolkien", "Fantasy", 12, 7));
        books.add(new Book("BK007", "The Lord of the Rings", "J.R.R. Tolkien", "Fantasy", 15, 9));
        books.add(new Book("BK008", "Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy", 20, 10));
        books.add(new Book("BK009", "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "Science Fiction", 18, 12));
        books.add(new Book("BK010", "The Shining", "Stephen King", "Horror", 10, 5));
        books.add(new Book("BK011", "The Da Vinci Code", "Dan Brown", "Thriller", 14, 7));
        books.add(new Book("BK012", "The Chronicles of Narnia", "C.S. Lewis", "Fantasy", 16, 8));
        books.add(new Book("BK013", "The Lord of the Flies", "William Golding", "Fiction", 9, 3));

        return books;
    }
}
