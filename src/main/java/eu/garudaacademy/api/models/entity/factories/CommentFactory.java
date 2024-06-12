package eu.garudaacademy.api.models.entity.factories;

import eu.garudaacademy.api.models.entity.Comment;
import eu.garudaacademy.api.models.entity.User;
import eu.garudaacademy.api.models.requests.CommentCreationRequest;
import org.springframework.stereotype.Service;

@Service
public class CommentFactory {

    public Comment buildComment(CommentCreationRequest commentCreationRequest, User loggedInUser) {
        Comment comment = new Comment();

        comment.setUser(loggedInUser);
        comment.setComment(commentCreationRequest.getComment());
        comment.setCommentLike(0);

        return comment;
    }
}
