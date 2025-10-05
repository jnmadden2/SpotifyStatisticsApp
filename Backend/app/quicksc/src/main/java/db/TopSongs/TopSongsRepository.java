package db.TopSongs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TopSongsRepository extends JpaRepository<TopSongs, Integer> 
{
    TopSongs findById(int id);

    TopSongs findBySpotify(String spotify);

    @Transactional
    void deleteById(int id);

    @Transactional
    void deleteBySpotify(String spotify);

}

