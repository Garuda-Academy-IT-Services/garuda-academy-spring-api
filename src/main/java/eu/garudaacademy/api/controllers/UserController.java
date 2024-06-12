package eu.garudaacademy.api.controllers;

import eu.garudaacademy.api.models.constants.ApiPaths;
import eu.garudaacademy.api.models.entity.User;
import eu.garudaacademy.api.models.entity.factories.UserFactory;
import eu.garudaacademy.api.models.exception.ResourceNotFoundException;
import eu.garudaacademy.api.models.exception.UsernameAlreadyExistsException;
import eu.garudaacademy.api.models.requests.UserCreationRequest;
import eu.garudaacademy.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.callback.PasswordValidationCallback;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@CrossOrigin
@RequestMapping(ApiPaths.USERS_BASE)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFactory userFactory;

    @GetMapping(ApiPaths.GET_ALL)
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    @GetMapping(ApiPaths.GET_BY_ID)
    public User getUserById(@PathVariable(value = "id") final long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
    }

    @RequestMapping(
        value = ApiPaths.CREATE,
        produces = "application/json",
        method = {RequestMethod.POST})
    public User create(@Valid @RequestBody final UserCreationRequest userCreationRequest)
            throws UsernameAlreadyExistsException {

        final User user = userFactory.buildUser(userCreationRequest);

        try {
            return this.userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExistsException("Ez a felhasználónév már foglalt! Kérlek válassz másikat!");
        }
    }

    @PutMapping(ApiPaths.UPDATE)
    public User updateUser(
            @RequestBody final User user,
            @PathVariable(value = "id") final long userId) {

        final User current = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        current.setUsername(user.getUsername());
        current.setPassword(user.getPassword());
        current.setEmail(user.getEmail());

        return this.userRepository.save(current);
    }

    @DeleteMapping(ApiPaths.DELETE)
    public ResponseEntity<User> deleteUser(
            @PathVariable(value = "id") final long userId) {

        final User current = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        this.userRepository.delete(current);

        return ResponseEntity.ok().build();
    }
}
