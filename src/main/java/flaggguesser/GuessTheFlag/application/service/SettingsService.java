package flaggguesser.GuessTheFlag.application.service;

import flaggguesser.GuessTheFlag.domain.ports.in.SettingsUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class SettingsService implements SettingsUseCase {

    @Value("${settings.defaultMode}")
    private String defaultMode;

    public SettingsService() {
    }

    @Override
    public String getDefaultMode() {
        return defaultMode;
    }

    @Override
    public void setDefaultMode(String mode) {
        this.defaultMode = mode;
    }

    public boolean isDarkMode() {
        return "dark".equalsIgnoreCase(this.defaultMode);
    }

}