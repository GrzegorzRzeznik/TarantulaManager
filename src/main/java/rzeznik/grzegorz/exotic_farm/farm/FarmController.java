package rzeznik.grzegorz.exotic_farm.farm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rzeznik.grzegorz.exotic_farm.animal.AnimalDTO;
import rzeznik.grzegorz.exotic_farm.animal.AnimalService;
import rzeznik.grzegorz.exotic_farm.care.CareDTO;
import rzeznik.grzegorz.exotic_farm.care.CareType;
import rzeznik.grzegorz.exotic_farm.user.UserContextService;
import rzeznik.grzegorz.exotic_farm.user.UserDTO;
import rzeznik.grzegorz.exotic_farm.user.UserNotFoundException;
import rzeznik.grzegorz.exotic_farm.user.UserService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class FarmController {

    private final FarmService farmService;
    private final AnimalService animalService;
    private final UserContextService userContextService;
    private final UserService userService;

    public FarmController(FarmService farmService, AnimalService animalService, UserContextService userContextService, UserService userService) {
        this.farmService = farmService;
        this.animalService = animalService;
        this.userContextService = userContextService;
        this.userService = userService;
    }

    @GetMapping("/farms")
    public String farmsPage(Model model) {
        UserDTO userDTO = userService.findUserByUsername(userContextService.userName()).orElseThrow(UserNotFoundException::new);
        List<FarmDTO> farmsWhereAdmin = farmService.findAllByAdminsContaining(userDTO);
        List<FarmDTO> farmsWhereUser = farmService.findAllByUsersContaining(userDTO);
        model.addAttribute("farmsWhereAdmin", farmsWhereAdmin);
        model.addAttribute("farmsWhereUser", farmsWhereUser);
        return "farmsPage";
    }

    @PostMapping("/addFarm")
    public String addFarm(@RequestParam String farmName) {
        Optional<UserDTO> admin = userService.findUserByUsername(userContextService.userName());
        FarmDTO farmDTO = new FarmDTO(farmName);
        admin.ifPresent(farmDTO::addAdmin);
        farmService.save(farmDTO);
        return "redirect:/farms";
    }

    @PostMapping("/farms/delete")
    public String deleteFarm(@RequestParam(name = "id") Integer farmId){
        FarmDTO farm = farmService.findById(farmId);
        UserDTO user = userService.findUserByUsername(userContextService.userName()).orElseThrow(UserNotFoundException::new);
        if (farm.getAdmins().contains(user)){
            farmService.delete(farm);
        }
        return "redirect:/farms";
    }
    @PostMapping("/farms/{id}/addUser/")
    public String addUser(@PathVariable(name = "id") Integer farmId, @RequestParam String username){
        UserDTO user = userService.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
        FarmDTO farm = farmService.findById(farmId);
        farm.addUser(user);
        farmService.save(farm);
        return "redirect:/farms/"+farmId;
    }
    @PostMapping("/farms/{id}/addAdmin/")
    public String addAdmin(@PathVariable(name = "id") Integer farmId, @RequestParam String username){
        UserDTO user = userService.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
        FarmDTO farm = farmService.findById(farmId);
        farm.addAdmin(user);
        farmService.save(farm);
        return "redirect:/farms/"+farmId;
    }
    @GetMapping("/farms/{id}")
    public String editFarm(@PathVariable(name = "id") Integer farmId, Model model) {
        FarmDTO farm = farmService.findById(farmId);
        UserDTO user = userService.findUserByUsername(userContextService.userName()).orElseThrow(UserNotFoundException::new);
        boolean isAdmin = farm.getAdmins().contains(user);
        boolean isUser = farm.getUsers().contains(user);
        List<AnimalDTO> animals = animalService.findByFarmId(farmId);
        Map<AnimalDTO, String> lastFeedingMap = findLastFeeding(animals);
        Map<AnimalDTO, String> lastRehouseMap = findLastRehouse(animals);
        Map<AnimalDTO, String> lastSubstrateChangeMap = findLastSubstrateChange(animals);
        model.addAttribute("isAdmin", isAdmin);
        if (isUser || isAdmin) {
            model.addAttribute("farm", farm);
            model.addAttribute("spiderList", animals);
        }
        model.addAttribute("careType", CareType.values());
        model.addAttribute("recentFeeding", lastFeedingMap);
        model.addAttribute("recentRehouse", lastRehouseMap);
        model.addAttribute("recentSubstrateChange", lastSubstrateChangeMap);

        return "farmEditPage";
    }

    private Map<AnimalDTO, String> findLastFeeding(List<AnimalDTO> animals) {
        Map<AnimalDTO, String> feedingMap = new HashMap<>();
        for (AnimalDTO a : animals) {
            String recentFeeding = a.getCareDTOList().stream()
                    .filter(c -> c.getType().equals(CareType.FEEDING))
                    .map(CareDTO::getDate)
                    .max(LocalDate::compareTo)
                    .map(LocalDate::toString)
                    .orElse("Never fed");
            feedingMap.put(a, recentFeeding);
        }
        return feedingMap;
    }

    private Map<AnimalDTO, String> findLastRehouse(List<AnimalDTO> animals) {
        Map<AnimalDTO, String> rehouseMap = new HashMap<>();
        for (AnimalDTO a : animals) {
            String recentFeeding = a.getCareDTOList().stream()
                    .filter(c -> c.getType().equals(CareType.REHOUSE))
                    .map(CareDTO::getDate)
                    .max(LocalDate::compareTo)
                    .map(LocalDate::toString)
                    .orElse("Never rehoused");
            rehouseMap.put(a, recentFeeding);
        }
        return rehouseMap;
    }
    private Map<AnimalDTO, String> findLastSubstrateChange(List<AnimalDTO> animals) {
        Map<AnimalDTO, String> substrateChangeMap = new HashMap<>();
        for (AnimalDTO a : animals) {
            String recentFeeding = a.getCareDTOList().stream()
                    .filter(c -> c.getType().equals(CareType.SUBSTRATE_CHANGE))
                    .map(CareDTO::getDate)
                    .max(LocalDate::compareTo)
                    .map(LocalDate::toString)
                    .orElse("Never changed");
            substrateChangeMap.put(a, recentFeeding);
        }
        return substrateChangeMap;
    }

}
