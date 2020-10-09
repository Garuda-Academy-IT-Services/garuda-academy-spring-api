package eu.garudaacademy.api.controllers;

import eu.garudaacademy.api.models.entity.User;
import eu.garudaacademy.api.models.exception.ResourceNotFoundException;
import eu.garudaacademy.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("get-all")
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    // get user by id

    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable(value = "id") final long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
    }
    @RequestMapping(
        value = "/create",
        produces = "application/json",
        method = {RequestMethod.POST})
    public User create(@RequestBody final User user) {
        return this.userRepository.save(user);
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(
            @PathVariable(value = "id") final long userId
    ) {

        final User current = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        this.userRepository.delete(current);

        return ResponseEntity.ok().build();
    }
}
