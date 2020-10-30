package alex.app.service;


import alex.app.domen.CountryCode;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {

    @Mock(name = "codesPerCountry")
    Map<Long, String> codesPerCountry;

    @InjectMocks
    ApplicationService service;


    @Test
    public void testFilterAndSplitCodes() {
        List<Long> strings = service.filterAndSplitCodes("something, +371, +3722, something");
        assertEquals(2, strings.size());
        assertEquals(371, strings.get(0));
        assertEquals(3722, strings.get(1));
    }

    @Test
    public void testGroupCodesPerCountriesGivesSameAmountOfElementsOnValidInput() {
        List<CountryCode> countryCodes = prepareCountryCodes();
        Map<Long, String> result = service.groupCodesPerCountries(countryCodes);
        Map<Long, String> expectedResult = prepareExpectedResult();
        assertEquals(expectedResult.size(), result.size());
        assertTrue(CollectionUtils.isEqualCollection(expectedResult.keySet(), result.keySet()));
        assertTrue(CollectionUtils.isEqualCollection(expectedResult.values(), result.values()));
    }

    @Test
    public void testFindCountryByPrefixCalledOnceWhenTheCountryFound() {
        when(codesPerCountry.get(anyLong())).thenReturn("Latvia");
        Optional<String> countryByPrefix = service.findCountryByPrefix("110");
        verify(codesPerCountry, times(1)).get(anyLong());
        assertTrue(countryByPrefix.isPresent());
        assertEquals("Latvia", countryByPrefix.get());
    }

    @Test
    public void testFindCountryByPrefixCalledTillRunningOutOfDigits() {
        when(codesPerCountry.get(anyLong())).thenReturn(null);
        Optional<String> countryByPrefix = service.findCountryByPrefix("110");
        verify(codesPerCountry, times(3)).get(anyLong());
        assertFalse(countryByPrefix.isPresent());
    }

    private Map<Long, String> prepareExpectedResult() {
        Map<Long, String> result = new TreeMap<>(Collections.reverseOrder());
        result.put(100L, "CountryZero");
        result.put(101L, "CountryZero");
        result.put(110L, "CountryZero");
        result.put(1110L, "CountryZero");
        result.put(222L, "CountryTwo");
        result.put(233L, "CountryTwo");
        result.put(111L, "CountryOne");
        return result;
    }

    private List<CountryCode> prepareCountryCodes() {
        List<CountryCode> result = new ArrayList<>();
        result.add(new CountryCode(null, "100,101", "CountryZero"));
        result.add(new CountryCode(null, "110", "CountryZero"));
        result.add(new CountryCode(null, "1110", "CountryZero"));
        result.add(new CountryCode(null, "222", "CountryTwo"));
        result.add(new CountryCode(null, "233", "CountryTwo"));
        result.add(new CountryCode(null, "111", "CountryOne"));
        return result;
    }

}