package pl.polsl.wachowski.nutritionassistant.service;

public class MockAuthenticationService implements AuthenticationService {

    private String authenticatedUserEmail;

    @Override
    public String authenticate(final String userEmail, final String password) {
        this.authenticatedUserEmail = userEmail;
        return String.join(":", userEmail, password);
    }

    @Override
    public String getAuthenticatedUserEmail() {
        return authenticatedUserEmail;
    }

}
