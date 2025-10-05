package db.SearchedSongs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SearchedSongsRepository extends JpaRepository<SearchedSongs, Integer> {
    SearchedSongs findBySpotify(String spotify);

    @Transactional
    void deleteBySpotify(String spotify);
}
