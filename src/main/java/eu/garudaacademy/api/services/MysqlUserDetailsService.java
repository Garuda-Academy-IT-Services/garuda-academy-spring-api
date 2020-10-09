package eu.garudaacademy.api.services;

import eu.garudaacademy.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MysqlUserDetailsService implements UserDetailsService {

     @Autowired
     private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final List<eu.garudaacademy.api.models.entity.User> userEntities = userRepository.findByUsername(username);

        if (userEntities.size() > 0) {
            eu.garudaacademy.api.models.entity.User userEntity = userEntities.get(0);
            return new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
        }

        return new User(null, null, new ArrayList<>());
    }
}
