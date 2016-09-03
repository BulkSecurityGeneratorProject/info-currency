package pl.lodz.p.zzpj.controllers.vm;

import java.util.Date;

/**
 * Created by piotr on 26.08.16.
 */
public class CurrencyVM {
    private String currency;
    private boolean upToDateRates;
    private String historicalDate;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isUpToDateRates() {
        return upToDateRates;
    }

    public void setUpToDateRates(boolean upToDateRates) {
        this.upToDateRates = upToDateRates;
    }

    public String getHistoricalDate() {
        return historicalDate;
    }

    public void setHistoricalDate(String historicalDate) {
        this.historicalDate = historicalDate;
    }

    @Override
    public String toString() {
        return "CurrencyVM{" +
                "currency='" + currency + '\'' +
                ", upToDateRates=" + upToDateRates +
                ", historicalDate=" + historicalDate +
                '}';
    }
}