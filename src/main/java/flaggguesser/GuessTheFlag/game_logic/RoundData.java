package flaggguesser.GuessTheFlag.game_logic;


import flaggguesser.GuessTheFlag.countrys.Country;

import java.util.List;

public record RoundData(List<Country> options, Country correct) {
}
