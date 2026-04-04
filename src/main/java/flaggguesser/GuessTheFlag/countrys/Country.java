package flaggguesser.GuessTheFlag.countrys;

public record Country(String name, String isoCode) {

    public String getFlagUrl() {
        return "https://flagcdn.com/w320/" + isoCode + ".png";
    }
}
