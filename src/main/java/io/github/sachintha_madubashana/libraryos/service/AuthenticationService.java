package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.model.User;
import io.github.sachintha_madubashana.libraryos.repository.UserRepository;
import io.github.sachintha_madubashana.libraryos.utill.PasswordUtil;

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

        if (credential.getUsername() == null || credential.getPassword() == null) {
            return Result.error("Username or Password is Empty");
        }

        if (credential.getUsername().isEmpty() || credential.getPassword().isEmpty()) {
            return Result.error("Username or Password is Empty");
        }

        if (credential.getUsername().length() < 3 || credential.getPassword().length() < 3) {
            return Result.error("Username or Password is too short");
        }

        if (credential.getUsername().contains(" ") || credential.getPassword().contains(" ")) {
            return Result.error("Username or Password contains space");
        }

        Optional<User> user = userRepository.findUser(credential);

        if(user.isEmpty()) {
            return Result.error("User not found");
        }

        if (!PasswordUtil.verify(credential.getPassword(), user.get().getPassword())) {
            return Result.error("Invalid Credentials");
        }

        return Result.success("User Authenticated", user.get());
    }
}
