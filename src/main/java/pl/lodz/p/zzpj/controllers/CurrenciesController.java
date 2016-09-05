package pl.lodz.p.zzpj.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.zzpj.controllers.vm.ConverterVM;
import pl.lodz.p.zzpj.controllers.vm.CurrencyVM;
import pl.lodz.p.zzpj.managers.Converter;
import pl.lodz.p.zzpj.managers.CurrenciesManager;
import pl.lodz.p.zzpj.model.ConverterResponse;
import pl.lodz.p.zzpj.model.Currency;
import pl.lodz.p.zzpj.model.CurrencyResponse;
import pl.lodz.p.zzpj.xml.XMLparserJAXB;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.Map;

@Controller
public class CurrenciesController {

    Logger logger = Logger.getLogger(CurrenciesController.class);

    private static final String LAST_CURRENCIES_VIEW = "lastCurrencies";
    private static final String SINGLE_CURRENCY_VIEW = "currency";
    private static final String CONVERTER_VIEW = "converter";

    @Autowired
    private CurrenciesManager currenciesManager;

    @Autowired
    private Converter converter;

    @RequestMapping(value = "currencies", method = RequestMethod.GET)
    public String getLastCurrencies(Map<String, Object> model) {
        logger.info("getLastCurrencies invoked");
        model.put("currenciesXML", currenciesManager.getLastCurrenciesXMLFromWebsite());
        return LAST_CURRENCIES_VIEW;
    }

    @RequestMapping(value = "currencies/{symbol}", method = RequestMethod.GET)
    public String getDailyCurrencyRate(@PathVariable String symbol, Map<String, Object> model) {
        logger.info("getDailyCurrencyRate invoked");
        model.put("currencyRates", currenciesManager.getDailyCurrencyRateFromWebsite(Currency.valueOf(symbol.toUpperCase())));
        return SINGLE_CURRENCY_VIEW;
    }

    @RequestMapping(value = "/currencies/find",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrencyResponse> getCurrenciesRate(@Valid @RequestBody CurrencyVM currencyVM, HttpServletRequest request, Map<String, Object> model) {
        logger.info("getCurrenciesRate invoked");
        CurrencyResponse response = new CurrencyResponse();
        response.setData(currenciesManager.getRatesDependsOnParams(currencyVM));
        //response.setData(currenciesManager.getCurrencyRate(currencyVM));
        return new ResponseEntity<CurrencyResponse>(response, HttpStatus.OK);
 //       return LAST_CURRENCIES_VIEW;
    }

    @RequestMapping(value = "/converter")
    public String getConverter() {
        logger.info("getConverter invoked");
        return CONVERTER_VIEW;
    }

    @RequestMapping(value = "/converter/calculate",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConverterResponse> getConverterResult(@Valid @RequestBody ConverterVM converterVM, HttpServletRequest request, Map<String, Object> model) {
        logger.info("getConverterResult invoked");
        ConverterResponse response = new ConverterResponse();
        response.setData(converter.convert(converterVM));
        return new ResponseEntity<ConverterResponse>(response, HttpStatus.OK);
    }

    public CurrenciesManager getCurrenciesManager() {
        return currenciesManager;
    }

    public void setCurrenciesManager(CurrenciesManager currenciesManager) {
        this.currenciesManager = currenciesManager;
    }
}
