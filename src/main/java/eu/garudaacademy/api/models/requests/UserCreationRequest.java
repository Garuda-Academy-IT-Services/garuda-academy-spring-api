package eu.garudaacademy.api.models.requests;

import eu.garudaacademy.api.validators.Password;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserCreationRequest {

    @NotNull
    @NotEmpty
    @Size(min = 2)
    private String username;

    @NotNull
    @NotEmpty
    @Password
    @Size(min = 8)
    private String password;

    @NotNull
    @NotEmpty
    @Email
    private String email;

}
