package db.FollowedArtists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling HTTP requests related to followed artists.
 */
@RestController
public class FollowedArtistsController {

    private final FollowedArtistsRepository followedArtistsRepository;

    /**
     * Constructs a new FollowedArtistsController with the given repository.
     * 
     * @param followedArtistsRepository The repository for followed artists.
     */
    @Autowired
    public FollowedArtistsController(FollowedArtistsRepository followedArtistsRepository) {
        this.followedArtistsRepository = followedArtistsRepository;
    }

    /**
     * Retrieves the followed artists for a user based on their Spotify identifier.
     * 
     * @param spotify The Spotify identifier of the user.
     * @return The FollowedArtists entity associated with the user.
     */
    @GetMapping(path = "/users/{spotify}/followedartists")
    public FollowedArtists getUserFollowedArtists(@PathVariable String spotify) {
        return followedArtistsRepository.findBySpotify(spotify);
    }

    /**
     * Updates the followed artists for a user based on their Spotify identifier.
     * 
     * @param spotify      The Spotify identifier of the user.
     * @param
