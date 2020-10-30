package alex.app.exceptions;

import alex.app.common.ResponseStatus;

public abstract class ApplicationException extends RuntimeException {

    private ResponseStatus responseStatus;

    public ApplicationException() {

    }

    public ApplicationException(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ApplicationException(String message, ResponseStatus responseStatus) {
        super(message);
        this.responseStatus = responseStatus;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
