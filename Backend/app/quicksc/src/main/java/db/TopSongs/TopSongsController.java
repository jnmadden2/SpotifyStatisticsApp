package db.TopSongs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopSongsController 
{
    @Autowired
    private final TopSongsRepository top_songs_repository;

    private String success = "{\"message\":\"[+] success\"}";
    private String failure = "{\"message\":\"[-] failure\"}";

    public TopSongsController(TopSongsRepository top_songs_repository)
    {
        this.top_songs_repository = top_songs_repository;

    }

    
    /** 
     * @param spotify
     * @return TopSongs
     */
    @GetMapping(path = "/users/{spotify}/topsongs")
    TopSongs getUserTopSongs(@PathVariable String spotify)
    {
        return this.top_songs_repository.findBySpotify(spotify);

    }
    
    @PostMapping(path = "/users/update/{spotify}/topsongs")
    void updateUserTopSongs(@PathVariable String spotify, @RequestBody TopSongs updated_user)
    {
        this.top_songs_repository.deleteBySpotify(spotify);
        this.top_songs_repository.save(updated_user);

    }

}
