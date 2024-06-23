package eu.garudaacademy.api.repository;

import eu.garudaacademy.api.models.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByCategoryId(long categoryId);
    List<Video> findByCategoryIdOrderByNameAsc(long categoryId);
}
