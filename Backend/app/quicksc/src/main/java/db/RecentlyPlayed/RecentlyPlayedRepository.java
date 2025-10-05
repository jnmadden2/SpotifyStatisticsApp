package db.RecentlyPlayed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RecentlyPlayedRepository extends JpaRepository<RecentlyPlayed, Integer>
{
    RecentlyPlayed findById(int id);

    RecentlyPlayed findBySpotify(String spotify);

    @Transactional
    void deleteById(int id);

    @Transactional
    void deleteBySpotify(String spotify);

}
