package flaggguesser.GuessTheFlag.domain.exeptions;

public class UserNameAlreadyExistsException extends RuntimeException {
    public UserNameAlreadyExistsException(String username) {
        super("Username already exists: " + username);
    }
}
