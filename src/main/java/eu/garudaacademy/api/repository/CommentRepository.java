package eu.garudaacademy.api.repository;

import eu.garudaacademy.api.models.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT comment FROM Video video INNER JOIN video.comments comment WHERE video.id = :videoId ")
    List<Comment> findByVideoContaining(@Param("videoId") long videoId);
}
