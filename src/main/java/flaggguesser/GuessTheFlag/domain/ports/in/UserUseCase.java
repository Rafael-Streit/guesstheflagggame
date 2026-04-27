package flaggguesser.GuessTheFlag.domain.ports.in;

import flaggguesser.GuessTheFlag.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    void createUser(String username, String password);
    Optional<User> getUser(String username);
    void updateScore(String username, int score);
    List<User> getHighscores();
}