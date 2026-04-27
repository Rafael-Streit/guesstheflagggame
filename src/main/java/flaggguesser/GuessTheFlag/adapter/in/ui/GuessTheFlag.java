package flaggguesser.GuessTheFlag.adapter.in.ui;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import flaggguesser.GuessTheFlag.application.service.UserService;
import flaggguesser.GuessTheFlag.domain.exeptions.UserNameAlreadyExistsException;
import flaggguesser.GuessTheFlag.domain.model.Country;
import flaggguesser.GuessTheFlag.domain.model.RoundData;
import flaggguesser.GuessTheFlag.application.service.SettingsService;
import flaggguesser.GuessTheFlag.domain.ports.in.GameUseCase;
import flaggguesser.GuessTheFlag.domain.ports.in.SettingsUseCase;
import flaggguesser.GuessTheFlag.domain.ports.in.UserUseCase;


@Route("guessTheFlag")
@CssImport("./styles.css")
public class GuessTheFlag extends VerticalLayout {

    private final GameUseCase gameUseCase;
    private final SettingsUseCase settingsService;
    private final UserUseCase userUseCase;

    public GuessTheFlag(GameUseCase gameUseCase, SettingsService settingsService, UserService userUseCase) {
        this.gameUseCase = gameUseCase;
        this.settingsService = settingsService;
        this.userUseCase = userUseCase;
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

        Image userLoginButton = new Image("https://cdn-icons-png.flaticon.com/512/1077/1077114.png", null);
        userLoginButton.addClickListener(e -> {
            System.out.println("login-click");
        });
        userLoginButton.setClassName("my-img-button");

        add(title, startButton, settingsButton, userLoginButton);
        startButton.addClickListener(e -> {
            gameUseCase.resetGame();
            removeAll();
            askForRegistration();
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
        createLightOrDarkModeButton();
    }

    private void createLightOrDarkModeButton() {
        Checkbox toggle = new Checkbox("Dark Mode");
        toggle.getElement().getThemeList().add("switch");
        toggle.setValue(settingsService.isDarkMode());

        toggle.addValueChangeListener(event -> {
            boolean dark = event.getValue();
            settingsService.setDefaultMode(dark ? "dark" : "light");
            ThemeList themes = UI.getCurrent().getElement().getThemeList();
            if (dark) themes.add(Lumo.DARK);
            else themes.remove(Lumo.DARK);
        });

        add(toggle);
    }



    // ======================== Game ========================

    private void askForRegistration() {
        Input userNameInput = new Input();
        Input userPasswordInput = new Input();
        userNameInput.addClassName("userNameInput");
        userPasswordInput.addClassName("userNameInput");
        H1 gameHeading = new H1("Registration");
        gameHeading.addClassName("gameHeading");
        Button continueButton = new Button("Continue");
        add(gameHeading, userNameInput, userPasswordInput, continueButton);
        goBackToStartingMenu();

        continueButton.addClickListener(e -> {
            try {
                System.out.println("INPUT VALUE = " + userNameInput.getValue());
                System.out.println("INPUT VALUE PASSWORD = " + userPasswordInput.getValue());
                userUseCase.createUser(userNameInput.getValue(), userPasswordInput.getValue());
                gameUseCase.setCurrentUsername(userNameInput.getValue());
                removeAll();
                startRound();
            } catch (UserNameAlreadyExistsException ex) {
                Notification.show("Username already taken!");
            } catch (IllegalArgumentException ex) {
                Notification.show(ex.getMessage());
            }
        });
    }

    private void startRound() {
        RoundData round = gameUseCase.nextRound();

        H2 question = new H2("Which flag is this?");
        question.setClassName("heading-game");

        Image flagToGuess = new Image(round.correct().getFlagUrl(), round.correct().getName());
        flagToGuess.addClassName("flagImage");
        flagToGuess.setWidth("300px");
        flagToGuess.setHeight("200px");

        add(question, flagToGuess);
        add(buildAnswerButtons(round));
        add(new Paragraph("Score: " + gameUseCase.getScore()));
        add(new Paragraph("Round: " + (gameUseCase.getRounds() + 1) + "/" + gameUseCase.getAmountOfRounds()));
    }

    private HorizontalLayout buildAnswerButtons(RoundData round) {
        HorizontalLayout answers = new HorizontalLayout();
        answers.addClassName("answersLayout");

        for (Country country : round.options()) {
            Button flagButton = new Button(country.getName());
            flagButton.addClassName("flagButton");

            flagButton.addClickListener(e -> {
                boolean correct = gameUseCase.checkAnswer(country, round.correct());
                if (correct) {
                    flagButton.addClassName("rightButton");
                } else {
                    flagButton.addClassName("wrongAnswerButton");
                    answers.getChildren().filter(c -> c instanceof Button).map(c -> (Button) c).filter(b -> b.getText().equals(round.correct().getName())).findFirst().ifPresent(b -> b.addClassName("rightButton"));
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
            gameUseCase.incrementRound();
            if (gameUseCase.isGameOver()) {
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
        this.gameUseCase.fireGameCompletedEvent();
        add(new H1("Game Over"));
        add(new Paragraph("Final Score: " + gameUseCase.getScore()));
        Button playAgain = new Button("Play Again");
        playAgain.addClickListener(e -> {
            removeAll();
            startingMenu();
        });
        add(playAgain);
    }
}