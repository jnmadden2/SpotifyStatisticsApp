package db.RecentlyPlayed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for CRUD operations on RecentlyPlayed entities.
 */
public interface RecentlyPlayedRepository extends JpaRepository<RecentlyPlayed, Integer> {

    /**
     * Finds a RecentlyPlayed entity by the user's ID.
     * 
     * @param id The ID of the user.
     * @return The found RecentlyPlayed entity or null if not found.
     */
    RecentlyPlayed findById(int id);

    /**
     * Finds a RecentlyPlayed entity by the user's Spotify identifier.
     * 
     * @param spotify The Spotify identifier of the user.
     * @return The found RecentlyPlayed entity or null if not found.
     */
    RecentlyPlayed findBySpotify(String spotify);

    /**
     * Deletes a RecentlyPlayed entity by the user's ID.
     * 
     * @param id The ID of the user.
     */
    @Transactional
    void deleteById(int id);

    /**
     * Deletes a RecentlyPlayed entity by the user's Spotify identifier.
     * 
     * @param spotify The Spotify identifier of the user.
     */
    @Transactional
    void deleteBySpotify(String spotify);
}
