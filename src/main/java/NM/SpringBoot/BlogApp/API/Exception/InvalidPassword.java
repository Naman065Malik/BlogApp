package NM.SpringBoot.BlogApp.API.Exception;

public class InvalidPassword extends RuntimeException {
    public InvalidPassword(String message) {
        super(message);
    }
}
