package rzeznik.grzegorz.exotic_farm.farm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rzeznik.grzegorz.exotic_farm.user.UserDTO;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public class FarmDTO {

    private Integer id;
    private String name;
    private Set<UserDTO> users = new HashSet<>();
    private Set<UserDTO> admins = new HashSet<>();


    public FarmDTO(String name) {
        this.name = name;
    }

    public void addUser(UserDTO userDTO) {
        if(users.contains(userDTO) || admins.contains(userDTO)){
            return;
        }
        users.add(userDTO);
    }

    public void addAdmin(UserDTO userDTO) {
        if(admins.contains(userDTO)){
            return;
        }
        users.remove(userDTO);
        admins.add(userDTO);
    }

}
