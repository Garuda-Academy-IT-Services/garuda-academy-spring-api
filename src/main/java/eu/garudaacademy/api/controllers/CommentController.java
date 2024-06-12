package eu.garudaacademy.api.controllers;

import eu.garudaacademy.api.controllers.services.CommentService;
import eu.garudaacademy.api.models.constants.ApiPaths;
import eu.garudaacademy.api.models.entity.Comment;
import eu.garudaacademy.api.models.entity.Video;
import eu.garudaacademy.api.models.requests.CommentCreationRequest;
import eu.garudaacademy.api.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(ApiPaths.COMMENTS_BASE)
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository repository;

    @RequestMapping(
        value = ApiPaths.CREATE,
        produces = "application/json",
        method = {RequestMethod.POST})
    public Video createComment(@RequestBody CommentCreationRequest commentCreationRequest) {
        return commentService.addCommentToVideo(commentCreationRequest);
    }

    // /get-video/12
    @RequestMapping(value = "/get-all-by-video/{videoId}")
    public List<Comment> getAllByVideo(@PathVariable int videoId) {
        return repository.findByVideoContaining(videoId);
    }

}
