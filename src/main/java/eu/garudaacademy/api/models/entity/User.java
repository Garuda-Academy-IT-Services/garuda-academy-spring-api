package eu.garudaacademy.api.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "email")
    private String email;

    public User(final String username, final String password, final String email) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
