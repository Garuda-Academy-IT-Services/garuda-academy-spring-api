package eu.garudaacademy.api.config.filters;

import eu.garudaacademy.api.services.MysqlUserDetailsService;
import eu.garudaacademy.api.services.JwtToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static String USER_CREDENTIALS_TOKEN_KEY = "jwt";
    private static String USER_CREDENTIALS_USERNAME_KEY = "username";

    @Autowired
    private MysqlUserDetailsService userDetailsService;

    @Autowired
    private JwtToolService jwtToolService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final Map<String, String> userCredentials = getUserCredentialsFromToken(authorizationHeader);

        validateToken(userCredentials, filterChain, request, response);
    }

    private Map<String, String> getUserCredentialsFromToken(final String authorizationHeader) {
        final Map<String, String> userCredentials = new HashMap<>();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            userCredentials.put(USER_CREDENTIALS_TOKEN_KEY, authorizationHeader.substring(7));
            userCredentials.put(
                    USER_CREDENTIALS_USERNAME_KEY,
                    jwtToolService.extractUsername(userCredentials.get("jwt")));
        }

        return userCredentials;
    }

    private void validateToken(
            final Map<String, String> userCredentials,
            final FilterChain filterChain,
            final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException, IOException {

        if (userCredentials.get(USER_CREDENTIALS_USERNAME_KEY) != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            final UserDetails userDetails
                    = this.userDetailsService.loadUserByUsername(userCredentials.get(USER_CREDENTIALS_USERNAME_KEY));

            if (jwtToolService.validateToken(userCredentials.get(USER_CREDENTIALS_TOKEN_KEY), userDetails)) {
                setToken(userDetails, request);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setToken(final UserDetails userDetails, final HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
