package rzeznik.grzegorz.exotic_farm.animal;

public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException(Integer id) {
        super("Animal with id: " + id + " not found.");
    }
}
