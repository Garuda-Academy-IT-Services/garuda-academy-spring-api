package eu.garudaacademy.api.controllers;

import eu.garudaacademy.api.controllers.services.AuthenticationService;
import eu.garudaacademy.api.models.authentication.LoggedInUser;
import eu.garudaacademy.api.models.constants.ApiPaths;
import eu.garudaacademy.api.models.requests.AuthenticationRequest;
import eu.garudaacademy.api.models.requests.TokenVerificationRequest;
import eu.garudaacademy.api.models.responses.AuthenticationResponse;
import eu.garudaacademy.api.models.responses.TokenVerificationResponse;
import eu.garudaacademy.api.services.MysqlUserDetailsService;
import eu.garudaacademy.api.services.JwtToolService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.AUTHENTICATION_BASE)
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MysqlUserDetailsService mysqlUserDetailsService;

    @Autowired
    private JwtToolService jwtToolService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(
            value = ApiPaths.AUTHENTICATION_AUTHENTICATE,
            method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("incorrect username or password", e);
        }

        final UserDetails userDetails = mysqlUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtToolService.generateToken(userDetails);

        return ResponseEntity.ok(AuthenticationResponse.builder()
                .jwt(jwt)
                .user(((LoggedInUser) userDetails).getUserEntity())
                .build());
    }

    @RequestMapping(
            value = ApiPaths.VERIFY_TOKEN,
            method = RequestMethod.POST)
    public ResponseEntity<?> verifyAuthenticationToken(
            @RequestBody TokenVerificationRequest tokenVerificationRequest) {

        return ResponseEntity.ok(authenticationService.verifyToken(tokenVerificationRequest));
    }

}
