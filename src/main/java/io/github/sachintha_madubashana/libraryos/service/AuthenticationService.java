package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.model.User;
import io.github.sachintha_madubashana.libraryos.repository.UserRepository;

import java.util.Optional;

public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Result<User> authenticate(User credential) {
        if (credential == null) {
            return Result.error("Credentials are Empty");
        }

        if (credential.getUsername() == null || (credential.getPassword() == null && credential.getRawPassword() == null)) {
            return Result.error("Username or Password is Empty");
        }

        if (credential.getUsername().isEmpty() || ((credential.getPassword() != null)? credential.getPassword().isEmpty(): credential.getRawPassword().isEmpty())) {
            return Result.error("Username or Password is Empty");
        }

        if (credential.getUsername().length() < 3 || (credential.getRawPassword().length() < 3)) {
            return Result.error("Username or Password is too short");
        }

        if (credential.getUsername().contains(" ") || credential.getRawPassword().contains(" ")) {
            return Result.error("Username or Password contains space");
        }

        Optional<User> user = userRepository.findUser(credential);

        if(user.isEmpty()) {
            return Result.error("User not found");
        }

        if (!user.get().isEquals(credential)) {
            return Result.error("Invalid Credentials");
        }

        return Result.success("User Authenticated", user.get());
    }
}
