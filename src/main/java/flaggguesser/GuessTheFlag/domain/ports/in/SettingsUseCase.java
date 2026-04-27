package flaggguesser.GuessTheFlag.domain.ports.in;

public interface SettingsUseCase {
        boolean isDarkMode();
        String getDefaultMode();
        void setDefaultMode(String mode);
}
