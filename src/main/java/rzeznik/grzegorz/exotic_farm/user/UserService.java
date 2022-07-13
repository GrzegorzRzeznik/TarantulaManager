package rzeznik.grzegorz.exotic_farm.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void registerUser(UserRegistrationDTO dto) {
        userRepository.findByEMail(dto.getEmail())
                .ifPresent(e -> {
                    throw new EmailAlreadyExistsException("Email " + dto.getEmail() + " already in use");
                });
        final String passwordHash = passwordEncoder.encode(dto.getPassword());
        final User user = User.applyDTO(dto, passwordHash);
        user.addRole(roleRepository.findByRoleName(Role.USER));
        userRepository.save(user);
    }

    public Optional<UserDTO> findUserByUsername(String name) {
        return userRepository.findUserByUsername(name).map(User::toDTO);
    }

    public void save(UserDTO userDTO) {
        userRepository.save(User.applyDTO(userDTO));
    }
}
