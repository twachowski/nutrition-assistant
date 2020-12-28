package pl.polsl.wachowski.nutritionassistant.service;

public interface AuthenticationService {

    String authenticate(String userEmail, String password);

    String getAuthenticatedUserEmail();

}
