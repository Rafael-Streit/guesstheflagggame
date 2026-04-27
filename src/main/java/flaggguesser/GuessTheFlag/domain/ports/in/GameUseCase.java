package flaggguesser.GuessTheFlag.domain.ports.in;

import flaggguesser.GuessTheFlag.domain.model.Country;
import flaggguesser.GuessTheFlag.domain.model.RoundData;

public interface GameUseCase {

        RoundData nextRound();
        boolean checkAnswer(Country answer, Country correct);
        boolean isGameOver();
        void incrementRound();
        void resetGame();
        int getScore();
        int getRounds();
        int getAmountOfRounds();
        void setCurrentUsername(String username);
        String getCurrentUsername();
        void fireGameCompletedEvent();

}
