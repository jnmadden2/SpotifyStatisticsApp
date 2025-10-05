package db.RecentlyPlayed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling HTTP requests related to recently played items.
 */
@RestController
public class RecentlyPlayedController {

    private final RecentlyPlayedRepository recentlyPlayedRepository;

    /**
     * Constructs a new RecentlyPlayedController with the given repository.
     * 
     * @param recentlyPlayedRepository The repository for recently played items.
     */
    @Autowired
    public RecentlyPlayedController(RecentlyPlayedRepository recentlyPlayedRepository) {
        this.recentlyPlayedRepository = recentlyPlayedRepository;
    }

    /**
     * Retrieves the recently played items for a user based on their Spotify identifier.
     * 
     * @param spotify The Spotify identifier of the user.
     * @return The RecentlyPlayed entity associated with the user.
     */
    @GetMapping(path = "/users/{spotify}/recentlyplayed")
    public RecentlyPlayed getUserRecentlyPlayed(@PathVariable String spotify) {
        return recentlyPlayedRepository.findBySpotify(spotify);
    }

    /**
     * Updates the recently played items for a user based on their Spotify identifier.
     * 
     * @param spotify      The Spotify identifier of the user.
     *
