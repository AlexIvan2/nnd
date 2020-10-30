package alex.app.service.gatherer;


import alex.app.domen.CountryCode;
import alex.app.service.gatherer.exceptions.GathererException;
import alex.app.service.gatherer.exceptions.PageNotFoundException;
import alex.app.utils.NumberUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@PropertySource("classpath:/wikigatherer.properties")
public class WikiGathererImpl implements Gatherer {

    @Value("#{'${artifactDictionary}'.split(',')}")
    private List<String> artifactDictionary;
    @Value("#{'${URL}'}")
    private String URL;

    public List<CountryCode> gatherCountryCodes() throws GathererException {
        Document doc = Optional.ofNullable(getDocument(URL))
                .orElseThrow(PageNotFoundException::new);
        Elements pageTables = doc.select(".mw-content-ltr tbody");
        Optional<Element> targetTable = detectTargetTable(pageTables);
        return targetTable.isPresent() ? gatherCountryCodes(targetTable.get()) : Collections.emptyList();
    }

    private Document getDocument(String url) throws GathererException {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error("Jsoup: Unable to connect {}", e.getMessage());
            throw new GathererException("Unable to connect.", e);
        }
        return doc;
    }

    private List<CountryCode> gatherCountryCodes(Element table) throws GathererException {
        try {
            List<CountryCode> countryCodes = new ArrayList<>();
            Elements tableRows = table.select("tr");
            tableRows.remove(0);
            for (Element row : tableRows) {
                Elements element = row.select("tr").select("td");
                CountryCode countryCode = CountryCode.builder()
                        .country(element.get(0).text())
                        .code(resolveCode(element.get(1).text()))
                        .build();
                countryCodes.add(countryCode);
            }
            return countryCodes;
        } catch (Exception e) {
            log.error("Selector parse exception. There might have no table tags: {}", e.getMessage());
            throw new GathererException("Can't query tag. ", e);
        }
    }

    private String resolveCode(String code) {
        return NumberUtils.removeArtifacts(code.replace(" ", ""), artifactDictionary);
    }

    private static Optional<Element> detectTargetTable(Elements pageTables) throws GathererException {
        try {
            for (Element table : pageTables) {
                Element tag = table.select("tr").select("th").get(0);
                if (tag.text().equals("Country, Territory or Service")) {
                    return Optional.of(table);
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            log.error("Selector parse exception. There might have no table tags: {}", e.getMessage());
            throw new GathererException("Can't query tag. ", e);
        }
    }

}
