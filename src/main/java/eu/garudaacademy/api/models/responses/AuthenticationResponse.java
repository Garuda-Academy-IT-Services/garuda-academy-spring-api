package eu.garudaacademy.api.models.responses;

import eu.garudaacademy.api.models.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {

    private final String jwt;
    private final User user;
}
