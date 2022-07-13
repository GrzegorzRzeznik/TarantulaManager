package rzeznik.grzegorz.exotic_farm.animal.spider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpiderRepository extends JpaRepository<Spider, Integer> {

    List<Spider> findByFarmId(Integer id);
}
