package rzeznik.grzegorz.exotic_farm.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE lower(u.email) = lower(?1)")
    Optional<User> findByEMail(String email);

    Optional<User> findUserByUsername(String userName);
}
