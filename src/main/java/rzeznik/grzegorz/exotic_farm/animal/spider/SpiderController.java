package rzeznik.grzegorz.exotic_farm.animal.spider;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rzeznik.grzegorz.exotic_farm.animal.Sex;
import rzeznik.grzegorz.exotic_farm.animal.Temperament;
import rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo.SpiderSpeciesInfoDTO;
import rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo.SpiderSpeciesInfoService;
import rzeznik.grzegorz.exotic_farm.care.CareDTO;
import rzeznik.grzegorz.exotic_farm.care.CareService;
import rzeznik.grzegorz.exotic_farm.care.CareType;
import rzeznik.grzegorz.exotic_farm.farm.FarmDTO;
import rzeznik.grzegorz.exotic_farm.farm.FarmService;
import rzeznik.grzegorz.exotic_farm.user.UserContextService;
import rzeznik.grzegorz.exotic_farm.user.UserDTO;
import rzeznik.grzegorz.exotic_farm.user.UserNotFoundException;
import rzeznik.grzegorz.exotic_farm.user.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class SpiderController {

    private final SpiderService spiderService;
    private final SpiderSpeciesInfoService spiderSpeciesInfoService;
    private final FarmService farmService;
    private final UserService userService;
    private final CareService careService;
    private final UserContextService userContextService;

    public SpiderController(SpiderService spiderService,
                            SpiderSpeciesInfoService spiderSpeciesInfoService,
                            FarmService farmService,
                            UserService userService,
                            CareService careService,
                            UserContextService userContextService) {
        this.spiderService = spiderService;
        this.spiderSpeciesInfoService = spiderSpeciesInfoService;
        this.farmService = farmService;
        this.userService = userService;
        this.careService = careService;
        this.userContextService = userContextService;
    }

    @GetMapping("farms/{farmID}/spiders/{spiderID}")
    public String editSpider(@PathVariable(name = "farmID") Integer farmID, @PathVariable(name = "spiderID") Integer spiderID, Model model) {

        List<CareDTO> feedingList = createFeedingList(spiderID);
        List<CareDTO> substrateChangeList = createSubstrateChangeList(spiderID);
        List<CareDTO> rehouseList = createRehouseList(spiderID);
        SpiderDTO spiderDTO = spiderService.findById(spiderID);

        model.addAttribute("spider", spiderService.findById(spiderID));
        model.addAttribute("moltsNumber", spiderDTO.getMoltsDTOList().size());
        model.addAttribute("feedingList", feedingList);
        model.addAttribute("substrateChangeList", substrateChangeList);
        model.addAttribute("rehouseList", rehouseList);

        return "spiderEditPage";
    }

    @PostMapping("/spiders")
    public String addSpider(@RequestParam Status status,
                            @RequestParam String name,
                            @RequestParam String genus,
                            @RequestParam String species,
                            @RequestParam String acquisitionDate,
                            @RequestParam Sex sex,
                            @RequestParam Temperament temperament,
                            @RequestParam String farmID,
                            @RequestParam VenomPotency venomPotency,
                            @RequestParam Type type,
                            Model model) {
        FarmDTO farmDTO = farmService.findById(Integer.parseInt(farmID));
        model.addAttribute("farm", farmDTO);
        SpiderSpeciesInfoDTO speciesInfo = spiderSpeciesInfoService.findByGenusAndSpecies(genus, species);
        LocalDate date = LocalDate.parse(acquisitionDate);
        SpiderDTO spiderDTO = new SpiderDTO(date, name, farmDTO, sex, status, temperament, venomPotency, type);
        spiderDTO.setInfoDTO(speciesInfo);
        spiderService.save(spiderDTO);
        return "redirect:/farms/" + farmID;
    }

    @PostMapping("/farms/{farmID}/deleteSpider/")
    public String deleteSpider(@PathVariable(name = "farmID") Integer farmID, @RequestParam(name = "spiderID") Integer spiderID) {
        FarmDTO farm = farmService.findById(farmID);
        UserDTO user = userService.findUserByUsername(userContextService.userName()).orElseThrow(UserNotFoundException::new);
        if (farm.getAdmins().contains(user)) {
            SpiderDTO spider = spiderService.findById(spiderID);
            spiderService.delete(spider);
        }
        return "redirect:/farms/" + farmID;
    }

    @PostMapping("farms/{farmID}/addSpider/")
    public String addSpiderPage(@PathVariable(name = "farmID") String farmId, Model model) {

        final Map<String, List<String>> genusSpeciesMap = createGenusSpeciesListMap();

        model.addAttribute(farmId);
        model.addAttribute("statusList", Status.values());
        model.addAttribute("sexList", Sex.values());
        model.addAttribute("temperamentList", Temperament.values());
        model.addAttribute("typeList", Type.values());
        model.addAttribute("venomPotencyList", VenomPotency.values());
        model.addAttribute("careType", CareType.values());
        model.addAttribute("genusSpeciesMap", genusSpeciesMap);

        return "addSpider";
    }

    private Map<String, List<String>> createGenusSpeciesListMap() {
        Map<String, List<SpiderSpeciesInfoDTO>> genusSpeciesMap = spiderSpeciesInfoService.findAll().stream()
                .collect(Collectors.groupingBy(SpiderSpeciesInfoDTO::getGenus));
        return genusSpeciesMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, i -> i.getValue().stream().map(SpiderSpeciesInfoDTO::getSpecies).collect(Collectors.toList())));
    }

    private List<CareDTO> createFeedingList(Integer spiderId) {
        return careService.findAllByAnimalId(spiderId).stream()
                .filter(c -> c.getType().equals(CareType.FEEDING))
                .collect(Collectors.toList());
    }

    private List<CareDTO> createSubstrateChangeList(Integer spiderId) {
        return careService.findAllByAnimalId(spiderId).stream()
                .filter(c -> c.getType().equals(CareType.SUBSTRATE_CHANGE))
                .collect(Collectors.toList());
    }

    private List<CareDTO> createRehouseList(Integer spiderId) {
        return careService.findAllByAnimalId(spiderId).stream()
                .filter(c -> c.getType().equals(CareType.REHOUSE))
                .collect(Collectors.toList());
    }
}
