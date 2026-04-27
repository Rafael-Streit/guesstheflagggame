package flaggguesser.GuessTheFlag.domain.ports.out;

import flaggguesser.GuessTheFlag.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPort {
    List<User> findTop10ByOrderByScoreDesc();
    List<User> findByScoreGreaterThan(int score);
    Optional<User> findByUsername(String username);
    User save(User user);
}
