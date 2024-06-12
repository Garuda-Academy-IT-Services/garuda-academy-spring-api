package eu.garudaacademy.api.services;

import eu.garudaacademy.api.models.authentication.LoggedInUser;
import eu.garudaacademy.api.models.entity.User;
import eu.garudaacademy.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MysqlUserDetailsService implements UserDetailsService {

     @Autowired
     private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final List<User> userEntities = userRepository.findByUsername(username);

        if (userEntities.size() > 0) {
            User userEntity = userEntities.get(0);

            return LoggedInUser.builder()
                    .user(userEntity)
                    .build();
        }

        throw new UsernameNotFoundException("We couldn't log you in with the provided credentials!");
    }
}
