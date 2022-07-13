package rzeznik.grzegorz.exotic_farm.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String username;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String city;
    @ManyToMany
    @JoinTable(name = "Users_roles")
    private List<Role> roles;


    public User(String username, String email, String passwordHash, String firstName, String lastName, String city) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static User applyDTO(UserRegistrationDTO dto, String passwordHash) {
        User user = new User();
        user.username = dto.getUsername();
        user.email = dto.getEmail();
        user.passwordHash = passwordHash;
        user.firstName = dto.getFirstName();
        user.lastName = dto.getLastName();
        user.city = dto.getCity();
        return user;
    }

    public static User applyDTO(UserDTO dto) {
        User user = new User();
        user.id = dto.getId();
        user.username = dto.getUsername();
        user.email = dto.getEmail();
        user.passwordHash = dto.getPasswordHash();
        user.firstName = dto.getFirstName();
        user.lastName = dto.getLastName();
        user.city = dto.getCity();
        return user;
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new ArrayList<>();
            roles.add(role);
        } else if (!roles.contains(role)) {
            roles.add(role);
        }
    }

    public UserDTO toDTO() {
        return new UserDTO(id, username, email, passwordHash, firstName, lastName, city);
    }
}
