package rzeznik.grzegorz.exotic_farm.care;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareRepository extends JpaRepository<Care, Integer> {
    List<Care> findAllByAnimalId(Integer animalId);
}
