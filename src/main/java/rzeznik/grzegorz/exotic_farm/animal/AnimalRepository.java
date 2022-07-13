package rzeznik.grzegorz.exotic_farm.animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository <Animal, Integer>{
    List<Animal> findByFarmId(Integer id);
}
