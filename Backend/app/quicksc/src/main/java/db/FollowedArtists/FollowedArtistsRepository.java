package db.FollowedArtists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface FollowedArtistsRepository extends JpaRepository<FollowedArtists, Integer>
{
    FollowedArtists findById(int id);

    FollowedArtists findBySpotify(String spotify);

    @Transactional
    void deleteById(int id);

    @Transactional
    void deleteBySpotify(String spotify);

}
