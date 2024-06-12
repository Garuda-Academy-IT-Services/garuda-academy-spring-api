package eu.garudaacademy.api.models.responses;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private String errorName;
    private String message;
    private int status;
}
