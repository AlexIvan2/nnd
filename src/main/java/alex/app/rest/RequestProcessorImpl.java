package alex.app.rest;

import alex.app.domen.dto.Response;
import alex.app.exceptions.InternalServerException;
import alex.app.exceptions.PrefixNotFoundException;
import alex.app.service.ApplicationService;
import alex.app.utils.NumberUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RequestProcessorImpl implements RequestProcessor {

    @Autowired
    private ApplicationService service;
    @Autowired
    private ResponseBuilder responseBuilder;


    @Override
    public Response process(String request) {
        try {
            String phoneNumber = prepareNumber(request);
            String result = service.findCountryByPrefix(phoneNumber).orElseThrow(PrefixNotFoundException::new);
            return responseBuilder.buildSuccess(result);
        } catch (PrefixNotFoundException e) {
            log.error("Prefix not found for: {}", request);
            return responseBuilder.buildFail(request, e);
        } catch (InternalServerException e) {
            log.error("Internal server error");
            return responseBuilder.buildFail(request, e);
        } catch (RuntimeException e) {
            log.error("Runtime exception");
            return responseBuilder.buildFail(request, e);
        }
    }

    private String prepareNumber(String phoneNumber) {
        phoneNumber = NumberUtils.clearAllButDigits(phoneNumber);
        return NumberUtils.removeDoubleZeroPrefix(phoneNumber);
    }
}
