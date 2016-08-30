package pl.lodz.p.zzpj.managers;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import pl.lodz.p.zzpj.model.Currency;
import pl.lodz.p.zzpj.model.ExchangeRatesSeries;
import pl.lodz.p.zzpj.xml.XMLparser;
import pl.lodz.p.zzpj.xml.XMLparserJAXB;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * CurrenciesManager implementation that downloads currencies from Narodowy Bank Polski site.
 */
@Component("currenciesManager")
public class CurrenciesManagerNBP implements CurrenciesManager {

    private static final String COURSES_URL_PREFIX = "http://www.nbp.pl/kursy/xml/";
    private static final String LAST_COURSES_URL = "http://www.nbp.pl/kursy/xml/LastA.xml";

    private XMLparser parser;

    @Override
    public String getLastCurrenciesXMLFromWebsite() {
        String content = "";
        URL url = null;
        try {
            url = new URL(LAST_COURSES_URL);
            InputStream input = url.openStream();
            content = IOUtils.toString(input, "utf-8");
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public ExchangeRatesSeries getDailyCurrencyRateFromWebsite(Currency currencySymbol) {
        parser = new XMLparserJAXB();
        try {
            return parser.parseXMLtoObject(currencySymbol);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
