package alex.app.rest;

import alex.app.domen.dto.Response;

public interface RequestProcessor {

    Response process(String request);

}
