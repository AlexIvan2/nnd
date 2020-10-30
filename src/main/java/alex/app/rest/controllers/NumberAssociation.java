package alex.app.rest.controllers;

import alex.app.domen.dto.Response;
import alex.app.rest.RequestProcessor;
import alex.app.rest.validation.ValidateNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping(value = "/rest/v1", produces = APPLICATION_JSON_VALUE)
public class NumberAssociation {

    @Autowired
    RequestProcessor requestProcessor;

    /**
     * GET method to get Country by Phone Number
     * @param number String type Phone Number
     * @return {@link Response} Object
     */
    @RequestMapping(value = "/check/{number}", method = RequestMethod.GET)
    public Response checkNumber(@PathVariable @ValidateNumber String number) {
        return requestProcessor.process(number);
    }
}
