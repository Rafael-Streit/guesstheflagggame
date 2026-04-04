package flaggguesser.GuessTheFlag.view;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import flaggguesser.GuessTheFlag.countrys.Country;
import flaggguesser.GuessTheFlag.game_logic.GameService;
import flaggguesser.GuessTheFlag.game_logic.RoundData;

@Route("")
@CssImport("./styles.css")
public class GuessTheFlag extends VerticalLayout {

    private final GameService gameService;

    public GuessTheFlag(GameService gameService) {
        this.gameService = gameService;
        startingMenu();
    }

    // ======================== Starting Menu ========================

    private void startingMenu() {
        H1 title = new H1("Guess the Flag");
        title.addClassName("HeadingGameLoop");
        Button startButton = new Button("Play");
        startButton.addClassName("StartButton");
        Button settingsButton = new Button("Settings");
        settingsButton.addClassName("SettingsButton");

        add(title, startButton, settingsButton);
        startButton.addClickListener(e -> {
            gameService.resetGame();
            removeAll();
            askForUserName();
        });

        settingsButton.addClickListener(e -> {
            removeAll();
            openSettings();
        });
    }

    private void goBackToStartingMenu() {
        Button back = new Button("⬅");
        add(back);
        back.addClickListener(e -> {
            removeAll();
            startingMenu();
        });
    }

    // ======================== Settings ========================

    private void openSettings() {
        H1 settingsText = new H1("Settings");
        settingsText.addClassName("settingsHeading");
        add(settingsText);
        goBackToStartingMenu();
    }

    // ======================== Game Logic ========================

    private void askForUserName() {
        Input input = new Input();
        input.addClassName("userNameInput");
        H1 gameHeading = new H1("Choose your username");
        gameHeading.addClassName("gameHeading");
        Button continueButton = new Button("Continue");

        add(gameHeading, input, continueButton);
        goBackToStartingMenu();

        continueButton.addClickListener(e -> {
            removeAll();
            startRound();
        });
    }

    private void startRound() {
        RoundData round = gameService.nextRound();

        H2 question = new H2("Which flag is this?");
        question.setClassName("heading-game");

        Image flagToGuess = new Image(round.correct().getFlagUrl(), round.correct().name());
        flagToGuess.addClassName("flagImage");
        flagToGuess.setWidth("300px");
        flagToGuess.setHeight("200px");

        add(question, flagToGuess);
        add(buildAnswerButtons(round));
        add(new Paragraph("Score: " + gameService.getScore()));
        add(new Paragraph("Round: " + (gameService.getRounds() + 1) + "/" + gameService.getAmountOfRounds()));
    }

    private HorizontalLayout buildAnswerButtons(RoundData round) {
        HorizontalLayout answers = new HorizontalLayout();
        answers.addClassName("answersLayout");

        for (Country country : round.options()) {
            Button flagButton = new Button(country.name());
            flagButton.addClassName("flagButton");

            flagButton.addClickListener(e -> {
                boolean correct = gameService.checkAnswer(country, round.correct());
                if (correct) {
                    flagButton.addClassName("rightButton");
                } else {
                    flagButton.addClassName("wrongAnswerButton");
                    answers.getChildren().filter(c -> c instanceof Button).map(c -> (Button) c).filter(b -> b.getText().equals(round.correct().name())).findFirst().ifPresent(b -> b.addClassName("rightButton"));
                }
                disableButtons(answers);
                addNextButton();
            });

            answers.add(flagButton);
        }
        return answers;
    }

    private void disableButtons(HorizontalLayout layout) {
        layout.getChildren().filter(c -> c instanceof Button).forEach(c -> ((Button) c).setEnabled(false));
    }

    private void addNextButton() {
        Button next = new Button("Next");
        next.addClickListener(e -> {
            gameService.incrementRound();
            if (gameService.isGameOver()) {
                showGameOver();
            } else {
                removeAll();
                startRound();
            }
        });
        add(next);
    }

    private void showGameOver() {
        removeAll();
        add(new H1("Game Over"));
        add(new Paragraph("Final Score: " + gameService.getScore()));
        Button playAgain = new Button("Play Again");
        playAgain.addClickListener(e -> {
            removeAll();
            startingMenu();
        });
        add(playAgain);
    }
}