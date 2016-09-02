package pl.lodz.p.zzpj.managers;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import pl.lodz.p.zzpj.controllers.vm.CurrencyVM;
import pl.lodz.p.zzpj.model.Currency;
import pl.lodz.p.zzpj.model.ExchangeRatesSeries;
import pl.lodz.p.zzpj.xml.XMLparser;
import pl.lodz.p.zzpj.xml.XMLparserJAXB;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * CurrenciesManager implementation that downloads currencies from Narodowy Bank Polski site.
 */
@Component("currenciesManager")
public class CurrenciesManagerNBP implements CurrenciesManager {

    private static final String COURSES_URL_PREFIX = "http://www.nbp.pl/kursy/xml/";
    private static final String LAST_COURSES_URL = "http://www.nbp.pl/kursy/xml/LastA.xml";
    private static final String CURRENCY_URL_PREFIX = "http://api.nbp.pl/api/exchangerates/rates/c/";
    private static final String CURRENCY_DAILY_SUFFIX = "/today?format=xml";
    private static final String FORMAT_SUFFIX = "?format=xml";

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
//        parser = new XMLparserJAXB();
//        try {
//            return parser.parseXMLtoObject(currencySymbol);
//        } catch (JAXBException | IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    public ExchangeRatesSeries getCurrencyRate(CurrencyVM request) {
        parser = new XMLparserJAXB();
        String url = null;
        if(request.isUpToDateRates() && (request.getCurrency() != null)) {
            url = CURRENCY_URL_PREFIX + request.getCurrency() + CURRENCY_DAILY_SUFFIX;
        } else if(request.getHistoricalDate() != null && (request.getCurrency() != null)) {
            url = CURRENCY_URL_PREFIX + request.getCurrency() + "/" + request.getHistoricalDate() + FORMAT_SUFFIX;
        } else {
            return null;
        }

        try {
            return parser.parseXMLtoObject(url);
        } catch (JAXBException | IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
