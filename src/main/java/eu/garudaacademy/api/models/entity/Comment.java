package eu.garudaacademy.api.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private String comment;

    @Column
    private int commentLike;

    public Comment(final User user, final String comment, final int commentLike) {
        super();
        this.user = user;
        this.comment = comment;
        this.commentLike = commentLike;
    }
}
