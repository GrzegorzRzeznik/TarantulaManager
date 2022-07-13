package rzeznik.grzegorz.exotic_farm.user;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String s) {
        super(s);
    }
}
