package eu.garudaacademy.api.controllers.services;

import eu.garudaacademy.api.models.authentication.LoggedInUser;
import eu.garudaacademy.api.models.entity.Comment;
import eu.garudaacademy.api.models.entity.Video;
import eu.garudaacademy.api.models.entity.factories.CommentFactory;
import eu.garudaacademy.api.models.exception.ResourceNotFoundException;
import eu.garudaacademy.api.models.requests.CommentCreationRequest;
import eu.garudaacademy.api.repository.CommentRepository;
import eu.garudaacademy.api.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentFactory commentFactory;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VideoRepository videoRepository;

    public Video addCommentToVideo(CommentCreationRequest commentCreationRequest) {
        final LoggedInUser loggedInUser = (LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Optional<Video> videoToComment = videoRepository.findById(commentCreationRequest.getVideoId());

        if (videoToComment.isPresent()) {
            final Video presentVideoToComment = videoToComment.get();
            Comment newComment = commentFactory.buildComment(commentCreationRequest, loggedInUser.getUserEntity());

            presentVideoToComment.getComments().add(newComment);

            return videoRepository.save(presentVideoToComment);
        }

        throw new ResourceNotFoundException("Video not found with this id: " + commentCreationRequest.getVideoId());
    }
}
