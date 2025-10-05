package db.Radio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RadioRepository extends JpaRepository<Radio, Integer>
{
    Radio findById(int id);

    Radio findBySpotify(String spotify);

    @Transactional
    void deleteById(int id);

    @Transactional
    void deleteBySpotify(String spotify);

}
