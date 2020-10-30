package alex.app.rest;

import alex.app.domen.dto.Response;
import alex.app.exceptions.InternalServerException;
import alex.app.exceptions.PrefixNotFoundException;
import org.springframework.stereotype.Component;

@Component
public interface ResponseBuilder {

    Response buildSuccess(String payload);
    Response buildFail(String payload, InternalServerException e);
    Response buildFail(String payload, PrefixNotFoundException e);
    Response buildFail(String payload, RuntimeException e);

}
