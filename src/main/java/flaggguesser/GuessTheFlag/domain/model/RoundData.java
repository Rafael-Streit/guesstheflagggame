package flaggguesser.GuessTheFlag.domain.model;


import java.util.List;

public record RoundData(List<Country> options, Country correct) {
}
