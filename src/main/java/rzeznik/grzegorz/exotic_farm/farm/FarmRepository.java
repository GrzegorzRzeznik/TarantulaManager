package rzeznik.grzegorz.exotic_farm.farm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rzeznik.grzegorz.exotic_farm.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Integer> {

    Optional<Farm> findByName(String name);
    List<Farm> findAllByAdminsContainingOrUsersContaining(User user, User admin);
    List<Farm> findAllByAdminsContaining(User admin);
    List<Farm> findAllByUsersContaining(User user);
}
