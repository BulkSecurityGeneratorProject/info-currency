package pl.lodz.p.zzpj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lodz.p.zzpj.managers.CurrenciesManager;

import java.util.Map;

@Controller
public class CurrenciesController {

    private static final String LAST_CURRENCIES_VIEW = "lastCurrencies";

    @Autowired
    private CurrenciesManager currenciesManager;

    @RequestMapping(value = "currencies", method = RequestMethod.GET)
    public String getLastCurrencies(Map<String, Object> model) {
        model.put("currenciesXML", currenciesManager.getLastCurrenciesXMLFromWebsite());
        return LAST_CURRENCIES_VIEW;
    }

    public CurrenciesManager getCurrenciesManager() {
        return currenciesManager;
    }

    public void setCurrenciesManager(CurrenciesManager currenciesManager) {
        this.currenciesManager = currenciesManager;
    }
}
