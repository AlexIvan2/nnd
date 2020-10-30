package alex.app.service.gatherer;

import alex.app.domen.CountryCode;
import alex.app.service.gatherer.exceptions.GathererException;

import java.util.List;

public interface Gatherer {

    List<CountryCode> gatherCountryCodes() throws GathererException;

}
