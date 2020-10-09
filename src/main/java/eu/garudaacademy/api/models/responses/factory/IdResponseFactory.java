package eu.garudaacademy.api.models.responses.factory;

import eu.garudaacademy.api.models.entity.Category;
import eu.garudaacademy.api.models.entity.User;
import eu.garudaacademy.api.models.entity.Video;
import eu.garudaacademy.api.models.responses.IdResponse;
import org.springframework.stereotype.Service;

@Service
public class IdResponseFactory {

    public IdResponse build(final Category rawCategory) {
        return IdResponse.builder()
                .id(rawCategory.getId())
                .build();
    }

    public IdResponse build(final User rawUser) {
        return IdResponse.builder()
                .id(rawUser.getId())
                .build();
    }

    public IdResponse build(final Video rawVideo) {
        return IdResponse.builder()
                .id(rawVideo.getId())
                .build();
    }
}
