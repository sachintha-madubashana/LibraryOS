package io.github.sachintha_madubashana.libraryos.repository.mysql;

import io.github.sachintha_madubashana.libraryos.database.DatabaseConnection;
import io.github.sachintha_madubashana.libraryos.model.User;
import io.github.sachintha_madubashana.libraryos.repository.UserRepository;

import java.sql.PreparedStatement;
import java.util.Optional;

public class MySQLUserRepository implements UserRepository{
    @Override
    public Optional<User> findUser(User user) {
        String query = "SELECT `username`,`password` FROM `user` WHERE `username` = ?";

        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            var resultSet = statement.executeQuery();
            if(resultSet.next()){
                return Optional.of(new User(resultSet.getString("username"), resultSet.getString("password")));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to find user.", e);
        }
        return Optional.empty();
    }
}
