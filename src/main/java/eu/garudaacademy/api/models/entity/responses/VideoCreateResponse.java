package eu.garudaacademy.api.models.entity.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VideoCreateResponse {
    private long id;
    private String name;
    private String description;
    private String url;
    private int userLike;
    private IdResponse category;
}
