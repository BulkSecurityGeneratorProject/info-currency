package pl.lodz.p.zzpj.managers;

import pl.lodz.p.zzpj.controllers.vm.CurrencyVM;
import pl.lodz.p.zzpj.model.Currency;
import pl.lodz.p.zzpj.model.ExchangeRatesSeries;

import java.util.ArrayList;

public interface CurrenciesManager {
    String getLastCurrenciesXMLFromWebsite();
    ExchangeRatesSeries getCurrencyRate(CurrencyVM request);
    Object getRatesDependsOnParams(CurrencyVM request);
    ArrayList<ExchangeRatesSeries> getRangeRates(CurrencyVM requste);
}
