package rzeznik.grzegorz.exotic_farm.farm;

public class FarmNotFoundException extends RuntimeException{
    public FarmNotFoundException(Integer productId) {
        super("Farm with ID: " + productId+" not found.");
    }
}
