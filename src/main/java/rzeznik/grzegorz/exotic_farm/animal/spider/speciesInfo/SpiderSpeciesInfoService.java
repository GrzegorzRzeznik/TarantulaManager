package rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpiderSpeciesInfoService {

    private final SpiderSpeciesInfoRepository spiderSpeciesInfoRepository;

    public SpiderSpeciesInfoService(SpiderSpeciesInfoRepository spiderSpeciesInfoRepository) {
        this.spiderSpeciesInfoRepository = spiderSpeciesInfoRepository;
    }

    public List<SpiderSpeciesInfoDTO> findAll(){
        return spiderSpeciesInfoRepository.findAll().stream()
                .map(SpiderSpeciesInfo::toDTO)
                .collect(Collectors.toList());
    }

    public SpiderSpeciesInfoDTO findByGenusAndSpecies(String genus, String species) {
        return spiderSpeciesInfoRepository.findByGenusAndSpecies(genus, species)
                .map(SpiderSpeciesInfo::toDTO)
                .orElseThrow(RuntimeException::new);
    }
}
