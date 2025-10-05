package db.MinutesListened;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MinutesListenedController
{
    @Autowired
    private final MinutesListenedRepository minutes_listened_repository;

    private String success = "{\"message\":\"[+] success\"}";
    private String failure = "{\"message\":\"[-] failure\"}";

    public MinutesListenedController(MinutesListenedRepository minutes_listened_repository)
    {
        this.minutes_listened_repository = minutes_listened_repository;

    }

    @GetMapping(path = "/users/{spotify}/minutes")
    MinutesListened getUserMinutesListened(@PathVariable String spotify)
    {
        return this.minutes_listened_repository.findBySpotify(spotify);

    }
    
    @PostMapping(path = "/users/update/{spotify}/minutes")
    void updateUserMinutesListened(@PathVariable String spotify, @RequestBody MinutesListened updated_user)
    {
        this.minutes_listened_repository.deleteBySpotify(spotify);
        this.minutes_listened_repository.save(updated_user);

    }

}
