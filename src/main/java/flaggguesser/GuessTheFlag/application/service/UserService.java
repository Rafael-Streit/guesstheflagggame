package flaggguesser.GuessTheFlag.application.service;

import flaggguesser.GuessTheFlag.domain.exeptions.UserNameAlreadyExistsException;
import flaggguesser.GuessTheFlag.domain.exeptions.UserNotFoundException;
import flaggguesser.GuessTheFlag.domain.model.User;
import flaggguesser.GuessTheFlag.domain.ports.in.UserUseCase;
import flaggguesser.GuessTheFlag.domain.ports.out.UserPort;
import flaggguesser.GuessTheFlag.domain.validator.UserValidator;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserUseCase {

    private final UserPort userPort;

    public UserService(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public void createUser(String username, String password) {
        UserValidator.validate(username, password);
        if (userPort.findByUsername(username).isPresent()) {
            throw new UserNameAlreadyExistsException(username);
        }
        userPort.save(new User(username,  password));
    }

    @Override
    public Optional<User> getUser(String username) {
        return Optional.ofNullable(userPort.findByUsername(username))
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public void updateScore(String username, int score) {
        User user = userPort.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (score > user.getScore()) {
            user.setScore(score);
            userPort.save(user);
        }
    }

    @Override
    public List<User> getHighscores() {
        return userPort.findTop10ByOrderByScoreDesc();
    }
}
