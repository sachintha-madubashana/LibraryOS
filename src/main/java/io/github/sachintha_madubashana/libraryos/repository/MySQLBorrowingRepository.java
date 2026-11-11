package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.database.DatabaseConnection;
import io.github.sachintha_madubashana.libraryos.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLBorrowingRepository implements BorrowingRepository{
    private final Connection connection;

    public MySQLBorrowingRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public Optional<BorrowingRecord> findById(String code) {
        if (code == null || code.isEmpty()) {
            return Optional.empty();
        }

        String query = "SELECT `borrowing_code`,`issue_date`,`due_date`, `retuned_date`, `book_code`, `title`, `author`, `published_year`, `total_copies`, `available_copies`, `category_id`, `category`, `member_code`, `name`, `email`,`phone`, `address`,`joined_date`, `status`  FROM borrowing_record INNER JOIN `book` ON `book_id` = `book`.`id` INNER JOIN `member` ON `member_id` = `member`.`id` INNER JOIN `borrowing_status` ON `borrowing_status_id` = `borrowing_status`.`id` INNER JOIN `category` ON book.category_id = category.id WHERE `borrowing_code` = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code);
            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(createBorrowingRecordObj(resultSet));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to find borrowing record.", e);

        }

        return Optional.empty();
    }

    @Override
    public boolean existsById(String code) {
        return findById(code).isPresent();
    }

    @Override
    public List<BorrowingRecord> findAll() {
        String query = "SELECT `borrowing_code`,`issue_date`,`due_date`, `retuned_date`, `book_code`, `title`, `author`, `published_year`, `total_copies`, `available_copies`, `category_id`, `category`, `member_code`, `name`, `email`,`phone`, `address`,`joined_date`, `status`  FROM borrowing_record INNER JOIN `book` ON `book_id` = `book`.`id` INNER JOIN `member` ON `member_id` = `member`.`id` INNER JOIN `borrowing_status` ON `borrowing_status_id` = `borrowing_status`.`id` INNER JOIN `category` ON book.category_id = category.id";

        return getBorrowingRecords(query);
    }


    @Override
    public boolean save(BorrowingRecord borrowingRecord) {
        if (borrowingRecord == null || existsById(borrowingRecord.getRecordId())) {
            return false;
        }

        String query = "INSERT INTO borrowing_record (borrowing_code, member_id, book_id, issue_date, due_date, retuned_date, borrowing_status_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, borrowingRecord.getRecordId());
            statement.setString(2, borrowingRecord.getMember().getId());
            statement.setInt(3, borrowingRecord.getBook().getId());
            statement.setDate(4, java.sql.Date.valueOf(borrowingRecord.getIssueDate()));
            statement.setDate(5, java.sql.Date.valueOf(borrowingRecord.getDueDate()));
            statement.setDate(6, borrowingRecord.getReturnDate() != null ? Date.valueOf(borrowingRecord.getReturnDate()) : null);
            statement.setInt(7, borrowingRecord.getStatus().ordinal());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save borrowing record.", e);
        }
    }

    @Override
    public boolean update(BorrowingRecord borrowingRecord) {
        if (borrowingRecord == null || borrowingRecord.getRecordId() == null) {
            return false;
        }

        String query = "UPDATE borrowing_record SET `member_id` = ?, `book_id` = ?, `issue_date` = ?, `due_date` = ?, `retuned_date` = ?, `borrowing_status_id` = ? WHERE `borrowing_code` = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, borrowingRecord.getMember().getId());
            statement.setInt(2, borrowingRecord.getBook().getId());
            statement.setDate(3, java.sql.Date.valueOf(borrowingRecord.getIssueDate()));
            statement.setDate(4, java.sql.Date.valueOf(borrowingRecord.getDueDate()));
            statement.setDate(5, borrowingRecord.getReturnDate() != null ? Date.valueOf(borrowingRecord.getReturnDate()) : null);
            statement.setInt(6, borrowingRecord.getStatus().getValue());
            statement.setString(7, borrowingRecord.getRecordId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update borrowing record.", e);
        }
    }

    @Override
    public boolean deleteById(String code) {
        if(code == null || findById(code).isEmpty()) {
            return false;
        }

        String query = "DELETE FROM borrowing_record WHERE borrowing_code = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete borrowing record.", e);
        }
    }

    @Override
    public List<BorrowingRecord> findActiveLoans()  {
        String query = "SELECT `borrowing_code`,`issue_date`,`due_date`, `retuned_date`, `book_code`, `title`, `author`, `published_year`, `total_copies`, `available_copies`, `category_id`, `category`, `member_code`, `name`, `email`,`phone`, `address`,`joined_date`, `status`  FROM borrowing_record INNER JOIN `book` ON `book_id` = `book`.`id` INNER JOIN `member` ON `member_id` = `member`.`id` INNER JOIN `borrowing_status` ON `borrowing_status_id` = `borrowing_status`.`id` INNER JOIN `category` ON book.category_id = category.id WHERE `borrowing_status`.`id` != " + BorrowStatus.RETURNED.getValue();

        return getBorrowingRecords(query);
    }

    @Override
    public List<BorrowingRecord> findAllOverdue() {
        String query = "SELECT `borrowing_code`,`issue_date`,`due_date`, `retuned_date`, `book_code`, `title`, `author`, `published_year`, `total_copies`, `available_copies`, `category_id`, `category`, `member_code`, `name`, `email`,`phone`, `address`,`joined_date`, `status`  FROM borrowing_record INNER JOIN `book` ON `book_id` = `book`.`id` INNER JOIN `member` ON `member_id` = `member`.`id` INNER JOIN `borrowing_status` ON `borrowing_status_id` = `borrowing_status`.`id` INNER JOIN `category` ON book.category_id = category.id WHERE `borrowing_status`.`id` = " + BorrowStatus.OVERDUE.getValue();

        return getBorrowingRecords(query);
    }

    private BorrowingRecord createBorrowingRecordObj(ResultSet resultSet) throws SQLException {
        Category category = new Category(
                resultSet.getInt("category_id"),
                resultSet.getString("category")
        );

        Book book = new Book(
                resultSet.getString("book_code"),
                resultSet.getString("title"),
                resultSet.getString("author"),
                resultSet.getInt("published_year"),
                category,
                resultSet.getInt("total_copies"),
                resultSet.getInt("available_copies")
        );

        Member member = new Member(
                resultSet.getString("member_code"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getString("address"),
                resultSet.getDate("joined_date").toLocalDate()
        );

        return new BorrowingRecord(
                resultSet.getString("borrowing_code"),
                member,
                book,
                resultSet.getDate("issue_date").toLocalDate(),
                resultSet.getDate("due_date").toLocalDate(),
                resultSet.getDate("retuned_date") != null ? resultSet.getDate("retuned_date").toLocalDate() : null,
                BorrowStatus.fromString(resultSet.getString("status"))
        );
    }

    private List<BorrowingRecord> getBorrowingRecords(String query) {
        List<BorrowingRecord> borrowingRecords = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                borrowingRecords.add(createBorrowingRecordObj(resultSet));
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to find borrowing record.", e);
        }

        return borrowingRecords;
    }

}
