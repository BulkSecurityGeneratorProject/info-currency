package pl.lodz.p.zzpj.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.zzpj.domain.User;
import pl.lodz.p.zzpj.repository.UserRepository;
import pl.lodz.p.zzpj.vm.ConverterVM;
import pl.lodz.p.zzpj.vm.CurrencyVM;
import pl.lodz.p.zzpj.managers.Converter;
import pl.lodz.p.zzpj.managers.CurrenciesManager;
import pl.lodz.p.zzpj.model.ConverterResponse;
import pl.lodz.p.zzpj.model.CurrencyResponse;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing currencies endpoints.
 */
@Controller
public class CurrenciesController {

    private Logger logger = Logger.getLogger(CurrenciesController.class);

    private static final String LAST_CURRENCIES_VIEW = "lastCurrencies";
    private static final String SINGLE_CURRENCY_VIEW = "currency";
    private static final String CONVERTER_VIEW = "converter";

    @Autowired
    private CurrenciesManager currenciesManager;

    @Autowired
    private Converter converter;

    @Inject
    private UserRepository userRepository;

    /**
     * Maps /currencies endpoint.
     * @param model
     * @return
     */
    @RequestMapping(value = "currencies", method = RequestMethod.GET)
    public String getLastCurrencies(Map<String, Object> model) {
        logger.info("getLastCurrencies invoked");
        model.put("currenciesXML", currenciesManager.getLastCurrenciesXMLFromWebsite());
        return LAST_CURRENCIES_VIEW;
    }

    /**
     * Maps /currencies/symbol endpoints.
     * @param symbol currency symbol (eq. "USD")
     * @param model
     * @return
     */
    @RequestMapping(value = "currencies/{symbol}", method = RequestMethod.GET)
    public String getDailyCurrencyRate(@PathVariable String symbol, Map<String, Object> model) {
        logger.info("getDailyCurrencyRate invoked");
        return SINGLE_CURRENCY_VIEW;
    }

    /**
     * Maps /currencies/find endpoint.
     * @param currencyVM
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/currencies/find",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CurrencyResponse> getCurrenciesRate(@Valid @RequestBody CurrencyVM currencyVM, HttpServletRequest request, Map<String, Object> model) {
        logger.info("getCurrenciesRate invoked");
        logger.info(currencyVM.toString());
        User user = (userRepository.findOneByLogin(request.getUserPrincipal().getName())).get();
        CurrencyResponse response = currenciesManager.getRatesDependsOnParams(currencyVM, user.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Maps /converter endpoint
     * @return
     */
    @RequestMapping(value = "/converter")
    public String getConverter() {
        logger.info("getConverter invoked");
        return CONVERTER_VIEW;
    }

    /**
     * Maps /converter/calculate endpoint
     * @param converterVM
     * @param request
     * @param model
     * @return
     */
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
