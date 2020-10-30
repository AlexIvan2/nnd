package alex.app.service;

import alex.app.database.repository.CountryCodeRepository;
import alex.app.domen.CountryCode;
import alex.app.service.gatherer.Gatherer;
import alex.app.service.gatherer.exceptions.GathererException;
import liquibase.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApplicationService {

    @Autowired
    Gatherer gatherer;
    @Autowired
    CountryCodeRepository countryCodeRepository;

    private Map<Long, String> codesPerCountry;

    @PostConstruct
    void init() {
        List<CountryCode> countryCodes = new ArrayList<>();
        try {
            countryCodes = gatherer.gatherCountryCodes();
        } catch (GathererException e) {
            log.error("Gatherer exception: {}", e.getMessage());
            e.printStackTrace();
        }
        if (!countryCodes.isEmpty()) {
            countryCodeRepository.saveAll(countryCodes);
        }
        codesPerCountry = groupCodesPerCountries(countryCodeRepository.findAll());
    }

    public Optional<String> findCountryByPrefix(String code) {
        String country;
        do {
            country = codesPerCountry.get(Long.valueOf(code));
            code = StringUtils.chop(code);
            if (country != null) {
                break;
            }
        } while (StringUtil.isNotEmpty(code));
        return Optional.ofNullable(country);
    }

    Map<Long, String> groupCodesPerCountries(List<CountryCode> countryCodes) {
        Map<Long, String> result = new TreeMap<>(Collections.reverseOrder());
        for (CountryCode countryCode : countryCodes) {
            List<Long> codes = filterAndSplitCodes(countryCode.getCode());
            codes.forEach(code -> result.put(code, countryCode.getCountry()));
        }

        return result;
    }

    List<Long> filterAndSplitCodes(String code) {
        List<String> codes = Arrays.asList(code.split(","));
        return codes.stream()
                .map(item -> item.replaceAll("[^0-9]", ""))
                .filter(StringUtil::isNotEmpty)
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }
}
