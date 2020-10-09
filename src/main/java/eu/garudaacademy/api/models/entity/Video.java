package eu.garudaacademy.api.models.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
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

    public Video(final String name, final String description, final String url, final int userLike) {
        super();
        this.name = name;
        this.description = description;
        this.url = url;
        this.userLike = userLike;
    }
}
