package eu.garudaacademy.api.models.entity.factories;

import eu.garudaacademy.api.models.entity.Role;
import eu.garudaacademy.api.models.entity.User;
import eu.garudaacademy.api.models.requests.UserCreationRequest;
import eu.garudaacademy.api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class UserFactory {

    private final BCryptPasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();

    @Autowired
    private RoleRepository roleRepository;

    public User buildUser(final UserCreationRequest userCreationRequest) {
        final User user = new User();
        final Role customerRole = roleRepository.findById(2).orElseGet(null);

        user.setUsername(userCreationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));
        user.setEmail(userCreationRequest.getEmail());
        user.setRole(customerRole);

        return user;
    }
}
