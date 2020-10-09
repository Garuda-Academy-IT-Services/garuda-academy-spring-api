package eu.garudaacademy.api.models.entity.responses;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryWithPurchasesResponse {

    private long id;
    private String name;
    private String description;
    private String url;
    private Boolean isCommercial;
    private Boolean isPurchased;
}
