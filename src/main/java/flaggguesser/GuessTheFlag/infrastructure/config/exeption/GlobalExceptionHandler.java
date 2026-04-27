package flaggguesser.GuessTheFlag.infrastructure.config.exeption;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.Route;
import flaggguesser.GuessTheFlag.domain.exeptions.UserNameAlreadyExistsException;
import flaggguesser.GuessTheFlag.domain.exeptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletResponse;


@Route("error")
public class GlobalExceptionHandler extends VerticalLayout
        implements HasErrorParameter<Exception> {

    @Override
    public int setErrorParameter(BeforeEnterEvent event,
                                 ErrorParameter<Exception> parameter) {
        Exception exception = parameter.getException();

        if (exception instanceof UserNameAlreadyExistsException) {
            add(new H2("Username already taken!"));
            return HttpServletResponse.SC_CONFLICT;
        }

        if (exception instanceof IllegalArgumentException) {
            add(new H2("Invalid User Attributes"));
            return HttpServletResponse.SC_CONFLICT;
        }

        if (exception instanceof UserNotFoundException) {
            add(new H2("User not found!"));
            return HttpServletResponse.SC_NOT_FOUND;
        }

        add(new H2("Something went wrong!"));
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }
}
