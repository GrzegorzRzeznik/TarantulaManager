package rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpiderSpeciesInfoRepository extends JpaRepository<SpiderSpeciesInfo, Integer> {
    @Query("SELECT i FROM SpiderSpeciesInfo i WHERE lower(i.genus) = lower(?1) AND lower(i.species) = lower(?2)")
    Optional<SpiderSpeciesInfo> findByGenusAndSpecies(String genus, String species);
}
