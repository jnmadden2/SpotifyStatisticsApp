package db.TopArtists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TopArtistsController
{
    @Autowired
    private final TopArtistRepository top_artist_repository;

    private String success = "{\"message\":\"[+] success\"}";
    private String failure = "{\"message\":\"[-] failure\"}";

    public TopArtistsController(TopArtistRepository top_artist_repository)
    {
        this.top_artist_repository = top_artist_repository;

    }

    
    /** 
     * @param spotify
     * @return TopArtists
     */
    @GetMapping(path = "/users/{spotify}/topartists")
    TopArtists getUserTopArtists(@PathVariable String spotify)
    {
        return this.top_artist_repository.findBySpotify(spotify);

    }
    
    @PostMapping(path = "/users/update/{spotify}/topartists")
    void updateUserTopArtists(@PathVariable String spotify, @RequestBody TopArtists updated_user)
    {
        this.top_artist_repository.deleteBySpotify(spotify);
        this.top_artist_repository.save(updated_user);

    }

}
