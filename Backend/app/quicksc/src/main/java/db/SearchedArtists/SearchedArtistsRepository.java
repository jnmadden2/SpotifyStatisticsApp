package db.SearchedArtists;

import db.SearchedArtists.SearchedArtists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SearchedArtistsRepository extends JpaRepository<SearchedArtists, Integer> {
    SearchedArtists findBySpotify(String spotify);

    @Transactional
    void deleteBySpotify(String spotify);
}
