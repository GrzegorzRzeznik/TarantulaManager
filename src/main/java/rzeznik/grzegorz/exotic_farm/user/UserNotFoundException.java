package rzeznik.grzegorz.exotic_farm.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found.");
    }


}
