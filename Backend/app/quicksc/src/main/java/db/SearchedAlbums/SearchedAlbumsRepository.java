package db.SearchedAlbums;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SearchedAlbumsRepository extends JpaRepository<SearchedAlbums, Integer> {
    SearchedAlbums findBySpotify(String spotify);

    @Transactional
    void deleteBySpotify(String spotify);
}
