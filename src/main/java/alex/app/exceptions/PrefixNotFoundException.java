package alex.app.exceptions;

import alex.app.common.ResponseStatus;

public class PrefixNotFoundException extends ApplicationException {

    public PrefixNotFoundException() {
        super(ResponseStatus.NOT_FOUND);
    }

}
