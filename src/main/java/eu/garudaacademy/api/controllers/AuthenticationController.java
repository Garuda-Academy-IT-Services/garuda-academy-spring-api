package eu.garudaacademy.api.controllers;

import eu.garudaacademy.api.models.constants.ApiPaths;
import eu.garudaacademy.api.models.requests.AuthenticationRequest;
import eu.garudaacademy.api.models.responses.AuthenticationResponse;
import eu.garudaacademy.api.services.MysqlUserDetailsService;
import eu.garudaacademy.api.services.JwtToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.AUTHENTICATION_BASE)
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MysqlUserDetailsService mysqlUserDetailsService;

    @Autowired
    private JwtToolService jwtToolService;

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

        return ResponseEntity.ok(new AuthenticationResponse(jwt));


    }

}
