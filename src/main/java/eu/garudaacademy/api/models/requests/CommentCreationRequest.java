package eu.garudaacademy.api.models.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentCreationRequest {
    // {comment: "valami sziövveg", videoId: 12}
    private String comment;
    private long videoId;
}
