package rzeznik.grzegorz.exotic_farm.farm;

import lombok.AllArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import rzeznik.grzegorz.exotic_farm.animal.Animal;
import rzeznik.grzegorz.exotic_farm.user.User;
import rzeznik.grzegorz.exotic_farm.user.UserDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    @ManyToMany
    @JoinTable(name = "Farm_users")
    private Set<User> users = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "Farm_admins")
    private Set<User> admins = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "farm")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Animal> animals = new HashSet<>();

    public Farm() {
    }

    public Farm(Integer id, String name, Set<User> users, Set<User> admins) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.admins = admins;
    }

    public Farm(String name, Set<User> users, Set<User> admins) {
        this.name = name;
        this.users = users;
        this.admins = admins;
    }

    public static Farm applyDTO(FarmDTO dto) {
        Set<User> admins = new HashSet<>();
        if (dto.getAdmins() != null) {
            admins = dto.getAdmins().stream()
                    .map(User::applyDTO)
                    .collect(Collectors.toSet());
        }
        Set<User> users = new HashSet<>();
        if (dto.getUsers() != null) {
            users = dto.getUsers().stream()
                    .map(User::applyDTO)
                    .collect(Collectors.toSet());
        }

        return new Farm(dto.getId(), dto.getName(), users, admins);
    }

    public FarmDTO toDTO() {
        Set<UserDTO> usersDTOs = users.stream().map(User::toDTO).collect(Collectors.toSet());
        Set<UserDTO> adminsDTOs = admins.stream().map(User::toDTO).collect(Collectors.toSet());
        return new FarmDTO(this.id, this.name, usersDTOs, adminsDTOs);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addAdmin(User admin) {
        this.admins.add(admin);
    }

}
