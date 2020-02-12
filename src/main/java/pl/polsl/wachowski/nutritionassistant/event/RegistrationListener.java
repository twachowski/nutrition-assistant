package pl.polsl.wachowski.nutritionassistant.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.polsl.wachowski.nutritionassistant.db.user.User;
import pl.polsl.wachowski.nutritionassistant.service.UserService;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<RegistrationCompleteEvent> {

    private static final String TITLE = "Nutrition Assistant account confirmation";
    private static final String CONTENT =   "Thank you for your registration at Nutrition Assistant! " +
                                            "Click the link below in order to activate your account:\n" +
                                            "%s/registration/confirm/%s";

    private UserService userService;

    private JavaMailSender mailSender;

    @Autowired
    public RegistrationListener(final UserService userService, final JavaMailSender mailSender) {
        this.userService = userService;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(final RegistrationCompleteEvent registrationCompleteEvent) {
        final User user = registrationCompleteEvent.getUser();
        final String token = createVerificationToken(user);
        sendEmail(user.getEmail(), registrationCompleteEvent.getUrl(), token);
    }

    private String createVerificationToken(final User user) {
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
