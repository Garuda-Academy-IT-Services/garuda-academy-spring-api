package eu.garudaacademy.api.models.entity.factories;

import eu.garudaacademy.api.models.entity.User;
import eu.garudaacademy.api.models.requests.UserCreationRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserFactory {

    private final BCryptPasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();

    public User buildUser(final UserCreationRequest userCreationRequest) {
        final User user = new User();

        user.setUsername(userCreationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));
        user.setEmail(userCreationRequest.getEmail());

        return user;
    }
}
