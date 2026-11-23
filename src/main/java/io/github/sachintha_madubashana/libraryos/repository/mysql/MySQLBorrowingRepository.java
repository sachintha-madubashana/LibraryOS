package io.github.sachintha_madubashana.libraryos.repository.mysql;

import io.github.sachintha_madubashana.libraryos.database.DatabaseConnection;
import io.github.sachintha_madubashana.libraryos.model.*;
import io.github.sachintha_madubashana.libraryos.repository.BorrowingRepository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

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

        String query = "SELECT book.`id` AS book_id, `borrowing_code`,`issue_date`,`due_date`, `retuned_date`, `book_code`, `title`, `author`, `published_year`, `total_copies`, `available_copies`, `category_id`, `category`, member.`id` AS member_id, `member_code`, `name`, `email`,`phone`, `address`,`joined_date`, `status`  FROM borrowing_record INNER JOIN `book` ON `book_id` = `book`.`id` INNER JOIN `member` ON `member_id` = `member`.`id` INNER JOIN `borrowing_status` ON `borrowing_status_id` = `borrowing_status`.`id` INNER JOIN `category` ON book.category_id = category.id WHERE `borrowing_code` = ?";

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
        String query = "SELECT book.`id` AS book_id, `borrowing_code`,`issue_date`,`due_date`, `retuned_date`, `book_code`, `title`, `author`, `published_year`, `total_copies`, `available_copies`, `category_id`, `category`, member.`id` AS member_id, `member_code`, `name`, `email`,`phone`, `address`,`joined_date`, `status`  FROM borrowing_record INNER JOIN `book` ON `book_id` = `book`.`id` INNER JOIN `member` ON `member_id` = `member`.`id` INNER JOIN `borrowing_status` ON `borrowing_status_id` = `borrowing_status`.`id` INNER JOIN `category` ON book.category_id = category.id ORDER BY borrowing_code DESC";

        return getBorrowingRecords(query);
    }

    @Override
    public BorrowingRecord findLast() {
        String query = "SELECT book.`id` AS book_id, `borrowing_code`,`issue_date`,`due_date`, `retuned_date`, `book_code`, `title`, `author`, `published_year`, `total_copies`, `available_copies`, `category_id`, `category`, member.`id` AS member_id, `member_code`, `name`, `email`,`phone`, `address`,`joined_date`, `status`  FROM borrowing_record INNER JOIN `book` ON `book_id` = `book`.`id` INNER JOIN `member` ON `member_id` = `member`.`id` INNER JOIN `borrowing_status` ON `borrowing_status_id` = `borrowing_status`.`id` INNER JOIN `category` ON book.category_id = category.id ORDER BY borrowing_code DESC LIMIT 1";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createBorrowingRecordObj(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find last borrowing record.", e);
        }

        return null;
    }

    @Override
    public boolean save(BorrowingRecord borrowingRecord) {
        if (borrowingRecord == null || existsById(borrowingRecord.getRecordId())) {
            return false;
        }

        String query = "INSERT INTO borrowing_record (borrowing_code, member_id, book_id, issue_date, due_date, retuned_date, borrowing_status_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, borrowingRecord.getRecordId());
            statement.setInt(2, borrowingRecord.getMember().getId());
            statement.setInt(3, borrowingRecord.getBook().getId());
            statement.setDate(4, java.sql.Date.valueOf(borrowingRecord.getIssueDate()));
            statement.setDate(5, java.sql.Date.valueOf(borrowingRecord.getDueDate()));
            statement.setDate(6, borrowingRecord.getReturnDate() != null ? Date.valueOf(borrowingRecord.getReturnDate()) : null);
            statement.setInt(7, borrowingRecord.getStatus().getValue());

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
            statement.setInt(1, borrowingRecord.getMember().getId());
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

    private BorrowingRecord createBorrowingRecordObj(ResultSet resultSet) throws SQLException {
        Category category = new Category(
                resultSet.getInt("category_id"),
                resultSet.getString("category")
        );

        Book book = new Book(
                resultSet.getInt("book_id"),
                resultSet.getString("book_code"),
                resultSet.getString("title"),
                resultSet.getString("author"),
                resultSet.getInt("published_year"),
                category,
                resultSet.getInt("total_copies"),
                resultSet.getInt("available_copies")
        );

        Member member = new Member(
                resultSet.getInt("member_id"),
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

    private List<BorrowingRecord> getBorrowingRecords(String query, String... params) {
        List<BorrowingRecord> borrowingRecords = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                borrowingRecords.add(createBorrowingRecordObj(resultSet));
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to find borrowing record.", e);
        }

        return borrowingRecords;
    }

    @Override
    public Map<BorrowStatus, Long> countByStatuses(List<BorrowStatus> statuses) {
        if(statuses == null || statuses.isEmpty()) {
            throw new IllegalArgumentException("Statuses list cannot be null or empty.");
        }

        StringBuilder query = new StringBuilder("SELECT borrowing_status_id, COUNT(*) AS count FROM borrowing_record WHERE 1=0");
        String[] params = new String[statuses.size()];

        for (int i = 0; i < statuses.size(); i++) {
            query.append(" OR borrowing_status_id = ?");
            params[i] = String.valueOf(statuses.get(i).getValue());
        }

        query.append(" GROUP BY borrowing_status_id");
        Map<BorrowStatus, Long> statusCountMap = new EnumMap<>(BorrowStatus.class);

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            for (int j = 0; j < params.length; j++) {
                statement.setString(j + 1, params[j]);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BorrowStatus status = BorrowStatus.fromValue(resultSet.getInt("borrowing_status_id"));
                Long count = resultSet.getLong("count");
                statusCountMap.put(status, count);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to count borrowing records by statuses.", e);
        }

        return statusCountMap;
    }

    @Override
    public List<BorrowingRecord> searchBorrowingsWithStatuses(List<String> terms, List<BorrowStatus> statuses) {
        if (terms == null || statuses == null) return new ArrayList<>();

        String baseQuery = "SELECT book.`id` AS book_id, `borrowing_code`,`issue_date`,`due_date`, `retuned_date`, `book_code`, `title`, `author`, `published_year`, `total_copies`, `available_copies`, `category_id`, `category`, member.`id` AS member_id, `member_code`, `name`, `email`,`phone`, `address`,`joined_date`, `status`  FROM borrowing_record INNER JOIN `book` ON `book_id` = `book`.`id` INNER JOIN `member` ON `member_id` = `member`.`id` INNER JOIN `borrowing_status` ON `borrowing_status_id` = `borrowing_status`.`id` INNER JOIN `category` ON book.category_id = category.id WHERE 1=1";

        StringBuilder query = new StringBuilder(baseQuery);
        List<String> params = new ArrayList<>();

        for (String term : terms) {
            if (term == null || term.isBlank()) continue;
            query.append(" AND (borrowing_code LIKE ? OR LOWER(name) LIKE ? OR LOWER(title) LIKE ?)");
            String likeValue = "%" + term.toLowerCase() + "%";
            params.add(likeValue);
            params.add(likeValue);
            params.add(likeValue);
        }

        if (!statuses.isEmpty() && !statuses.contains(BorrowStatus.ALL)) {
            params.addAll(appendStatuesToQuery(statuses, query));
        }

        query.append(" ORDER BY borrowing_code DESC");

        return getBorrowingRecords(query.toString(), params.toArray(new String[0]));
    }

    private List<String> appendStatuesToQuery(List<BorrowStatus> statuses, StringBuilder query) {
        List<String> params = new ArrayList<>();
        boolean isFirst = true;

        for (BorrowStatus status : statuses) {
            if (status == null) continue;

            if (isFirst) {
                query.append(" AND (borrowing_status_id = ?");
                isFirst = false;
            } else {
                query.append(" OR borrowing_status_id = ?");
            }
            params.add(String.valueOf(status.getValue()));
        }
        query.append(")");
        return params;
    }
}
