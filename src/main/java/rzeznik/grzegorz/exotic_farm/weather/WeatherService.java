package rzeznik.grzegorz.exotic_farm.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rzeznik.grzegorz.exotic_farm.user.UserContextService;
import rzeznik.grzegorz.exotic_farm.user.UserDTO;
import rzeznik.grzegorz.exotic_farm.user.UserService;

@Service
public class WeatherService {
    private final String apiUrl = "http://api.openweathermap.org/data/2.5/weather";
    private final String apiKey = "9da62b652d43799056358549a2753ff3";
    @Autowired
    private RestTemplate weatherRestTemplate;
    @Autowired
    private UserContextService userContextService;
    @Autowired
    private UserService userService;

    public String downloadWeather() {
        String name = userContextService.userName();
        UserDTO user = userService.findUserByUsername(name).orElse(null);
        String city = user == null ? "Lublin" : user.getCity();
        String url = apiUrl + "?q=" + city
                + "&appId=" + apiKey
                + "&units=" + "metric"
                + "&lang=" + "pl";
        final ResponseEntity<String> response = weatherRestTemplate.getForEntity(url, String.class);
        System.out.println(response.getBody());
        return response.getBody();
    }
}
