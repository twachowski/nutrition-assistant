package pl.polsl.wachowski.nutritionassistant.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;

@Getter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private final UserEntity user;

    private final String url;

    public RegistrationCompleteEvent(final UserEntity user, final String url) {
        super(user);
        this.user = user;
        this.url = url;
    }

}
