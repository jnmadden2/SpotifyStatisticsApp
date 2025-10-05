package db.Radio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RadioController
{
    @Autowired
    private final RadioRepository radio_repository;

    private String success = "{\"message\":\"[+] success\"}";
    private String failure = "{\"message\":\"[-] failure\"}";

    public RadioController(RadioRepository radio_repository)
    {
        this.radio_repository = radio_repository;

    }

    @GetMapping(path = "/users/{spotify}/radio")
    Radio getUserRadio(@PathVariable String spotify)
    {
        return this.radio_repository.findBySpotify(spotify);

    }
    
    @PostMapping(path = "/users/update/{spotify}/radio")
    void updateUserRadio(@PathVariable String spotify, @RequestBody Radio updated_user)
    {
        this.radio_repository.deleteBySpotify(spotify);
        this.radio_repository.save(updated_user);

    }

}
