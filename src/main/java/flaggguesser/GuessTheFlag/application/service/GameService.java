package flaggguesser.GuessTheFlag.application.service;

import flaggguesser.GuessTheFlag.domain.model.Country;
import flaggguesser.GuessTheFlag.domain.model.RoundData;
import flaggguesser.GuessTheFlag.domain.ports.in.GameUseCase;
import flaggguesser.GuessTheFlag.domain.ports.out.CountryPort;
import flaggguesser.GuessTheFlag.infrastructure.events.RoundCompletedEvent;
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
public class GameService implements GameUseCase {

    private final CountryPort countryPort;
    private final Random RANDOM = new Random();
    @Value("${game.rounds}")
    private int AMOUNT_OF_ROUNDS;
    private int score = 0;
    private int rounds = 0;
    private final List<Country> usedCountries = new ArrayList<>();
    private String currentUsername;
    private final ApplicationEventPublisher applicationEventPublisher;

    public GameService(CountryPort countryPort, ApplicationEventPublisher applicationEventPublisher) {
        this.countryPort = countryPort;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void resetGame() {
        this.score = 0;
        this.rounds = 0;
        this.usedCountries.clear();
    }

    public RoundData nextRound() {
        List<Country> shuffled = new ArrayList<>(countryPort.findAll());
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
            this.score++;
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

    public int getAmountOfRounds() {
        return AMOUNT_OF_ROUNDS;
    }

    public void fireGameCompletedEvent() {
        applicationEventPublisher.publishEvent(
                new RoundCompletedEvent(this, currentUsername, score)
        );
    }

    public CountryPort getCountryPort() {
        return countryPort;
    }

    public Random getRANDOM() {
        return RANDOM;
    }

    public int getAMOUNT_OF_ROUNDS() {
        return AMOUNT_OF_ROUNDS;
    }

    public void setAMOUNT_OF_ROUNDS(int AMOUNT_OF_ROUNDS) {
        this.AMOUNT_OF_ROUNDS = AMOUNT_OF_ROUNDS;
    }

    @Override
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public List<Country> getUsedCountries() {
        return usedCountries;
    }

    @Override
    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }
}
