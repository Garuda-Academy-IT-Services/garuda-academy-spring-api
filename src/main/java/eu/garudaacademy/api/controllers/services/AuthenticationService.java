package eu.garudaacademy.api.controllers.services;

import eu.garudaacademy.api.models.requests.TokenVerificationRequest;
import eu.garudaacademy.api.models.responses.TokenVerificationResponse;
import eu.garudaacademy.api.services.JwtToolService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private JwtToolService jwtToolService;

    public TokenVerificationResponse verifyToken(TokenVerificationRequest request) {
        try {
            return TokenVerificationResponse.builder()
                    .isValid(!jwtToolService.isTokenExpired(request.getToken()))
                    .build();

        } catch (ExpiredJwtException e) {
            return TokenVerificationResponse.builder()
                    .isValid(false)
                    .build();
        }
    }
}
