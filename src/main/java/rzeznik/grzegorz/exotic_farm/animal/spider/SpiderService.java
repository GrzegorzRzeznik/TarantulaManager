package rzeznik.grzegorz.exotic_farm.animal.spider;

import org.springframework.stereotype.Service;
import rzeznik.grzegorz.exotic_farm.animal.AnimalNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpiderService {

    private final SpiderRepository spiderRepository;

    public SpiderService(SpiderRepository spiderRepository) {
        this.spiderRepository = spiderRepository;
    }

    public void save(SpiderDTO spiderDTO){
        spiderRepository.save(Spider.applyDTO(spiderDTO));
    }

    public SpiderDTO findById(Integer id){
        return spiderRepository.findById(id)
                .map(Spider::toDTO)
                .orElseThrow(() -> new AnimalNotFoundException(id));

    }

    public List<SpiderDTO> findByFarmId(Integer id){
        return spiderRepository.findByFarmId(id).stream().map(Spider::toDTO).collect(Collectors.toList());
    }

    public void delete(SpiderDTO spiderDTO) {
        spiderRepository.delete(Spider.applyDTO(spiderDTO));
    }
}
