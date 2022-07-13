package rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo;

import lombok.Getter;
import rzeznik.grzegorz.exotic_farm.animal.Country;

@Getter
public class SpeciesInfoFromFileDTO {
    private String genus;
    private String species;
    private String commonName;
    private String preferredTemperature;
    private String preferredHumidity;
    private boolean urticatingHairPresent;
    private Country country;

    public SpeciesInfoFromFileDTO(String genus,
                                  String species,
                                  String commonName,
                                  String  preferredTemperature,
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

    public static SpeciesInfoFromFileDTO applyFromText(String info){
        String[] data = info.split(",");
        return new SpeciesInfoFromFileDTO(
                data[0],
                data[1],
                data[2],
                data[3],
                data[4],
                Boolean.parseBoolean(data[5]),
                Country.valueOf(data[6]));
    }

    @Override
    public String toString() {
        return "SpeciesInfoFromFileDTO{" +
                "genus='" + genus + '\'' +
                ", species='" + species + '\'' +
                ", commonName='" + commonName + '\'' +
                ", preferredTemperature=" + preferredTemperature +
                ", preferredHumidity=" + preferredHumidity +
                ", urticatingHairPresent=" + urticatingHairPresent +
                ", country=" + country +
                '}';
    }
}
