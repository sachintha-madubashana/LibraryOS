package io.github.sachintha_madubashana.libraryos.model;

import java.util.ArrayList;

public class AuthenticationService {
    private final ArrayList<User> users = new ArrayList<>();

    public AuthenticationService() {
        users.add(new User("admin", "admin"));
        users.add(new User("user", "user"));
        users.add(new User("guest", "guest"));
    }

    public boolean authenticate(User credential) {
        if (credential == null) {
            return false;
        }

        if (credential.getUsername() == null || credential.getPassword() == null) {
            return false;
        }

        if (credential.getUsername().isEmpty() || credential.getPassword().isEmpty()) {
            return false;
        }

        if (credential.getUsername().length() < 3 || credential.getPassword().length() < 3) {
            return false;
        }

        if (credential.getUsername().contains(" ") || credential.getPassword().contains(" ")) {
            return false;
        }

        for (User user : users) {
            if (user.isEquals(credential)) {
                return true;
            }
        }
        return false;
    }
}
