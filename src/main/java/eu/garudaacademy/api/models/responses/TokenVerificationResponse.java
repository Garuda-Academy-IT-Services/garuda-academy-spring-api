package eu.garudaacademy.api.models.responses;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenVerificationResponse {
    private final boolean isValid;
}
