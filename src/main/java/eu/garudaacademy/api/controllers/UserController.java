package eu.garudaacademy.api.controllers;

import eu.garudaacademy.api.models.constants.ApiPaths;
import eu.garudaacademy.api.models.entity.User;
import eu.garudaacademy.api.models.exception.ResourceNotFoundException;
import eu.garudaacademy.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(ApiPaths.USERS_BASE)
public class UserController {

    @Autowired
    private UserRepository userRepository;

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
    public User create(@RequestBody final User user) {
        return this.userRepository.save(user);
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
