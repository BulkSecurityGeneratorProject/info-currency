package pl.lodz.p.zzpj.managers;

import pl.lodz.p.zzpj.controllers.vm.CurrencyVM;
import pl.lodz.p.zzpj.model.Currency;
import pl.lodz.p.zzpj.model.ExchangeRatesSeries;

public interface CurrenciesManager {
    String getLastCurrenciesXMLFromWebsite();
    ExchangeRatesSeries getDailyCurrencyRateFromWebsite(Currency currencySymbol);
    ExchangeRatesSeries getCurrencyRate(CurrencyVM request);
    Object getRatesDependsOnParams(CurrencyVM request);
}
