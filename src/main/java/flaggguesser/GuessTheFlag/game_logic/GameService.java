package flaggguesser.GuessTheFlag.game_logic;

import flaggguesser.GuessTheFlag.countrys.Country;
import flaggguesser.GuessTheFlag.countrys.CountryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameService {

    private final CountryRepository countryRepository;
    private final Random RANDOM = new Random();
    @Value("${game.rounds}")
    private int AMOUNT_OF_ROUNDS;
    private int score = 0;
    private int rounds = 0;
    private final List<Country> usedCountries = new ArrayList<>();
    private final ApplicationEventPublisher applicationEventPublisher;

    public GameService(CountryRepository countryRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.countryRepository = countryRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void resetGame() {
        this.score = 0;
        this.rounds = 0;
        this.usedCountries.clear();
    }

    public RoundData nextRound() {
        List<Country> shuffled = new ArrayList<>(countryRepository.findAll());
        shuffled.removeAll(usedCountries);
        Collections.shuffle(shuffled);

        List<Country> options = shuffled.subList(0, 4);
        int correctIndex = RANDOM.nextInt(4);
        Country correct = options.get(correctIndex);
        usedCountries.add(correct);

        return new RoundData(new ArrayList<>(options), correct);
    }

    public boolean checkAnswer(Country answer, Country correct) {
        if (answer.equals(correct)) {
            score++;
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return rounds >= AMOUNT_OF_ROUNDS;
    }

    public void incrementRound() {
        rounds++;
    }

    public int getScore() {
        return score;
    }

    public int getRounds() {
        return rounds;
    }

    public int getAmountOfRounds() {
        return AMOUNT_OF_ROUNDS;
    }
}
