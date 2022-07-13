package rzeznik.grzegorz.exotic_farm.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Integer id;
    private String username;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String city;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id.equals(userDTO.id) &&
                username.equals(userDTO.username) &&
                Objects.equals(email, userDTO.email) &&
                Objects.equals(passwordHash, userDTO.passwordHash) &&
                Objects.equals(firstName, userDTO.firstName) &&
                Objects.equals(lastName, userDTO.lastName) &&
                Objects.equals(city, userDTO.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, passwordHash, firstName, lastName, city);
    }
}
