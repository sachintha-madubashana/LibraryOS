package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.model.User;

import java.util.ArrayList;

public class AuthenticationService {
    private final ArrayList<User> users = new ArrayList<>();

    public AuthenticationService() {
        users.add(new User("admin", "admin"));
        users.add(new User("user", "user"));
        users.add(new User("guest", "guest"));
    }

    public ResultService<User> authenticate(User credential) {
        if (credential == null) {
            return ResultService.error("Credentials are Empty");
        }

        if (credential.getUsername() == null || credential.getPassword() == null) {
            return ResultService.error("Username or Password is Empty");
        }

        if (credential.getUsername().isEmpty() || credential.getPassword().isEmpty()) {
            return ResultService.error("Username or Password is Empty");
        }

        if (credential.getUsername().length() < 3 || credential.getPassword().length() < 3) {
            return ResultService.error("Username or Password is too short");
        }

        if (credential.getUsername().contains(" ") || credential.getPassword().contains(" ")) {
            return ResultService.error("Username or Password contains space");
        }

        for (User user : users) {
            if (user.isEquals(credential)) {
                return ResultService.success("User Authenticated", user);
            }
        }
        return ResultService.error("Invalid Credentials");
    }
}
