package pl.polsl.wachowski.nutritionassistant.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.polsl.wachowski.nutritionassistant.db.user.User;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private final String userName;
    private final String password;
    private final boolean isEnabled;

    UserPrincipal(final User user) {
        this.userName = user.getEmail();
        this.password = user.getUserCredentials().getPassword();
        this.isEnabled = user.isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}
