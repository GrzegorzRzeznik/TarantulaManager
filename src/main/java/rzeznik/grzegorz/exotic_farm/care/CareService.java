package rzeznik.grzegorz.exotic_farm.care;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CareService {

    private final CareRepository careRepository;

    public CareService(CareRepository careRepository) {
        this.careRepository = careRepository;
    }

    public List<CareDTO> findAllByAnimalId(Integer animalId){
       return careRepository.findAllByAnimalId(animalId).stream().map(Care::toDTO).collect(Collectors.toList());
    }
}
