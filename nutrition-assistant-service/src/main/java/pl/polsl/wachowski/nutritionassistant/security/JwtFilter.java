package pl.polsl.wachowski.nutritionassistant.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String JWT_PREFIX = "Bearer ";

    private final UserDetailsService userDetailsService;
    private final JwtHelper jwtHelper;

    @Autowired
    public JwtFilter(final UserDetailsService userDetailsService, final JwtHelper jwtHelper) {
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest,
                                    @NotNull final HttpServletResponse httpServletResponse,
                                    @NotNull final FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = httpServletRequest.getHeader(AUTH_HEADER);

        if (authHeader != null && authHeader.startsWith(JWT_PREFIX)) {
            final String jwtToken = authHeader.substring(JWT_PREFIX.length());
            final String user = JwtHelper.extractUserFrom(jwtToken);
            if (jwtHelper.isValid(jwtToken) && userExists(user)) {
                final WebAuthenticationDetails details =
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest);
                putTokenInContext(user, details);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean userExists(final String user) {
        try {
            userDetailsService.loadUserByUsername(user);
            return true;
        } catch (final UsernameNotFoundException ex) {
            return false;
        }
    }

    private static void putTokenInContext(final String user, final WebAuthenticationDetails details) {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext.getAuthentication() == null) {
            final UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, null);
            authenticationToken.setDetails(details);
            securityContext.setAuthentication(authenticationToken);
        }
    }

}
