package rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo;

import lombok.Getter;
import rzeznik.grzegorz.exotic_farm.animal.Country;
import rzeznik.grzegorz.exotic_farm.animal.spider.SpiderDTO;

import java.util.ArrayList;
import java.util.List;


@Getter
public class SpiderSpeciesInfoDTO {
    private Integer id;
    private String genus;
    private String species;
    private String commonName;
    private String preferredTemperature;
    private String preferredHumidity;
    private boolean urticatingHairPresent;
    private Country country;
    private List<SpiderDTO> spiders = new ArrayList<>();


    public SpiderSpeciesInfoDTO(Integer id,
                                String genus,
                                String species,
                                String commonName,
                                String preferredTemperature,
                                String preferredHumidity,
                                boolean urticatingHairPresent,
                                Country country) {
        this.id = id;
        this.genus = genus;
        this.species = species;
        this.commonName = commonName;
        this.preferredTemperature = preferredTemperature;
        this.preferredHumidity = preferredHumidity;
        this.urticatingHairPresent = urticatingHairPresent;
        this.country = country;
    }

    public void addSpider(SpiderDTO spiderDTO){
        spiders.add(spiderDTO);
    }

}
