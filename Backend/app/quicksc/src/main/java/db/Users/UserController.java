package db.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController
{
    @Autowired
    private final UserRepository user_repository;

    private String success = "{\"message\":\"[+] success\"}";
    private String failure = "{\"message\":\"[-] failure\"}";

    public UserController(UserRepository user_repository)
    {
        this.user_repository = user_repository;

    }

    
    /** 
     * @return List<User>
     */
    @GetMapping(path = "/users")
    List<User> getAllUsers()
    {
        return this.user_repository.findAll();

    }
    @GetMapping(path = "/users/{spotify}")
    User getUserByID(@PathVariable String spotify)
    {
        return this.user_repository.findBySpotify(spotify);

    }
    @PostMapping(path = "/users/create")
    String createUser(@RequestBody User user)
    {
        this.user_repository.save(user);
        return success;

    }
    @PostMapping("/users/update/{spotify}")
    void updateUser(@PathVariable String spotify, @RequestBody User updated_user)
    {
        this.user_repository.deleteBySpotify(spotify);
        this.user_repository.save(updated_user);

    }
    @DeleteMapping(path = "/users/{spotify}")
    String deleteUser(@PathVariable String spotify)
    {
        this.user_repository.deleteBySpotify(spotify);
        return success;

    }

}
