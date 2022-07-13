package rzeznik.grzegorz.exotic_farm.animal.spider;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import rzeznik.grzegorz.exotic_farm.animal.Animal;
import rzeznik.grzegorz.exotic_farm.animal.Sex;
import rzeznik.grzegorz.exotic_farm.animal.Temperament;
import rzeznik.grzegorz.exotic_farm.animal.spider.molt.Molt;
import rzeznik.grzegorz.exotic_farm.animal.spider.molt.MoltDTO;
import rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo.SpiderSpeciesInfo;
import rzeznik.grzegorz.exotic_farm.care.Care;
import rzeznik.grzegorz.exotic_farm.care.CareDTO;
import rzeznik.grzegorz.exotic_farm.farm.Farm;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Setter
@NoArgsConstructor
public class Spider extends Animal {

    @ManyToOne(fetch = FetchType.LAZY)
    private SpiderSpeciesInfo speciesInfo;
    @Enumerated(EnumType.STRING)
    private VenomPotency venomPotency;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "spider")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Molt> molts = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Type type;


    public Spider(String name, Sex sex, Status status) {
        this.name = name;
        this.sex = sex;
        this.status = status;
    }

    public Spider(String name, LocalDate acquisitionDate, Sex sex, Status status, Temperament temperament, SpiderSpeciesInfo speciesInfo, Farm farm, VenomPotency venomPotency, Type type) {
        super(acquisitionDate, name, farm, sex, status, temperament);
        this.speciesInfo = speciesInfo;
        this.venomPotency = venomPotency;
        this.type = type;
    }

    public Spider(Integer id,
                  String name,
                  LocalDate acquisitionDate,
                  Sex sex,
                  Status status,
                  Temperament temperament,
                  SpiderSpeciesInfo speciesInfo,
                  Farm farm,
                  VenomPotency venomPotency,
                  Type type
    ) {
        super(id, acquisitionDate, name, farm, sex, status, temperament);
        this.speciesInfo = speciesInfo;
        this.venomPotency = venomPotency;
        this.type = type;
        setFarm(farm);
    }

    public Spider(Integer id, LocalDate acquisitionDate, String name, Farm farm, Sex sex, Status status, Temperament temperament, SpiderSpeciesInfo speciesInfo, VenomPotency venomPotency, Type type) {
        super(id, acquisitionDate, name, farm, sex, status, temperament);
        this.speciesInfo = speciesInfo;
        this.venomPotency = venomPotency;
        this.type = type;
    }

    public void addMolt(Molt molt) {
        if (molts.contains(molt))
            return;
        molt.setSpider(this);
        molts.add(molt);
    }

    public SpiderDTO toDTO() {
        SpiderDTO spiderDTO = new SpiderDTO(id, acquisitionDate, name, farm.toDTO(), sex, status, temperament, speciesInfo.toDTO(), venomPotency, type);
        List<CareDTO> careDTOList = careList.stream().map(Care::toDTO).collect(Collectors.toList());
        List<MoltDTO> moltDTOList = molts.stream().map(Molt::toDTO).collect(Collectors.toList());
        for (CareDTO c : careDTOList) {
            spiderDTO.addCare(c);
        }
        for (MoltDTO m : moltDTOList){
            spiderDTO.addMolt(m);
        }
        return spiderDTO;
    }

    public static Spider applyDTO(SpiderDTO dto) {
        List<Care> careList = dto.getCareDTOList().stream().map(Care::applyDTO).collect(Collectors.toList());
        List<Molt> molts = dto.getMoltsDTOList().stream().map(Molt::applyDTO).collect(Collectors.toList());
        Spider spider = new Spider(dto.getId(),
                dto.getAcquisitionDate(),
                dto.getName(),
                Farm.applyDTO(dto.getFarmDTO()),
                dto.getSex(),
                dto.getStatus(),
                dto.getTemperament(),
                SpiderSpeciesInfo.applyDTO(dto.getInfoDTO()),
                dto.getVenomPotency(),
                dto.getType());
        for (Care c : careList) {
            spider.addCare(c);
        }
        for (Molt m : molts){
            spider.addMolt(m);
        }
        return spider;
    }


}
