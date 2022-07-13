package rzeznik.grzegorz.exotic_farm.user;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserRegistrationValidationService {

    public Map<String, String> validateUser (UserRegistrationDTO dto){
        Map<String, String> exceptionsMap = new HashMap<>();
        if (!dto.getFirstName().matches("^[A-Z]{1}[a-z]{2,}$")){
            exceptionsMap.put("firstNameValidationResult", "At least 3 letters, first uppercase.");
        }

        return exceptionsMap;
    }
}