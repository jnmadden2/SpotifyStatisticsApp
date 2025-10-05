package db.Users;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer>
{
    User findById(int id);

    User findBySpotify(String spotify);

    @Transactional
    void deleteById(int id);

    @Transactional
    void deleteBySpotify(String spotify);

}
