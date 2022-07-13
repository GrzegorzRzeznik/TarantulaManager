package rzeznik.grzegorz.exotic_farm.farm;

import org.springframework.stereotype.Service;
import rzeznik.grzegorz.exotic_farm.user.User;
import rzeznik.grzegorz.exotic_farm.user.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FarmService {

    private final FarmRepository farmRepository;

    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    public void save(FarmDTO farmDTO){
        Farm farm = Farm.applyDTO(farmDTO);
        farmRepository.save(farm);
    }

    public List<FarmDTO> findAll(){
        return farmRepository.findAll().stream()
                .map(Farm::toDTO)
                .collect(Collectors.toList());
    }

    public FarmDTO findById(Integer id){
        return farmRepository.findById(id)
                .map(Farm::toDTO)
                .orElseThrow(() -> new FarmNotFoundException(id));
    }

    public List<FarmDTO> findAllByAdminsContainingOrUsersContaining(UserDTO userDTO){
        User user = User.applyDTO(userDTO);
        return farmRepository.findAllByAdminsContainingOrUsersContaining(user, user).stream()
                .map(Farm::toDTO)
                .collect(Collectors.toList());
    }
    public List<FarmDTO> findAllByAdminsContaining(UserDTO userDTO){
        User user = User.applyDTO(userDTO);
        return farmRepository.findAllByAdminsContaining(user).stream()
                .map(Farm::toDTO)
                .collect(Collectors.toList());
    }
    public List<FarmDTO> findAllByUsersContaining(UserDTO userDTO){
        User user = User.applyDTO(userDTO);
        return farmRepository.findAllByUsersContaining(user).stream()
                .map(Farm::toDTO)
                .collect(Collectors.toList());
    }


    public void delete(FarmDTO farmDTO) {
        Farm farm = Farm.applyDTO(farmDTO);
        farmRepository.delete(farm);
    }
}
