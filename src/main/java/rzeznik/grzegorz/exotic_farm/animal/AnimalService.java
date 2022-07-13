package rzeznik.grzegorz.exotic_farm.animal;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public void save(AnimalDTO animalDTO){
        animalRepository.save(Animal.applyDTO(animalDTO));
    }

    public List<AnimalDTO> findByFarmId(Integer id){
        return animalRepository.findByFarmId(id).stream().map(Animal::toDTO).collect(Collectors.toList());
    }

}
