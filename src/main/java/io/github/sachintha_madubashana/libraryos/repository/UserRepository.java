package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUser(User user);
}
