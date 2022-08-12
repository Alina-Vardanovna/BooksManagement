package exception;

public class AuthorNotFoundException extends Throwable {

    public AuthorNotFoundException() {
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }

}
