package db.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller class for handling API requests related to Users.
 */
@RestController
public class UserController {

    private final UserRepository userRepository;

    private String success = "{\"message\":\"[+] success\"}";
    private String failure = "{\"message\":\"[-] failure\"}";

    /**
     * Constructor for UserController that injects the UserRepository.
     *
     * @param userRepository The repository handling User entities.
     */
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a list of all users.
     *
     * @return A list of User entities.
     */
    @GetMapping(path = "/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their Spotify ID.
     *
     * @param spotify The Spotify ID of the user.
     * @return The User entity if found.
     */
    @GetMapping(path = "/users/{spotify}")
    public User getUserBySpotify(@PathVariable String spotify) {
        return userRepository.findBySpotify(spotify);
    }

    /**
     * Creates a new user in the system.
     *
     * @param user The User entity to be created.
     * @return A success message.
     */
    @PostMapping(path = "/users/create")
    public String createUser(@RequestBody User user) {
        userRepository.save(user);
        return success;
    }

    /**
     * Updates an existing user identified by their Spotify ID.
     *
     * @param spotify     The Spotify ID of the user to update.
     * @param updatedUser The updated User entity.
     */
    @PostMapping("/users/update/{spotify}")
    public void updateUser(@PathVariable String spotify, @RequestBody User updatedUser) {
        userRepository.deleteBySpotify(spotify);
        userRepository.save(updatedUser);
    }

   
