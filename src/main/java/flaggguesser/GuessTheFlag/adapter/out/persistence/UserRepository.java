package flaggguesser.GuessTheFlag.adapter.out.persistence;

import flaggguesser.GuessTheFlag.domain.model.User;
import flaggguesser.GuessTheFlag.domain.ports.out.UserPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>, UserPort {
}
