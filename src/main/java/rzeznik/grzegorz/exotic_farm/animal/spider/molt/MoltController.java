package rzeznik.grzegorz.exotic_farm.animal.spider.molt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rzeznik.grzegorz.exotic_farm.animal.spider.SpiderDTO;
import rzeznik.grzegorz.exotic_farm.animal.spider.SpiderService;

import java.time.LocalDate;

@Controller
public class MoltController {

    private final SpiderService spiderService;

    public MoltController(SpiderService spiderService) {
        this.spiderService = spiderService;
    }

    @PostMapping("/molts")
    public String addMolt(@RequestParam(name = "farmId") Integer farmId, @RequestParam(name = "spiderId") Integer spiderId){
        SpiderDTO spiderDTO = spiderService.findById(spiderId);
        MoltDTO moltDTO = new MoltDTO(LocalDate.now());
        spiderDTO.addMolt(moltDTO);
        spiderService.save(spiderDTO);
        return "redirect:/farms/"+farmId;
    }
}
