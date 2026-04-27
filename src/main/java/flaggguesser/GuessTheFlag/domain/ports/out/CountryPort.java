package flaggguesser.GuessTheFlag.domain.ports.out;

import flaggguesser.GuessTheFlag.domain.model.Country;

import java.util.List;

public interface CountryPort {
    List<Country> findAll();
}
