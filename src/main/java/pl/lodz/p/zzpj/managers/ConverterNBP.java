package pl.lodz.p.zzpj.managers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.zzpj.controllers.vm.ConverterVM;
import pl.lodz.p.zzpj.controllers.vm.CurrencyVM;
import pl.lodz.p.zzpj.model.ExchangeRatesSeries;

import java.math.BigDecimal;

@Component("converter")
public class ConverterNBP implements Converter {

    Logger logger = Logger.getLogger(ConverterNBP.class);

    private CurrencyVM currency1;
    private CurrencyVM currency2;
    private ExchangeRatesSeries currencyRates1;
    private ExchangeRatesSeries currencyRates2;

    private BigDecimal moneyValue;

    @Autowired
    CurrenciesManager currenciesManager;

    @Override
    public String convert(ConverterVM converterVM) {
        logger.info("convert method");
        setUpCurrencies(converterVM);
        setUpCurrencyRates();

        moneyValue = BigDecimal.valueOf(Double.parseDouble(converterVM.getMoneyValue()));
        moneyValue = moneyValue.multiply(currencyRates1.getRates().getRate().getMid());
        moneyValue = moneyValue.divide(currencyRates2.getRates().getRate().getMid(), 2, BigDecimal.ROUND_DOWN);
        return moneyValue.toString();
    }

    private void setUpCurrencies(ConverterVM converterVM) {
        currency1 = new CurrencyVM();
        currency1.setUpToDateRates(true);
        currency1.setCurrency(converterVM.getCurrency1());

        currency2 = new CurrencyVM();
        currency2.setUpToDateRates(true);
        currency2.setCurrency(converterVM.getCurrency2());
    }

    private void setUpCurrencyRates() {
        currencyRates1 = new ExchangeRatesSeries();
        currencyRates1 = currenciesManager.getCurrencyRate(currency1);

        currencyRates2 = new ExchangeRatesSeries();
        currencyRates2 = currenciesManager.getCurrencyRate(currency2);
    }
}
