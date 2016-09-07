package pl.lodz.p.zzpj.vm;

public class CurrencyVM {
    private String currency;
    private boolean upToDateRates;
    private String historicalDate;
    private String lowHistDate;
    private String highHistDate;

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

    public String getLowHistDate() { return lowHistDate; }

    public void setLowHistDate(String lowHistDate) { this.lowHistDate = lowHistDate; }

    public String getHighHistDate() { return highHistDate; }

    public void setHighHistDate(String highHistDate) { this.highHistDate = highHistDate; }

    @Override
    public String toString() {
        return "CurrencyVM{" +
                "currency='" + currency + '\'' +
                ", upToDateRates=" + upToDateRates +
                ", historicalDate=" + historicalDate +
                ", lowHistDate=" + lowHistDate +
                ", highHistDate=" + highHistDate +
                '}';
    }
}