package io.github.sachintha_madubashana.libraryos.model;

import java.util.regex.Pattern;

public class MemberValidator {
    private static final Pattern EMAIL_PATTERN =            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN =            Pattern.compile("^[0-9]{3}-[0-9]{7}$");

    private MemberValidator() {
        // Utility class
    }

    public static String validateName(String name) {
        if (name == null || name.isBlank()) {
            return "Full name is required.";
        }

        name = name.trim();

        if (name.length() < 3) {
            return "Name must be at least 3 characters.";
        }

        if (name.length() > 100) {
            return "Name must not exceed 100 characters.";
        }

        if (!name.matches("^[a-zA-Z ]+$")) {
            return "Name can only contain letters and spaces.";
        }

        return null;
    }

    public static String validateEmail(String email) {
        if (email == null || email.isBlank()) {
            return "Email address is required.";
        }

        email = email.trim();

        if (email.length() > 100) {
            return "Email must not exceed 100 characters.";
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return "Enter a valid email address.";
        }

        return null;
    }

    public static String validatePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            return "Phone number is required.";
        }

        phone = phone.trim();

        if (!PHONE_PATTERN.matcher(phone).matches()) {
            return "Phone must be in the format 077-1234567.";
        }

        return null;
    }

    public static String validateAddress(String address) {
        if (address == null || address.isBlank()) {
            return "Address is required.";
        }

        address = address.trim();

        if (address.length() < 5) {
            return "Address must be at least 5 characters.";
        }

        if (address.length() > 255) {
            return "Address must not exceed 255 characters.";
        }

        return null;
    }
}