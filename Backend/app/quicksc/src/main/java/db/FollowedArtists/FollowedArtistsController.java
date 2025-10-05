package db.FollowedArtists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FollowedArtistsController
{
    @Autowired
    private final FollowedArtistsRepository followed_artists_repository;

    private String success = "{\"message\":\"[+] success\"}";
    private String failure = "{\"message\":\"[-] failure\"}";

    public FollowedArtistsController(FollowedArtistsRepository followed_artists_repository)
    {
        this.followed_artists_repository = followed_artists_repository;

    }

    
    /** 
     * @param spotify
     * @return FollowedArtists
     */
    @GetMapping(path = "/users/{spotify}/followedartists")
    FollowedArtists getUserFollowedArtists(@PathVariable String spotify)
    {
        return this.followed_artists_repository.findBySpotify(spotify);

    }
    /** 
     * @param spotify
     * @param updated_user
     */
    @PostMapping(path = "/users/update/{spotify}/followedartists")
    void updateUserFollowedArtists(@PathVariable String spotify, @RequestBody FollowedArtists updated_user)
    {
        this.followed_artists_repository.deleteBySpotify(spotify);
        this.followed_artists_repository.save(updated_user);

    }

}
