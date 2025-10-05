package db.TopArtists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TopArtistRepository extends JpaRepository<TopArtists, Integer>
{
    TopArtists findById(int id);

    TopArtists findBySpotify(String spotify);

    @Transactional
    void deleteById(int id);

    @Transactional
    void deleteBySpotify(String spotify);

}
