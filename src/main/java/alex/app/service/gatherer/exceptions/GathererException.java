package alex.app.service.gatherer.exceptions;

public class GathererException extends Exception {

    public GathererException() {
    }

    public GathererException(String message, Throwable cause) {
        super(message, cause);
    }

    public GathererException(String message) {
        super(message);
    }
}
