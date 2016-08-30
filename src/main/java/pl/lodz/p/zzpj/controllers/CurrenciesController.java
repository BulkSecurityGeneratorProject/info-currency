package pl.lodz.p.zzpj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lodz.p.zzpj.managers.CurrenciesManager;
import pl.lodz.p.zzpj.model.Currency;
import pl.lodz.p.zzpj.xml.XMLparserJAXB;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.Map;

@Controller
public class CurrenciesController {

    private static final String LAST_CURRENCIES_VIEW = "lastCurrencies";
    private static final String SINGLE_CURRENCY_VIEW = "currency";

    @Autowired
    private CurrenciesManager currenciesManager;

    @RequestMapping(value = "currencies", method = RequestMethod.GET)
    public String getLastCurrencies(Map<String, Object> model) {
        model.put("currenciesXML", currenciesManager.getLastCurrenciesXMLFromWebsite());
        return LAST_CURRENCIES_VIEW;
    }

    @RequestMapping(value = "currencies/{symbol}", method = RequestMethod.GET)
    public String getDailyCurrencyRate(@PathVariable String symbol, Map<String, Object> model) {
        model.put("currencyRates", currenciesManager.getDailyCurrencyRateFromWebsite(Currency.valueOf(symbol.toUpperCase())));
        return SINGLE_CURRENCY_VIEW;
    }

    public CurrenciesManager getCurrenciesManager() {
        return currenciesManager;
    }

    public void setCurrenciesManager(CurrenciesManager currenciesManager) {
        this.currenciesManager = currenciesManager;
    }
}
