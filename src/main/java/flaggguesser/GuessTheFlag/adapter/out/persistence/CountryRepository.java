package flaggguesser.GuessTheFlag.adapter.out.persistence;

import flaggguesser.GuessTheFlag.domain.model.Country;
import flaggguesser.GuessTheFlag.domain.ports.out.CountryPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>, CountryPort {
}