package rzeznik.grzegorz.exotic_farm.animal.spider.molt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoltRepository extends JpaRepository<Molt, Integer> {
    public List<Molt> findAllBySpiderId (Integer id);
}
