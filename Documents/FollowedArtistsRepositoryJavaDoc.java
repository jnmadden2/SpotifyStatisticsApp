package db.FollowedArtists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for CRUD operations on FollowedArtists entities.
 */
public interface FollowedArtistsRepository extends JpaRepository<FollowedArtists, Integer> {

    /**
     * Finds a followed artist by their user ID.
     * 
     * @param id The ID of the user.
     * @return The found FollowedArtists entity or null if not found.
     */
    FollowedArtists findById(int id);

    /**
     * Finds a followed artist by their Spotify identifier.
     * 
     * @param spotify The Spotify identifier of the artist.
     * @return The found FollowedArtists entity or null if not found.
     */
    FollowedArtists findBySpotify(String spotify);

    /**
     * Deletes the followed artist by their user ID.
     * 
     * @param id The ID of the user.
     */
    @Transactional
    void deleteById(int id);

    /**
     * Deletes the followed artist by their Spotify identifier.
     * 
     * @param spotify The Spotify identifier of the artist.
     */
    @Transactional
    void deleteBySpotify(String spotify);
}
