package flaggguesser.GuessTheFlag.adapter.in.ui;


import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.AppShellSettings;

public class AppShell implements AppShellConfigurator {

    @Override
    public void configurePage(AppShellSettings settings) {
        settings.addFavIcon("icon", "images/logo.png", "203x176");
        settings.setPageTitle("Guess The Flag");
        settings.addMetaTag("description", "Guess the flag of countries!");
        settings.addMetaTag("author", "Rafael");
        settings.addMetaTag("viewport", "width=device-width, initial-scale=1");
        settings.addMetaTag("theme-color", "#1a1a2e");
    }

}
