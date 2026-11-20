package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.model.BorrowStatus;
import io.github.sachintha_madubashana.libraryos.model.BorrowingRecord;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.repository.BookRepository;
import io.github.sachintha_madubashana.libraryos.repository.BorrowingRepository;
import io.github.sachintha_madubashana.libraryos.repository.MemberRepository;

import java.util.List;
import java.util.Map;

public class DashboardService {
    private final BookRepository bookRepository;
    private final BorrowingRepository borrowingRepository;
    private final MemberRepository memberRepository;

    public DashboardService(BookRepository bookRepository, BorrowingRepository borrowingRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.borrowingRepository = borrowingRepository;
        this.memberRepository = memberRepository;
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

    public Result<Map<BorrowStatus, Long>> countBorrowings() {
        List<BorrowStatus> statuses = List.of(BorrowStatus.BORROWED, BorrowStatus.OVERDUE);
        Map<BorrowStatus, Long> counts = borrowingRepository.countByStatuses(statuses);

        if (counts == null || counts.isEmpty() || !counts.containsKey(BorrowStatus.BORROWED) || !counts.containsKey(BorrowStatus.OVERDUE) || counts.get(BorrowStatus.BORROWED) == null || counts.get(BorrowStatus.OVERDUE) == null) {
            return Result.error(
                    "Unable to retrieve Borrowing count",
                    "The Borrowing count could not be loaded."
            );
        }

        return Result.success(
                "Borrowing count retrieved successfully",
                "The Borrowing count has been loaded.",
                counts
        );
    }
}
