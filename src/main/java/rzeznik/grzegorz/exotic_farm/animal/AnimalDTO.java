package rzeznik.grzegorz.exotic_farm.animal;

import lombok.Getter;
import rzeznik.grzegorz.exotic_farm.animal.spider.Status;
import rzeznik.grzegorz.exotic_farm.care.CareDTO;
import rzeznik.grzegorz.exotic_farm.farm.FarmDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AnimalDTO {

    private Integer id;
    private LocalDate acquisitionDate;
    private String name;
    private FarmDTO farmDTO;
    private Sex sex;
    private Status status;
    private Temperament temperament;
    private List<CareDTO> careDTOList = new ArrayList<>();

    public AnimalDTO(LocalDate acquisitionDate, String name, FarmDTO farmDTO, Sex sex, Status status, Temperament temperament) {
        this.acquisitionDate = acquisitionDate;
        this.name = name;
        this.farmDTO = farmDTO;
        this.sex = sex;
        this.status = status;
        this.temperament = temperament;
    }
    public AnimalDTO(Integer id, LocalDate acquisitionDate, String name, FarmDTO farmDTO, Sex sex, Status status, Temperament temperament) {
        this.id = id;
        this.acquisitionDate = acquisitionDate;
        this.name = name;
        this.farmDTO = farmDTO;
        this.sex = sex;
        this.status = status;
        this.temperament = temperament;
    }

    public void setCareDTOList(List<CareDTO> careDTOList) {
        this.careDTOList = careDTOList;
    }

    public void addCare(CareDTO careDTO){
        if (careDTOList.contains(careDTO))
            return ;
        careDTOList.add(careDTO);
    }

    public void removeCare(CareDTO careDTO){
        if (!careDTOList.contains(careDTO))
            return ;
        careDTOList.remove(careDTO);
    }
}
