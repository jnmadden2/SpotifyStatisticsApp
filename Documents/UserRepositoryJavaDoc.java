package db.TopSongs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data repository interface for {@link TopSongs} entities.
 * This interface extends JpaRepository, providing standard methods for CRUD operations.
 */
public interface TopSongsRepository extends JpaRepository<TopSongs, Integer> {

    /**
     * Finds a {@link TopSongs} entity based on its ID.
     * 
     * @param id The ID of the {@link TopSongs} entity to find.
     * @return The {@link TopSongs} entity if found, or {@code null} otherwise.
     */
    TopSongs findById(int id);

    /**
     * Finds a {@link TopSongs} entity based on the Spotify ID.
     * 
     * @param spotify The Spotify ID associated with the {@link TopSongs} entity to find.
     * @return The {@link TopSongs} entity if found, or {@code null} otherwise.
     */
    TopSongs findBySpotify(String spotify);

    /**
     * Deletes a {@link TopSongs} entity based on its ID.
     * This method is transactional, meaning it can be rolled back if any part of the transaction fails.
     * 
     * @param id The ID of the {@link TopSongs} entity to delete.
     */
    @Transactional
    void deleteById(int id);

    /**
     * Deletes a {@link TopSongs} entity based on the Spotify ID.
     * This method is transactional, meaning it can be rolled back if any part of the transaction fails.
     * 
     * @param spotify The Spotify ID associated with the {@link TopSongs} entity to delete.
     */
    @Transactional
    void deleteBySpotify(String spotify);
}
