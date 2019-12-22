package pl.polsl.wachowski.nutritionassistant.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

@Getter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private final User user;

    private final String url;

    public RegistrationCompleteEvent(final User user, final String url) {
        super(user);
        this.user = user;
        this.url = url;
    }

}
