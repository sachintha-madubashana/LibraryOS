package io.github.sachintha_madubashana.libraryos.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;

public class User implements Serializable {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final String username;
    private final String password;
    private String rawPassword;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public boolean isEquals(User credential) {
        if (credential == null || this.username == null || !this.username.equals(credential.getUsername())) {
            return false;
        }
        if (credential.rawPassword == null || credential.rawPassword.isEmpty()) {
            return false;
        }

        return verifyPassword(credential.rawPassword);
    }

    public String hashPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }

    public boolean verifyPassword(String plainPassword) {
        return encoder.matches(plainPassword, password);
    }
}
