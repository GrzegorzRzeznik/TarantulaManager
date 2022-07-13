package rzeznik.grzegorz.exotic_farm.care;

import lombok.Getter;
import lombok.NoArgsConstructor;
import rzeznik.grzegorz.exotic_farm.animal.Animal;
import rzeznik.grzegorz.exotic_farm.user.User;
import rzeznik.grzegorz.exotic_farm.user.UserDTO;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Care {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Animal animal;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private CareType type;

    public Care(User user, Animal animal, LocalDate date, CareType type){
        this.user = user;
        this.animal = animal;
        this.date = date;
        this.type = type;
    }

    public Care(Integer id, User user, LocalDate date, CareType type) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.type = type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAnimal(Animal animal) {
       this.animal = animal;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setType(CareType type) {
        this.type = type;
    }

    public CareDTO toDTO(){
        UserDTO userDTO = user.toDTO();
        return new CareDTO(id, userDTO, date, type);
    }

    public static Care applyDTO(CareDTO careDTO){
        return new Care(careDTO.getId(),
                User.applyDTO(careDTO.getUserDTO()),
                careDTO.getDate(),
                careDTO.getType());
    }

}
