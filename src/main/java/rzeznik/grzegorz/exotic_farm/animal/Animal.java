package rzeznik.grzegorz.exotic_farm.animal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import rzeznik.grzegorz.exotic_farm.animal.spider.Status;
import rzeznik.grzegorz.exotic_farm.care.Care;
import rzeznik.grzegorz.exotic_farm.farm.Farm;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Integer id;
    protected LocalDate acquisitionDate;
    protected String name;
    @ManyToOne(fetch = FetchType.LAZY)
    protected Farm farm;
    @Enumerated(EnumType.STRING)
    protected Sex sex;
    @Enumerated(EnumType.STRING)
    protected Status status;
    @Enumerated(EnumType.STRING)
    protected Temperament temperament;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "animal")
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected List<Care> careList = new ArrayList<>();

    public Animal(LocalDate acquisitionDate, String name, Farm farm, Sex sex, Status status, Temperament temperament) {
        this.acquisitionDate = acquisitionDate;
        this.name = name;
        this.farm = farm;
        this.sex = sex;
        this.status = status;
        this.temperament = temperament;
    }

    public Animal(Integer id, LocalDate acquisitionDate, String name, Farm farm, Sex sex, Status status, Temperament temperament) {
        this.id = id;
        this.acquisitionDate = acquisitionDate;
        this.name = name;
        this.farm = farm;
        this.sex = sex;
        this.status = status;
        this.temperament = temperament;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAcquisitionDate(LocalDate acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTemperament(Temperament temperament) {
        this.temperament = temperament;
    }

    public void setCareList(List<Care> careList) {
        this.careList = careList;
    }

    public void addCare(Care care) {
        if (careList.contains(care))
            return;
        care.setAnimal(this);
        careList.add(care);
    }

    public void removeCare(Care care) {
        if (!careList.contains(care))
            return;
        careList.remove(care);
        care.setAnimal(null);
    }

    public AnimalDTO toDTO() {
        AnimalDTO animalDTO = new AnimalDTO(id, acquisitionDate, name, farm.toDTO(), sex, status, temperament);
        for (Care c : careList) {
            animalDTO.addCare(c.toDTO());
        }
        return animalDTO;
    }

    public static Animal applyDTO(AnimalDTO animalDTO) {
        List<Care> careList = animalDTO.getCareDTOList().stream().map(Care::applyDTO).collect(Collectors.toList());
        Animal animal = new Animal(animalDTO.getId(),
                animalDTO.getAcquisitionDate(),
                animalDTO.getName(),
                Farm.applyDTO(animalDTO.getFarmDTO()),
                animalDTO.getSex(),
                animalDTO.getStatus(),
                animalDTO.getTemperament());
        for (Care c : careList) {
            animal.addCare(c);
        }
        return animal;
    }

}
