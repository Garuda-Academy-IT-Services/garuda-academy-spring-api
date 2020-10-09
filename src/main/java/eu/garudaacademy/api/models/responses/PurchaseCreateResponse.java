package eu.garudaacademy.api.models.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PurchaseCreateResponse {
    private long id;
    private IdResponse user;
    private IdResponse category;
}
