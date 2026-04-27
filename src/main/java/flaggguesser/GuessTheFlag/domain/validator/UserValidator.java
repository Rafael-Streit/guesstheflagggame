package flaggguesser.GuessTheFlag.domain.validator;

import org.springframework.util.StringUtils;

public class UserValidator {

    public static void validate(String username, String password) {
        validateUsername(username);
        validateUserPassword(password);
    }

    public static void validateUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        if (username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
    }

    public static void validateUserPassword(String password) {
        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }
}