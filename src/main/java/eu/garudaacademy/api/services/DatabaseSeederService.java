package eu.garudaacademy.api.services;

import eu.garudaacademy.api.models.entity.Category;
import eu.garudaacademy.api.models.entity.Video;
import eu.garudaacademy.api.repository.CategoryRepository;
import eu.garudaacademy.api.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DatabaseSeederService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VideoRepository videoRepository;

    @PostConstruct
    public void seedDb() {
        // seedCategories();
    }

    private void seedCategories() {
        Category category = new Category();
        category.setId(1);
        category.setName("Scratch");
        category.setUrl("scratch");
        category.setIsCommercial(false);

        Category category1 = new Category();
        category1.setId(2);
        category1.setName("Python");
        category1.setUrl("python");
        category1.setIsCommercial(false);

        Category category2 = new Category();
        category2.setId(3);
        category2.setName("Java");
        category2.setUrl("java");
        category2.setIsCommercial(false);

        Category category3 = new Category();
        category3.setId(4);
        category3.setName("HTML");
        category3.setUrl("html");
        category3.setIsCommercial(false);

        Category category4 = new Category();
        category4.setId(5);
        category4.setName("Java");
        category4.setUrl("java");
        category4.setIsCommercial(false);

        categoryRepository.save(category);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);

        seedJavaVideos(category4);
    }

    private void seedJavaVideos(Category category) {
        Video video = new Video();
        video.setId(1);
        video.setCategory(category);
        video.setUrl("uDxoxxWABnQ");
        video.setName("SQL alapok!");
        video.setUserLike(0);

        videoRepository.save(video);
    }
}
