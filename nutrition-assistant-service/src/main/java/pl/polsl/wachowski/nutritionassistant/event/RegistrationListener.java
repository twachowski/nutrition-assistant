package pl.polsl.wachowski.nutritionassistant.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.service.UserService;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationListener {

    private static final String TITLE = "Nutrition Assistant account confirmation";
    private static final String CONTENT = "Thank you for your registration at Nutrition Assistant! " +
            "Click the link below in order to activate your account:\n" +
            "%s/registration/confirm/%s";

    private final UserService userService;
    private final JavaMailSender mailSender;

    @Autowired
    public RegistrationListener(final UserService userService, final JavaMailSender mailSender) {
        this.userService = userService;
        this.mailSender = mailSender;
    }

    @Async
    @EventListener
    public void handleRegistrationCompleteEvent(final RegistrationCompleteEvent registrationCompleteEvent) {
        final UserEntity user = registrationCompleteEvent.getUser();
        log.info("New user {} has registered - generating verification token...", user.getEmail());
        final String token = createVerificationToken(user);
        log.info("Sending confirmation email to {}", user.getEmail());
        sendEmail(user.getEmail(), registrationCompleteEvent.getUrl(), token);
    }

    private String createVerificationToken(final UserEntity user) {
        final String token = UUID.randomUUID().toString();
        userService.createVerificationToken(token, user);
        return token;
    }

    private void sendEmail(final String userEmail, final String url, final String token) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject(TITLE);
        final String messageContent = String.format(CONTENT, url, token);
        message.setText(messageContent);

        mailSender.send(message);
    }

}
