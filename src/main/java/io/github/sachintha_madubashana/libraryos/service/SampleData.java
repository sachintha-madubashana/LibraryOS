package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SampleData {
    private SampleData() {
    }

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

    public static  List<BorrowingRecord> createBorrowings(){
        List<BorrowingRecord> borrowingRecords = new ArrayList<>();
        borrowingRecords.add(new BorrowingRecord("BR001", "John Smith", "To Kill a Mockingbird", LocalDate.of(2026, 8, 1), LocalDate.of(2026, 8, 15), LocalDate.of(2026, 8, 13), "Returned"));
        borrowingRecords.add(new BorrowingRecord("BR002", "Emily Johnson", "1984", LocalDate.of(2026, 8, 5), LocalDate.of(2026, 8, 19), LocalDate.of(2026, 8, 18), "Returned"));
        borrowingRecords.add(new BorrowingRecord("BR003", "Michael Brown", "The Great Gatsby", LocalDate.of(2026, 8, 12), LocalDate.of(2026, 8, 26), null, "Borrowed"));
        borrowingRecords.add(new BorrowingRecord("BR004", "Sarah Wilson", "Pride and Prejudice", LocalDate.of(2026, 8, 18), LocalDate.of(2026, 9, 1), LocalDate.of(2026, 8, 30), "Returned"));
        borrowingRecords.add(new BorrowingRecord("BR005", "David Miller", "The Hobbit", LocalDate.of(2026, 8, 22), LocalDate.of(2026, 9, 5), null, "Overdue"));
        borrowingRecords.add(new BorrowingRecord("BR006", "Jessica Davis", "The Lord of the Rings", LocalDate.of(2026, 8, 25), LocalDate.of(2026, 9, 8), LocalDate.of(2026, 9, 6), "Returned"));
        borrowingRecords.add(new BorrowingRecord("BR007", "Daniel Anderson", "Harry Potter and the Philosopher's Stone", LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 15), null, "Borrowed"));
        borrowingRecords.add(new BorrowingRecord("BR008", "Sophia Taylor", "The Shining", LocalDate.of(2026, 9, 4), LocalDate.of(2026, 9, 18), LocalDate.of(2026, 9, 17), "Returned"));
        borrowingRecords.add(new BorrowingRecord("BR009", "James Thomas", "The Da Vinci Code", LocalDate.of(2026, 9, 10), LocalDate.of(2026, 9, 24), null, "Borrowed"));
        borrowingRecords.add(new BorrowingRecord("BR010", "Olivia Moore", "The Chronicles of Narnia", LocalDate.of(2026, 9, 12), LocalDate.of(2026, 9, 26), null, "Overdue"));
        borrowingRecords.add(new BorrowingRecord("BR011", "William Martin", "The Lord of the Flies", LocalDate.of(2026, 9, 18), LocalDate.of(2026, 10, 2), LocalDate.of(2026, 9, 29), "Returned"));
        borrowingRecords.add(new BorrowingRecord("BR012", "Emma Jackson", "The Catcher in the Rye", LocalDate.of(2026, 9, 20), LocalDate.of(2026, 10, 4), null, "Borrowed"));
        return borrowingRecords;
    }
}
