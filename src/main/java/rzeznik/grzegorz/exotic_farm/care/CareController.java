package rzeznik.grzegorz.exotic_farm.care;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import rzeznik.grzegorz.exotic_farm.animal.AnimalNotFoundException;
import rzeznik.grzegorz.exotic_farm.animal.spider.SpiderDTO;
import rzeznik.grzegorz.exotic_farm.animal.spider.SpiderService;
import rzeznik.grzegorz.exotic_farm.user.UserContextService;
import rzeznik.grzegorz.exotic_farm.user.UserDTO;
import rzeznik.grzegorz.exotic_farm.user.UserService;

import java.time.LocalDate;

@Controller
public class CareController {

    private final SpiderService spiderService;
    private final UserService userService;
    private final UserContextService userContextService;

    public CareController(SpiderService spiderService, UserService userService, UserContextService userContextService) {
        this.spiderService = spiderService;
        this.userService = userService;
        this.userContextService = userContextService;
    }

    @PostMapping("/farms/{farmID}/spiders/{spiderID}/care")
    public String addCare(@PathVariable String farmID, @PathVariable String spiderID, String careType, Model model){
        SpiderDTO spiderDTO = spiderService.findByFarmId(Integer.valueOf(farmID)).stream()
                .filter(e -> e.getId().equals(Integer.valueOf(spiderID)))
                .findFirst()
                .orElseThrow(() ->new AnimalNotFoundException(Integer.valueOf(spiderID)));

        UserDTO userDTO = userService.findUserByUsername(userContextService.userName())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Not logged in"));

        CareDTO careDTO = new CareDTO(userDTO, LocalDate.now(), CareType.valueOf(careType));
        spiderDTO.addCare(careDTO);
        model.addAttribute("CareType", CareType.values());
        model.addAttribute("Spider", spiderDTO);
        model.addAttribute("User", userDTO);

        spiderService.save(spiderDTO);

        return "redirect:/farms/"+farmID;
    }

}
