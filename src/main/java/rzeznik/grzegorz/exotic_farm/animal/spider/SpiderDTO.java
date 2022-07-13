package rzeznik.grzegorz.exotic_farm.animal.spider;

import lombok.Getter;
import rzeznik.grzegorz.exotic_farm.animal.AnimalDTO;
import rzeznik.grzegorz.exotic_farm.animal.Sex;
import rzeznik.grzegorz.exotic_farm.animal.Temperament;
import rzeznik.grzegorz.exotic_farm.animal.spider.molt.MoltDTO;
import rzeznik.grzegorz.exotic_farm.animal.spider.speciesInfo.SpiderSpeciesInfoDTO;
import rzeznik.grzegorz.exotic_farm.farm.FarmDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SpiderDTO extends AnimalDTO {

    private SpiderSpeciesInfoDTO infoDTO;
    private VenomPotency venomPotency;
    private Type type;
    private List<MoltDTO> moltsDTOList = new ArrayList<>();

    public SpiderDTO(Integer id,
                     LocalDate acquisitionDate,
                     String name,
                     FarmDTO farmDTO,
                     Sex sex,
                     Status status,
                     Temperament temperament,
                     SpiderSpeciesInfoDTO infoDTO,
                     VenomPotency venomPotency,
                     Type type) {
        super(id, acquisitionDate, name, farmDTO, sex, status, temperament);
        this.infoDTO = infoDTO;
        this.venomPotency = venomPotency;
        this.type = type;
    }

    public SpiderDTO(LocalDate acquisitionDate,
                     String name,
                     FarmDTO farmDTO,
                     Sex sex,
                     Status status,
                     Temperament temperament,
                     VenomPotency venomPotency,
                     Type type) {
        super(acquisitionDate, name, farmDTO, sex, status, temperament);
        this.venomPotency = venomPotency;
        this.type = type;
    }

    public void addMolt(MoltDTO moltDTO){
        if (moltsDTOList.contains(moltDTO))
            return ;
        moltsDTOList.add(moltDTO);
    }

    public void setInfoDTO(SpiderSpeciesInfoDTO infoDTO) {
        this.infoDTO = infoDTO;
    }

}
