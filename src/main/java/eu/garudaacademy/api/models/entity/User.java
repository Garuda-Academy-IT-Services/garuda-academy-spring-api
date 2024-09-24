package eu.garudaacademy.api.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    @Column
    @JsonIgnore
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    private String pictureUrl;

    @ManyToOne
    @JoinColumn(name = "role_id", columnDefinition = "int default 2")
    private Role role;

    public User(final String username, final String password, final String email) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
