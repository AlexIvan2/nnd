package alex.app.exceptions;

import alex.app.common.ResponseStatus;

public class InternalServerException extends ApplicationException{

    public InternalServerException() {
        super(ResponseStatus.INTERNAL_SERVER_ERROR);
    }

}
