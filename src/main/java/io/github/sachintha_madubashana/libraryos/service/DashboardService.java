package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.repository.BookRepository;
import io.github.sachintha_madubashana.libraryos.repository.BorrowingRepository;
import io.github.sachintha_madubashana.libraryos.repository.MemberRepository;

import java.util.List;

public class DashboardService {
    private final BookRepository bookRepository;
    private final BorrowingRepository borrowingRepository;
    private final MemberRepository memberRepository;

    public DashboardService(BookRepository bookRepository, BorrowingRepository borrowingRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.borrowingRepository = borrowingRepository;
        this.memberRepository = memberRepository;
    }

    public Result<List<BorrowingRecord>> getBorrowingRecords() {
        List<BorrowingRecord> borrowingRecords = borrowingRepository.findAll();

        if (borrowingRecords == null) {
            return Result.error(
                    "Unable to retrieve Borrowing Records",
                    "The Borrowing Record list could not be loaded."
            );
        }

        return Result.success(
                "Borrowing Records retrieved successfully",
                "The Borrowing Record list has been loaded.",
                borrowingRecords
        );
    }

    public Result<List<BorrowingRecord>> findAllActivities() {
        List<BorrowingRecord> records = borrowingRepository.findAll();

        if (records == null) {
            return Result.error(
                    "Unable to retrieve Borrowing Records",
                    "The Borrowing Record list could not be loaded."
            );
        }

        return Result.success(
                "Borrowing Records retrieved successfully",
                "The Borrowing Record list has been loaded.",
                records
        );
    }

    public Result<Integer> countBooks() {
        List<Book> books = bookRepository.findAll();

        if (books == null) {
            return Result.error(
                    "Unable to retrieve Book count",
                    "The Book count could not be loaded."
            );
        }

        return Result.success(
                "Book count retrieved successfully",
                "The Book count has been loaded.",
                books.size()
        );
    }

    public Result<Integer> countMembers() {
        List<Member> members = memberRepository.findAll();

        if (members == null) {
            return Result.error(
                    "Unable to retrieve Member count",
                    "The Member count could not be loaded."
            );
        }

        return Result.success(
                "Member count retrieved successfully",
                "The Member count has been loaded.",
                members.size()
        );
    }

    public Result<Integer> countBorrowings() {
        List<BorrowingRecord> borrowings = borrowingRepository.findActiveLoans();

        if (borrowings == null) {
            return Result.error(
                    "Unable to retrieve Borrowing count",
                    "The Borrowing count could not be loaded."
            );
        }

        return Result.success(
                "Borrowing count retrieved successfully",
                "The Borrowing count has been loaded.",
                borrowings.size()
        );
    }

    public Result<Integer> countOverdueBorrowings() {
        List<BorrowingRecord> borrowings = borrowingRepository.findAllOverdue();

        if (borrowings == null) {
            return Result.error(
                    "Unable to retrieve Overdue Borrowing count",
                    "The Overdue Borrowing count could not be loaded."
            );
        }

        return Result.success(
                "Overdue Borrowing count retrieved successfully",
                "The Overdue Borrowing count has been loaded.",
                borrowings.size()
        );
    }
}
