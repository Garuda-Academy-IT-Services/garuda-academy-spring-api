package eu.garudaacademy.api.models.responses.factory;

import eu.garudaacademy.api.models.entity.Video;
import eu.garudaacademy.api.models.responses.VideoCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoCreateResponseFactory {

    @Autowired
    private IdResponseFactory idResponseFactory;

    public VideoCreateResponse build(final Video rawVideo) {
        return VideoCreateResponse.builder()
                .id(rawVideo.getId())
                .name(rawVideo.getName())
                .description(rawVideo.getDescription())
                .url(rawVideo.getUrl())
                .userLike(rawVideo.getUserLike())
                .category(idResponseFactory.build(rawVideo.getCategory()))
                .build();
    }
}
