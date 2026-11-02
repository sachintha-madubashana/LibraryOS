package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.database.DatabaseConnection;
import io.github.sachintha_madubashana.libraryos.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLBookRepository implements BookRepository{
    private final Connection connection;

    public MySQLBookRepository(){
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public Optional<Book> findById(String id) {
        String query = "SELECT `id`, `book_code`, `title`, `author`, `published_year`, `total_copies`, `available_copies`, `category_id` FROM `book` WHERE `id` = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Book book = new Book(
                            resultSet.getString("id"),
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getString("category_id"),
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
        return false;
    }

    @Override
    public List<Book> findAll() {
        List <Book> books = new ArrayList<>();
        String query = "SELECT `id`, `book_code`, `title`, `author`, `published_year`, `total_copies`, `available_copies`, `category_id` FROM `book` ";

        try(PreparedStatement statement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book(
                            resultSet.getString("id"),
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getString("category_id"),
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
        return false;
    }

    @Override
    public boolean update(Book book) {
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }
}
