package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.database.DatabaseConnection;
import io.github.sachintha_madubashana.libraryos.model.Book;
import io.github.sachintha_madubashana.libraryos.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLBookRepository implements BookRepository {
    private final Connection connection;

    public MySQLBookRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public Optional<Book> findById(String code) {
        String query = "SELECT b.`book_code`, b.`title`, b.`author`, b.`published_year`, b.`total_copies`, b.`available_copies`, c.`id` AS category_id, c.`category` AS category_name FROM book b INNER JOIN category c ON b.`category_id` = c.`id` WHERE b.`book_code` = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Category category = new Category(
                            resultSet.getInt("category_id"),
                            resultSet.getString("category_name")
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

                    return Optional.of(book);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find book.", e);
        }

        return Optional.empty();
    }

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.book_code, b.title, b.author, b.published_year, b.total_copies, b.available_copies, c.id AS category_id, c.category AS category_name FROM book b INNER JOIN category c ON b.category_id = c.id";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Category category = new Category(
                            resultSet.getInt("category_id"),
                            resultSet.getString("category_name")
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

                    books.add(book);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find book.", e);
        }

        return books;
    }

    @Override
    public boolean save(Book book) {
        if (book == null || existsById(book.getId())) {
            return false;
        }

        String query = "INSERT INTO `book` (`book_code`, `title`, `author`, `published_year`, `total_copies`, `available_copies`, `category_id`) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getPublishedYear());
            statement.setInt(5, book.getTotalCopies());
            statement.setInt(6, book.getAvailableCopies());
            statement.setInt(7, book.getCategory().getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Book book) {
        if (book == null || book.getId() == null) {
            return false;
        }

        if (existsById(book.getId())) {
            return false;
        }

        String query = "UPDATE `book` SET `title` = ?, `author` = ?, `published_year` = ?, `total_copies` = ?, `available_copies` = ?, `category_id` = ? WHERE `book_code` = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getPublishedYear());
            statement.setInt(4, book.getTotalCopies());
            statement.setInt(5, book.getAvailableCopies());
            statement.setInt(6, book.getCategory().getId());
            statement.setString(7, book.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        if (id == null) {
            return false;
        }

        String query = "DELETE FROM `book` WHERE `book_code` = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete book.", e);
        }
    }
}
