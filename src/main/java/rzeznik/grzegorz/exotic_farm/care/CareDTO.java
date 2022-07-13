package rzeznik.grzegorz.exotic_farm.care;

import lombok.Getter;
import lombok.Setter;
import rzeznik.grzegorz.exotic_farm.user.UserDTO;

import java.time.LocalDate;

@Getter
@Setter
public class CareDTO {

    private Integer id;
    private UserDTO userDTO;
    private LocalDate date;
    private CareType type;

    public CareDTO(Integer id, UserDTO toDTO, LocalDate date, CareType type) {
        this.id = id;
        userDTO = toDTO;
        this.date = date;
        this.type = type;
    }

    public CareDTO(UserDTO userDTO, LocalDate date, CareType type) {
        this.userDTO = userDTO;
        this.date = date;
        this.type = type;
    }
}
