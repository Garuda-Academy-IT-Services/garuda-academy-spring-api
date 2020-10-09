package eu.garudaacademy.api.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String url;

    @Column(name = "is_commercial")
    private Boolean isCommercial;

    public Category(final String name, final String url) {
        super();
        this.name = name;
        this.url = url;
    }
}
