package db.MinutesListened;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MinutesListenedRepository extends JpaRepository<MinutesListened, Integer>
{
    MinutesListened findById(int id);

    MinutesListened findBySpotify(String spotify);

    @Transactional
    void deleteById(int id);

    @Transactional
    void deleteBySpotify(String spotify);

}
