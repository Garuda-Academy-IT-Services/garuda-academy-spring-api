package eu.garudaacademy.api.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "purchase")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    public Purchase(final User user, final Category category) {
        super();
        this.user = user;
        this.category = category;
    }
}
