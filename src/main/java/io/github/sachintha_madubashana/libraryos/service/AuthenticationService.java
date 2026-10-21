package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.model.User;

import java.util.ArrayList;

public class AuthenticationService {
    private final ArrayList<User> users = new ArrayList<>();

    public AuthenticationService() {
        users.add(new User("admin", "admin"));
        users.add(new User("user", "user"));
        users.add(new User("guest", "guest"));
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

        for (User user : users) {
            if (user.isEquals(credential)) {
                return Result.success("User Authenticated", user);
            }
        }
        return Result.error("Invalid Credentials");
    }
}
