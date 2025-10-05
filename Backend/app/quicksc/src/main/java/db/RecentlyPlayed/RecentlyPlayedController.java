package db.RecentlyPlayed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecentlyPlayedController
{
    @Autowired
    private final RecentlyPlayedRepository recently_played_repository;

    private String success = "{\"message\":\"[+] success\"}";
    private String failure = "{\"message\":\"[-] failure\"}";

    public RecentlyPlayedController(RecentlyPlayedRepository recently_played_repository)
    {
        this.recently_played_repository = recently_played_repository;

    }

    
    /** 
     * @param spotify
     * @return RecentlyPlayed
     */
    @GetMapping(path = "/users/{spotify}/recentlyplayed")
    RecentlyPlayed getUserRecentlyPlayed(@PathVariable String spotify)
    {
        return this.recently_played_repository.findBySpotify(spotify);

    }
    
    @PostMapping(path = "/users/update/{spotify}/recentlyplayed")
    void updateUserRecentlyPlayed(@PathVariable String spotify, @RequestBody RecentlyPlayed updated_user)
    {
        this.recently_played_repository.deleteBySpotify(spotify);
        this.recently_played_repository.save(updated_user);

    }

}
