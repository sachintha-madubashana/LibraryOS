package io.github.sachintha_madubashana.libraryos.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;

public class User implements Serializable {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final String username;
    private final String password;
    private final String rawPassword;

    public User(String username, String password) {
        this.username = username;
        this.rawPassword = password;
        this.password = hashPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEquals(User credential) {
        if (credential == null || this.username == null || !this.username.equals(credential.getUsername())) {
            return false;
        }
        String candidatePassword = credential.rawPassword != null ? credential.rawPassword : credential.getPassword();
        return verifyPassword(candidatePassword);
    }

    public String hashPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }

    public boolean verifyPassword(String plainPassword) {
        return encoder.matches(plainPassword, password);
    }
}
