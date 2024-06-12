package eu.garudaacademy.api.models.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "videos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String url;

    @Column(name = "user_like")
    private Integer userLike;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id")
    private List<Comment> comments;
}
