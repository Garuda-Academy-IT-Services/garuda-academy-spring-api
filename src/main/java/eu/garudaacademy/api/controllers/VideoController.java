package eu.garudaacademy.api.controllers;

import eu.garudaacademy.api.models.entity.responses.factory.VideoCreateResponseFactory;
import eu.garudaacademy.api.models.entity.Purchase;
import eu.garudaacademy.api.models.entity.Video;
import eu.garudaacademy.api.models.entity.responses.VideoCreateResponse;
import eu.garudaacademy.api.models.exception.ResourceNotFoundException;
import eu.garudaacademy.api.repository.PurchaseRepository;
import eu.garudaacademy.api.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private VideoCreateResponseFactory videoCreateResponseFactory;

    @GetMapping("/get-all")
    public List<Video> getAll() {
        return this.videoRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public Video getById(@PathVariable(value = "id") final long videoId) {
        return this.videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found: " + videoId));
    }

    @GetMapping("/get-by-category/{categoryId}")
    public List<Video> getByCategory(
            @PathVariable(value = "categoryId") final long categoryId) {

        List<Video> videos = this.videoRepository.findByCategoryId(categoryId);

        return videos;
    }

    @RequestMapping(
        value = "/create",
        produces = "application/json",
        method = {RequestMethod.POST})
    public VideoCreateResponse create(@RequestBody final Video video) {
        final Video saveResponse = this.videoRepository.save(video);

        return videoCreateResponseFactory.build(saveResponse);
    }

    @PutMapping("/update/{id}")
    public Video updateUser(
            @RequestBody final Video video,
            @PathVariable(value = "id") final long videoId) {

        final Video current = this.videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + videoId));
        current.setName(video.getName());
        current.setDescription(video.getDescription());
        current.setUrl(video.getUrl());
        current.setUserLike(video.getUserLike());

        return this.videoRepository.save(current);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Video> delete(@PathVariable(value = "id") final long videoId) {

        final Video current = this.videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + videoId));
        this.videoRepository.delete(current);

        return ResponseEntity.ok().build();
    }
}
