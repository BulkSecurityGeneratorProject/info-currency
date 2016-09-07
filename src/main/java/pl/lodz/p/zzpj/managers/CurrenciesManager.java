package pl.lodz.p.zzpj.managers;

import pl.lodz.p.zzpj.vm.CurrencyVM;
import pl.lodz.p.zzpj.model.CurrencyResponse;
import pl.lodz.p.zzpj.model.ExchangeRatesSeries;

import java.util.ArrayList;

public interface CurrenciesManager {
    String getLastCurrenciesXMLFromWebsite();
    ExchangeRatesSeries getCurrencyRate(CurrencyVM request);
    CurrencyResponse getRatesDependsOnParams(CurrencyVM request, Long userId);
    ArrayList<ExchangeRatesSeries> getRangeRates(CurrencyVM requste);
}
