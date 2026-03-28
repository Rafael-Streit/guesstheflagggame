package flaggguesser.GuessTheFlag;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.jetbrains.annotations.NotNull;
import java.util.*;

@Route("")
@CssImport("./styles.css")
public class GuessTheFlag extends VerticalLayout {

    private boolean isSettingsActive = false;
    private boolean isGameActive = false;
    private int score = 0;
    private int rounds = 0;
    private static final int AMOUNT_OF_ROUNDS = 25;
    private final Random RANDOM = new Random();
    private final ArrayList<String> deletedCountriesFromRound = new ArrayList<>(26);

    public GuessTheFlag() {
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
        addElementsToStartMenu(title, startButton, settingsButton);
        startButton.addClickListener(event -> {
            if (!isGameActive) {
                this.isGameActive = true;
                if (this.isSettingsActive) {
                    remove(settingsButton);
                    remove();
                }
                playGame(settingsButton, title, startButton);
            }
        });
        settingsButton.addClickListener(event -> {
            openSettings(startButton);
            remove(startButton);
        });
    }

    private void goBackToStartingMenu(){
        Button backToStartingMenu = new Button("⬅");
        add(backToStartingMenu);
        backToStartingMenu.addClickListener(press -> {
            removeAll();
            this.isGameActive = false;
            this.isSettingsActive = false;
            startingMenu();
        });
    }

    private void addElementsToStartMenu(@NotNull H1 title, Button startingButton, Button settingsButton) {
        add(title);
        add(startingButton);
        add(settingsButton);
    }

    private void removeElementFromStartingMenu(H1 title, Button startingButton, Button settingsButton) {
        remove(title);
        remove(startingButton);
        remove(settingsButton);
    }

// ======================== Settings ========================

    private void openSettings(Button startingMenu) {
        removeAll();
        H1 settingsText = new H1("Settings");
        settingsText.addClassName("settingsHeading");
        add(settingsText);
        goBackToStartingMenu();
    }

// ======================== Game Logic ========================

    private void askForUserName(Button settingsButton, H1 heading, Button playButton) {
        goBackToStartingMenu();
        Input input = new Input();
        input.addClassName("userNameInput");
        H1 gameHeading = new H1("Choose you're username");
        gameHeading.addClassName("gameHeading");
        removeElementFromStartingMenu(heading, settingsButton, playButton);
        add(input);
        add(gameHeading);
        if (input.getValue() != null) {
            String userName = input.getValue();
            add(new Paragraph(userName));
        } else {
            add(new Paragraph("this user name is invalid"));
        }
        remove(input);
        remove(gameHeading);
    }

    private void playGame(@NotNull Button settingsButton, H1 heading, Button playButton) {
        Button continueAfterEnteringUserName = new Button("Continue");
        askForUserName(settingsButton, heading, playButton);
        add(continueAfterEnteringUserName);
        continueAfterEnteringUserName.addClickListener(event -> {
            startRound();
        });
    }

    public final void deleteCountryFromRotation(String country){
        deletedCountriesFromRound.add(country);
    }

    private void startRound() {
        removeAll();

        ArrayList<String> countries = new ArrayList<>(COUNTRY_CODES.keySet());
        List<String> shuffled = new ArrayList<>(countries);
        Collections.shuffle(shuffled);
        List<String> roundCountries = shuffled.subList(0, 4);

        int correctIndex = RANDOM.nextInt(4);
        String realCountry = roundCountries.get(correctIndex);
        while (deletedCountriesFromRound.contains(realCountry)){
            Collections.shuffle(shuffled);
            roundCountries = shuffled.subList(0,4);
        }
        deleteCountryFromRotation(realCountry);

        String isoCode = COUNTRY_CODES.getOrDefault(realCountry, "xx");
        String flagUrl = "https://flagcdn.com/w320/" + isoCode + ".png";

        H2 question = new H2("Which flag is this?");
        question.setClassName("heading-game");
        Image flagToGuess = new Image(flagUrl, realCountry);
        flagToGuess.addClassName("flagImage");
        flagToGuess.setWidth("300px");
        flagToGuess.setHeight("200px");

        add(question, flagToGuess);
        HorizontalLayout answers = getHorizontalLayout(countries, roundCountries, correctIndex);
        add(answers);
        add(new Paragraph("Score: " + score));
        add(new Paragraph("Round: " + (rounds + 1) + "/" + AMOUNT_OF_ROUNDS));
    }

    private HorizontalLayout getHorizontalLayout(ArrayList<String> countries, List<String> roundCountries, int correctIndex) {
        HorizontalLayout answers = new HorizontalLayout();
        answers.addClassName("answersLayout");

        for (int i = 0; i < 4; i++) {
            String country = roundCountries.get(i);
            String displayName = formatCountryName(country);
            Button flagButton = new Button(displayName);
            flagButton.addClassName("flagButton");

            int index = i;
            flagButton.addClickListener(e -> {
                if (index == correctIndex) {
                    flagButton.addClassName("rightButton");
                    score++;
                } else {
                    flagButton.addClassName("wrongAnswerButton");
                    answers.getChildren()
                            .filter(c -> c instanceof Button)
                            .skip(correctIndex)
                            .findFirst()
                            .ifPresent(c -> ((Button) c).
                                    addClassName("rightButton"));
                }
                disableButtons(answers);
                addNextButton(countries);
            });

            answers.add(flagButton);
        }
        return answers;
    }


    private void lockButtonsAndColor(Button pressed, Button correct) {
        pressed.addClassName("wrongAnswerButton");
        correct.addClassName("rightButton");
        pressed.getParent().ifPresent(parent -> {
            parent.
                    getChildren().
                    filter(c -> c instanceof Button)
                    .forEach(c -> ((Button) c)
                            .setEnabled(false))
            ;
        });
    }

    private String formatCountryName(String country) {
        if (country == null || country.isEmpty()) return country;
        return switch (country) {
            case "usa" -> "USA";
            case "uae" -> "UAE";
            case "drcongo" -> "DR Congo";
            case "antiguaandbarbuda" -> "Antigua & Barbuda";
            case "bosniaandherzegovina" -> "Bosnia & Herzegovina";
            case "saintkittsandnevis" -> "Saint Kitts & Nevis";
            case "saintvincentandthegrenadines" -> "Saint Vincent & Grenadines";
            case "saotomeandprincipe" -> "São Tomé & Príncipe";
            case "trinidadandtobago" -> "Trinidad & Tobago";
            case "papuanewguinea" -> "Papua New Guinea";
            case "newzealand" -> "New Zealand";
            case "northkorea" -> "North Korea";
            case "southkorea" -> "South Korea";
            case "southafrica" -> "South Africa";
            case "southsudan" -> "South Sudan";
            case "costarica" -> "Costa Rica";
            case "elsalvador" -> "El Salvador";
            case "greatbritain" -> "Great Britain";
            case "unitedkingdom" -> "United Kingdom";
            case "northmacedonia" -> "North Macedonia";
            case "saudiarabia" -> "Saudi Arabia";
            case "sierraleone" -> "Sierra Leone";
            case "srilanka" -> "Sri Lanka";
            case "marshallislands" -> "Marshall Islands";
            case "solomonislands" -> "Solomon Islands";
            case "capeverde" -> "Cape Verde";
            case "ivorycoast" -> "Ivory Coast";
            case "burkinfaso" -> "Burkina Faso";
            case "easttimor" -> "East Timor";
            case "equatorialguinea" -> "Equatorial Guinea";
            case "centralafricanrepublic" -> "Central African Republic";
            case "dominicanrepublic" -> "Dominican Republic";
            default -> country.substring(0, 1).toUpperCase() + country.substring(1);
        };
    }


    private void disableButtons(HorizontalLayout layout) {
        layout.getChildren().forEach(c -> ((Button) c).setEnabled(false));
    }

    private void addNextButton(ArrayList<String> countries) {
        Button next = new Button("Next");
        next.addClickListener(e -> {
            rounds++;
            if (rounds < AMOUNT_OF_ROUNDS) {
                startRound();
            } else {
                showGameOver();
            }
        });
        add(next);
    }


    private void showGameOver() {
        removeAll();
        this.deletedCountriesFromRound.clear();
        add(new H1("Game Over"));
        add(new Paragraph("Final Score: " + score));
    }


// ============================== Stop here is only a Map with All Countries and some other stuff ==============================

    public static final Map<String, String> COUNTRY_CODES = Map.<String, String>ofEntries(
            Map.entry("Afghanistan", "af"),
            Map.entry("Albania", "al"),
            Map.entry("Algeria", "dz"),
            Map.entry("Andorra", "ad"),
            Map.entry("Angola", "ao"),
            Map.entry("Antigua and Barbuda", "ag"),
            Map.entry("Argentina", "ar"),
            Map.entry("Armenia", "am"),
            Map.entry("Australia", "au"),
            Map.entry("Austria", "at"),
            Map.entry("Azerbaijan", "az"),
            Map.entry("Bahamas", "bs"),
            Map.entry("Bahrain", "bh"),
            Map.entry("Bangladesh", "bd"),
            Map.entry("Barbados", "bb"),
            Map.entry("Belarus", "by"),
            Map.entry("Belgium", "be"),
            Map.entry("Belize", "bz"),
            Map.entry("Benin", "bj"),
            Map.entry("Bhutan", "bt"),
            Map.entry("Bolivia", "bo"),
            Map.entry("Bosnia and Herzegovina", "ba"),
            Map.entry("Botswana", "bw"),
            Map.entry("Brazil", "br"),
            Map.entry("Brunei", "bn"),
            Map.entry("Bulgaria", "bg"),
            Map.entry("Burkina Faso", "bf"),
            Map.entry("Burundi", "bi"),
            Map.entry("Cabo Verde", "cv"),
            Map.entry("Cambodia", "kh"),
            Map.entry("Cameroon", "cm"),
            Map.entry("Canada", "ca"),
            Map.entry("Central African Republic", "cf"),
            Map.entry("Chad", "td"),
            Map.entry("Chile", "cl"),
            Map.entry("China", "cn"),
            Map.entry("Colombia", "co"),
            Map.entry("Comoros", "km"),
            Map.entry("Congo", "cg"),
            Map.entry("Democratic Republic of the Congo", "cd"),
            Map.entry("Costa Rica", "cr"),
            Map.entry("Croatia", "hr"),
            Map.entry("Cuba", "cu"),
            Map.entry("Cyprus", "cy"),
            Map.entry("Czechia", "cz"),
            Map.entry("Denmark", "dk"),
            Map.entry("Djibouti", "dj"),
            Map.entry("Dominica", "dm"),
            Map.entry("Dominican Republic", "do"),
            Map.entry("Ecuador", "ec"),
            Map.entry("Egypt", "eg"),
            Map.entry("El Salvador", "sv"),
            Map.entry("Equatorial Guinea", "gq"),
            Map.entry("Eritrea", "er"),
            Map.entry("Estonia", "ee"),
            Map.entry("Eswatini", "sz"),
            Map.entry("Ethiopia", "et"),
            Map.entry("Fiji", "fj"),
            Map.entry("Finland", "fi"),
            Map.entry("France", "fr"),
            Map.entry("Gabon", "ga"),
            Map.entry("Gambia", "gm"),
            Map.entry("Georgia", "ge"),
            Map.entry("Germany", "de"),
            Map.entry("Ghana", "gh"),
            Map.entry("Greece", "gr"),
            Map.entry("Grenada", "gd"),
            Map.entry("Guatemala", "gt"),
            Map.entry("Guinea", "gn"),
            Map.entry("Guinea-Bissau", "gw"),
            Map.entry("Guyana", "gy"),
            Map.entry("Haiti", "ht"),
            Map.entry("Honduras", "hn"),
            Map.entry("Hungary", "hu"),
            Map.entry("Iceland", "is"),
            Map.entry("India", "in"),
            Map.entry("Indonesia", "id"),
            Map.entry("Iran", "ir"),
            Map.entry("Iraq", "iq"),
            Map.entry("Ireland", "ie"),
            Map.entry("Israel", "il"),
            Map.entry("Italy", "it"),
            Map.entry("Jamaica", "jm"),
            Map.entry("Japan", "jp"),
            Map.entry("Jordan", "jo"),
            Map.entry("Kazakhstan", "kz"),
            Map.entry("Kenya", "ke"),
            Map.entry("Kiribati", "ki"),
            Map.entry("Kuwait", "kw"),
            Map.entry("Kyrgyzstan", "kg"),
            Map.entry("Laos", "la"),
            Map.entry("Latvia", "lv"),
            Map.entry("Lebanon", "lb"),
            Map.entry("Lesotho", "ls"),
            Map.entry("Liberia", "lr"),
            Map.entry("Libya", "ly"),
            Map.entry("Liechtenstein", "li"),
            Map.entry("Lithuania", "lt"),
            Map.entry("Luxembourg", "lu"),
            Map.entry("Madagascar", "mg"),
            Map.entry("Malawi", "mw"),
            Map.entry("Malaysia", "my"),
            Map.entry("Maldives", "mv"),
            Map.entry("Mali", "ml"),
            Map.entry("Malta", "mt"),
            Map.entry("Marshall Islands", "mh"),
            Map.entry("Mauritania", "mr"),
            Map.entry("Mauritius", "mu"),
            Map.entry("Mexico", "mx"),
            Map.entry("Micronesia", "fm"),
            Map.entry("Moldova", "md"),
            Map.entry("Monaco", "mc"),
            Map.entry("Mongolia", "mn"),
            Map.entry("Montenegro", "me"),
            Map.entry("Morocco", "ma"),
            Map.entry("Mozambique", "mz"),
            Map.entry("Myanmar", "mm"),
            Map.entry("Namibia", "na"),
            Map.entry("Nauru", "nr"),
            Map.entry("Nepal", "np"),
            Map.entry("Netherlands", "nl"),
            Map.entry("New Zealand", "nz"),
            Map.entry("Nicaragua", "ni"),
            Map.entry("Niger", "ne"),
            Map.entry("Nigeria", "ng"),
            Map.entry("North Korea", "kp"),
            Map.entry("North Macedonia", "mk"),
            Map.entry("Norway", "no"),
            Map.entry("Oman", "om"),
            Map.entry("Pakistan", "pk"),
            Map.entry("Palau", "pw"),
            Map.entry("Palestine", "ps"),
            Map.entry("Panama", "pa"),
            Map.entry("Papua New Guinea", "pg"),
            Map.entry("Paraguay", "py"),
            Map.entry("Peru", "pe"),
            Map.entry("Philippines", "ph"),
            Map.entry("Poland", "pl"),
            Map.entry("Portugal", "pt"),
            Map.entry("Qatar", "qa"),
            Map.entry("Romania", "ro"),
            Map.entry("Russia", "ru"),
            Map.entry("Rwanda", "rw"),
            Map.entry("Saint Kitts and Nevis", "kn"),
            Map.entry("Saint Lucia", "lc"),
            Map.entry("Saint Vincent and the Grenadines", "vc"),
            Map.entry("Samoa", "ws"),
            Map.entry("San Marino", "sm"),
            Map.entry("Sao Tome and Principe", "st"),
            Map.entry("Saudi Arabia", "sa"),
            Map.entry("Senegal", "sn"),
            Map.entry("Serbia", "rs"),
            Map.entry("Seychelles", "sc"),
            Map.entry("Sierra Leone", "sl"),
            Map.entry("Singapore", "sg"),
            Map.entry("Slovakia", "sk"),
            Map.entry("Slovenia", "si"),
            Map.entry("Solomon Islands", "sb"),
            Map.entry("Somalia", "so"),
            Map.entry("South Africa", "za"),
            Map.entry("South Korea", "kr"),
            Map.entry("South Sudan", "ss"),
            Map.entry("Spain", "es"),
            Map.entry("Sri Lanka", "lk"),
            Map.entry("Sudan", "sd"),
            Map.entry("Suriname", "sr"),
            Map.entry("Sweden", "se"),
            Map.entry("Switzerland", "ch"),
            Map.entry("Syria", "sy"),
            Map.entry("Taiwan", "tw"),
            Map.entry("Tajikistan", "tj"),
            Map.entry("Tanzania", "tz"),
            Map.entry("Thailand", "th"),
            Map.entry("Timor-Leste", "tl"),
            Map.entry("Togo", "tg"),
            Map.entry("Tonga", "to"),
            Map.entry("Trinidad and Tobago", "tt"),
            Map.entry("Tunisia", "tn"),
            Map.entry("Turkey", "tr"),
            Map.entry("Turkmenistan", "tm"),
            Map.entry("Tuvalu", "tv"),
            Map.entry("Uganda", "ug"),
            Map.entry("Ukraine", "ua"),
            Map.entry("United Arab Emirates", "ae"),
            Map.entry("United Kingdom", "gb"),
            Map.entry("United States", "us"),
            Map.entry("Uruguay", "uy"),
            Map.entry("Uzbekistan", "uz"),
            Map.entry("Vanuatu", "vu"),
            Map.entry("Vatican City", "va"),
            Map.entry("Venezuela", "ve"),
            Map.entry("Vietnam", "vn"),
            Map.entry("Yemen", "ye"),
            Map.entry("Zambia", "zm"),
            Map.entry("Zimbabwe", "zw")
    );
}