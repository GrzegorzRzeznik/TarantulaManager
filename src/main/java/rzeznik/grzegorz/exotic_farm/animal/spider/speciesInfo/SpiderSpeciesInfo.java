package rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo;

import lombok.NoArgsConstructor;
import rzeznik.grzegorz.exotic_farm.animal.Country;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class SpiderSpeciesInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String genus;
    private String species;
    private String commonName;
    private String preferredTemperature;
    private String preferredHumidity;
    private boolean urticatingHairPresent;
    @Enumerated(EnumType.STRING)
    private Country country;

    public SpiderSpeciesInfo(String genus,
                             String species,
                             String commonName,
                             String preferredTemperature,
                             String preferredHumidity,
                             boolean urticatingHairPresent,
                             Country country) {
        this.genus = genus;
        this.species = species;
        this.commonName = commonName;
        this.preferredTemperature = preferredTemperature;
        this.preferredHumidity = preferredHumidity;
        this.urticatingHairPresent = urticatingHairPresent;
        this.country = country;
    }

    public SpiderSpeciesInfo(Integer id,
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

    public static SpiderSpeciesInfo applyDTO(SpeciesInfoFromFileDTO infoDTO) {
        return new SpiderSpeciesInfo(infoDTO.getGenus(),
                infoDTO.getSpecies(),
                infoDTO.getCommonName(),
                infoDTO.getPreferredTemperature(),
                infoDTO.getPreferredHumidity(),
                infoDTO.isUrticatingHairPresent(),
                infoDTO.getCountry());
    }

    public static SpiderSpeciesInfo applyDTO(SpiderSpeciesInfoDTO infoDTO) {
        return new SpiderSpeciesInfo(infoDTO.getId(),
                infoDTO.getGenus(),
                infoDTO.getSpecies(),
                infoDTO.getCommonName(),
                infoDTO.getPreferredTemperature(),
                infoDTO.getPreferredHumidity(),
                infoDTO.isUrticatingHairPresent(),
                infoDTO.getCountry());
    }

    public SpiderSpeciesInfoDTO toDTO() {
        return new SpiderSpeciesInfoDTO(id,
                genus,
                species,
                commonName,
                preferredTemperature,
                preferredHumidity,
                urticatingHairPresent,
                country);
    }
}
