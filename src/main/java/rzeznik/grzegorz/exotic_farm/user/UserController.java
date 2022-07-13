package rzeznik.grzegorz.exotic_farm.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;
    private final UserRegistrationValidationService validationService;
    private final UserContextService userContextService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          UserRegistrationValidationService validationService,
                          UserContextService userContextService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.validationService = validationService;
        this.userContextService = userContextService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String registrationFrom(Model model) {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        model.addAttribute("registrationData", dto);
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String registrationEffect(UserRegistrationDTO dto, Model model) {
        Map<String, String> exceptionMap = validationService.validateUser(dto);
        if (!exceptionMap.isEmpty()) {
            model.addAllAttributes(exceptionMap);
            model.addAttribute("registrationData", dto);
            return "registrationPage";
        }
        try{
            userService.registerUser(dto);
        }catch (EmailAlreadyExistsException e){
            model.addAttribute("emailAlreadyExists", e.getMessage());
            model.addAttribute("registrationData", dto);
            return "registrationPage";
        }
        model.addAttribute("email", dto.getEmail());
        return "welcomePage";
    }

    @GetMapping("/editAccount")
    public String editAccount(Model model){
        UserDTO userDTO = userService.findUserByUsername(userContextService.userName())
                .orElseThrow(UserNotFoundException::new);
        model.addAttribute("user", userDTO);
        return "accountEditPage";
    }
    @PostMapping("/editAccount")
    public String editAccount(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String email,
                              @RequestParam String city, Model model){
        UserDTO userDTO = userService.findUserByUsername(userContextService.userName())
                .orElseThrow(UserNotFoundException::new);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setCity(city);
        userService.save(userDTO);
        model.addAttribute("user", userDTO);
        return "accountEditPage";
    }

    @PostMapping("/changePassword")
    public String changePassword (Model model,
                                  @RequestParam String currentPassword,
                                  @RequestParam String newPassword){

        UserDTO userDTO = userService.findUserByUsername(userContextService.userName())
                .orElseThrow(UserNotFoundException::new);
        if (passwordEncoder.matches(currentPassword, userDTO.getPasswordHash())){
            userDTO.setPasswordHash(passwordEncoder.encode(newPassword));
            userService.save(userDTO);
        }else{
           throw new RuntimeException();
        }
        model.addAttribute("user", userDTO);
        return "accountEditPage";
    }

}
