package io.github.sachintha_madubashana.libraryos.utill;

import com.password4j.BcryptFunction;
import com.password4j.Password;

public class PasswordUtil {
    private static final BcryptFunction BCRYPT = BcryptFunction.getInstance(com.password4j.types.Bcrypt.A, 12);

    private PasswordUtil() {
    }

    public static String hash(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        return Password.hash(rawPassword).with(BCRYPT).getResult();
    }

    public static boolean verify(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null || hashedPassword.isBlank()) {
            return false;
        }
        return Password.check(rawPassword, hashedPassword).with(BCRYPT);
    }
}
