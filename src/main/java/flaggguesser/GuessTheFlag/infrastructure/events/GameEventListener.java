package flaggguesser.GuessTheFlag.infrastructure.events;


import flaggguesser.GuessTheFlag.domain.ports.in.UserUseCase;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GameEventListener {

    private final UserUseCase userUseCase;

    public GameEventListener(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @EventListener
    public void onGameCompleted(RoundCompletedEvent event) {
        userUseCase.updateScore(event.getUsername(), event.getScore());
    }
}

