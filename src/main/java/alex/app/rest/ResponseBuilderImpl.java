package alex.app.rest;

import alex.app.common.ResponseStatus;
import alex.app.domen.dto.Response;
import alex.app.exceptions.InternalServerException;
import alex.app.exceptions.PrefixNotFoundException;
import org.springframework.stereotype.Component;

/**
 * The Class provides functionality to build failed and success {@link Response}
 */
@Component
public class ResponseBuilderImpl implements ResponseBuilder {


    @Override
    public Response buildSuccess(String payload) {
        return build(payload, ResponseStatus.SUCCESS);
    }

    @Override
    public Response buildFail(String payload, InternalServerException e) {
        return build(null, e.getResponseStatus());
    }

    @Override
    public Response buildFail(String payload, PrefixNotFoundException e) {
        return build(null, e.getResponseStatus());
    }

    @Override
    public Response buildFail(String payload, RuntimeException e) {
        return build(null, ResponseStatus.INTERNAL_SERVER_ERROR);
    }

    private Response build(String payload, ResponseStatus responseStatus) {
        return Response.builder()
                .countryCode(payload)
                .status(responseStatus)
                .build();
    }
}
