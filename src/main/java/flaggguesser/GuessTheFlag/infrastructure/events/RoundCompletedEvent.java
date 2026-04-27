package flaggguesser.GuessTheFlag.infrastructure.events;


import org.springframework.context.ApplicationEvent;

public class RoundCompletedEvent extends ApplicationEvent {

    private final String username;
    private final int score;

    public RoundCompletedEvent(Object source, String username, int score) {
        super(source);
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}

