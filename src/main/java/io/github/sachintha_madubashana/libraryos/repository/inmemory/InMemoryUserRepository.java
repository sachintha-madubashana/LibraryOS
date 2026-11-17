package io.github.sachintha_madubashana.libraryos.repository.inmemory;

import io.github.sachintha_madubashana.libraryos.model.User;
import io.github.sachintha_madubashana.libraryos.repository.UserRepository;
import io.github.sachintha_madubashana.libraryos.service.AppServices;
import io.github.sachintha_madubashana.libraryos.service.SampleData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {
    private final List<User> users;

    public InMemoryUserRepository() {
        users =new ArrayList<>(SampleData.getUsers());
    }

    @Override
    public Optional<User> findUser(User user) {
        return users.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst();
    }
}
