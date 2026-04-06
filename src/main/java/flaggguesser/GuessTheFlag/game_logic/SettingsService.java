package flaggguesser.GuessTheFlag.game_logic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class SettingsService {

    private String defaultMode;

    public SettingsService(@Value("${settings.defaultMode}") String defaultMode) {

    }

    public String getDefaultMode() {
        return this.defaultMode;
    }

    public boolean isDarkMode() {
        return "dark".equalsIgnoreCase(this.defaultMode);
    }

}